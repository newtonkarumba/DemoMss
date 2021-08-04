package com.systech.mss.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.controller.BenefitRequestController;
import com.systech.mss.controller.vm.*;
import com.systech.mss.domain.*;
import com.systech.mss.domain.common.AuthorizationLevel;
import com.systech.mss.domain.common.Clients;
import com.systech.mss.fileupload.FileModel;
import com.systech.mss.repository.BenefitRequestRepository;
import com.systech.mss.repository.ClaimDocumentsRepository;
import com.systech.mss.service.*;
import com.systech.mss.service.dto.FmListDTO;
import com.systech.mss.service.dto.StringListDTO;
import com.systech.mss.util.StringUtil;
import com.systech.mss.vm.SMSVM;
import com.systech.mss.vm.benefitrequest.*;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BenefitRequestControllerImpl extends BaseController implements BenefitRequestController {

    @Inject
    BenefitRequestRepository benefitRequestRepository;

    @Inject
    private BenefitRequestService benefitRequestService;

    @Inject
    private ActivityTrailService activityTrailService;

    @Inject
    private FMCRMClient fmcrmClient;


    @Inject
    private ClaimDocumentsRepository claimDocumentsRepository;


    @Inject
    private SmsService smsService;


    @Inject
    private NotificationService notificationService;

//    private long schemeId;

    @Override
    public Response uploadClaimDocuments(long mssUserId, MultipartFormDataInput input) throws ParseException {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        if (uploadForm.isEmpty())
            return ErrorMsg("No files selected");
        if (!uploadForm.containsKey("reqId")) {  //benefit request ID
            return ErrorMsg("Error processing request");
        }

        try {
            String reqId = (uploadForm.get("reqId").get(0).getBodyAsString());
            long benefitRequestId = Long.parseLong(reqId);
            BenefitRequest benefitRequest = benefitRequestRepository.find(benefitRequestId);
            if (benefitRequest == null) {
                return ErrorMsg("Claim not found");
            }

            List<FileModel> fileModels = upload(input);
            if (fileModels == null) {
                return ErrorMsg("Failed to upload documents");
            }

            User user = userRepository.find(mssUserId);
            if (user != null) {
                List<Documents> documentsList = saveFiles(user, fileModels);
                for (Documents document : documentsList) {
                    ClaimDocuments claimDocuments = new ClaimDocuments();
                    claimDocuments.setBenefitRequestId(benefitRequestId);
                    claimDocuments.setDocumentId(document.getId());
                    try {
                        claimDocumentsRepository.create(claimDocuments);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                benefitRequestRepository.updateSetDocumentsUploaded(benefitRequestId);
                logActivityTrail(mssUserId, "Uploaded claim documents");
                return SuccessMsg("Documents uploaded", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorMsg("Error encountered");
    }

    @Override
    public Response uploadClaimDocument(long mssUserId, long claimDocumentId, MultipartFormDataInput input) throws ParseException {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        if (uploadForm.isEmpty())
            return ErrorMsg("No files selected");
        if (!uploadForm.containsKey("reqId")) {  //benefit request ID
            return ErrorMsg("Error processing request");
        }

        try {
            String reqId = (uploadForm.get("reqId").get(0).getBodyAsString());
            long benefitRequestId = Long.parseLong(reqId);
            BenefitRequest benefitRequest = benefitRequestRepository.find(benefitRequestId);
            if (benefitRequest == null) {
                return ErrorMsg("Claim not found");
            }

            List<FileModel> fileModels = upload(input);
            if (fileModels == null) {
                return ErrorMsg("Failed to upload documents");
            }

            ClaimDocuments claimDocuments = claimDocumentsRepository.find(claimDocumentId);
            if (claimDocuments != null) {
                User user = userRepository.find(mssUserId);
                if (user != null) {
                    List<Documents> documentsList = saveFiles(user, fileModels);
                    for (Documents document : documentsList) {
                        claimDocuments.setDocumentId(document.getId());
                        claimDocuments.setUploaded(true);
                        try {
                            claimDocumentsRepository.edit(claimDocuments);
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    List<ClaimDocuments> claimDocumentsList = claimDocumentsRepository.getByClaimIdAndReasonForExit(
                            benefitRequestId,
                            benefitRequest.getBenefitReason()
                    );
                    boolean notUploadedFound = false;
                    for (ClaimDocuments documents : claimDocumentsList) {
                        if (!documents.isUploaded()) {
                            notUploadedFound = true;
                            break;
                        }
                    }
                    if (!notUploadedFound) //notUploadedFound==FALSE ie ALL DOCUMENTS UPLOADED
                        benefitRequestRepository.updateSetDocumentsUploaded(benefitRequestId);
                    logActivityTrail(mssUserId, "Uploaded claim document");
                    return SuccessMsg("Documents uploaded", notUploadedFound ? "NOT DONE" : "DONE");
                }
            }
            return ErrorMsg("Claim document not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorMsg("Error encountered");
    }

    @Override
    public Response getMemberBenefitRequests(long mssUserId, long memberId) {
        List<BenefitRequest> benefitRequest = benefitRequestRepository.getMemberBenefitRequests(memberId);
        if (benefitRequest != null) {
            logActivityTrail(mssUserId, "Get Benefit Request");
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(benefitRequest).build())
                    .build();
        }
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response savePersonalDetails(long mssUserId, long memberId, PersonalDetailsVM personalDetailsVM) {

        List<BenefitRequest> list = benefitRequestRepository.getMemberBenefitRequests(memberId);
        if (list != null) {
            for (BenefitRequest benefitRequest : list) {
                Status status = benefitRequest.getStatus();
                //KEEP THE ORDER
                switch (status) {
                    case UPLOADED:
                    case CERTIFIED:
                    case AUTHORIZED:
                    case APPROVED:
                    case PUSHED_TO_FUNDMASTER:
                    case PENDING:
                        return ErrorMsg("You have a pending claim, please wait until it is resolved");

                    case DECLINED:
                    case NOT_UPLOADED:
                    case DECLINED_BY_CRM:
                    case DECLINED_BY_SPONSOR:
                    default:
                }
            }
        }

        log.error(personalDetailsVM.toString());
        BenefitRequest benefitRequest = benefitRequestRepository.savePersonalDetails(memberId, mssUserId, personalDetailsVM);
        if (benefitRequest != null) {
            logActivityTrail(mssUserId, "Initiated Benefit Request");
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(benefitRequest.getId()).build())
                    .build();
        }
        return ErrorMsg("Failed, please try again");
    }

    private StringListDTO getMemberSubmittedDocuments(long memberId) {
        return fmMemberClient.getSubmittedDocuments(memberId);
    }

    private List<String> getMissingRequiredDocuments(long memberId, List<DocumentVM> docs) {
        List<String> response = new ArrayList<>();
        try {
            StringListDTO stringListDTO = getMemberSubmittedDocuments(memberId);
            if (stringListDTO.getSuccess().equalsIgnoreCase("true")) {
                if (stringListDTO.getRows() != null) {
                    List<Object> objects = stringListDTO.getRows();
                    for (DocumentVM doc :
                            docs) {

                        boolean isAvailable = false;
                        for (Object o :
                                objects) {
                            String jsonString = StringUtil.toJsonString(o);
                            try {
                                assert jsonString != null;
                                org.json.JSONObject jsonObject = new org.json.JSONObject(jsonString);
                                String docName = jsonObject.getString("name");

                                if (docName.contains(doc.getShortName())) {
                                    isAvailable = true;
                                    break;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (!isAvailable) {
                            response.add(doc.getDocumentName());
                        }
                    }
                    return response;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.clear();//set to empty
        for (DocumentVM documentVM :
                docs) {
            response.add(documentVM.getDocumentName());
        }
        return response;  //should upload the documents
    }

    @Override
    public Response saveGroundOfBenefits(long mssUserId, long memberId, GroundOfBenefits groundOfBenefits) {
        try {
            Map<String, Object> reasonForExit = fmMemberClient.getReasonForExitById(groundOfBenefits.getReasonId());
            if (reasonForExit == null) {
                return ErrorMsg("Unknown reason for exit");
            }
            List<ReasonForExitDocument> reasonForExitDocuments = fmMemberClient.getDocumentsRequiredPerClaim(
                    groundOfBenefits.getReasonId()
            );

            log.error("Documents :" + StringUtil.toJsonString(reasonForExitDocuments));

            Map<String, Object> map = new HashMap<>();
            String reason = groundOfBenefits.getReason();
            String category = StringUtil.toString(reasonForExit.get("category"));
//            String mbshipStatus = StringUtil.toString(reasonForExit.get("mbshipStatus"));

            // Conflicting at NICO SOLUTION IS TO INITIATE PRE EXIT ON SEPARATE ACTION
//            if (category.equalsIgnoreCase("withdrawal") && mbshipStatus.equalsIgnoreCase("Partial Withdrawal Paid")) {
//                reason = "Pre-Exit";
//            }
            if (reason.equalsIgnoreCase("Normal Retirement")) {
                User user = userRepository.find(mssUserId);
                if (user != null) {
                    FmListDTO fmListDTO = fmMemberClient.getMemberDetails(memberId);
                    if (fmListDTO != null && fmListDTO.isSuccess()) {
                        if (fmListDTO.getRows() != null) {
                            Object ob = fmListDTO.getRows().get(0);
                            JSONParser jsonParser = new JSONParser();
                            JSONObject jsonObject = (JSONObject) jsonParser.parse(
                                    new ObjectMapper().writeValueAsString(ob)
                            );
                            if (jsonObject != null) {
                                //get user age
                                log.error(jsonObject.toString());
                                if (jsonObject.containsKey("age") && jsonObject.containsKey("retirementAge")) {
                                    try {
                                        int currentAge = Integer.parseInt(
                                                String.valueOf(jsonObject.get("age"))
                                        ),
                                                retirementAge = Integer.parseInt(
                                                        String.valueOf(jsonObject.get("retirementAge"))
                                                );
                                        if (currentAge < retirementAge) {
                                            return Response.status(Response.Status.BAD_REQUEST)
                                                    .entity(ErrorVM.builder().success(false)
                                                            .msg("You have not reached retirement age of " + retirementAge).build())
                                                    .build();
                                        }
                                    } catch (NumberFormatException e) {
                                        log.error(e.getMessage());
                                    }
                                }
                            }

                        }
                    }
                }
            }

//            List<String> requiredDocs = new ArrayList<>();
            int size = 0;
            if (reasonForExitDocuments != null) {
                size = reasonForExitDocuments.size();
                //REMOVE PREVIOUS DOCUMENTS IF ANY
                List<ClaimDocuments> claimDocumentsList=claimDocumentsRepository.getByClaimId(groundOfBenefits.getId());
                if (claimDocumentsList!=null){
                    for (ClaimDocuments claimDocument : claimDocumentsList) {
                        claimDocumentsRepository.remove(claimDocument);
                    }
                }
                for (ReasonForExitDocument reasonForExitDocument : reasonForExitDocuments) {
//                    requiredDocs.add(reasonForExitDocument.getChecklistName());
                    ClaimDocuments claimDocuments = ClaimDocuments.from(groundOfBenefits, reasonForExitDocument);
                    claimDocumentsRepository.create(claimDocuments);
                }
            }

            groundOfBenefits.setRequiresDocuments(size > 0);
            //            for (int i = 0; i < requiredDocs.size(); i++) {
//                docsString.append("<p>").append(i + 1).append(" ").append(requiredDocs.get(i)).append("</p>");
//            }
            map.put("docs", "<p>Kindly Note that you will be required to attached the following documents</p>");
            map.put("reason", reason);
            map.put("isMedical", category.contains("Ill Health"));
            map.put("skipPaymentOptions", true);
            map.put("uploadFiles", size > 0);

//            log.error(StringUtil.toJsonString(map));
            BenefitRequest benefitRequest = benefitRequestRepository.saveGroundOfBenefits(groundOfBenefits);
            if (benefitRequest != null) {
                logActivityTrail(mssUserId, "Saved ground of benefits for claim process");
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(map).build())
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorMsg("Failed, please try again");
    }

    @SuppressWarnings("ALL")
    public Response saveGroundOfBenefits_(long mssUserId, long memberId, GroundOfBenefits groundOfBenefits) {
        try {
            Map<String, Object> reasonForExit = fmMemberClient.getReasonForExitById(groundOfBenefits.getReasonId());
            if (reasonForExit == null) {
                return ErrorMsg("Unknown reason for exit");
            }

            String category = StringUtil.toString(reasonForExit.get("category"));
            String mbshipStatus = StringUtil.toString(reasonForExit.get("mbshipStatus"));


            List<DocumentVM> docs = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("category", category);
            map.put("mbshipStatus", mbshipStatus);

            String reason = groundOfBenefits.getReason();

            boolean isMedical = false,
                    uploadDocuments = true,
                    skipPaymentOptions = true;

            List<ReasonForExitDocument> reasonForExitDocuments = fmMemberClient.getDocumentsRequiredPerClaim(
                    groundOfBenefits.getReasonId()
            );
            if (reasonForExitDocuments != null) {
                for (ReasonForExitDocument reasonForExitDocument : reasonForExitDocuments) {
                    docs.add(new DocumentVM(reasonForExitDocument.getChecklistName(), reasonForExitDocument.getChecklistName()));
                }
            } else {
                docs.add(new DocumentVM("Claim Form", "Claim Form"));
                docs.add(new DocumentVM("National ID", "Copy of National ID Card."));
            }

            try {
                if (reason.contains("Death") || reason.contains("Dismissal")) {
                    map.put("reason", "Death");
                    if (docs.isEmpty()) {
                        docs.add(new DocumentVM("Death Certificate", "Death Certificate"));
                        docs.add(new DocumentVM("Order of appointment of administrator", "Order of appointment of administrator"));
                        docs.add(new DocumentVM("ID of deceased", "ID of deceased"));
                        docs.add(new DocumentVM("ID of administrator", "ID of administrator"));
                        docs.add(new DocumentVM("Death claim form", "Death claim form"));
                    }
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(ErrorVM.builder().success(false)
                                    .msg("You can not initiate claim for " + reason).build())
                            .build();
                } else if ((category.contains("Ill Health") || mbshipStatus.contains("ill"))) {
                    reason = "medical";
                    isMedical = true;
                    if (docs.isEmpty()) {
                        docs.add(new DocumentVM("Medical Discharge", "Letter of medical discharge"));
                        docs.add(new DocumentVM("Medical Certificate", "Medical Certificate"));
                    }
                }
                //THIS IS A PRE-EXIT WITHDRAWAL
                else if (category.contains("Withdrawal") && mbshipStatus.equalsIgnoreCase("Partial Withdrawal Paid")) {
                    uploadDocuments = false;
                    skipPaymentOptions = false;
                    reason = "Pre-Exit";
                }
                //Normal retirement check age
                //check ground Of Benefits reason against age for normal retirement
                else if (mbshipStatus.contains("Retired") && reason.equalsIgnoreCase("Normal Retirement")) {
                    reason = "Normal Retirement";

                    if (docs.isEmpty()) {
                        docs.add(new DocumentVM("Annuity Form", "Annuity Form."));
                    }
                    User user = userRepository.find(mssUserId);
                    if (user != null) {
                        FmListDTO fmListDTO = fmMemberClient.getMemberDetails(memberId);
                        if (fmListDTO != null && fmListDTO.isSuccess()) {
                            if (fmListDTO.getRows() != null) {
                                Object ob = fmListDTO.getRows().get(0);
                                JSONParser jsonParser = new JSONParser();
                                JSONObject jsonObject = (JSONObject) jsonParser.parse(
                                        new ObjectMapper().writeValueAsString(ob)
                                );
                                if (jsonObject != null) {
                                    //get user age
                                    if (jsonObject.containsKey("age") && jsonObject.containsKey("retirementAge")) {
                                        int currentAge = Integer.parseInt(
                                                String.valueOf(jsonObject.get("age"))
                                        ),
                                                retirementAge = Integer.parseInt(
                                                        String.valueOf(jsonObject.get("retirementAge"))
                                                );
                                        if (currentAge < retirementAge) {
                                            return Response.status(Response.Status.BAD_REQUEST)
                                                    .entity(ErrorVM.builder().success(false)
                                                            .msg("You have not reached retirement age of " + retirementAge).build())
                                                    .build();
                                        }
                                    }
                                }

                            }
                        }
                    }
                } else if (reason.contains("retirement")) {
                    reason = "Early Retirement";
                    docs.add(new DocumentVM("Letter", "Letter of Retirement."));
                    docs.add(new DocumentVM("Annuity form", "Annuity form"));
                } else if (mbshipStatus.contains("Withdraw") && category.contains("Withdraw")) {
                    if (docs.isEmpty()) {
                        if (reason.contains("resign"))
                            docs.add(new DocumentVM("Resignation Letter", "Letter of Resignation"));
                    }
                    reason = "Withdraw";
                } else if (mbshipStatus.contains("Transfer") && category.contains("Withdraw")) {
                    reason = "Transfer";
                    if (docs.isEmpty()) {
                        docs.add(new DocumentVM("Tax Pin", "Tax Pin"));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            List<String> requiredDocs = new ArrayList<>();
            if (uploadDocuments) {
                requiredDocs = getMissingRequiredDocuments(memberId, docs);
                groundOfBenefits.setRequiresDocuments(requiredDocs.size() > 0);

                StringBuilder docsString = new StringBuilder("<p>Kindly Note that you will be required to attached the following documents</p>");
                for (int i = 0; i < requiredDocs.size(); i++) {
                    docsString.append("<p>").append(i + 1).append(" ").append(requiredDocs.get(i)).append("</p>");
                }
                map.put("docs", docsString.toString());
            }

            map.put("reason", reason);
            map.put("isMedical", isMedical);
            map.put("skipPaymentOptions", skipPaymentOptions);
            map.put("uploadFiles", uploadDocuments && requiredDocs.size() > 0);

            BenefitRequest benefitRequest = benefitRequestRepository.saveGroundOfBenefits(groundOfBenefits);
            if (benefitRequest != null) {
                logActivityTrail(mssUserId, "Saved ground of benefits for claim process");
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(map).build())
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response getDocumentsForReasonOfExit(long mssUserId, long reasonForExitId) {
        List<ReasonForExitDocument> reasonForExitDocuments = fmMemberClient.getDocumentsRequiredPerClaim(reasonForExitId);
        if (reasonForExitDocuments != null) {
            return SuccessMsg("Done", reasonForExitDocuments);
        }
        return ErrorMsg("Please try again");
    }

    @Override
    public Response updateSetDocumentsUploaded(long mssUserId, long recordId) {
        benefitRequestRepository.updateSetDocumentsUploaded(recordId);
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).build())
                .build();
    }

    @Override
    public Response saveMedicalReasons(long mssUserId, @Valid MedicalReasons medicalReasons) {
        try {
            BenefitRequest benefitRequest = benefitRequestRepository.saveMedicalReasons(medicalReasons);
            if (benefitRequest != null) {
                logActivityTrail(mssUserId, "Saved medical reasons");
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).build())
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response savePaymentOptions(long mssUserId, @Valid PaymentOptionsVM paymentOptionsVM) {

        PaymentOptionsVM optionsVM = PaymentOptionsVM.getInstance(paymentOptionsVM);
//        log.error(optionsVM.toString());
        BenefitRequest benefitRequest = benefitRequestRepository.savePaymentOptions(
                optionsVM
        );
        if (benefitRequest != null) {
            logActivityTrail(mssUserId, "Saved payment options for claim process");
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).build())
                    .build();
        }
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response checkVestingLiabilities(long mssUserId, long id) {
        BenefitRequest benefitRequest = benefitRequestRepository.find(id);
        if (benefitRequest != null) {
            logActivityTrail(mssUserId, "Check vesting scales");
            Object o = fmMemberClient.checkVestingLiabilities(benefitRequest.getMemberId(), benefitRequest.getBenefitReasonId());
            if (o != null) {
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).data(o).build())
                        .build();
            }
        }
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response saveBankDetails(long mssUserId, @Valid BankDetailsVM bankDetailsVM) {
        BenefitRequest benefitRequest = benefitRequestRepository.saveBankDetails(bankDetailsVM);
        if (benefitRequest != null) {
            logActivityTrail(mssUserId, "Saved bank details for claim process");
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(bankDetailsVM).build())
                    .build();
        }
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response finishSavingBenefitRequest(long mssUserId, long reqId) {
        BenefitRequest benefitRequest = benefitRequestRepository.finishSavingBenefitRequest(reqId);
        if (benefitRequest != null) {
            logActivityTrail(mssUserId, "Submitted claim");

            User user = userService.getUserFromFundmaster(benefitRequest.getMemberId());
            if (user != null) {
                //send notifications
                notificationService.sendNotification(
                        user,
                        EmailTemplatesEnum.MEMBER_CLAIM_INITIATED,
                        String.valueOf(benefitRequest.getId())
                );

                //send email to po
                long schemeId = user.getUserDetails().getSchemeId();
                userService.sendPoEMail(schemeId, EmailTemplatesEnum.PO_PENDING_CLAIM, user.getUserDetails().getName());
            }

            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("Done").build())
                    .build();
        }
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response finishSavingBenefitRequestPo(long mssUserId, long reqId) {
        BenefitRequest benefitRequest = benefitRequestRepository.finishSavingBenefitRequest(reqId);
        if (benefitRequest != null) {
            logActivityTrail(mssUserId, "Submitted claim");


            //here we need to revamp get users email from fundmaster
            try {
                //User can be null when not registered in MSS
                User user = userService.getUserFromFundmaster(benefitRequest.getMemberId());
                if (user != null) {
                    notificationService.sendNotification(
                            user,
                            EmailTemplatesEnum.CLAIM_STATUS,
                            String.valueOf(benefitRequest.getId()),
                            "Successfully created By your Principal Officer " + user.getUserDetails().getName()
                    );

                    //send email to crm

                    long schemeId = user.getUserDetails().getSchemeId();
                    userService.sendCrmEMail(schemeId, EmailTemplatesEnum.CLAIM_INITIATED, user.getUserDetails().getName());

                }

            } catch (Exception e) {
                log.error(e.getMessage());

                log.info("catch");

                FmListDTO memberDetails = fmMemberClient.getMemberDetails(benefitRequest.getMemberId());
                Object details = memberDetails.getRows().get(0);
                JSONParser jsonParser = new JSONParser();
                try {
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(
                            new ObjectMapper().writeValueAsString(details));
                    long scheme = Long.parseLong(String.valueOf(jsonObject.get("schemeId")));
                    String name = String.valueOf(jsonObject.get("name"));
                    userService.sendCrmEMail(scheme, EmailTemplatesEnum.CLAIM_INITIATED, name);
                } catch (ParseException | JsonProcessingException parseException) {
                    parseException.printStackTrace();
                }
            }

            benefitRequest.setCertify(true);
            benefitRequest.setDeclined(false);
            benefitRequest.setStatus(Status.CERTIFIED);
            benefitRequest.setCertifiedBy(mssUserId);
            benefitRequest.setDateCertified(LocalDateTime.now());
            benefitRequest.setApproved(true);
            benefitRequest.setStatus(Status.APPROVED);
            benefitRequest.setDateApproved(LocalDateTime.now());
            benefitRequest.setApprovedById(mssUserId);
            benefitRequestRepository.edit(benefitRequest);

            return SuccessMsg("Saved Successfully", null);

        }
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response getBenefitsBySchemeIdAndSponsorId(long schemeId, long sponsorId) {
        List<BenefitRequest> benefitRequestList = benefitRequestService.getBenefitsBySchemeIdAndSponsorId(schemeId, sponsorId);
        if (benefitRequestList != null) {
            return SuccessMsg("Done", benefitRequestService.setBenefitRequestExtraDetails(benefitRequestList));
        }
        return ErrorMsg("No benefits found");
    }

    @Override
    public Response getUnauthorizedBenefitsBySchemeId(long mssUserId, long schemeId) {
        activityTrailService.logActivityTrail(mssUserId, "Viewed Member Claims");
        List<BenefitRequest> benefitRequests = benefitRequestService
                .getBenefitsBySchemeId(schemeId)
                .stream().filter(benefitRequest -> benefitRequest.isApproved() || benefitRequest.isCertify() || !benefitRequest.isAuthorize()).collect(Collectors.toList());
        return benefitRequests.size() > 0 ?
                SuccessMsg("Done", benefitRequestService.setBenefitRequestExtraDetailsById(benefitRequests))
                :
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(ErrorVM.builder().success(false).msg("No benefits found").build())
                        .build();
    }

    @Override
    public Response getUncertifiedBenefitsBySchemeId(long mssUserId, long schemeId) {
        activityTrailService.logActivityTrail(mssUserId, "Viewed Member Claims");
        List<BenefitRequest> benefitRequests = benefitRequestService
                .getBenefitsBySchemeId(schemeId)
                .stream().filter(benefitRequest -> !benefitRequest.isApproved() || !benefitRequest.isCertify() || !benefitRequest.isAuthorize()).collect(Collectors.toList());
        return benefitRequests.size() > 0 ?
                SuccessMsg("Done", benefitRequestService.setBenefitRequestExtraDetailsById(benefitRequests))
                :
                ErrorMsg("No benefits found");
    }

    @Override
    public Response getUnapprovedBenefitsBySchemeId(long mssUserId, long schemeId) {
        activityTrailService.logActivityTrail(mssUserId, "Viewed Member Claims");
        List<BenefitRequest> benefitRequests = benefitRequestService
                .getBenefitsBySchemeId(schemeId)
                .stream().filter(benefitRequest -> !benefitRequest.isApproved() || benefitRequest.isCertify() || !benefitRequest.isAuthorize()).collect(Collectors.toList());
        return benefitRequests.size() > 0 ?
                SuccessMsg("Done", benefitRequestService.setBenefitRequestExtraDetailsById(benefitRequests))
                :
                ErrorMsg("No benefits found");
    }

    @Override
    public Response getBenefitsBySchemeId(long mssUserId, long schemeId) {
        activityTrailService.logActivityTrail(mssUserId, "Viewed Member Claims");
        List<BenefitRequest> benefitRequests = benefitRequestService
                .getBenefitsBySchemeId(schemeId)
                .stream().filter(benefitRequest -> !benefitRequest.isApproved() || !benefitRequest.isCertify() || !benefitRequest.isAuthorize()).collect(Collectors.toList());
        return benefitRequests.size() > 0 ?
                SuccessMsg("Done", benefitRequestService.setBenefitRequestExtraDetailsById(benefitRequests))
                :
                ErrorMsg("No benefits found");
    }

    @Override
    public Response getAllBenefits(long mssUserId) {
        activityTrailService.logActivityTrail(mssUserId, "Viewed all claims");

        List<BenefitRequest> benefitRequests = benefitRequestService.getAll();

        if (benefitRequests != null) {
            for (BenefitRequest b :
                    benefitRequests) {
                b.setShortCreatedDate(b.getDateCreated().format(DateTimeFormatter.ofPattern("MMM dd, uuuu")));
//                benefitRequests1.add(b);
            }
            return SuccessMsg("Success", benefitRequestService.setBenefitRequestExtraDetails(benefitRequests));
        }
        return ErrorMsg("No benefits found");
    }

    @Override
    public Response getAllUnAuthorizedCrmBenefits(long mssUserId) {
        activityTrailService.logActivityTrail(mssUserId, "Viewed all unauthorized claims");

        List<BenefitRequest> benefitRequests = benefitRequestService
                .getAll()
                .stream()
                .filter(BenefitRequest::isApproved)
                .filter(BenefitRequest::isCertify)
//                .filter(benefitRequest -> benefitRequest.isApproved() && benefitRequest.isCertify() &&  benefitRequest.isAuthorize() && !benefitRequest.isAuthorizeByCrm())
                .collect(Collectors.toList());
        return benefitRequests.size() > 0 ?
                SuccessMsg("Done", benefitRequestService.setBenefitRequestExtraDetails(benefitRequests))
                :
                ErrorMsg("No benefits found");
    }

    @Override
    public Response saveBenefitsFM(@Valid PostFormByIdVM postFormByIdVM) {

        activityTrailService.logActivityTrail(postFormByIdVM.getUserId(), "Forwarded claims to fundmaster");

        //check if benefit is approved, certified and authorized
        BenefitRequest benefitRequest = benefitRequestRepository.find(postFormByIdVM.getId());
        //check if  benefit is available
        return saveBenefitsFM_(postFormByIdVM, benefitRequest);
    }

    /**
     * Avoid re-querying of database
     *
     * @param benefitRequest b
     * @return Response r
     */
    Response saveBenefitsFM_(PostFormByIdVM postFormByIdVM, BenefitRequest benefitRequest) {
        if (benefitRequest != null) {
            if (!benefitRequest.isCertify()) {
                return ErrorMsg("Benefit not certified");
            }
            if (!benefitRequest.isApproved()) {
                return ErrorMsg("Benefit not approved");
            }
            if (!benefitRequest.isAuthorize()) {
                return ErrorMsg("Benefit not authorized");
            }

            Config activeConfig = configRepository.getActiveConfig();
            FmListDTO fmListDTO;
            if (activeConfig.getClient().equals(Clients.ETL)) {
                BenefitsToFMVMETL benefitsToFMVM = fmcrmClient.buildBenefitsObjectForFmETL(postFormByIdVM.getId());
                fmListDTO = fmcrmClient.saveBenefitsFMETL(benefitsToFMVM);
            } else {
                BenefitsToFMVM benefitsToFMVM = fmcrmClient.buildBenefitsObjectForFm(postFormByIdVM.getId());
                fmListDTO = fmcrmClient.saveBenefitsFM(benefitsToFMVM);
            }

            if (fmListDTO != null) {
                if (fmListDTO.isSuccess()) {
                    //update db
                    benefitRequest.setSendToXi(true);
                    benefitRequest.setStatus(Status.PUSHED_TO_FUNDMASTER);
                    benefitRequestRepository.edit(benefitRequest);

                    User user = userService.getUserFromFundmaster(benefitRequest.getMemberId());
                    if (user != null) {
                        notificationService.sendNotification(
                                user,
                                EmailTemplatesEnum.CLAIM_STATUS,
                                String.valueOf(benefitRequest.getId()),
                                "Forwarded for processing"
                        );
                    }

                    return Response.status(Response.Status.OK)
                            .entity(SuccessVM.builder().success(true).msg("Benefit pushed to fundmaster for processing").build())
                            .build();

                }
                benefitRequest.setSendToXi(false);
                benefitRequest.setAuthorize(false);
                benefitRequest.setDeclined(true);
                benefitRequest.setStatus(Status.DECLINED_BY_CRM);
                benefitRequest.setCRMDeclineReason(fmListDTO.getMessage());
                benefitRequestRepository.edit(benefitRequest);
                return ErrorMsg(fmListDTO.getMessage());
            }
            return ErrorMsg("Failed to save benefit to core system");

        }
        return ErrorMsg("Benefit not found");
    }

    @Override
    public Response approveBenefits(@Valid PostFormByIdVM postFormByIdVM) {

        activityTrailService.logActivityTrail(postFormByIdVM.getUserId(), "Approved claims");

        BenefitRequest benefitRequest = benefitRequestRepository.find(postFormByIdVM.getId());
        //check if  benefit is available
        if (benefitRequest != null) {
            if (benefitRequest.isApproved()) {
                return SuccessMsg("Benefit is Already approved!", null);
            }
            if (benefitRequest.isCertify()) {
                benefitRequest.setApproved(true);
                benefitRequest.setDeclined(false);
                benefitRequest.setStatus(Status.APPROVED);
                benefitRequest.setDateApproved(LocalDateTime.now());
                benefitRequest.setApprovedById(postFormByIdVM.getUserId());
                benefitRequestRepository.edit(benefitRequest);
                User user = userService.getUserFromFundmaster(benefitRequest.getMemberId());
                if (user != null) {
                    notificationService.sendNotification(
                            user,
                            EmailTemplatesEnum.CLAIM_STATUS,
                            String.valueOf(benefitRequest.getId()),
                            "successfully approved"
                    );
                }

                //send email to crm
                long schemeId = user.getUserDetails().getSchemeId();
                userService.sendCrmEMail(schemeId, EmailTemplatesEnum.CLAIM_INITIATED, user.getUserDetails().getName());


                return SuccessMsg("benefit approved", null);
            }
            return ErrorMsg("Benefit not Certified");

        }
        return ErrorMsg("Benefit not found");
    }

    @Override
    public Response certifyBenefits(@Valid PostFormByIdVM postFormByIdVM) {
        //List<BenefitRequest> benefitRequests = benefitRequestService.getAll();
//       User user= userService.getUserByMemberId(userRepository.find(benefitRequest.getMemberId());

//        User user = userService.getUserByMemberId(benefitRequest.getMemberId());
        activityTrailService.logActivityTrail(postFormByIdVM.getUserId(), "Certified claims");

        BenefitRequest benefitRequest = benefitRequestRepository.find(postFormByIdVM.getId());


        //check if  benefit is available
        if (benefitRequest != null) {

            Config activeConfig = configRepository.getActiveConfig();
            if (activeConfig.getClient().equals(Clients.ETL)) {
                SponsorConfig sponsorConfig = sponsorConfigRepository.getActiveConfig();
                if (sponsorConfig.getAuthorizationLevel().equals(AuthorizationLevel.LEVEL_TWO)) {
                    benefitRequest.setApproved(true);
                    benefitRequest.setApprovedById(postFormByIdVM.getUserId());
                    benefitRequest.setStatus(Status.APPROVED);
                    benefitRequest.setDateApproved(LocalDateTime.now());
                    benefitRequest.setCertify(true);
                    benefitRequest.setStatus(Status.CERTIFIED);
                    benefitRequest.setCertifiedBy(postFormByIdVM.getUserId());
                    benefitRequest.setDateCertified(LocalDateTime.now());
                    benefitRequestRepository.edit(benefitRequest);
                    User user = userService.getUserFromFundmaster(benefitRequest.getMemberId());
                    if (user != null) {
                        notificationService.sendNotification(
                                user,
                                EmailTemplatesEnum.CLAIM_STATUS,
                                String.valueOf(benefitRequest.getId()),
                                "Successfully Certified"
                        );
                    }

                    return SuccessMsg("benefit certified and approved", null);

                }

            }
            if (benefitRequest.isCertify()) {
                return SuccessMsg("Benefit is Already certified!", null);
            }

            //check if benefit is approved
            benefitRequest.setCertify(true);
            benefitRequest.setStatus(Status.CERTIFIED);
            benefitRequest.setCertifiedBy(postFormByIdVM.getUserId());
            benefitRequest.setDateCertified(LocalDateTime.now());
            benefitRequestRepository.edit(benefitRequest);
            User user = userService.getUserFromFundmaster(benefitRequest.getMemberId());
            if (user != null) {
                notificationService.sendNotification(
                        user,
                        EmailTemplatesEnum.CLAIM_STATUS,
                        String.valueOf(benefitRequest.getId()),
                        "Successfully Certified"
                );
            }

            return SuccessMsg("benefit certified", null);


        }
        return ErrorMsg("Benefit not found");
    }

    @Override
    public Response authorizeBenefits(@Valid PostFormByIdVM postFormByIdVM) {

        activityTrailService.logActivityTrail(postFormByIdVM.getUserId(), "Authorized claims");

        BenefitRequest benefitRequest = benefitRequestRepository.find(postFormByIdVM.getId());
        if (benefitRequest == null)
            return ErrorMsg("Benefit not found");
        //check if  benefit is available

        if (benefitRequest.isAuthorize()) {
            return ErrorMsg("Benefit is Already authorized!");
        }
        //check if benefit is approved and certified
        if (!benefitRequest.isCertify()) {
            return ErrorMsg("Benefit not certified");
        }
        if (!benefitRequest.isApproved()) {
            return ErrorMsg("Benefit not approved");
        }

        benefitRequest.setAuthorize(true);
        benefitRequest.setStatus(Status.AUTHORIZED);
        benefitRequest.setAuthorizedByCrmId(postFormByIdVM.getUserId());
        benefitRequest.setDateApprovedByCrm(LocalDateTime.now());
        benefitRequest.setAuthorizedById(postFormByIdVM.getUserId());
        benefitRequest.setDateAuthorize(LocalDateTime.now());
        benefitRequestRepository.edit(benefitRequest);
        User user = userService.getUserFromFundmaster(benefitRequest.getMemberId());
        if (user != null) {
            notificationService.sendNotification(
                    user,
                    EmailTemplatesEnum.CLAIM_STATUS,
                    String.valueOf(benefitRequest.getId()),
                    "Successfully Authorized"
            );
        }

        return SuccessMsg("Benefit authorized", null);

    }

    @Override
    public Response sponsorDeclineBenefits(@Valid BenefitDeclineVM benefitDeclineVM) {
        // User user= userService.getUserByEmail(String.valueOf(userRepository.findOneByEmail(benefitRequest.getEmail())));

        activityTrailService.logActivityTrail(benefitDeclineVM.getUserId(), "Declined claims");

        BenefitRequest benefitRequest = benefitRequestRepository.find(benefitDeclineVM.getId());
        //check if  benefit is available
        if (benefitRequest != null) {
            benefitRequest.setDeclined(true);
            benefitRequest.setApproved(false);
            benefitRequest.setAuthorize(false);
            benefitRequest.setCertify(false);
            benefitRequest.setStatus(Status.DECLINED_BY_SPONSOR);
            benefitRequest.setDateDeclined(LocalDateTime.now());
            benefitRequest.setSponsorDeclineReason(benefitDeclineVM.getReason());
            benefitRequest.setDeclinedById(benefitDeclineVM.getUserId());
            benefitRequestRepository.edit(benefitRequest);
            User user = userService.getUserFromFundmaster(benefitRequest.getMemberId());
            if (user != null) {
                notificationService.sendNotification(
                        user,
                        EmailTemplatesEnum.CLAIM_STATUS,
                        String.valueOf(benefitRequest.getId()),
                        "declined because " + benefitDeclineVM.getReason()
                );
            }


            return Response.ok()
                    .entity(SuccessVM
                            .builder()
                            .success(true)
                            .msg("benefit declined")
                            .build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM
                        .builder()
                        .success(false)
                        .msg("Benefit not found")
                        .build())
                .build();
    }

    @Override
    public Response CRMDeclineBenefits(@Valid BenefitDeclineVM benefitDeclineVM) {

        activityTrailService.logActivityTrail(benefitDeclineVM.getUserId(), "Declined claims");

        BenefitRequest benefitRequest = benefitRequestRepository.find(benefitDeclineVM.getId());
        //check if  benefit is available
        if (benefitRequest != null) {
            benefitRequest.setDeclined(true);
            benefitRequest.setApproved(false);
            benefitRequest.setAuthorize(false);
            benefitRequest.setCertify(false);
            benefitRequest.setStatus(Status.DECLINED_BY_CRM);
            benefitRequest.setDateDeclined(LocalDateTime.now());
            benefitRequest.setCRMDeclineReason(benefitDeclineVM.getReason());
            benefitRequest.setDeclinedById(benefitDeclineVM.getUserId());
            benefitRequestRepository.edit(benefitRequest);
            User user = userService.getUserFromFundmaster(benefitRequest.getMemberId());
            if (user != null) {
                notificationService.sendNotification(
                        user,
                        EmailTemplatesEnum.CLAIM_STATUS,
                        String.valueOf(benefitRequest.getId()),
                        "declined because " + benefitDeclineVM.getReason()
                );
            }


            return Response.ok()
                    .entity(SuccessVM
                            .builder()
                            .success(true)
                            .msg("benefit declined")
                            .build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM
                        .builder()
                        .success(false)
                        .msg("Benefit not found")
                        .build())
                .build();
    }

    @Override
    public Response getBenefitByid(long id) {
        BenefitRequest benefitRequest = benefitRequestRepository.find(id);
        if (benefitRequest != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(benefitRequestService.setBenefitRequestExtraDetailsById(benefitRequest)).build())
                    .build();

        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("benefit Not found").build())
                .build();
    }

    @Override
    public Response getBenefitByMemberNo(String memberNO) {
        List<BenefitRequest> benefitRequests = benefitRequestService.getAll();
        return benefitRequests != null ?
                Response
                        .ok()
                        .entity(
                                SuccessVM.builder()
                                        .success(true)
                                        .data(benefitRequestService.setBenefitRequestExtraDetails(benefitRequests)
                                                .stream()
                                                .filter(((benefitRequest) -> benefitRequest.getMemberNo().equals(memberNO))).collect(Collectors.toList()))
                                        .build())
                        .build()
                :
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(ErrorVM.builder().success(false).msg("No benefits found").build())
                        .build();
    }

    @Override
    public Response benefitAuthorizerAuthorizeAndPushBenefitsToFM(PostFormByIdVM postFormByIdVM) {

        activityTrailService.logActivityTrail(postFormByIdVM.getUserId(), "Authorized and forwarded claims to Fundmaster");

        BenefitRequest benefitRequest = benefitRequestRepository.find(postFormByIdVM.getId());
        if (benefitRequest == null)
            return ErrorMsg("Benefit not found");
        User user = userService.getUserFromFundmaster(benefitRequest.getMemberId());
        //check if  benefit is available
        //check if benefit is approved and certified
        if (!benefitRequest.isCertify()) {
            return ErrorMsg("Benefit not certified");
        }
        if (!benefitRequest.isApproved()) {
            return ErrorMsg("Benefit not approved");
        }
        benefitRequest.setAuthorizeByCrm(true);
        benefitRequest.setAuthorize(true);
        benefitRequest.setStatus(Status.AUTHORIZED);
        benefitRequest.setAuthorizedById(postFormByIdVM.getUserId());
        benefitRequest.setDateAuthorize(LocalDateTime.now());
        benefitRequest.setAuthorizedByCrmId(postFormByIdVM.getUserId());
        benefitRequest.setDateApprovedByCrm(LocalDateTime.now());
        benefitRequest.setCrmUser(user.getFirstName());
        benefitRequestRepository.edit(benefitRequest);

        //call save method
        return saveBenefitsFM(postFormByIdVM);

    }

    @Override
    public Response memberAuthorizeAndPushBenefitsToFM(PostFormByIdVM postFormByIdVM) {

        activityTrailService.logActivityTrail(postFormByIdVM.getUserId(), "Approve and forwarded claims to Fundmaster");
        BenefitRequest benefitRequest = benefitRequestRepository.find(postFormByIdVM.getId());
        if (benefitRequest == null)
            return ErrorMsg("Benefit not found");
        User user = userService.getUserFromFundmaster(benefitRequest.getMemberId());
        //check if  benefit is available
        //check if benefit is approved and certified
        if (!benefitRequest.isCertify()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ErrorVM
                            .builder()
                            .success(false)
                            .msg("Benefit not certified")
                            .build())
                    .build();
        }
        if (!benefitRequest.isApproved()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ErrorVM
                            .builder()
                            .success(false)
                            .msg("Benefit not approved")
                            .build())
                    .build();
        }
        if (!benefitRequest.isAuthorize()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ErrorVM
                            .builder()
                            .success(false)
                            .msg("Benefit not authorized")
                            .build())
                    .build();
        }

//        benefitRequest.setApproved(true);
//        benefitRequest.setApprovedById(postFormByIdVM.getUserId());
//        benefitRequest.setDateApproved(LocalDateTime.now());
//        benefitRequest.setCertify(true);
//        benefitRequest.setCertifiedBy(postFormByIdVM.getUserId());
//        benefitRequest.setDateCertified(LocalDateTime.now());
//        benefitRequest.setAuthorizeByCrm(true);
//        benefitRequest.setAuthorize(true);
        benefitRequest.setIsApprovedByMember(YesNo.YES);
        benefitRequest.setStatus(Status.APPROVED_BY_MEMBER);
//        benefitRequest.setAuthorizedById(postFormByIdVM.getUserId());
        benefitRequest.setIsPostedToFm(YesNo.YES);
//        benefitRequest.setDateApproved(LocalDateTime.now());

//        benefitRequest.setCrmUser(user.getFirstName());
        benefitRequestRepository.edit(benefitRequest);

        //call save method
        return saveBenefitsFM(postFormByIdVM);

    }

    @Override
    public Response authorizeAndPushBenefitsToFM(@Valid PostFormByIdVM postFormByIdVM) {
        activityTrailService.logActivityTrail(postFormByIdVM.getUserId(), "Authorized and forwarded claims to Fundmaster");
        BenefitRequest benefitRequest = benefitRequestRepository.find(postFormByIdVM.getId());
        if (benefitRequest == null)
            return ErrorMsg("Benefit not found");
        User user = userService.getUserFromFundmaster(benefitRequest.getMemberId());
        //check if  benefit is available
        //check if benefit is approved and certified
        if (!benefitRequest.isCertify()) {
            return ErrorMsg("Benefit not certified");
        }
        if (!benefitRequest.isApproved()) {
            return ErrorMsg("Benefit not approved");
        }

        benefitRequest.setAuthorize(true);
        benefitRequest.setAuthorizedById(postFormByIdVM.getUserId());
        benefitRequest.setAuthorizeByCrm(true);
        benefitRequest.setDateAuthorize(LocalDateTime.now());
        benefitRequest.setStatus(Status.AUTHORIZED_BY_CRM);
        benefitRequest.setAuthorizedByCrmId(postFormByIdVM.getUserId());
        benefitRequest.setDateApprovedByCrm(LocalDateTime.now());
        benefitRequest.setCrmUser(user.getUserDetails().getName());
        benefitRequestRepository.edit(benefitRequest);

        //call save method
        return saveBenefitsFM_(postFormByIdVM, benefitRequest);
    }

    @Override
    public Response authorizeAndPushBenefitsToFMPO(@Valid PostFormByIdVM postFormByIdVM) {
        BenefitRequest benefitRequest = benefitRequestRepository.find(postFormByIdVM.getId());
        if (benefitRequest != null) {
            benefitRequest.setApproved(true);
            benefitRequest.setDeclined(false);
            benefitRequest.setStatus(Status.APPROVED);
            benefitRequest.setDateApproved(LocalDateTime.now());
            benefitRequest.setApprovedById(postFormByIdVM.getUserId());
            benefitRequest.setCertify(true);
            benefitRequest.setStatus(Status.CERTIFIED);
            benefitRequest.setCertifiedBy(postFormByIdVM.getUserId());
            benefitRequest.setDateCertified(LocalDateTime.now());
            benefitRequest.setAuthorize(true);
            benefitRequest.setStatus(Status.AUTHORIZED);
            benefitRequest.setAuthorizedById(postFormByIdVM.getId());
            benefitRequest.setDateAuthorize(LocalDateTime.now());

            benefitRequestRepository.edit(benefitRequest);

            //call save method
            return saveBenefitsFM(postFormByIdVM);

        }
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response getRecentClaims(int start, int size) {
        List<BenefitRequest> benefitRequests = benefitRequestService.getAll()
                .stream().filter(benefitRequest -> benefitRequest.isApproved() && benefitRequest.isCertify() && !benefitRequest.isAuthorize()).collect(Collectors.toList());
        if (benefitRequests.size() > 0) {
            if (benefitRequests.size() > 6) {
                return Response
                        .ok()
                        .entity(
                                SuccessVM.builder()
                                        .success(true)
                                        .data(benefitRequestService.setBenefitRequestExtraDetails(benefitRequests.subList(start, size)))
                                        .build())
                        .build();
            } else {
                return Response
                        .ok()
                        .entity(
                                SuccessVM.builder()
                                        .success(true)
                                        .data(benefitRequestService.setBenefitRequestExtraDetails(benefitRequests))
                                        .build())
                        .build();
            }
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg("No benefits found").build())
                .build();
    }

    @Override
    public Response getCountOfPendingClaims() throws IOException {
        long count = benefitRequestService.getCountOfPendingClaims();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("count", count)
                .build();
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).data(objectMapper.readValue(jsonObject.toString(), Object.class)).build())
                .build();
    }

    @Override
    public Response SearchBenefitRequest(String name) {
        return Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(benefitRequestService.setBenefitRequestExtraDetailsById(benefitRequestRepository.searchABenefitRequest(name)))
                                .build()
                ).build();
    }

    @Override
    public Response getMemberEditedClaims(long mssUserId, long memberId) {

        activityTrailService.logActivityTrail(mssUserId, "get member edited claims");

        List<BenefitRequest> memberEditedClaims = benefitRequestRepository.getMemberEditedClaims(memberId);
        if (memberEditedClaims == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ErrorVM.builder().success(false).msg("No benefits found").build())
                    .build();
        }
        return Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(benefitRequestService.setBenefitRequestExtraDetailsById(memberEditedClaims))
                                .build()
                ).build();
    }

    @Override
    public Response approveMemberClaim(long mssUserId, long benefitId) {
        activityTrailService.logActivityTrail(mssUserId, "approved member claims of id " + benefitId);

        BenefitRequest benefitRequest = benefitRequestRepository.find(benefitId);
        if (benefitRequest != null) {
            benefitRequest.setIsEdited(YesNo.YES);
//            benefitRequest.setApproved(true);
//            benefitRequest.setAuthorize(true);
//            benefitRequest.setCertify(true);
//            benefitRequest.setDateApproved(LocalDateTime.now());
//            benefitRequest.setDateCertified(LocalDateTime.now());
//            benefitRequest.setDateAuthorize(LocalDateTime.now());
//            benefitRequest.setApprovedById(mssUserId);
//            benefitRequest.setAuthorizedById(mssUserId);
//            benefitRequest.setCertifiedBy(mssUserId);
            benefitRequest.setIsApprovedByMember(YesNo.YES);
            benefitRequest.setStatus(Status.APPROVED_BY_MEMBER);
            benefitRequestRepository.edit(benefitRequest);
            return Response.ok()
                    .entity(
                            SuccessVM.builder()
                                    .success(true)
                                    .msg("Benefit Approved Successfully")
                                    .build()
                    ).build();
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg("No benefits found").build())
                .build();
    }

    @Override
    public Response getClaimDocuments(long mssUserId, long claimId) {
        logActivityTrail(mssUserId, "getting claim documents for ID " + claimId);
        BenefitRequest benefitRequest=benefitRequestRepository.find(claimId);
        if (benefitRequest!=null) {
            List<ClaimDocuments> claimDocuments = claimDocumentsRepository.getByClaimIdAndReasonForExit(claimId,
                    benefitRequest.getBenefitReason());
            if (claimDocuments == null) {
                claimDocuments = new ArrayList<>();
            }
            claimDocuments = ClaimDocuments.format(claimDocuments);
            return SuccessMsg("Done", claimDocuments);
        }
        return ErrorMsg("Claim not found");
    }

    @Override
    public Response editMemberClaim(long mssUserId, long benefitId, String amount) {

        activityTrailService.logActivityTrail(mssUserId, "edited member claims of id " + benefitId);

        BenefitRequest benefitRequest = benefitRequestRepository.find(benefitId);
        double amt;
        if (benefitRequest != null) {
            if (amount.contains("%")) {
                String amnt = amount.substring(0, amount.length() - 1);

                amt = Long.parseLong(amnt);
                benefitRequest.setAmountPercentage(amt);

            } else {
                amt = Double.parseDouble(amount.replaceAll(",",""));
                benefitRequest.setTotalAmount(amt);
            }
            benefitRequest.setIsEdited(YesNo.YES);
            benefitRequest.setStatus(Status.EDITED);
            benefitRequest.setDateCreated(LocalDateTime.now());
            User user = userService.getUserFromFundmaster(benefitRequest.getMemberId());
            //send notifications
            notificationService.sendNotification(
                    user,
                    EmailTemplatesEnum.CLAIM_STATUS,
                    String.valueOf(benefitRequest.getId()),
                    "Successfully Edited"
            );
            benefitRequestRepository.edit(benefitRequest);
            return Response.ok()
                    .entity(
                            SuccessVM.builder()
                                    .success(true)
                                    .msg("Benefit Edited Successfully")
                                    .build()
                    ).build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg("No benefits found").build())
                .build();
    }

    @Override
    public Response getClaimUploadedDocs(long mssUserId, long claimId) {
        logActivityTrail(mssUserId, "fetched documents of claim id " + claimId);

//        List<ClaimDocuments> claimDocuments=claimDocumentsService.getClaimDocuments(claimId);
        BenefitRequest benefitRequest = benefitRequestRepository.find(claimId);
        if (benefitRequest != null) {
            List<ClaimDocuments> claimDocuments = claimDocumentsRepository.getUploadedClaimDocuments(claimId,
                    benefitRequest.getBenefitReason());
            List<Documents> documentsList = new ArrayList<>();
            if (claimDocuments!=null && !claimDocuments.isEmpty()) {
                for (ClaimDocuments claimDocuments1 : claimDocuments) {
                    Documents documents = documentRepository.find(claimDocuments1.getDocumentId());
                    documentsList.add(documents);
                }
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true)
                                .data(setExtraInfo(documentsList)).build())
                        .build();
            }
            return NotFoundErrorMsg("No documents found");
        }
        return ErrorMsg("Benefit not found");
    }
}
