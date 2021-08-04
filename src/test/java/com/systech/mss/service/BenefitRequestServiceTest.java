package com.systech.mss.service;

import com.systech.mss.domain.BenefitRequest;
import com.systech.mss.domain.User;
import com.systech.mss.repository.BenefitRequestRepository;
import com.systech.mss.service.dto.FmListDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BenefitRequestServiceTest {

    @Mock
    private BenefitRequestRepository mockBenefitRequestRepository;
    @Mock
    private UserService mockUserService;
    @Mock
    private FMMemberClient mockFmMemberClient;
    @Mock
    private Logger mockLogger;
    @Mock
    private EntityManager mockEntityManager;

    @InjectMocks
    private BenefitRequestService benefitRequestServiceUnderTest;


    @Test
    public void testSetBenefitRequestExtraDetails1() {
        // Setup
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
        final List<BenefitRequest> expectedResult = Arrays.asList(benefitRequest1);

        // Configure UserService.getUserByMemberId(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPassword("password");
        user.setEmail("email");
        user.setActivated(false);
        user.setActivationKey("activationKey");
        user.setSecurityCode("securityCode");
        user.setResetKey("resetKey");
        when(mockUserService.getUserByMemberId(0L)).thenReturn(user);


        // Configure FMMemberClient.getMemberDetails(...).
        final FmListDTO fmListDTO =  new FmListDTO();//FmListDTO(0L, false, Arrays.asList("value"), "message");

        // Run the test
        final List<BenefitRequest> result = benefitRequestServiceUnderTest.setBenefitRequestExtraDetails(benefitRequests);

        // Verify the results
        assertEquals(expectedResult.size(), result.size());
    }

    @Test
    public void testSetBenefitRequestExtraDetails2() {
        // Setup
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

        final BenefitRequest expectedResult = new BenefitRequest();
        expectedResult.setCertifiedBy(0L);
        expectedResult.setId(0L);
        expectedResult.setMemberNo("memberNo");
        expectedResult.setMemberId(0L);
        expectedResult.setDob("dob");
        expectedResult.setEmail("email");
        expectedResult.setBenefitReason("benefitReason");
        expectedResult.setBenefitReasonId(0L);
        expectedResult.setPaymentOption("paymentOption");
        expectedResult.setTotalAmount(0.0);

        // Configure UserService.getUserByMemberId(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPassword("password");
        user.setEmail("email");
        user.setActivated(false);
        user.setActivationKey("activationKey");
        user.setSecurityCode("securityCode");
        user.setResetKey("resetKey");
        when(mockUserService.getUserByMemberId(0L)).thenReturn(user);


        // Configure FMMemberClient.getMemberDetails(...).
        final FmListDTO fmListDTO = new FmListDTO();//(0L, false, Arrays.asList("value"), "message");

        // Run the test
        final BenefitRequest result = benefitRequestServiceUnderTest.setBenefitRequestExtraDetails(benefitRequest);

        // Verify the results
        assertEquals(expectedResult.getAge(), result.getAge());
    }

    @Test
    public void testGetAll() {
        // Setup
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
        final List<BenefitRequest> expectedResult = Arrays.asList(benefitRequest);

        // Configure BenefitRequestRepository.findAll(...).
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
        final List<BenefitRequest> benefitRequests = Arrays.asList(benefitRequest1);
        when(mockBenefitRequestRepository.findAll()).thenReturn(benefitRequests);

        // Run the test
        final List<BenefitRequest> result = benefitRequestServiceUnderTest.getAll();

        // Verify the results
        assertEquals(expectedResult.size(), result.size());
    }

    @Test
    public void testGetAll_BenefitRequestRepositoryReturnsNoItems() {
        // Setup
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
        final List<BenefitRequest> expectedResult = Arrays.asList(benefitRequest);
        when(mockBenefitRequestRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<BenefitRequest> result = benefitRequestServiceUnderTest.getAll();

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testGetRecentClaims() {
        // Setup
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
        final List<BenefitRequest> expectedResult = Arrays.asList(benefitRequest);

        // Configure BenefitRequestRepository.findRange(...).
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
        final List<BenefitRequest> benefitRequests = Arrays.asList(benefitRequest1);
        when(mockBenefitRequestRepository.findRange(0, 1)).thenReturn(benefitRequests);

        // Run the test
        final List<BenefitRequest> result = benefitRequestServiceUnderTest.getRecentClaims(0, 1);

        // Verify the results
        assertEquals(expectedResult.size(), result.size());
    }

    @Test
    public void testGetRecentClaims_BenefitRequestRepositoryReturnsNoItems() {
        // Setup
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
        final List<BenefitRequest> expectedResult = Arrays.asList(benefitRequest);
        when(mockBenefitRequestRepository.findRange(0, 1)).thenReturn(Collections.emptyList());

        // Run the test
        final List<BenefitRequest> result = benefitRequestServiceUnderTest.getRecentClaims(0, 1);

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    public void testGetEntityManager() {
        // Setup

        // Run the test
        final EntityManager result = benefitRequestServiceUnderTest.getEntityManager();

        // Verify the results
    }
}
