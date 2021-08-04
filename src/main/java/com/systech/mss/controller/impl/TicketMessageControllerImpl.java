package com.systech.mss.controller.impl;

import com.systech.mss.controller.TicketMessageController;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.*;
import com.systech.mss.domain.common.Clients;
import com.systech.mss.repository.ConfigRepository;
import com.systech.mss.repository.TicketRepository;
import com.systech.mss.service.*;
import com.systech.mss.service.dto.MessageModelDTO;
import com.systech.mss.vm.SMSVM;
import org.slf4j.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketMessageControllerImpl implements TicketMessageController {

    @Inject
    private TicketsService ticketsService;

    @Inject
    private TicketRepository ticketRepository;

    @Inject
    private ActivityTrailService activityTrailService;

    @Inject
    private TicketMessageService ticketMessageService;

    @Inject
    private UserService userService;

    @Inject
    private NotificationService notificationService;

    @Override
    public Response getTicketMessage(long id) {
        if(ticketsService.checkIfTicketExists(id)){
            Ticket ticket=ticketsService.getTicketById(id);
            ticket.setNewOwnerRepliesCount(0L);
            ticket.setNewSupportRepliesCount(0L);
            ticketRepository.edit(ticket);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(ticketMessageService.setTicketMessageExtraDetails(ticketMessageService.getTicketMessage(id))).build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("Ticket Not found").build())
                .build();

    }

    @Override
    public Response getReloadTicketMessage(long id) {
        if(ticketsService.checkIfTicketExists(id)){
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(ticketMessageService.setTicketMessageExtraDetails(ticketMessageService.getTicketMessage(id))).build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("Ticket Not found").build())
                .build();

    }

    @Override
    public Response getTicketMessageByiD(long id) {
        if(ticketMessageService.checkIfTicketMessageExists(id)){
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(ticketMessageService.setTicketMessageExtraDetails(ticketMessageService.getTicketMessageById(id))).build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("Ticket Message Not found").build())
                .build();

    }

    @Override
    public Response getTicketsMessageByUserId(long id,long userId) {

        activityTrailService.logActivityTrail(userId, "viewed tickets Created");
        //check if ticket and user exists
        if(userService.checkIfUserExists(userId) && ticketsService.checkIfTicketExists(id)){
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(ticketMessageService.setTicketMessageExtraDetails(ticketMessageService.getTicketMessageByUserId(id, userId))).build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("Ticket Message or user Not found").build())
                .build();
    }

    @Override
    public Response createTicketMessage(@Valid TicketMessage ticketMessage) {
        //check if ticket exists
        if(ticketMessageService.checkIfTicketMessageExists(ticketMessage.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(ErrorVM.builder()
                            .success(false)
                            .msg("Ticket already exists")
                            .build())
                    .build();
        }
        Ticket ticket=ticketsService.getTicketById(ticketMessage.getTicket().getId());
        //check if reply is by owner or support
        //if owner replies increase their new replies count
        if(ticket.getCreatedBy().getId()==ticketMessage.getCreatedBy().getId()){
            long count=ticket.getNewOwnerRepliesCount();
            count=count+1;
            ticket.setNewOwnerRepliesCount(count);
        }
        else {
            long count=ticket.getNewOwnerRepliesCount();
            count=count+1;
            ticket.setNewSupportRepliesCount(count);
        }
        ticketRepository.edit(ticket);

        ticketMessageService.createTicketMessage(ticketMessage);

        //send mail
        if(ticket.getCreatedBy().getId()!=ticketMessage.getCreatedBy().getId()){
            User user = userService.getUserById(ticket.getCreatedBy().getId());
            notificationService.sendNotification(
                    user,
                    EmailTemplatesEnum.TICKET_REPLY,
                    String.valueOf(ticket.getId()),
                    ticketMessage.getMessage(),
                    userService.getUserById(ticketMessage.getCreatedBy().getId()).getUserDetails().getName(),
                    DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm a").format(ticketMessage.getCreatedDate())
            );
        }
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder()
                        .success(true)
                        .data(ticketMessage)
                        .msg("Reply sent successfully")
                        .build())
                .build();
    }

    @Override
    public Response editTicketMessage(long id, @Valid TicketMessage ticketMessage) {
        //check if ticket exists
        if(ticketMessageService.checkIfTicketMessageExists(id)) {
            ticketMessageService.editTicketMessage(ticketMessage);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(ticketMessage).msg("ticket message edited").build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("ticket category not found").build())
                .build();
    }

    @Override
    public Response deleteTicketMessage(long id) {
        //check if ticket exists
        if(ticketMessageService.checkIfTicketMessageExists(id)) {
            ticketMessageService.deleteTicketMessage(id);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("ticket message Deleted").build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("ticket Message not found").build())
                .build();
    }
}
