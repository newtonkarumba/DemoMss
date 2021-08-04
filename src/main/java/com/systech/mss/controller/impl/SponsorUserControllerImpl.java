package com.systech.mss.controller.impl;

import com.systech.mss.controller.FMSponsorController;
import com.systech.mss.controller.SponsorUserController;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.SponsorUserVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.*;
import com.systech.mss.domain.common.AuthorizationLevel;
import com.systech.mss.domain.common.Clients;
import com.systech.mss.domain.common.UserStatus;
import com.systech.mss.repository.*;
import com.systech.mss.service.ActivityTrailService;
import com.systech.mss.service.ProfileService;
import com.systech.mss.service.SponsorUserService;
import com.systech.mss.service.UserService;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static com.systech.mss.config.Constants.EMAIL_ALREADY_USED_TYPE;
import static com.systech.mss.config.Constants.LOGIN_ALREADY_USED_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;


@Transactional
public class SponsorUserControllerImpl implements SponsorUserController {

    @Inject
    UserRepository userRepository;

    @Inject
    SponsorUserRepository sponsorUserRepository;

    @Inject
    private ActivityTrailRepository trailRepository;

    @Inject
    private SponsorConfigRepository sponsorConfigRepository;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    ProfileService profileService;

    @Inject
    private Logger log;

    @Inject
    UserService userService;



    @Inject
    SponsorUserPermissionRepository sponsorUserPermissionRepository;

    @Inject
    private ActivityTrailService activityTrailService;

