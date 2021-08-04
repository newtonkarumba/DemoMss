package com.systech.mss.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.systech.mss.config.APICall;
import com.systech.mss.controller.vm.BillRequestVM;
import com.systech.mss.controller.vm.CostCenterVm;
import com.systech.mss.controller.vm.MemberClassVm;
import com.systech.mss.domain.Config;
import com.systech.mss.domain.User;
import com.systech.mss.domain.common.Clients;
import com.systech.mss.repository.ConfigRepository;
import com.systech.mss.service.dto.*;
import com.systech.mss.util.StringUtil;
import com.systech.mss.vm.OutgoingSMSVM;
import com.systech.mss.vm.SMSVM;
import com.systech.mss.vm.benefitrequest.AddBeneficiaryVM;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.*;

@RequestScoped
public class FundMasterClient {
    private WebTarget target;
    @Inject
    private ConfigRepository configRepository;
    Config config;
    private MultivaluedMap<String, Object> myHeaders;
    @Inject
    Logger log;

    @PostConstruct
    public void setup() {
        Client client = ClientBuilder.newClient();
        if (getFMConfig().isPresent()) {
            config = getFMConfig().get();
            myHeaders = new MultivaluedHashMap<>();
            myHeaders.add("username", config.getFmUsername());
            myHeaders.add("password", config.getFmPassword());
            target = client.target(config.getFmBasePath()); //http:168.235.82.130:8084/Xe/api
        }
    }

