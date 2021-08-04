package com.systech.mss.controller.impl;

import com.systech.mss.controller.vm.BenefitDeclineVM;
import com.systech.mss.controller.vm.PostFormByIdVM;
import com.systech.mss.domain.BenefitRequest;
import com.systech.mss.domain.YesNo;
import com.systech.mss.repository.BenefitRequestRepository;
import com.systech.mss.service.ActivityTrailService;
import com.systech.mss.service.BenefitRequestService;
import com.systech.mss.service.FMCRMClient;
import com.systech.mss.service.dto.FmListDTO;
import com.systech.mss.vm.benefitrequest.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("ALL")
public class BenefitRequestControllerImplTest {

    @Mock
    @Inject
    private BenefitRequestRepository mockBenefitRequestRepository;

    @Mock
    private BenefitRequestService mockBenefitRequestService;
    @Mock
    private ActivityTrailService mockActivityTrailService;
    @Mock
    private FMCRMClient mockFmcrmClient;

    @InjectMocks
    private BenefitRequestControllerImpl benefitRequestControllerImplUnderTest;


    @Test
    public void testGetMemberBenefitRequests() {
        // Setup

        // Configure BenefitRequestRepository.getMemberBenefitRequests(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        final List<BenefitRequest> benefitRequests = Arrays.asList(benefitRequest);
        when(mockBenefitRequestRepository.getMemberBenefitRequests(0L)).thenReturn(benefitRequests);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.getMemberBenefitRequests(0L, 0L);

        // Verify the results
        assertEquals(Response.Status.BAD_REQUEST,result.getStatus());
    }

