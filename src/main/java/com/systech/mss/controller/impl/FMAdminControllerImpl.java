package com.systech.mss.controller.impl;

import com.systech.mss.controller.FMAdminController;
import com.systech.mss.controller.vm.CreateAdminVM;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.*;
import com.systech.mss.fileupload.FileModel;
import com.systech.mss.repository.ProfileRepository;
import com.systech.mss.service.ActivityTrailService;
import com.systech.mss.service.FMAdminClient;
import com.systech.mss.service.NotificationService;
import com.systech.mss.service.ProfileService;
import com.systech.mss.service.dto.FmListDTO;
import com.systech.mss.service.dto.MemberDTO;
import com.systech.mss.seurity.PasswordEncoder;
import com.systech.mss.vm.DocumentsVM;
import com.systech.mss.vm.OutgoingSMSVM;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.systech.mss.config.Constants.LOGIN_ALREADY_USED_TYPE;
import static com.systech.mss.util.StringUtil.generateRandomPassword;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;


public class FMAdminControllerImpl extends BaseController implements FMAdminController {

    @Inject
    private FMAdminClient fmAdminClient;

    @Inject
    private ProfileService profileService;

    @Inject
    ActivityTrailService activityTrailService;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private NotificationService notificationService;


    @Override
    public Response getAdminByStaffNo(long StaffNo) {
        activityTrailService.logActivityTrail(StaffNo, "Viewed All Members");

        FmListDTO fmListDTO = fmAdminClient.getAdminDetails(StaffNo);
        if (fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows().get(0)).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getAdminDetailsAll() {
        //activityTrailService.logActivityTrail(mssUserId, "Viewed All Admins");
        List<User> users = userService.getAdminUsers();

        return users != null ? Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(users)
                                .build()
                ).build() :
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(ErrorVM.builder().success(false).msg("No data found").build())
                        .build();

    }


    @Override
    public Response createAdmin(@Valid Admins admins) {
        activityTrailService.logActivityTrail(admins.getUserId(), "created admin of email " + admins.getEmail());

        return userRepository.findOneByLogin(admins.getEmail().toLowerCase())
                .map(user -> Response.status(BAD_REQUEST).type(APPLICATION_JSON_TYPE)
                        .entity(
                                ErrorVM.builder().msg(LOGIN_ALREADY_USED_TYPE).success(false).build()).build())
                .orElseGet(() -> {
                    Profile profile = profileService.getProfileByName("ADMIN");

                    MemberDTO memberDTO = fundMasterClient.checkMemberIfExists1(
                            "EMAIL",
                            admins.getEmail(),
                            "ADMINISTRATOR"
                    );
                    if (memberDTO.isSuccess() && !memberDTO.getName().equals("")) {
                        User user = new User();
                        user.setActivated(true);
                        String pwd = generateRandomPassword(8);
                        String encryptedPassword = passwordEncoder.encode(pwd);
                        user.setPassword(encryptedPassword);
                        user.setEmail(admins.getEmail());
                        user.setLogin(admins.getEmail());
                        String name = memberDTO.getName();
                        //split name by space
                        if (name != null) {
                            String[] nameParts = name.trim().split("\\s+");
                            if (nameParts.length >= 2) {
                                user.setFirstName(nameParts[0]);
                                user.setLastName(nameParts[1]);
                            }
                        }
                        user.setFmIdentifier(profile.getName());
                        user.setProfileById(profile.getId());
                        user.setUserDetails(memberDTO);

                        fundMasterClient.sendSMS(new OutgoingSMSVM(user.getCellPhone(), "Dear" + user.getFirstName() + "you successfully created an admin", user.getProfile().getName(), true));

                        notificationService.sendNotification(
                                user,
                                EmailTemplatesEnum.ADMIN_ACCOUNT_ACTIVATION,
                                admins.getEmail(),
                                pwd
                        );
                        userRepository.create(user);
                        return Response.status(Response.Status.OK)
                                .entity(SuccessVM.builder().data(user).msg("Admin created successfully and email sent to the admin").success(true).build())
                                .build();
                    }
                    return ErrorMsg("Admin does not exist in core Xe");
                });
    }

