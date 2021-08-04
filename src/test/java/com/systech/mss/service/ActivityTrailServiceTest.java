package com.systech.mss.service;

import com.systech.mss.domain.ActivityTrail;
import com.systech.mss.repository.ActivityTrailRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ActivityTrailServiceTest {

    @Mock
    private Logger mockLogger;
    @Mock
    private ActivityTrailRepository mockActivityTrailRepository;
    @Mock
    private EntityManager mockEntityManager;

    @InjectMocks
    private ActivityTrailService activityTrailServiceUnderTest;

    @Test
    void testLogActivityTrail() {
        // Setup

        // Configure ActivityTrailRepository.create(...).
        final ActivityTrail activityTrail = new ActivityTrail();
        activityTrail.setDescription("description");
        activityTrail.setId(0L);
        activityTrail.setUserId(0L);
        activityTrail.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
//        activityTrail.setUserName("userName");
        activityTrail.setShortDate("shortDate");
        activityTrail.setShortDateTime("shortDateTime");
        when(mockActivityTrailRepository.create(any(ActivityTrail.class))).thenReturn(activityTrail);

        // Configure ActivityTrailRepository.getActivityTrail(...).
        final ActivityTrail activityTrail1 = new ActivityTrail();
        activityTrail1.setDescription("description");
        activityTrail1.setId(0L);
        activityTrail1.setUserId(0L);
        activityTrail1.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
//        activityTrail1.setUserName("userName");
        activityTrail1.setShortDate("shortDate");
        activityTrail1.setShortDateTime("shortDateTime");
        when(mockActivityTrailRepository.getActivityTrail(0L, "msg")).thenReturn(activityTrail1);

        // Run the test
        activityTrailServiceUnderTest.logActivityTrail(0L, "msg");

        // Verify the results
    }

    @Test
    void testGetActivityTrailById() {
        // Setup

        // Run the test
        final List<ActivityTrail> result = activityTrailServiceUnderTest.getActivityTrailById(0L);

        // Verify the results
    }

    @Test
    void testGetEntityManager() {
        // Setup

        // Run the test
        final EntityManager result = activityTrailServiceUnderTest.getEntityManager();

        // Verify the results
    }
}
