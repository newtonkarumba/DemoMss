package com.systech.mss.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.controller.AuthenticationController;
import com.systech.mss.controller.vm.*;
import com.systech.mss.domain.*;
import com.systech.mss.repository.OtpConfigRepository;
import com.systech.mss.repository.OtpLogsRepository;
import com.systech.mss.repository.SessionRepository;
import com.systech.mss.service.NotificationService;
import com.systech.mss.service.SessionService;
import com.systech.mss.service.dto.FmListDTO;
import com.systech.mss.service.dto.LoginDTO;
import com.systech.mss.seurity.TokenProvider;
import com.systech.mss.vm.OutgoingSMSVM;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.*;

import static com.systech.mss.config.Constants.BEARER_PREFIX;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

public class AuthenticationControllerImpl extends BaseController implements AuthenticationController {
    @Inject
    private SessionService sessionService;

    @Inject
    private TokenProvider tokenProvider;

    @Context
    HttpServletRequest httpServletRequest;


    @Inject
    private SessionRepository sessionRepository;

    @Inject
    OtpLogsRepository otpLogsRepository;

    @Inject
    OtpConfigRepository otpConfigRepository;

    @Inject
    private NotificationService notificationService;

    @Override
    public Response login(@Valid LoginDTO loginDTO) {
        Config config = configRepository.getActiveConfig();
        if (config != null) {
            BaseVM baseVM;
            if (config.isEnableTwoFactorAuth()) {
                //CREATE OTP NEXT DATE ie OtpLogs.nextCheck>now()?true:false
                OtpLogs otpLogs = otpLogsRepository.checkIfOtpValid(loginDTO.getUsername().trim());
                baseVM = doLogin(config, loginDTO, otpLogs != null);//do not create session
            } else {
                baseVM = doLogin(config, loginDTO, true); //create session
            }
            if (baseVM instanceof ErrorVM) {
                return ErrorMsg(((ErrorVM) baseVM).getMsg());
            }
            if (baseVM instanceof SuccessVM) {
                SuccessVM successVM = (SuccessVM) baseVM;
                return SuccessMsg(successVM.getToken(), "Done", successVM.getData());
            }
        }
        return ErrorMsg("Please try again");
    }

    /**
     * 84400000ms=1day
     * 3600000ms=1hr
     *
     * @param config        C
     * @param loginDTO      L
     * @param createSession s
     * @return BaseVM
     */