    private String getParams(Object... args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object s :
                args) {
            stringBuilder.append(s).append("/");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    public FmListDTO getSchemes() {
        try {
            Response response = target.path(APICall.SCHEME_GETSCHEMES)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }

    public FmListBooleanDto getSponsorMemberAndPensionerCount(String sponsorRefNo) {
        try {
            Response response = target.path(APICall.GET_SPONSOR_MEMBER_AND_PENSIONERS_COUNT)
                    .path(String.valueOf(sponsorRefNo))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListBooleanDto.class);
        } catch (Exception e) {
            return FmListBooleanDto.builder().success(false).build();
        }
    }

    public TotalCountAndRowsDTO getMemberBankDetails(long memberId, String yesNo) {
        try {
            Response response = target.path(APICall.GET_MEMBER_BANK_DETAILS)
                    .path(String.valueOf(memberId))
                    .path(yesNo)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(TotalCountAndRowsDTO.class);
        } catch (Exception e) {
            return TotalCountAndRowsDTO.builder().build();
        }
    }

    public TilesDTO getMemberBenefitsFm(String name, long memberId) {
        try {
            Response response = target.path(APICall.GET_EMPLOYER_AND_MEMBER_BENEFITS_FM)
                    .path(name)
                    .path(String.valueOf(memberId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(TilesDTO.class);
        } catch (Exception e) {
            return TilesDTO.builder().success(false).build();
        }
    }

    public FmListBooleanDto getSponsorDetails(String sponsorRefNo) {
        try {
            Response response = target.path(APICall.GET_SPONSOR_DETAILS)
                    .path(String.valueOf(sponsorRefNo))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();


            return response.readEntity(FmListBooleanDto.class);
        } catch (Exception e) {
            return FmListBooleanDto.builder().success(false).build();
        }
    }

    public SponsorContributionDTO getContributionBillsPerSponsor(long schemeId, long sponsorId, int start, int size) {
        try {

            String apiPath;
            if (config.getClient().equals(Clients.ETL)) {
                apiPath = APICall.GET_CONTRIBUTION_BILLS_PER_SPONSOR_OLD;
                log.info("************ etl");
            } else {
                log.info("****************** others");
                apiPath = APICall.GET_CONTRIBUTION_BILLS_PER_SPONSOR;
            }
            Response response = target.path(apiPath)
                    .path(String.valueOf(schemeId))
                    .path(String.valueOf(sponsorId))
                    .queryParam("start", start)
                    .queryParam("size", size)
                    .request()
                    .headers(myHeaders)
                    .get();

            String s = response.readEntity(String.class);
//            log.error(s);
            s = s.replaceAll(",\n]}", "]}");
            Gson g = new Gson();
            SponsorContributionDTO sponsorContributionDTO = g.fromJson(s, SponsorContributionDTO.class);
            sponsorContributionDTO.setSuccess(true);
            return sponsorContributionDTO;
        } catch (Exception e) {
            SponsorContributionDTO sponsorContributionDTO = new SponsorContributionDTO();
            sponsorContributionDTO.setSuccess(false);
            return sponsorContributionDTO;
        }
    }

    public SponsorContributionDTO filterContributionBillsPerSponsor(long schemeId, long sponsorId, Date dateFrom, Date dateTo, int start, int size) {
        try {
            Response response = target.path(APICall.FILTER_CONTRIBUTION_BILLS_PER_SPONSOR)
                    .path(String.valueOf(schemeId))
                    .path(String.valueOf(sponsorId))
                    .path(String.valueOf(dateFrom))
                    .path(String.valueOf(dateTo))
                    .queryParam("start", start)
                    .queryParam("size", size)
                    .request()
                    .headers(myHeaders)
                    .get();

            String s = response.readEntity(String.class);
            s = s.replaceAll(",\n]}", "]}");
            Gson g = new Gson();
            SponsorContributionDTO sponsorContributionDTO = g.fromJson(s, SponsorContributionDTO.class);
            sponsorContributionDTO.setSuccess(true);
            return sponsorContributionDTO;
        } catch (Exception e) {
            SponsorContributionDTO sponsorContributionDTO = new SponsorContributionDTO();
            sponsorContributionDTO.setSuccess(false);
            return sponsorContributionDTO;
        }
    }

    public SponsorContributionDTO searchContributionBillsPerSponsor(long schemeId, long sponsorId, String searchKey, int start, int size) {
        try {
            Response response = target.path(APICall.SEARCH_CONTRIBUTION_BILLS_PER_SPONSOR)
                    .path(String.valueOf(schemeId))
                    .path(String.valueOf(sponsorId))
                    .path(searchKey)
                    .queryParam("start", start)
                    .queryParam("size", size)
                    .request()
                    .headers(myHeaders)
                    .get();

            String s = response.readEntity(String.class);
            s = s.replaceAll(",\n]}", "]}");
            Gson g = new Gson();
            SponsorContributionDTO sponsorContributionDTO = g.fromJson(s, SponsorContributionDTO.class);
            sponsorContributionDTO.setSuccess(true);
            return sponsorContributionDTO;
        } catch (Exception e) {
            SponsorContributionDTO sponsorContributionDTO = new SponsorContributionDTO();
            sponsorContributionDTO.setSuccess(false);
            return sponsorContributionDTO;
        }
    }

    public ReceiptDTO getContributionsReceiptsPerSponsor(long schemeId, String sponsorId) {
        try {
            String apiPath;
            if (config.getClient().equals(Clients.ETL)) {
                apiPath = APICall.GET_SPONSOR_CONTRIBUTIONS_RECEIPT_ETL;
                log.info("************ etl");
            } else {
                log.info("****************** others");
                apiPath = APICall.GET_SPONSOR_CONTRIBUTIONS_RECEIPT;
            }
            Response response = target.path(apiPath)
                    .path(String.valueOf(schemeId))
                    .path(String.valueOf(sponsorId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            String s = response.readEntity(String.class);
            s = s.replaceAll(",\n]}", "]}");
            Gson g = new Gson();
            return g.fromJson(s, ReceiptDTO.class);
        } catch (Exception e) {
            return ReceiptDTO.builder().build();
        }
    }

    public FmListDTO getMembersDueForRetirementPerSponsor(Long id, Long profileId) {
        try {
            Response response = target.path(APICall.GET_MEMBERS_DUE_FOR_RETIREMENT_PER_SPONSOR)
                    .path(String.valueOf(id))
                    .path(String.valueOf(profileId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }

    public FmListDTO bookContributionBill(BillRequestVM billRequestVM) {
        try {
            String apiPath;
            if (config.getClient().equals(Clients.ETL)) {
                apiPath = APICall.BOOK_CONTRIBUTION_BILLS_OLD;
                log.info("etl");
            } else {
                log.info("others");
                apiPath = APICall.BOOK_CONTRIBTION_BILLS;
            }
            Response response = target.path(apiPath)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.entity(billRequestVM, MediaType.APPLICATION_JSON));

            String s = response.readEntity(String.class);
            //            http://129.159.250.225:8086/mss/resources/api/getpotentialmemberdetails/341779088

            return new ObjectMapper().readValue(s, FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).message(e.getMessage()).build();
        }
    }

    public FmListDTO searchForSponsorMemberDetails(String identifierValue, String value, String profile, String sponsorId, String schemeId) {
        try {
            Response response = target.path(APICall.SEARCH_FOR_SPONSOR_MEMBER_DETAILS)
                    .path(identifierValue)
                    .path(value)
                    .path(profile)
                    .path(sponsorId)
                    .path(schemeId)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }

    public FmListDTO checkIdnumberExits(String sponsorId, String idType, String idnumber, String isSponsorId) {
        try {
            Response response = target.path(APICall.CHECK_IF_IDNUMBER_EXISTS)
                    .path(sponsorId)
                    .path(idType)
                    .path(idnumber)
                    .path(isSponsorId)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }

    public FmListDTO getSponsorPotentialMembers(Long schemeId, Long sponsorId) {
        try {
            Response response = target.path(APICall.GET_POTENTIAL_MEMBERS_PER_SPONSOR)
                    .path(String.valueOf(schemeId))
                    .path(String.valueOf(sponsorId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }

    public FmListDTO getschemesponsorid(Long schemeId, Long profileId) {
        try {
            Response response = target.path(APICall.GET_SCHEME_SPONSOR_ID)
                    .path(String.valueOf(schemeId))
                    .path(String.valueOf(profileId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }

    public TilesDTO getPotentialmemberbeneficiaries(long memberId) {
        try {
            Response response = target.path(APICall.GET_POTENTIAL_MEMBERS_BENEFICIARY_DETAILS)
                    .path(String.valueOf(memberId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            s = s.replaceAll(",\n]}", "]}");
            Gson g = new Gson();
            TilesDTO tilesDTO = g.fromJson(s, TilesDTO.class);
            tilesDTO.setSuccess(true);
            return tilesDTO;
        } catch (Exception e) {
            TilesDTO tilesDTO = new TilesDTO();
            tilesDTO.setSuccess(false);
            return tilesDTO;
        }
    }


    public TilesDTO getpotentialmemberdetails(long memberId) {
        try {
            Response response = target.path(APICall.GET_POTENTIAL_MEMBERS_DETAILS)
                    .path(String.valueOf(memberId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            String s = response.readEntity(String.class);
            s = s.replaceAll(",\n]}", "]}");
            Gson g = new Gson();
            TilesDTO tilesDTO = g.fromJson(s, TilesDTO.class);
            tilesDTO.setSuccess(true);
            return tilesDTO;
        } catch (Exception e) {
            TilesDTO tilesDTO = new TilesDTO();
            tilesDTO.setSuccess(false);
            return tilesDTO;
        }
    }

    public FmListDTO getSponsorMemberSchemes(long sponsorId) {
        try {
            Response response = target.path(APICall.GET_MEMBER_SCHEMES)
                    .path(String.valueOf(sponsorId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            s = s.replaceAll(",\n]}", "]}");
            Gson g = new Gson();
            FmListDTO fmListDTO = g.fromJson(s, FmListDTO.class);
            fmListDTO.setSuccess(true);
            return fmListDTO;
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }

    public List<MemberClassVm> getSponsorMemberClassesList(long sponsorId) {
        org.json.JSONArray fmSponsorDTO = getSponsorMemberClasses(sponsorId);
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
            return memberClassVms;
        }
        return null;
    }

    public org.json.JSONArray getSponsorMemberClasses(long sponsorId) {
        try {
            Response response = target.path(APICall.GET_SPONSOR_MEMBER_CLASSES)
                    .path(String.valueOf(sponsorId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            try {
                return new org.json.JSONArray(s);
            } catch (JSONException e) {
                org.json.JSONObject jsonObject = new org.json.JSONObject(s);
                if (jsonObject.getBoolean("success")) {
                    Object o = jsonObject.get("rows");
//                    log.error(o.toString());
                    return new org.json.JSONArray(o.toString());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public List<CostCenterVm> getSponsorCompanyCostCentresList(long sponsorId) {
        org.json.JSONArray fmSponsorDTO = getSponsorCompanyCostCentres(sponsorId);
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
            return costCenterVms;
        }
        return null;
    }

    public org.json.JSONArray getSponsorCompanyCostCentres(long sponsorId) {
        try {
            Response response = target.path(APICall.GET_SPONSOR_COST_CENTRES)
                    .path(String.valueOf(sponsorId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
//            log.error(s);
            try {
                return new org.json.JSONArray(s);
            } catch (JSONException e) {
                org.json.JSONObject jsonObject = new org.json.JSONObject(s);
                if (jsonObject.getBoolean("success")) {
                    Object o = jsonObject.get("rows");
//                    log.error(o.toString());
                    return new org.json.JSONArray(o.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public FmListDTO getSponsorMemberListing(long id, String profile, long schemeId, int start, int size) {
        try {
            Response response = target.path(APICall.GET_MEMBER_LISTING)
                    .path(String.valueOf(id))
                    .path(profile)
                    .path(String.valueOf(schemeId))
                    .queryParam("start", start)
                    .queryParam("size", size)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            s = s.replaceAll(",\n]}", "]}");
            return new Gson().fromJson(s, FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }

    public SucessAndRowsDTO getCompaniesEtl(long sponsorId, int start, int size) {
        try {
            Response response = target.path(APICall.GET_SPONSOR_COST_CENTRES)
                    .path(String.valueOf(sponsorId))
                    .queryParam("start", start)
                    .queryParam("size", size)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            s = s.replaceAll(",\n]}", "]}");
            return new Gson().fromJson(s, SucessAndRowsDTO.class);
        } catch (Exception e) {
            return SucessAndRowsDTO.builder().success(false).build();
        }
    }

    public SucessAndRowsDTO getMemberClassesEtl(long sponsorId, int start, int size) {
        try {

            Response response = target.path(APICall.GET_SPONSOR_MEMBER_CLASSES)
                    .path(String.valueOf(sponsorId))
                    .queryParam("start", start)
                    .queryParam("size", size)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            s = s.replaceAll(",\n]}", "]}");
            return new Gson().fromJson(s, SucessAndRowsDTO.class);
        } catch (Exception e) {
            return SucessAndRowsDTO.builder().success(false).build();
        }
    }

    public FmListDTO cancelBill(long id) {
        try {

            Response response = target.path(APICall.CANCEL_BILL)
                    .path(String.valueOf(id))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .delete();

            String s = response.readEntity(String.class);
            return new ObjectMapper().readValue(s, FmListDTO.class);
        } catch (Exception e) {
            log.info("Message E: " + e.getMessage());
            return FmListDTO.builder().success(false).message("Failed to process Bill").build();
        }
    }

    public MessageModelDTO saveBill(String json) {
        try {
//            BillExceptionDTO billDTO = BillExceptionDTO.builder()
//                    .rows(billExceptionDTO.getRows()).build();
            String apiPath;
            if (config.getClient().equals(Clients.ETL)) {
                apiPath = APICall.SAVE_BILL_EXCEPTION_ETL;
                log.info("************ etl");
            } else {
                log.info("****************** others");
                apiPath = APICall.SAVE_BILL_EXCEPTION;
            }
            Response response = target.path(apiPath)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.entity(json, MediaType.APPLICATION_JSON));

            String s = response.readEntity(String.class);
            log.error("S LOG IS:" + s);
            return new ObjectMapper().readValue(s, MessageModelDTO.class);
        } catch (Exception e) {
            log.info("Message E: " + e.getMessage());
            return MessageModelDTO.builder().success(false).message("Failed to process Bill").build();
        }
    }

    public boolean saveorupdateBatchPotentialMember(List<MemberUploadDTO> data) {
        Map<String, Object> batchDataMap = new HashMap<>();
        batchDataMap.put("totalCount", data.size());
        batchDataMap.put("rows", data);
        try {
            Response response = target.path(APICall.SAVE_BATCH_POTENTIAL_MEMBER)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.entity(batchDataMap, MediaType.APPLICATION_JSON));

            String s = response.readEntity(String.class);
            log.error(s);
            //log.info(json);

            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean checkBillExceptions(ContributionBillingDTO contributionBillingDTO) {
        try {
            Response response = target.path(APICall.CHECK_BILL_EXCEPTIONS)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.entity(contributionBillingDTO, MediaType.APPLICATION_JSON));

            String s = response.readEntity(String.class);
            log.error(s);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public long getMemberClassId(String code) {
        String apiPath;
        if (config.getClient().equals(Clients.ETL)) {
            apiPath = APICall.GET_SPONSOR_MEMBER_CLASSES;
            log.info("************ etl");
        } else {
            log.info("****************** others");
            apiPath = APICall.GET_MEMBERCLASS_INFO;
        }
        Response response = target.path(apiPath).path(code).request(MediaType.APPLICATION_JSON_TYPE).headers(myHeaders).get();
        if (response.getStatus() == 200) {
            return response.readEntity(MemberClassDTO.class).getId();
        }
        return 0;

    }

    public long getCompanyId(String code) {
        String apiPath;
        if (config.getClient().equals(Clients.ETL)) {
            apiPath = APICall.GET_SPONSOR_COST_CENTRES;
            log.info("************ etl");
        } else {
            log.info("****************** others");
            apiPath = APICall.GET_COMPANY_INFO;
        }
        Response response = target.path(apiPath).path(code).request(MediaType.APPLICATION_JSON_TYPE).headers(myHeaders).get();
        if (response.getStatus() == 200) {
            return response.readEntity(CompanyDTO.class).getId();
        }
        return 0;

    }

    public boolean savePotentialMember(String json) {
        try {
            Response response = target.path(APICall.SAVE_OR_UPDATE_MEMBER)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.entity(json, MediaType.APPLICATION_JSON));

            String s = response.readEntity(String.class);
            log.error(s);
            log.info(json);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean saveBanks(String json) {
        try {
            Response response = target.path(APICall.SAVE_BANK_DETAILS)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.entity(json, MediaType.APPLICATION_JSON));

            String s = response.readEntity(String.class);
            log.error(s);
            log.info(json);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateUssdBenefitPaymentStatus(String ussdBenefitId, String action, String message) {
        try {
            String url = getParams(ussdBenefitId, action, message);
            url = APICall.UPDATE_USSD_BENEFIT_PAYMENT_STATUS + url;
            Response response = target.path(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.text("NONE"));
            String s = response.readEntity(String.class);
//            log.error(s);
            return new org.json.JSONObject(s).getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBankDefaultPoint(String yesNo, long bankDetailId) {
        try {
            String url = getParams(yesNo, bankDetailId);
            url = APICall.UPDATE_BANK_DEFAULT_POINT + url;
            Response response = target.path(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.text("NONE"));
            String s = response.readEntity(String.class);
//            log.error(s);
            return new org.json.JSONObject(s).getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean approvePotentialMember(String json) {
        try {
            Response response = target.path(APICall.APPROVE_POTENTIAL_MEMBER)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.entity(json, MediaType.APPLICATION_JSON));

            String s = response.readEntity(String.class);
            log.error(s);
            log.info(json);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Optional<Config> getFMConfig() {
        return configRepository
                .findAll()
                .stream()
                .findFirst();
    }

    public PensionerDTO getPensionerDetails(long pensionerId) {
        try {
            Response response = target.path(APICall.GET_PENSIONER_DETAILS)
                    .path(String.valueOf(pensionerId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
//        log.info(response.readEntity(String.class));

            return response.readEntity(PensionerDTO.class);
        } catch (Exception e) {
            return PensionerDTO.builder().success(false).build();
        }
    }

    public PensionerPayrollDTO getPensionerPayrolls(String pensionerNo, Long schemeId) {

        try {
            Response response = target.path(APICall.GET_LAST_PAYROLL_PER_PENSIONER)
                    .path(String.valueOf(pensionerNo))
                    .path(String.valueOf(schemeId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
//            log.error(response.readEntity(String.class));
            return response.readEntity(PensionerPayrollDTO.class);
        } catch (Exception e) {
            return PensionerPayrollDTO.builder().success("false").build();
        }
    }

    public FmListDTO getPensionerBeneficiaries(long memberId) {
        try {
            Response response;
            response = target.path(APICall.GET_MEMBER_BENEFICIARIES)
                    .path(String.valueOf(memberId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
//        log.info(response.readEntity(String.class));

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }

    public PensionerDTO getPensionerPayrollDeductions(String pensionerNo, long schemeId) {

        try {
            Response response = target.path(APICall.GET_PENSIONER_DEDUCTIONS)
                    .path(String.valueOf(pensionerNo))
                    .path(String.valueOf(schemeId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
//            log.error(response.readEntity(String.class));
            return response.readEntity(PensionerDTO.class);
        } catch (Exception e) {
            return PensionerDTO.builder().success(false).build();
        }
    }

    public FmListDTO getPensionerPayrollYears() {
        try {
            Response response;
            response = target.path(APICall.GET_PAYROLL_YEARS)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            //log.info(response.readEntity(String.class));

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }

    public FmListDTO getPensionAdvice(String schemeId, String pensionerId, String year) {
        try {
            Response response;
            response = target.path(APICall.GET_PENSION_ADVICE)
                    .path(schemeId)
                    .path(pensionerId)
                    .path(year)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }

    public PensionerDTO addPensionerBeneficiary(AddBeneficiaryVM addBeneficiaryVM) {

        try {
            Response response = target.path(APICall.SAVE_OR_UPDATE_BENEFICIARY_DETAILS)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.entity(addBeneficiaryVM, MediaType.APPLICATION_JSON));
            return response.readEntity(PensionerDTO.class);
        } catch (Exception e) {
            return PensionerDTO.builder().success(false).build();
        }
    }

    public MemberDTO checkMemberIfExists(String identifierValue, String email, String profile) {
        try {
            Response response;
            String url = String.format("%s%s/%s/%s",
                    APICall.CHECK_MEMBER_EXISTS,
                    identifierValue,
                    email,
                    profile);

            log.error(url);

            response = target.path(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();


            return response.readEntity(MemberDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return MemberDTO.builder().success(false).build();
        }
    }

    public MemberDTO checkMemberIfExists1(String identifierValue, String email, String profile) {
        try {
            Response response;
            String url = String.format("%s%s/%s/%s",
                    APICall.CHECK_MEMBER_EXISTS_1,
                    identifierValue,
                    email,
                    profile);

            log.error(url);

            response = target.path(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(MemberDTO.class);
        } catch (Exception e) {
            return MemberDTO.builder().success(false).build();
        }
    }

    public JSONObject getSchemeById(long id) {
        try {
            Response response = target.path(APICall.SCHEME_GET_SCHEME_BY_ID)
                    .path(String.valueOf(id))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(response.readEntity(String.class));
            if (jsonArray != null) {
                return (JSONObject) jsonArray.get(0);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public FmListDTO getAllBanks() {
        try {
            Response response = target.path(APICall.GET_ALL_BANKS)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }

    public FmListDTO getBankBranches(long id) {
        try {
            Response response = target.path(APICall.GET_BANK_BRANCHES_PER_BANK)
                    .path(String.valueOf(id))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }

    public FmListBooleanDto getPensionerCOE(String pensionerNo) {
        try {
            Response response = target.path(APICall.GET_PENSIONER_COE)
                    .path(pensionerNo)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(FmListBooleanDto.class);
        } catch (Exception e) {
            return FmListBooleanDto.builder().success(false).build();
        }
    }

    public FmListDTO getSchemeByName(String schemeName) {
        try {
            Response response = target.path(APICall.SCHEME_GET_SCHEME_BY_NAME)
                    .path(schemeName)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return null;
        }
    }

    public FmListDTO district() {
        try {
            Response response = target.path(APICall.GET_ALL_DISTRICTS)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return null;
        }
    }

    public FmListDTO traditionalAuthority(long districtId) {
        try {
            Response response = target.path(APICall.GET_TRADITIONAL_AUTHOROTIES)
                    .path(String.valueOf(districtId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return null;
        }
    }

    public FmListDTO village(long traditionalAuthorityId) {
        try {
            Response response = target.path(APICall.GET_VILLAGES)
                    .path(String.valueOf(traditionalAuthorityId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return null;
        }
    }

    public MessageModelDTO sendSmsEtl(SMSVM smsvm) {
        Response response = target.path(APICall.SENDSMS)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .headers(myHeaders)
                .post(Entity.entity(smsvm, MediaType.APPLICATION_JSON));
        try {
            String s=response.readEntity(String.class);
            log.error(s);
            return MessageModelDTO.from(s);
        } catch (Exception e) {
            return null;
        }
    }

    public TilesDTO getSponsorDashboardMenuStatistics(long sponsorId) {
        try {
            Response response = target.path(APICall.GET_SPONSOR_DASHBOARD_MENU_STATISTICS)
                    .path(String.valueOf(sponsorId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();


            return response.readEntity(TilesDTO.class);
        } catch (Exception e) {
            return TilesDTO.builder().success(false).build();
        }
    }

    public TilesDTO getSponsorPotentialMembers(long schemeId, long sponsorId) {

        try {
            Response response = target.path(APICall.GET_SPONSOR_POTENTIAL_MEMBERS)
                    .path(String.valueOf(schemeId))
                    .path(String.valueOf(sponsorId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();


            String s = response.readEntity(String.class);
            s = s.replaceAll("},\n]", "}]");
            Gson g = new Gson();
            TilesDTO tilesDTO = g.fromJson(s, TilesDTO.class);
            tilesDTO.setSuccess(true);
            return tilesDTO;
        } catch (Exception e) {
            return TilesDTO.builder().success(false).build();
        }
    }


    public ResultsDTO getSponsorUssdBenefitPaymentRequest(long schemeId, long sponsorId) {

        try {
            Response response = target.path(APICall.GET_SPONSOR_USSD_BENEFIT_PAYMENT_REQUEST)
                    .path(String.valueOf(schemeId))
                    .path(String.valueOf(sponsorId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();


            String s = response.readEntity(String.class);
            s = s.replaceAll("},\n]", "}]");
            Gson g = new Gson();
            return g.fromJson(s, ResultsDTO.class);
        } catch (Exception e) {
            return ResultsDTO.builder().build();
        }
    }

    public TilesDTO getSponsorBenefitsFm(String name, long sponsorId) {
        try {
            Response response = target.path(APICall.GET_EMPLOYER_AND_MEMBER_BENEFITS_FM)
                    .path(name)
                    .path(String.valueOf(sponsorId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(TilesDTO.class);
        } catch (Exception e) {
            return TilesDTO.builder().success(false).build();
        }
    }

    public BulkDTO printUnitizedStatement() {
        try {
            Response response = target.path(APICall.PRINT_UNITIZED_STATEMENT)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(BulkDTO.class);
        } catch (Exception e) {
            return BulkDTO.builder().success(false).build();
        }
    }

    public ResultsDTO getSponsorTPFA(long schemeId, long sponsorId, String receiptStatus) {
        try {
            Response response = target.path(APICall.GET_SPONSOR_TPFA)
                    .path(String.valueOf(schemeId))
                    .path(String.valueOf(sponsorId))
                    .path(String.valueOf(receiptStatus))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();


            String s = response.readEntity(String.class);
            s = s.replaceAll("},]", "}]");
            Gson g = new Gson();
            return g.fromJson(s, ResultsDTO.class);
        } catch (Exception e) {
            return ResultsDTO.builder().build();
        }
    }


    public org.json.JSONObject processListLinesRecursion(List<Vector<String>> lines, long billId, long sponsorId, long schemeId) {
        try {
            String url = APICall.CHECK_BILL_EXCEPTIONS + billId + "/" + schemeId + "/" + sponsorId;
            log.error("processListLinesRecursion: " + url);

            Response response = target.path(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.text(StringUtil.toJsonString(lines)));

            String s = response.readEntity(String.class);
            log.error(s);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FmListDTO sendSMS(OutgoingSMSVM outgoingSMSVM) {
        try {
            Response response = target.path(APICall.SAVESMS)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.entity(outgoingSMSVM, MediaType.APPLICATION_JSON));
            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }
}