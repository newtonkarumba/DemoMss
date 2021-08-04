package com.systech.smileIdentity.Service;

import com.systech.mss.config.APICall;
import com.systech.mss.domain.Config;
import com.systech.mss.domain.MobileMoneyConfig;
import com.systech.mss.mobilemoney.Mpesa;
import com.systech.mss.mobilemoney.MpesaCallBack;
import com.systech.mss.repository.ConfigRepository;
import com.systech.mss.repository.MobileMoneyRepository;
import com.systech.mss.service.dto.FmListDTO;
import com.systech.mss.seurity.DateUtils;
import com.systech.smileIdentity.DTO.*;
import com.systech.smileIdentity.Service.vm.SecKeyVM;
import com.systech.smileIdentity.Service.vm.SelfiePaymentVm;
import com.systech.smileIdentity.Service.vm.SelfieResultVM;
import com.systech.smileIdentity.Service.vm.SmileClientVm;
import com.systech.smileIdentity.model.PaymentStatus;
import com.systech.smileIdentity.model.SelfieAction;
import com.systech.smileIdentity.model.SelfiePayment;
import com.systech.smileIdentity.model.SmileIdentityConfig;
import com.systech.smileIdentity.repository.SelfiePaymentRepository;
import com.systech.smileIdentity.repository.SmileIdentityConfigRepository;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import smile.identity.core.*;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.systech.smileIdentity.util.Constants.*;

@RequestScoped
public class SelfieService {
    private Client client;
    private WebTarget target;
    @Inject
    private ConfigRepository configRepository;
    Config config;
    private MultivaluedMap<String, Object> myHeaders;

    @Inject
    Logger log;

    @Inject
    private MobileMoneyRepository mobileMoneyRepository;

    @Inject
    private SelfiePaymentRepository selfiePaymentRepository;

    @PostConstruct
    public void setup() {
        client = ClientBuilder.newClient();
        if (getFMConfig().isPresent()) {
            config = getFMConfig().get();
            myHeaders = new MultivaluedHashMap<>();
            myHeaders.add("username", config.getFmUsername());
            myHeaders.add("password", config.getFmPassword());
            target = client.target(config.getFmBasePath()); //http:168.235.82.130:8084/Xe/api
        }
    }

    private Optional<Config> getFMConfig() {
        return configRepository
                .findAll()
                .stream()
                .findFirst();
    }


    @Inject
    private SmileIdentityConfigRepository smileIdentityConfigRepository;
    @Inject
    Logger logger;