    private BaseVM doLogin(Config config, LoginDTO loginDTO, boolean createSession) {
        //check member login indentifier
        Profile profile = profileService.getProfileByName("MEMBER");
        if (profile.getLoginIdentifier().equals(LoginIdentifier.PHONE)) {
            String username = loginDTO.getUsername().trim();
            if (username.startsWith("0")) {
                if (config.getCountryCode().startsWith("+")) {
                    username = config.getCountryCode() + loginDTO.getUsername().substring(1);
                } else {
                    username = "+" + config.getCountryCode() + loginDTO.getUsername().substring(1);
                }

            } else if (username.startsWith(config.getCountryCode().startsWith("+") ? config.getCountryCode().substring(1) : config.getCountryCode())) {
                username = "+" + loginDTO.getUsername();
            }
            loginDTO.setUsername(username);
        }
        Optional<User> userOptional = userRepository.findOneByLogin(loginDTO.getUsername().trim());
        if (!userOptional.isPresent()) {
            return ErrorVM.builder().success(false).msg("Username not Found").build();
        }
        if (userOptional.get().isLockedStatus()) {
            return ErrorVM.builder().success(false).msg("Your account is locked, Please contact Administrator").build();
        }
        User user;
        try {
            user = userService.authenticate(loginDTO);
            if (user != null) {
                if (!user.isActivated()) {
                    return ErrorVM.builder().success(false).msg("Account not activated").build();
                } else if (user.isDeactivatedByAdmin()) {
                    return ErrorVM.builder().success(false).msg("Account was locked by admin. Please contact your administrator").build();
                }
                //check member status before login
                if (user.getProfile().getName().equals("MEMBER")) {
                    FmListDTO fmListDTO = fmMemberClient.getMemberDetails(user.getUserDetails().getMemberId());
                    if (fmListDTO != null && fmListDTO.isSuccess()) {
                        Object memberDetails = fmListDTO.getRows().get(0);
                        String jsonString = new ObjectMapper().writeValueAsString(memberDetails);
                        JSONParser jsonParser = new JSONParser();
                        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
                        if (jsonObject != null) {
                            String membershipStatus = jsonObject.get("mbshipStatus").toString();
                            if (membershipStatus.equals("RETIRED") ||
                                    membershipStatus.equals("RETIRED_ILL_HEALTH") ||
                                    membershipStatus.equals("DEATH_IN_RETIREMENT") ||
                                    membershipStatus.equals("WITHDRAWN") ||
                                    membershipStatus.equals("DEATH_IN_SERVICE") ||
                                    membershipStatus.equals("INTERDICTION") ||
                                    membershipStatus.equals("DEFFERED") ||
                                    membershipStatus.equals("TRANSFERED") ||
                                    membershipStatus.equals("DELETED") ||
                                    membershipStatus.equals("TRANSFERRED_INTRA_INTRA") ||
                                    membershipStatus.equals("TRANSFERRED_INTRA_OUT")
                            ) {
                                return ErrorVM.builder()
                                        .success(false)
                                        .msg("Sorry, your membership status has no portal access rights. Please contact your scheme administrator").build();
                            }
                        }
                    }
                }

                String token = tokenProvider.generateToken(user, loginDTO.isRememberMe());
                String profileName = user.getProfile().getName();
                Map<Object, Object> data = new HashMap<>();
                data.put("user", user);
                data.put("profileName", profileName);
                data.put("sessionCreated", createSession); //USED TO CHECK FOR 2FA AUTHENTICATION
                Session session = new Session();
                session.setActive(true);
                session.setCreatedDate(LocalDateTime.now());
                session.setUserId(user.getId());
                session.setIpAddress(httpServletRequest.getRemoteAddr());
                if (createSession) {
                    Session session1 =
                            sessionService.saveSession(session);
                    data.put("sessionId", session1.getId());
                }
                return SuccessVM.builder().success(true).token(token).data(data).build();
            }

            return ErrorVM.builder().success(false).msg("Incorrect login credentials").build();

        } catch (AuthenticationException ex) {
            log.error(ex.getMessage());
            User user1 = userOptional.get();
//            log.info(user1.toString());
            int trial;
            try {
                trial = user1.getNumTrials();
                trial = trial + 1;
                user1.setNumTrials(trial);
                if (user1.getNumTrials() >= configRepository.getActiveConfig().getNumTrials()) {
                    user1.setLockedStatus(true);
                }
                userRepository.edit(user1);
            } catch (NullPointerException nullPointerException) {
                trial = 1;
                user1.setNumTrials(1);
                userRepository.edit(user1);
            }
            trial = configRepository.getActiveConfig().getNumTrials() - trial;
            if (trial == 0) {
                return ErrorVM.builder().success(false).msg("Incorrect credentials. Your account has been locked, please contact the administrator").build();
            }

            return ErrorVM.builder().success(false).msg("Incorrect credentials. You have " + trial + " Trials remaining").build();
        } catch (ParseException | JsonProcessingException e) {
            return ErrorVM.builder().success(false).msg(e.getLocalizedMessage()).build();
        }
    }

    @Override
    public Response sessionLogout(long sessionId) {
        Session session = sessionRepository.find(sessionId);
        if (session != null) {
            session.setStoppedAt(LocalDateTime.now());
            session.setActive(false);
            sessionRepository.edit(session);

            return Response.ok()
                    .header(AUTHORIZATION, BEARER_PREFIX)
                    .entity(SuccessVM.builder().success(true).build())
                    .build();
        }
        return Response.status(UNAUTHORIZED)
                .entity(ErrorVM.builder().success(false).msg("Session Invalid").build())
                .build();
    }

