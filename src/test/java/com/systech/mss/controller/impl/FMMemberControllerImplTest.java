package com.systech.mss.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.controller.vm.MemberBalanceVM;
import com.systech.mss.repository.ConfigRepository;
import com.systech.mss.service.dto.FmListDTO;
import com.systech.mss.service.dto.MemberBalanceDTO;
import com.systech.mss.service.dto.MessageModelDTO;
import com.systech.mss.service.dto.StringListDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

class FMMemberControllerImplTest extends BaseController {

    @Inject
    ConfigRepository configRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getMemberDetailsSingle() {
        if (fmMemberClient != null) {
            FmListDTO fmListDTO = fmMemberClient.getMemberDetails(7165);
            Assert.assertNotNull(fmListDTO);
            Assert.assertTrue(fmListDTO.isSuccess());
            Assert.assertNotNull(fmListDTO.getRows());
        }
    }

    @Test
    void getMemberDetailsBySchemeAndEmail() {
        if (fmMemberClient != null) {
            FmListDTO fmListDTO = fmMemberClient.getMemberDetailsBySchemeAndEmail(7165, "aviatoryona67@gmail.com");
            Assert.assertNotNull(fmListDTO);
            Assert.assertTrue(fmListDTO.isSuccess());
            Assert.assertNotNull(fmListDTO.getRows());
        }
    }

    @Test
    void getMemberDetailsAll() {
        if (fmMemberClient != null) {
            FmListDTO fmListDTO = fmMemberClient.getMemberDetails(7165);
            Assert.assertNotNull(fmListDTO);
            Assert.assertTrue(fmListDTO.isSuccess());
            Assert.assertNotNull(fmListDTO.getRows());
        }
    }

    @Test
    void getMemberSchemes() {
        if (fmMemberClient != null) {
            FmListDTO fmListDTO = fmMemberClient.getMemberSchemes("EMAIL", "aviatoryona67@gmail.com", "MEMBER");
            Assert.assertNotNull(fmListDTO);
            Assert.assertTrue(fmListDTO.isSuccess());
            Assert.assertNotNull(fmListDTO.getRows());
        }
    }
//
//    @Test
//    void getMemberBankDetails() {
//        if (fmMemberClient != null) {
//            Object fmListDTO = fmMemberClient.getMemberBankDetails(7165);
//            Assert.assertNotNull(fmListDTO);
//        }
//    }

    @Test
    void getSummary() {
        if (fmMemberClient != null) {
            FmListDTO fmListDTO = fmMemberClient.getSummary(7165);
            Assert.assertNotNull(fmListDTO);
            Assert.assertTrue(fmListDTO.isSuccess());
            Assert.assertNotNull(fmListDTO.getRows());
        }
    }

