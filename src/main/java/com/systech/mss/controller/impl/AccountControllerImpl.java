package com.systech.mss.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.controller.AccountController;
import com.systech.mss.controller.vm.*;
import com.systech.mss.domain.*;
import com.systech.mss.fileupload.FileModel;
import com.systech.mss.repository.ProfileRepository;
import com.systech.mss.service.ActivityTrailService;
import com.systech.mss.service.NotificationService;
import com.systech.mss.service.dto.FmListDTO;
import com.systech.mss.service.dto.MemberDTO;
import com.systech.mss.service.dto.PensionerDTO;
import com.systech.mss.service.dto.UserDTO;
import com.systech.mss.seurity.DateUtils;
import com.systech.mss.seurity.PasswordEncoder;
import com.systech.mss.seurity.SecurityHelper;
import com.systech.mss.util.RandomUtil;
import com.systech.mss.util.StringUtil;
import com.systech.mss.vm.KeyAndPasswordVM;
import com.systech.mss.vm.PasswordChangeVM;
import com.systech.mss.vm.SMSVM;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static com.systech.mss.config.Constants.*;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.*;

public class AccountControllerImpl extends BaseController implements AccountController {
    private final String UPLOADED_FILE_PATH = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;

    List<BatchMemberRegistrationVm> batchRegistration = new ArrayList<>();

    List<BatchMemberRegistrationVm> validBatchRegistration = new ArrayList<>();

    List<BatchMemberRegistrationVm> invalidBatchRegistration = new ArrayList<>();

    Map<String, Integer> titles = new HashMap<>();

    @Inject
    private SecurityHelper securityHelper;

    @Context
    private HttpServletRequest request;

    @Inject
    ProfileRepository profileRepository;

    @Inject
    private ActivityTrailService activityTrailService;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private NotificationService notificationService;


