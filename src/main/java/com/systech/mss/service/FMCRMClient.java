package com.systech.mss.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.config.APICall;
import com.systech.mss.controller.vm.FMUserVM;
import com.systech.mss.controller.vm.SchemeVM;
import com.systech.mss.domain.BenefitRequest;
import com.systech.mss.domain.Config;
import com.systech.mss.domain.YesNo;
import com.systech.mss.repository.BenefitRequestRepository;
import com.systech.mss.repository.ConfigRepository;
import com.systech.mss.service.dto.CumulativeInterestDTO;
import com.systech.mss.service.dto.FmListBooleanDto;
import com.systech.mss.service.dto.FmListDTO;
import com.systech.mss.vm.benefitrequest.BenefitsToFMVM;
import com.systech.mss.vm.benefitrequest.BenefitsToFMVMETL;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import java.util.List;
import java.util.Optional;

@RequestScoped
public class FMCRMClient {
    private Client client;
    private WebTarget target;

    @Inject
    private ConfigRepository configRepository;

    @Inject
    private BenefitRequestRepository benefitRequestRepository;

    @Inject
    private UserService userService;

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

    public FmListDTO saveBenefitsFM(BenefitsToFMVM benefitsToFMVM) {
        Response response = target.path(APICall.SAVE_OR_UPDATE_BENEFIT)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .headers(myHeaders)
                .post(Entity.entity(benefitsToFMVM, MediaType.APPLICATION_JSON));
        try {
            FmListDTO fmListDTO = response.readEntity(FmListDTO.class);
            log.info(fmListDTO.toString());
            return fmListDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FmListDTO saveBenefitsFMETL(BenefitsToFMVMETL benefitsToFMVM) {
        Response response = target.path(APICall.SAVE_OR_UPDATE_BENEFIT)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .headers(myHeaders)
                .post(Entity.entity(benefitsToFMVM, MediaType.APPLICATION_JSON));
        try {
            FmListDTO fmListDTO = response.readEntity(FmListDTO.class);
            log.info(fmListDTO.toString());
            return fmListDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FmListBooleanDto getSponsorsByCrmId(long crmId) {
        try {
            Response response = target.path(APICall.GET_SPONSOR_BY_CRM_ID)
                    .path(String.valueOf(crmId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(FmListBooleanDto.class);
        } catch (Exception e) {
            return FmListBooleanDto.builder().success(false).build();
        }
    }

    public FmListDTO getSponsorMemberListing(Long id, String profile, Long schemeId, int start, int size) {
        Response response = target.path(APICall.GET_MEMBER_LISTING)
                .path(String.valueOf(id))
                .path(profile)
                .path(String.valueOf(schemeId))
                .queryParam("start", start)
                .queryParam("size", size)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .headers(myHeaders)
                .get();
        try {
            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            return FmListDTO.builder().success(false).build();
        }
    }

    public FmListBooleanDto getFMUsersByProfileName(String profile) {
        Response response = target.path(APICall.GET_USER_BY_PROFILE_NAME)
                .path(profile)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .headers(myHeaders)
                .get();
        try {
            return response.readEntity(FmListBooleanDto.class);
        } catch (Exception e) {
            return FmListBooleanDto.builder().success(false).build();
        }
    }

    public long getFMCRMUserId(String email) throws IOException, ParseException {
        FmListBooleanDto fmListBooleanDto = getFMUsersByProfileName("System Administrators");
        List<Object> users = fmListBooleanDto.getRows();

        if (!users.isEmpty()) {
            for (Object user : users) {
                String jsonString = new ObjectMapper().writeValueAsString(user);
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
                if (jsonObject != null) {
                    try {
                        String temp = jsonObject.get("email").toString();
                        if (email.equals(temp)) {
                            return Long.parseLong(jsonObject.get("id").toString());
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return 0L;
    }

    public FmListBooleanDto getFmUserDetailsById(long id) {
        Response response = target.path(APICall.GET_USER_BY_FMId)
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .headers(myHeaders)
                .get();
        try {
            return response.readEntity(FmListBooleanDto.class);
        } catch (Exception e) {
            return FmListBooleanDto.builder().success(false).build();
        }
    }

    public CumulativeInterestDTO getMemberCumulativeStatement(long memberId, long schemeId) {
        Response response = target.path(APICall.GET_MEMBER_CUMULATIVE_STATEMENT)
                .path(String.valueOf(memberId))
                .path(String.valueOf(schemeId))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .headers(myHeaders)
                .get();
        try {
            return response.readEntity(CumulativeInterestDTO.class);
        } catch (Exception e) {
            return CumulativeInterestDTO.builder().success(false).build();
        }
    }

    public SchemeVM getSchemeById(long schemeId) {
        String url = APICall.GET_SCHEME_BY_ID + schemeId;
        log.error("getSchemeById: " + url);
        Response response = target.path(url)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .headers(myHeaders)
                .get();
        try {
            String s = response.readEntity(String.class);
            log.warn(s);
            JSONArray jsonArray = new JSONArray(s); //response.readEntity(FmListDTO.class);
            if (jsonArray.length() > 0) {
                org.json.JSONObject jsonObject = jsonArray.getJSONObject(0);
                if (jsonObject.getBoolean("success")) {
                    org.json.JSONObject jsonObject1 = jsonObject.getJSONObject("scheme");
                    return new ObjectMapper().readValue(jsonObject1.toString(), SchemeVM.class);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public FMUserVM getFmUserDetails(long id) throws JsonProcessingException, ParseException {
        FmListBooleanDto fmListBooleanDto = getFmUserDetailsById(id);
        if (fmListBooleanDto.isSuccess()) {
            List<Object> users = fmListBooleanDto.getRows();
            if (!users.isEmpty()) {
                Object user = users.get(0);
                String jsonString = new ObjectMapper().writeValueAsString(user);
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
                if (jsonObject != null) {
                    FMUserVM fmUserVM = new FMUserVM();
                    fmUserVM.setId(jsonObject.get("id").toString());
                    fmUserVM.setEmail(jsonObject.get("email").toString());
                    fmUserVM.setName(jsonObject.get("name").toString());
                    fmUserVM.setPhoneNumber(jsonObject.get("phoneNumber").toString());
                    fmUserVM.setProfile(jsonObject.get("profile").toString());
                    fmUserVM.setProfileId(jsonObject.get("profileId").toString());

                    return fmUserVM;
                }

            }

        }

        return null;
    }

    //convert object to match fm benefit api object
    public BenefitsToFMVMETL buildBenefitsObjectForFmETL(long benefitId) {
        BenefitRequest benefitRequest = benefitRequestRepository.find(benefitId);
        BenefitsToFMVMETL benefitsToFMVM = new BenefitsToFMVMETL();
        if (benefitRequest != null) {
            //set certified by id
            benefitsToFMVM.setBenefitCertifiedBy(userService
                    .getUsersFullNameById(benefitRequest
                            .getCertifiedBy()
                            .getId()
                    )
            );
            //set benefit total amount
            benefitsToFMVM.setBenefitTotalAmount(BigDecimal
                    .valueOf(benefitRequest
                            .getTotalAmount()
                    )
            );
            //set benefit member id
            benefitsToFMVM.setBenefitMemberId(benefitRequest.getMemberId());
            //set benefit authorized by
            benefitsToFMVM.setBenefitAuthorizedBy(userService
                    .getUsersFullNameById(benefitRequest
                            .getAuthorizedBy()
                            .getId()
                    )
            );
            //set benefit date approved

            benefitsToFMVM.setBenefitDateApproved(String
                    .valueOf(benefitRequest
                            .getDateApproved()
                    )
            );
            //set benefit reason id
            benefitsToFMVM.setBenefitReasonId(benefitRequest.getBenefitReasonId());
            //set benefit amount percentage
            benefitsToFMVM.setBenefitAmountPercentage(benefitRequest.getAmountPercentage());
            //set benefit reason
            benefitsToFMVM.setBenefitBenefitReason(benefitRequest.getBenefitReason());
            //set benefit decline reason
            benefitsToFMVM.setBenefitDeclinedReason(benefitRequest.getDeclineReason());
            //set benefits member vested
            benefitsToFMVM.setBenefitIsMemberVested(YesNo.YES);
            //set do member have mortgage
            benefitsToFMVM.setBenefitDoMemberHasMortgage(YesNo.YES);
            //set benefit date declined

            benefitsToFMVM.setBenefitDateDeclined(String.valueOf(benefitRequest.getDateDeclined()));
            //set benefit is percentage or amount
            benefitsToFMVM.setBenefitIsPercentageOrAmount(benefitRequest.getIsPercentageOrAmount());
            //set date benefit authorized

            benefitsToFMVM.setBenefitDateAuthorized(String.valueOf(benefitRequest.getDateAuthorize()));
            //set date benefit vesting %
            benefitsToFMVM.setBenefitVestingPercentage(benefitRequest.getStaffPercentagePerVestingRule());
            //set benefit approved by
            benefitsToFMVM.setBenefitApprovedBy(userService
                    .getUsersFullNameById(benefitRequest
                            .getApprovedBy()
                            .getId()
                    )
            );
            //set benefit email
            benefitsToFMVM.setBenefitEmail(benefitRequest.getEmail());
            //set benefit date certified
            benefitsToFMVM.setBenefitDateCertified(String.valueOf(benefitRequest.getDateCertified()));
            //set benefit id
            benefitsToFMVM.setBenefitBenefitId(benefitRequest.getId());
            //set benefit liability amount
            benefitsToFMVM.setBenefitLiabilityAmount(null);
            //set benefit do member has liability
            benefitsToFMVM.setBenefitDoMemberHasLiability(YesNo.NO);
            //set bank branch
            benefitsToFMVM.setBenefitBankBranchName(benefitRequest.getBankBranchName());
            //set mortgage amount
            benefitsToFMVM.setBenefitMortgageAmount(benefitRequest.getMortageLoan());
            //set benefit declined by
            try {
                benefitsToFMVM.setBenefitDeclinedBy(userService
                        .getUsersFullNameById(benefitRequest
                                .getDeclinedBy()
                                .getId()
                        ));
            } catch (NullPointerException e) {
                benefitsToFMVM.setBenefitDeclinedBy("");
            }

            //set bank name
            benefitsToFMVM.setBenefitBankName(benefitRequest.getBankName());
            //set benefit member no
            benefitsToFMVM.setBenefitMemberNo(benefitRequest.getMemberNo());
            //set account no
            benefitsToFMVM.setBenefitAccountNo(benefitRequest.getAccountNumber());
            //set account name
            benefitsToFMVM.setBenefitAccountName(benefitRequest.getAccountName());
            benefitsToFMVM.setBenefitAuthorizedByCrm(userService
                    .getUsersFullNameById(benefitRequest
                            .getCrmApprovedUser()
                            .getId()
                    )
            );

            benefitsToFMVM.setBenefitDateAuthorizedByCrm(String.valueOf(benefitRequest.getDateApprovedByCrm()));

            return benefitsToFMVM;
        }
        return null;
    }

    //convert object to match fm benefit api object
    public BenefitsToFMVM buildBenefitsObjectForFm(long benefitId) {
        BenefitRequest benefitRequest = benefitRequestRepository.find(benefitId);
        BenefitsToFMVM benefitsToFMVM = new BenefitsToFMVM();
        if (benefitRequest != null) {
            //set certified by id
            benefitsToFMVM.setBenefitCertifiedBy(userService
                    .getUsersFullNameById(benefitRequest
                            .getCertifiedBy()
                            .getId()
                    )
            );
            //set benefit total amount
            benefitsToFMVM.setBenefitTotalAmount(BigDecimal
                    .valueOf(benefitRequest
                            .getTotalAmount()
                    )
            );
            //set benefit member id
            benefitsToFMVM.setBenefitMemberId(benefitRequest.getMemberId());
            //set benefit authorized by
            benefitsToFMVM.setBenefitAuthorizedBy(userService
                    .getUsersFullNameById(benefitRequest
                            .getAuthorizedBy()
                            .getId()
                    )
            );
            //set benefit date approved
            benefitsToFMVM.setBenefitDateApproved(String
                    .valueOf(benefitRequest
                            .getDateApproved()
                    )
            );
            //set benefit reason id
            benefitsToFMVM.setBenefitReasonId(benefitRequest.getBenefitReasonId());
            //set benefit amount percentage
            benefitsToFMVM.setBenefitAmountPercentage(benefitRequest.getAmountPercentage());
            //set benefit reason
            benefitsToFMVM.setBenefitBenefitReason(benefitRequest.getBenefitReason());
            //set benefit decline reason
            benefitsToFMVM.setBenefitDeclinedReason(benefitRequest.getDeclineReason());
            //set benefits member vested
            benefitsToFMVM.setBenefitIsMemberVested(YesNo.YES);
            //set do member have mortgage
            benefitsToFMVM.setBenefitDoMemberHasMortgage(YesNo.YES);
            //set benefit date declined
            benefitsToFMVM.setBenefitDateDeclined(String.valueOf(benefitRequest.getDateDeclined()));
            //set benefit is percentage or amount
            benefitsToFMVM.setBenefitIsPercentageOrAmount(benefitRequest.getIsPercentageOrAmount());
            //set date benefit authorized
            benefitsToFMVM.setBenefitDateAuthorized(String.valueOf(benefitRequest.getDateAuthorize()));
            //set date benefit vesting %
            benefitsToFMVM.setBenefitVestingPercentage(benefitRequest.getStaffPercentagePerVestingRule());
            //set benefit approved by
            benefitsToFMVM.setBenefitApprovedBy(userService
                    .getUsersFullNameById(benefitRequest
                            .getApprovedBy()
                            .getId()
                    )
            );
            //set benefit email
            benefitsToFMVM.setBenefitEmail(benefitRequest.getEmail());
            //set benefit date certified
            benefitsToFMVM.setBenefitDateCertified(String.valueOf(benefitRequest.getDateCertified()));
            //set benefit id
            benefitsToFMVM.setBenefitBenefitId(benefitRequest.getId());
            //set benefit liability amount
            benefitsToFMVM.setBenefitLiabilityAmount(null);
            //set benefit do member has liability
            benefitsToFMVM.setBenefitDoMemberHasLiability(YesNo.NO);
            //set bank branch
            benefitsToFMVM.setBenefitBankBranchName(benefitRequest.getBankBranchName());
            //set mortgage amount
            benefitsToFMVM.setBenefitMortgageAmount(benefitRequest.getMortageLoan());
            //set benefit declined by
            try {
                benefitsToFMVM.setBenefitDeclinedBy(userService
                        .getUsersFullNameById(benefitRequest
                                .getDeclinedBy()
                                .getId()
                        ));
            } catch (NullPointerException e) {
                benefitsToFMVM.setBenefitDeclinedBy("");
            }

            //set bank name
            benefitsToFMVM.setBenefitBankName(benefitRequest.getBankName());
            //set benefit member no
            benefitsToFMVM.setBenefitMemberNo(benefitRequest.getMemberNo());
            //set account no
            benefitsToFMVM.setBenefitAccountNo(benefitRequest.getAccountNumber());
            //set account name
            benefitsToFMVM.setBenefitAccountName(benefitRequest.getAccountName());

            return benefitsToFMVM;
        }
        return null;
    }

    private Optional<Config> getFMConfig() {
        return configRepository
                .findAll()
                .stream()
                .findFirst();
    }

}
