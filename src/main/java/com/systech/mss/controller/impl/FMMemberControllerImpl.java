package com.systech.mss.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.systech.mss.callback.ProcessCallBack;
import com.systech.mss.config.Fields;
import com.systech.mss.controller.FMMemberController;
import com.systech.mss.controller.vm.*;
import com.systech.mss.domain.*;
import com.systech.mss.domain.common.Clients;
import com.systech.mss.fileupload.FileModel;
import com.systech.mss.mobilemoney.Mpesa;
import com.systech.mss.mobilemoney.MpesaCallBack;
import com.systech.mss.repository.*;
import com.systech.mss.service.*;
import com.systech.mss.service.dto.*;
import com.systech.mss.seurity.DateUtils;
import com.systech.mss.util.StringUtil;
import com.systech.mss.vm.ObjectComparisonVM;
import com.systech.mss.vm.OutgoingSMSVM;
import com.systech.mss.vm.UploadMemberDocumentVM;
import com.systech.mss.vm.benefitrequest.AddBeneficiaryVM;
import com.systech.mss.vm.benefitrequest.MakeContributionStkVM;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@SuppressWarnings("ALL")
public class FMMemberControllerImpl extends BaseController implements FMMemberController {

    @Inject
    StageContributionRepository stageContributionRepository;

    @Inject
    MobileMoneyRepository mobileMoneyRepository;

    @Inject
    FMCREClient fmcreClient;

    @Inject
    EmailTemplatesRepository emailTemplatesRepository;

    @Inject
    ProfileService profileService;

    @Inject
    UserService userService;

    @Inject
    StageMemberDetailsRepository stageMemberDetailsRepository;

    @Inject
    StagedBeneficiariesRepository stagedBeneficiariesRepository;

    @Inject
    MailService mailService;

    @Inject
    ProfileRepository profileRepository;

    @Inject
    private NotificationService notificationService;