    public String registerSelfie(String userId, String jobId) {

        try {
            PartnerParameters partnerParameters = new PartnerParameters(userId, jobId, 2);
            //job types
            /*1
            Register with ID
            Verify an ID
            Take a Selfie
            Compare the Selfie with the ID Authority Photo
            2
            Authenticate
            Take a Selfie
            Compare the Selfie with the Selfie provided during a successful Registration
            4
            Register without ID
            Take a Selfie
            5
            ID Validation
            Verify an ID
            8
            Update Photo
            Take a Selfie
            Compare the Selfie with the Selfie provided during a successful Registration
            */

            partnerParameters.add("optional_info", "some optional info");

            // Note dob is only required for VOTER_ID, DRIVERS_LICENSE, NATIONAL_ID, TIN, and CAC. For the rest of the id types you can send through dob as null or empty.
            IDParameters idInfo = new IDParameters("SIMON", "MUNGIRIA", "KIMATHI", "KE", "NATIONAL_ID", "35848896", "12/18/1996", "0741753780", "true");

            // Create image list
            // Set the imageTypeId as an Integer using the following table
            // 0 - Selfie image jpg or png
            // 1 - ID card image jpg or png
            // 2 - Selfie image jpg or png base64 encoded
            // 3 - ID card image jpg or png base 64 encoded
            String inputFilePath = "/home/symoh/workspace/smileIdentity/simon.png";  //dark
            String yona = "/home/symoh/workspace/smileIdentity/yona.png";  //yona
            byte[] fileContent = FileUtils.readFileToByteArray(new File(yona));
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            System.out.println("base 64:****" + encodedString);
//            ClassLoader classLoader = getClass().getClassLoader();
//            File inputFile = new File(classLoader
//                    .getResource(inputFilePath)
//                    .getFile());
//            byte[] fileContent = FileUtils.readFileToByteArray(inputFile);
//            String encodedString = Base64
//                    .getEncoder()
//                    .encodeToString(fileContent);
            /*light*/
            String img = "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";
//            System.out.println(encodedString);
            ImageParameters imageParameters = new ImageParameters();
            imageParameters.add(0, yona);

            Options options = new Options(CALLBACK, true, true, true);

            WebApi connection = new WebApi(PARTNER_ID, CALLBACK, DECODED_API_KEY, TEST_SERVER_ID); //0-test, 1--development

            String response = connection.submit_job(partnerParameters.get(), imageParameters.get(), idInfo.get(), options.get());
            System.out.println("\n Response" + response);
            return response;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String processSelfie(ProcessSelfieDto processSelfieDto) {
        SmileIdentityConfig smileIdentityConfig = smileIdentityConfigRepository.getActiveConfig();
        logger.info(processSelfieDto.toString());
        try {
            PartnerParameters partnerParameters = new PartnerParameters(
                    processSelfieDto.getUserId(),
                    processSelfieDto.getJobId(),
                    processSelfieDto.getJobType()
            );
            partnerParameters.add("optional_info", "some optional info");

            IDParameters idInfo = new IDParameters(
                    processSelfieDto.getIdFirstName(),
                    processSelfieDto.getIdMiddleName(),
                    processSelfieDto.getIdLastName(),
                    processSelfieDto.getIdCountry(),
                    processSelfieDto.getIdType(),
                    processSelfieDto.getIdNumber(),
                    processSelfieDto.getIdDob(),
                    processSelfieDto.getIdPhoneNumber(),
                    processSelfieDto.getIdConfirmed()
            );
            ImageParameters imageParameters = new ImageParameters();
            imageParameters.add(processSelfieDto.getImageType(), processSelfieDto.getImage());

            Options options = new Options(
                    smileIdentityConfig.getCallbackApi(),
                    smileIdentityConfig.isReturnJobStatus(),
                    smileIdentityConfig.isReturnHistory(),
                    smileIdentityConfig.isReturnImages()
            );

            WebApi connection = new WebApi(
                    smileIdentityConfig.getPartnerId(),
                    smileIdentityConfig.getCallbackApi(),
                    smileIdentityConfig.getDecodedApiKey(),
                    smileIdentityConfig.getServerId() //0-test, 1--development
            );

            return connection.submit_job(
                    partnerParameters.get(),
                    imageParameters.get(),
                    idInfo.get(),
                    options.get()
            );


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public FmListDTO sendResultsToFM(SelfieResultVM selfieResultVM) {
        try {
            Response response = target.path(APICall.PROCESS_SELFIE)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .post(Entity.json(selfieResultVM));
            return response.readEntity(FmListDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return FmListDTO.builder().success(false).build();
        }
    }

    public SelfieListDto loadUserImages(long userId) {
        try {
            Response response = target.path(APICall.LOAD_USER_IMAGES)
                    .path(String.valueOf(userId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();

            return response.readEntity(SelfieListDto.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return SelfieListDto.builder().success(false).build();
        }
    }


    public SelfieListDto loadUserRegistrationImage(long userId) {
        try {
            Response response = target.path(APICall.LOAD_USER_REGISTRATION_IMAGE)
                    .path(String.valueOf(userId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(SelfieListDto.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return SelfieListDto.builder().success(false).build();
        }
    }


    public SelfieListDto checkIfRegistrationSelfieIsSubmitted(long userId) {
        try {
            Response response = target.path(APICall.CHECK_IF_SELFIE_IS_REGISTERED)
                    .path(String.valueOf(userId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(SelfieListDto.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return SelfieListDto.builder().success(false).build();
        }
    }


    public SelfieListDto checkIfAlreadyAuthenticated(long cycleId, long schemeId) {
        try {
            Response response = target.path(APICall.CHECK_IF_PENSIONER_COE_IS_SUBMITTED)
                    .path(String.valueOf(cycleId))
                    .path(String.valueOf(schemeId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(SelfieListDto.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return SelfieListDto.builder().success(false).build();
        }
    }


    public SelfieListDto loadSelfieRegistrationDetails(String searchKey) {
        try {
            Response response = target.path(APICall.LOAD_SELFIE_REGISTRATION_DETAILS)
                    .path(searchKey)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .headers(myHeaders)
                    .get();
            return response.readEntity(SelfieListDto.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return SelfieListDto.builder().success(false).build();
        }
    }


    public SecKeyVM generateSecKey() {
        SmileIdentityConfig smileIdentityConfig = smileIdentityConfigRepository.getActiveConfig();
        try {


            int partner_id = Integer.parseInt(smileIdentityConfig.getPartnerId());

            long timestamp = System.currentTimeMillis();
            String toHash = partner_id + ":" + timestamp;
            System.out.println("Original: " + toHash);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(toHash.getBytes());
            byte[] hashed = md.digest();
            String toEncryptString = bytesToHexStr(hashed);

            System.out.println("Key used to encrypt: " + smileIdentityConfig.getDecodedApiKey());

            PublicKey publicKey = loadPublicKey(smileIdentityConfig.getDecodedApiKey());

            byte[] encSignature = encryptString(publicKey, toEncryptString);
            String signature = Base64.getEncoder().encodeToString(encSignature) + "|" + toEncryptString;
            String server = "";
            if (smileIdentityConfig.getServerId() == 0) {
                server = "portal";
            } else {
                server = "prod";
            }
            return SecKeyVM.builder().signature(signature).timestamp(timestamp).Environment(server).partnerId(smileIdentityConfig.getPartnerId()).build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PublicKey loadPublicKey(String apiKey) throws GeneralSecurityException, IOException {
        byte[] data = Base64.getDecoder().decode((apiKey.getBytes()));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory factObj = KeyFactory.getInstance("RSA");
        PublicKey lPKey = factObj.generatePublic(spec);
        return lPKey;
    }

    public static byte[] encryptString(PublicKey key, String plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plaintext.getBytes());
    }

    public static String bytesToHexStr(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public SmileClientVm reEnrollUser(ReEnrollUserDto reEnrollUserDto) {  //https://[env].smileidentity.com/api/v2/partner/enrollee
        String url = "";
        if (reEnrollUserDto.getEnvironment().equals("portal")) { //test
            url = "https://portal.smileidentity.com/api/v2/partner/enrollee";
        } else {
            url = "https://prod.smileidentity.com/api/v2/partner/enrollee";
        }

        try {
            Response response = client.target(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(reEnrollUserDto, MediaType.APPLICATION_JSON_TYPE));
            return response.readEntity(SmileClientVm.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SmileClientVm deactivateUser(DeactivateUserDto deactivateUserDto) {  //https://[env].smileidentity.com/api/v2/partner/enrollee
        String url = "";
        if (deactivateUserDto.getEnvironment().equals("portal")) { //test
            url = "https://portal.smileidentity.com/api/v2/partner/enrollee";
        } else {
            url = "https://prod.smileidentity.com/api/v2/partner/enrollee";
        }

        try {
            Response response = client.target(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(deactivateUserDto, MediaType.APPLICATION_JSON_TYPE));
            return response.readEntity(SmileClientVm.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SmileClientVm deleteUser(DeleteUserDto deleteUserDto) {  //https://[env].smileidentity.com/api/v2/partner/enrollee
        String url = "";
        if (deleteUserDto.getEnvironment().equals("portal")) { //test
            url = "https://portal.smileidentity.com/api/v2/partner/enrollee";
        } else {
            url = "https://prod.smileidentity.com/api/v2/partner/enrollee";
        }

        try {
            Response response = client.target(url)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(deleteUserDto, MediaType.APPLICATION_JSON_TYPE));

            return response.readEntity(SmileClientVm.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean makePayment(SelfiePaymentVm selfiePaymentVm) {

        SmileIdentityConfig smileIdentityConfig = smileIdentityConfigRepository.getActiveConfig();
        MobileMoneyConfig mobileMoneyConfig = mobileMoneyRepository.getActiveConfig();
        String mpesaPhoneNumber = selfiePaymentVm.getPhone();
        if (mpesaPhoneNumber.startsWith("0"))
            mpesaPhoneNumber = config.getCountryCode() + mpesaPhoneNumber.substring(1);
        if (mpesaPhoneNumber.startsWith("+"))
            mpesaPhoneNumber = mpesaPhoneNumber.substring(1);

        selfiePaymentVm.setFinalPhone(mpesaPhoneNumber);

        long charge = 0L;
        String transactionDescription = "";
        if (selfiePaymentVm.getAction().equals(SelfieAction.REGISTRATION)) {
            charge = smileIdentityConfig.getRegistrationAmount();
            transactionDescription = "Selfie " + SelfieAction.REGISTRATION.toString();
        } else if (selfiePaymentVm.getAction().equals(SelfieAction.AUTHENTICATION)) {
            charge = smileIdentityConfig.getAuthenticationAmount();
            transactionDescription = "Selfie " + SelfieAction.AUTHENTICATION.toString();
        } else if (selfiePaymentVm.getAction().equals(SelfieAction.RE_REGISTRATION)) {
            charge = smileIdentityConfig.getReRegistrationAmount();
            transactionDescription = "Selfie " + SelfieAction.RE_REGISTRATION.toString();
        }
        selfiePaymentVm.setAmount(charge);
        log.error(mpesaPhoneNumber);
        String timeStamp = DateUtils.getTimestamp();
        String password = Mpesa.getPassword(mobileMoneyConfig.getMpesaPaybill(), mobileMoneyConfig.getMpesaPassKey(), timeStamp);
        Mpesa mpesa = new Mpesa(
                mobileMoneyConfig.getMpesaAppKey(),
                mobileMoneyConfig.getMpesaAppSecret(),
                mobileMoneyConfig.isLive()
        );
        MpesaCallBack mpesaCallBack = new MpesaCallBack() {
            @Override
            public Object start(Object o) {
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(String.valueOf(o));
                    if (jsonObject.has("errorCode")) {
                        return jsonObject.has("errorMessage");
                    }

                    selfiePaymentVm.setPaybill(mobileMoneyConfig.getMpesaPaybill());
                    selfiePaymentVm.setRequestId(jsonObject.getString("CheckoutRequestID"));
                    selfiePaymentVm.setMerchantRequestID(jsonObject.getString("MerchantRequestID"));
                    selfiePaymentVm.setTimestamp(timeStamp);
                    selfiePaymentVm.setPassword(password);

                    selfiePaymentRepository.save(selfiePaymentVm);
                    return "success";
                } catch (Exception e) {
                    return "Error encountered";
                }
            }
        };
        String res = null;
        String callbackUrl=mobileMoneyConfig.getCallbackUrl() + "/smileIdentity";
        String timeoutUrl=mobileMoneyConfig.getTimeoutUrl() + "/smileIdentity";
        try {
            res = mpesa.STKPushSimulation(
                    mobileMoneyConfig.getMpesaPaybill(),
                    password,
                    timeStamp,
                    String.valueOf(charge),
                    mpesaPhoneNumber,
                    mpesaPhoneNumber,
                    mobileMoneyConfig.getMpesaPaybill(),
                    callbackUrl,
                    timeoutUrl,
                    mobileMoneyConfig.getAccountReference(),
                    transactionDescription,
                    mpesaCallBack
            );

            if (res.equals("success")) {
                return true;
                //check status
//                TimeUnit.SECONDS.sleep(45);
//                return checkPaymentStatus(mpesaCallBack,mobileMoneyConfig,selfiePaymentVm,mpesa);

            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean checkPaymentStatus(long userId,SelfieAction selfieAction) {

        SelfiePayment selfiePayment=selfiePaymentRepository.getUserPendingOrUnusedPaymentPerAction(userId,selfieAction);

        if(selfiePayment.getPaymentStatus().equals(PaymentStatus.PAID_SUCCESSFUL_NOT_USED)){
            return true;
        }

        MobileMoneyConfig mobileMoneyConfig = mobileMoneyRepository.getActiveConfig();

        MpesaCallBack mpesaCallBack = new MpesaCallBack() {
            @Override
            public Object start(Object o) {
                org.json.JSONObject jsonObject = null;
                try {
                    jsonObject = new org.json.JSONObject(String.valueOf(o));
                    if (jsonObject.has("ResultCode")) {
                        String ResultCode = jsonObject.getString("ResultCode");
                        if (ResultCode.equals("0")) {
                            return "success";
                        }
                        return jsonObject.getString("ResultDesc");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "Failed";
            }
        };

        Mpesa mpesa = new Mpesa(
                mobileMoneyConfig.getMpesaAppKey(),
                mobileMoneyConfig.getMpesaAppSecret(),
                mobileMoneyConfig.isLive()
        );

        try {
            String res = mpesa.STKPushTransactionStatus(
                    mobileMoneyConfig.getMpesaPaybill(),
                    selfiePayment.getPassword(),
                    selfiePayment.getTimestamp(),
                    selfiePayment.getRequestId(),
                    mpesaCallBack
            );
            if (res.equalsIgnoreCase("success")) {
                //update payment
                selfiePayment.setPaymentStatus(PaymentStatus.PAID_SUCCESSFUL_NOT_USED);
                selfiePaymentRepository.edit(selfiePayment);
                return true;
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        selfiePayment.setPaymentStatus(PaymentStatus.NOT_PAID);
        selfiePaymentRepository.edit(selfiePayment);
        return false;
    }
}
