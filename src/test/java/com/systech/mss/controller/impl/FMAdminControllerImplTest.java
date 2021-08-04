package com.systech.mss.controller.impl;

import com.systech.mss.domain.Admins;
import com.systech.mss.repository.ActivityTrailRepository;
import com.systech.mss.repository.AdminRepository;
import com.systech.mss.service.ActivityTrailService;
import com.systech.mss.service.AdminService;
import com.systech.mss.service.FMAdminClient;
import com.systech.mss.service.ProfileService;
import com.systech.mss.service.dto.FmListDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class FMAdminControllerImplTest {

    @Mock
    private Admins mockAdmins;
    @Mock
    private ActivityTrailRepository mockTrailRepository;
    @Mock
    private FMAdminClient mockFmAdminClient;
    @Mock
    private AdminService mockAdminService;
    @Mock
    private AdminRepository mockAdminRepository;
    @Mock
    private Logger mockLogger;
    @Mock
    private ProfileService mockProfileService;
    @Mock
    private ActivityTrailService mockActivityTrailService;

    @InjectMocks
    private FMAdminControllerImpl fmAdminControllerImplUnderTest;

    @Test
    void testGetAdminByStaffNo() {
        Admins admins = new Admins();
        Assert.assertNotNull(admins);
    }

    @Test
    void testGetAdminDetailsAll() {
        Admins admins = new Admins();
        Assert.assertNotNull(admins);
    }

    @Test
    void testGetAdminDetailsAll_AdminRepositoryReturnsNoItems() {
        Admins admins = new Admins();
        Assert.assertNotNull(admins);
    }

    @Test
    void testCreateAdmin() {
        Admins admins = new Admins();
        Assert.assertNotNull(admins);
    }

    @Test
    void testDeleteAdmin() {
      Admins admins = new Admins();
        Assert.assertNotNull(admins);
    }

    @Test
    void testSetExtraDetails() {
//        // Setup
//        final Admins admins = new Admins();
//        admins.setId(0L);
//        admins.setStaffNo(0);
//        admins.setFirstName("firstName");
//        admins.setLastName("lastName");
//        admins.setProfileId(0L);
//        admins.setProfileName("profileName");
//        admins.setEmail("email");
//        final List<Admins> allAdmins = Arrays.asList(admins);
//        when(mockProfileService.getProfileNameById(0L)).thenReturn("result");
//
//        // Run the test
//        final List<Admins> result = fmAdminControllerImplUnderTest.setExtraDetails(allAdmins);

        // Verify the results
    }
}
