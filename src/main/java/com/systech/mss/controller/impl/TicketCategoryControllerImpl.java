package com.systech.mss.controller.impl;

import com.systech.mss.controller.TicketCategoryController;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.Ticket;
import com.systech.mss.domain.TicketCategory;
import com.systech.mss.service.TicketCategoryService;
import com.systech.mss.service.TicketsService;
import com.systech.mss.service.UserService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class TicketCategoryControllerImpl implements TicketCategoryController {

    @Inject
    private TicketCategoryService ticketCategoryService;

    @Inject
    private TicketsService ticketsService;

    @Inject
    private UserService userService;

    @Override
    public Response getTicketCategories() {
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).data(ticketCategoryService.getTicketCategories()).build())
                .build();
    }

    @Override
    public Response getTicketCategoryRange(int start, int size) {
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).data(ticketCategoryService.getTicketCategoriesRange(start,size)).build())
                .build();
    }

    @Override
    public Response getTicketCategoryByiD(long id) {
        TicketCategory ticketCategory=ticketCategoryService.getTicketCategoryById(id);
        if(ticketCategory!= null){
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(ticketCategory).build())
                    .build();

        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("Ticket Category Not found").build())
                .build();
    }

    @Override
    public Response getTicketsInTicketCategory(long id) {
        //check if ticket category exists
        if(ticketCategoryService.checkIfTicketCategoryExists(id)) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(ticketsService.setTicketExtraDetails(ticketCategoryService.getTicketsInTicketCategory(id))).build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("ticket category not found").build())
                .build();
    }

    @Override
    public Response createTicketCategory(@Valid TicketCategory ticketCategory) {
        //check if ticket exists
        if(ticketCategoryService.checkIfTicketCategoryExists(ticketCategory.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(ErrorVM.builder().success(false).
                            msg("Ticket already exists").build())
                    .build();
        }
        ticketCategoryService.createTicketCategory(ticketCategory);
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).data(ticketCategory).msg("ticket category created").build())
                .build();
    }

    @Override
    public Response editTicketCategory(long id, @Valid TicketCategory ticketCategory) {
        //check if ticket exists
        if(ticketCategoryService.checkIfTicketCategoryExists(id)) {
            ticketCategoryService.editTicketCategory(ticketCategory);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(ticketCategory).msg("ticket edited").build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("ticket category not found").build())
                .build();
    }

    @Override
    public Response deleteTicketCategory(long id) {
        //check if ticket exists
        if(ticketCategoryService.checkIfTicketCategoryExists(id)) {
            ticketCategoryService.deleteTicketCategory(id);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("ticket category Deleted").build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("ticket category not found").build())
                .build();
    }
}