    @Test
    public void testGetMemberBenefitRequests_BenefitRequestRepositoryReturnsNoItems() {
        // Setup
        when(mockBenefitRequestRepository.getMemberBenefitRequests(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.getMemberBenefitRequests(0L, 0L);

        // Verify the results
    }

    @Test
    public void testSavePersonalDetails() {
        // Setup
        final PersonalDetailsVM personalDetailsVM = new PersonalDetailsVM("name", "memberNo", "dob", "email", 0L, 0L);

        // Configure BenefitRequestRepository.savePersonalDetails(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.savePersonalDetails(eq(0L),2676, any(PersonalDetailsVM.class))).thenReturn(benefitRequest);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.savePersonalDetails(0L, 0L, personalDetailsVM);

        // Verify the results
    }

    @Test
    public void testSaveGroundOfBenefits() {
        // Setup
        final GroundOfBenefits groundOfBenefits = new GroundOfBenefits(0L, 0L, "reason",false);

        // Configure BenefitRequestRepository.saveGroundOfBenefits(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.saveGroundOfBenefits(any(GroundOfBenefits.class))).thenReturn(benefitRequest);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.saveGroundOfBenefits(0L,7165, groundOfBenefits);

        // Verify the results
    }

    @Test
    public void testSavePaymentOptions() {
        // Setup
        final PaymentOptionsVM paymentOptionsVM = new PaymentOptionsVM(0L, "paymentOption", 0L, 0L, 0L, 0L, "isPercentageOrAmount");

        // Configure BenefitRequestRepository.savePaymentOptions(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.savePaymentOptions(any(PaymentOptionsVM.class))).thenReturn(benefitRequest);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.savePaymentOptions(0L, paymentOptionsVM);

        // Verify the results
    }

    @Test
    public void testCheckVestingLiabilities() {
        // Setup

        // Configure BenefitRequestRepository.find(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.find(0L)).thenReturn(benefitRequest);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.checkVestingLiabilities(0L, 0L);

        // Verify the results
    }

    @Test
    public void testSaveBankDetails() {
        // Setup
        final BankDetailsVM bankDetailsVM = new BankDetailsVM(0L, "bankName", "bankBranch", "accountName", "accountNumber");

        // Configure BenefitRequestRepository.saveBankDetails(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.saveBankDetails(any(BankDetailsVM.class))).thenReturn(benefitRequest);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.saveBankDetails(0L, bankDetailsVM);

        // Verify the results
    }

    @Test
    public void testGetBenefitsBySchemeIdAndSponsorId() {
        // Setup

        // Configure BenefitRequestService.getBenefitsBySchemeIdAndSponsorId(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        final List<BenefitRequest> benefitRequests = Arrays.asList(benefitRequest);
        when(mockBenefitRequestService.getBenefitsBySchemeIdAndSponsorId(0L, 0L)).thenReturn(benefitRequests);

        // Configure BenefitRequestService.setBenefitRequestExtraDetails(...).
        final BenefitRequest benefitRequest1 = new BenefitRequest();
        benefitRequest1.setCertifiedBy(0L);
        benefitRequest1.setId(0L);
        benefitRequest1.setMemberNo("memberNo");
        benefitRequest1.setMemberId(0L);
        benefitRequest1.setDob("dob");
        benefitRequest1.setEmail("email");
        benefitRequest1.setBenefitReason("benefitReason");
        benefitRequest1.setBenefitReasonId(0L);
        benefitRequest1.setPaymentOption("paymentOption");
        benefitRequest1.setTotalAmount(0.0);
        final List<BenefitRequest> benefitRequests1 = Arrays.asList(benefitRequest1);
        when(mockBenefitRequestService.setBenefitRequestExtraDetails(Arrays.asList(new BenefitRequest()))).thenReturn(benefitRequests1);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.getBenefitsBySchemeIdAndSponsorId(0L, 0L);

        // Verify the results
    }

    @Test
    public void testGetBenefitsBySchemeIdAndSponsorId_BenefitRequestServiceGetBenefitsBySchemeIdAndSponsorIdReturnsNoItems() {
        // Setup
        when(mockBenefitRequestService.getBenefitsBySchemeIdAndSponsorId(0L, 0L)).thenReturn(Collections.emptyList());

        // Configure BenefitRequestService.setBenefitRequestExtraDetails(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        final List<BenefitRequest> benefitRequests = Arrays.asList(benefitRequest);
        when(mockBenefitRequestService.setBenefitRequestExtraDetails(Arrays.asList(new BenefitRequest()))).thenReturn(benefitRequests);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.getBenefitsBySchemeIdAndSponsorId(0L, 0L);

        // Verify the results
    }

    @Test
    public void testGetBenefitsBySchemeIdAndSponsorId_BenefitRequestServiceSetBenefitRequestExtraDetailsReturnsNoItems() {
        // Setup

        // Configure BenefitRequestService.getBenefitsBySchemeIdAndSponsorId(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        final List<BenefitRequest> benefitRequests = Arrays.asList(benefitRequest);
        when(mockBenefitRequestService.getBenefitsBySchemeIdAndSponsorId(0L, 0L)).thenReturn(benefitRequests);

        when(mockBenefitRequestService.setBenefitRequestExtraDetails(Arrays.asList(new BenefitRequest()))).thenReturn(Collections.emptyList());

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.getBenefitsBySchemeIdAndSponsorId(0L, 0L);

        // Verify the results
    }

    @Test
    public void testGetAllBenefits() {
        // Setup

        // Configure BenefitRequestService.getAll(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        final List<BenefitRequest> benefitRequests = Arrays.asList(benefitRequest);
        when(mockBenefitRequestService.getAll()).thenReturn(benefitRequests);

        // Configure BenefitRequestService.setBenefitRequestExtraDetails(...).
        final BenefitRequest benefitRequest1 = new BenefitRequest();
        benefitRequest1.setCertifiedBy(0L);
        benefitRequest1.setId(0L);
        benefitRequest1.setMemberNo("memberNo");
        benefitRequest1.setMemberId(0L);
        benefitRequest1.setDob("dob");
        benefitRequest1.setEmail("email");
        benefitRequest1.setBenefitReason("benefitReason");
        benefitRequest1.setBenefitReasonId(0L);
        benefitRequest1.setPaymentOption("paymentOption");
        benefitRequest1.setTotalAmount(0.0);
        final List<BenefitRequest> benefitRequests1 = Arrays.asList(benefitRequest1);
        when(mockBenefitRequestService.setBenefitRequestExtraDetails(Arrays.asList(new BenefitRequest()))).thenReturn(benefitRequests1);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.getAllBenefits(0L);

        // Verify the results
        verify(mockActivityTrailService).logActivityTrail(0L, "msg");
    }

    @Test
    public void testGetAllBenefits_BenefitRequestServiceGetAllReturnsNoItems() {
        // Setup
        when(mockBenefitRequestService.getAll()).thenReturn(Collections.emptyList());

        // Configure BenefitRequestService.setBenefitRequestExtraDetails(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        final List<BenefitRequest> benefitRequests = Arrays.asList(benefitRequest);
        when(mockBenefitRequestService.setBenefitRequestExtraDetails(Arrays.asList(new BenefitRequest()))).thenReturn(benefitRequests);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.getAllBenefits(0L);

        // Verify the results
        verify(mockActivityTrailService).logActivityTrail(0L, "msg");
    }

    @Test
    public void testGetAllBenefits_BenefitRequestServiceSetBenefitRequestExtraDetailsReturnsNoItems() {
        // Setup

        // Configure BenefitRequestService.getAll(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        final List<BenefitRequest> benefitRequests = Arrays.asList(benefitRequest);
        when(mockBenefitRequestService.getAll()).thenReturn(benefitRequests);

        when(mockBenefitRequestService.setBenefitRequestExtraDetails(Arrays.asList(new BenefitRequest()))).thenReturn(Collections.emptyList());

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.getAllBenefits(0L);

        // Verify the results
        verify(mockActivityTrailService).logActivityTrail(0L, "msg");
    }

    @Test
    public void testSaveBenefitsFM() {
        // Setup
        final PostFormByIdVM postFormByIdVM = new PostFormByIdVM(0L, 0L);

        // Configure BenefitRequestRepository.find(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.find(0L)).thenReturn(benefitRequest);

        // Configure FMCRMClient.buildBenefitsObjectForFm(...).
        final BenefitsToFMVM benefitsToFMVM = new BenefitsToFMVM();
        benefitsToFMVM.setBenefitCertifiedBy("benefitCertifiedBy");
        benefitsToFMVM.setBenefitTotalAmount(new BigDecimal("0.00"));
        benefitsToFMVM.setBenefitMemberId(0L);
        benefitsToFMVM.setBenefitAuthorizedBy("benefitAuthorizedBy");
        benefitsToFMVM.setBenefitDateApproved("benefitDateApproved");
        benefitsToFMVM.setBenefitReasonId(0L);
        benefitsToFMVM.setBenefitAmountPercentage(0L);
        benefitsToFMVM.setBenefitBenefitReason("benefitBenefitReason");
        benefitsToFMVM.setBenefitDeclinedReason("benefitDeclinedReason");
        benefitsToFMVM.setBenefitIsMemberVested(YesNo.YES);
        when(mockFmcrmClient.buildBenefitsObjectForFm(0L)).thenReturn(benefitsToFMVM);

        // Configure FMCRMClient.saveBenefitsFM(...).
        final FmListDTO fmListDTO = new FmListDTO();//(0L, false, Arrays.asList("value"), "message");
        when(mockFmcrmClient.saveBenefitsFM(any(BenefitsToFMVM.class))).thenReturn(fmListDTO);

        // Configure BenefitRequestRepository.edit(...).
        final BenefitRequest benefitRequest1 = new BenefitRequest();
        benefitRequest1.setCertifiedBy(0L);
        benefitRequest1.setId(0L);
        benefitRequest1.setMemberNo("memberNo");
        benefitRequest1.setMemberId(0L);
        benefitRequest1.setDob("dob");
        benefitRequest1.setEmail("email");
        benefitRequest1.setBenefitReason("benefitReason");
        benefitRequest1.setBenefitReasonId(0L);
        benefitRequest1.setPaymentOption("paymentOption");
        benefitRequest1.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.edit(new BenefitRequest())).thenReturn(benefitRequest1);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.saveBenefitsFM(postFormByIdVM);

        // Verify the results
        verify(mockActivityTrailService).logActivityTrail(0L, "msg");
    }

    @Test
    public void testApproveBenefits() {
        // Setup
        final PostFormByIdVM postFormByIdVM = new PostFormByIdVM(0L, 0L);

        // Configure BenefitRequestRepository.find(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.find(0L)).thenReturn(benefitRequest);

        // Configure BenefitRequestRepository.edit(...).
        final BenefitRequest benefitRequest1 = new BenefitRequest();
        benefitRequest1.setCertifiedBy(0L);
        benefitRequest1.setId(0L);
        benefitRequest1.setMemberNo("memberNo");
        benefitRequest1.setMemberId(0L);
        benefitRequest1.setDob("dob");
        benefitRequest1.setEmail("email");
        benefitRequest1.setBenefitReason("benefitReason");
        benefitRequest1.setBenefitReasonId(0L);
        benefitRequest1.setPaymentOption("paymentOption");
        benefitRequest1.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.edit(new BenefitRequest())).thenReturn(benefitRequest1);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.approveBenefits(postFormByIdVM);

        // Verify the results
        verify(mockActivityTrailService).logActivityTrail(0L, "msg");
    }