    @Test
    void requestStatementAsAt() {
        if (fmMemberClient != null) {
            JSONObject fmListDTO = fmMemberClient.requestStatement(7165, 3306, 6603);
            Assert.assertNotNull(fmListDTO);
            try {
                Assert.assertTrue(fmListDTO.getBoolean("success"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void getClosingBalancesSummary() {
        if (fmMemberClient != null) {
            FmListDTO fmListDTO = fmMemberClient.getClosingBalances(7165);
            Assert.assertNotNull(fmListDTO);
            Assert.assertTrue(fmListDTO.isSuccess());
        }
    }

    @Test
    void getClosingBalances() {
        if (fmMemberClient != null) {
            FmListDTO fmListDTO = fmMemberClient.getClosingBalances(7165);
            Assert.assertNotNull(fmListDTO);
            Assert.assertTrue(fmListDTO.isSuccess());
        }
    }

    @Test
    void getBeneficiaries() {
        if (fmMemberClient != null) {
            FmListDTO fmListDTO = fmMemberClient.getBeneficiaries(7165);
            Assert.assertNotNull(fmListDTO);
            Assert.assertTrue(fmListDTO.isSuccess());
        }
    }

    @Test
    void checkIfCanAddBeneficiaries() {
        if (fmMemberClient != null) {
            FmListDTO fmListDTO = fmMemberClient.getBeneficiaries(7165);
            Assert.assertNotNull(fmListDTO);
            Assert.assertFalse(fmListDTO.isSuccess());

            if (fmListDTO.getRows() != null) {
                int total = 0;
                for (Object o :
                        fmListDTO.getRows()) {
                    try {
                        JSONParser parser = new JSONParser();
                        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(
                                new ObjectMapper().writeValueAsString(o)
                        );
                        if (jsonObject != null) {
                            if (jsonObject.containsKey("lumpsumEntitlement")) {
                                total += Integer.parseInt(String.valueOf(jsonObject.get("lumpsumEntitlement")));
                            }
                        }
                    } catch (Exception e) {
                    }
                }

                Assertions.assertTrue(total == 100);

            }
        }
    }

    @Test
    void addBeneficiary() {
        if (fmMemberClient != null) {
            MessageModelDTO fmListDTO = fmMemberClient.addBeneficiary(new org.json.simple.JSONObject());
            Assert.assertNotNull(fmListDTO);
            Assert.assertFalse(fmListDTO.isSuccess());
        }
    }

    @Test
    void getContributions() {
        if (fmMemberClient != null) {
            FmListDTO fmListDTO = fmMemberClient.getContributions(7165, 6603);
            Assert.assertNotNull(fmListDTO);
            Assert.assertTrue(fmListDTO.isSuccess());
        }
    }

    @Test
    void filterContributions() {
        if (fmMemberClient != null) {
            FmListDTO fmListDTO = fmMemberClient.filterContributions(7165, "JAN 12, 2010", "APR 04, 2021");
            Assert.assertNotNull(fmListDTO);
            Assert.assertTrue(fmListDTO.isSuccess());
        }
    }

    @Test
    void getBalances() {
        if (fmMemberClient != null) {
            MemberBalanceDTO fmListDTO = fmMemberClient.getBalances(7165);
            Assert.assertNotNull(fmListDTO);
            Assert.assertTrue(fmListDTO.isSuccess());
        }
    }

    @Test
    void getBalancesAsAtToday() {
        if (fmMemberClient == null) return;
        org.json.simple.JSONObject jsonObject = fmMemberClient.getBalancesAsAtToday(7165, 6603);
        Assert.assertNotNull(jsonObject);
        Assert.assertTrue(jsonObject.containsKey("success"));

        /**
         * If above fails, calculate balances from contributions
         */
        MemberBalanceDTO memberBalanceDTO = fmMemberClient.getBalances(7165);
        Assert.assertNotNull(memberBalanceDTO);
        List<MemberBalanceVM> memberBalanceVMS = memberBalanceDTO.getRows();
        Assert.assertNotNull(memberBalanceVMS.get(0));
        Assert.assertTrue(memberBalanceVMS.get(0).getMemberId() == "7165");
    }

    @Test
    void getClaims() {

    }

    @Test
    void initiateClaim() {
    }

    @Test
    void projections() {
    }

    @Test
    void calculateWhatIfAnalysis() {
    }

    @Test
    void requestMemberCertificate() {
    }

    @Test
    void changePwd() {
    }

    @Test
    void getReasonsForExit() {
        if (fmMemberClient != null) {
            FmListDTO fmListDTO = fmMemberClient.getReasonsForExit();
            Assert.assertNotNull(fmListDTO);
            Assert.assertTrue(fmListDTO.isSuccess());
        }
    }

    @Test
    void getReasonForExitById() {
    }

    @Test
    void activityLog() {
    }

    @Test
    void getMemberLoans() {
    }

    @Test
    void getMissingDocuments() {
        if (fmMemberClient != null) {
            StringListDTO fmListDTO = fmMemberClient.getMissingDocuments(7165);
            Assert.assertNotNull(fmListDTO);
            Assert.assertTrue(Boolean.parseBoolean(fmListDTO.getSuccess()));
        }
    }

    @Test
    void getSubmittedDocuments() {
    }

    @Test
    void checkSubmittedDocuments() {
    }

    @Test
    void getPayBill() {
        if (configRepository != null) {
            Object config = configRepository.getSpecificFieldsOfActiveConfigs();
            Assert.assertNotNull(config);
        }
    }

    @Test
    void getAllAccountingPeriods() {
    }

    @Test
    void getMemberStatement() {
    }

    @Test
    void memberSubmitRequiredDocument() {
    }

    @Test
    void getCurrentMonthlyContributionAndBasicSalary() {
    }

    @Test
    void receiveMemberUploadedDocument() {
    }
}