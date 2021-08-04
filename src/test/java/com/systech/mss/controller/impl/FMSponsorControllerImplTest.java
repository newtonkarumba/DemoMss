//package com.systech.mss.controller.impl;
//
//
//import com.systech.mss.service.dto.FmListBooleanDto;
//import com.systech.mss.service.dto.FmListDTO;
//import com.systech.mss.service.dto.SponsorContributionDTO;
//import org.junit.Assert;
//import org.junit.jupiter.api.Test;
//import org.junit.runners.Parameterized;
//
//import java.util.Date;
//
//public class FMSponsorControllerImplTest extends BaseController{
//
//    @Test
//    public void getSponsorDetails() {
//
//        if (fundMasterClient!=null){
//            FmListDTO fmListDTO=fundMasterClient.getSponsorDetails("5212");
//            Assert.assertNotNull(fmListDTO);
//            Assert.assertTrue(fmListDTO.isSuccess());
//            Assert.assertNotNull(fmListDTO.getRows());
//        }
//    }
//    @Test
//    public void getContributionBillsPerSponsor() {
//
//        if (fundMasterClient!=null){
//            SponsorContributionDTO fmListDTO=fundMasterClient.getContributionBillsPerSponsor(6603,6607,0,100000000);
//            Assert.assertNotNull(fmListDTO);
//            Assert.assertTrue(fmListDTO.isSuccess());
//            Assert.assertNotNull(fmListDTO.getRows());
//        }
//    }
//    @Test
//    public void getSponsorMemberListing() {
//
//        if (fundMasterClient!=null){
//            FmListDTO fmListDTO=fundMasterClient.getSponsorMemberListing(6607,"SPONSOR",6603,0,100000000);
//            Assert.assertNotNull(fmListDTO);
//            Assert.assertTrue(fmListDTO.isSuccess());
//            Assert.assertNotNull(fmListDTO.getRows());
//        }
//    }
//
//    @Test
//    public void getSponsorCompanyCostCentres() {
//
//        if (fundMasterClient!=null){
//            Object fmListDTO=fundMasterClient.getSponsorCompanyCostCentres(6607);
//            Assert.assertNotNull(fmListDTO);
//        }
//    }
//
//    @Test
//    public void getSponsorMemberClasses() {
//
//        if (fundMasterClient!=null){
//            Object fmListDTO=fundMasterClient.getSponsorMemberClasses(6607);
//            Assert.assertNotNull(fmListDTO);
//        }
//    }
//    @Test
//    public void filterContributionBillsPerSponsor() {
//
//        if (fundMasterClient!=null){
//            Date dateFrom = new Date(2021/04/04);
//            Date dateTo = new Date(2021/05/05);
//            SponsorContributionDTO sponsorContributionDTO = fundMasterClient.filterContributionBillsPerSponsor(6603, 6607, dateFrom, dateTo, 0, 100000000);
//            Assert.assertNotNull(sponsorContributionDTO.getRows());
//            Assert.assertTrue(sponsorContributionDTO.isSuccess());
//        }
//    }
//    @Test
//    public void searchContributionBillsPerSponsor() {
//
//        if (fundMasterClient!=null){
//            SponsorContributionDTO sponsorContributionDTO = fundMasterClient.searchContributionBillsPerSponsor(6603, 6607, "MAR", 0, 100000000);
//            Assert.assertNotNull(sponsorContributionDTO.getRows());
//            Assert.assertTrue(sponsorContributionDTO.isSuccess());
//        }
//    }
//    private FmListDTO getMemberList(long id, String profile, long schemeId, int start, int size) {
//        return fundMasterClient.getSponsorMemberListing(id, profile, schemeId, start, size);
//    }
//    @Test
//    public void getSponsorMemberListingCount() {
//
//        if (fundMasterClient!=null){
//            FmListDTO fmListDTO = getMemberList(6607, "SPONSOR", 6603, 0, 2000000);
//            Assert.assertNotNull(fmListDTO.getRows());
//            Assert.assertTrue(fmListDTO.isSuccess());
//        }
//    }
//
////   @Test
////    public void getSponsorMemberAndPensionerCount() {
////
////        if (fundMasterClient!=null){
////            FmListBooleanDto fmListDTO = fundMasterClient.getSponsorMemberAndPensionerCount("5212");
////            Assert.assertNotNull(fmListDTO.getRows());
////            Assert.assertTrue(fmListDTO.isSuccess());
////        }
////    }
//
//
//
//}
//
