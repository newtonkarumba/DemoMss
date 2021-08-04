package com.systech.smileIdentity.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.systech.mss.callback.ProcessCallBack;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.StatusConfig;
import com.systech.mss.domain.User;
import com.systech.mss.repository.UserRepository;
import com.systech.mss.service.ActivityTrailService;
import com.systech.mss.service.FundMasterClient;
import com.systech.mss.service.UserService;
import com.systech.mss.service.dto.FmListDTO;
import com.systech.mss.seurity.DateUtils;
import com.systech.mss.vm.OutgoingSMSVM;
import com.systech.smileIdentity.DTO.*;
import com.systech.smileIdentity.Service.SelfieService;
import com.systech.smileIdentity.Service.vm.*;
import com.systech.smileIdentity.controller.SmileIdentityController;
import com.systech.smileIdentity.model.*;
import com.systech.smileIdentity.repository.SelfiePaymentRepository;
import com.systech.smileIdentity.repository.SelfieResultsRepository;
import com.systech.smileIdentity.repository.SmileIdentityConfigRepository;
import org.slf4j.Logger;
import smile.identity.core.Options;
import smile.identity.core.PartnerParameters;
import smile.identity.core.WebApi;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class SmileIdentityControllerImpl implements SmileIdentityController {

    @Inject
    private SelfieService selfieService;

    @Inject
    public Logger logger;

    @Inject
    private SelfieResultsRepository selfieResultsRepository;


    @Inject
    private SmileIdentityConfigRepository smileIdentityConfigRepository;


    @Inject
    private UserService userService;


    @Inject
    private ActivityTrailService activityTrailService;


    @Inject
    private SelfiePaymentRepository selfiePaymentRepository;


    @Inject
    private FundMasterClient fundMasterClient;



    @Inject
    private UserRepository userRepository;

    @Override
    public Response smileIdentityCallbackPost(String json) {
        logger.info("callback called POST>>>"+json);
        logger.info(json);
        String message = "";
        boolean success = false;
        Gson gson = new Gson();
        Result result = gson.fromJson(json, Result.class);
        logger.info(result.toString());

        SelfieResults resultsByJobId = selfieResultsRepository.getResultsByJobId(result.getPartnerParams().getJob_id());
        //clear db
//        selfieResultsRepository.deleteById(resultsByJobId.getId());

        if(result.getPartnerParams().getJob_type().equals("2")){ //authentication
            switch (result.getResultCode()) {
                case "1220":
                    message = "Human Judgement  Passed";
//                    sendResultsToFM(result, SelfieAction.REGISTRATION,processSelfieDto,cycleId);
//                    sendResultsToFM(result,SelfieAction.AUTHENTICATION);
                    success = true;
                    break;
                case "1221":
                    message = "Human Judgement  FAILED  Human Compare Failed";
                    success = false;
                    break;
                case "1222":
                    message = "Human Judgement  FAILED  Spoof Detected";
                    success = false;
                    break;
                default:
                    message = "Failed";
                    success = false;
            }
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(success).data(result).msg(message).build())
                    .build();

        }
        else if(result.getPartnerParams().getJob_type().equals("4")){
            switch (result.getResultCode()) {
                case "1240":
                    message = "PASSED  Human Judgement";

//                    sendResultsToFM(result,SelfieAction.REGISTRATION);
                    success = true;
                    break;
                case "1241":
                    message = "FAILED  Image Unusable";
                    success = false;
                    break;
                case "1242":
                    message = "Spoof detected  Human Judgement";
                    success = false;
                    break;
                default:
                    message = "Failed";
                    success = false;
            }
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(success).data(result).msg(message).build())
                    .build();
        }
        return null;
    }

    @Override
    public Response testRegister(String userId, String jobId) {
        String jsonString = selfieService.registerSelfie(userId, jobId);
        Gson gson = new Gson();
        SmileIdentityResponseDTO smileIdentityResponseDTO = gson.fromJson(jsonString, SmileIdentityResponseDTO.class);
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).data(smileIdentityResponseDTO).msg("Done").build())
                .build();
    }

    @Override
    public Response processSelfie(@Valid ProcessSelfieDto processSelfieDto, long cycleId, long mssUserId) {

        //update payment to be used
        SelfieAction action=processSelfieDto.getJobType() == 2 ? SelfieAction.AUTHENTICATION : SelfieAction.REGISTRATION ;
        SelfiePayment selfiePayment= selfiePaymentRepository.getUserPendingOrUnusedPaymentPerAction(Long.parseLong(processSelfieDto.getUserId()),action);
        selfiePayment.setPaymentStatus(PaymentStatus.PAID_SUCCESSFUL_USED);
        selfiePaymentRepository.edit(selfiePayment);

        activityTrailService.logActivityTrail(mssUserId,"Processed selfie for user id :"+processSelfieDto.getUserId()+" for job type :"+processSelfieDto.getJobType());

        String message = "";
        boolean success = false;
        processSelfieDto.setJobId("Job_" + (DateUtils.getMillitime()));
        SmileIdentityConfig smileIdentityConfig= smileIdentityConfigRepository.getActiveConfig();
        User user=userService.getUserByMemberIdOrPensionerId(Long.parseLong(processSelfieDto.getUserId()));
        long authTrials=user.getAuthenticationTrials();
        if(processSelfieDto.getJobType() == 2){
            logger.info("check trials");
            if(user != null){
                logger.info("checking trials");
                if(user.getAuthenticationTrials()>=smileIdentityConfig.getAuthenticationTrials()){
                    logger.info("checking ... trials");
                    return Response.status(Response.Status.FORBIDDEN)
                            .entity(SuccessVM.builder().success(true).msg("Authentication trials exhausted, Please contact your scheme administrator").build())
                            .build();
                }
            }
        }
        String jsonString = selfieService.processSelfie(processSelfieDto);
        logger.info(jsonString);
        Gson gson = new Gson();
        SmileIdentityResponseDTO smileIdentityResponseDTO = gson.fromJson(jsonString, SmileIdentityResponseDTO.class);


        if (smileIdentityResponseDTO.isJob_complete() && smileIdentityResponseDTO.isJob_success()) {
            if (processSelfieDto.getJobType() == 1) { //registration with id verification

                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).msg("Registered Successfully").build())
                        .build();
            } else if (processSelfieDto.getJobType() == 2) { //authentication
                switch (smileIdentityResponseDTO.getResult().getResultCode()) {
                    case "0921":
                        message = "FAILED  No Face Found";
                        success = false;
                        authTrials=authTrials+1;
                        break;
                    case "0922":
                        message = "FAILED  Image Quality Judged Too Poor";
                        success = false;
                        authTrials=authTrials+1;
                        break;
                    case "0820":
                        message = "Machine Judgement PASSED, Authenticated successfully";
                        sendResultsToFM(processSelfieDto,
                                smileIdentityResponseDTO.getResult().getActions().getLiveness_Check(),
                                smileIdentityResponseDTO.getResult().getConfidenceValue(),
                                SelfieAction.AUTHENTICATION,
                                cycleId
                        );
                        success = true;
                        authTrials=0;
                        break;
                    case "0821":
                        message = "Machine Judgement  FAILED  COMPARISON";
                        success = false;
                        authTrials=authTrials+1;
                        break;
                    case "0822":
                    case "0824":
                    case "0825":
                        message=recursionCheckJobStatus(processSelfieDto,cycleId);
                        if(message.contains("FAILED")){
                            success = false;
                            authTrials=authTrials+1;
                        }
                        else{
                            success = true;
                            authTrials=0;
                        }
                        break;
                    case "0823":
                        message = "Machine Judgement  FAILED  Possible Spoof";
                        success = false;
                        authTrials=authTrials+1;
                        break;
                    default:

                }
                //send sms
                fundMasterClient.sendSMS(new OutgoingSMSVM(
                        processSelfieDto.getIdPhoneNumber(),
                        "Dear " +processSelfieDto.getIdFirstName()+". On recent Selfie action, "+message,
                        "pensioner",
                        true
                ));
                //update user
                user.setAuthenticationTrials(authTrials);
                userRepository.edit(user);
                if(success) {
                    return Response.status(Response.Status.OK)
                            .entity(SuccessVM.builder().success(true).msg(message).build())
                            .build();
                }
                else{
                    long remainingTrials=smileIdentityConfig.getAuthenticationTrials()-authTrials;
                    ErrorMsg(message+". You have "+remainingTrials+" Authentication trials remaining");
                }

            } else if (processSelfieDto.getJobType() == 4) { //Register Without ID
                switch (smileIdentityResponseDTO.getResult().getResultCode()) {
                    case "0941":
                        message = "FAILED  No Face Found";
                        success = false;
                        break;
                    case "0942":
                        message = "FAILED  Image Quality Judged Too Poor";
                        success = false;
                        break;
                    case "0840":
                        message = "PASSED  Machine Judgement, Registered successfully";
                        success = true;
                        sendResultsToFM(processSelfieDto,
                                smileIdentityResponseDTO.getResult().getActions().getLiveness_Check(),
                                smileIdentityResponseDTO.getResult().getConfidenceValue(),
                                SelfieAction.REGISTRATION,
                                cycleId
                        );
                        break;
                    case "0841":
                        message = "FAILED  Machine Judgement  Compare Failed";
                        success = false;
                        break;
                    case "0842":
                    case "0844":
                    case "0846":
                        success = true;
                        message=recursionCheckJobStatus(processSelfieDto,cycleId);
                        break;
                    case "0843":
                        message = "FAILED  Possible Spoof  Machine Judgement *";
                        success = false;
                        break;
                    default:

                }
                //send sms
                fundMasterClient.sendSMS(new OutgoingSMSVM(
                        processSelfieDto.getIdPhoneNumber(),
                        "Dear " +processSelfieDto.getIdFirstName()+". On recent Selfie action, "+message,
                        "pensioner",
                        true
                ));
                if(success) {
                    return Response.status(Response.Status.OK)
                            .entity(SuccessVM.builder().success(true).msg(message).build())
                            .build();
                }
                else {
                    return ErrorMsg(message);
                }

            }
        }
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(false).msg("Failed please try again").build())
                .build();
    }

    @Override
    public Response getAllSelfieConfigs() {
        List<SmileIdentityConfig> smileIdentityConfigs = smileIdentityConfigRepository.findAll();

        return smileIdentityConfigs != null ? Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(smileIdentityConfigs)
                                .build()
                ).build() :
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(ErrorVM.builder().success(false).msg("No data found").build())
                        .build();
    }


    @Override
    public Response createConfig(@Valid SmileIdentityConfig smileIdentityConfig) {
        List<SmileIdentityConfig> smileIdentityConfigs = smileIdentityConfigRepository.findAll();
        //set existing configs inactive
        for (SmileIdentityConfig configs : smileIdentityConfigs) {
            configs.setStatusConfig(StatusConfig.INACTIVE);
            smileIdentityConfigRepository.edit(configs);
        }
        SmileIdentityConfig createdConfig = smileIdentityConfigRepository.create(smileIdentityConfig);
        if (createdConfig != null) {

            logger.info("Config created");
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().data(createdConfig).success(true).build())
                    .build();


        }
        return ErrorMsg("Config not created");
    }


    @Override
    public Response saveConfig(@Valid SmileIdentityConfig smileIdentityConfig) {
        if (smileIdentityConfigRepository.existsById(smileIdentityConfig.getId())) {
            smileIdentityConfigRepository.edit(smileIdentityConfig);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(smileIdentityConfig).msg("Config edited").build())
                    .build();
        }
        else {
            return createConfig(smileIdentityConfig);
        }
    }

    @Override
    public Response deleteConfig(long id) {
        if (smileIdentityConfigRepository.existsById(id)) {
            smileIdentityConfigRepository.deleteById(id);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("Config Deleted").build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("Config not found").build())
                .build();
    }

    @Override
    public Response ReEnrollUser(long mssUserId, long userId) {
        ReEnrollUserDto reEnrollUserDto = new ReEnrollUserDto();


        activityTrailService.logActivityTrail(mssUserId, "Renrolled user of id "+userId);

        SecKeyVM secKeyVM = selfieService.generateSecKey();
        reEnrollUserDto.setUser_id(String.valueOf(userId));
        reEnrollUserDto.setEnvironment(secKeyVM.getEnvironment());
        reEnrollUserDto.setPartner_id(secKeyVM.getPartnerId());
        reEnrollUserDto.setSec_key(secKeyVM.getSignature());
        reEnrollUserDto.setTimestamp(secKeyVM.getTimestamp());
        reEnrollUserDto.setEmail("smnkimathi@gmail.com");

        logger.info(reEnrollUserDto.toString());

        SmileClientVm smileClientVm = selfieService.reEnrollUser(reEnrollUserDto);
        if(smileClientVm.getMessage().contains("not found")){
            return ErrorMsg(smileClientVm.getMessage()+" Kindly Register");
        }
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).msg(smileClientVm.getMessage()).build())
                .build();
    }

    @Override
    public Response DeactivateUser(long mssUserId, long userId) {

        activityTrailService.logActivityTrail(mssUserId, "Deactivated user of id "+userId);

        DeactivateUserDto deactivateUserDto = new DeactivateUserDto();

        SecKeyVM secKeyVM = selfieService.generateSecKey();
        deactivateUserDto.setUser_id(String.valueOf(userId));
        deactivateUserDto.setEnvironment(secKeyVM.getEnvironment());
        deactivateUserDto.setPartner_id(secKeyVM.getPartnerId());
        deactivateUserDto.setSec_key(secKeyVM.getSignature());
        deactivateUserDto.setTimestamp(secKeyVM.getTimestamp());
        deactivateUserDto.setEmail("smnkimathi@gmail.com");

        SmileClientVm smileClientVm = selfieService.deactivateUser(deactivateUserDto);
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).msg(smileClientVm.getMessage()).build())
                .build();
    }

    @Override
    public Response DeleteUser(long mssUserId, long userId) {

        activityTrailService.logActivityTrail(mssUserId, "Deleted user of id "+userId);

        DeleteUserDto deleteUserDto = new DeleteUserDto();

        SecKeyVM secKeyVM = selfieService.generateSecKey();
        deleteUserDto.setUser_id(String.valueOf(userId));
        deleteUserDto.setEnvironment(secKeyVM.getEnvironment());
        deleteUserDto.setPartner_id(secKeyVM.getPartnerId());
        deleteUserDto.setSec_key(secKeyVM.getSignature());
        deleteUserDto.setTimestamp(secKeyVM.getTimestamp());
        deleteUserDto.setEmail("smnkimathi@gmail.com");

        SmileClientVm smileClientVm = selfieService.deleteUser(deleteUserDto);
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).msg(smileClientVm.getMessage()).build())
                .build();
    }

    private Response ErrorMsg(String msg) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        msg
                ).build())
                .build();
    }

    public void sendResultsToFM(ProcessSelfieDto processSelfieDto, String livenessCheck, String confidenceValue, SelfieAction selfieAction, long cycleId) {
        SelfieResultVM selfieResultVM = new SelfieResultVM();
        SmileIdentityConfig activeConfig = smileIdentityConfigRepository.getActiveConfig();

        selfieResultVM.setUserType(UserType.valueOf(processSelfieDto.getUserType()));
        selfieResultVM.setSelfieAction(selfieAction);
        if(selfieAction.equals(SelfieAction.REGISTRATION)){
            selfieResultVM.setAmountPaid(activeConfig.getRegistrationAmount());
        }
        else if(selfieAction.equals(SelfieAction.AUTHENTICATION)){
            selfieResultVM.setAmountPaid(activeConfig.getAuthenticationAmount());
        }
        else{
            selfieResultVM.setAmountPaid(activeConfig.getReRegistrationAmount());
        }
        selfieResultVM.setSource(SelfieSource.MSS);
        selfieResultVM.setUserId(Long.valueOf(processSelfieDto.getUserId()));
        selfieResultVM.setJobId(processSelfieDto.getJobId());
        selfieResultVM.setCreatedDateSource(new Date());
        selfieResultVM.setImage(processSelfieDto.getImage());
        selfieResultVM.setConfidenceValue(confidenceValue);
        selfieResultVM.setLivenessCheck(livenessCheck);
        selfieResultVM.setCycleId(cycleId);

        FmListDTO fmListDTO = selfieService.sendResultsToFM(selfieResultVM);

    }

    public void sendResultsToFM(Result result, SelfieAction selfieAction, ProcessSelfieDto processSelfieDto,long cycleId) {
        SelfieResultVM selfieResultVM = new SelfieResultVM();

        SmileIdentityConfig activeConfig = smileIdentityConfigRepository.getActiveConfig();

        selfieResultVM.setUserType(UserType.valueOf(processSelfieDto.getUserType()));
        selfieResultVM.setSelfieAction(selfieAction);
        if(selfieAction.equals(SelfieAction.REGISTRATION)){
            selfieResultVM.setAmountPaid(activeConfig.getRegistrationAmount());
        }
        else if(selfieAction.equals(SelfieAction.AUTHENTICATION)){
            selfieResultVM.setAmountPaid(activeConfig.getAuthenticationAmount());
        }
        else{
            selfieResultVM.setAmountPaid(activeConfig.getReRegistrationAmount());
        }
        selfieResultVM.setSource(SelfieSource.MSS);
        selfieResultVM.setUserId(Long.valueOf(result.getPartnerParams().getUser_id()));
        selfieResultVM.setJobId(result.getPartnerParams().getJob_id());
        selfieResultVM.setCreatedDateSource(new Date());
        selfieResultVM.setImage(processSelfieDto.getImage());
        selfieResultVM.setConfidenceValue(result.getConfidenceValue());
        selfieResultVM.setLivenessCheck(result.getActions().getLiveness_Check());
        selfieResultVM.setCycleId(cycleId);
        FmListDTO fmListDTO = selfieService.sendResultsToFM(selfieResultVM);

    }

    @Override
    public Response loadUserImages(long mssUserId, long userId) throws IOException {
        //LOG ACTIVITY

        activityTrailService.logActivityTrail(mssUserId, "Loaded user images of id "+userId);

        SelfieListDto selfieListDto = selfieService.loadUserImages(userId);
        ObjectMapper objectMapper=new ObjectMapper();
        List<Object> data = (List<Object>) selfieListDto.getData();
        List<UserImagesVm> userImagesVms=new ArrayList<>();
        for( Object object:data) {
            String jsonString = objectMapper.writeValueAsString(object);
            UserImagesVm userImagesVm = objectMapper.readValue(jsonString, UserImagesVm.class);
            userImagesVm.setCreatedDateShort(DateUtils.shortDate(userImagesVm.getCreatedDate()));
            userImagesVm.setCreatedDateSourceShort(DateUtils.shortDate(userImagesVm.getCreatedDateSource()));
            userImagesVms.add(userImagesVm);
        }


        if (selfieListDto.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(userImagesVms).msg("done").build())
                    .build();
        }
        return ErrorMsg(selfieListDto.getMessage());
    }

    @Override
    public Response loadUserRegistrationImage(long mssUserId, long userId) {
        //LOG ACTIVITY

        activityTrailService.logActivityTrail(mssUserId, "Loaded registration image of user id "+userId);

        SelfieListDto selfieListDto = selfieService.loadUserRegistrationImage(userId);
        if (selfieListDto.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(selfieListDto.getData()).msg("done").build())
                    .build();
        }
        return ErrorMsg(selfieListDto.getMessage());
    }

    @Override
    public Response checkIfViableForAuthentication(long mssUserId, long userId, long cycleId, long schemeId) {


        activityTrailService.logActivityTrail(mssUserId, "checked authentication viability of user of id "+userId);

        //check if is registered
        SelfieListDto selfieListDto = selfieService.checkIfRegistrationSelfieIsSubmitted(userId);
        if (selfieListDto.getCount() < 1) {
            return ErrorMsg("You have not submitted a registration selfie");
        }
        SelfieListDto selfieListDto1 = selfieService.checkIfAlreadyAuthenticated(cycleId, schemeId);
        if (selfieListDto1.isSuccess()) {
            return ErrorMsg("You have already submitted an authentication selfie");
        }
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).msg("Can proceed to submit selfie").build())
                .build();
    }

    @Override
    public Response checkIfViableForRegistration(long mssUserId, long userId) {

        activityTrailService.logActivityTrail(mssUserId, "Checked registration viability user of id "+userId);

        //check if is registered
        SelfieListDto selfieListDto = selfieService.checkIfRegistrationSelfieIsSubmitted(userId);
        if (selfieListDto.getCount() < 1) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("Can proceed to register selfie").build())
                    .build();
        }
        return ErrorMsg("You have already submitted a registration selfie");

    }

    public void saveToLocalDb(SmileIdentityResponseDTO smileIdentityResponseDTO, ProcessSelfieDto processSelfieDto, long cycleId) {
        try {
            SelfieResults selfieResults = new SelfieResults();
            selfieResults.setJobId(smileIdentityResponseDTO.getResult().getPartnerParams().getJob_id());
            selfieResults.setUserId(smileIdentityResponseDTO.getResult().getPartnerParams().getUser_id());
            selfieResults.setImage(processSelfieDto.getImage());
            selfieResults.setSource(ResultSource.CALLBACK);
            selfieResults.setCycleId(cycleId);
            SelfieResults results = selfieResultsRepository.create(selfieResults);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkJobStatus(ProcessSelfieDto processSelfieDto,long cycleId) {
        logger.info("checking job status waiting \n>>>>>>>>");
        doInBackground(new ProcessCallBack() {
            @Override
            public void start(Object o) {
                SmileIdentityConfig activeConfig = smileIdentityConfigRepository.getActiveConfig();
                try {
                    TimeUnit.MINUTES.sleep(1);
                    logger.info("checking job status waiting after one min \n>>>>>>>>");
                    WebApi connection = new WebApi(activeConfig.getPartnerId(), activeConfig.getCallbackApi(), activeConfig.getDecodedApiKey(), activeConfig.getServerId());
                    PartnerParameters partnerParameters = new PartnerParameters(processSelfieDto.getUserId(), processSelfieDto.getJobId(), processSelfieDto.getJobType());
                    Options job_status_options = new Options(false, false);
                    String job_status = connection.get_job_status(partnerParameters.get(), job_status_options.get());
                    Gson gson = new Gson();
                    SmileIdentityResponseDTO smileIdentityResponseDTO = gson.fromJson(job_status, SmileIdentityResponseDTO.class);
                    Result result=smileIdentityResponseDTO.getResult();
                    logger.info("result back are>>" + job_status);
                    if (result.getPartnerParams().getJob_type().equals("2")) { //authentication
                        switch (result.getResultCode()) {
                            case "1220":
                                sendResultsToFM(result, SelfieAction.AUTHENTICATION,processSelfieDto,cycleId);
                                logger.info("success end process after sending to xe stop process");
                                break;
                            case "1221":
                                logger.info("fail 2 background check stop process>>");
                                break;
                            case "1222":
                                logger.info("fail 3 background check stop process>>");
                                break;
                            default:
                                logger.info("not complete re-run process");
                                checkJobStatus(processSelfieDto,cycleId);
                        }

                    } else if (result.getPartnerParams().getJob_type().equals("4")) {
                        switch (result.getResultCode()) {
                            case "1240":
                                sendResultsToFM(result, SelfieAction.REGISTRATION,processSelfieDto,cycleId);
                                break;
                            case "1241":
                                break;
                            case "1242":
                                break;
                            default:
                                checkJobStatus(processSelfieDto,cycleId);
                        }
                    }

                    logger.info("done background check>>");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void doInBackground(ProcessCallBack processCallBack) {
        if (processCallBack != null) {
            CompletableFuture.supplyAsync(
                    () -> {
                        processCallBack.start(null);
                        return 1L;
                    }
            );
        }
    }

    public String recursionCheckJobStatus(ProcessSelfieDto processSelfieDto,long cycleId){
        String message="";
        SmileIdentityConfig activeConfig = smileIdentityConfigRepository.getActiveConfig();
        try {
            TimeUnit.SECONDS.sleep(15);
            logger.info("checking job status waiting after one min \n>>>>>>>>");
            WebApi connection = new WebApi(activeConfig.getPartnerId(), activeConfig.getCallbackApi(), activeConfig.getDecodedApiKey(), activeConfig.getServerId());
            PartnerParameters partnerParameters = new PartnerParameters(processSelfieDto.getUserId(), processSelfieDto.getJobId(), processSelfieDto.getJobType());
            Options job_status_options = new Options(false, false);
            String job_status = connection.get_job_status(partnerParameters.get(), job_status_options.get());
            Gson gson = new Gson();
            SmileIdentityResponseDTO smileIdentityResponseDTO = gson.fromJson(job_status, SmileIdentityResponseDTO.class);
            Result result = smileIdentityResponseDTO.getResult();
            logger.info("result back are>>" + job_status);
            if (result.getPartnerParams().getJob_type().equals("2")) { //authentication
                switch (result.getResultCode()) {
                    case "1220":
                        sendResultsToFM(result, SelfieAction.AUTHENTICATION,processSelfieDto,cycleId);
                        message = "Human Judgement  Passed, Authentication Successful";
                        return message;
                    case "1221":
                        message = "Human Judgement  FAILED,  Human Compare Failed";
                        return message;
                    case "1222":
                        message = "Human Judgement  FAILED,  Spoof Detected";
                        return message;
                    default:
                        return recursionCheckJobStatus(processSelfieDto,cycleId);
                }

            } else if (result.getPartnerParams().getJob_type().equals("4")) {
                switch (result.getResultCode()) {
                    case "1240":
                        sendResultsToFM(result, SelfieAction.REGISTRATION,processSelfieDto,cycleId);
                        message = "PASSED  Human Judgement, Registration successful";
                        return message;
                    case "1241":
                        message = "FAILED  Image Unusable";
                        return message;
                    case "1242":
                        message = "FAILED, Spoof detected by  Human Judgement";
                        return message;
                    default:
                        return recursionCheckJobStatus(processSelfieDto,cycleId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;

    }

    @Override
    public Response loadSelfieRegistrationDetails(long mssUserId, String searchKey) {

        activityTrailService.logActivityTrail(mssUserId, "Loaded registration details using search key "+searchKey);

        SelfieListDto selfieListDto = selfieService.loadSelfieRegistrationDetails(searchKey);
        if(selfieListDto.isSuccess()){
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(selfieListDto.getRows()).msg("done").build())
                    .build();
        }
        return ErrorMsg("User(s) not found");

    }

    @Override
    public Response makePayment(long mssUserId, @Valid SelfiePaymentVm selfiePaymentVm) {

        activityTrailService.logActivityTrail(mssUserId, "requested payment for user id "+selfiePaymentVm.getUserId());

        //check if any unused payments exist first
        boolean userUnusedPayments = selfiePaymentRepository.getUserUnusedPayments(selfiePaymentVm.getUserId());
        if(userUnusedPayments){
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("PROCEED").build())
                    .build();
        }

        boolean payment = selfieService.makePayment(selfiePaymentVm);
        if(payment){
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("Payment stk push processing").build())
                    .build();
        }
        return ErrorMsg("Payment not Successful");
    }

    @Override
    public Response checkPaymentStatus(long mssUserId, long userId,SelfieAction selfieAction) {

        activityTrailService.logActivityTrail(mssUserId, "checked payment status for user id "+userId);

        boolean payment=selfieService.checkPaymentStatus(userId,selfieAction);
        if(payment){
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("Payment Successful").build())
                    .build();
        }
        return ErrorMsg("Payment not Successful");
    }

    @Override
    public Response getUsersAllPayments(long mssUserId, long userId) {
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder()
                        .success(true)
                        .data(selfiePaymentRepository.findAll())
                        .msg("done").build())
                .build();
    }

    @Override
    public Response resetAuthenticationTrials(long mssUserId, long userId) {

        User user=userService.getUserByMemberIdOrPensionerId(userId);
        if(user != null){
            user.setAuthenticationTrials(0);
            userRepository.edit(user);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("Authentication trials reset successfully").build())
                    .build();
        }
        return ErrorMsg("User Not Found");
    }
}