    @Override
    public Response getMemberDetailsSingle(long mssUserId, long memberId) throws IOException {
        logActivityTrail(mssUserId, "Requested member details");
        MemberDetailsVM memberDetailsVM = fmMemberClient.getMemberDetailsSingle(memberId);
        if (memberDetailsVM != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(
                            memberDetailsVM
                    ).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getMemberDetailsBySchemeAndEmail(long mssUserId, long schemeId, String email) {
        FmListDTO fmListDTO = fmMemberClient.getMemberDetailsBySchemeAndEmail(schemeId, email);
        if (fmListDTO != null && fmListDTO.isSuccess()) {
            if (fmListDTO.getRows() != null)
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows().get(0)).build())
                        .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response searchMemberDetails(SearchMemberDetailsVm searchMemberDetailsVm) {
        Config config = configRepository.getActiveConfig();
        Profile profile = profileRepository.findByName("MEMBER");
        log.info(profile.toString());
        if (profile != null) {
            org.json.JSONObject jsonObject = null;
            LoginIdentifier ordinal = profile.getLoginIdentifier();
            FmListDTO fmListDTO = null;
            switch (ordinal) {
                case PHONE:
                    if (config.getClient().equals(Clients.ETL)) {
                        jsonObject = fmMemberClient.getMemberDetailsBySponsorAndPhone(
                                searchMemberDetailsVm.getSponsorId(),
                                searchMemberDetailsVm.getPhone()
                        );
                    } else {
                        jsonObject = fmMemberClient.getMemberDetailsBySchemeAndPhone(
                                searchMemberDetailsVm.getSchemeId(),
                                searchMemberDetailsVm.getPhone()
                        );
                    }
                    if (jsonObject != null) {
                        try {
                            fmListDTO = new ObjectMapper().readValue(jsonObject.toString(), FmListDTO.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case EMAIL:
                    jsonObject = fmMemberClient.getMemberDetailsByScheme(
                            searchMemberDetailsVm.getSchemeId(),
                            searchMemberDetailsVm.getLogin()
                    );
                    if (jsonObject != null) {
                        try {
                            fmListDTO = new ObjectMapper().readValue(jsonObject.toString(), FmListDTO.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    fmListDTO = fmMemberClient.getMemberDetails(searchMemberDetailsVm.getMemberId());

            }
            if (fmListDTO != null) {
                return SuccessMsg("Done", fmListDTO.getRows().get(0));
            }
            return ErrorMsg("Member details not found");
        }
        return ErrorMsg("Profile not found");
    }

    @Override
    public Response getMemberDetailsAll(long mssUserId, long memberId) {
        logActivityTrail(mssUserId, "Requested member details");

        FmListDTO fmListDTO = fmMemberClient.getMemberDetails(memberId);
        if (fmListDTO != null && fmListDTO.isSuccess()) {
            if (fmListDTO.getRows() != null)
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                        .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    /**
     * @param mssUserId       long
     * @param identifierValue EMAIL/PHONE defaults to EMAIL
     * @param email           value of email or phone
     * @param profile         MEMBER
     * @return
     */
    @Override
    public Response getMemberSchemes(long mssUserId, String identifierValue, String emailPhone, String profile) {
        FmListDTO fmListDTO = fmMemberClient.getMemberSchemes(identifierValue, emailPhone, profile);

        if (fmListDTO != null && fmListDTO.isSuccess()) {
            if (!fmListDTO.getRows().isEmpty())
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                        .build();
        }
        //GET BY SCHEME AND PHONE
        fmListDTO = fmMemberClient.getMemberSchemes("PHONE", emailPhone, profile);
        if (fmListDTO != null && fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getMemberBankDetails(long mssUserId, long memberId) {
        Object o = fmMemberClient.getMemberBankDetails(memberId);
        if (o != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(o).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getSummary(long mssUserId, long memberId) {
        logActivityTrail(mssUserId, "Requested Contribution Totals");
        FmListDTO fmListDTO = fmMemberClient.getSummary(memberId);
        if (fmListDTO != null && fmListDTO.isSuccess()) {
            if (fmListDTO.getRows() != null)
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows().get(0)).build())
                        .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response requestStatement(long mssUserId, long memberId, long schemeId, String fromDate, String toDate) {
        logActivityTrail(mssUserId, "Requested statement");
        DateFormat format = new SimpleDateFormat("MMM-dd-yyyy", Locale.ENGLISH);
        Date dateFrom, dateTo;
        try {
            dateFrom = format.parse(fromDate);
            dateTo = format.parse(toDate);
        } catch (java.text.ParseException pe) {
            dateFrom = Calendar.getInstance().getTime();
            dateTo = Calendar.getInstance().getTime();
        }
        try {
            org.json.JSONObject object = fmMemberClient.getAccountingPeriod(format.format(dateFrom), schemeId);
            org.json.JSONObject object1 = fmMemberClient.getAccountingPeriod(format.format(dateTo), schemeId);

            if (object != null) {
                if (object.getBoolean(Fields.SUCCESS)) {
                    if (object.has("accountingPeriodId")) {
                        long apIdFRom = object.getLong("accountingPeriodId");
                        long apIdTo = object1.getLong("accountingPeriodId");
                        if (apIdFRom == apIdTo)
                            apIdFRom = 0l;
                        org.json.JSONObject messageModelDTO = fmMemberClient.requestStatement(memberId, apIdTo, schemeId);
                        if (messageModelDTO != null &&
                                messageModelDTO.has(Fields.SUCCESS)) {
                            log.error(messageModelDTO.toString());
                            return Response.status(Response.Status.OK)
                                    .entity(SuccessVM.builder().success(true).msg("Your statement has been sent to email").build())
                                    .build();
                        }
                        return ErrorMsg("Please try again after sometime");
                    }
                    return ErrorMsg("No accounting period");
                }
            }
            return ErrorMsg("Please try again after sometime");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response sendMemberStatement(long mssUserId, long memberId, long schemeId, String date_string) {
        logActivityTrail(mssUserId, "Requested statement");

        try {
            org.json.JSONObject messageModelDTO = fmMemberClient.sendMemberStatement(memberId, date_string);
//            log.error(messageModelDTO.toString());
            if (messageModelDTO != null &&
                    messageModelDTO.has(Fields.SUCCESS)) {
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).msg(messageModelDTO.getString("message")).build())
                        .build();
            }
            return ErrorMsg("Please try again after sometime");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorMsg("Mss Api call failed");
    }

    public Response requestStatement_(long mssUserId, long memberId, long schemeId, String date_string) {
        logActivityTrail(mssUserId, "Requested statement");
        DateFormat format = new SimpleDateFormat("MMM-dd-yyyy", Locale.ENGLISH);
        Date date;
        try {
            date = format.parse(date_string);
        } catch (java.text.ParseException pe) {
            date = Calendar.getInstance().getTime();
        }

        try {
            org.json.JSONObject object = fmMemberClient.getAccountingPeriod(format.format(date), schemeId);
            if (object != null) {
                if (object.getBoolean(Fields.SUCCESS)) {
                    if (object.has("accountingPeriodId")) {
                        long apId = object.getLong("accountingPeriodId");
                        org.json.JSONObject messageModelDTO = fmMemberClient.requestStatement(memberId, apId, schemeId);
                        if (messageModelDTO != null &&
                                messageModelDTO.has(Fields.SUCCESS)) {
                            log.error(messageModelDTO.toString());
                            return Response.status(Response.Status.OK)
                                    .entity(SuccessVM.builder().success(true).msg("Your statement has been sent to email").build())
                                    .build();
                        }
                        return ErrorMsg("Please try again after sometime");
                    }
                    return ErrorMsg("No accounting period");
                }
            }
        } catch (Exception e) {
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getClosingBalancesSummary(long mssUserId, long memberId, long schemeId) {
        logActivityTrail(mssUserId, "Requested recent closing balances");
        try {
            MemberBalanceDTO fmListDTO = fmMemberClient.getBalances(memberId);
            if (fmListDTO.isSuccess()) {
                List<MemberBalanceVM> rows = fmListDTO.getRows();
                if (rows != null) {
                    List<Object> responseObjects = new ArrayList<>();
                    Collections.reverse(rows);  // to get the recent object

                    //ADD CURRENT YEAR IF NOT EXISTS
                    try {
                        MemberBalanceVM jsonObject = getBalanceFromContributionTotals(
                                memberId, schemeId, (rows.size() > 0) ? rows.get(0) : null
                        );
                        if (jsonObject != null)
                            rows.add(0, jsonObject);
                    } catch (Exception e) {
                    }

                    rows = rows.size() > 10 ? rows.subList(0, 9) : rows;
                    for (MemberBalanceVM memberBalanceVM :
                            rows) {
                        Map<String, Object> map = new HashMap<>();

                        String yr = memberBalanceVM.getAccntprd().trim();
                        yr = yr.substring(yr.length() - 4, yr.length());
                        map.put("year", yr);
                        map.put("accntprd", memberBalanceVM.getAccntprd());
                        map.put("total", (StringUtil.toDouble(memberBalanceVM.getTotal()) / 1000));
                        responseObjects.add(map);

                    }

                    //REVERSE COLLECTION IN ASC ORDER
                    Collections.reverse(responseObjects);
                    return Response.status(Response.Status.OK)
                            .entity(SuccessVM.builder().success(true).data(responseObjects).build())
                            .build();
                }
            }
            return ErrorMsg("No closing balances registered");
        } catch (Exception e) {
        }
        return ErrorMsg("Mss Api call failed");
    }

    public Response getClosingBalancesSummary_1(long mssUserId, long memberId, long schemeId) {
        logActivityTrail(mssUserId, "Requested recent closing balances");
        try {
            MemberBalanceDTO fmListDTO = fmMemberClient.getBalances(memberId);
            if (fmListDTO.isSuccess()) {
                List<MemberBalanceVM> rows = fmListDTO.getRows();
                if (rows != null) {
                    List<Object> responseObjects = new ArrayList<>();
                    Collections.reverse(rows);  // to get the recent object

                    //ADD CURRENT YEAR IF NOT EXISTS
                    try {
//                        Map<String, Object> jsonObject = getBalanceFromContributionTotals(
//                                memberId, (rows.size() > 0) ? rows.get(0) : null
//                        );
                        MemberBalanceVM jsonObject = getBalanceFromContributionTotals(
                                memberId, schemeId, (rows.size() > 0) ? rows.get(0) : null
                        );
                        if (jsonObject != null)
                            rows.add(0, jsonObject);
                    } catch (Exception e) {
                    }

                    rows = rows.size() > 10 ? rows.subList(0, 9) : rows;
                    for (Object o :
                            rows) {
                        Map<String, Object> map = new HashMap<>();
                        JSONParser jsonParser = new JSONParser();
                        JSONObject jsonObject = (JSONObject) jsonParser.parse(
                                new ObjectMapper().writeValueAsString(o)
                        );
                        if (jsonObject != null) {
                            if (jsonObject.containsKey("accntprd") &&
                                    jsonObject.containsKey("total")) {
                                String yr =
                                        String.valueOf(jsonObject.get("accntprd")).trim();
                                yr = yr.substring(yr.length() - 4, yr.length());
                                map.put("year", yr);
                                map.put("accntprd", jsonObject.get("accntprd"));
                                map.put("total",
                                        Double.parseDouble(String.valueOf(jsonObject.get("total"))) / 1000
                                );
                                responseObjects.add(map);
                            }
                        }
                    }


                    //REVERSE COLLECTION IN ASC ORDER
                    Collections.reverse(responseObjects);
                    return Response.status(Response.Status.OK)
                            .entity(SuccessVM.builder().success(true).data(responseObjects).build())
                            .build();
                }
            }
            return ErrorMsg("No closing balances registered");
        } catch (Exception e) {
        }
        return ErrorMsg("Mss Api call failed");
    }

    public Response getClosingBalances(long mssUserId, long memberId) {
        logActivityTrail(mssUserId, "Requested closing balances");
        FmListDTO fmListDTO = fmMemberClient.getClosingBalances(memberId);
        if (fmListDTO != null && fmListDTO.isSuccess()) {
            if (fmListDTO.getRows() != null)
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                        .build();
        }
        return ErrorMsg(fmListDTO.getMessage());
    }

    @Override
    public Response getRecentContributions(long mssUserId, long memberId, long schemeId) {

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-dd-yyyy");

        LocalDate now = LocalDate.now(),
                bFor = LocalDate.of(now.getYear() - 10, now.getMonth(), now.getDayOfMonth());

        String fromDate = bFor.getMonth().name() + "-" + bFor.getDayOfMonth() + "-" + bFor.getYear();
//        fromDate = simpleDateFormat.format(new Date(fromDate));

        String toDate = now.getMonth().name() + "-" + now.getDayOfMonth() + "-" + now.getYear();
//        toDate = simpleDateFormat.format(new Date(toDate));

//        log.error(fromDate + "-" + toDate);

        List<Object> objects = filterContributions_(memberId, fromDate, toDate, 10);
        if (objects != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(objects).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getPersonalInfo(long mssUserId, long memberId) throws IOException {
        return getMemberDetailsSingle(mssUserId, memberId);
    }

    @Override
    public Response getBeneficiaries(long mssUserId, long memberId) throws IOException {
        logActivityTrail(mssUserId, "View Member beneficiaries");
        FmListDTO fmListDTO = getBeneficiaries_(memberId);
        if (fmListDTO != null && fmListDTO.isSuccess()) {
            if (fmListDTO.getRows() != null) {

                ObjectMapper objectMapper = new ObjectMapper();
                List<Object> beneficiaries = fmListDTO.getRows();

                List<MemberBeneficiariesVM> beneficiariesList = new ArrayList<>();
                for (Object object : beneficiaries) {
                    String jsonString = objectMapper.writeValueAsString(object);
                    MemberBeneficiariesVM beneficiariesVm = objectMapper.readValue(jsonString, MemberBeneficiariesVM.class);
                    if (beneficiariesVm.getDateOfAppointment().equals("") || beneficiariesVm.getDateOfAppointment().equals(null)) {
                        beneficiariesVm.setDateOfAppointment("NOT YET APPROVED");
                    }
                    Config config = configRepository.getActiveConfig();
                    if (config.getClient().equals(Clients.ETL)) {
                        beneficiariesVm.setMemberId((int) memberId);
                    }
//                    beneficiariesVm.setDob(beneficiariesVm.getDobFormatted());
                    beneficiariesList.add(beneficiariesVm);

                }
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(beneficiariesList).build())
                        .build();
            }
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getBeneficiariesChart(long mssUserId, long memberId) {
        try {
            logActivityTrail(mssUserId, "View Member beneficiaries");
            FmListDTO fmListDTO = getBeneficiaries_(memberId);
            if (fmListDTO != null && fmListDTO.isSuccess()) {
                if (fmListDTO.getRows() != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<Object> beneficiaries = fmListDTO.getRows();

                    List<MemberBeneficiariesVM> beneficiariesList = new ArrayList<>();
                    long entitlements = 0L;
                    for (Object object : beneficiaries) {
                        String jsonString = objectMapper.writeValueAsString(object);
                        MemberBeneficiariesVM beneficiariesVm = objectMapper.readValue(jsonString, MemberBeneficiariesVM.class);
                        entitlements += beneficiariesVm.getLumpsumEntitlement();
                        beneficiariesList.add(beneficiariesVm);
                    }
                    if (entitlements < 100) {
                        MemberBeneficiariesVM unallocatedBeneficiaries = new MemberBeneficiariesVM();
                        unallocatedBeneficiaries.setFirstname("UNALLOCATED");
                        unallocatedBeneficiaries.setLumpsumEntitlement((int) (100 - entitlements));
                        unallocatedBeneficiaries.setMonthlyEntitlement((int) (100 - entitlements));
                        unallocatedBeneficiaries.setName("UNALLOCATED");
                        beneficiariesList.add(unallocatedBeneficiaries);
                    }
                    return Response.status(Response.Status.OK)
                            .entity(SuccessVM.builder().success(true).data(beneficiariesList).build())
                            .build();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ErrorMsg("Mss Api call failed");
    }

    private FmListDTO getBeneficiaries_(long memberId) {
        return fmMemberClient.getBeneficiaries(memberId);
    }

    @Override
    public Response checkIfCanAddBeneficiaries(long mssUserId, long memberId) {
        try {
            logActivityTrail(mssUserId, "Check If Can Beneficiary");
            FmListDTO fmListDTO = getBeneficiaries_(memberId);
            if (fmListDTO != null && fmListDTO.isSuccess()) {
                if (fmListDTO.getRows() != null) {
                    Map<String, Object> map = new HashMap<>();
                    int total = 0;
                    for (Object o :
                            fmListDTO.getRows()) {
                        JSONParser parser = new JSONParser();
                        JSONObject jsonObject = (JSONObject) parser.parse(
                                new ObjectMapper().writeValueAsString(o)
                        );
                        try {
                            if (jsonObject != null) {
                                if (jsonObject.containsKey("lumpsumEntitlement")) {
                                    total += Integer.parseInt(String.valueOf(jsonObject.get("lumpsumEntitlement")));
                                }
                            }
                        } catch (Exception e) {
                        }
                    }

                    map.put("canAdd", total >= 100 ? false : true);
                    map.put("entitlement", total >= 100 ? 0 : 100 - total);
                    map.put("total", total);
                    return Response.status(Response.Status.OK)
                            .entity(SuccessVM.builder().success(true).data(map).build())
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage());
        }
        return ErrorMsg("Please try again");
    }

    @Override
    public Response addBeneficiary(long mssUserId, @Valid AddBeneficiaryVM addBeneficiaryVM) {

        Config config = configRepository.getActiveConfig();
        if (config.getClient().equals(Clients.ETL)) {
            MessageModelDTO messageModelDTO = addBeneficiaryToXe(mssUserId, config, addBeneficiaryVM);
            if (messageModelDTO.isSuccess()) {
                return Response.status(Response.Status.OK)
                        //DO NOT REMOVE -1 , USED TO CHECK IF TO UPDATE DOCUMENT OR NOT
                        .entity(SuccessVM.builder().success(true).msg(messageModelDTO.getMessage()).data(-1).build())
                        .build();
            }
            return ErrorMsg("Failed, please try again");
        } else {
            logActivityTrail(mssUserId, "Add/edit Beneficiary");
            return stageBeneficiaryDetails(mssUserId, addBeneficiaryVM);
        }
    }

    @Override
    public Response approveBeneficiary(long mssUserId, long id, String action) {
        StagedBeneficiaries stagedBeneficiaries = stagedBeneficiariesRepository.find(id);
        Config config = configRepository.getActiveConfig();
        MailConfig mailConfig = mailConfigRepository.getActiveMailConfigs();
        switch (action) {
            case "DELETE":
                if (stagedBeneficiaries != null) {
                    stagedBeneficiariesRepository.remove(stagedBeneficiaries);
                    User user = userRepository.find(stagedBeneficiaries.getUserId());
                    if (user != null)
                        if (user.getProfile().getName().equalsIgnoreCase("MEMBER"))
                            notificationService.sendNotification(user, EmailTemplatesEnum.MEMBER_BENEFICIARY_DECLINE);

                    //  TODO SMS
                    fundMasterClient.sendSMS(new OutgoingSMSVM(user.getCellPhone(), "Dear" + user.getFirstName() + "your beneficiary request is declined", user.getProfile().getName(), true));

//                    fundMasterClient.sendSMS(new OutgoingSMSVM(user.getCellPhone(),emailTemplatesRepository.findByEmailTemplatesEnum(EmailTemplatesEnum.MEMBER_BENEFICIARY_DECLINE).getTemplate(), user.getProfile().getName(), true) );

                }
                return SuccessMsg("Done", null);
            default:
                if (stagedBeneficiaries != null) {
                    MessageModelDTO messageModelDTO = addBeneficiaryToXe(mssUserId, config, stagedBeneficiaries.getDetails());
                    if (messageModelDTO.isSuccess()) {
                        stagedBeneficiariesRepository.remove(stagedBeneficiaries);

                        User user = userRepository.find(stagedBeneficiaries.getUserId());
                        if (user != null)
                            if (user.getProfile().getName().equalsIgnoreCase("MEMBER"))
                                notificationService.sendNotification(user, EmailTemplatesEnum.MEMBER_BENEFICIARY_APPROVAL);
                        return Response.status(Response.Status.OK)
                                .entity(SuccessVM.builder().success(true).msg(messageModelDTO.getMessage()).build())
                                .build();
                    }
                }
                return ErrorMsg("Failed, please try again");
        }
    }

    private Response stageBeneficiaryDetails(long mssUserId, AddBeneficiaryVM addBeneficiaryVM) {

        long memberId = addBeneficiaryVM.getBenMemberId();
        MemberDetailsVM memberDetailsVM = fmMemberClient.getMemberDetailsSingle(memberId);
        if (memberDetailsVM == null)
            return ErrorMsg("Failed to get member details");

        addBeneficiaryVM.setSchemeId(memberDetailsVM.getSchemeId());
        addBeneficiaryVM.setMemberEmail(memberDetailsVM.getEmail());
        addBeneficiaryVM.setMemberName(memberDetailsVM.getName());

        StagedBeneficiaries stagedBeneficiaries = StagedBeneficiaries.from(mssUserId, addBeneficiaryVM);
        stagedBeneficiaries = stagedBeneficiariesRepository.create(stagedBeneficiaries);
        if (stagedBeneficiaries != null) {
            User user = userRepository.find(stagedBeneficiaries.getUserId());
            if (user.getProfile().getName().equalsIgnoreCase("MEMBER")) {
                long schemeId = user.getUserDetails().getSchemeId();
                userService.sendPoEMail(schemeId, EmailTemplatesEnum.PO_MEMBER_BENEFICIARY_APPROVAL_REQUEST, user.getUserDetails().getName());
            }
            //RETURN RECORD ID TO UPDATE DOCUMENTS
            return SuccessMsg("Details received. Entitlements will reflect once the employer has approved", stagedBeneficiaries.getId());
        }
        return ErrorMsg("Failed, please try again");
    }

    public MessageModelDTO addBeneficiaryToXe(long mssUserId, Config config, @Valid AddBeneficiaryVM addBeneficiaryVM) {
        logActivityTrail(mssUserId, "Add/edit Beneficiary");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("beneficiaryId", addBeneficiaryVM.getBeneficiaryId());
        jsonObject.put("benMemberId", addBeneficiaryVM.getBenMemberId());
        jsonObject.put("benRelationshipType", (addBeneficiaryVM.getBenRelationshipType()).toUpperCase());
        jsonObject.put("benRelationShipCategory", (addBeneficiaryVM.getBenRelationShipCategory()).toUpperCase());
        jsonObject.put("benFirstname", addBeneficiaryVM.getBenFirstname());
        jsonObject.put("benSurname", addBeneficiaryVM.getBenSurname());
        jsonObject.put("memberEmail", addBeneficiaryVM.getMemberEmail());
        jsonObject.put("benDob", addBeneficiaryVM.getBenDob());
        jsonObject.put("benOthernames", addBeneficiaryVM.getBenOthernames());
        jsonObject.put("benAttachmentname", addBeneficiaryVM.getBenAttachmentname());
        jsonObject.put("benAttachment", addBeneficiaryVM.getBenAttachment());
        jsonObject.put("benGender", (addBeneficiaryVM.getBenGender()).toUpperCase());
        jsonObject.put("benMonthlyEntitlement", addBeneficiaryVM.getBenLumpsumEntitlement());
        jsonObject.put("benLumpsumEntitlement", addBeneficiaryVM.getBenLumpsumEntitlement());
        jsonObject.put("benIdNo", addBeneficiaryVM.getBenIdNo());
        jsonObject.put("benAddressPostalAddress", addBeneficiaryVM.getBenAddressPostalAddress());
        jsonObject.put("benPhysicalCondition", addBeneficiaryVM.getBenPhysicalCondition());
        jsonObject.put("benDateOfAppointment", addBeneficiaryVM.getBenDateOfAppointment());
        jsonObject.put("benBirthCert", addBeneficiaryVM.getBenBirthCert());
        jsonObject.put("benGuardianSn", addBeneficiaryVM.getBenGuardianSn());
        jsonObject.put("benMaritalStatus", addBeneficiaryVM.getBenMaritalStatus());
        jsonObject.put("benGuardianFn", addBeneficiaryVM.getBenGuardianFn());
        jsonObject.put("benGuardianOn", addBeneficiaryVM.getBenGuardianOn());
        jsonObject.put("benGuardianAddr", addBeneficiaryVM.getBenGuardianAddr());
        jsonObject.put("benGuardianGender", addBeneficiaryVM.getBenGuardianGender());
        jsonObject.put("legibilityStatus", addBeneficiaryVM.getLegibilityStatus());
        jsonObject.put("benGuardianRelation", addBeneficiaryVM.getBenGuardianRelation());

        if (config.getClient().equals(Clients.ETL)) {
            jsonObject.put("benBankBranchId", addBeneficiaryVM.getBenBankBranchId());
            jsonObject.put("benAccountName", addBeneficiaryVM.getBenAccountName());
            jsonObject.put("benAccountNo", addBeneficiaryVM.getBenAccountNo());
        } else {
            jsonObject.put("benMaidenName", addBeneficiaryVM.getBenMaidenName());
            jsonObject.put("benNationality", addBeneficiaryVM.getBenNationality());
            jsonObject.put("benCellPhone", addBeneficiaryVM.getBenCellPhone());
            jsonObject.put("placeOfBirthDistrictId", (addBeneficiaryVM.getPlaceOfBirthDistrictId()));
            jsonObject.put("placeOfBirthTAId", (addBeneficiaryVM.getPlaceOfBirthTAId()));
            jsonObject.put("placeOfBirthVillageId", (addBeneficiaryVM.getPlaceOfBirthVillageId()));
            jsonObject.put("permanentDistrictId", (addBeneficiaryVM.getPermanentDistrictId()));
            jsonObject.put("permanentTAId", (addBeneficiaryVM.getPermanentTAId()));
            jsonObject.put("permanentVillageId", (addBeneficiaryVM.getPermanentVillageId()));
            jsonObject.put("benBankBranchId", (addBeneficiaryVM.getBenBankBranchId()));
            jsonObject.put("benAccountName", addBeneficiaryVM.getBenAccountName());
            jsonObject.put("benAccountNo", addBeneficiaryVM.getBenAccountNo());
        }

        return fmMemberClient.addBeneficiary(jsonObject);
    }

    @Override
    public Response getContributions(long mssUserId, long memberId, long schemeId) {
        logActivityTrail(mssUserId, "View Contributions In Scheme");
        FmListDTO fmListDTO = fmMemberClient.getContributions(memberId, schemeId);
        if (fmListDTO != null && fmListDTO.isSuccess()) {
            List<Object> objects = fmListDTO.getRows();
            if (objects != null) {
                Collections.reverse(objects);
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(objects).build())
                        .build();
            }
        }
        return ErrorMsg("Error encountered, please try again");
    }

    @Override
    public Response getContributionsSummary(long mssUserId, long memberId, long schemeId) {
        logActivityTrail(mssUserId, "Get Contributions summary");
        LocalDate now = LocalDate.now(),
                bFor = LocalDate.of(now.getYear() - 5, now.getMonth(), now.getDayOfMonth());
        String fromDate = bFor.getDayOfMonth() + "/" + bFor.getMonthValue() + "/" + bFor.getYear();
        String toDate = now.getDayOfMonth() + "/" + now.getMonthValue() + "/" + now.getYear();
        List<Object> objects = filterContributions_(memberId, fromDate, toDate, -1);

        List<Object> objectsRes = new ArrayList<>();
        Map<Object, Object> map = new HashMap<>();
        if (objects != null) {
            for (Object o :
                    objects) {
                JSONParser jsonParser = new JSONParser();
                try {
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(new ObjectMapper().writeValueAsString(o));
                    if (jsonObject != null) {
                        Object year = jsonObject.get("year");
                        Object total = jsonObject.get("total");
                        if (map.containsKey(year)) {
                            double tt = Double.parseDouble(String.valueOf(total));
                            tt += Double.parseDouble(String.valueOf(map.get(year)));
                            map.replace(year, tt);
                        } else {
                            map.put(year, Double.parseDouble(String.valueOf(total)));
                        }
                    }
                } catch (ParseException e) {
                    log.error(e.getMessage());
                } catch (JsonProcessingException e) {
                    log.error(e.getMessage());
                }
            }

            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                Object key = entry.getKey();
                Object val = entry.getValue();
                objectsRes.add(new MemberContributionDTO(
                        String.valueOf(key),
                        Double.parseDouble(String.valueOf(val)) / 1000
                ));
            }
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(objectsRes).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response filterContributions(long mssUserId, long memberId, String fromDate, String toDate) {
        try {
            logActivityTrail(mssUserId,
                    String.format("View Contributions from %s to %s", fromDate, toDate));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-dd-yyyy");
            Date from = //DateUtils.from("dd-MM-yyyy",fromDate);
                    new Date(fromDate.replaceAll("-", "/"));
            Date to = toDate.trim().equalsIgnoreCase("today") ?
                    Calendar.getInstance().getTime() :
//                    DateUtils.from("dd-MM-yyyy",toDate);
                    new Date(toDate.replaceAll("-", "/"));
            String sFRom = simpleDateFormat.format(from);
            String sTo = simpleDateFormat.format(to);
            List<Object> objects = filterContributions_(memberId, sFRom, sTo, -1);
            if (objects != null) {
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(objects).build())
                        .build();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ErrorMsg("Mss Api call failed");
    }

    private List<Object> filterContributions_(long memberId, String fromDate, String toDate, int count) {
        try {
            FmListDTO fmListDTO = fmMemberClient.filterContributions(memberId, fromDate, toDate);
            if (fmListDTO.isSuccess()) {
                List<Object> objects = fmListDTO.getRows();
                if (objects != null) {
                    Collections.reverse(objects);
                    return (count != -1 && objects.size() > count) ? objects.subList(0, count) : objects;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Response makeContribution(long mssUserId, MakeContributionStkVM makeContributionStkVM) {
        if (!getFMConfig().isPresent()) {
            return ErrorMsg("No configurations set");
        }
        Config config = getFMConfig().get();
        MobileMoneyConfig mobileMoneyConfig
                = mobileMoneyRepository.getActiveConfig();
        if (mobileMoneyConfig == null) {
            return ErrorMsg("No configurations set");
        }

        String mpesaPhoneNumber = makeContributionStkVM.getPhone();
        if (mpesaPhoneNumber.startsWith("0"))
            mpesaPhoneNumber = config.getCountryCode() + mpesaPhoneNumber.substring(1);
        if (mpesaPhoneNumber.startsWith("+"))
            mpesaPhoneNumber = mpesaPhoneNumber.substring(1);

        log.error(mpesaPhoneNumber);
        String timeStamp = DateUtils.getTimestamp();
        String password = Mpesa.getPassword(mobileMoneyConfig.getMpesaPaybill(), mobileMoneyConfig.getMpesaPassKey(), timeStamp);
        try {
            Mpesa mpesa = new Mpesa(
                    mobileMoneyConfig.getMpesaAppKey(),
                    mobileMoneyConfig.getMpesaAppSecret(),
                    mobileMoneyConfig.isLive()
            );
            MpesaCallBack mpesaCallBack = new MpesaCallBack() {
                @Override
                public Object start(Object o) {
                    try {
                        org.json.JSONObject jsonObject = new org.json.JSONObject(String.valueOf(o));
                        if (jsonObject.has("errorCode")) {
                            return jsonObject.has("errorMessage");
                        }

                        makeContributionStkVM.setPaybill(mobileMoneyConfig.getMpesaPaybill());
                        makeContributionStkVM.setRequestId(jsonObject.getString("CheckoutRequestID"));
                        makeContributionStkVM.setMerchantRequestID(jsonObject.getString("MerchantRequestID"));
                        makeContributionStkVM.setTimestamp(timeStamp);
                        makeContributionStkVM.setPassword(password);

                        stageContributionRepository.save(makeContributionStkVM);
                        return "success";
                    } catch (Exception e) {
                        return "Error encountered";
                    }
                }
            };
            String res = mpesa.STKPushSimulation(
                    mobileMoneyConfig.getMpesaPaybill(),
                    password,
                    timeStamp,
                    String.valueOf(makeContributionStkVM.getAmount()),
                    mpesaPhoneNumber,
                    mpesaPhoneNumber,
                    mobileMoneyConfig.getMpesaPaybill(),
                    mobileMoneyConfig.getCallbackUrl(),
                    mobileMoneyConfig.getTimeoutUrl(),
                    mobileMoneyConfig.getAccountReference(),
                    "Scheme contributions",
                    mpesaCallBack
            );
            if (res.equals("success")) {
//                doInBackground(new ProcessCallBack() {
//                    @Override
//                    public void start(Object o) {
//                        //send email to po
//                        schemeId = makeContributionStkVM.getSchemeId();
//                        subject = "Member details update";
//                        emailBody = "You have a new member details update pending approval";
//                        userService.sendPoEMail(schemeId, subject, emailBody);
//                    }
//                });
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data("Check your device and complete transaction").build())
                        .build();
            }
            return ErrorMsg(res);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ErrorMsg("Please try again");
    }

    public Response makeContributionViaXi(long mssUserId, MakeContributionStkVM makeContributionStkVM) {
        try {
            logActivityTrail(mssUserId, "Initiated Make Contribution");
//        MessageModelDTO messageModelDTO = fmMemberClient.makeContribution(makeContributionStkVM);
            org.json.JSONObject jsonObject = fmMemberClient.makeContributionViaPortal(
                    makeContributionStkVM.getPhone(),
                    makeContributionStkVM.getPaybill(),
                    makeContributionStkVM.getAmount());

            if (jsonObject != null && jsonObject.getBoolean("success")) {
                String requestId = jsonObject.getString("request_id");
                makeContributionStkVM.setRequestId(requestId);
                stageContributionRepository.save(makeContributionStkVM);
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data("Your contribution has been received, you will be notified when confirmed").build())
                        .build();
            }
            log.error(jsonObject.toString());
        } catch (Exception e) {
        }
        return ErrorMsg("Please try again");
    }

    @Override
    public Response getBalances(long mssUserId, long memberId, long schemeId, int count) {
        logActivityTrail(mssUserId, "Requested balances");
        List<MemberBalanceVM> objects = getMemberBalances(mssUserId, memberId, schemeId, count);
        if (objects != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(objects).build())
                    .build();
        }
        return ErrorMsg("Please try again");
    }

    private List<MemberBalanceVM> getMemberBalances(long mssUserId, long memberId, long schemeId, int count) {
        MemberBalanceDTO fmListDTO = getBalances_(memberId);
        if (fmListDTO.isSuccess()) {
            List<MemberBalanceVM> objects = fmListDTO.getRows();

            if (objects != null) {
                Collections.reverse(objects);

                for (MemberBalanceVM memberBalanceVM :
                        objects) {
                    String accPrd = memberBalanceVM.getAccntprd();
                    String[] prds = accPrd.split("-");
                    memberBalanceVM.setYearEnding(prds[1].trim());
                }

                //ADD CURRENT YEAR IF NOT EXISTS
                try {
                    MemberBalanceVM jsonObject = getBalanceFromContributionTotals(
                            memberId, schemeId, (objects.size() > 0) ? objects.get(0) : null
                    );
                    if (jsonObject != null)
                        objects.add(0, jsonObject);
                } catch (Exception e) {
                }
                return (count != -1 && objects.size() > count) ? objects.subList(0, count) : objects;

            }
        }
        return null;
    }

    /**
     * GET CLOSING BALANCE FOR CURRENT YEAR NOT INCLUDED IN BALANCES
     * CALCULATE SUM OF CONTRIBUTIONS
     *
     * @param memberId
     * @param closingBal For previous year
     * @return
     */
    private MemberBalanceVM getBalanceFromContributionTotals(long memberId, long schemeId, MemberBalanceVM closingBal) {
        try {

//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-dd-yyyy");
            LocalDate now = LocalDate.now(),
                    bFor = LocalDate.of(now.getYear(), Month.JANUARY, 1);

            double totalEE = 0.0;
            double totalER = 0.0;
            double totalOpeningBalances = 0.0;
            double total = 0.0,
                    prevEEIntr = 0.0,
                    prevERIntr = 0.0;

            if (closingBal != null) {
                try {

                    String accPeriod = closingBal.getAccntprd();
                    /**
                     *If already closing balances have been run for current year, dont calculate sum of the contributions
                     */
                    if (accPeriod.contains(String.valueOf(now.getYear()))) {
                        return null;
                    }

                    totalOpeningBalances = StringUtil.toDouble(closingBal.getTotal());
                    prevEEIntr = StringUtil.toDouble(closingBal.getPrevEEIntr());
                    prevERIntr = StringUtil.toDouble(closingBal.getPrevERIntr());
                    totalEE = StringUtil.toDouble(closingBal.getTotalEE());
                    totalER = StringUtil.toDouble(closingBal.getTotalER());

                } catch (Exception e) {
                }
            }

            total = totalOpeningBalances;

            String fromDate = bFor.getMonth().name() + "-" + bFor.getDayOfMonth() + "-" + bFor.getYear();
//            fromDate = simpleDateFormat.format(new Date(fromDate));

            String toDate = now.getMonth().name() + "-" + now.getDayOfMonth() + "-" + now.getYear();
//            toDate = simpleDateFormat.format(new Date(toDate));

            String accPrd;
            fromDate = DateUtils.shortDateOfLocalDate(bFor);
            toDate = DateUtils.shortDateOfLocalDate(LocalDate.of(now.getYear(), Month.DECEMBER, 31));

            accPrd = fromDate + " - " + toDate;
            MemberBalanceVM memberBalanceVM = new MemberBalanceVM();
            JSONObject jsonObject = fmMemberClient.getBalancesAsAtToday(memberId, schemeId);
            if (jsonObject != null && jsonObject.containsKey("success")) {
                log.error("fmMemberClient.getBalancesAsAtToday......" + jsonObject.toString());
                if (Boolean.parseBoolean(String.valueOf(jsonObject.get("success")))) {
                    log.error("Using fmMemberClient.getBalancesAsAtToday......");
                    memberBalanceVM.setId("-1");
                    memberBalanceVM.setAccntprd(accPrd);
                    memberBalanceVM.setTotalOpeningBalances(StringUtil.toString(totalOpeningBalances));
                    memberBalanceVM.setPrevEEIntr(StringUtil.toString(prevEEIntr));
                    memberBalanceVM.setPrevERIntr(StringUtil.toString(prevERIntr));
                    memberBalanceVM.setEeIntr(StringUtil.toString(0));
                    memberBalanceVM.setAvcbal(StringUtil.toString(0));
                    memberBalanceVM.setErIntr(StringUtil.toString(0));
                    memberBalanceVM.setAvcerbal(StringUtil.toString(0));
                    memberBalanceVM.setTotalInterest(StringUtil.toString(jsonObject.get("currentTotalInterest")));
                    memberBalanceVM.setTotalEE(StringUtil.toString(jsonObject.get("currentEeTotalBalances")));
                    memberBalanceVM.setTotalER(StringUtil.toString(jsonObject.get("currentErTotalBalances")));
                    memberBalanceVM.setTotal(StringUtil.toString(jsonObject.get("currentTotalBalances")));
                    memberBalanceVM.setStatus("Registered");
                    memberBalanceVM.setYearEnding(DateUtils.shortDateOfLocalDate(now));
                    memberBalanceVM.setAsAt(DateUtils.shortDateOfLocalDate(now));
                    return memberBalanceVM;
                }
            }

            log.error("Using filter contributions option......");

            List<Object> objects = filterContributions_(memberId, fromDate, toDate, -1);
            if (objects != null) {

                for (Object o :
                        objects) {
                    String s = StringUtil.toJsonString(o);
                    if (s != null) {
                        org.json.JSONObject object = new org.json.JSONObject(s);
                        if (object != null) {
                            totalEE += object.getDouble("ee");
                            totalER += object.getDouble("er");
                            total += object.getDouble("total");
                        }
                    }
                }


                memberBalanceVM.setId("-1");
                memberBalanceVM.setAccntprd(accPrd);
                memberBalanceVM.setTotalOpeningBalances(StringUtil.toString(totalOpeningBalances));
                memberBalanceVM.setPrevEEIntr(StringUtil.toString(prevEEIntr));
                memberBalanceVM.setPrevERIntr(StringUtil.toString(prevERIntr));
                memberBalanceVM.setEeIntr(StringUtil.toString(0));
                memberBalanceVM.setAvcbal(StringUtil.toString(0));
                memberBalanceVM.setErIntr(StringUtil.toString(0));
                memberBalanceVM.setAvcerbal(StringUtil.toString(0));
                memberBalanceVM.setTotalInterest("-");
                memberBalanceVM.setTotalEE(StringUtil.toString(totalEE));
                memberBalanceVM.setTotalER(StringUtil.toString(totalER));
                memberBalanceVM.setTotal(StringUtil.toString(total));
                memberBalanceVM.setStatus("Registered");
                memberBalanceVM.setYearEnding(DateUtils.shortDateOfLocalDate(now));
                memberBalanceVM.setAsAt(DateUtils.shortDateOfLocalDate(now));
                return memberBalanceVM;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ORIGINAL METHOD
     *
     * @param memberId
     * @param closingBal
     * @return
     */
    private Map<String, Object> getBalanceFromContributionTotals_1(long memberId, Object closingBal) {
        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-dd-yyyy");
            LocalDate now = LocalDate.now(),
                    bFor = LocalDate.of(now.getYear(), Month.JANUARY, 1);

            double totalEE = 0.0;
            double totalER = 0.0;
            double totalOpeningBalances = 0.0;
            double total = 0.0,
                    prevEEIntr = 0.0,
                    prevERIntr = 0.0;

            if (closingBal != null) {
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(
                            StringUtil.toJsonString(closingBal)
                    );
                    String accPeriod = jsonObject.getString("accntprd");
                    /**
                     *If already closing balances have been run for current year, dont calculate sum of the contributions
                     */
                    if (accPeriod.contains(String.valueOf(now.getYear()))) {
                        return null;
                    }

                    totalOpeningBalances = jsonObject.getDouble("total");
                    prevEEIntr = jsonObject.getDouble("prevEEIntr");
                    prevERIntr = jsonObject.getDouble("prevERIntr");
                    totalEE = jsonObject.getDouble("totalEE");
                    totalER = jsonObject.getDouble("totalER");

                } catch (Exception e) {
                }
            }

            total = totalOpeningBalances;

            String fromDate = bFor.getDayOfMonth() + "/" + bFor.getMonthValue() + "/" + bFor.getYear();
            fromDate = simpleDateFormat.format(new Date(fromDate));
            String toDate = now.getDayOfMonth() + "/" + now.getMonthValue() + "/" + now.getYear();
            toDate = simpleDateFormat.format(new Date(toDate));
            List<Object> objects = filterContributions_(memberId, fromDate, toDate, -1);
            if (objects != null) {
                String accPrd;

                for (Object o :
                        objects) {
                    String s = StringUtil.toJsonString(o);
                    if (s != null) {
                        org.json.JSONObject object = new org.json.JSONObject(s);
                        if (object != null) {
                            totalEE += object.getDouble("ee");
                            totalER += object.getDouble("er");
                            total += object.getDouble("total");
                        }
                    }
                }

                fromDate = DateUtils.shortDateOfLocalDate(bFor);
                toDate = DateUtils.shortDateOfLocalDate(LocalDate.of(now.getYear(), Month.DECEMBER, 31));

                accPrd = fromDate + " - " + toDate;
                Map<String, Object> jsonObject = new HashMap<>();
                jsonObject.put("id", -1);
                jsonObject.put("accntprd", accPrd);

                jsonObject.put("totalOpeningBalances", totalOpeningBalances);
                jsonObject.put("prevEEIntr", prevEEIntr);
                jsonObject.put("prevERIntr", prevERIntr);
                jsonObject.put("eeIntr", 0);
                jsonObject.put("avcbal", 0);
                jsonObject.put("erIntr", 0);
                jsonObject.put("avcerbal", 0);

                jsonObject.put("avcbal", 0);
                jsonObject.put("avcerbal", 0);
                jsonObject.put("totalInterest", "-");
                jsonObject.put("totalEE", totalEE);
                jsonObject.put("totalER", totalER);
                jsonObject.put("total", total);
                jsonObject.put("status", "Registered");
//                log.error(jsonObject.toString());
                return jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Response getBalancesAsAtToday(long mssUserId, long memberId, long schemeId) {
        logActivityTrail(mssUserId, "Get Balances As Today");
        JSONObject jsonObject = fmMemberClient.getBalancesAsAtToday(memberId, schemeId);

        if (jsonObject != null && jsonObject.containsKey("success")) {
            if (Boolean.parseBoolean(String.valueOf(jsonObject.get("success")))) {
                Map<String, Object> map = new HashMap<>();
                Config config = configRepository.getActiveConfig();
                Clients clients = config.getClient();
                switch (clients) {
                    case ETL:
                        map.put("eeReg", jsonObject.get("eeReg"));
                        map.put("erReg", jsonObject.get("erReg"));
                        map.put("totalBenefits", jsonObject.get("totalBenefits"));
                        break;
                    default:
                        map.put("eeReg", jsonObject.get("currentEeTotalBalances"));
                        map.put("erReg", jsonObject.get("currentErTotalBalances"));
                        map.put("totalBenefits", jsonObject.get("currentTotalBalances"));
                        map.put("currentAVCErTotalBalances", jsonObject.get("currentAVCErTotalBalances"));
                        map.put("currentTotalInterest", jsonObject.get("currentTotalInterest"));
                        map.put("currentAVCEeTotalBalances", jsonObject.get("currentAVCEeTotalBalances"));
                        map.put("currentAVCErTotalBalances", jsonObject.get("currentAVCErTotalBalances"));
                }
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(map).build())
                        .build();
            }
        }

        /**
         * If above fails, calculate balances from contributions
         */
        List<MemberBalanceVM> objects = getMemberBalances(mssUserId, memberId, schemeId, 1);
        if (objects != null) {
            try {
                org.json.JSONObject jsonObject1 = new org.json.JSONObject(StringUtil.toJsonString(objects.get(0)));
                Map<String, Object> map = new HashMap<>();
                map.put("eeReg", jsonObject1.get("totalEE"));
                map.put("erReg", jsonObject1.get("totalER"));
                map.put("totalBenefits", jsonObject1.get("total"));
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(map).build())
                        .build();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ErrorMsg("Failed, please try again");
    }

    private MemberBalanceDTO getBalances_(long memberId) {
        logActivityTrail(memberId, "Requested balances");
        return fmMemberClient.getBalances(memberId);
    }

    @Override
    public Response getClaims(long mssUserId) {
        return null;
    }

    @Override
    public Response initiateClaim(long mssUserId) {
        return null;
    }

    @Override
    public Response projections(long mssUserId) {
        return null;
    }

    @Override
    public Response calculateWhatIfAnalysis(long mssUserId, long schemeId, long memberId, String avcReceiptOption, int ageAtExit, double returnRate, double salaryEscalationRate, long projectedAvc, long inflationRate) {
        logActivityTrail(mssUserId, "Calculate whatif analysis");
        BaseDTO serializable = fmMemberClient.calculateWhatIfAnalysis(schemeId, memberId, avcReceiptOption, ageAtExit, returnRate, salaryEscalationRate, projectedAvc, inflationRate);
        if (serializable == null) {
            if (ageAtExit > 60)
                return ErrorMsg("Sorry, please try again");
            return ErrorMsg("Please try again");
        }
        if (serializable instanceof FmListDTO) {
            if (((FmListDTO) serializable).isSuccess()) {
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(((FmListDTO) serializable).getRows().get(0)).build())
                        .build();
            }
        }
        if (serializable instanceof ErrorMsgDTO) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(false).data(((ErrorMsgDTO) serializable).getMessage()).build())
                    .build();
        }
        return ErrorMsg("Please try again");
    }

    @Override
    public Response requestMemberCertificate(long mssUserId, long memberId) {
        logActivityTrail(mssUserId, "Requested membership certificate");
        org.json.JSONObject jsonObject = fmMemberClient.getMemberCertificate(memberId);
        if (jsonObject != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).build())
                    .build();
        }
        return ErrorMsg("Error getting certficate");
    }


    @Override
    public Response getReasonsForExit(long mssUserId) {
        FmListDTO fmListDTO = fmMemberClient.getReasonsForExit();
        if (fmListDTO != null && fmListDTO.isSuccess()) {
            if (fmListDTO.getRows() != null)
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                        .build();
        }
        return ErrorMsg("Please try again");
    }

    @Override
    public Response getReasonForExitById(long mssUserId, long id) {
        Map<String, Object> map = fmMemberClient.getReasonForExitById(id);
        if (map != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(map).build())
                    .build();
        }
        return ErrorMsg("Please try again");
    }

    @Override
    public Response activityLog(long userId) {
        User user = userRepository.find(userId);
        if (user != null)
            return Response.ok()
                    .entity(
                            SuccessVM.builder()
                                    .success(true)
                                    .data(trailRepository.getByUserId(user))
                    ).build();
        return ErrorMsg("Please try again");
    }

    @Override
    public Response getMemberLoans(long mssUserId, long memberId) {
        String fmListDTO = fmMemberClient.getMemberLoans(memberId);
        if (fmListDTO != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getMissingDocuments(long mssUserId, long memberId) {
        StringListDTO fmListDTO = fmMemberClient.getMissingDocuments(memberId);
        if (fmListDTO.getSuccess().equalsIgnoreCase("true")) {
            if (fmListDTO.getRows() != null)
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                        .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getSubmittedDocuments(long mssUserId, long memberId) {
        logActivityTrail(mssUserId, "Get submitted Documents");
        StringListDTO fmListDTO = getMemberSubmittedDocuments(memberId);
        if (fmListDTO.getSuccess().equalsIgnoreCase("true")) {
            if (fmListDTO.getRows() != null)
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                        .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    private StringListDTO getMemberSubmittedDocuments(long memberId) {
        return fmMemberClient.getSubmittedDocuments(memberId);
    }

    @Override
    public Response checkSubmittedDocuments(long mssUserId, long memberId) {
        StringListDTO fmListDTO = getMemberSubmittedDocuments(memberId);
        if (fmListDTO.getSuccess().equalsIgnoreCase("true")) {
            List<Object> objects = fmListDTO.getRows();
            Map<String, Object> map = new HashMap<>();
            map.put("documents", objects);
            if (objects.isEmpty()) {
                map.put("upload", true);
//                map.put("documents",new String[]{});
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(map).build())
                        .build();
            }
            map.put("upload", false);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(map).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getPayBill(long mssUserId) {
//        Object config = configRepository.getSpecificFieldsOfActiveConfigs();
        MobileMoneyConfig mobileMoneyConfig = mobileMoneyRepository.getActiveConfig();
        if (mobileMoneyConfig != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("mobileMoneyProcedure", mobileMoneyConfig.getMobileMoneyProcedure());
            map.put("businessNo", mobileMoneyConfig.getMpesaPaybill());
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true)
                            .data(map).build())
                    .build();
        }
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response getAllAccountingPeriods(long mssUserId, String schemedId) {
        logActivityTrail(mssUserId, "Get Accounting periods");
        try {
            org.json.JSONObject o = fmMemberClient.getAllAccountingPeriods(schemedId);
            if (o != null && o.getBoolean(Fields.SUCCESS)) {
                org.json.JSONArray jsonArray = o.getJSONArray("rows");
                List<Object> res = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    org.json.JSONObject object = jsonArray.getJSONObject(i);
                    Iterator iterator = object.keys();
                    Map<Object, Object> map = new HashMap<>();
                    while (iterator.hasNext()) {
                        String key = StringUtil.toString(iterator.next());
                        map.put(key, object.get(key));
                    }
                    res.add(map);
                }

                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(res).build())
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorMsg("Error encountered");
    }

    @Override
    public Response getMemberStatement(long mssUserId, long memberId, long apId, long schemeId) {
        logActivityTrail(mssUserId, "Get member statement");
        try {
            org.json.JSONObject o = fmMemberClient.getMemberStatement(memberId, apId, schemeId);
            if (o != null && o.getBoolean(Fields.SUCCESS)) {
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(o.toString()).build())
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorMsg("Error encountered");
    }

    @Override
    public Response memberSubmitRequiredDocument(long mssUserId, UploadMemberDocumentVM uploadMemberDocumentVM) {
        logActivityTrail(mssUserId, "Submit required document");
        Documents documents = documentRepository.find(
                uploadMemberDocumentVM.getDocumentId()
        );
        if (documents == null) {
            return ErrorMsg("Document NOT found");
        }
        MemberSubmittedDocs memberSubmittedDocs = MemberSubmittedDocs.getInstance(documents, uploadMemberDocumentVM);
        MemberSubmittedDocs submittedDocs =
                memberSubmittedDocsRepository.create(memberSubmittedDocs);
        if (submittedDocs != null) {
            String msg = uploadMemberDocumentVM.getDocName() + " submitted";
            return SuccessMsg(msg, msg);
        }
        return ErrorMsg("Failed to submit document");
    }

    @Override
    public Response memberUploadRequiredDocument(long mssUserId, long memberId, String record, MultipartFormDataInput input) {
        UploadMemberDocumentVM documentVM;
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(record);
            documentVM = new UploadMemberDocumentVM();

            documentVM.setMemberId(jsonObject.getLong("memberId"));
            documentVM.setDocName(jsonObject.getString("docName"));
            documentVM.setDocNotes(jsonObject.getString("docNotes"));
            documentVM.setDocNum(jsonObject.getString("docNum"));
            documentVM.setDocTypeId(jsonObject.getString("docTypeId"));
        } catch (JSONException e) {
            return ErrorMsg("Error, please try again");
        }

        logActivityTrail(mssUserId, "Submit required document");
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        if (uploadForm.isEmpty())
            return ErrorMsg("No files selected");

        try {
            List<FileModel> fileModels = upload(input);
            if (fileModels == null) {
                return ErrorMsg("Failed to upload documents");
            }
            User user = userRepository.find(mssUserId);
            if (user != null) {
                List<Documents> documentsList = saveFiles(user, fileModels);
                for (Documents document : documentsList) {
                    MemberSubmittedDocs memberSubmittedDocs = MemberSubmittedDocs.getInstance(document, documentVM);
                    MemberSubmittedDocs submittedDocs =
                            memberSubmittedDocsRepository.create(memberSubmittedDocs);
                    if (submittedDocs != null) {
                        return SuccessMsg(documentVM.getDocName() + " submitted", "Submitted");
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ErrorMsg("Failed to submit document");
    }

    @Override
    public Response getCurrentMonthlyContributionAndBasicSalary(long mssUserId, long memberId, long schemeId) {
        logActivityTrail(mssUserId, "Get Current Monthly Contribution And BasicSalary");
        JSONObject jsonObject = fmMemberClient.getCurrentMonthlyContributionAndBasicSalary(memberId, schemeId);
        if (jsonObject != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true)
                            .data(jsonObject).build())
                    .build();
        }
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response mpesaCallBack(String s) {
        if (s != null) {
            try {
                File file = new File(getUploadFolder() + "/mpesa.txt");
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(s);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).build())
                .build();
    }

    @Override
    public Response pushContributionToXe(long mssUserId, long stageCotributionId) {
        Optional<StageContribution> contribution = stageContributionRepository.findById(stageCotributionId);
        if (contribution.isPresent()) {
            StageContribution stageContribution = contribution.get();
            MpesaCallBack mpesaCallBack = new MpesaCallBack() {
                @Override
                public Object start(Object o) {
                    org.json.JSONObject jsonObject = null;
                    try {
                        jsonObject = new org.json.JSONObject(String.valueOf(o));
                        if (jsonObject.has("ResultCode")) {
                            String ResultCode = jsonObject.getString("ResultCode");
                            if (ResultCode.equals("0")) {
                                return "success";
                            }
                            return jsonObject.getString("ResultDesc");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return "Failed";
                }
            };
            MobileMoneyConfig mobileMoneyConfig
                    = mobileMoneyRepository.getActiveConfig();
            if (mobileMoneyConfig == null) {
                return ErrorMsg("No configurations set");
            }

            String timeStamp = DateUtils.getTimestamp();
            String password = Mpesa.getPassword(mobileMoneyConfig.getMpesaPaybill(), mobileMoneyConfig.getMpesaPassKey(), timeStamp);

            Mpesa mpesa = new Mpesa(
                    mobileMoneyConfig.getMpesaAppKey(),
                    mobileMoneyConfig.getMpesaAppSecret(),
                    mobileMoneyConfig.isLive()
            );

            try {
                String res = mpesa.STKPushTransactionStatus(
                        mobileMoneyConfig.getMpesaPaybill(),
                        stageContribution.getPassword(),
                        stageContribution.getTimestamp(),
                        stageContribution.getRequestId(),
                        mpesaCallBack
                );
                if (res.equalsIgnoreCase("success")) {
                    /**
                     * Push to Xe
                     */
                    org.json.JSONObject jsonObject = fmMemberClient.postContributionMembersAccount(
                            stageContribution.getSchemeId(),
                            stageContribution.getMemberId(),
                            stageContribution.getAmount(),
                            DateUtils.formatDate(stageContribution.getCreatedAt(), "yyyy-MM-dd"));
                    if (jsonObject != null) {
                        stageContribution.setSendToXi(true);
                        stageContribution.setResultMessage("Posted to Xe");
                        stageContributionRepository.edit(stageContribution);
                        return Response.status(Response.Status.OK)
                                .entity(SuccessVM.builder().success(true).data("Contribution received").build())
                                .build();
                    }
                } else if (!res.equalsIgnoreCase("Failed")) {
                    stageContribution.setSendToXi(true);
                    stageContribution.setResultMessage(res);
                    stageContributionRepository.edit(stageContribution);
                    return ErrorMsg(res);
                }
                return ErrorMsg(res);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ErrorMsg("Record Not found");
    }

    @Override
    public Response mpesaTimeoutCallBack(String s) {
        log.error(s);
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).build())
                .build();
    }

    @Override
    public Response getStagedMemberDetailsAll(long schemeId, long sponsorId) {
        List<StageMemberDetails> stageMemberDetails = stageMemberDetailsRepository.findBySchemeAndSponsor(schemeId, sponsorId);
        if (stageMemberDetails != null) {
            return SuccessMsg("Done", setStagedExtraDetails(stageMemberDetails));
        }
        return ErrorMsg("No available data");
    }

    @Override
    public Response getStagedMemberDetailsSingle(long id) {
        StageMemberDetails stageMemberDetails = stageMemberDetailsRepository.find(id);
        if (stageMemberDetails != null)
            return SuccessMsg("Done", setStagedExtraDetails(stageMemberDetails));
        return ErrorMsg("No record found");
    }

    @Override
    public Response editMemberDetails(long memberId, MemberEditVM memberEditVM) {
        Config config = configRepository.getActiveConfig();
        if (config.getClient().equals(Clients.ETL)) {
            //Send direct to Xe
            boolean status = fmMemberClient.saveOrUpdateMember(xtractMemberFromRequestETL(memberEditVM));
            if (status) {
                doInBackground(new ProcessCallBack() {
                    @Override
                    public void start(Object o) {
                        fmMemberClient.updateKyc(memberId);
                    }
                });
                return Response.ok().entity(
                        SuccessVM.builder().success(true).msg("Details saved").build()
                ).build();
            }
            return ErrorMsg("Error sending request");
        } else {
            // Save to MSS and wait for approval
            StageMemberDetails stageMemberDetails =
                    stageMemberDetailsRepository.findByMemberId(memberId);
            if (stageMemberDetails != null) {
                stageMemberDetails.setDetails(memberEditVM);
                stageMemberDetails = stageMemberDetailsRepository.edit(stageMemberDetails);
            } else {
                stageMemberDetails = StageMemberDetails.from(memberId, memberEditVM);
                stageMemberDetails = stageMemberDetailsRepository.create(stageMemberDetails);
            }
            if (stageMemberDetails != null) {

                StageMemberDetails finalStageMemberDetails = stageMemberDetails;
                //send email to po
                long schemeId = finalStageMemberDetails.getDetails().getSchemeId();

                userService.sendPoEMail(schemeId, EmailTemplatesEnum.PO_MEMBER_BIO_DATA_APPROVAL_REQUEST, finalStageMemberDetails.getDetails().getFirstname() + " " + finalStageMemberDetails.getDetails().getSurname());

                return SuccessMsg("Done", stageMemberDetails.getId());
            }
            return ErrorMsg("Please try again");
        }
    }

    private List<StageMemberDetails> setStagedExtraDetails(List<StageMemberDetails> members) {
        for (StageMemberDetails member : members) {
            try {
                member.setNumDocuments(getFileModelsFromRegMember(member.getDocuments()).size());
            } catch (Exception ignored) {
            }
        }
        return members;
    }

    private StageMemberDetails setStagedExtraDetails(StageMemberDetails member) {
        try {
            member.setNumDocuments(getFileModelsFromRegMember(member.getDocuments()).size());
        } catch (Exception ignored) {
        }
        return member;
    }

    @Override
    public Response editMemberDetailsUploadDocs(long recordId, MultipartFormDataInput input) {
        StageMemberDetails stageMemberDetails =
                stageMemberDetailsRepository.find(recordId);
        try {
            if (stageMemberDetails == null)
                return ErrorMsg("Not found");

            List<FileModel> fileModels = upload(input);
            if (fileModels == null) {
                return ErrorMsg("Failed to upload documents");
            }
            JSONArray jsonArray = new JSONArray();
            for (FileModel fileModel : fileModels) {
                jsonArray.put(fileModel.getJson());
            }
            stageMemberDetails.setDocuments(jsonArray.toString());
            stageMemberDetailsRepository.edit(stageMemberDetails);
            return SuccessMsg("Done", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorMsg("Error encountered");
    }

    @Override
    public Response approveStageMemberDetails(long mssUserId, long id) {
        logActivityTrail(mssUserId, "Approved member details");
        StageMemberDetails stageMemberDetails = stageMemberDetailsRepository.find(id);
        if (stageMemberDetails != null) {
            MemberEditVM memberEditVM = stageMemberDetails.getDetails();
            boolean status = fmMemberClient.saveOrUpdateMember(xtractMemberFromRequest(memberEditVM));
            if (status) {
                stageMemberDetailsRepository.remove(stageMemberDetails);
                notificationService.sendNotification(stageMemberDetails, EmailTemplatesEnum.MEMBER_BIO_DATA_UPDATE_APPROVAL);
                return Response.ok().entity(
                        SuccessVM.builder().success(true).msg("Details saved").build()
                ).build();
            }
            return ErrorMsg("Error sending request");
        }
        return ErrorMsg("Failed to get record");
    }

    @Override
    public Response declineStageMemberDetails(long mssUserId, long id) {
        logActivityTrail(mssUserId, "Declined member details");
        StageMemberDetails stageMemberDetails = stageMemberDetailsRepository.find(id);
        if (stageMemberDetails != null) {
            stageMemberDetailsRepository.remove(stageMemberDetails);
            notificationService.sendNotification(stageMemberDetails, EmailTemplatesEnum.MEMBER_BIO_DATA_UPDATE_DECLINE);
            return SuccessMsg("Done", null);
        }
        return ErrorMsg("Failed to get record");
    }

    Map<String, Object> xtractMemberFromRequestETL(MemberEditVM m) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Map<String, Object> member = new HashMap<>();
        member.put("id", m.getMember_Id());
        member.put("schemeId", m.getSchemeId());
        member.put("maritalStatus", m.getMaritalStatus());
        member.put("staffNo", m.getStaffNo());
        member.put("pinNo", m.getPinNo());
        member.put("postalAddress", m.getPostalAddress());
        member.put("road", m.getRoad());
        member.put("department", m.getDepartment());
        member.put("designation", m.getDesignation());
        member.put("currentAnnualPensionableSalary", m.getMonthlySalary());
        member.put("surname", m.getSurname());
        member.put("firstname", m.getFirstname());
        member.put("othernames", m.getOthernames());
        member.put("email", m.getEmail());
        member.put("idNo", m.getIdNo());
        member.put("fixedPhone", m.getFixedPhone());
        member.put("secondaryPhone", m.getSecondaryPhone());
        member.put("cellPhone", m.getCellPhone());
        member.put("building", m.getBuilding());
        member.put("cellPhone", m.getCellPhone());

        Date dob = m.getDob();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formatedDob = formatter.format(dob);

        member.put("dob", formatedDob);

        Date dateJoined = m.getDateJoinedScheme();
        String formatedDoj = formatter.format(dateJoined);

        member.put("dateJoinedScheme", formatedDoj);
        member.put("nationalPenNo", m.getNationalPenNo());


        member.put("gender", m.getGender());
        member.put("title", m.getTitle());
        member.put("residentialAddress", m.getResidentialAddress());
        member.put("town", m.getTown());
        member.put("country", m.getCountry());
        member.put("mbshipStatus", m.getMbshipStatus());
        member.put("region", m.getRegion());
//        member.put("subRegion", m.getTraditionalAuthority());
//        member.put("maritalStatus", m.getMaritalStatus().name());
        member.put("memberNo", m.getMemberNo());
        return member;
    }

    Map<String, Object> xtractMemberFromRequest(MemberEditVM m) {
        DateFormat format_ = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Map<String, Object> member = new HashMap<>();
        member.put("staffNo", m.getStaffNo());
        member.put("postalAddress", m.getPostalAddress());
        member.put("road", m.getRoad());
        member.put("department", m.getDepartment());
        member.put("designation", m.getDesignation());
        member.put("surname", m.getSurname());
        member.put("firstname", m.getFirstname());
        member.put("othernames", m.getOthernames());
        member.put("email", m.getEmail());
        member.put("idNo", m.getIdNo());
        member.put("fixedPhone", m.getFixedPhone());
        member.put("secondaryPhone", m.getSecondaryPhone());
        member.put("dob", format_.format(m.getDob()));
        member.put("gender", m.getGender());
        member.put("title", m.getTitle());
        member.put("residentialAddress", m.getResidentialAddress());
        member.put("town", m.getTown());
        member.put("country", m.getCountry());
        member.put("memberNo", m.getMemberNo());

        FmListDTO fmListDTO = fmMemberClient.getMemberDetails(m.getMember_Id());
        if (fmListDTO != null && fmListDTO.isSuccess()) {
            Object membersDetails = fmListDTO.getRows().get(0);
            MemberDetailsVM memberDetailsVM = MemberDetailsVM.from(membersDetails);
//        member.put("region", m.getPlaceOfBirth());
//        member.put("subRegion", m.getTraditionalAuthority());
//        member.put("maritalStatus", m.getMaritalStatus().name());
//        member.put("currentAnnualPensionableSalary", m.getSalary());
//        String dOE = DateUtils.formatDate(m.getDateOfEmployment(), "yyyy-MM-dd");
//        member.put("dateOfEmployment", dOE);
            if (memberDetailsVM != null) {
                member.put("companyId", memberDetailsVM.getCostCenterId());
                member.put("mclassId", memberDetailsVM.getMemberclassId());
            }
        }
        return member;
    }

    @Override
    public Response getAllStagedContributions(long mssUserId) {
        logActivityTrail(mssUserId, "Viewed Staged Contributions");
        List<StageContribution> stageContributions = stageContributionRepository.findByNamedQuery("findFalseContribution");
        for (StageContribution s :
                stageContributions) {
            SimpleDateFormat DateFormatted = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String stringDate = DateFormatted.format(s.getCreatedAt());
            s.setShortCreatedDate(stringDate);
        }

        return stageContributions != null ?
                SuccessMsg("Done", stageContributions) :
                ErrorMsg("No data found");
    }

    @Override
    public Response getPortalLegibility(long memberId) throws IOException, ParseException {
        FmListDTO fmListDTO = fmMemberClient.getMemberDetails(memberId);
        if (fmListDTO != null && fmListDTO.isSuccess()) {
            Object memberDetails = fmListDTO.getRows().get(0);
            String jsonString = new ObjectMapper().writeValueAsString(memberDetails);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
            if (jsonObject != null) {
                String membershipStatus = jsonObject.get("mbshipStatus").toString();
                boolean isPortalLegible;
                if (membershipStatus.equals("RETIRED") ||
                        membershipStatus.equals("RETIRED_ILL_HEALTH") ||
                        membershipStatus.equals("DEATH_IN_RETIREMENT") ||
                        membershipStatus.equals("WITHDRAWN") ||
                        membershipStatus.equals("DEATH_IN_SERVICE") ||
                        membershipStatus.equals("INTERDICTION") ||
                        membershipStatus.equals("DEFFERED") ||
                        membershipStatus.equals("TRANSFERED") ||
                        membershipStatus.equals("DELETED") ||
                        membershipStatus.equals("TRANSFERRED_INTRA_INTRA") ||
                        membershipStatus.equals("TRANSFERRED_INTRA_OUT")
                ) {
                    isPortalLegible = false;
                } else {
                    isPortalLegible = true;
                }

                ObjectMapper objectMapper = new ObjectMapper();
                JsonObject jsonObject1 = Json.createObjectBuilder()
                        .add("isPortalLegible", isPortalLegible)
                        .build();

                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(
                                objectMapper.readValue(jsonObject1.toString(), Object.class)
                        ).build())
                        .build();
            }
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response GET_MEMBER_PRODUCTS(String login, long schemeId) {
        Profile profile = profileService.getProfileByName("MEMBER");
        if (profile != null) {
            org.json.JSONObject jsonObject = null;
            LoginIdentifier ordinal = profile.getLoginIdentifier();
            switch (ordinal) {
                case MEMBER_NO:
                case MEMBER_ID:
                case PHONE:
                case NATIONAL:
                case EMAIL:
                    jsonObject = fmMemberClient.GET_MEMBER_PRODUCTS(
                            login,
                            schemeId,
                            ordinal.name());
                    break;
                default:

            }
            try {
                if (jsonObject != null) {
                    if (jsonObject.has("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("rows");
                        List<MemberSponsorVM> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            MemberSponsorVM memberSponsorVM = MemberSponsorVM.map(jsonArray.getJSONObject(i).toString());
                            if (memberSponsorVM != null)
                                list.add((MemberSponsorVM) memberSponsorVM);
                        }
                        return SuccessMsg("Done", list);
                    }
                    return ErrorMsg(jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return ErrorMsg("Sponsors not found");
        }
        return ErrorMsg("Profile not found");
    }

    @Override
    public Response preserveAccount(long mssUserId, long memberId, long schemeId) {
        logActivityTrail(mssUserId, "Preserved an account");
        boolean b = fmMemberClient.preserveAccount(memberId, schemeId);
        if (b) {
            return SuccessMsg("Done", null);
        }
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response memberKYCCheck(long memberId, long schemeId) {
        MemberKYC memberKYC = fmMemberClient.memberKYCCheck(memberId, schemeId);
        if (memberKYC != null)
            return SuccessMsg("Done", memberKYC);
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response checkIfMemberHasGenericDoB(long memberId) {
        GenericDOBVM genericDOBVM = fmMemberClient.checkIfMemberHasGenericDob(memberId);
        if (genericDOBVM != null)
            return SuccessMsg("Done", genericDOBVM);
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response getMemberAccountDetails(long memberId, long schemeId) {
        MemberAccountDetailsVM memberAccountDetailsVM = fmMemberClient.getMemberAccountDetails(memberId);
        if (memberAccountDetailsVM != null)
            return SuccessMsg("Done", memberAccountDetailsVM);
        return ErrorMsg("Account summary loading failed");
    }

    @Override
    public Response CHECK_IF_IDNUMBER_EXIST(String employerREFNo, String idNumber, String idType) {
        org.json.JSONObject jsonObject = fmMemberClient.checkIfIdNumberExists(employerREFNo, idType, idNumber, "NO");
        if (jsonObject != null) {
            try {
                if (jsonObject.getBoolean(Fields.SUCCESS)) {
                    return SuccessMsg("Done", "Id Number does not exist");
                } else {
                    return ErrorMsg(jsonObject.getString(Fields.MESSAGE));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ErrorMsg("An error occured while processing the request!");
    }

    @Override
    public Response getStagedBeneficiaries(long mssUserId, long schemeId, long sponsorId) {
        logActivityTrail(mssUserId, "Get staged beneficiary details");
        List<StagedBeneficiaries> stagedBeneficiaries = stagedBeneficiariesRepository.findBySchemeAndSponsor(schemeId, sponsorId);
        if (stagedBeneficiaries != null) {
            return SuccessMsg("Done", stagedBeneficiaries);
        }
        return ErrorMsg("No data available");
    }

    @Override
    public Response uploadBeneficiaryDocuments(long recordId, MultipartFormDataInput input) {
        try {
            Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
            if (uploadForm.isEmpty())
                return ErrorMsg("No files selected");
            if (!uploadForm.containsKey("reqId")) {  //benefit request ID
                return ErrorMsg("Error processing request");
            }

            List<FileModel> fileModels = upload(input);
            if (fileModels == null) {
                return ErrorMsg("Failed to upload documents");
            }

            JSONArray documents = new JSONArray();
            for (FileModel fileModel : fileModels) {
                documents.put(fileModel.getJson());
            }

            StagedBeneficiaries stagedBeneficiaries = stagedBeneficiariesRepository.find(recordId);
            if (stagedBeneficiaries != null) {
                stagedBeneficiaries.setDocuments(
                        documents.toString()
                );
                stagedBeneficiariesRepository.edit(stagedBeneficiaries);
            }
            return SuccessMsg("Documents uploaded", null);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ErrorMsg("Error encountered");
    }

    @Override
    public Response getMemberBankDetails(long memberId, String yesNo) {
        TotalCountAndRowsDTO fmListDTO = fundMasterClient.getMemberBankDetails(memberId, yesNo);
        if (fmListDTO != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getMemberBenefitsFm(String name, long memberId) {
        TilesDTO fmListDTO = fundMasterClient.getMemberBenefitsFm(name, memberId);
        if (fmListDTO != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response previewMemberBioDataChanges(long mssUserId, long stagedMemberId) {
        logActivityTrail(mssUserId, "Preview Member BioData changes for {" + stagedMemberId + "}");
        StageMemberDetails stageMemberDetails = stageMemberDetailsRepository.find(stagedMemberId);
        if (stageMemberDetails != null) {
            MemberEditVM memberEditVM = stageMemberDetails.getDetails();
            if (memberEditVM != null) {
                MemberDetailsVM memberDetailsVM = fmMemberClient.getMemberDetailsSingle(stageMemberDetails.getMemberId());
                if (memberDetailsVM != null) {
                    String prevJson = StringUtil.toJsonString(memberDetailsVM);
                    String newJson = StringUtil.toJsonString(memberEditVM);
                    List<ObjectComparisonVM> comparator = objectComparator(
                            prevJson, newJson,
                            "member_Id","dob","mbshipStatus"
                            ,"schemeId","dateJoinedScheme","dateOfAppointment",
                            "jobGradeId","contractEndDate","partyRefNo",
                            "companyId","mclassId","sponsorId"
                    );
                    if (comparator.isEmpty()){
                        return ErrorMsg("No changes made");
                    }
                    for (ObjectComparisonVM objectComparisonVM : comparator) {
                        String key = objectComparisonVM.getKey();
                        objectComparisonVM.setReadableKey(
                                key.toUpperCase(Locale.getDefault())
                        );
                    }
                    return SuccessMsg(
                            "Done",
                            comparator
                    );
                }
            }
            return ErrorMsg("Unable to get original member details");
        }
        return ErrorMsg("Record NOT found");
    }

    @Override
    public Response updateBankDefaultPoint(String yesNo, long bankDetailId) {
        boolean updateBankDefaultPoint = fundMasterClient.updateBankDefaultPoint(yesNo, bankDetailId);
        if (updateBankDefaultPoint) {
            return Response.ok().entity(SuccessVM.builder().success(true).msg("Bank default point successfully Updated").build()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Bank default point could not be Updated, please try again after some time"
                ).build())
                .build();
    }

    @Override
    public Response addMemberBank(AddBankVM addBankVM) {
        String s = getBankDetailsMemberJson(addBankVM);
        if (s != null)
            return saveMemberBanks(s);
        return ErrorMsg("Failed, please try again");
    }

    public Response saveMemberBanks(String json) {
        boolean saveBank = fundMasterClient.saveBanks(json);
        if (saveBank) {
            return Response.ok().entity(SuccessVM.builder().success(true).msg("Bank Details updated successfully").build()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }

    String getBankDetailsMemberJson(AddBankVM addBankVM) {
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject();
            jsonObject.put("bankDetailsId", addBankVM.getBankDetailsId());
            jsonObject.put("memberId", addBankVM.getMemberId());
            jsonObject.put("bankCode", addBankVM.getBankCode());
            jsonObject.put("bankbranchCode", addBankVM.getBankBranchCode());
            jsonObject.put("accountName", addBankVM.getAccountName());
            jsonObject.put("accountNumber", addBankVM.getAccountNumber());
            jsonObject.put("defaultPoint", addBankVM.getDefaultPoint());

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Response calculateProvisionalBalance(long schemeId, long memberId) {
        MessageModelDTO messageModelDTO = fmMemberClient.calculateProvisionalBalance(schemeId, memberId);
        if (messageModelDTO != null)
            if (messageModelDTO.isSuccess())
                return SuccessMsg("Done", null);
        return ErrorMsg("Failed to perform operation");
    }

    /**
     * To receive documents use this method
     *
     * @param uploadMemberDocumentVM
     * @return Response
     */
    public Response receiveMemberUploadedDocument(UploadMemberDocumentVM uploadMemberDocumentVM) {
        Object o = fmMemberClient.memberUploadDocument(uploadMemberDocumentVM);
        if (o != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true)
                            .data(o).build())
                    .build();
        }
        return ErrorMsg("Failed, please try again");
    }

    public void TestPdf() throws IOException, DocumentException {
        URL url = new URL("http://localhost:8080/mss/");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.connect();
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("html.pdf"));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new InputStreamReader(
                httpURLConnection.getInputStream()
        ));

        httpURLConnection.disconnect();
        document.close();
    }

    public List<StageContribution> setExtraDetails(List<StageContribution> stageContributionList) {
        for (StageContribution s : stageContributionList) {
            s.setMemberName(userService.getFullNameByMemberId(s.getMemberId()));
        }
        return stageContributionList;
    }

}
