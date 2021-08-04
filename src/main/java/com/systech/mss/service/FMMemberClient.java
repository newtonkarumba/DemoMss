package com.systech.mss.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.config.APICall;
import com.systech.mss.controller.vm.*;
import com.systech.mss.domain.Config;
import com.systech.mss.domain.MssMemberDTO;
import com.systech.mss.domain.StageContribution;
import com.systech.mss.domain.common.Clients;
import com.systech.mss.repository.ConfigRepository;
import com.systech.mss.service.dto.*;
import com.systech.mss.util.StringUtil;
import com.systech.mss.vm.UploadMemberDocumentVM;
import com.systech.mss.vm.benefitrequest.MakeContributionStkVM;
import org.json.JSONArray;
import org.json.JSONException;
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
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@RequestScoped
public class FMMemberClient {
    private Client client;
    private WebTarget target;
    @Inject
    private ConfigRepository configRepository;
    Config config;
    private MultivaluedMap<String, Object> myHeaders;

    @Inject
    Logger log;

    @PostConstruct
    public void setup() {
        client = ClientBuilder.newClient();
        if (getFMConfig().isPresent()) {
            config = getFMConfig().get();
            myHeaders = new MultivaluedHashMap<>();
            myHeaders.add("username", config.getFmUsername());
            myHeaders.add("password", config.getFmPassword());
            target = client.target(config.getFmBasePath()); //http:168.235.82.130:8084/Xe/api
        }
    }

    private Optional<Config> getFMConfig() {
        return configRepository
                .findAll()
                .stream()
                .findFirst();
    }

