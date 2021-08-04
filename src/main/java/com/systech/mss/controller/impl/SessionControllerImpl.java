package com.systech.mss.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.controller.SessionController;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.ActivityTrail;
import com.systech.mss.domain.Session;
import com.systech.mss.domain.User;
import com.systech.mss.repository.ActivityTrailRepository;
import com.systech.mss.repository.SessionRepository;
import com.systech.mss.service.ProfileService;
import com.systech.mss.service.SessionService;
import com.systech.mss.vm.SessionVM;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SessionControllerImpl implements SessionController {

    @Inject
    SessionRepository sessionRepository;
    @Inject
    SessionService sessionService;
    @Inject
    private ProfileService profileService;

    @Override
    public Response getSessions() {
        List<Session> sessions=sessionRepository.findAll();
        return sessions!=null?Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(setSessionsMoreDetails((sessions)))

                                .build()
                ).build():
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(ErrorVM.builder().success(false).msg("No data found").build())
                        .build();
    }

    @Override
    public Response filterSessions(String date) {
        return Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(sessionRepository.getSessionOfDate(date))
                                .build()
                ).build();
    }



    @Override
    public Response getSessionsSingle(long id) {
        return Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(sessionRepository.getBySessionId(id))
                                .build()
                ).build();
    }

    @Override
    public Response getSessionCount() throws IOException {
        long activeSessions = sessionService.getActiveSessionCount();
        long allSessions = sessionService.getAllSessions();
        ObjectMapper objectMapper=new ObjectMapper();
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("activeSessions", activeSessions)
                .add("allSessions", allSessions)
                .build();

        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).data(objectMapper.readValue(jsonObject.toString(),Object.class)).build())
                .build();

    }

    @Override
    public Response getSessionCountInAWeek() {
        List<SessionVM> sessionCountPerWeekdayList=sessionService.getWeeklySessions();
        if(sessionCountPerWeekdayList != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(sessionCountPerWeekdayList).build())
                    .build();
        }

     return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg("No data found").build())
                .build();


    }

    public List<Session> setSessionsMoreDetails(List<Session> sessions){
        Collections.reverse(sessions);
        for (Session session: sessions){
            session.setShortDate(session.getCreatedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            session.setShortDateTime(session.getCreatedDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
            session.setStoppedShortDateTime(session.getStoppedAt()!= null ? session.getStoppedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)):"-");
            session.setProfileName(session.getUserId().getProfile().getName());
            session.setUserName(session.getUserId().getUserDetails().getName());

        }
        return sessions;
    }



}
