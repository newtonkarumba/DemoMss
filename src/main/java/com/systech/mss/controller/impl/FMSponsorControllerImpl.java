package com.systech.mss.controller.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.controller.FMSponsorController;
import com.systech.mss.controller.vm.*;
import com.systech.mss.domain.Config;
import com.systech.mss.domain.LoginIdentifier;
import com.systech.mss.domain.Profile;
import com.systech.mss.domain.common.Clients;
import com.systech.mss.repository.ActivityTrailRepository;
import com.systech.mss.repository.ConfigRepository;
import com.systech.mss.repository.ProfileRepository;
import com.systech.mss.service.ActivityTrailService;
import com.systech.mss.service.FMMemberClient;
import com.systech.mss.service.FundMasterClient;
import com.systech.mss.service.dto.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FMSponsorControllerImpl implements FMSponsorController {

    @Inject
    private ActivityTrailRepository trailRepository;

    @Inject
    private FundMasterClient fundMasterClient;

    @Inject
    public FMMemberClient fmMemberClient;

    @Inject
    private Logger log;

    @Inject
    ConfigRepository configRepository;

    @Inject
    ProfileRepository profileRepository;

    @Inject
    private ActivityTrailService activityTrailService;

    private static final String uploadFolderName = "BillsGenerated";
    private static volatile String uploadFolder = null;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyy");
    LocalDateTime now = LocalDateTime.now();

    @Override
    public Response getSponsorDetails(long mssuserid, String sponsorRefNo) {
        try {
            //activityTrailService.logActivityTrail(mssuserid, " Get Sponsor Details");
            FmListBooleanDto fmListDTO = fundMasterClient.getSponsorDetails(sponsorRefNo);
            if (fmListDTO.isSuccess()) {
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows().get(0)).build())
                        .build();
            }
        } catch (Exception ignored) {
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Could not find sponsor details"
                ).build())
                .build();
    }


    @Override
    public Response getContributionBillsPerSponsor(long mssuserid, long schemeId, long sponsorId, int start, int size) {
        activityTrailService.logActivityTrail(mssuserid, "Viewed Contribution Bills");
        SponsorContributionDTO sponsorContributionDTO = fundMasterClient.getContributionBillsPerSponsor(schemeId, sponsorId, 0, 100000000);
        Config config = configRepository.getActiveConfig();
        if (config.getClient().getName().equalsIgnoreCase("ETL")) {
            if (sponsorContributionDTO != null)
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true)
                                .data(sponsorContributionDTO.getRows()).build())
                        .build();
        } else {
            String configPath = config.getFmBasePath();
            if (configPath.endsWith("/"))
                configPath = configPath.substring(0, configPath.length() - 5);
            else
                configPath = configPath.substring(0, configPath.length() - 4);

            if (sponsorContributionDTO != null) {
                List<Object> list = sponsorContributionDTO.getRows();
                List<BillVM> billVMS = new ArrayList<>();
                for (Object o : list) {
//                    log.error(String.valueOf(o));
                    BillVM billVM = BillVM.from(o);
                    if (billVM != null) {
                        String url = configPath + "/download?file=" + billVM.getFileName();
                        billVM.setFileName(url);
                    }
                    billVMS.add(billVM);
                }
                sponsorContributionDTO.setBillVMS(billVMS);
                sponsorContributionDTO.setRows(null);
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true)
                                .data(sponsorContributionDTO.getBillVMS()).build())
                        .build();
            }
        }


        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();

    }

    @Override
    public Response filterContributionBillsPerSponsor(long mssuserid, long schemeId, long sponsorId, Date dateFrom, Date dateTo, int start, int size) {
        activityTrailService.logActivityTrail(mssuserid, "Filtered Contribution Bills By Date");
        SponsorContributionDTO sponsorContributionDTO = fundMasterClient.filterContributionBillsPerSponsor(schemeId, sponsorId, dateFrom, dateTo, 0, 100000000);
        if (sponsorContributionDTO != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(sponsorContributionDTO.getRows()).build())
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }

    @Override
    public Response searchContributionBillsPerSponsorEtl(long mssuserid, long schemeId, long sponsorId, String searchKey, int start, int size) throws IOException, ParseException {
        activityTrailService.logActivityTrail(mssuserid, "Searched Contribution Bills on " +now.format(format));

        ObjectMapper objectMapper=new ObjectMapper();
        SponsorContributionDTO sponsorContributionDTO = fundMasterClient.getContributionBillsPerSponsor(schemeId, sponsorId, 0, 100000000);
        if (sponsorContributionDTO.isSuccess()) {
            List<Object> contributionDTORows = sponsorContributionDTO.getRows();
            List<ContributionBillVM> contributionBillVMS = new ArrayList<>();
            if (!contributionDTORows.isEmpty()) {
                for (Object object : contributionDTORows) {
                    String jsonString = objectMapper.writeValueAsString(object);
                    ContributionBillVM contributionBillVM = objectMapper.readValue(jsonString, ContributionBillVM.class);
                    if (contributionBillVM.getSponsor().contains(searchKey.toUpperCase()) || contributionBillVM.getPeriod().contains(searchKey.toUpperCase())|| contributionBillVM.getParticulars().contains(searchKey.toUpperCase()) || contributionBillVM.getStatus().contains(searchKey.toUpperCase())) {
                        contributionBillVMS.add(contributionBillVM);
                    }
                }

               return Response.ok().entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(contributionBillVMS)
                                .build())
                        .build();

            }
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();

    }

    @Override
    public Response searchContributionBillsPerSponsor(long mssuserid, long schemeId, long sponsorId, String searchKey, int start, int size) {
        activityTrailService.logActivityTrail(mssuserid, "Searched Contribution Bills");
        SponsorContributionDTO sponsorContributionDTO = fundMasterClient.searchContributionBillsPerSponsor(schemeId, sponsorId, searchKey, 0, 100000000);
        if (sponsorContributionDTO != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(sponsorContributionDTO.getRows()).build())
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }

    Long convertStringToLong(String number) {
        Float stringFloat = new Float(number);
        return stringFloat.longValue();
    }

    BigDecimal convertStringToBd(String currency) {
        currency = currency.replace(",", "");
        return new BigDecimal(currency);
    }


    @Override
    public Response getContributionsBillsSummary(long mssUserId, long schemeId, long sponsorId, int start, int size) throws JsonProcessingException, ParseException {
        SponsorContributionDTO fmListDTO = fundMasterClient.getContributionBillsPerSponsor(schemeId, sponsorId, 0, 100000000);
        if (fmListDTO.getRows().size() > 0) {
            List<Object> rows = fmListDTO.getRows();
            List<SponsorBillTotalPeriodVM> sponsorBillTotalPeriodVMS = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            if (rows != null) {
                //Collections.reverse(rows);
                rows = rows.size() > 10 ? rows.subList(0, 9) : rows;
                for (Object contributionBillingDTO :
                        rows) {
                    SponsorBillTotalPeriodVM sponsorBillTotalPeriodVM = new SponsorBillTotalPeriodVM();
                    String jsonString = new ObjectMapper().writeValueAsString(contributionBillingDTO);
                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
                    if (jsonObject != null) {
                        Config config = configRepository.getActiveConfig();
                        if (config.getClient().equals(Clients.ETL)) {
                            sponsorBillTotalPeriodVM.setAmount(convertStringToBd(jsonObject.get("total").toString()).divide(BigDecimal.valueOf(1000000)));
                            sponsorBillTotalPeriodVM.setContributionMonthYear(jsonObject.get("period").toString());
                        } else {
                            sponsorBillTotalPeriodVM.setAmount(convertStringToBd(jsonObject.get("amount").toString()).divide(BigDecimal.valueOf(1000000)));
                            sponsorBillTotalPeriodVM.setContributionMonthYear(jsonObject.get("contributionMonthYear").toString());
                        }
                        sponsorBillTotalPeriodVMS.add(sponsorBillTotalPeriodVM);
//                            sponsorBillTotalPeriodVM.setAmount(convertStringToLong(jsonObject.get("amount").toString())/1000000);

                    }


                }
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(sponsorBillTotalPeriodVMS).build())
                        .build();
            }
        }
        return ErrorMsg("Mss Api call failed");

    }

    @Override
    public Response getMembersDueForRetirementPerSponsor(Long id, Long profileId) {

        FmListDTO fmListDTO = fundMasterClient.getMembersDueForRetirementPerSponsor(id, profileId);
        if (fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows().get(0)).build())
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }

    @Override
    public Response searchForSponsorMemberDetails(String identifierValue, String value, String profile, String sponsorId, String schemeId) {
        logActivityTrail(Long.parseLong(sponsorId), "Requested sponsor member details");
        FmListDTO fmListDTO = fundMasterClient.searchForSponsorMemberDetails(identifierValue, value, profile, sponsorId, schemeId);
        if (fmListDTO.isSuccess()) {
            if (!fmListDTO.getRows().isEmpty())
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                        .build();
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg(
                            "No results found"
                    ).build())
                    .build();
        }
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }

    @Override
    public Response checkIdnumberExits(String sponsorId, String idType, String idnumber, String isSponsorId) {
        FmListDTO fmListDTO = fundMasterClient.checkIdnumberExits(sponsorId, idType, idnumber, isSponsorId);
        if (fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows().get(0)).build())
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }

    @Override
    public Response getSponsorPotentialMembers(Long schemeId, Long sponsorId) {
        FmListDTO fmListDTO = fundMasterClient.getSponsorPotentialMembers(schemeId, sponsorId);
        if (fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows().get(0)).build())
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }

    @Override
    public Response getschemesponsorid(Long schemeId, Long profileId) {
        FmListDTO fmListDTO = fundMasterClient.getschemesponsorid(schemeId, profileId);
        if (fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows().get(0)).build())
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }

    @Override
    public Response getSponsorMemberSchemes(long sponsorId) {
        FmListDTO fmListDTO = fundMasterClient.getSponsorMemberSchemes(sponsorId);
        if (fmListDTO != null && fmListDTO.isSuccess()) {
            if (fmListDTO.getRows() != null)
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                        .build();
        }
        return ErrorMsg("Mss Api call failed");
    }


    @Override
    public Response searchSponsorContributionsReceiptsEtl(long mssuserid, long schemeId, String sponsorId, String searchKey) throws IOException {
        activityTrailService.logActivityTrail(mssuserid, "Searched Contribution Receipts");
        ReceiptDTO fmListDTO = fundMasterClient.getContributionsReceiptsPerSponsor(schemeId, sponsorId);
        ObjectMapper objectMapper = new ObjectMapper();
        if (fmListDTO != null) {
            List<Object> receiptRows = fmListDTO.getRows();
            List<SponsorContributionReceiptVM> sponsorContributionReceiptVMS = new ArrayList<>();
            if (!receiptRows.isEmpty()) {
                for (Object object : receiptRows) {
                    String jsonString = objectMapper.writeValueAsString(object);
                    SponsorContributionReceiptVM sponsorContributionReceiptVM = objectMapper.readValue(jsonString, SponsorContributionReceiptVM.class);
                    if (sponsorContributionReceiptVM.getPayee().contains(searchKey.toUpperCase()) || sponsorContributionReceiptVM.getParticular().contains(searchKey.toUpperCase())|| sponsorContributionReceiptVM.getReceiptno().contains(searchKey.toUpperCase()) || sponsorContributionReceiptVM.getPaymentmethod().contains(searchKey.toUpperCase())) {
                        sponsorContributionReceiptVMS.add(sponsorContributionReceiptVM);
                    }
                }
               return Response.ok().entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(sponsorContributionReceiptVMS)
                                .build())
                        .build();

            }
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }

    @Override
    public Response getSponsorContributionsReceipts(long mssuserid, long schemeId, String sponsorId) {
        activityTrailService.logActivityTrail(mssuserid, "Viewed Receipts");
        ReceiptDTO fmListDTO = fundMasterClient.getContributionsReceiptsPerSponsor(schemeId, sponsorId);
        if (fmListDTO != null) {
            if (fmListDTO.getRows() != null)
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                        .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getSponsorMemberListing(long mssuserid, long id, String profile, long schemeId, int start, int size) {
        switch (profile) {
            case "PRINCIPAL OFFICER":
                profile = "SPONSOR";
                break;

            case "CRM":
                break;

            default:
        }

        activityTrailService.logActivityTrail(mssuserid, "Viewed Members in Scheme");
        FmListDTO fmListDTO = getMemberList(id, profile, schemeId, start, size);
        if (fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }

    private FmListDTO getMemberList(long id, String profile, long schemeId, int start, int size) {
        return fundMasterClient.getSponsorMemberListing(id, profile, schemeId, start, size);
    }

    @Override
    public Response getSponsorMemberListingCount(Long id, String profile, Long schemeId) throws IOException {

        switch (profile) {
            case "PRINCIPAL OFFICER":

            case "CRM":
                profile = "SPONSOR";
                break;

            default:
        }

        FmListDTO fmListDTO = getMemberList(id, profile, schemeId, 0, 2000000);

        if (fmListDTO.isSuccess()) {
            int count = fmListDTO.getRows().size();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonObject jsonObject = Json.createObjectBuilder()
                    .add("count", count)
                    .build();
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(objectMapper.readValue(jsonObject.toString(), Object.class)).build())
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }


    @Override
    public Response getSponsorMemberClasses(long sponsorId) {
        JSONArray fmSponsorDTO = fundMasterClient.getSponsorMemberClasses(sponsorId);
        if (fmSponsorDTO != null) {
            List<MemberClassVm> memberClassVms = new ArrayList<>();
            for (int i = 0; i < fmSponsorDTO.length(); i++) {
                try {
                    MemberClassVm memberClassVm = MemberClassVm.from(fmSponsorDTO.getJSONObject(i));
                    if (memberClassVm != null)
                        memberClassVms.add(memberClassVm);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(memberClassVms).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getSponsorCompanyCostCentres(long sponsorId) {
        JSONArray fmSponsorDTO = fundMasterClient.getSponsorCompanyCostCentres(sponsorId);
        if (fmSponsorDTO != null) {
//            log.error(fmSponsorDTO.toString());
            List<CostCenterVm> costCenterVms = new ArrayList<>();
            for (int i = 0; i < fmSponsorDTO.length(); i++) {
                try {
                    CostCenterVm costCenterVm = CostCenterVm.from(fmSponsorDTO.getJSONObject(i));
                    if (costCenterVm != null)
                        costCenterVms.add(costCenterVm);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(costCenterVms).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getSponsorMemberAndPensionerCount(String sponsorRefNo) throws IOException {
        FmListBooleanDto fmListDTO = fundMasterClient.getSponsorMemberAndPensionerCount(sponsorRefNo);
        if (fmListDTO != null && fmListDTO.isSuccess()) {
            List<Object> counts = fmListDTO.getRows();
            if (!counts.isEmpty()) {
                Object statement = counts.get(0);
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonString = objectMapper.writeValueAsString(statement);
                SponsorPensionerCountVM sponsorPensionerCountVM = objectMapper.readValue(jsonString, SponsorPensionerCountVM.class);

                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(sponsorPensionerCountVM).build())
                        .build();
            }
        }
        return ErrorMsg("Mss Api call failed");

    }

    @Override
    public Response bookContributionBill(long mssuserid, @Valid BillRequestVM billRequestVM) {
        activityTrailService.logActivityTrail(mssuserid, "Booked a Contribution Bill");
        FmListDTO contributionBill = fundMasterClient.bookContributionBill(billRequestVM);
        if (contributionBill.isSuccess()) {
            return Response.ok().entity(SuccessVM.builder().success(true).msg(contributionBill.getMessage()).build()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false)
                        .msg(contributionBill.getMessage()).build())
                .build();
    }

    @Override
    public Response savePotentialMember(PotentialMemberVM potentialMemberVM) {
        String s = getPotentialMemberJson(potentialMemberVM);
        if (s != null)
            return savePotentialMember_(s);
        return ErrorMsg("Failed, please try again");
    }

    String getPotentialMemberJson(PotentialMemberVM potentialMemberVM) {
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject();
            jsonObject.put("companyId", potentialMemberVM.getCompanyId());
            jsonObject.put("schemeId", potentialMemberVM.getSchemeId());
            jsonObject.put("staffNo", potentialMemberVM.getStaffNo());
            jsonObject.put("idNo", potentialMemberVM.getIdNumber());
            jsonObject.put("pinNo", potentialMemberVM.getPinNo());
            jsonObject.put("nationalPenNo", potentialMemberVM.getNationalPenNo());
            jsonObject.put("postalAddress", potentialMemberVM.getPostalAddress());
            jsonObject.put("fixedPhone", potentialMemberVM.getFixedPhone());
            jsonObject.put("secondaryPhone", potentialMemberVM.getSecondaryPhoneNumber());
            jsonObject.put("building", potentialMemberVM.getBuilding());
            jsonObject.put("road", potentialMemberVM.getRoad());
            jsonObject.put("town", potentialMemberVM.getTown());
            jsonObject.put("residentialAddress", potentialMemberVM.getResidentialAddress());
            jsonObject.put("country", potentialMemberVM.getCountry());
            jsonObject.put("department", potentialMemberVM.getDepartment());
            jsonObject.put("designation", potentialMemberVM.getDesignation());
            jsonObject.put("title", potentialMemberVM.getTitle());
            jsonObject.put("dob", potentialMemberVM.getDateOfBirth());
            jsonObject.put("surname", potentialMemberVM.getLastname());
            jsonObject.put("firstname", potentialMemberVM.getFirstname());
            jsonObject.put("othernames", potentialMemberVM.getOthernames());
            jsonObject.put("currentAnnualPensionableSalary", "0.00");
            jsonObject.put("gender", potentialMemberVM.getGender());
            jsonObject.put("maritalStatus", potentialMemberVM.getMaritalStatus());
            jsonObject.put("mbshipStatus", "ACTIVE");
            jsonObject.put("dateOfEmployment", potentialMemberVM.getDateOfAppointment());
            jsonObject.put("dateJoinedScheme", potentialMemberVM.getDateJoinedScheme());
            jsonObject.put("mclassId", potentialMemberVM.getMclassId());
            jsonObject.put("disabled", "NO");
            jsonObject.put("email", potentialMemberVM.getEmailAddress());
            jsonObject.put("sponsorId", potentialMemberVM.getSponsorId());
//            jsonObject.put("placeOfBirthDistrictId", p);
//            jsonObject.put("placeOfBirthTAId", );
//            jsonObject.put("placeOfBirthVillageId", );
//            jsonObject.put("permanentDistrictId", );
//            jsonObject.put("permanentTAId", );
//            jsonObject.put("permanentVillageId", );
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    String getPotentialMembersSponsorIdandMemberId(SponsorIdAndMemberIdVm sponsorIdAndMemberIdVm) {
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject();
            jsonObject.put("potentialMemberId", sponsorIdAndMemberIdVm.getId());
            jsonObject.put("sponsorId", sponsorIdAndMemberIdVm.getSponsorId());
            jsonObject.put("action", sponsorIdAndMemberIdVm.getAction());
            jsonObject.put("message", sponsorIdAndMemberIdVm.getMessage());

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response savePotentialMember_(String json) {
        boolean saveMember = fundMasterClient.savePotentialMember(json);
        if (saveMember) {
            return Response.ok().entity(SuccessVM.builder().success(true).msg("Member registration successfully").build()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }


    public Response approvePotentialMember(String json) {
        boolean saveMember = fundMasterClient.approvePotentialMember(json);
        if (saveMember) {
            return Response.ok().entity(SuccessVM.builder().success(true).msg("Member registration successfully").build()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }

    @Override
    public Response getSponsorMemberByName(long mssuserid,
                                           String name,
                                           long id,
                                           String profile,
                                           long schemeId,
                                           int start,
                                           int size) throws IOException {
        activityTrailService.logActivityTrail(mssuserid, "Searched for  a Member");
        FmListDTO fmListDTO = getMemberList(id, profile, schemeId, 0, 2000000);

        if (fmListDTO.isSuccess()) {
            List<Object> members = fmListDTO.getRows();
            List<SponsorMemberVM> sponsorMemberVMS = new ArrayList<>();
            if (!members.isEmpty()) {
                for (Object object : members) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ;
                    String jsonString = objectMapper.writeValueAsString(object);
                    SponsorMemberVM sponsorMemberVM = objectMapper.readValue(jsonString, SponsorMemberVM.class);
                    sponsorMemberVMS.add(sponsorMemberVM);
                }
                List<SponsorMemberVM> sponsorMemberVMList = new ArrayList<>();
                for (SponsorMemberVM sponsorMemberVM : sponsorMemberVMS) {
                    if (sponsorMemberVM.getFirstname().equals(name)) {
                        sponsorMemberVMList.add(sponsorMemberVM);
                        log.info("Found");
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sponsorMemberVMList);
                    }

                }
                ObjectMapper objectMapper = new ObjectMapper();
                Response.ok().entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sponsorMemberVMList))
                                .build())
                        .build();
                log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sponsorMemberVMList));
            }


        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }

    @Override
    public Response cancelBill(long id) {
        FmListDTO success = fundMasterClient.cancelBill(id);
        if (success.isSuccess()) {
            return Response.ok().entity(SuccessVM.builder().success(true).msg(success.getMessage()).build()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        success.getMessage()
                ).build())
                .build();


    }

    @Override
    public Response getSponsorDashboardMenuStatistics(long sponsorId) {
        //activityTrailService.logActivityTrail(mssuserid, "Viewed Receipts");
        TilesDTO fmListDTO = fundMasterClient.getSponsorDashboardMenuStatistics(sponsorId);
        if (fmListDTO != null) {
            if (fmListDTO.getRows() != null)
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                        .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getSponsorDetailsByIdentifier(long schemeId, String identifier, String identifierValue) {
        Config config = configRepository.getActiveConfig();
        List<SponsorSchemeVM> sponsorSchemeVM = fmMemberClient.getSponsorDetailsByIdentifier(config.getClient(), schemeId, identifier, identifierValue);
        if (!sponsorSchemeVM.isEmpty())
            return SuccessMsg("Done", sponsorSchemeVM);
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response searchSponsorDetails(SearchSponsorDetailsVM searchSponsorDetailsVM) {
        Config config = configRepository.getActiveConfig();
        Profile profile = profileRepository.findByName("SPONSOR");
        List<SponsorSchemeVM> sponsorSchemeVM = null;
        LoginIdentifier ordinal = profile.getLoginIdentifier();
        switch (ordinal) {
            case PHONE:
                sponsorSchemeVM = fmMemberClient.getSponsorDetailsByIdentifier(
                        config.getClient(),
                        searchSponsorDetailsVM.getSchemeId(),
                        "PHONE",
                        searchSponsorDetailsVM.getPhone());
                break;

            case SPONSOR_PROD_NO:

            case EMAIL:
                sponsorSchemeVM = fmMemberClient.getSponsorDetailsByIdentifier(
                        config.getClient(),
                        searchSponsorDetailsVM.getSchemeId(),
                        "EMAIL",
                        searchSponsorDetailsVM.getEmail());
                break;

            default:
        }
        if (sponsorSchemeVM != null) {
            return SuccessMsg("Done", sponsorSchemeVM);
        }
        return ErrorMsg("Member details not found");
    }

    @Override
    public Response getSponsorUssdBenefitPaymentRequest(long schemeId, long sponsorId) {
        ResultsDTO fmListDTO = fundMasterClient.getSponsorUssdBenefitPaymentRequest(schemeId, sponsorId);
        if (fmListDTO != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getSponsorPotentialMembers(long schemeId, long sponsorId) {
        TilesDTO fmListDTO = fundMasterClient.getSponsorPotentialMembers(schemeId, sponsorId);
        if (fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getPotentialMemberBeneficiaries(long memberId) {
        TilesDTO fmListDTO = fundMasterClient.getPotentialmemberbeneficiaries(memberId);
        if (fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getPotentialMemberDetails(long memberId) {
        TilesDTO fmListDTO = fundMasterClient.getpotentialmemberdetails(memberId);
        if (fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response approveOrDissaproveSponsorPotentialMember(SponsorIdAndMemberIdVm sponsorIdAndMemberIdVm) {
        String s = getPotentialMembersSponsorIdandMemberId(sponsorIdAndMemberIdVm);
        if (s != null)
            return approvePotentialMember(s);
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response updateUssdBenefitPaymentStatus(String ussdBenefitId, String action, String message) {
        boolean updateUssdBenefitPaymentStatus = fundMasterClient.updateUssdBenefitPaymentStatus(ussdBenefitId, action, message);
        if (updateUssdBenefitPaymentStatus) {
            return Response.ok().entity(SuccessVM.builder().success(true).msg("Benefit status successfully Updated").build()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Benefit Request could not be Updated, please try again after some time"
                ).build())
                .build();
    }

    @Override
    public Response printBulkMemberStatement() {
        BulkDTO bulkDTO = fundMasterClient.printUnitizedStatement();
        if (bulkDTO != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(bulkDTO.getMessage()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response searchSponsorBenefitsFm(String name, long sponsorId, String searchKey) throws IOException {
        TilesDTO fmListDTO = fundMasterClient.getSponsorBenefitsFm(name, sponsorId);

        ObjectMapper objectMapper = new ObjectMapper();
        if (fmListDTO.isSuccess()) {
            List<Object> fmListDTORows = fmListDTO.getRows();
            List<SponsorBenefitsVM> sponsorBenefitsVMS = new ArrayList<>();
            if (!fmListDTORows.isEmpty()) {
                for (Object object : fmListDTORows) {
                    String jsonString = objectMapper.writeValueAsString(object);
                    SponsorBenefitsVM sponsorBenefitsVM = objectMapper.readValue(jsonString, SponsorBenefitsVM.class);
                    if (sponsorBenefitsVM.getMemberName().contains(searchKey.toUpperCase()) || sponsorBenefitsVM.getMembershipNo().contains(searchKey.toUpperCase()) || sponsorBenefitsVM.getDateOfCalc().contains(searchKey.toUpperCase()) || sponsorBenefitsVM.getPaymentType().contains(searchKey.toUpperCase()) ) {
                        sponsorBenefitsVMS.add(sponsorBenefitsVM);
                    }
                }

                return Response.ok().entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(sponsorBenefitsVMS)
                                .build())
                        .build();

            }
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();

    }

    @Override
    public Response getSponsorBenefitsFm(String name, long sponsorId) {
        TilesDTO fmListDTO = fundMasterClient.getSponsorBenefitsFm(name, sponsorId);
        if (fmListDTO != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response searchSponsorTPFA(long mssuserid, long schemeId, long sponsorId, String receiptStatus, String searchKey) throws IOException {
        activityTrailService.logActivityTrail(mssuserid, "Searched TPFA");
        ResultsDTO resultsDTO = fundMasterClient.getSponsorTPFA(schemeId, sponsorId, receiptStatus);
        ObjectMapper objectMapper = new ObjectMapper();
        if (resultsDTO != null) {
            List<Object> resultsDTORows = resultsDTO.getRows();
            List<TPFAVM> tpfaVMS = new ArrayList<>();
            if (!resultsDTORows.isEmpty()) {
                for (Object object : resultsDTORows) {
                    String jsonString = objectMapper.writeValueAsString(object);
                    TPFAVM tpfavm = objectMapper.readValue(jsonString, TPFAVM.class);
                    if (tpfavm.getMembershipNo().contains(searchKey.toUpperCase()) || tpfavm.getName().contains(searchKey.toUpperCase())) {
                        tpfaVMS.add(tpfavm);
                    }
                }

              return  Response.ok().entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(tpfaVMS)
                                .build())
                        .build();

            }
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }

    @Override
    public Response getSponsorTPFA(long schemeId, long sponsorId, String receiptStatus) {
        ResultsDTO resultsDTO = fundMasterClient.getSponsorTPFA(schemeId, sponsorId, receiptStatus);
        if (resultsDTO != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(resultsDTO.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    private void logActivityTrail(long userId, String msg) {
        trailRepository.create(trailRepository.getActivityTrail(userId, msg));
    }

    private Response ErrorMsg(String msg) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        msg
                ).build())
                .build();
    }

    protected Response SuccessMsg(String msg, Object data) {
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).msg(msg).data(data).build())
                .build();
    }


}
