package com.systech.mss.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.controller.TicketController;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.ForwardTicketVm;
import com.systech.mss.controller.vm.PostFormByIdVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.*;
import com.systech.mss.domain.common.Clients;
import com.systech.mss.repository.ConfigRepository;
import com.systech.mss.service.*;
import com.systech.mss.repository.TicketRepository;
import com.systech.mss.service.dto.MessageModelDTO;
import com.systech.mss.vm.SMSVM;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class TicketControllerImpl implements TicketController {

    @Inject
    TicketsService ticketsService;

    @Inject
    ActivityTrailService activityTrailService;

    @Inject
    UserService userService;


    @Inject
    MailService mailService;

    @Inject
    ProfileService profileService;

    @Inject
    TicketRepository ticketRepository;

    @Inject
    ConfigRepository configRepository;

    @Inject
    FundMasterClient fundMasterClient;

    @Inject
    private NotificationService notificationService;

    @Override
    public Response getTickets(int start, int size) {
        if(start >= 0 && size > 0){
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().
                            success(true)
                            .data(ticketsService.setTicketExtraDetails(ticketsService.getTicketsRange(start,size)))
                            .build())
                    .build();
        }
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder()
                        .success(true)
                        .data(ticketsService.setTicketExtraDetails(ticketsService.getTickets()))
                        .build())
                .build();
    }

    @Override
    public Response getTicketsRange(int start, int size) {
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).data(ticketsService.setTicketExtraDetails(ticketsService.getTicketsRange(start,size))).build())
                .build();
    }

    @Override
    public Response getTicketByiD(long id) {
        Ticket ticket=ticketsService.getTicketById(id);
        if(ticket!= null){
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(ticketsService.setTicketExtraDetails(ticket)).build())
                    .build();

        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("Ticket Not found").build())
                .build();

    }

    @Override
    public Response createTicket(@Valid Ticket ticket) {
        //check if ticket exists
        if(ticketsService.checkIfTicketExists(ticket.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(ErrorVM.builder().success(false).
                            msg("Ticket already exists").build())
                    .build();
        }
        ticket=ticketsService.createTicket(ticket);
        //mail notification
        User user= userService.getUserById(ticket.getCreatedBy().getId());
        notificationService.sendNotification(
                user,
                EmailTemplatesEnum.TICKET_RAISED,
                String.valueOf(ticket.getId())
        );

//        mailService.sendTicketRaisedMail(user,ticket);

        //Log activity Log
        activityTrailService.logActivityTrail(
                ticket.getCreatedBy().getId(),
                "Created a ticket"
        );

        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).data(ticket).msg("Ticket of id "+ticket.getId() + " has been created successfully").build())
                .build();
    }

    @Override
    public Response editTicket(long id, @Valid Ticket ticket) {
        //check if ticket exists
        if(ticketsService.checkIfTicketExists(id)) {
            ticketsService.editTicket(ticket);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(ticket).msg("ticket edited").build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("ticket not found").build())
                .build();

    }

    @Override
    public Response closeTicket(long id) {
        //check if ticket exists
        if(ticketsService.checkIfTicketExists(id)) {
            ticketsService.closeTicket(id);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("ticket closed").build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("ticket not found").build())
                .build();
    }

    @Override
    public Response openTicket(long id) {
        //check if ticket exists
        if(ticketsService.checkIfTicketExists(id)) {
            ticketsService.openTicket(id);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("ticket opened").build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("ticket not found").build())
                .build();
    }

    @Override
    public Response getTicketByRecipientProfile(String profileName, long sponsorId, long schemeId) {
        List<Ticket> tickets =ticketsService.getTicketByRecipientProfile(profileService.getProfileIdByName(profileName), sponsorId, schemeId);
        return getResponse(tickets);
    }

    @Override
    public Response getRecentTickets(String profileName,int start,int size) {

        List<Ticket> tickets =ticketsService.getRecentTickets(profileService.getProfileIdByName(profileName),start,size);
        return getResponse(tickets);
    }

    private Response getResponse(List<Ticket> tickets) {
        if(tickets.size()>0){
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(ticketsService.setTicketExtraDetails(tickets)).build())
                    .build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ErrorVM.builder().success(false).
                            msg("tickets not found").build())
                    .build();
        }
    }

    @Override
    public Response getCountOfOpenSupportTicket(String profileName) throws IOException {
        long count=ticketsService.getCountOfOpenSupportTicket(profileService.getProfileIdByName(profileName));
        ObjectMapper objectMapper=new ObjectMapper();
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("count", count)
                .build();
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).data(objectMapper.readValue(jsonObject.toString(),Object.class)).build())
                .build();

    }

    @Override
    public Response ForwardTicket(ForwardTicketVm forwardTicketVm) {
        Ticket ticket=ticketsService.getTicketById(forwardTicketVm.getTicketId());
        if (ticket != null) {
            ticket.setProfileById(forwardTicketVm.getProfileId());
            ticket.setForwardedBy(forwardTicketVm.getUserId());
            ticketRepository.edit(ticket);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(ticket).build())
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).
                        msg("tickets not forwarded").build())
                .build();
    }

    @Override
    public Response getTicketRaisedBetween(long mssuserid, String dateFrom, String dateTo) {

        activityTrailService.logActivityTrail(mssuserid, "filtered tickets raised from +" + dateFrom + " to " + dateTo);

        return Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(ticketRepository.filter(dateFrom, dateTo))
                                .build()
                ).build();
    }

    @Override
    public Response getTicketRaisedBetween(long mssuserid, String profileName, String dateFrom, String dateTo) {

        activityTrailService.logActivityTrail(mssuserid, "filtered tickets of profile " + profileName + " raised from +" + dateFrom + " to " + dateTo);

        Profile profile = profileService.getProfileByName(profileName);
        return Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(ticketRepository.filter(profile, dateFrom, dateTo))
                                .build()
                ).build();
    }

    @Override
    public Response getTicketRaisedUnreplied(long mssuserid) {
        return null;
    }
}
