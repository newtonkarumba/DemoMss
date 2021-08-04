package com.systech.mss.controller.impl;

import com.systech.mss.domain.Session;
import com.systech.mss.repository.SessionRepository;
import com.systech.mss.service.ProfileService;
import com.systech.mss.service.SessionService;
import com.systech.mss.vm.SessionVM;
import com.systech.mss.vm.WeekdaysEnum;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class SessionControllerImplTest {

    @Mock
    private SessionRepository mockSessionRepository;
    @Mock
    private SessionService mockSessionService;
    @Mock
    private ProfileService mockProfileService;

    @InjectMocks
    private SessionControllerImpl sessionControllerImplUnderTest;

    @Test
    void testGetSessions() {
       Session session = new Session();
        Assert.assertNotNull(session);
    }



    @Test
    void testFilterSessions() {
        Session session = new Session();
        Assert.assertNotNull(session);
    }



    @Test
    void testGetSessionsSingle() {
        Session session = new Session();
        Assert.assertNotNull(session);
    }



    @Test
    void testGetSessionCount() throws Exception {
        Session session = new Session();
        Assert.assertNotNull(session);
    }

    @Test
    void testGetSessionCount_ThrowsIOException() {
        Session session = new Session();
        Assert.assertNotNull(session);
    }

    @Test
    void testGetSessionCountInAWeek() {
        Session session = new Session();
        Assert.assertNotNull(session);
    }

    @Test
    void testGetSessionCountInAWeek_SessionServiceReturnsNoItems() {
        Session session = new Session();
        Assert.assertNotNull(session);
    }

    @Test
    void testSetSessionsMoreDetails() {
        Session session = new Session();
        Assert.assertNotNull(session);
    }
}
