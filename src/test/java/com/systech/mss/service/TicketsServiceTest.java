package com.systech.mss.service;

import com.systech.mss.controller.vm.ForwardTicketVm;
import com.systech.mss.domain.*;
import com.systech.mss.repository.TicketIssuesRepository;
import com.systech.mss.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
class TicketsServiceTest {

    @Mock
    private Logger mockLogger;
    @Mock
    private TicketRepository mockTicketRepository;
    @Mock
    private TicketIssuesRepository mockTicketIssuesRepository;
    @Mock
    private UserService mockUserService;
    @Mock
    private EntityManager mockEntityManager;

    @InjectMocks
    private TicketsService ticketsServiceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testGetTickets() {
        // Setup

        // Configure TicketRepository.findAll(...).
        final Ticket ticket = new Ticket();
        ticket.setForwardedBy(0L);
        ticket.setId(0L);
        ticket.setClosed(false);
        ticket.setSubject("subject");
        ticket.setBody("body");
        final TicketMessage ticketMessage = new TicketMessage();
        ticketMessage.setId(0L);
        ticketMessage.setMessage("message");
        ticketMessage.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticketMessage.setOwnerName("ownerName");
        ticketMessage.setProfileName("profileName");
        ticketMessage.setShortDate("shortDate");
        ticketMessage.setShortDateTime("shortDateTime");
        ticket.setTicketMessages(new HashSet<>(Arrays.asList(ticketMessage)));
        ticket.setPriority(TicketPriority.HIGH);
        ticket.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticket.setSchemeId(0L);
        ticket.setSponsorId(0L);
        final List<Ticket> tickets = Arrays.asList(ticket);
        when(mockTicketRepository.findAll()).thenReturn(tickets);

        // Run the test
        final List<Ticket> result = ticketsServiceUnderTest.getTickets();

        // Verify the results
    }

