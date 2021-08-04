package com.systech.mss.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.systech.mss.config.Helper;
import com.systech.mss.controller.MemberBatchUploadController;
import com.systech.mss.controller.vm.*;
import com.systech.mss.domain.Config;
import com.systech.mss.domain.User;
import com.systech.mss.domain.common.Clients;
import com.systech.mss.repository.ConfigRepository;
import com.systech.mss.service.ActivityTrailService;
import com.systech.mss.service.FundMasterClient;
import com.systech.mss.service.UserService;
import com.systech.mss.service.dto.FmListDTO;
import com.systech.mss.service.dto.MemberUploadDTO;
import com.systech.mss.service.dto.SucessAndRowsDTO;
import com.systech.mss.service.enums.MembershipStatus;
import com.systech.mss.service.enums.YesNo;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class MemberBatchUploadControllerImpl implements MemberBatchUploadController {
    private final String UPLOADED_FILE_PATH = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
    @Inject
    Logger log;

    @Inject
    FundMasterClient fundMasterClient;
    @Inject
    ActivityTrailService activityTrailService;

    @Inject
    UserService userService;

    @Inject
    public ConfigRepository configRepository;

    Map<String, Object> validImports = new HashMap<>();
    Map<String, Object> inValidImports = new HashMap<>();

    List<MemberUploadDTO> validMembers = new ArrayList<>();
    List<MemberUploadDTO> membersException = new ArrayList<>();

    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy");
    private Map<String, Object> convertToCSV(Sheet sheet, long mssuserid, long sponsorId, long schemeId,List<MemberClassVm> memberClassVMS,List<CompanyClassVM> companyClassVMS) throws JsonProcessingException {
        Row row = null;
        List<List<String>> sheetValues = new ArrayList<>();
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            List<String> rowValues = new ArrayList<>();
            row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                rowValues.add(String.valueOf(row.getCell(j)));
            }
            sheetValues.add(rowValues);
        }
        return parseLines(sheetValues, mssuserid, sponsorId, schemeId, memberClassVMS,companyClassVMS);
    }

    @Override
    public Response uploadFile(long mssuserid, long sponsorId, long schemeId, MultipartFormDataInput input) {
        activityTrailService.logActivityTrail(mssuserid, "Upload or update member in batch");
        String fileName = "";
        Map<String, Object> json = null;

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("uploadedFile");

        SucessAndRowsDTO fmListDTO = fundMasterClient.getMemberClassesEtl(sponsorId, 0, 0);

        SucessAndRowsDTO sucessAndRowsDTO = fundMasterClient.getCompaniesEtl(sponsorId, 0, 0);

        List<MemberClassVm> memberClassVMS = new ArrayList<>();
        if (fmListDTO.isSuccess()) {
            List<Object> rows = Collections.singletonList(fmListDTO.getRows().get(0));
            for (Object row : rows) {
                MemberClassVm memberClassVm = MemberClassVm.fromObject(row);
                if (memberClassVm != null) {
                    memberClassVMS.add(memberClassVm);
                }
            }
        }

        List<CompanyClassVM> companyClassVMS = new ArrayList<>();
        if (sucessAndRowsDTO.isSuccess()) {
            List<Object> rows = Collections.singletonList(sucessAndRowsDTO.getRows().get(0));
            for (Object row : rows) {
                CompanyClassVM companyClassVM = CompanyClassVM.from(row);
                if (companyClassVM != null) {
                    companyClassVMS.add(companyClassVM);
                }
            }
        }

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

                for (int i = 0; i < memberWorkBook.getNumberOfSheets(); i++) {
                    json = convertToCSV(memberWorkBook.getSheetAt(0), mssuserid, sponsorId, schemeId, memberClassVMS, companyClassVMS);
                }

                memberWorkBook.close();
                inputStream.close();
                file.delete();

            } catch (IOException e) {
                log.error("Error:{}",e.getLocalizedMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ErrorVM.builder().success(false)
                        .msg(e.getLocalizedMessage()).build()
                ).build();
            }
        }
        return Response.status(200)
                .entity(SuccessVM.builder().success(true).data(json).build()).build();
    }
    //create DTOS
    Map<String, Integer> titles = new HashMap<>();

    long convertStringToLong(String number) {
        if (number.equals("")) {
            return 0L;
        }
        Float stringFloat = new Float(number);
        return stringFloat.longValue();
    }

    private Map<String, Object> parseLines(List<List<String>> lines, long userId, long sponsorId,long schemeId,List<MemberClassVm> memberClassVMS,List<CompanyClassVM> companyClassVMS) throws JsonProcessingException {
//        User user = userService.getUserById(userId);
        for (int i = 0; i < lines.size(); i++) {
            if (i == 0) {
                //add all titles to map
                for (int j = 0; j < lines.get(i).size(); j++) {
                    titles.put(String.valueOf(lines.get(i).get(j)), j);
                }
                continue;
            }

            MemberUploadDTO userDTO = new MemberUploadDTO();
            List<String> rowValues = lines.get(i);
            userDTO.setSchemeId(schemeId);
            userDTO.setSponsorId(sponsorId);
            Config config = configRepository.getActiveConfig();
            if (config.getClient().equals(Clients.ETL)) {
                try {
                    userDTO.setSurname(String.valueOf(rowValues.get(titles.get("Surname")) == null?"":rowValues.get(titles.get("Surname"))));
                    userDTO.setFirstname(String.valueOf(rowValues.get(titles.get("FirstName")) == null?"":rowValues.get(titles.get("FirstName"))));
                    userDTO.setOthernames(String.valueOf(rowValues.get(titles.get("OtherNames")) == null?"":rowValues.get(titles.get("OtherNames"))));

                    userDTO.setTown(String.valueOf(rowValues.get(titles.get("Town")) == null?"":rowValues.get(titles.get("Town"))));


                    userDTO.setTitle(String.valueOf(rowValues.get(titles.get("Title"))==null?"UNKNOWN": rowValues.get(titles.get("Title")).toUpperCase()));

                    userDTO.setResidentialAddress(String.valueOf(rowValues.get(titles.get("Residential Address")) == null?"":rowValues.get(titles.get("Residential Address"))));
                    userDTO.setPinNo(String.valueOf(rowValues.get(titles.get("PinNumber")) == null?"":rowValues.get(titles.get("PinNumber"))));
                    userDTO.setIdNo(String.valueOf(rowValues.get(titles.get("IDNumber")) == null?"":rowValues.get(titles.get("IDNumber"))));
                    if (!memberClassVMS.isEmpty()) {

                        for (MemberClassVm memberClassVm : memberClassVMS) {
                            userDTO.setMclassId(convertStringToLong(memberClassVm.getMemberClassId()));
                            }

                    }

                    if (!companyClassVMS.isEmpty()) {

                        for (CompanyClassVM companyClassVM : companyClassVMS) {
                            userDTO.setCompanyId(convertStringToLong(companyClassVM.getCompanyId()));
                        }

                    }


                    String dateOfEmployment = String.valueOf(rowValues.get(titles.get("DoEmployment")) == null?"":rowValues.get(titles.get("DoEmployment")));
                    if (dateOfEmployment == ""){
                        userDTO.setException("Date of Employment is required");
                    }
                    else {
                        userDTO.setDateOfEmployment(String.valueOf(rowValues.get(titles.get("DoEmployment")) == null?"":rowValues.get(titles.get("DoEmployment"))));
                    }

                    String dateOfJoiningScheme= String.valueOf(rowValues.get(titles.get("DoScheme")) == null?"":rowValues.get(titles.get("DoScheme")));
                    if (dateOfJoiningScheme == ""){
                        userDTO.setException("Date of Joining Scheme is required");
                    }
                    else {
                        userDTO.setDateJoinedScheme(String.valueOf(rowValues.get(titles.get("DoScheme")) == null?"":rowValues.get(titles.get("DoScheme"))));;
                    }

                    String dateOfBirth= String.valueOf(rowValues.get(titles.get("DoBirth"))==null?"": rowValues.get(titles.get("DoBirth")));
                    if (dateOfBirth == ""){
                        userDTO.setException("Date of Birth is required");
                    }
                    else {
                        userDTO.setDob(String.valueOf(rowValues.get(titles.get("DoBirth"))==null?"": rowValues.get(titles.get("DoBirth"))));
                    }


                    userDTO.setDepartment(String.valueOf(rowValues.get(titles.get("Department")) == null?"":rowValues.get(titles.get("Department"))));


                    userDTO.setGender(String.valueOf(rowValues.get(titles.get("Gender"))==null?"UNKNOWN": rowValues.get(titles.get("Gender")).toUpperCase()=="F"?"FEMALE":"MALE"));
                    userDTO.setEmail(String.valueOf(rowValues.get(titles.get("Email")) == null?"":rowValues.get(titles.get("Email"))));


                    userDTO.setMaritalStatus(String.valueOf(rowValues.get(titles.get("Maritalstatus"))==null?"NOT_SPECIFIED": rowValues.get(titles.get("Maritalstatus")).toUpperCase()));
                    userDTO.setCurrentAnnualPensionableSalary(new BigDecimal(String.valueOf(rowValues.get(titles.get("AnnualSalary"))==null? BigDecimal.ZERO : rowValues.get(titles.get("AnnualSalary")))));
                    userDTO.setDesignation(String.valueOf(rowValues.get(titles.get("Designation"))==null?"": rowValues.get(titles.get("Designation")).toUpperCase()));


                    userDTO.setRegion(String.valueOf(rowValues.get(titles.get("Region")) == null?"":rowValues.get(titles.get("Region"))));
                    userDTO.setCountry(String.valueOf(rowValues.get(titles.get("Town")) == null?"":rowValues.get(titles.get("Town"))));
                    userDTO.setSubRegion(String.valueOf(rowValues.get(titles.get("Sub Region")) == null?"":rowValues.get(titles.get("Sub Region"))));
                    userDTO.setDepot(String.valueOf(rowValues.get(titles.get("Depot")) == null?"":rowValues.get(titles.get("Depot"))));

                    userDTO.setStaffNo(String.valueOf(rowValues.get(titles.get("Staff Number")) == null?"":rowValues.get(titles.get("Staff Number"))));

                    userDTO.setBuilding(String.valueOf(rowValues.get(titles.get("Building")) == null?"":rowValues.get(titles.get("Building"))));
                    userDTO.setRoad(String.valueOf(rowValues.get(titles.get("Road")) == null?"":rowValues.get(titles.get("Road"))));
                    userDTO.setFixedPhone(String.valueOf(rowValues.get(titles.get("Telephone"))== null?"":rowValues.get(titles.get("Telephone"))));
                    userDTO.setCellPhone(String.valueOf(rowValues.get(titles.get("Cell Phone"))== null?"":rowValues.get(titles.get("Cell Phone"))));
                    userDTO.setFax(String.valueOf(rowValues.get(titles.get("Cell Phone"))== null?"":rowValues.get(titles.get("Cell Phone"))));
                    userDTO.setTelex(String.valueOf(rowValues.get(titles.get("Cell Phone"))== null?"":rowValues.get(titles.get("Cell Phone"))));

                    userDTO.setMbshipStatus(MembershipStatus.ACTIVE);

                    userDTO.setDatesConfirmed(YesNo.YES);

                    userDTO.setPostalAddress(String.valueOf(rowValues.get(titles.get("Address")) == null?"":rowValues.get(titles.get("Address"))));

                    userDTO.setPartyRefNo(String.valueOf(rowValues.get(titles.get("Unique Reference Number")) == null?"":rowValues.get(titles.get("Unique Reference Number"))));


                    userDTO.setNationalPenNo(String.valueOf(rowValues.get(titles.get("IDNumber")) == null?"":rowValues.get(titles.get("IDNumber"))));

                    userDTO.setIdType(String.valueOf(rowValues.get(titles.get("IDType"))==null?"NOT_SPECIFIED": rowValues.get(titles.get("IDType")).toUpperCase()));
                    userDTO.setPayrollNo(String.valueOf(rowValues.get(titles.get("Payroll No")) == null?"":rowValues.get(titles.get("Payroll No"))));

                    //  userDTO.setJobGradeId(convertStringToLong(rowValues.get(titles.get("Grade"))==null?"": rowValues.get(titles.get("Grade"))));


                } catch (Exception e) {
                    e.printStackTrace();
                    // return sendResponse("{success: false, msg: \"Unable to find Subprocess!\"}");
                }
            }
            else {
                try {

                    userDTO.setSurname(String.valueOf(rowValues.get(titles.get("SURNAME")) == null?"":rowValues.get(titles.get("SURNAME"))));
                    userDTO.setFirstname(String.valueOf(rowValues.get(titles.get("FIRSTNAME")) == null?"":rowValues.get(titles.get("FIRSTNAME"))));
                    userDTO.setOthernames(String.valueOf(rowValues.get(titles.get("OTHERNAMES")) == null?"":rowValues.get(titles.get("OTHERNAMES"))));

                    String costCenter = String.valueOf(rowValues.get(titles.get("COST CENTER")) == null?"":rowValues.get(titles.get("COST CENTER")));

                    if (costCenter == ""){
                        userDTO.setException("Cost Center cannot be null");
                    }
                    else {
                        userDTO.setCompanyId(fundMasterClient.getCompanyId(String.valueOf(rowValues.get(titles.get("COST CENTER")) == null?"":rowValues.get(titles.get("COST CENTER")))));
                    }

                    userDTO.setMclassId(fundMasterClient.getMemberClassId(String.valueOf(rowValues.get(titles.get("MEMBER CLASS")) == null?"":rowValues.get(titles.get("MEMBER CLASS")))));
                    userDTO.setTown(String.valueOf(rowValues.get(titles.get("TOWN")) == null?"":rowValues.get(titles.get("TOWN"))));


                    userDTO.setTitle(String.valueOf(rowValues.get(titles.get("TITLE"))==null?"UNKNOWN": rowValues.get(titles.get("TITLE")).toUpperCase()));

                    userDTO.setResidentialAddress(String.valueOf(rowValues.get(titles.get("POSTAL ADDRESS")) == null?"":rowValues.get(titles.get("POSTAL ADDRESS"))));
                    userDTO.setPinNo(String.valueOf(rowValues.get(titles.get("TPIN")) == null?"":rowValues.get(titles.get("TPIN"))));
                    userDTO.setIdNo(String.valueOf(rowValues.get(titles.get("NATIONAL ID NUMBER")) == null?"":rowValues.get(titles.get("NATIONAL ID NUMBER"))));

                    String dateOfEmployment = String.valueOf(rowValues.get(titles.get("DOEMPLOYMENT")) == null?"":rowValues.get(titles.get("DOEMPLOYMENT")));
                    if (dateOfEmployment == ""){
                        userDTO.setException("Date of Employment is required");
                    }
                    else {
                        userDTO.setDateOfEmployment(String.valueOf(rowValues.get(titles.get("DOEMPLOYMENT")) == null?"":rowValues.get(titles.get("DOEMPLOYMENT"))));
                    }

                    String dateOfJoiningScheme= String.valueOf(rowValues.get(titles.get("DOJSCHEME")) == null?"":rowValues.get(titles.get("DOJSCHEME")));
                    if (dateOfJoiningScheme == ""){
                        userDTO.setException("Date of Joining Scheme is required");
                    }
                    else {
                        userDTO.setDateJoinedScheme(String.valueOf(rowValues.get(titles.get("DOSCHEME")) == null?"":rowValues.get(titles.get("DOSCHEME"))));;
                    }

                    String dateOfBirth= String.valueOf(rowValues.get(titles.get("DOB"))==null?"": rowValues.get(titles.get("DOB")));
                    if (dateOfBirth == ""){
                        userDTO.setException("Date of Birth is required");
                    }
                    else {
                        userDTO.setDob(String.valueOf(rowValues.get(titles.get("DOB"))==null?"": rowValues.get(titles.get("DOB"))));
                    }


                    //  userDTO.setDepartment(String.valueOf(rowValues.get(titles.get("Department")) == null?"":rowValues.get(titles.get("Department"))));


                    userDTO.setGender(String.valueOf(rowValues.get(titles.get("GENDER"))==null?"UNKNOWN": rowValues.get(titles.get("GENDER")).toUpperCase()=="F"?"FEMALE":"MALE"));

                    String email= String.valueOf(rowValues.get(titles.get("EMAIL")) == null?"":rowValues.get(titles.get("EMAIL")));
                    if (email == ""){
                        userDTO.setException("Please Enter A valid Email");
                    }
                    else {
                        userDTO.setEmail(String.valueOf(rowValues.get(titles.get("EMAIL")) == null?"":rowValues.get(titles.get("EMAIL"))));
                    }



                    userDTO.setRegion(String.valueOf(rowValues.get(titles.get("NATIONALITY")) == null?"":rowValues.get(titles.get("NATIONALITY"))));
                    userDTO.setSubRegion(String.valueOf(rowValues.get(titles.get("NATIONALITY")) == null?"":rowValues.get(titles.get("NATIONALITY"))));
                    userDTO.setDepot(String.valueOf(rowValues.get(titles.get("NATIONALITY")) == null?"":rowValues.get(titles.get("NATIONALITY"))));

                    userDTO.setStaffNo(String.valueOf(rowValues.get(titles.get("STAFF NUMBER")) == null?"":rowValues.get(titles.get("STAFF NUMBER"))));

                    //userDTO.setBuilding(String.valueOf(rowValues.get(titles.get("Building")) == null?"":rowValues.get(titles.get("Building"))));
                    //userDTO.setRoad(String.valueOf(rowValues.get(titles.get("Road")) == null?"":rowValues.get(titles.get("Road"))));
                    userDTO.setFixedPhone(String.valueOf(rowValues.get(titles.get("TELEPHONE")) == null?"":rowValues.get(titles.get("TELEPHONE"))));
                    userDTO.setCellPhone(String.valueOf(rowValues.get(titles.get("CELLPHONE")) == null?"":rowValues.get(titles.get("CELLPHONE"))));
                    userDTO.setPostalAddress(String.valueOf(rowValues.get(titles.get("POSTAL ADDRESS")) == null?"":rowValues.get(titles.get("POSTAL ADDRESS"))));

                    // userDTO.setPartyRefNo(String.valueOf(rowValues.get(titles.get("Unique Reference Number")) == null?"":rowValues.get(titles.get("Unique Reference Number"))));


                    userDTO.setNationalPenNo(String.valueOf(rowValues.get(titles.get("NATIONAL PEN NUMBER")) == null?"":rowValues.get(titles.get("NATIONAL PEN NUMBER"))));

                    userDTO.setIdType(String.valueOf(rowValues.get(titles.get("OTHER IDENTIFICATION TYPES(PASSPORT,VOTER,DRIVER)"))==null?"NOT_SPECIFIED": rowValues.get(titles.get("OTHER IDENTIFICATION TYPES(PASSPORT,VOTER,DRIVER)")).toUpperCase()));
                    userDTO.setMaritalStatus(String.valueOf(rowValues.get(titles.get("CURRENT MARITAL STATUS"))==null?"NOT_SPECIFIED": rowValues.get(titles.get("CURRENT MARITAL STATUS")).toUpperCase()));
                    //userDTO.setPayrollNo(String.valueOf(rowValues.get(titles.get("Payroll No")) == null?"":rowValues.get(titles.get("Payroll No"))));



                    //userDTO.setJobGradeId(Long.valueOf(rowValues.get(titles.get("Grade"))==null?"": rowValues.get(titles.get("Grade"))));

                    userDTO.setCurrentAnnualPensionableSalary(new BigDecimal(String.valueOf(rowValues.get(titles.get("MONTHLY SALARY"))==null? BigDecimal.ZERO : rowValues.get(titles.get("MONTHLY SALARY")))));
                    //userDTO.setCurrentAnnualPensionableSalary(rowValues.get(titles.get("AnnualSalary")))

                    userDTO.setPlaceOfBirthDistrictId(String.valueOf(rowValues.get(titles.get("PLACE OF BIRTH DISTRICT"))==null?"": rowValues.get(titles.get("PLACE OF BIRTH DISTRICT"))));
                    userDTO.setPermanentDistrictId(String.valueOf(rowValues.get(titles.get("PARMANENT DISTRICT"))==null?"": rowValues.get(titles.get("PARMANENT DISTRICT"))));
                    userDTO.setPlaceOfBirthTAId(String.valueOf(rowValues.get(titles.get("PLACE OF BIRTH TA"))==null?"": rowValues.get(titles.get("PLACE OF BIRTH TA"))));
                    userDTO.setPlaceOfBirthVillageId(String.valueOf(rowValues.get(titles.get("PLACE OF BIRTH VILLAGE"))==null?"": rowValues.get(titles.get("PLACE OF BIRTH VILLAGE"))));
                    userDTO.setPermanentTAId(String.valueOf(rowValues.get(titles.get("PARMANENT TA"))==null?"": rowValues.get(titles.get("PARMANENT TA"))));
                    userDTO.setPermanentVillageId(String.valueOf(rowValues.get(titles.get("PARMANENT VILLAGE"))==null?"": rowValues.get(titles.get("PARMANENT VILLAGE"))));

//                     String email = rowValues.get(titles.get("Email")).toString();
//                    if (helper.isEmailAddress(email)) {
//                        userDTO.setEmailAddress(email);
//
//                    } else {
//                        userDTO.setException("Invalid Email " + email);
//                    }
//                    String phone = rowValues.get(titles.get("Cell Phone")).toString();
//                    if (helper.isValidPhone(phone)) {
//                        userDTO.setPhoneNumber(phone);
//
//                    } else {
//                        userDTO.setException("Invalid Phone Number " + phone);
//                    }
//                    String secondaryPhone = rowValues.get(titles.get("Telephone")).toString();
//                    if (helper.isValidPhone(secondaryPhone)) {
//                        userDTO.setSecondaryPhoneNumber(secondaryPhone);
//
//                    } else {
//                        userDTO.setException("Invalid Secondary Phone Number " + secondaryPhone);
//                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    // return sendResponse("{success: false, msg: \"Unable to find Subprocess!\"}");
                }
            }

            if (userDTO.getException()!=null){
                membersException.add(userDTO);
            }
            else{
                validMembers.add(userDTO);
            }

        }

        validImports.put("totalCount", validMembers.size());
        validImports.put("rows", validMembers);
        log.info("Valid Members: " + validMembers.size());
        log.info("Valid Members List: " + validMembers);

        inValidImports.put("totalCount", membersException.size());
        inValidImports.put("rows", membersException);
        log.info("Invalid Members with Exception Size: " + membersException.size());
        log.info("Invalid Members with Exception Json: " + membersException);

        Map<String, Object> jsonMap = new HashMap<>();

        jsonMap.put("success", true);

        jsonMap.put("validImports", validImports);
        jsonMap.put("inValidImports", inValidImports);

        log.info("Json MAP: " + jsonMap);
        return jsonMap;

    }

    @Override
    public Response saveFile(long mssuserid, @Valid SaveMemberBatchVM saveMemberBatchVM)  {

        List<MemberUploadDTO> memberUploadDTOList = saveMemberBatchVM.getRows().stream().map(
                x -> MemberUploadDTO.builder().
                        avcRate(x.getAvcRate())
                        .building(x.getBuilding())
                        .country(x.getCountry())
                        .dateJoinedScheme(x.getDateJoinedScheme())
                        .dateOfEmployment(x.getDateOfEmployment())
                        .cellPhone(x.getCellPhone())
                        .department(x.getDepartment())
                        .depot(x.getDepot())
                        .designation(x.getDesignation())
                        .firstname(x.getFirstname())
                        .surname(x.getSurname())
                        .othernames(x.getOthernames())
                        .fixedPhone(x.getFixedPhone())
                        .currentAnnualPensionableSalary(x.getCurrentAnnualPensionableSalary())
                        .datesConfirmed(x.getDatesConfirmed())
                        .dob(x.getDateOfEmployment())
                        .exitDate(x.getExitDate())
                        .disabled(x.getDisabled())
                        .jobGradeId(x.getJobGradeId())
                        .maritalStatus(x.getMaritalStatus())
                        .email(x.getEmail())
                        .companyId(x.getCompanyId())
                        .employerProdNo(x.getEmployerProdNo())
                        .gender(x.getGender())
                        .joinedPrevScheme(x.getJoinedPrevScheme())
                        .fax(x.getFax())
                        .idNo(x.getIdNo())
                        .mbshipStatus(x.getMbshipStatus())
                        .mclassId(x.getMclassId())
                        .memberNo(x.getMemberNo())
                        .nationalPenNo(x.getNationalPenNo())
                        .partyRefNo(x.getPartyRefNo())
                        .idType(x.getIdType())
                        .payrollNo(x.getPayrollNo())
                        .pinNo(x.getPinNo())
                        .policyNo(x.getPolicyNo())
                        .postalAddress(x.getPostalAddress())
                        .profileId(x.getProfileId())
                        .reasonForExit(x.getReasonForExit())
                        .region(x.getRegion())
                        .subRegion(x.getSubRegion())
                        .residentialAddress(x.getResidentialAddress())
                        .road(x.getRoad())
                        .schemeId(x.getSchemeId())
                        .secondaryPhone(x.getSecondaryPhone())
                        .sponsorId(x.getSponsorId())
                        .staffNo(x.getStaffNo())
                        .telex(x.getTelex())
                        .title(x.getTitle())
                        .town(x.getTown())
                        .cellPhone(x.getCellPhone())
                        .build()
        ).collect(Collectors.toList());


        boolean batchPotentialMemberSaved = fundMasterClient.saveorupdateBatchPotentialMember(memberUploadDTOList);
        if (batchPotentialMemberSaved) {
            return Response.status(200)
                    .entity(SuccessVM.builder().success(true).msg("Success").build()).build();
        }
        return Response.status(200)
                .entity(ErrorVM.builder().success(false).msg("Not Found").build()).build();
    }

    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    //save to somewhere
    private File writeFile(byte[] content, String filename) throws IOException {

        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();
        return file;

    }
}