    public FmListDTO getMemberDetails(long memberId) {
        try {
            Response response = target.path(APICall.GET_MEMBER_DETAILS)
                    .path(String.valueOf(memberId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
            return FmListDTO.builder().success(false).build();
        }
    }

    public MemberDetailsVM getMemberDetailsSingle(long memberId){
        try {
            FmListDTO fmListDTO = getMemberDetails(memberId);
            if (fmListDTO != null && fmListDTO.isSuccess()) {
                Object membersDetails = fmListDTO.getRows().get(0);
                MemberDetailsVM memberDetailsVM;
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonString = objectMapper.writeValueAsString(membersDetails);
                memberDetailsVM = objectMapper.readValue(jsonString, MemberDetailsVM.class);
                return memberDetailsVM;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FmListDTO getSummary(long memberId) {
        try {
            Response response = target.path(APICall.GET_MEMBER_CONTRIBUTION_TOTALS)
                    .path(String.valueOf(memberId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return FmListDTO.builder().success(false).build();
        }
    }

    public FmListDTO getClosingBalances(long memberId) {
        try {
            Response response = target.path(APICall.GET_MEMBER_CLOSING_BALANCES)
                    .path(String.valueOf(memberId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
//            log.error(response.readEntity(String.class));
            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return FmListDTO.builder().success(false).build();
        }
    }

    public FmListDTO getBeneficiaries(long memberId) {
        try {
            Response response = target.path(APICall.GET_MEMBER_BENEFICIARIES)
                    .path(String.valueOf(memberId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return FmListDTO.builder().success(false).build();
        }
    }

    public MessageModelDTO addBeneficiary(JSONObject jsonObject) {
        try {
            Response response = target.path(APICall.SAVE_OR_UPDATE_BENEFICIARY_DETAILS)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.json(jsonObject));
//                    .post(Entity.entity(addBeneficiaryVM, MediaType.APPLICATION_JSON));
//            log.error(response.readEntity(String.class));
            return response.readEntity(MessageModelDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return MessageModelDTO.builder().success(false).build();
        }
    }

    public FmListDTO getContributions(long memberId, long schemeId) {
        try {
            Response response = target.path(APICall.GETMEMBERCONTRIBUTIONS + getParams(memberId, schemeId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return FmListDTO.builder().success(false).build();
        }
    }

    public FmListDTO filterContributions(long memberId, String fromDate, String toDate) {
        try {
            String url = APICall.GET_CONTRIBUTIONS_BTWN_DATES + getParams(memberId, fromDate, toDate);
//            log.error(url);
            Response response = target.path(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return FmListDTO.builder().success(false).build();
        }
    }

    private String getStartLimit(int start, int limit) {
        return "?start=" + start + "&size=" + limit;
    }

    public MessageModelDTO makeContribution(MakeContributionStkVM makeContributionStkVM) {
        try {
            Response response = target.path(APICall.MAKE_CONTRIBUTION_PAYMENT_VIA_PORTAL)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.entity(makeContributionStkVM, MediaType.APPLICATION_JSON));
            return response.readEntity(MessageModelDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return MessageModelDTO.builder().success(false).build();
        }
    }

    public MemberBalanceDTO getBalances(long memberId) {
        try {
            Response response = target.path(APICall.GET_DC_MEMBER_BALANCES)
                    .path(String.valueOf(memberId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(MemberBalanceDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return MemberBalanceDTO.builder().success(false).message("Please try again").build();
        }
    }

    public Response getClaims() {
        return null;
    }

    public Response projections() {
        return null;
    }

    public Response changePwd() {
        return null;
    }

    public FmListDTO getReasonsForExit() {
        try {
            Response response = target.path(APICall.SCHEME_GET_REASONS_FOR_EXIT + "All")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
//            String s = response.readEntity(String.class);
//            log.error(s);
            return response.readEntity(FmListDTO.class);
//
        } catch (Exception e) {
            log.error(e.getMessage());
            return FmListDTO.builder().success(false).build();
        }
    }

    public FmListDTO getMemberSchemes(String identifierValue, String email, String profile) {
        try {
            String params = getParams(identifierValue, email, profile);
            Response response = target.path(APICall.GET_MEMBER_SCHEMES + params)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return FmListDTO.builder().success(false).build();
        }
    }

    public FmListDTO getMemberDetailsBySchemeAndEmail(long schemeId, String email) {
        try {
            String params = getParams(schemeId, email);
            Response response = target.path(APICall.GET_MEMBER_DETAILS_BY_SCHEME_AND_EMAIL + params)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return FmListDTO.builder().success(false).build();
        }
    }

    public org.json.JSONObject getMemberDetailsByScheme(long schemeId, String email) {
        try {
            log.error("API /getmemberdetailsbyschemeandemail");
            String url = getParams(schemeId, email);
            url = APICall.GET_MEMBER_DETAILS_BY_SCHEME_AND_EMAIL + url;
            log.error(url);
            Response response = target.path(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
//            log.error(s);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BaseDTO calculateWhatIfAnalysis(long schemeId, long memberId, String avcReceiptOption, int ageAtExit, double returnRate, double salaryEscalationRate, long projectedAvc, long inflationRate) {
        try {
            String params = getParams(schemeId, memberId, avcReceiptOption, ageAtExit, returnRate, salaryEscalationRate, projectedAvc, inflationRate);
            Response response = target.path(APICall.CALCULATE_WHATIF_ANALYSIS + params)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            try {
                return response.readEntity(FmListDTO.class);
            } catch (Exception e) {
                log.error(e.getMessage());
                return response.readEntity(ErrorMsgDTO.class);
            }
        } catch (Exception e) {
            return null;
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

    public String getMemberLoans(long memberId) {
        try {
            Response response = target.path(APICall.GET_MEMBER_LOANS)
                    .path(String.valueOf(memberId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(String.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public StringListDTO getMissingDocuments(long memberId) {
        try {
            Response response = target.path(APICall.GET_MISSING_DOCUMENTS)
                    .path(String.valueOf(memberId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(StringListDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return StringListDTO.builder().success("false").build();
        }
    }

    public StringListDTO getSubmittedDocuments(long memberId) {
        try {
            Response response = target.path(APICall.GET_SUBMITTED_DOCUMENTS)
                    .path(String.valueOf(memberId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
//            log.error(response.readEntity(String.class));
            return response.readEntity(StringListDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return StringListDTO.builder().success("false").build();
        }
    }

    public BenefitReasonDTO getReasonForExitById_(long id) {
        try {
            Response response = target.path(APICall.GET_REASON_FOR_EXIT_BY_ID + id)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(BenefitReasonDTO.class);
        } catch (Exception e) {
//            log.error(e.getMessage());
            return BenefitReasonDTO.builder().success(false).build();
        }
    }

    public Map<String, Object> getReasonForExitById(long reasonId) {
        try {
            FmListDTO fmListDTO = getReasonsForExit();
            if (fmListDTO.isSuccess()) {
                List<Object> objects = fmListDTO.getRows();
                for (Object o :
                        objects) {
                    String s = StringUtil.toJsonString(o);
                    if (s != null) {
                        org.json.JSONObject jsonObject = new org.json.JSONObject(
                                s
                        );
                        if (jsonObject.getLong("id") == reasonId) {
                            Map<String, Object> map = new HashMap<>();
                            Iterator iterator = jsonObject.keys();
                            while (iterator.hasNext()) {
                                String key = StringUtil.toString(iterator.next());
                                map.put(key, jsonObject.get(key));
                            }
//                        map.put("description", jsonObject.get("description"));
//                        map.put("maximumDateOfExit", jsonObject.get("maximumDateOfExit"));
//                        map.put("ignoreVestingScalesEntitlement", jsonObject.get("ignoreVestingScalesEntitlement"));
//                        map.put("enforceRegulatoryThreshold", jsonObject.get("enforceRegulatoryThreshold"));
//                        map.put("useFullYearsInService", jsonObject.get("useFullYearsInService"));
//                        map.put("attestationMonthsLimit", jsonObject.get("attestationMonthsLimit"));
//                        map.put("allowPaymentProcess", jsonObject.get("allowPaymentProcess"));
//                        map.put("requiresAttestation", jsonObject.get("requiresAttestation"));
//                        map.put("monthsForGroupLifeComputation", jsonObject.get("monthsForGroupLifeComputation"));
//                        map.put("memberTransferType", jsonObject.get("memberTransferType"));
//                        map.put("isTransfer", jsonObject.get("isTransfer"));
//                        map.put("taxHeaderId", jsonObject.get("taxHeaderId"));
//                        map.put("externalAccCode", jsonObject.get("externalAccCode"));
//                        map.put("mbshipStatus", jsonObject.get("mbshipStatus"));
//                        map.put("annuityProviderMandatory", jsonObject.get("annuityProviderMandatory"));
//                        map.put("earlyLate", jsonObject.get("earlyLate"));
//                        map.put("enabled", jsonObject.get("enabled"));
//                        map.put("applyDeferrement", jsonObject.get("applyDeferrement"));
//                        map.put("applyTax", jsonObject.get("applyTax"));
//                        map.put("refund", jsonObject.get("refund"));
//                        map.put("erDeferrement", jsonObject.get("erDeferrement"));
//                        map.put("eeDeferrement", jsonObject.get("eeDeferrement"));
//                        map.put("enforceVSSelectionAtExit", jsonObject.get("enforceVSSelectionAtExit"));
//                        map.put("overrideVestingScale", jsonObject.get("overrideVestingScale"));
//                        map.put("payEr", jsonObject.get("payEr"));
//                        map.put("reason", jsonObject.get("reason"));
//                        map.put("category", jsonObject.get("category"));
//                        map.put("id", jsonObject.get("id"));
//                        log.error(map.toString());
                            return map;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object memberUploadDocument(UploadMemberDocumentVM uploadMemberDocumentVM) {
        try {
            Response response = target.path(APICall.UPLOAD + getParams(
                    uploadMemberDocumentVM.getMemberId()
                    , uploadMemberDocumentVM.getDocName()
                    , uploadMemberDocumentVM.getDocNotes()
                    , uploadMemberDocumentVM.getDocNum()
                    , uploadMemberDocumentVM.getDocTypeId()
            ))
                    .request(MediaType.APPLICATION_FORM_URLENCODED)
                    .headers(myHeaders)
                    .post(Entity.entity(uploadMemberDocumentVM, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
            return response.readEntity(String.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }


    public Object addMemberbank(AddBankVM addBankVM) {
        try {
            Response response = target.path(APICall.SAVE_BANK_DETAILS + getParams(
                    addBankVM.getBankDetailsId()
                    ,addBankVM.getMemberId()
                    , addBankVM.getBankCode()
                    , addBankVM.getBankBranchCode()
                    , addBankVM.getAccountName()
                    , addBankVM.getAccountNumber()
                    ,addBankVM.getDefaultPoint()
            ))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.entity(addBankVM, MediaType.APPLICATION_JSON));
            return response.readEntity(String.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public Object checkVestingLiabilities(long memberId, long reasonForExit) {
        try {
            Response response = target.path(APICall.GET_MEMBER_VESTINGS +
                    getParams(memberId, reasonForExit))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            log.error(s);
            return s;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public Object getMemberBankDetails(long memberId) {
        try {
            Response response = target.path(APICall.GET_MEMBER_ACCOUNT_DETAILS)
                    .path(String.valueOf(memberId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(String.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public JSONObject getBalancesAsAtToday(long memberId, long schemeId) {
        try {
            String apiPath = "";
            if (config.getClient().equals(Clients.ETL)) {
                apiPath = APICall.GET_BALANCES_TODAY;
                log.info("************ etl");
            } else {
                log.info("****************** others");
                apiPath = APICall.GET_BALANCES_AS_AT_TODAY;
            }
            String url=apiPath +getParams(memberId, schemeId);
            log.error(url);
            Response response = target.path(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            log.info(s);
            JSONParser jsonParser = new JSONParser();
            return (JSONObject) jsonParser.parse(s);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public JSONObject getCurrentMonthlyContributionAndBasicSalary(long memberId, long schemeId) {
        try {
            Response response = target.path(APICall.GET_CONTRIBUTION_RATE_SALARY +
                    getParams(memberId, schemeId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            JSONParser jsonParser = new JSONParser();
            return (JSONObject) jsonParser.parse(s);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public org.json.JSONObject calculateWhatIfAnalysis_(String schemeId, String memberId, String avcReceiptOption, String ageAtExit, String returnRate, String salaryEscalationRate, String projectedAvc, String inflationRate) {
        try {
            String params = getParams(schemeId, memberId, avcReceiptOption, ageAtExit, returnRate, salaryEscalationRate, projectedAvc, inflationRate);
            Response response = target.path(APICall.CALCULATE_WHATIF_ANALYSIS + params)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
//            log.error(s);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public org.json.JSONObject getCurrentMonthlyContributionAndBasicSalary(String memberId, String schemeId) {
        try {
            String params = getParams(memberId, schemeId);
            Response response = target.path(APICall.GET_CONTRIBUTION_RATE_SALARY + params)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
//            log.error(s);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public org.json.JSONObject calculateTaxOnGrossLumpsumAndGrossPension(String schemeId, String memberId, String ageAtExit, String grossMothlyPension, String grossLumpsum) {
        try {
            String params = getParams(schemeId, memberId, ageAtExit, grossMothlyPension, grossLumpsum);
            Response response = target.path(APICall.CALCULATE_TAX + params)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
//            log.error(s);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param date     MM-dd-yyyy
     * @param schemeID l
     * @return JSONObject
     */
    public org.json.JSONObject getAccountingPeriod(String date, long schemeID) {
        try {
            String params = getParams(date, schemeID);
            Response response = target.path(APICall.GET_ACCOUNTING_PERIOD_FROM_DATE_FOR_SCHEME + params)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public org.json.JSONObject getAllAccountingPeriods(String schemeID) {
        try {
            String params = getParams(schemeID);
            Response response = target.path(APICall.GET_SCHEME_ACCOUNTING_PERIODS + params)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public org.json.JSONObject getMemberStatement(long memberId, long apId, long schemeId) {
        try {
            String params = getParams(memberId, apId, schemeId);
            Response response = target.path(APICall.GET_MEMBER_STATEMENT + params)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            log.error(s);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public org.json.JSONObject getMemberCertificate(long memberId) {
        try {
            String params = getParams(memberId);
            Response response = target.path(APICall.MEMBER_CERTIFICATE + params)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param memberId long
     * @param date     MM-dd-yyyy
     * @return JSONObject
     */
    public org.json.JSONObject sendMemberStatement(long memberId, String date) {
        try {
            String params = getParams(memberId, date, "memberStatement");
            Response response = target.path(APICall.SEND_MEMBER_STATEMENT + params)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.text("Request Statement"));
            String s = response.readEntity(String.class);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public org.json.JSONObject requestStatement(long memberId, long apId, long schemeId) {
        try {
            String params = getParams(memberId, apId, schemeId);
            Response response = target.path(APICall.GET_MEMBER_STATEMENT + params)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public org.json.JSONObject makeContributionViaPortal(String mpesaPhoneNumber, String paybill, double amount) {
//        String zero = "0";
//        String plus = "+";
//        String finalPhone = "";
//        if (mpesaPhoneNumber.startsWith(zero))
//            finalPhone = "254" + mpesaPhoneNumber.substring(1);
////            finalPhone = "254" + mpesaPhoneNumber.substring(1);
//        else if (mpesaPhoneNumber.startsWith(plus))
//            finalPhone = mpesaPhoneNumber.substring(1);
//        else
//            finalPhone = mpesaPhoneNumber;
        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("phoneNo", mpesaPhoneNumber);
        params.put("paybill", paybill);
        params.put("account_reference", mpesaPhoneNumber);

        try {
            Response response = target.path(APICall.MAKE_CONTRIBUTION_PAYMENT_VIA_PORTAL)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.json(params));
            String s = response.readEntity(String.class);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public org.json.JSONObject postContributionMembersAccount(long schemeId, long memberId, BigDecimal amount, String transactionTime) {
        try {
            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("schemeId", (schemeId));
            jsonObject.put("memberId", (memberId));
            jsonObject.put("amount", (amount));
            jsonObject.put("transactionTime", transactionTime);  //yyyy-MM-dd

            Response response = target.path(APICall.POST_CONTRIBUTION_TO_MEMBERAC)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.json(jsonObject));
            String s = response.readEntity(String.class);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public org.json.JSONObject confirmContributionPaymentViaPortal(StageContribution stageContribution) {

        Map<String, Object> params = new HashMap<>();
        boolean status;
        params.put("request_id", stageContribution.getRequestId());
        try {
            Response response = target.path(APICall.CONFIRM_CONTRIBUTION_PAYMENT_VIA_PORTAL)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.json(params));
            String s = response.readEntity(String.class);
            return new org.json.JSONObject(s);
//            try {
//                if (response.get("success") instanceof Boolean) status = response.getBoolean("success");
//                else status = false;
//                if (status) {
//                    String amount = response.getString("amount");
//                    String transactionTime = response.getString("transaction_date").split(" ")[0];
//                    response = postContributionMembersAccount(helper.toString(stageContribution.getSchemeId()), helper.toString(stageContribution.getMemberId()), amount, transactionTime);
//                    boolean statusII = response.getBoolean(Fields.SUCCESS);
//                    if (statusII) {
//                        stageContribution.setSendToXi(true);
//                        stageContributionBeanI.edit(stageContribution);
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//                return null;
//            }
//            return response;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public org.json.JSONObject confirmContributionPaymentViaPortal(String request_id) {
        try {
            String params = getParams(request_id);
            Response response = target.path(APICall.CONFIRM_CONTRIBUTION_PAYMENT_VIA_PORTAL + params)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean saveOrUpdateMember(Map<String, Object> json) {
        try {
            log.error("API /saveorupdatemember");

            Response response = target.path(APICall.SAVE_OR_UPDATE_MEMBER)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.json(json));
            String s = response.readEntity(String.class);
            log.error(s);
            MessageModelDTO messageModelDTO = new ObjectMapper().readValue(s, MessageModelDTO.class); //response.readEntity(MessageModelDTO.class);
            if (messageModelDTO != null) {
                return messageModelDTO.isSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateKyc(long memberId) {
        try {
            Response response = target.path(APICall.UPDATE_KYC_MEMBER)
                    .path(String.valueOf(memberId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.text("NONE"));
            String s = response.readEntity(String.class);
            MessageModelDTO messageModelDTO = new ObjectMapper().readValue(s, MessageModelDTO.class); //response.readEntity(MessageModelDTO.class);
            if (messageModelDTO != null) {
                return messageModelDTO.isSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ReasonForExitDocument> getDocumentsRequiredPerClaim(long reasonForExitId) {
        try {
            Response response = target.path(APICall.GET_DOCUMENTS_REQUIRED_PER_CLAIM + reasonForExitId)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);

            log.error("Response for required docs>>>> " + s);

            org.json.JSONObject jsonObject = new org.json.JSONObject(s);
            if (Boolean.parseBoolean(
                    StringUtil.toString(jsonObject.get("success"))
            )) {
                JSONArray jsonArray = jsonObject.getJSONArray("rows");
                if (jsonArray != null) {
                    List<ReasonForExitDocument> reasonForExitDocuments = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        org.json.JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        if (jsonObject1 != null) {
//                            {
//                                "id": "7670931",
//                                    "mandatory": "Yes",
//                                    "checklistName": "NATIONAL IDENTITY CARD",
//                                    "effectiveDate": "1 Jan 1999",
//                                    "reasonForExit": "SECTION 69",
//                                    "appliesForAllExits": "No"
//                            }
                            ReasonForExitDocument reasonForExitDocument = new ReasonForExitDocument();
                            reasonForExitDocument.setId(jsonObject1.getString("id"));
                            reasonForExitDocument.setMandatory(jsonObject1.getString("mandatory"));
                            reasonForExitDocument.setChecklistName(jsonObject1.getString("checklistName"));
                            reasonForExitDocument.setEffectiveDate(jsonObject1.getString("effectiveDate"));
                            reasonForExitDocument.setReasonForExit(jsonObject1.getString("reasonForExit"));
                            reasonForExitDocument.setAppliesForAllExits(jsonObject1.getString("appliesForAllExits"));

                            reasonForExitDocuments.add(reasonForExitDocument);
                        }
                    }
                    return reasonForExitDocuments;
                }
            }
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public org.json.JSONObject getProjectionsForMember(ProjectionsForMemberVM projectionsForMemberVM) {
        try {
            log.error("/getProjectionsForMember/{id}/{age}/{reasonId}/{projectionType}");
            String url = getParams(
                    projectionsForMemberVM.getMemberId(),
                    projectionsForMemberVM.getAge(),
                    projectionsForMemberVM.getReasonId(),
                    projectionsForMemberVM.getProjectionType()
            );
            Response response = target.path(APICall.GET_MEMBER_PROJECTIONS + url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
//            log.error(s);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param email    Phone, email, member No
     * @param schemeId LONG
     * @param ordinal  PHONE, EMAIL, MEMBERNO
     * @return JSONObject
     */
        public org.json.JSONObject GET_MEMBER_PRODUCTS(String email, long schemeId, String ordinal) {
        try {
            log.error("API /getmemberproducts");
            String url = getParams(
                    email,
                    schemeId,
                    ordinal
            );
            url = APICall.GET_MEMBER_PRODUCTS + url;
            log.error(url);
            Response response = target.path(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
//            log.error(s);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public org.json.JSONObject getMemberDetailsBySponsorAndPhone(long sponsorId, String phone) {
        try {
            log.error("API /getmemberdetailsbysponsorandphone");
            String url = getParams(sponsorId, phone);
            url = APICall.GET_MEMBER_DETAILS_BY_SPONSOR_AND_PHONE + url;
            log.error(url);
            Response response = target.path(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
//            log.error(s);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public org.json.JSONObject getMemberDetailsBySchemeAndPhone(long schemeId, String phone) {
        try {
            log.error("API /getmemberdetailsbyschemeandphone");
            String url = getParams(schemeId, phone);
            url = APICall.GET_MEMBER_DETAILS_BY_SCHEME_AND_PHONE + url;
            log.error(url);
            Response response = target.path(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
//            log.error(s);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean preserveAccount(long memberId, long schemeId) {
        try {
            log.error("API /preserveAccount");
            String url = getParams(memberId, schemeId);
            url = APICall.PRESERVE_ACCOUNT + url;
            log.error(url);
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

    public MemberKYC memberKYCCheck(long memberId, long schemeId) {
        try {
//            http://197.221.87.90:8080/Xi/api/mssV2/checkIfMemberIsDueForKycUpdate/13372713
            log.error("API /checkIfMemberIsDueForKycUpdate");
            String url = getParams(memberId);
            url = APICall.CHECK_IF_MEMBER_IS_DUE_FOR_KYC_UPDATE + url;
            log.error(url);
            Response response = target.path(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            org.json.JSONObject jsonObject = new org.json.JSONObject(s);
            if (jsonObject.getBoolean("success")) {
                JSONArray jsonArray = jsonObject.getJSONArray("rows");
                if (jsonArray.length() > 0) {
                    String data = jsonArray.getJSONObject(0).toString();
                    return new ObjectMapper()
                            .readValue(data, MemberKYC.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SponsorSchemeVM> getSponsorDetailsByIdentifier(Clients client, long schemeId, String identifier, String identifierValue) {
        try {
            String url = getParams(schemeId, identifier, identifierValue);
            url = (client == Clients.ETL ? APICall.GET_SPONSOR_DETAILS_BY_IDENTIFIER_ETL : APICall.GET_SPONSOR_DETAILS_BY_IDENTIFIER) + url;
            log.error(url);
            Response response = target.path(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            org.json.JSONObject jsonObject = new org.json.JSONObject(s);
            if (jsonObject.getBoolean("success")) {
                JSONArray jsonArray = jsonObject.getJSONArray("rows");
                ArrayList<SponsorSchemeVM> schemes = new ArrayList<>();
                ObjectMapper mapper = new ObjectMapper();
                String json;
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        org.json.JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        json = jsonObject1.toString();
                        SponsorSchemeVM sponsorSchemeVM = mapper.readValue(json, SponsorSchemeVM.class);
                        schemes.add(sponsorSchemeVM);
                    }
                    return schemes;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GenericDOBVM checkIfMemberHasGenericDob(long memberId) {
        try {
            String url = getParams(memberId);
            url = APICall.CHECK_IF_MEMBER_HAS_GENERIC_DOB + url;
            Response response = target.path(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            org.json.JSONObject jsonObject = new org.json.JSONObject(s);
            if (jsonObject.getBoolean("success")) {
                JSONArray jsonArray = jsonObject.getJSONArray("rows");
                if (jsonArray.length() > 0) {
                    String data = jsonArray.getJSONObject(0).toString();
                    return new ObjectMapper()
                            .readValue(data, GenericDOBVM.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public MemberAccountDetailsVM getMemberAccountDetails(long memberId) {
        try {
            log.error("API /getMemberAccountDetails");
            String url = getParams(memberId);
            url = APICall.GET_MEMBER_ACCOUNT_DETAILS + url;
            Response response = target.path(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            org.json.JSONObject jsonObject = new org.json.JSONObject(s);
            if (jsonObject.getBoolean("success")) {
                JSONArray jsonArray = jsonObject.getJSONArray("rows");
                if (jsonArray.length() > 0) {
                    String data = jsonArray.getJSONObject(0).toString();
                    return new ObjectMapper()
                            .readValue(data, MemberAccountDetailsVM.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param employerREFNo employerREFNo
     * @param idType        idType
     * @param idNumber      idNumber
     * @param no            isSponsorId
     * @return JSONObject
     */
    public org.json.JSONObject checkIfIdNumberExists(String employerREFNo, String idType, String idNumber, String no) {
        try {
            log.error("API /checkIdnumberExits");
            String url = getParams(employerREFNo, idType, idNumber, no);
            url = APICall.CHECK_IF_IDNUMBER_EXISTS + url;
            Response response = target.path(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public org.json.JSONObject saveOrUpdateEtlMember(Map<String, Object> map) {
        log.error("API /saveorupdatememberetl");
        try {
            Response response = target.path(APICall.SAVE_OR_UPDATE_ETL_MEMBER)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.json(map));
            String s = response.readEntity(String.class);
            log.info(s);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public org.json.JSONObject updateEtlPotentialMember(Map<String, Object> map) {
        try {
            Response response = target.path(APICall.UPDATE_ETL_POTENTIAL_MEMBER)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.json(map));
            String s = response.readEntity(String.class);
            log.info(s);
            return new org.json.JSONObject(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveOrUpdateMember(MssMemberDTO mssMemberDTO) {
        try {
            log.error("API /saveorupdatemember");

            Response response = target.path(APICall.SAVE_OR_UPDATE_MEMBER)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.json(mssMemberDTO));
            String s = response.readEntity(String.class);
            log.error(s);
            MessageModelDTO messageModelDTO = new ObjectMapper().readValue(s, MessageModelDTO.class); //response.readEntity(MessageModelDTO.class);
            if (messageModelDTO != null) {
                return messageModelDTO.isSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public MessageModelDTO calculateProvisionalBalance(long schemeId, long memberId) {
        try {

            String url = getParams(schemeId, memberId);
            url = APICall.CALCULATEPROVISIONALBALANCE + url;

            log.error("API /" + url);

            Response response = target.path(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            String s = response.readEntity(String.class);
            log.error(s);
            return new ObjectMapper().readValue(s, MessageModelDTO.class); //response.readEntity(MessageModelDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    /**
     * GET_MEMBER_PRODUCTS same with GET_MEMBER_SPONSORS //
     * DELETE_BENEFICIARY
     * GET__POTENTIAL_MEMBER_BENEFICIARIES
     * GET_MEMBER_TOTAL_ENTITLEMENT
     * GET_SPONSOR_ID_USING_PROD_NO
     * GET_MEMBER_DETAILS_BY_SPONSOR_AND_EMAIL
     * GET_MEMBER_DETAILS_BY_SCHEME_AND_PHONE
     * GET_MEMBER_DETAILS_BY_SCHEME_AND_SPONSOR_AND_PHONE
     * GET_MEMBER_DETAILS_BY_SPONSOR_AND_PHONE
     * GET_MEMBER_ID_FROM_MAIL
     * MEMBER_GET_MEMBER_BENEFIT_PAYMENTS
     * GET_DC_MEMBER_BALANCES
     * GET_DB_MEMBER_BALANCES
     * GET_MEMBER_LATEST_CONTRIBUTION
     * GET_MEMBER_TOTAL_UNITS
     * PRESERVE_ACCOUNT
     * UPDATE_POTENTIAL_MEMBER
     */
}