    @Override
    public Response otpInit(String login) {
        //GET ENABLED OTP IDENTIFIERS
        Optional<User> userOptional = userRepository.findOneByLogin(login.trim());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<OtpConfig> otpConfigs = otpConfigRepository.findEnabled();
            if (otpConfigs != null && otpConfigs.size() > 0) {
                List<OtpInitVM> otpInitVMS = new ArrayList<>();
                for (OtpConfig otpConfig : otpConfigs) {
                    OtpInitVM otpInitVM = new OtpInitVM();
                    switch (otpConfig.getOtpIdentifier()) {
                        case PUSH_NOTIFICATION:
                            otpInitVM.setOtpIdentifier(otpConfig.getOtpIdentifier().getName());
                            otpInitVM.setValue("NONE");
                            otpInitVMS.add(otpInitVM);
                            break;
                        case EMAIL:
                            String email = user.getEmail();
                            String[] strings = email.split("@");
                            try {
                                String sub = strings[0].substring(3, strings[0].length() - 2);
                                StringBuilder repl = new StringBuilder();
                                for (int i = 0; i < sub.length(); i++) {
                                    repl.append("*");
                                }
                                email = strings[0].replace(
                                        sub,
                                        repl.toString()
                                ) + strings[1];
                            } catch (Exception ignored) {
                            }
                            otpInitVM.setOtpIdentifier(otpConfig.getOtpIdentifier().getName());
                            otpInitVM.setValue(email);
                            otpInitVMS.add(otpInitVM);
                            break;

                        case SMS:
                            String phone = user.getUserDetails().getCellPhone();
                            try {
                                String sub = phone.substring(4, phone.length() - 2);
                                StringBuilder repl = new StringBuilder();
                                for (int i = 0; i < sub.length(); i++) {
                                    repl.append("*");
                                }
                                phone = phone.replace(
                                        sub,
                                        repl.toString()
                                );
                            } catch (Exception ignored) {
                            }
                            otpInitVM.setOtpIdentifier(otpConfig.getOtpIdentifier().getName());
                            otpInitVM.setValue(phone);
                            otpInitVMS.add(otpInitVM);
                            break;
                        default:
                    }
                }

                return SuccessMsg("Done", otpInitVMS);
            }
            return ErrorMsg("Configurations have not been enabled");
        }
        return ErrorMsg("Username '" + login + "' not found");
    }

    @Override
    public Response otpVerify(OtpVM otpVM) {
        OtpLogs otpLogs = otpLogsRepository.findUnExpiredOtp(otpVM.getUsername());
        if (otpLogs != null) {
            if (otpLogs.getToken().equalsIgnoreCase(otpVM.getOtp())) {
                Config config = configRepository.getActiveConfig();
                LoginDTO loginDTO = LoginDTO.from(otpVM);
                BaseVM baseVM = doLogin(config, loginDTO, true);
                if (baseVM instanceof ErrorVM) {
                    return ErrorMsg("Authentication Failed");
                }
                if (baseVM instanceof SuccessVM) {
                    int days;
                    do {
                        days = new Random().nextInt(5);
                    } while (days == 0);
                    try {
                        Calendar calendar=Calendar.getInstance();
                        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth+4);
                        otpLogs.setNextCheck(calendar.getTime());
                        otpLogsRepository.edit(otpLogs);
                    } catch (Exception e) {
                        otpLogs.setNextCheck(new Date());
                    }
                    otpLogsRepository.edit(otpLogs);
                    return SuccessMsg(((SuccessVM) baseVM).getToken(), "Done", ((SuccessVM) baseVM).getData());
                }
                return ErrorMsg("Error processing request");
            }
            return ErrorMsg("Invalid token");
        }
        return ErrorMsg("Token has expired");
    }

    @Override
    public Response sendOtpCode(String login, String otpIdentifier) {
        OtpLogs otpLogs = otpLogsRepository.findUnExpiredOtp(login);
        if (otpLogs != null) {
            otpLogs.setOtpIdentifier(OtpIdentifier.from(otpIdentifier));
            OtpLogs.from(otpLogs);
            otpLogsRepository.edit(otpLogs);
        } else {
            otpLogs = OtpLogs.newInstance(login, otpIdentifier);
            otpLogsRepository.create(otpLogs);
        }

        Optional<User> userOptional = userRepository.findOneByLogin(login.trim());
        switch (otpLogs.getOtpIdentifier()) {
            case SMS:
                fundMasterClient.sendSMS(new OutgoingSMSVM(
                        userOptional.isPresent() ? userOptional.get().getCellPhone() : login,
                        "OTP: " + otpLogs.getToken(),
                        "member",
                        true
                ));
//                fundMasterClient.sendSmsEtl(new SMSVM(userOptional.isPresent() ? userOptional.get().getCellPhone() : login,
//                        "OTP: " + otpLogs.getToken()));
                return SuccessMsg("Done", "An SMS has been sent");
            case EMAIL:
                if (userOptional.isPresent()) {
                    notificationService.sendNotification(
                            userOptional.get(),
                            EmailTemplatesEnum.OTP_VERIFICATION,
                            otpLogs.getToken(),
                            ""
                    );
                    return SuccessMsg("Done", "Verification code sent to email");
                }
                break;
            case PUSH_NOTIFICATION:
                return ErrorMsg("Option not available at the moment");
            default:
        }
        return ErrorMsg("Failed, please try again");
    }

    @Override
    public Response enableOtpConfig(String otpIdentifier, boolean enable) {
        OtpIdentifier identifier = OtpIdentifier.from(otpIdentifier);
        if (identifier != null) {
            OtpConfig otpConfig = otpConfigRepository.findByIdentifier(identifier);
            if (otpConfig != null) {
                otpConfig.setEnabled(enable);
                otpConfigRepository.edit(otpConfig);
            } else {
                otpConfig = OtpConfig.from(identifier);
                otpConfig.setEnabled(enable);
                otpConfigRepository.create(otpConfig);
            }
            return SuccessMsg("Done", identifier.getName() + (enable ? " enabled" : " disabled"));
        }
        return ErrorMsg("Identifier not found");
    }

    @Override
    public Response getUserOtps(String username) {
        List<OtpLogs> otpLogs = otpLogsRepository.findByLogin(username);
        return SuccessMsg("Done",
                otpLogs == null ? new ArrayList<>() : otpLogs
        );
    }
}