    @Test
    public void testCertifyBenefits() {
        // Setup
        final PostFormByIdVM postFormByIdVM = new PostFormByIdVM(0L, 0L);

        // Configure BenefitRequestRepository.find(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.find(0L)).thenReturn(benefitRequest);

        // Configure BenefitRequestRepository.edit(...).
        final BenefitRequest benefitRequest1 = new BenefitRequest();
        benefitRequest1.setCertifiedBy(0L);
        benefitRequest1.setId(0L);
        benefitRequest1.setMemberNo("memberNo");
        benefitRequest1.setMemberId(0L);
        benefitRequest1.setDob("dob");
        benefitRequest1.setEmail("email");
        benefitRequest1.setBenefitReason("benefitReason");
        benefitRequest1.setBenefitReasonId(0L);
        benefitRequest1.setPaymentOption("paymentOption");
        benefitRequest1.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.edit(new BenefitRequest())).thenReturn(benefitRequest1);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.certifyBenefits(postFormByIdVM);

        // Verify the results
        verify(mockActivityTrailService).logActivityTrail(0L, "msg");
    }

    @Test
    public void testAuthorizeBenefits() {
        // Setup
        final PostFormByIdVM postFormByIdVM = new PostFormByIdVM(0L, 0L);

        // Configure BenefitRequestRepository.find(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.find(0L)).thenReturn(benefitRequest);

        // Configure BenefitRequestRepository.edit(...).
        final BenefitRequest benefitRequest1 = new BenefitRequest();
        benefitRequest1.setCertifiedBy(0L);
        benefitRequest1.setId(0L);
        benefitRequest1.setMemberNo("memberNo");
        benefitRequest1.setMemberId(0L);
        benefitRequest1.setDob("dob");
        benefitRequest1.setEmail("email");
        benefitRequest1.setBenefitReason("benefitReason");
        benefitRequest1.setBenefitReasonId(0L);
        benefitRequest1.setPaymentOption("paymentOption");
        benefitRequest1.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.edit(new BenefitRequest())).thenReturn(benefitRequest1);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.authorizeBenefits(postFormByIdVM);

        // Verify the results
        verify(mockActivityTrailService).logActivityTrail(0L, "msg");
    }

    @Test
    public void testSponsorDeclineBenefits() {
        // Setup
        final BenefitDeclineVM benefitDeclineVM = new BenefitDeclineVM(0L, 0L, "reason");

        // Configure BenefitRequestRepository.find(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.find(0L)).thenReturn(benefitRequest);

        // Configure BenefitRequestRepository.edit(...).
        final BenefitRequest benefitRequest1 = new BenefitRequest();
        benefitRequest1.setCertifiedBy(0L);
        benefitRequest1.setId(0L);
        benefitRequest1.setMemberNo("memberNo");
        benefitRequest1.setMemberId(0L);
        benefitRequest1.setDob("dob");
        benefitRequest1.setEmail("email");
        benefitRequest1.setBenefitReason("benefitReason");
        benefitRequest1.setBenefitReasonId(0L);
        benefitRequest1.setPaymentOption("paymentOption");
        benefitRequest1.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.edit(new BenefitRequest())).thenReturn(benefitRequest1);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.sponsorDeclineBenefits(benefitDeclineVM);

        // Verify the results
        verify(mockActivityTrailService).logActivityTrail(0L, "msg");
    }

    @Test
    public void testCRMDeclineBenefits() {
        // Setup
        final BenefitDeclineVM benefitDeclineVM = new BenefitDeclineVM(0L, 0L, "reason");

        // Configure BenefitRequestRepository.find(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.find(0L)).thenReturn(benefitRequest);

        // Configure BenefitRequestRepository.edit(...).
        final BenefitRequest benefitRequest1 = new BenefitRequest();
        benefitRequest1.setCertifiedBy(0L);
        benefitRequest1.setId(0L);
        benefitRequest1.setMemberNo("memberNo");
        benefitRequest1.setMemberId(0L);
        benefitRequest1.setDob("dob");
        benefitRequest1.setEmail("email");
        benefitRequest1.setBenefitReason("benefitReason");
        benefitRequest1.setBenefitReasonId(0L);
        benefitRequest1.setPaymentOption("paymentOption");
        benefitRequest1.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.edit(new BenefitRequest())).thenReturn(benefitRequest1);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.CRMDeclineBenefits(benefitDeclineVM);

        // Verify the results
        verify(mockActivityTrailService).logActivityTrail(0L, "msg");
    }

    @Test
    public void testGetBenefitByid() {
        // Setup

        // Configure BenefitRequestRepository.find(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.find(0L)).thenReturn(benefitRequest);

        // Configure BenefitRequestService.setBenefitRequestExtraDetails(...).
        final BenefitRequest benefitRequest1 = new BenefitRequest();
        benefitRequest1.setCertifiedBy(0L);
        benefitRequest1.setId(0L);
        benefitRequest1.setMemberNo("memberNo");
        benefitRequest1.setMemberId(0L);
        benefitRequest1.setDob("dob");
        benefitRequest1.setEmail("email");
        benefitRequest1.setBenefitReason("benefitReason");
        benefitRequest1.setBenefitReasonId(0L);
        benefitRequest1.setPaymentOption("paymentOption");
        benefitRequest1.setTotalAmount(0.0);
        when(mockBenefitRequestService.setBenefitRequestExtraDetails(new BenefitRequest())).thenReturn(benefitRequest1);

        // Run the test
//        final Response result = benefitRequestControllerImplUnderTest.getBenefitByid(0L,0L);

        // Verify the results
    }

    @Test
    public void testGetBenefitByMemberNo() {
        // Setup

        // Configure BenefitRequestService.getAll(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        final List<BenefitRequest> benefitRequests = Arrays.asList(benefitRequest);
        when(mockBenefitRequestService.getAll()).thenReturn(benefitRequests);

        // Configure BenefitRequestService.setBenefitRequestExtraDetails(...).
        final BenefitRequest benefitRequest1 = new BenefitRequest();
        benefitRequest1.setCertifiedBy(0L);
        benefitRequest1.setId(0L);
        benefitRequest1.setMemberNo("memberNo");
        benefitRequest1.setMemberId(0L);
        benefitRequest1.setDob("dob");
        benefitRequest1.setEmail("email");
        benefitRequest1.setBenefitReason("benefitReason");
        benefitRequest1.setBenefitReasonId(0L);
        benefitRequest1.setPaymentOption("paymentOption");
        benefitRequest1.setTotalAmount(0.0);
        final List<BenefitRequest> benefitRequests1 = Arrays.asList(benefitRequest1);
        when(mockBenefitRequestService.setBenefitRequestExtraDetails(Arrays.asList(new BenefitRequest()))).thenReturn(benefitRequests1);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.getBenefitByMemberNo("memberNO");

        // Verify the results
    }

    @Test
    public void testGetBenefitByMemberNo_BenefitRequestServiceGetAllReturnsNoItems() {
        // Setup
        when(mockBenefitRequestService.getAll()).thenReturn(Collections.emptyList());

        // Configure BenefitRequestService.setBenefitRequestExtraDetails(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        final List<BenefitRequest> benefitRequests = Arrays.asList(benefitRequest);
        when(mockBenefitRequestService.setBenefitRequestExtraDetails(Arrays.asList(new BenefitRequest()))).thenReturn(benefitRequests);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.getBenefitByMemberNo("memberNO");

        // Verify the results
    }

    @Test
    public void testGetBenefitByMemberNo_BenefitRequestServiceSetBenefitRequestExtraDetailsReturnsNoItems() {
        // Setup

        // Configure BenefitRequestService.getAll(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        final List<BenefitRequest> benefitRequests = Arrays.asList(benefitRequest);
        when(mockBenefitRequestService.getAll()).thenReturn(benefitRequests);

        when(mockBenefitRequestService.setBenefitRequestExtraDetails(Arrays.asList(new BenefitRequest()))).thenReturn(Collections.emptyList());

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.getBenefitByMemberNo("memberNO");

        // Verify the results
    }

    @Test
    public void testAuthorizeAndPushBenefitsToFM() {
        // Setup
        final PostFormByIdVM postFormByIdVM = new PostFormByIdVM(0L, 0L);

        // Configure BenefitRequestRepository.find(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.find(0L)).thenReturn(benefitRequest);

        // Configure BenefitRequestRepository.edit(...).
        final BenefitRequest benefitRequest1 = new BenefitRequest();
        benefitRequest1.setCertifiedBy(0L);
        benefitRequest1.setId(0L);
        benefitRequest1.setMemberNo("memberNo");
        benefitRequest1.setMemberId(0L);
        benefitRequest1.setDob("dob");
        benefitRequest1.setEmail("email");
        benefitRequest1.setBenefitReason("benefitReason");
        benefitRequest1.setBenefitReasonId(0L);
        benefitRequest1.setPaymentOption("paymentOption");
        benefitRequest1.setTotalAmount(0.0);
        when(mockBenefitRequestRepository.edit(new BenefitRequest())).thenReturn(benefitRequest1);

        // Configure FMCRMClient.buildBenefitsObjectForFm(...).
        final BenefitsToFMVM benefitsToFMVM = new BenefitsToFMVM();
        benefitsToFMVM.setBenefitCertifiedBy("benefitCertifiedBy");
        benefitsToFMVM.setBenefitTotalAmount(new BigDecimal("0.00"));
        benefitsToFMVM.setBenefitMemberId(0L);
        benefitsToFMVM.setBenefitAuthorizedBy("benefitAuthorizedBy");
        benefitsToFMVM.setBenefitDateApproved("benefitDateApproved");
        benefitsToFMVM.setBenefitReasonId(0L);
        benefitsToFMVM.setBenefitAmountPercentage(0L);
        benefitsToFMVM.setBenefitBenefitReason("benefitBenefitReason");
        benefitsToFMVM.setBenefitDeclinedReason("benefitDeclinedReason");
        benefitsToFMVM.setBenefitIsMemberVested(YesNo.YES);
        when(mockFmcrmClient.buildBenefitsObjectForFm(0L)).thenReturn(benefitsToFMVM);

        // Configure FMCRMClient.saveBenefitsFM(...).
        final FmListDTO fmListDTO = new FmListDTO();//(0L, false, Arrays.asList("value"), "message");
        when(mockFmcrmClient.saveBenefitsFM(any(BenefitsToFMVM.class))).thenReturn(fmListDTO);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.authorizeAndPushBenefitsToFM(postFormByIdVM);

        // Verify the results
        verify(mockActivityTrailService).logActivityTrail(0L, "msg");
    }

    @Test
    public void testGetRecentClaims() {
        // Setup

        // Configure BenefitRequestService.getRecentClaims(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        final List<BenefitRequest> benefitRequests = Arrays.asList(benefitRequest);
        when(mockBenefitRequestService.getRecentClaims(0, 0)).thenReturn(benefitRequests);

        // Configure BenefitRequestService.setBenefitRequestExtraDetails(...).
        final BenefitRequest benefitRequest1 = new BenefitRequest();
        benefitRequest1.setCertifiedBy(0L);
        benefitRequest1.setId(0L);
        benefitRequest1.setMemberNo("memberNo");
        benefitRequest1.setMemberId(0L);
        benefitRequest1.setDob("dob");
        benefitRequest1.setEmail("email");
        benefitRequest1.setBenefitReason("benefitReason");
        benefitRequest1.setBenefitReasonId(0L);
        benefitRequest1.setPaymentOption("paymentOption");
        benefitRequest1.setTotalAmount(0.0);
        final List<BenefitRequest> benefitRequests1 = Arrays.asList(benefitRequest1);
        when(mockBenefitRequestService.setBenefitRequestExtraDetails(Arrays.asList(new BenefitRequest()))).thenReturn(benefitRequests1);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.getRecentClaims(0, 0);

        // Verify the results
    }

    @Test
    public void testGetRecentClaims_BenefitRequestServiceGetRecentClaimsReturnsNoItems() {
        // Setup
        when(mockBenefitRequestService.getRecentClaims(0, 0)).thenReturn(Collections.emptyList());

        // Configure BenefitRequestService.setBenefitRequestExtraDetails(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        final List<BenefitRequest> benefitRequests = Arrays.asList(benefitRequest);
        when(mockBenefitRequestService.setBenefitRequestExtraDetails(Arrays.asList(new BenefitRequest()))).thenReturn(benefitRequests);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.getRecentClaims(0, 0);

        // Verify the results
    }

    @Test
    public void testGetRecentClaims_BenefitRequestServiceSetBenefitRequestExtraDetailsReturnsNoItems() {
        // Setup

        // Configure BenefitRequestService.getRecentClaims(...).
        final BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setCertifiedBy(0L);
        benefitRequest.setId(0L);
        benefitRequest.setMemberNo("memberNo");
        benefitRequest.setMemberId(0L);
        benefitRequest.setDob("dob");
        benefitRequest.setEmail("email");
        benefitRequest.setBenefitReason("benefitReason");
        benefitRequest.setBenefitReasonId(0L);
        benefitRequest.setPaymentOption("paymentOption");
        benefitRequest.setTotalAmount(0.0);
        final List<BenefitRequest> benefitRequests = Arrays.asList(benefitRequest);
        when(mockBenefitRequestService.getRecentClaims(0, 0)).thenReturn(benefitRequests);

        when(mockBenefitRequestService.setBenefitRequestExtraDetails(Arrays.asList(new BenefitRequest()))).thenReturn(Collections.emptyList());

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.getRecentClaims(0, 0);

        // Verify the results
    }

    @Test
    public void testGetCountOfPendingClaims() throws Exception {
        // Setup
        when(mockBenefitRequestService.getCountOfPendingClaims()).thenReturn(0L);

        // Run the test
        final Response result = benefitRequestControllerImplUnderTest.getCountOfPendingClaims();

        // Verify the results
    }

    @Test
    public void testGetCountOfPendingClaims_ThrowsIOException() {
        // Setup
        when(mockBenefitRequestService.getCountOfPendingClaims()).thenReturn(0L);

        // Run the test
        assertThrows(IOException.class, () -> benefitRequestControllerImplUnderTest.getCountOfPendingClaims());
    }

    @Test
    public void testLogActivityTrail() {
        // Setup

        // Run the test
        benefitRequestControllerImplUnderTest.logActivityTrail(0L, "msg");

        // Verify the results
    }
}