    @Override
    public Response getSponsorUser(long id) {
        User userSponsor = userService.getUserById(id);
        return userSponsor !=null ?Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(userSponsor)
                                .build()
                ).build():
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(ErrorVM.builder().success(false).msg("No data found").build())
                        .build();
    }

    @Override
    public Response getSponsorUser(String name) {
        List<UserSponsor> sponsorUserList = sponsorUserRepository.getSponsorUserByName(name);
        return sponsorUserList!=null ?Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(sponsorUserList)
                                .build()
                ).build():
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(ErrorVM.builder().success(false).msg("No data found").build())
                        .build();
    }

    @Override
    public Response getSponsorUsers() {
        List<UserSponsor> sponsorUserList = sponsorUserRepository.getSponsorUsers();
        return sponsorUserList!=null ?Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(setExtraDetails(sponsorUserList))
                                .build()
                ).build():
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(ErrorVM.builder().success(false).msg("No data found").build())
                        .build();
    }

    @Override
    public Response getSponsorUsersPermissions() {
        List<Permission> sponsorUserPermissionsList = sponsorUserPermissionRepository.getSponsorUsersPermissions();
        return sponsorUserPermissionsList!=null ?Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(sponsorUserPermissionsList)
                                .build()
                ).build():
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(ErrorVM.builder().success(false).msg("No data found").build())
                        .build();
    }

    @Override
    public Response addSponsorUser(@Valid SponsorUserVM sponsorUserVM) {
        SponsorConfig sponsorConfig = sponsorConfigRepository.getActiveConfig();

        Optional<User> oneByLogin = userRepository.findOneByLogin(sponsorUserVM.getLogin());
        if (oneByLogin.isPresent()){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ErrorVM.builder().success(false).msg(
                            EMAIL_ALREADY_USED_TYPE
                    ).build())
                    .build();
        }
        String message="";
        Profile profile = profileService.getProfileByName(sponsorUserVM.getProfileName());

        if (sponsorConfig.getAuthorizationLevel().equals(AuthorizationLevel.LEVEL_TWO)) {
            if(profile.getName().equals("CLAIM AUTHORIZER")){
                //check if claim officer exist
                User claim_officer = userRepository.findUserBySchemeSponsorIdAndProfile(profileService.getProfileByName("CLAIM APPROVER"),
                        sponsorUserVM.getSchemeId(),sponsorUserVM.getSponsorId());

                if(claim_officer==null){
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(ErrorVM.builder().success(false).msg(
                                    "Please Add Claim Approver first before adding a Claim Authorizer"
                            ).build())
                            .build();
                }
                User claimAuthorizer = userRepository.findUserBySchemeSponsorIdAndProfile(profileService.getProfileByName("CLAIM AUTHORIZER"),
                        sponsorUserVM.getSchemeId(),sponsorUserVM.getSponsorId());
                if(claimAuthorizer!=null){
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(ErrorVM.builder().success(false).msg(
                                    "A Claim Authorizer Already Exists."
                            ).build())
                            .build();
                }

            }
            else if(profile.getName().equals("CLAIM APPROVER")){
                //check if authorizer exist
                User claim_authorizer = userRepository.findUserBySchemeSponsorIdAndProfile(profileService.getProfileByName("CLAIM AUTHORIZER"),
                        sponsorUserVM.getSchemeId(),sponsorUserVM.getSponsorId());
                if(claim_authorizer == null){
                    message=message+"Please add a Claim Authorizer to complete the process";
                }
                User claimOfficer = userRepository.findUserBySchemeSponsorIdAndProfile(profileService.getProfileByName("CLAIM APPROVER"),
                        sponsorUserVM.getSchemeId(),sponsorUserVM.getSponsorId());
                if(claimOfficer!=null){
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(ErrorVM.builder().success(false).msg(
                                    "A Claim Approver Already Exists!.Add a Claim Authorizer"
                            ).build())
                            .build();
                }
            }
        }
        else if(sponsorConfig.getAuthorizationLevel().equals(AuthorizationLevel.LEVEL_THREE)){
            if(profile.getName().equals("CLAIM APPROVER")){
                //check if claim officer exist
                User claim_officer = userRepository.findUserBySchemeSponsorIdAndProfile(profileService.getProfileByName("CLAIM REVIEWER"),
                        sponsorUserVM.getSchemeId(),sponsorUserVM.getSponsorId());

                if(claim_officer==null){
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(ErrorVM.builder().success(false).msg(
                                    "Please Add Claim Reviewer first before adding a Claim Approver"
                            ).build())
                            .build();
                }
                User claimReviewer = userRepository.findUserBySchemeSponsorIdAndProfile(profileService.getProfileByName("CLAIM APPROVER"),
                        sponsorUserVM.getSchemeId(),sponsorUserVM.getSponsorId());
                if(claimReviewer!=null){
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(ErrorVM.builder().success(false).msg(
                                    "A Claim Approver Already Exists!"
                            ).build())
                            .build();
                }
            }
            else if(profile.getName().equals("CLAIM AUTHORIZER")){
                //check if claim Reviewer exist
                User claim_Reviewer = userRepository.findUserBySchemeSponsorIdAndProfile(profileService.getProfileByName("CLAIM REVIEWER"),
                        sponsorUserVM.getSchemeId(), sponsorUserVM.getSponsorId());

                if(claim_Reviewer==null){
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(ErrorVM.builder().success(false).msg(
                                    "Please Add Claim Reviewer first before adding a Claim Authorizer"
                            ).build())
                            .build();
                }
                User claim_authorizer= userRepository.findUserBySchemeSponsorIdAndProfile(profileService.getProfileByName("CLAIM AUTHORIZER"),
                        sponsorUserVM.getSchemeId(),sponsorUserVM.getSponsorId());
                if(claim_authorizer!= null){
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(ErrorVM.builder().success(false).msg(
                                    "A Claim Authorizer Already Exists!"
                            ).build())
                            .build();
                }
            }
            else if(profile.getName().equals("CLAIM REVIEWER")){
                User claim_Reviewer = userRepository.findUserBySchemeSponsorIdAndProfile(profileService.getProfileByName("CLAIM APPROVER"),
                        sponsorUserVM.getSchemeId(),sponsorUserVM.getSponsorId());
                if(claim_Reviewer == null){
                    message=message+"Please add a Claim Approver";
                }
                //check if authorizer exist
                User claim_authorizer = userRepository.findUserBySchemeSponsorIdAndProfile(profileService.getProfileByName("CLAIM AUTHORIZER"),
                        sponsorUserVM.getSchemeId(),sponsorUserVM.getSponsorId());
                if(claim_authorizer == null){
                    message=message+" and a Claim Authorizer to Complete the process";
                }
                User officer = userRepository.findUserBySchemeSponsorIdAndProfile(profileService.getProfileByName("CLAIM REVIEWER"),
                        sponsorUserVM.getSchemeId(),sponsorUserVM.getSponsorId());
                if(officer!=null){
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(ErrorVM.builder().success(false).msg(
                                    "A Claim Reviewer already Exists!."
                            ).build())
                            .build();
                }
            }
        }
        log.warn("User being added: " +sponsorUserVM.toString());
        User user = userService.createUser(sponsorUserVM);


        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).msg(
                        "Successfully added user. "+message
                ).build())
                .build();

    }

    @Override
    public Response updateSponsorUser(@Valid SponsorUserVM sponsorUserVM) {
        User userSponsor = userService.editUser(sponsorUserVM);
        if(userSponsor!= null){
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(userSponsor).msg("user edited").build())
                    .build();
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();

    }

    @Override
    public Response resetSponsorUser(long id) {
        User userSponsor = userService.resetUser(id);
        if(userSponsor!= null){
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(userSponsor).msg("password edited").build())
                    .build();
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }


    @Override
    public Response dropSponsorUser(long id) {
        User user = userRepository.find(id);
        if (user == null)
            return ErrorMsg("User not Found");

        try {
            user.setUserStatus(UserStatus.DELETED);
            userRepository.edit(user);
            return SuccessMsg("User Deleted", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorMsg("System error, please try again");
    }

    public List<UserSponsor> setExtraDetails(List<UserSponsor > userSponsorList){

        for (UserSponsor u : userSponsorList){
            u.setProfileName(profileService.getProfileNameById(u.getProfileID()));
            u.setSponsorName(userService.getUsersFullNameById(u.getParentSponsor().getId()));
        }

        return userSponsorList;

    }
    private void logActivityTrail(long userId,String msg){
        trailRepository.create(trailRepository.getActivityTrail(userId,msg));
    }
    protected Response ErrorMsg(String msg) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        msg
                ).build())
                .build();
    }

    protected Response SuccessMsg(String msg, Object data) {
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).msg(msg).data(data).build())
                .build();
    }

}