    @Override
    public Response adminCreateAdmin(CreateAdminVM adminVM) {
        Profile profile = profileRepository.findByName("ADMIN");
        if (profile == null) {
            return ErrorMsg("Profile does not exist");
        }
        return userRepository.findOneByLogin(adminVM.getLogin())
                .map(user -> ErrorMsg("Username already used"))
                .orElseGet(() -> userRepository.findOneByLogin(adminVM.getEmail())
                        .map(user -> ErrorMsg("Email already used"))
                        .orElseGet(() -> {
                            User user = new User();
                            user.setActivated(true);
                            String pwd = generateRandomPassword(8);
                            String encryptedPassword = passwordEncoder.encode(pwd);
                            user.setPassword(encryptedPassword);
                            user.setEmail(adminVM.getEmail());
                            user.setLogin(adminVM.getLogin());
                            user.setFirstName(adminVM.getFirstName());
                            user.setLastName(adminVM.getLastName());
                            user.setFmIdentifier(profile.getName());
                            user.setProfileById(profile.getId());

                            MemberDTO memberDTO = new MemberDTO();
                            memberDTO.setName(user.getLastName() + " " + user.getFirstName());
                            memberDTO.setEmail(user.getEmail());
                            memberDTO.setCellPhone(adminVM.getCellPhone());
                            memberDTO.setProfile(profile.getName());
                            memberDTO.setMbshipStatus("ACTIVE");
                            memberDTO.setAccountStatus("ACTIVE");

                            user.setUserDetails(memberDTO);
                            notificationService.sendNotification(
                                    user,
                                    EmailTemplatesEnum.ADMIN_ACCOUNT_ACTIVATION,
                                    adminVM.getLogin(),
                                    pwd
                            );
                            user = userRepository.create(user);
                            if (user != null)
                                return Response.status(Response.Status.OK)
                                        .entity(SuccessVM.builder().data(user).msg("Admin created successfully and " +
                                                "password to email").success(true).build())
                                        .build();
                            return ErrorMsg("Failed, please try again");
                        }));

    }

    @Override
    public Response adminEditAdmin(long mssUserId, CreateAdminVM adminVM) {
        User user = userRepository.find(adminVM.getId());
        if (user != null) {
            user.setEmail(adminVM.getEmail());
            user.getUserDetails().setCellPhone(adminVM.getCellPhone());
            user.setLogin(adminVM.getLogin());
            user = userRepository.edit(user);
            if (user != null) {
                mailService.sendPlainEmail(
                        user.getEmail(),
                        "ACCOUNT ACTION",
                        "Dear " + user.getFirstName() + ",<br/>Your account details have been updated"
                );
                return SuccessMsg("Done", null);
            }
            return ErrorMsg("Failed to update user details");
        }
        return ErrorMsg("User not found");
    }

    @Override
    public Response lockAccount(long id) {

        User user = userRepository.find(id);
        if (user != null) {
            user.setDeactivatedByAdmin(true);
            userRepository.edit(user);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("Account Locked").build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("User not found").build())
                .build();
    }

    @Override
    public Response sendFileToUsers(long mssUserId, MultipartFormDataInput input) {
        User user = userRepository.find(mssUserId);
        if (user == null)
            return ErrorMsg("Kindly login in");


        try {
            Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
            String profile = (uploadForm.get("forProfileId").get(0).getBodyAsString());
            String comments = (uploadForm.get("comments").get(0).getBodyAsString());

            List<FileModel> fileModels = upload(input);
            if (fileModels == null) {
                return ErrorMsg("Document not uploaded");
            }

            for (FileModel fileModel :
                    fileModels) {
                DocumentsVM documentsVM = new DocumentsVM();
                documentsVM.setUserId(user.getId());
                documentsVM.setFileName(fileModel.getFileName());
                documentsVM.setOriginalFileName(fileModel.getOriginalFileName());
                documentsVM.setForProfileId(Long.parseLong(profile.trim()));
                documentsVM.setFilePath(fileModel.getFilePath());
                documentsVM.setToUserId(user.getId());
                documentsVM.setComments(comments);

                Documents documents = Documents.getDocumentsInstance(user, documentsVM);
                documentRepository.create(documents);
            }

            try {
                Profile toProfile = profileRepository.find(Long.parseLong(profile));
                if (toProfile != null) {
                    logActivityTrail(mssUserId, "Uploaded document for profile " + toProfile.getName());
                    List<User> users = userRepository.findByProfile(toProfile);
                    if (users != null) {
                        for (User user1 : users) {
                            if (user1.getEmail() != null) {
                                mailService.sendPlainEmail(
                                        user1.getEmail(),
                                        "Shared Portal Document",
                                        comments
                                );
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return SuccessMsg("Done", null);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return ErrorMsg("System error, please try again");
    }
}


