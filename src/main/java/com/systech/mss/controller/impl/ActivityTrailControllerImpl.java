package com.systech.mss.controller.impl;

import com.systech.mss.controller.ActivityTrailController;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.ActivityTrail;
import com.systech.mss.domain.User;
import com.systech.mss.repository.ActivityTrailRepository;
import com.systech.mss.repository.UserRepository;
import com.systech.mss.service.ActivityTrailService;
import com.systech.mss.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

public class ActivityTrailControllerImpl implements ActivityTrailController {

    @Inject
    ActivityTrailRepository trailRepository;

    @Inject
    private ActivityTrailService activityTrailService;

    @Inject
    UserRepository userRepository;

    @Inject
    UserService userService;

    @Override
    public Response getAll(long mssUserId) {
        activityTrailService.logActivityTrail(mssUserId,"viewed all activity trails");
        List<ActivityTrail> activityTrails = activityTrailService.getAll();
        return activityTrails != null ? Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(activityTrails)
                                .build()
                ).build() :
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(ErrorVM.builder().success(false).msg("No data found").build())
                        .build();
    }

    @Override
    public Response getByUserId(long userId) {
       activityTrailService.logActivityTrail(userId, "view activity logs");

        User user = userRepository.find(userId);
        if (user != null)
            return Response.ok()
                    .entity(SuccessVM.builder()
                            .success(true)
                            .data(setExtraDetails(trailRepository.getByUserId(user)))
                            .build()
                    ).build();

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Please try again"
                ).build())
                .build();
    }

    @Override
    public Response getActivityOfDate(String date) {
        return Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(trailRepository.getActivityOfDate(date))
                                .build()
                ).build();
    }

    @Override
    public Response SearchActivityTrail(long mssuserid,String name) {
        activityTrailService.logActivityTrail(mssuserid,"searched activity logs using +"+name);

        return Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(setExtraDetails(trailRepository.searchActivityTrail(mssuserid,name)))
                                .build()
                ).build();
    }

    @Override
    public Response filterActivityByDate(long mssuserid,String dateFrom, String dateTo) {
        activityTrailService.logActivityTrail(mssuserid,"filtered activity logs from +"+dateFrom+" to "+dateTo);

        return Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(trailRepository.filterActivityByDate(mssuserid,dateFrom,dateTo))
                                .build()
                ).build();
    }

    @Override
    public Response activityTrailMultiUser(long mssuserid,String name) {
        activityTrailService.logActivityTrail(mssuserid,"searched activity logs using +"+name);

        return Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(setExtraDetails(trailRepository.searchActivityTrailMultiUser(mssuserid, name)))
                                .build()
                ).build();
    }

    @Override
    public Response activityTrailMultiUser(long mssuserid,String dateFrom, String dateTo) {
        activityTrailService.logActivityTrail(mssuserid,"filtered activity logs from +"+dateFrom+" to "+dateTo);

        return Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(trailRepository.filterActivityByDateMultiUser(mssuserid,dateFrom,dateTo))
                                .build()
                ).build();
    }

    @Override
    public Response getActivityLogOfUserOnDate(long userId, String date) {

        return Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(trailRepository.getActivityLogOfUserOnDate(userId, date))
                                .build()
                ).build();
    }

    public List<ActivityTrail> setExtraDetails(List<ActivityTrail > activityTrails){

        for (ActivityTrail a : activityTrails){
            //USE RECURSION FOR EFFECTIVESS
           a.setUserName(userService.getUsersFullNameById(a.getUserId()));
           a.setCreatedDate(null);
        }
        return activityTrails;

    }
}
