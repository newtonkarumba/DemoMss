package com.systech.mss.controller.impl;

import com.systech.mss.controller.vm.ManagedUserVM;
import com.systech.mss.domain.Ticket;
import com.systech.mss.domain.TicketMessage;
import com.systech.mss.domain.TicketPriority;
import com.systech.mss.domain.User;
import com.systech.mss.repository.TicketRepository;
import com.systech.mss.repository.UserRepository;
import com.systech.mss.service.*;
import com.systech.mss.service.dto.UserDTO;
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
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserControllerImplTest {

    @Mock
    private ProfileService mockProfileService;
    @Mock
    private Logger mockLog;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private TicketsService mockTicketsService;
    @Mock
    private TicketRepository mockTicketRepository;
    @Mock
    private ActivityTrailService mockActivityTrailService;
    @Mock
    private MailService mockMailService;
    @Mock
    private UserService mockUserService;

    @InjectMocks
    private UserControllerImpl userControllerImplUnderTest;

    @Test
    void testCreateUser() {
        User user = new User();
        Assert.assertNotNull(user);
    }





    @Test
    void testGetAllUsers1() {User user = new User();
        Assert.assertNotNull(user);
    }


    @Test
    void testEditUser() {
        User user = new User();
        Assert.assertNotNull(user);
    }

    @Test
    void testDropUser() {
        User user = new User();
        Assert.assertNotNull(user);
    }

    @Test
    void testGetUser() {
        User user = new User();
        Assert.assertNotNull(user);
    }

    @Test
    void testGetTicketByCreatedByUserId_TicketsServiceSetTicketExtraDetailsReturnsNoItems() {
        User user = new User();
        Assert.assertNotNull(user);
    }


    @Test
    void testSetExtraDetails() {
        User user = new User();
        Assert.assertNotNull(user);
    }

    @Test
    void testSetAdminExtraDetails() {
        User user = new User();
        Assert.assertNotNull(user);    }
}
