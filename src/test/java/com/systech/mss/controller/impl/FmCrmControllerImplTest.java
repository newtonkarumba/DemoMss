package com.systech.mss.controller.impl;

import com.systech.mss.controller.FmCrmController;
import com.systech.mss.repository.ConfigRepository;
import com.systech.mss.service.FMCRMClient;
import com.systech.mss.service.FundMasterClient;
import com.systech.mss.service.dto.CumulativeInterestDTO;
import com.systech.mss.service.dto.FmListBooleanDto;
import com.systech.mss.service.dto.FmListDTO;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FmCrmControllerImplTest {
    @Inject
    ConfigRepository configRepository;

    @Inject
    private FMCRMClient fmcrmClient;

    @Inject
    private FmCrmController fmCrmController;

    @Inject
    private FundMasterClient fundMasterClient;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getSponsorsByCrmId() {

        if (fmcrmClient != null) {
            FmListBooleanDto sponsorsByCrmId = fmcrmClient.getSponsorsByCrmId(367209);
            Assert.assertNotNull(sponsorsByCrmId);
            Assert.assertTrue(sponsorsByCrmId.isSuccess());
            Assert.assertNotNull(sponsorsByCrmId.getRows());
        }
    }

    @Test
    void getSponsorMemberListing() {
        if (fundMasterClient!=null){
            FmListDTO fmListDTO=fundMasterClient.getSponsorMemberListing(6607,"SPONSOR",6603,0,100000000);
            Assert.assertNotNull(fmListDTO);
            Assert.assertTrue(fmListDTO.isSuccess());
            Assert.assertNotNull(fmListDTO.getRows());
        }
    }

    @Test
    void getCountOfMembers() throws IOException, ParseException {
        if(fmcrmClient != null){
            Response countOfMembers = fmCrmController.getCountOfMembers(234L);
            assertNotNull(countOfMembers);

        }
    }

    @Test
    void getCountOfSponsorsAndMembers() throws IOException, ParseException {
        if(fmcrmClient != null){
            Response countOfSponsorsAndMembers = fmCrmController.getCountOfSponsorsAndMembers(234L);
            assertNotNull(countOfSponsorsAndMembers);
        }
    }


    @Test
    void getFMCRMUserId() throws IOException, ParseException {
        if (fmcrmClient != null) {
            long fmcrmUserId = fmcrmClient.getFMCRMUserId("smnkimathi@gmail.com");
            assertEquals(fmcrmUserId,367209);
            Assert.assertNotNull(fmcrmUserId);
        }
    }

    @Test
    void getFmUserDetailsById() {
        if(fmcrmClient != null){
            FmListBooleanDto fmUserDetailsById = fmcrmClient.getFmUserDetailsById(367209);
            assertNotNull(fmUserDetailsById);
            assertTrue(fmUserDetailsById.isSuccess());
            assertNotNull(fmUserDetailsById.getRows());
        }
    }

    @Test
    void getMemberCumulativeStatement() {
        if(fmcrmClient != null){
            CumulativeInterestDTO memberCumulativeStatement = fmcrmClient.getMemberCumulativeStatement(7165, 6603);
            assertNotNull(memberCumulativeStatement);
            assertTrue(memberCumulativeStatement.isSuccess());
            assertNotNull(memberCumulativeStatement.getRows());
        }
    }
}