    @Override
    public Response registerAccount(@Valid ManagedUserVM managedUserVM) {
        String pwd = StringUtil.generateRandomPassword(7);
        managedUserVM.setPassword(pwd);

        Optional<Profile> profile = profileRepository.findById(managedUserVM.getProfileId());
        if (!profile.isPresent()) {
            return ErrorMsg("Profile does not exist");
        }

        return userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase())
                .map(user -> ErrorMsg(profile.get().getLoginIdentifier().getName() + " already used, kindly activate your account"))
                .orElseGet(() -> checkIfExists(managedUserVM, profile.get()));
//        return userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase())
//                .map(user -> Response.status(BAD_REQUEST).type(APPLICATION_JSON_TYPE)
//                        .entity(
//                                ErrorVM.builder().msg(LOGIN_ALREADY_USED_TYPE).success(false).build()).build())
//                .orElseGet(() -> userRepository.findOneByEmail(managedUserVM.getEmail())
//                        .map(user -> Response.status(BAD_REQUEST).type(APPLICATION_JSON_TYPE)
//                                .entity(
//                                        ErrorVM.builder().msg(EMAIL_ALREADY_USED_TYPE).success(false).build()
//                                ).build())
//                        .orElseGet(() -> {
//                            try {
//                                return checkIfExists(managedUserVM, profile.get());
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                return ErrorMsg("Error encountered, please try again");
//                            }
//                        })
//                );
    }

    @Override
    public Response registerUserAccount(@Valid RegisterUserVm registerUserVm) {
        logActivityTrail(registerUserVm.getUserId(), "Registered user using email " + registerUserVm.getEmail());

        Optional<Profile> profile = profileRepository.findById(registerUserVm.getProfileId());
        return userRepository.findOneByLogin(registerUserVm.getEmail().toLowerCase())
                .map(user -> Response.status(BAD_REQUEST).type(APPLICATION_JSON_TYPE)
                        .entity(
                                ErrorVM.builder().msg(LOGIN_ALREADY_USED_TYPE).success(false).build()).build())
                .orElseGet(() -> {
                    ManagedUserVM managedUserVM = new ManagedUserVM();
                    String pwd = StringUtil.generateRandomPassword(7);
                    managedUserVM.setPassword(pwd);
                    managedUserVM.setLangKey("en");
                    managedUserVM.setFmIdentifier(profile.get().getName());
                    managedUserVM.setEmail(registerUserVm.getEmail());
                    managedUserVM.setLogin(registerUserVm.getEmail());
                    managedUserVM.setProfileId(registerUserVm.getProfileId());

                    return checkIfExists(managedUserVM, profile.get());

                });
    }

    @Override
    public Response registerMembersInBatch(long mssUserId, MultipartFormDataInput input) {
        activityTrailService.logActivityTrail(mssUserId, "Uploaded a batch member registration");
        String fileName = "";
        Map<String, Object> json = null;

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("formFile");


        for (InputPart inputPart : inputParts) {

            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);

                //convert the uploaded file to inputstream
                InputStream contentStream = inputPart.getBody(InputStream.class, null);

                byte[] bytes = IOUtils.toByteArray(contentStream);

                //constructs upload file path
                fileName = UPLOADED_FILE_PATH + fileName;

                File file = writeFile(bytes, fileName);

                //Create an object of FileInputStream class to read excel file
                FileInputStream inputStream = new FileInputStream(file);

                Workbook memberWorkBook = null;

                //Find the file extension by splitting file name in substring  and getting only extension name
                String fileExtensionName = fileName.substring(fileName.indexOf("."));

                //Check condition if the file is xlsx file
                if (fileExtensionName.equals(".xlsx")) {

                    //If it is xlsx file then create object of XSSFWorkbook class
                    memberWorkBook = new XSSFWorkbook(inputStream);

                }

                //Check condition if the file is xls file
                else if (fileExtensionName.equals(".xls")) {

                    //If it is xls file then create object of HSSFWorkbook class
                    memberWorkBook = new HSSFWorkbook(inputStream);

                }
                if (memberWorkBook != null) {
                    json = convertToCSV(memberWorkBook.getSheetAt(0), mssUserId);
                }


                memberWorkBook.close();
                inputStream.close();
                file.delete();

            } catch (IOException | ParseException e) {
                log.error("Error:{}", e.getLocalizedMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ErrorVM.builder().success(false)
                        .msg(e.getLocalizedMessage()).build()
                ).build();
            }
        }

        return Response.status(200)
                .entity(SuccessVM.builder().success(true).data(json).build()).build();
    }


    private Map<String, Object> convertToCSV(Sheet sheet, long mssUserId) throws IOException, ParseException {
        Row row;
        List<List<String>> sheetValues = new ArrayList<>();
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            List<String> rowValues = new ArrayList<>();
            row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                rowValues.add(String.valueOf(row.getCell(j)));
            }
            sheetValues.add(rowValues);
        }
        return parseLines(sheetValues, mssUserId);
    }

    private Map<String, Object> parseLines(List<List<String>> lines, long userId) throws IOException, ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        batchRegistration.clear();

        for (int i = 0; i < lines.size(); i++) {

            if (i == 0) {
                //add all titles to map
                for (int j = 0; j < lines.get(i).size(); j++) {
                    titles.put(String.valueOf(lines.get(i).get(j)), j);
                }
                continue;
            }
            BatchMemberRegistrationVm batchMemberRegistrationVm = new BatchMemberRegistrationVm();

            List<String> rowValues = lines.get(i);


            try {
                batchMemberRegistrationVm.setIdentifier(rowValues.get(titles.get("identifier")) == null ? "" : rowValues.get(titles.get("identifier")));
                if (batchMemberRegistrationVm.getIdentifier() == null || batchMemberRegistrationVm.getIdentifier().equals("")) {
                    batchMemberRegistrationVm.setMessage("Fundmaster Identifier Missing");
                    batchMemberRegistrationVm.setSuccess(false);
                }


                batchRegistration.add(batchMemberRegistrationVm);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        Profile profile = profileRepository.findByName("MEMBER");
        for (BatchMemberRegistrationVm batchMemberRegistrationVm : batchRegistration) {
            if (batchMemberRegistrationVm.isSuccess()) {

                Optional<User> user = userRepository.findOneByLogin(batchMemberRegistrationVm.getIdentifier());
                if (user.isPresent()) {
                    batchMemberRegistrationVm.setMessage("member already exists");
                    batchMemberRegistrationVm.setSuccess(false);
                } else {

                    ManagedUserVM managedUserVM = new ManagedUserVM();
                    String pwd = StringUtil.generateRandomPassword(7);
                    managedUserVM.setPassword(pwd);
                    managedUserVM.setLangKey("en");
                    managedUserVM.setFmIdentifier(profile.getName());
                    managedUserVM.setEmail(batchMemberRegistrationVm.getIdentifier());
                    managedUserVM.setLogin(batchMemberRegistrationVm.getIdentifier());
                    managedUserVM.setProfileId(profile.getId());

                    MemberDTO memberDTO = fundMasterClient.checkMemberIfExists(
                            profile.getLoginIdentifier().name().toUpperCase(Locale.getDefault()),
                            managedUserVM.getEmail(),
                            profile.getName()
                    );

                    if (memberDTO.isSuccess()) {
                        //check membership status before register
                        String membershipStatus = memberDTO.getMbshipStatus();
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
                            batchMemberRegistrationVm.setMessage("membership status is not allowed");
                            batchMemberRegistrationVm.setSuccess(false);
                        } else {
                            Response response = registerUserToMss(memberDTO, managedUserVM, profile);

                            batchMemberRegistrationVm.setSuccess(true);
                            batchMemberRegistrationVm.setMessage("member registered successfully");
                        }

                    } else {
                        batchMemberRegistrationVm.setMessage("member does not exist in core system");
                        batchMemberRegistrationVm.setSuccess(false);
                    }
                }
            }
            if (batchMemberRegistrationVm.isSuccess()) {
                validBatchRegistration.add(batchMemberRegistrationVm);
            } else {
                invalidBatchRegistration.add(batchMemberRegistrationVm);
            }

        }


        Map<String, Object> successfulRegistration = new HashMap<>();
        Map<String, Object> unsuccessfulRegistration = new HashMap<>();

        Map<String, Object> jsonMap = new HashMap<>();
        successfulRegistration.put("totalCount", validBatchRegistration.size());
        successfulRegistration.put("rows", validBatchRegistration);
        unsuccessfulRegistration.put("totalCount", invalidBatchRegistration.size());
        unsuccessfulRegistration.put("rows", invalidBatchRegistration);

        jsonMap.put("success", true);

        jsonMap.put("successfulRegistration", successfulRegistration);

        jsonMap.put("unsuccessfulRegistration", unsuccessfulRegistration);


        return jsonMap;

    }


    long convertStringToLong(String number) {
        if (number == null || number.equals("")) {
            return 0L;
        }
        Float stringFloat = new Float(number);
        return stringFloat.longValue();
    }

    /**
     * Original Method
     *
     * @param managedUserVM m
     * @return response
     */
    public Response registerAccount_(@Valid ManagedUserVM managedUserVM) {
        if (checkPasswordLength(managedUserVM.getPassword())) {
            return Response.status(BAD_REQUEST).entity(
                    ErrorVM.builder().msg(INVALID_PASSWORD_TYPE).success(false).build()
            ).build();
        }
        return userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase())
                .map(user -> Response.status(BAD_REQUEST).type(APPLICATION_JSON_TYPE)
                        .entity(
                                ErrorVM.builder().msg(LOGIN_ALREADY_USED_TYPE).success(false).build()).build())
                .orElseGet(() -> userRepository.findOneByEmail(managedUserVM.getEmail())
                        .map(user -> Response.status(BAD_REQUEST).type(APPLICATION_JSON_TYPE)
                                .entity(
                                        ErrorVM.builder().msg(EMAIL_ALREADY_USED_TYPE).success(false).build()
                                ).build())
                        .orElseGet(() -> {
                            try {
                                Optional<Profile> profile = profileRepository.findById(managedUserVM.getProfileId());
                                if (!profile.isPresent()) {
                                    return Response.status(BAD_REQUEST).entity(ErrorVM.builder().success(false)
                                            .msg("Profile does not exist")
                                            .build()).build();
                                }
                                return checkIfExists(managedUserVM, profile.get());
                            } catch (Exception e) {
                                e.printStackTrace();
                                return Response.status(BAD_REQUEST).entity(ErrorVM.builder().success(false)
                                        .msg("Error encountered, please try again")
                                        .build()).build();
                            }
                        })
                );
    }

    @Override
    public Response newMemberRegisterAccount(Member member) {
        if (member == null)
            return ErrorMsg("Please try again");
        try {
            Member member1 = memberRepository.create(member);
            if (member1 != null) {
                //send email to po
                long schemeId = member.getSchemeId();
                if (!member1.getEmailAddress().isEmpty()) {
                    log.warn("Initiating send email to {} ", member1.getEmailAddress());
                    notificationService.sendNotification(
                            member1,
                            EmailTemplatesEnum.NEW_MEMBER_REGISTERED,
                            member1.getSchemeName(),
                            member1.getSponsorName()
                    );
                }
                userService.sendPoEMail(schemeId,
                        EmailTemplatesEnum.PO_NEW_MEMBER_APPROVAL_REQUEST,
                        member.getFirstname() + " " + member.getLastname(),
                        member1.getSchemeName(),
                        member1.getSponsorName()
                );

                return SuccessMsg("Thank you for registering. Your details have been received and you will be notified by email or phone upon verification.", member1.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ErrorMsg("Registration Failed");
    }

    @Override
    public Response newMemberRegisterAccountETL(String data) {
        data = data.replaceAll("undefined", "");
        try {
            PotentialMemberETL potentialMemberETL = new ObjectMapper().readValue(
                    data,
                    PotentialMemberETL.class
            );
//            log.error(potentialMemberETL.toString());
            Map<String, Object> map = potentialMemberETL.xtractEtlMemberFromRequest();
            if (map != null) {
                log.error(StringUtil.toJsonString(map));
                org.json.JSONObject jsonObjectFinal = fmMemberClient.saveOrUpdateEtlMember(map);
                if (jsonObjectFinal != null) {
                    try {
                        if (jsonObjectFinal.getBoolean("success"))
                            return SuccessMsg("Done", "Registration successful");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return ErrorMsg("Could not save details");
            }
            return ErrorMsg("Registration failed, please try again");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ErrorMsg("Error encountered, please try again");
    }

    @Override
    public Response updateMemberRegisterAccountETL(String data) {
        data = data.replaceAll("undefined", "");
        try {
            PotentialMemberUpdateEtl potentialMemberETL = new ObjectMapper().readValue(
                    data,
                    PotentialMemberUpdateEtl.class
            );
            Map<String, Object> map = potentialMemberETL.xtractEtlUpdateMemberFromRequest();
            if (map != null) {
                log.error(StringUtil.toJsonString(map));
                org.json.JSONObject jsonObjectFinal = fmMemberClient.updateEtlPotentialMember(map);
                if (jsonObjectFinal != null) {
                    try {
                        if (jsonObjectFinal.getBoolean("success"))
                            return SuccessMsg("Done", "Update successful");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return ErrorMsg("Could not update details");
            }
            return ErrorMsg("Registration failed, please try again");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ErrorMsg("Error encountered, please try again");
    }

    @Override
    public Response getRegisteredNewMembers(long schemeId, long sponsorId) {
        List<Member> members = memberRepository.getUnPushedToXe(schemeId, sponsorId);
        if (members != null)
            return SuccessMsg("Done", setExtraDetails(members));
        return SuccessMsg("Done", "No new registered members");
    }

    @Override
    public Response getRegisteredNewMemberDetails(long memberId) {
        Member member = memberRepository.find(memberId);
        if (member != null)
            return SuccessMsg("Done", setExtraDetails(member));
        return ErrorMsg("Member not found");
    }

    private List<Member> setExtraDetails(List<Member> members) {
        for (Member member : members) {
            member.setShortDate(
                    DateUtils.shortDate(member.getCreatedAt())
            );
            try {
                member.setNumAttachedDocs(getFileModelsFromRegMember(member.getDocuments()).size());
            } catch (Exception ignored) {
            }
        }
        return members;
    }

    private Member setExtraDetails(Member member) {
        member.setShortDate(
                DateUtils.shortDate(member.getCreatedAt())
        );
        try {
            member.setNumAttachedDocs(getFileModelsFromRegMember(member.getDocuments()).size());
        } catch (Exception ignored) {
        }
        return member;
    }

    @Override
    public Response getRegisteredNewMembersDocs(long memberId) {
        List<FileModel> fileModels = getRegisteredNewMembersDocsImpl(memberId);
        if (fileModels != null)
            return SuccessMsg("Done", fileModels);
        return ErrorMsg(getMsg());
    }


    private List<FileModel> getRegisteredNewMembersDocsImpl(long memberId) {
        Member member = memberRepository.find(memberId);
        if (member != null) {
            String documents = member.getDocuments();
            try {
                return getFileModelsFromRegMember(documents);
            } catch (Exception e) {
                setMsg(e.getMessage());
            }
            return null;
        }
        setMsg("Member not found");
        return null;
    }

    @Override
    public Response uploadFile(long memberId, MultipartFormDataInput input) {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        if (uploadForm.isEmpty())
            return ErrorMsg("No files selected");
        try {
            Member member = memberRepository.find(memberId);
            if (member == null) {
                return ErrorMsg("Member not found");
            }

            List<FileModel> fileModels = upload(input);
            if (fileModels == null) {
                return ErrorMsg("Failed to upload documents");
            }
            JSONArray jsonArray = new JSONArray();
            for (FileModel fileModel : fileModels) {
                jsonArray.put(fileModel.getJson());
            }
            member.setDocuments(jsonArray.toString());
            memberRepository.edit(member);
            return SuccessMsg("Done", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorMsg("Error encountered");
    }

    @Override
    public Response newMemberRegisterSendToXe(Member member) {
        if (member == null)
            return ErrorMsg("Please try again");

        return registerNewMemberToXe_(member);
    }

    @Override
    public Response approveNewMemberDetails(long mssUserId, long id) {
        logActivityTrail(mssUserId, "Approve new member details");
        Member member = memberRepository.find(id);
        if (member == null)
            return ErrorMsg("Please try again");

        //send mail to member
        return registerNewMemberToXe_(member);
    }

    @Override
    public Response declineNewMemberDetails(long mssUserId, long id) {
        logActivityTrail(mssUserId, "Declined new member details");
        Member member = memberRepository.find(id);
        if (member == null)
            return ErrorMsg("No record found");

        memberRepository.remove(member);
        notificationService.sendNotification(member, EmailTemplatesEnum.NEW_MEMBER_DECLINE);
        return SuccessMsg("Done", null);
    }

    Response registerNewMemberToXe_(Member member) {
        MssMemberDTO mssMemberDTO = xtractMssMemberDTOFromRequest(member);
//        boolean status = fmMemberClient.saveOrUpdateMember(xtractMemberFromRequest(member));
        boolean status = fmMemberClient.saveOrUpdateMember(mssMemberDTO);
        if (status) {
            try {
                Member member1 = memberRepository.find(member.getId());
                if (member1 != null) {
                    member1.setPosted(true);
                    memberRepository.edit(member1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //send mail to member
            notificationService.sendNotification(
                    member, EmailTemplatesEnum.NEW_MEMBER_APPROVAL,
                    member.getSchemeName(),
                    member.getSponsorName()
            );
            return Response.ok().entity(
                    SuccessVM.builder().success(true).msg("Registration successful").build()
            ).build();
        }
        return ErrorMsg("Registration failed");
    }

    MssMemberDTO xtractMssMemberDTOFromRequest(Member m) {
        MssMemberDTO mssMemberDTO = MssMemberDTO.from(m);

        List<CostCenterVm> costCenterVms = fundMasterClient.getSponsorCompanyCostCentresList(m.getSponsorId());
        if (costCenterVms != null && !costCenterVms.isEmpty()) {
            CostCenterVm costCenterVm = costCenterVms.get(0);
            mssMemberDTO.setCompanyId(costCenterVm.getId());
        }

        List<MemberClassVm> memberClassVms = fundMasterClient.getSponsorMemberClassesList(m.getSponsorId());
        if (memberClassVms != null && !memberClassVms.isEmpty()) {
            MemberClassVm memberClassVm = memberClassVms.get(0);
            mssMemberDTO.setMclassId(memberClassVm.getId());
        }
        return mssMemberDTO;
    }

    Map<String, Object> xtractMemberFromRequest(Member m) {
        DateFormat format_ = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Map<String, Object> member = new HashMap<>();
        member.put("staffNo", StringUtil.defaultValue(m.getStaffNo()));
        member.put("postalAddress", StringUtil.defaultValue(m.getPostalAddress()));
        member.put("road", StringUtil.defaultValue(m.getRoad()));
        member.put("department", StringUtil.defaultValue(m.getDepartment()));
        member.put("designation", StringUtil.defaultValue(m.getDesignation()));
        member.put("currentAnnualPensionableSalary", StringUtil.defaultValue(m.getCurrentMonthlySalary()));
        String dOE = DateUtils.formatDate(m.getDateOfAppointment(), "yyyy-MM-dd");
        member.put("dateOfEmployment", StringUtil.defaultValue(dOE));
        member.put("disabled", StringUtil.defaultValue(m.getDisabled(), "YES").toString().toUpperCase(Locale.getDefault()));
        member.put("surname", StringUtil.defaultValue(m.getLastname()));
        member.put("firstname", StringUtil.defaultValue(m.getFirstname()));
        member.put("othernames", StringUtil.defaultValue(m.getOthernames()));
        member.put("email", StringUtil.defaultValue(m.getEmailAddress()));
        member.put("idNo", StringUtil.defaultValue(m.getIdNumber()));
        member.put("fixedPhone", StringUtil.defaultValue(m.getPhoneNumber()));
        member.put("secondaryPhone", StringUtil.defaultValue(m.getSecondaryPhoneNumber()));
        member.put("dob", StringUtil.defaultValue(format_.format(m.getDateOfBirth())));
        member.put("gender", StringUtil.defaultValue(m.getGender().name()));
        member.put("title", StringUtil.defaultValue(m.getTitle()));
        member.put("residentialAddress", StringUtil.defaultValue(m.getResidentialAddress()));
        member.put("town", StringUtil.defaultValue(m.getCity()));
        member.put("country", StringUtil.defaultValue(m.getCountry()));
        member.put("region", StringUtil.defaultValue(m.getAddressPlaceOfBirth()));
        member.put("subRegion", StringUtil.defaultValue(m.getAddressTraditionalAuthority()));
        member.put("maritalStatus", StringUtil.defaultValue(m.getMaritalStatus().name()));
        member.put("mbshipStatus", "ACTIVE");
        member.put("schemeId", StringUtil.defaultValue(m.getSchemeId()));
        member.put("sponsorId", StringUtil.defaultValue(m.getSponsorId()));

        List<CostCenterVm> costCenterVms = fundMasterClient.getSponsorCompanyCostCentresList(m.getSponsorId());
        if (costCenterVms != null && !costCenterVms.isEmpty()) {
            CostCenterVm costCenterVm = costCenterVms.get(0);
            member.put("companyId", costCenterVm.getId());
        }

        List<MemberClassVm> memberClassVms = fundMasterClient.getSponsorMemberClassesList(m.getSponsorId());
        if (memberClassVms != null && !memberClassVms.isEmpty()) {
            MemberClassVm memberClassVm = memberClassVms.get(0);
            member.put("mclassId", memberClassVm.getId());
        }
        return member;
    }

    private Response checkIfExists(ManagedUserVM managedUserVM, Profile profile) {

        MemberDTO memberDTO;
        switch (profile.getName()) {
            case "CRM":
                profile.setName("CUSTOMER_RELATIONSHIP_EXECUTIVE");
                break;
            case "CRE":
                profile.setName("CUSTOMER_RELATIONSHIP_MANAGER");
                break;
            case "ADMIN":
                profile.setName("ADMINISTRATOR");
                break;
            case "PRINCIPAL OFFICER":
                profile.setName("PRINCIPAL_OFFICER");
                break;

            default:

        }

        if (profile.getName().equals("ADMINISTRATOR") ||
                profile.getName().equals("CUSTOMER_RELATIONSHIP_EXECUTIVE") ||
                profile.getName().equals("CUSTOMER_RELATIONSHIP_MANAGER")) {

            memberDTO = fundMasterClient.checkMemberIfExists1(
                    profile.getLoginIdentifier().name().toUpperCase(Locale.getDefault()),
                    managedUserVM.getEmail(), //email/phone
                    profile.getName()
            );

        } else {
            memberDTO = fundMasterClient.checkMemberIfExists(
                    profile.getLoginIdentifier().name().toUpperCase(Locale.getDefault()),
                    managedUserVM.getEmail(),
                    profile.getName()
            );
            //check membership status before register
            if (profile.getName().equals("MEMBER")) {
                String membershipStatus = memberDTO.getMbshipStatus();
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
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(ErrorVM.builder()
                                    .success(false)
                                    .msg("Sorry, your membership status has no portal access rights. Please contact your scheme administrator").build())
                            .build();
                }
            }

        }

        log.error(memberDTO.toString());

        if (memberDTO.isSuccess()) {
            String nm = memberDTO.getName();
            if (nm == null || nm.isEmpty()) {
                memberDTO.setName("Hello " + memberDTO.getEmail());
            }
            return registerUserToMss(memberDTO, managedUserVM, profile);
        }

        return ErrorMsg(String.format(
                "Sorry, you are not a registered %s in the core system",
                profile.getName()
        ));

    }

    private Response registerCREToMss(ManagedUserVM managedUserVM, Profile profile) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setEmail(managedUserVM.getEmail());
        memberDTO.setName("CRE");
        memberDTO.setAccountStatus("ACTIVE");
        return registerUserToMss(memberDTO, managedUserVM, profile);
    }

    private Response registerUserToMss(MemberDTO memberDTO, ManagedUserVM managedUserVM, Profile profile) {

        try {
            if (memberDTO.getSchemeId() != 0L) {
                SchemeVM schemeVM= fmcrmClient.getSchemeById(memberDTO.getSchemeId());
                if (schemeVM!=null)
                    memberDTO.setSchemeName(schemeVM.getSchemeName());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        User user = userService.createUser(managedUserVM.getLogin(),
                managedUserVM.getPassword(),
                memberDTO.getEmail().toLowerCase(),
                managedUserVM.getLangKey(),
                managedUserVM.getFmIdentifier(),
                managedUserVM.getProfileId(),
                memberDTO);
        if (user == null) {
            return ErrorMsg("Your details are incorrect. If error persists kindly contact us");
        }

        if (profile.getName().equalsIgnoreCase("MEMBER")) {
            afterMemberRegister(user, memberDTO);
        }

        if (profile.getName().equalsIgnoreCase("PENSIONER")) {
            afterPensionerRegister(user, memberDTO);
        }

        //send sms and emails
        sendNotifications(profile, user, managedUserVM);

        return Response.status(CREATED).entity(SuccessVM.builder().success(true).build()).build();
    }

    public void sendNotifications(Profile profile, User user, ManagedUserVM managedUserVM) {
        if (profile.getName().equalsIgnoreCase("MEMBER")) {
            log.info("sending member email");
            notificationService.sendNotification(
                    user,
                    EmailTemplatesEnum.MEMBER_ACCOUNT_ACTIVATION,
                    managedUserVM.getLogin(),
                    managedUserVM.getPassword()
            );
        } else if (profile.getName().equalsIgnoreCase("ADMINISTRATOR") || profile.getName().equalsIgnoreCase("ADMIN")) {
            notificationService.sendNotification(
                    user,
                    EmailTemplatesEnum.ADMIN_ACCOUNT_ACTIVATION,
                    managedUserVM.getLogin(),
                    managedUserVM.getPassword()
            );
        } else if (profile.getName().equalsIgnoreCase("PRINCIPAL OFFICER") || profile.getName().equalsIgnoreCase("PRINCIPAL_OFFICER")) {
            notificationService.sendNotification(
                    user,
                    EmailTemplatesEnum.PRINCIPAL_OFFICER_ACCOUNT_ACTIVATION,
                    managedUserVM.getLogin(),
                    managedUserVM.getPassword()
            );
        } else {

            notificationService.sendNotification(
                    user,
                    EmailTemplatesEnum.ACCOUNT_ACTIVATION,
                    managedUserVM.getLogin(),
                    managedUserVM.getPassword()
            );
        }
    }

    private void afterPensionerRegister(User user, MemberDTO memberDTO) {
        PensionerDTO pensionerDTO = fundMasterClient.getPensionerDetails(Long.parseLong(memberDTO.getPensionerId()));
        if (pensionerDTO != null && pensionerDTO.isSuccess()) {
            if (pensionerDTO.getRows() != null) {
                Object o = pensionerDTO.getRows().get(0);
                if (o != null) {
                    JSONParser jsonParser = new JSONParser();
                    try {
                        JSONObject jsonObject = (JSONObject) jsonParser.parse(
                                new ObjectMapper().writeValueAsString(o)
                        );
                        if (jsonObject != null) {
                            if (jsonObject.containsKey("memberId"))
                                user.getUserDetails().setMemberId(Long.parseLong(String.valueOf(jsonObject.get("memberId")))
                                );
                            if (jsonObject.containsKey("pensionerNo"))
                                user.getUserDetails().setNationalPenNo(String.valueOf(
                                        jsonObject.get("pensionerNo")
                                ));

                            String name = String.valueOf(jsonObject.get("name"));
                            //split name by space
                            String[] nameParts = name.trim().split("\\s+");

                            if (nameParts.length >= 2) {
                                user.setFirstName(nameParts[0]);
                                user.setLastName(nameParts[1]);
                            }
                        }
                        userRepository.edit(user);
                    } catch (ParseException | JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void afterMemberRegister(User user, MemberDTO memberDTO) {
        FmListDTO fmListDTO = fmMemberClient.getMemberDetails(memberDTO.getMemberId());
        if (fmListDTO != null && fmListDTO.isSuccess()) {
            if (fmListDTO.getRows() != null) {
                Object o = fmListDTO.getRows().get(0);
                if (o != null) {
                    JSONParser jsonParser = new JSONParser();
                    try {
                        JSONObject jsonObject = (JSONObject) jsonParser.parse(
                                new ObjectMapper().writeValueAsString(o)
                        );
                        if (jsonObject != null) {
                            if (jsonObject.containsKey("name"))
                                user.getUserDetails().setName(String.valueOf(
                                        jsonObject.get("name")
                                ));
                            if (jsonObject.containsKey("firstname"))
                                user.setFirstName(String.valueOf(
                                        jsonObject.get("firstname")
                                ));
                            if (jsonObject.containsKey("surname"))
                                user.setLastName(String.valueOf(
                                        jsonObject.get("surname")
                                ));
                        }
                        userRepository.edit(user);
                    } catch (ParseException | JsonProcessingException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public Response activateAccount(String key) {
        return userService.activateRegistration(key)
                .map(user -> Response.ok().entity(
                        SuccessVM.builder().success(true).build()
                ).build())
                .orElse(Response.status(INTERNAL_SERVER_ERROR).entity(
                        ErrorVM.builder()
                                .success(false)
                                .msg(INTERNAL_SERVER_ERROR.getReasonPhrase())
                                .build()
                ).build());
    }

    @Override
    public String isAuthenticated() {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    @Override
    public Response getAccount() {
//        return Optional.ofNullable(userService.getUserWithAuthorities())
//                .map(user -> Response.ok(new UserDTO(user)).build())
//                .orElse(Response.status(INTERNAL_SERVER_ERROR).build());
        return null;
    }

    @Override
    public Response saveAccount(@Valid UserDTO userDTO) {

        final String userLogin = securityHelper.getCurrentUserLogin();
        Optional<User> existingUser = userRepository.findOneByEmail(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            return Response.status(BAD_REQUEST).entity(
                    ErrorVM.builder().msg("Email already in use").success(false).build()
            ).build();
        }
        return userRepository
                .findOneByLogin(userLogin)
                .map(user -> {
                    userService.updateUser(userDTO.getFmIdentifier(), userDTO.getEmail(),
                            userDTO.getLangKey());
                    return Response.ok().entity(SuccessVM.builder().success(true).build())
                            .build();
                })
                .orElseGet(() -> Response.status(INTERNAL_SERVER_ERROR).entity(
                        ErrorVM.builder().success(false).msg(INTERNAL_SERVER_ERROR.getReasonPhrase()).build()
                ).build());
    }

    @Override
    public Response changePassword(PasswordChangeVM passwordChangeVM) {
        if (checkPasswordLength(passwordChangeVM.getNewPassword())) {
            return Response.status(BAD_REQUEST).entity(
                    ErrorVM.builder().msg(INVALID_PASSWORD_TYPE).success(false).build()
            ).build();
        }
        User user = userRepository.find(passwordChangeVM.getUserId());
        if (user == null) {
            return Response.status(BAD_REQUEST).entity(
                    ErrorVM.builder().msg("Kindly login again").success(false).build()
            ).build();
        }
        passwordChangeVM.setLogin(user.getLogin());
        user = userService.changePassword(passwordChangeVM.getLogin(), passwordChangeVM.getCurrentPassword(), passwordChangeVM.getNewPassword(), true);
        if (user != null)
            return Response.ok().entity(
                    SuccessVM.builder().success(true).build()
            ).build();

        return ErrorMsg("Current password is incorrect");
    }

    @Override
    public Response changePasswordNoCurrentPwd(PasswordChangeVM passwordChangeVM) {
        if (checkPasswordLength(passwordChangeVM.getNewPassword())) {
            return Response.status(BAD_REQUEST).entity(
                    ErrorVM.builder().msg(INVALID_PASSWORD_TYPE).success(false).build()
            ).build();
        }
        User user = userRepository.find(passwordChangeVM.getUserId());
        if (user == null) {
            return Response.status(BAD_REQUEST).entity(
                    ErrorVM.builder().msg("Kindly login again").success(false).build()
            ).build();
        }
        passwordChangeVM.setLogin(user.getLogin());
        user = userService.changePassword(passwordChangeVM.getLogin(), passwordChangeVM.getCurrentPassword(), passwordChangeVM.getNewPassword(), false);
        if (user != null)
            return Response.ok().entity(
                    SuccessVM.builder().success(true).build()
            ).build();

        return ErrorMsg("Current password is incorrect");
    }

    @Override
    public Response requestPasswordReset(String mail) {
        Optional<User> userOptional = userRepository.findOneByEmail(mail);
        if (userOptional.isPresent()) {
            if (userOptional.get().isDeactivatedByAdmin()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(ErrorVM.builder().success(false).msg("Account was locked by admin. Please contact your administrator").build())
                        .build();
            }
        }
        return userService.requestPasswordReset(mail)
                .map(user -> {
//                    mailService.sendPasswordResetMail(user);
                    notificationService.sendNotification(
                            user,
                            EmailTemplatesEnum.REQUEST_PASSWORD_RESET,
                            user.getLogin(),
                            StringUtil.generateRandomPassword(7)
                    );
                    return Response.ok().entity(
                            SuccessVM.builder().success(true).build()
                    ).build();
                }).orElse(Response.status(BAD_REQUEST).entity(
                        ErrorVM.builder().success(false).msg(EMAIL_NOT_FOUND_TYPE).build()
                ).build());
    }

    @Override
    public Response finishPasswordReset(KeyAndPasswordVM keyAndPassword) {
        if (checkPasswordLength(keyAndPassword.getNewPassword())) {
            return Response.status(BAD_REQUEST).entity(
                    ErrorVM.builder().msg(INVALID_PASSWORD_TYPE).success(false).build()
            ).build();
        }
        return userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey())
                .map(user -> Response.ok().entity(
                        SuccessVM.builder().success(true)
                                .msg("Successful").build()
                ).build())
                .orElse(Response.status(INTERNAL_SERVER_ERROR).entity(
                        ErrorVM.builder().msg(INTERNAL_SERVER_ERROR.getReasonPhrase()).success(false).build()
                ).build());
    }

    @Override
    public Response checkIfMemberExists(String identifierValue, String email, String profile) {
        return Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(fundMasterClient.checkMemberIfExists(identifierValue, email, profile))
                                .build()
                ).build();
    }

    @Override
    public Response etlRequestPasswordReset(String username, String type) {
//        return userRepository.findOneByLogin(username)
//                .map(this::etlRequestPwdReset)
//                .orElseGet(() -> userRepository.findOneByEmail(username)
//                        .map(this::etlRequestPwdReset)
//                        .orElseGet(() -> ErrorMsg("Account does not exist with " + type + " " + username))
//                );
        return userRepository.findOneByLogin(username)
                .map(this::etlRequestPwdReset)
                .orElseGet(() -> ErrorMsg("Account does not exist with " + type + " " + username));
    }

    Response etlRequestPwdReset(User user) {
        String code = StringUtil.generateRandomCode(6);
        user.setResetKey(code);
        user.setResetDate(LocalDateTime.now());
        userRepository.edit(user);
        String email = user.getEmail();
        String phone = user.getUserDetails().getCellPhone();
        boolean hasEmail = !email.isEmpty();
        boolean hasPhone = !phone.isEmpty();
        if (hasEmail || hasPhone) {
            if (hasEmail) {
                mailService.sendPlainEmail(
                        email.trim(),
                        "Reset Password Code",
                        "Dear " + user.getUserDetails().getName() + "<br/><br/>You recently requested for password reset. You" +
                                " the code below to reset your password. If it was not you, ignore this message" +
                                ".<br/><h2>" + code + "</h2>"
                );
            }
            if (hasPhone) {
                fundMasterClient.sendSmsEtl(
                        new SMSVM(
                                phone.trim(),
                                code
                        )
                );
            }

            String c =
                    hasPhone && hasEmail ? "Reset code has been sent to your phone and email"
                            : hasPhone ? "SMS has been sent to your phone" : "Reset code has been sent to your email(" + email +
                            ")";

            return SuccessMsg(c, null);
        }
        return ErrorMsg("No active email address or phone number registered. Kindly contact system administrator");
    }

    @Override
    public Response etlRequestPasswordVerifyCode(String username, String otpCode) {
        return userRepository.findOneByLogin(username)
                .map(user -> {
                    String code = user.getResetKey();
                    if (otpCode.trim().equalsIgnoreCase(code)) {
                        user.setResetKey(null);
                        user.setResetDate(null);
                        userRepository.edit(user);
                        return SuccessMsg("Done", null);
                    }
                    return ErrorMsg("Invalid code");
                }).orElseGet(() -> ErrorMsg("Account not found"));
    }

    @Override
    public Response etlResetPwdFinish(String username, String pwd) {
        return userRepository.findOneByLogin(username)
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(pwd.trim()));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    userRepository.edit(user);
                    return SuccessMsg("Done", null);
                }).orElseGet(() -> ErrorMsg("Account not found"));
    }

    @Override
    public Response testEmail(long userId, String action) {
        User user = userRepository.find(userId);

        if (user != null) {
            user.setActivationKey(RandomUtil.generateActivationKey());
//            mailService.sendActivationEmail(user);
            notificationService.sendNotification(
                    user,
                    EmailTemplatesEnum.ACCOUNT_ACTIVATION,
                    "Username",
                    StringUtil.generateRandomPassword(7)
            );
//            mailService.sendActivationEmail(user,"Username",generateRandomPassword(7));
            return Response.status(OK).entity(
                    SuccessVM.builder().success(true).build()
            ).build();
        }
        return ErrorMsg("User not found");
    }

    @Override
    public Response testEmailPlain(String email) {

        log.info("Sending email started to " + email);

        mailService.sendPlainEmail(
                email,
                "Test plain Email",
                "<h3>Lorem Ipsum</h3> is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
        );

        return Response.status(OK).entity(
                SuccessVM.builder().success(true).build()
        ).build();
    }

    private boolean checkPasswordLength(String password) {
        return StringUtils.isEmpty(password)
                || password.length() < PASSWORD_MIN_LENGTH
                || password.length() > PASSWORD_MAX_LENGTH;
    }


}