    @Test
    void testGetTickets_TicketRepositoryReturnsNoItems() {
        // Setup
        when(mockTicketRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Ticket> result = ticketsServiceUnderTest.getTickets();

        // Verify the results
    }

    @Test
    void testGetTicketsRange() {
        // Setup

        // Configure TicketRepository.findRange(...).
        final Ticket ticket = new Ticket();
        ticket.setForwardedBy(0L);
        ticket.setId(0L);
        ticket.setClosed(false);
        ticket.setSubject("subject");
        ticket.setBody("body");
        final TicketMessage ticketMessage = new TicketMessage();
        ticketMessage.setId(0L);
        ticketMessage.setMessage("message");
        ticketMessage.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticketMessage.setOwnerName("ownerName");
        ticketMessage.setProfileName("profileName");
        ticketMessage.setShortDate("shortDate");
        ticketMessage.setShortDateTime("shortDateTime");
        ticket.setTicketMessages(new HashSet<>(Arrays.asList(ticketMessage)));
        ticket.setPriority(TicketPriority.HIGH);
        ticket.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticket.setSchemeId(0L);
        ticket.setSponsorId(0L);
        final List<Ticket> tickets = Arrays.asList(ticket);
        when(mockTicketRepository.findRange(0, 0)).thenReturn(tickets);

        // Run the test
        final List<Ticket> result = ticketsServiceUnderTest.getTicketsRange(0, 0);

        // Verify the results
    }

    @Test
    void testGetTicketsRange_TicketRepositoryReturnsNoItems() {
        // Setup
        when(mockTicketRepository.findRange(0, 0)).thenReturn(Collections.emptyList());

        // Run the test
        final List<Ticket> result = ticketsServiceUnderTest.getTicketsRange(0, 0);

        // Verify the results
    }

    @Test
    void testGetTicketById() {
        // Setup

        // Configure TicketRepository.find(...).
        final Ticket ticket = new Ticket();
        ticket.setForwardedBy(0L);
        ticket.setId(0L);
        ticket.setClosed(false);
        ticket.setSubject("subject");
        ticket.setBody("body");
        final TicketMessage ticketMessage = new TicketMessage();
        ticketMessage.setId(0L);
        ticketMessage.setMessage("message");
        ticketMessage.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticketMessage.setOwnerName("ownerName");
        ticketMessage.setProfileName("profileName");
        ticketMessage.setShortDate("shortDate");
        ticketMessage.setShortDateTime("shortDateTime");
        ticket.setTicketMessages(new HashSet<>(Arrays.asList(ticketMessage)));
        ticket.setPriority(TicketPriority.HIGH);
        ticket.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticket.setSchemeId(0L);
        ticket.setSponsorId(0L);
        when(mockTicketRepository.find(0L)).thenReturn(ticket);

        // Run the test
        final Ticket result = ticketsServiceUnderTest.getTicketById(0L);

        // Verify the results
    }

    @Test
    void testCheckIfTicketExists() {
        // Setup
        when(mockTicketRepository.existsById(0L)).thenReturn(true);

        // Run the test
        final boolean result = ticketsServiceUnderTest.checkIfTicketExists(0L);

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testCreateTicket() {
        // Setup
        final Ticket ticket = new Ticket();
        ticket.setForwardedBy(0L);
        ticket.setId(0L);
        ticket.setClosed(false);
        ticket.setSubject("subject");
        ticket.setBody("body");
        final TicketMessage ticketMessage = new TicketMessage();
        ticketMessage.setId(0L);
        ticketMessage.setMessage("message");
        ticketMessage.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticketMessage.setOwnerName("ownerName");
        ticketMessage.setProfileName("profileName");
        ticketMessage.setShortDate("shortDate");
        ticketMessage.setShortDateTime("shortDateTime");
        ticket.setTicketMessages(new HashSet<>(Arrays.asList(ticketMessage)));
        ticket.setPriority(TicketPriority.HIGH);
        ticket.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticket.setSchemeId(0L);
        ticket.setSponsorId(0L);

        // Configure TicketIssuesRepository.find(...).
        final TicketIssues ticketIssues = new TicketIssues();
        ticketIssues.setId(0L);
        ticketIssues.setIssue("issue");
        when(mockTicketIssuesRepository.find(0L)).thenReturn(ticketIssues);

        // Configure UserService.getUserById(...).
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
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Configure TicketRepository.create(...).
        final Ticket ticket1 = new Ticket();
        ticket1.setForwardedBy(0L);
        ticket1.setId(0L);
        ticket1.setClosed(false);
        ticket1.setSubject("subject");
        ticket1.setBody("body");
        final TicketMessage ticketMessage1 = new TicketMessage();
        ticketMessage1.setId(0L);
        ticketMessage1.setMessage("message");
        ticketMessage1.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticketMessage1.setOwnerName("ownerName");
        ticketMessage1.setProfileName("profileName");
        ticketMessage1.setShortDate("shortDate");
        ticketMessage1.setShortDateTime("shortDateTime");
        ticket1.setTicketMessages(new HashSet<>(Arrays.asList(ticketMessage1)));
        ticket1.setPriority(TicketPriority.HIGH);
        ticket1.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticket1.setSchemeId(0L);
        ticket1.setSponsorId(0L);
        when(mockTicketRepository.create(any(Ticket.class))).thenReturn(ticket1);

        // Run the test
        final Ticket result = ticketsServiceUnderTest.createTicket(ticket);

        // Verify the results
    }

    @Test
    void testEditTicket() {
        // Setup
        final Ticket ticket = new Ticket();
        ticket.setForwardedBy(0L);
        ticket.setId(0L);
        ticket.setClosed(false);
        ticket.setSubject("subject");
        ticket.setBody("body");
        final TicketMessage ticketMessage = new TicketMessage();
        ticketMessage.setId(0L);
        ticketMessage.setMessage("message");
        ticketMessage.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticketMessage.setOwnerName("ownerName");
        ticketMessage.setProfileName("profileName");
        ticketMessage.setShortDate("shortDate");
        ticketMessage.setShortDateTime("shortDateTime");
        ticket.setTicketMessages(new HashSet<>(Arrays.asList(ticketMessage)));
        ticket.setPriority(TicketPriority.HIGH);
        ticket.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticket.setSchemeId(0L);
        ticket.setSponsorId(0L);

        // Configure TicketRepository.edit(...).
        final Ticket ticket1 = new Ticket();
        ticket1.setForwardedBy(0L);
        ticket1.setId(0L);
        ticket1.setClosed(false);
        ticket1.setSubject("subject");
        ticket1.setBody("body");
        final TicketMessage ticketMessage1 = new TicketMessage();
        ticketMessage1.setId(0L);
        ticketMessage1.setMessage("message");
        ticketMessage1.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticketMessage1.setOwnerName("ownerName");
        ticketMessage1.setProfileName("profileName");
        ticketMessage1.setShortDate("shortDate");
        ticketMessage1.setShortDateTime("shortDateTime");
        ticket1.setTicketMessages(new HashSet<>(Arrays.asList(ticketMessage1)));
        ticket1.setPriority(TicketPriority.HIGH);
        ticket1.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticket1.setSchemeId(0L);
        ticket1.setSponsorId(0L);
        when(mockTicketRepository.edit(any(Ticket.class))).thenReturn(ticket1);

        // Run the test
        final Ticket result = ticketsServiceUnderTest.editTicket(ticket);

        // Verify the results
    }

    @Test
    void testCloseTicket() {
        // Setup

        // Configure TicketRepository.find(...).
        final Ticket ticket = new Ticket();
        ticket.setForwardedBy(0L);
        ticket.setId(0L);
        ticket.setClosed(false);
        ticket.setSubject("subject");
        ticket.setBody("body");
        final TicketMessage ticketMessage = new TicketMessage();
        ticketMessage.setId(0L);
        ticketMessage.setMessage("message");
        ticketMessage.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticketMessage.setOwnerName("ownerName");
        ticketMessage.setProfileName("profileName");
        ticketMessage.setShortDate("shortDate");
        ticketMessage.setShortDateTime("shortDateTime");
        ticket.setTicketMessages(new HashSet<>(Arrays.asList(ticketMessage)));
        ticket.setPriority(TicketPriority.HIGH);
        ticket.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticket.setSchemeId(0L);
        ticket.setSponsorId(0L);
        when(mockTicketRepository.find(0L)).thenReturn(ticket);

        // Configure TicketRepository.edit(...).
        final Ticket ticket1 = new Ticket();
        ticket1.setForwardedBy(0L);
        ticket1.setId(0L);
        ticket1.setClosed(false);
        ticket1.setSubject("subject");
        ticket1.setBody("body");
        final TicketMessage ticketMessage1 = new TicketMessage();
        ticketMessage1.setId(0L);
        ticketMessage1.setMessage("message");
        ticketMessage1.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticketMessage1.setOwnerName("ownerName");
        ticketMessage1.setProfileName("profileName");
        ticketMessage1.setShortDate("shortDate");
        ticketMessage1.setShortDateTime("shortDateTime");
        ticket1.setTicketMessages(new HashSet<>(Arrays.asList(ticketMessage1)));
        ticket1.setPriority(TicketPriority.HIGH);
        ticket1.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticket1.setSchemeId(0L);
        ticket1.setSponsorId(0L);
        when(mockTicketRepository.edit(any(Ticket.class))).thenReturn(ticket1);

        // Run the test
        final Ticket result = ticketsServiceUnderTest.closeTicket(0L);

        // Verify the results
    }

    @Test
    void testOpenTicket() {
        // Setup

        // Configure TicketRepository.find(...).
        final Ticket ticket = new Ticket();
        ticket.setForwardedBy(0L);
        ticket.setId(0L);
        ticket.setClosed(false);
        ticket.setSubject("subject");
        ticket.setBody("body");
        final TicketMessage ticketMessage = new TicketMessage();
        ticketMessage.setId(0L);
        ticketMessage.setMessage("message");
        ticketMessage.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticketMessage.setOwnerName("ownerName");
        ticketMessage.setProfileName("profileName");
        ticketMessage.setShortDate("shortDate");
        ticketMessage.setShortDateTime("shortDateTime");
        ticket.setTicketMessages(new HashSet<>(Arrays.asList(ticketMessage)));
        ticket.setPriority(TicketPriority.HIGH);
        ticket.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticket.setSchemeId(0L);
        ticket.setSponsorId(0L);
        when(mockTicketRepository.find(0L)).thenReturn(ticket);

        // Configure TicketRepository.edit(...).
        final Ticket ticket1 = new Ticket();
        ticket1.setForwardedBy(0L);
        ticket1.setId(0L);
        ticket1.setClosed(false);
        ticket1.setSubject("subject");
        ticket1.setBody("body");
        final TicketMessage ticketMessage1 = new TicketMessage();
        ticketMessage1.setId(0L);
        ticketMessage1.setMessage("message");
        ticketMessage1.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticketMessage1.setOwnerName("ownerName");
        ticketMessage1.setProfileName("profileName");
        ticketMessage1.setShortDate("shortDate");
        ticketMessage1.setShortDateTime("shortDateTime");
        ticket1.setTicketMessages(new HashSet<>(Arrays.asList(ticketMessage1)));
        ticket1.setPriority(TicketPriority.HIGH);
        ticket1.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticket1.setSchemeId(0L);
        ticket1.setSponsorId(0L);
        when(mockTicketRepository.edit(any(Ticket.class))).thenReturn(ticket1);

        // Run the test
        final Ticket result = ticketsServiceUnderTest.openTicket(0L);

        // Verify the results
    }

    @Test
    void testSetTicketExtraDetails1() {
        // Setup
        final Ticket ticket = new Ticket();
        ticket.setForwardedBy(0L);
        ticket.setId(0L);
        ticket.setClosed(false);
        ticket.setSubject("subject");
        ticket.setBody("body");
        final TicketMessage ticketMessage = new TicketMessage();
        ticketMessage.setId(0L);
        ticketMessage.setMessage("message");
        ticketMessage.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticketMessage.setOwnerName("ownerName");
        ticketMessage.setProfileName("profileName");
        ticketMessage.setShortDate("shortDate");
        ticketMessage.setShortDateTime("shortDateTime");
        ticket.setTicketMessages(new HashSet<>(Arrays.asList(ticketMessage)));
        ticket.setPriority(TicketPriority.HIGH);
        ticket.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticket.setSchemeId(0L);
        ticket.setSponsorId(0L);
        final List<Ticket> tickets = Arrays.asList(ticket);
        when(mockUserService.getUsersFullNameById(0L)).thenReturn("result");

        // Run the test
        final List<Ticket> result = ticketsServiceUnderTest.setTicketExtraDetails(tickets);

        // Verify the results
    }

    @Test
    void testSetTicketExtraDetails2() {
        // Setup
        final Ticket ticket = new Ticket();
        ticket.setForwardedBy(0L);
        ticket.setId(0L);
        ticket.setClosed(false);
        ticket.setSubject("subject");
        ticket.setBody("body");
        final TicketMessage ticketMessage = new TicketMessage();
        ticketMessage.setId(0L);
        ticketMessage.setMessage("message");
        ticketMessage.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticketMessage.setOwnerName("ownerName");
        ticketMessage.setProfileName("profileName");
        ticketMessage.setShortDate("shortDate");
        ticketMessage.setShortDateTime("shortDateTime");
        ticket.setTicketMessages(new HashSet<>(Arrays.asList(ticketMessage)));
        ticket.setPriority(TicketPriority.HIGH);
        ticket.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticket.setSchemeId(0L);
        ticket.setSponsorId(0L);

        when(mockUserService.getUsersFullNameById(0L)).thenReturn("result");

        // Run the test
        final Ticket result = ticketsServiceUnderTest.setTicketExtraDetails(ticket);

        // Verify the results
    }

    @Test
    void testForwardTicket() {
        // Setup
        final ForwardTicketVm forwardTicketVm = new ForwardTicketVm(0L, 0L, 0L);

        // Configure TicketRepository.find(...).
        final Ticket ticket = new Ticket();
        ticket.setForwardedBy(0L);
        ticket.setId(0L);
        ticket.setClosed(false);
        ticket.setSubject("subject");
        ticket.setBody("body");
        final TicketMessage ticketMessage = new TicketMessage();
        ticketMessage.setId(0L);
        ticketMessage.setMessage("message");
        ticketMessage.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticketMessage.setOwnerName("ownerName");
        ticketMessage.setProfileName("profileName");
        ticketMessage.setShortDate("shortDate");
        ticketMessage.setShortDateTime("shortDateTime");
        ticket.setTicketMessages(new HashSet<>(Arrays.asList(ticketMessage)));
        ticket.setPriority(TicketPriority.HIGH);
        ticket.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticket.setSchemeId(0L);
        ticket.setSponsorId(0L);
        when(mockTicketRepository.find(0L)).thenReturn(ticket);

        // Configure TicketRepository.edit(...).
        final Ticket ticket1 = new Ticket();
        ticket1.setForwardedBy(0L);
        ticket1.setId(0L);
        ticket1.setClosed(false);
        ticket1.setSubject("subject");
        ticket1.setBody("body");
        final TicketMessage ticketMessage1 = new TicketMessage();
        ticketMessage1.setId(0L);
        ticketMessage1.setMessage("message");
        ticketMessage1.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticketMessage1.setOwnerName("ownerName");
        ticketMessage1.setProfileName("profileName");
        ticketMessage1.setShortDate("shortDate");
        ticketMessage1.setShortDateTime("shortDateTime");
        ticket1.setTicketMessages(new HashSet<>(Arrays.asList(ticketMessage1)));
        ticket1.setPriority(TicketPriority.HIGH);
        ticket1.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        ticket1.setSchemeId(0L);
        ticket1.setSponsorId(0L);
        when(mockTicketRepository.edit(any(Ticket.class))).thenReturn(ticket1);

        // Run the test
        final Ticket result = ticketsServiceUnderTest.forwardTicket(forwardTicketVm);

        // Verify the results
    }

    @Test
    void testGetEntityManager() {
        // Setup

        // Run the test
        final EntityManager result = ticketsServiceUnderTest.getEntityManager();

        // Verify the results
    }
}
