package com.systech.mss.controller.impl;

import com.systech.mss.controller.TicketIssuesController;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.TicketIssues;
import com.systech.mss.repository.TicketIssuesRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.List;

@Transactional
public class TicketIssuesControllerImpl implements TicketIssuesController {

    @Inject
    private TicketIssuesRepository ticketIssuesRepository;

    @Override
    public Response getTicketIssues() {
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder()
                        .success(true)
                        .data(setTicketIssuesExtraDetails(ticketIssuesRepository.findAll()))
                        .build())
                .build();
    }

    @Override
    public Response createTicketIssue(@Valid TicketIssues ticketIssues) {
        ticketIssuesRepository.create(ticketIssues);
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).data(ticketIssues).msg("ticket issue created").build())
                .build();
    }
    
    @Override
    public Response deleteTicketIssue(@Valid TicketIssues ticketIssues) {
        ticketIssuesRepository.remove(ticketIssues);
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).data(ticketIssues).msg("ticket issue deleted").build())
                .build();
    }
    
    @Override
    public Response deleteTicketIssueById(long id) {
        ticketIssuesRepository.deleteById(id);
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).msg("ticket issue deleted").build())
                .build();
    }

    public List<TicketIssues> setTicketIssuesExtraDetails(List<TicketIssues> ticketIssues){
        for(TicketIssues ticketIssue:ticketIssues){
            ticketIssue.setProfileName(ticketIssue.getProfileId().getName());
        }
        return ticketIssues;
    }
}
