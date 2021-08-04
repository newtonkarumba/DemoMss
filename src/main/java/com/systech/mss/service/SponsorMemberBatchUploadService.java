//package com.systech.mss.service;
//
//
//import com.systech.mss.config.ApiEJB;
//import com.systech.mss.config.Constants;
//import com.systech.mss.config.Fields;
//import com.systech.mss.config.Helper;
//import com.systech.mss.service.dto.SponsorUploadMemberDTO;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.slf4j.Logger;
//
//import javax.ejb.EJB;
//import javax.inject.Inject;
//import javax.json.Json;
//import javax.json.JsonObject;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.*;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@WebServlet(name = "AddBatchMember", urlPatterns = {"/addMember"})
//@MultipartConfig
//public class SponsorMemberBatchUploadService extends HttpServlet implements Serializable {
//
//    @Inject
//    private Logger logger;
//
//    Helper helper = new Helper();
//
//    @Inject
//    ApiEJB apiEJB;
//
//    List<SponsorUploadMemberDTO> membersBatchAddList = new ArrayList<SponsorUploadMemberDTO>();
//    List<SponsorUploadMemberDTO> membersBatchAddListExceptions = new ArrayList<SponsorUploadMemberDTO>();
//
//    private void uploadDocument(HttpServletRequest request, HttpServletResponse response, String
//            FILE_SEPERATOR, String SCHEME_DOC_ROOT_FOLDER, String scheme_doc_folder) {
//
//        boolean attachment = false;
//        String attachment_path = null;
//        String attachment_name = null;
//
//        try {
//            for (Part part : request.getParts()) {
//                String fileName = extractFileName(part);
//                if (!fileName.equals("")) {
//                    logger.info("File name is :::::::::" + fileName);
//                    File path = new File(getServletContext().getRealPath("/"));
//                    if (scheme_doc_folder == null) {
//                        scheme_doc_folder = path.getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getPath() + FILE_SEPERATOR + SCHEME_DOC_ROOT_FOLDER;
//                        helper.createFolderIfNotExists(scheme_doc_folder);
//                    }
//                    try {
//                        String fullpath = scheme_doc_folder + FILE_SEPERATOR + fileName;
//                        logger.info("full path is:" + fullpath);
//                        part.write(fullpath);
//                        logger.info("Complete file path is: " + fullpath);
//                        attachment_name = fileName;
//                        attachment_path = fullpath;
//                        attachment = true;
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            logger.info("Attachment has been uploaded >>>>>>>> " + attachment + " <<<<<<<<<<<");
//
//            String attachment_url = attachment_path;
//            logger.info("Attachment URL is ::::::::::::::::::> " + attachment_url);
//
//            String memberID = this.get(request, "memberID");
//
//            JSONObject member = new JSONObject();
//
//            try {
//                member.put("memberAttachmentname", attachment_name)
//                        .put("memberId", helper.toLong(memberID)).put("memberSchemeId", helper.toLong(getSessKey(request, Constants.SCHEME_ID)));
//                if (attachment)
//                    member.put("memberAttachment", attachment_url);
//                else
//                    member.put("memberAttachment", new ArrayList<String>());
//
//                boolean status_ = apiEJB.uploadMemberDocument(member.toString());
//
//                this.respond(response, status_, status_ ? "Document was successfully uploaded" : "Document was not uploaded", null);
//            } catch (JSONException e) {
//                this.respond(response, false, "Sorry, something didn't work out right. Couldn't upload document", null);
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//    }
//
//    private String memberToJsonObject(SponsorUploadMemberDTO sponsorUploadMemberDTO){
//        JsonObject jsonObject = Json.createObjectBuilder()
//                .add("surname",sponsorUploadMemberDTO.getSurname() == null ? "" : sponsorUploadMemberDTO.getSurname())
//                .add("firstname",sponsorUploadMemberDTO.getFirstname() == null ? "" : sponsorUploadMemberDTO.getFirstname())
//                .add("memberNo",sponsorUploadMemberDTO.getMemberNo() == null ? "" : sponsorUploadMemberDTO.getMemberNo())
//                .add("title", sponsorUploadMemberDTO.getTitle() == null ? "" :sponsorUploadMemberDTO.getTitle())
//                .add("othernames",sponsorUploadMemberDTO.getOthernames() == null ? "" : sponsorUploadMemberDTO.getOthernames())
//                .add("country",sponsorUploadMemberDTO.getCountry() == null ? "" : sponsorUploadMemberDTO.getCountry())
//                .add("dob",sponsorUploadMemberDTO.getDateOfBirth() == null ? "" : sponsorUploadMemberDTO.getDateOfBirth())
//                .add("dateOfEmployment",sponsorUploadMemberDTO.getDateOfEmployment() == null ? "" : sponsorUploadMemberDTO.getDateOfEmployment())
//                .add("dateJoinedScheme",sponsorUploadMemberDTO.getDateJoinedScheme() == null ? "" : sponsorUploadMemberDTO.getDateJoinedScheme())
//                //check on sponsor id and scheme id
//                .add("sponsorId",sponsorUploadMemberDTO.getSponsorId() == null ? "" :  sponsorUploadMemberDTO.getSponsorId())
//                .add("schemeId",sponsorUploadMemberDTO.getSchemeId() == null ? "" :  sponsorUploadMemberDTO.getSchemeId())
//                .add("gender",sponsorUploadMemberDTO.getGender().toUpperCase() == null ? "" :  sponsorUploadMemberDTO.getGender().toUpperCase())
//                .add("idType",sponsorUploadMemberDTO.getIdType().toUpperCase() == null ? "" :  sponsorUploadMemberDTO.getIdType().toUpperCase())
//                .add("idNo", sponsorUploadMemberDTO.getIdNumber() == null ? "" : sponsorUploadMemberDTO.getIdNumber())
//                .add("mbshipStatus",sponsorUploadMemberDTO.getMbshipStatus() == null ? "ACTIVE" : sponsorUploadMemberDTO.getMbshipStatus())
//                .add("postalAddress",sponsorUploadMemberDTO.getPostalAddress() == null ? "" :  sponsorUploadMemberDTO.getPostalAddress())
//                .add("fixedPhone",sponsorUploadMemberDTO.getPhoneNumber() == null ? "" :  sponsorUploadMemberDTO.getPhoneNumber())
//                //check on cellphone
//                .add("cellPhone",sponsorUploadMemberDTO.getPhoneNumber() == null ? "" :  sponsorUploadMemberDTO.getPhoneNumber())
//                .add("secondaryPhone",sponsorUploadMemberDTO.getSecondaryPhoneNumber() == null ? "" :  sponsorUploadMemberDTO.getSecondaryPhoneNumber())
//                .add("subRegion",sponsorUploadMemberDTO.getSubRegion() == null ? "" :  sponsorUploadMemberDTO.getSubRegion())
//                .add("email",sponsorUploadMemberDTO.getEmailAddress() == null ? "" :  sponsorUploadMemberDTO.getEmailAddress())
//                .add("road",sponsorUploadMemberDTO.getRoad() == null ? "" :  sponsorUploadMemberDTO.getRoad())
//                .add("town",sponsorUploadMemberDTO.getTown() == null ? "" :  sponsorUploadMemberDTO.getTown())
//                .add("residentialAddress",sponsorUploadMemberDTO.getResidentialAddress() == null ? "" :  sponsorUploadMemberDTO.getResidentialAddress())
//                .add("region",sponsorUploadMemberDTO.getRegion() == null ? "" :  sponsorUploadMemberDTO.getRegion())
//                .add("depot",sponsorUploadMemberDTO.getDepot() == null ? "" :  sponsorUploadMemberDTO.getDepot())
//                .add("nationalPenNo",sponsorUploadMemberDTO.getNationalPenNo() == null ? "" :  sponsorUploadMemberDTO.getNationalPenNo())
//                .add("pinNo",sponsorUploadMemberDTO.getPinNo() == null ? "" :  sponsorUploadMemberDTO.getPinNo())
//                .add("department",sponsorUploadMemberDTO.getDepartment() == null ? "" :  sponsorUploadMemberDTO.getDepartment())
//                .add("designation", sponsorUploadMemberDTO.getDesignation() == null ? "" : sponsorUploadMemberDTO.getDesignation())
//                .add("currentAnnualPensionableSalary",sponsorUploadMemberDTO.getAnnualPensionableSalary() == null ? BigDecimal.ZERO :  new BigDecimal(sponsorUploadMemberDTO.getAnnualPensionableSalary()))
//                .add("maritalStatus",sponsorUploadMemberDTO.getMaritalStatus() == null ? "" :  sponsorUploadMemberDTO.getMaritalStatus())
//                .build();
//        return jsonObject.toString();
//    }
//
//    private void addMembersViaTemplate(HttpServletRequest request, HttpServletResponse response, String
//            FILE_SEPERATOR, String SCHEME_DOC_ROOT_FOLDER, String scheme_doc_folder){
//
//        List<SponsorUploadMemberDTO> memberDTOS = membersBatchAddList;
//        int size = memberDTOS != null && memberDTOS.size() > 0 ? memberDTOS.size() : 0;
//        boolean status = false;
//
//        if (membersBatchAddListExceptions != null && membersBatchAddListExceptions.size() > 0) {
//            this.respond(response, false, "Sorry, You cannot save batch with exceptions!", null);
//        }else {
//            JsonObject jsonObject = Json.createObjectBuilder()
//                    .add("totalCount", size)
//                    .add("rows: [", )
//        }
//    }
//    private void addMembersBatchUpload(HttpServletRequest request, HttpServletResponse response, String
//            FILE_SEPERATOR, String SCHEME_DOC_ROOT_FOLDER, String scheme_doc_folder) {
//
//        logger.info(" we are just about to read a file ");
//
//        boolean attachment = false;
//        String attachment_path = null;
//        String attachment_name = null;
//
//        try {
//            String fileName = null;
//            for (Part part : request.getParts()) {
//                fileName = extractFileName(part);
//                if (!fileName.equals("")) {
//                    logger.info("File name is :::::::::" + fileName);
//                    File path = new File(getServletContext().getRealPath("/"));
//                    if (scheme_doc_folder == null) {
//                        scheme_doc_folder = path.getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getPath() + FILE_SEPERATOR + SCHEME_DOC_ROOT_FOLDER;
//                        helper.createFolderIfNotExists(scheme_doc_folder);
//                    }
//                    try {
//                        // String url = scheme_doc_folder + FILE_SEPERATOR + fileName;
//                        String fullpath = scheme_doc_folder + FILE_SEPERATOR + fileName;
//
//
//                        File file1 = new File(fullpath);
//                        if (file1.exists()) {
//                            String randomString = UUID.randomUUID().toString();
//                            randomString = randomString.substring(0, 5);
//                            fullpath = scheme_doc_folder + FILE_SEPERATOR + randomString + fileName;
//
//                            part.write(fullpath);
//
//
//                        } else {
//                            part.write(fullpath);
//                        }
//
//
//                        attachment_name = fileName;
//                        attachment_path = fullpath;
//                        attachment = true;
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//
//            String attachment_url = attachment_path;
//            Workbook workbook = WorkbookFactory.create(new File(attachment_url));
//            Sheet datatypeSheet = workbook.getSheetAt(0);
//            Iterator<Row> iterator = datatypeSheet.iterator();
//
//            membersBatchAddList.clear();
//            membersBatchAddListExceptions.clear();
//
//            Row row;
//            //int count = 1;
//            for (int i = 1; i <= datatypeSheet.getLastRowNum(); i++) {
//
//                SponsorUploadMemberDTO member = new SponsorUploadMemberDTO();
//
//                //points to the starting of excel i.e excel first row
//                row = datatypeSheet.getRow(i);  //sheet number
//
//                String surname;
//                if (row.getCell(0) == null) {
//                    surname = "null";
//                }  //suppose excel cell is empty then its set to 0 the variable
//                else surname = row.getCell(0).toString();   //else copies cell data to name variable
//
//                String firstName;
//                if (row.getCell(1) == null) {
//                    firstName = "null";
//                } else firstName = row.getCell(1).toString();
//
//                String otherNames;
//                if (row.getCell(2) == null) {
//                    otherNames = "null";
//                } else otherNames = row.getCell(2).toString();
//
//                String gender;
//                if (row.getCell(3) == null) {
//                    gender = "null";
//                } else gender = row.getCell(3).toString();
//
//                String dob;
//                if (row.getCell(4) == null) {
//                    dob = "null";
//                } else dob = row.getCell(4).toString();
//
//                String doEmployment;
//                if (row.getCell(5) == null) {
//                    doEmployment = "null";
//                } else doEmployment = row.getCell(5).toString();
//
//                String doScheme;
//                if (row.getCell(6) == null) {
//                    doScheme = "null";
//                } else doScheme = row.getCell(6).toString();
//
//                String payrollNo;
//                if (row.getCell(7) == null) {
//                    payrollNo = "null";
//                } else payrollNo = row.getCell(7).toString();
//
//                String grade;
//                if (row.getCell(8) == null) {
//                    grade = "null";
//                } else grade = row.getCell(8).toString();
//
//                String title;
//                if (row.getCell(9) == null) {
//                    title = "null";
//                } else title = row.getCell(9).toString();
//
//                String department;
//                if (row.getCell(10) == null) {
//                    department = "null";
//                } else department = row.getCell(10).toString();
//
//                String designation;
//                if (row.getCell(11) == null) {
//                    designation = "null";
//                } else designation = row.getCell(11).toString();
//
//                String maritualStatus;
//                if (row.getCell(12) == null) {
//                    maritualStatus = "null";
//                } else maritualStatus = row.getCell(12).toString();
//
//
//                String currentMonthlySalary;
//                if (row.getCell(13) == null) {
//                    currentMonthlySalary = "null";
//                } else currentMonthlySalary = row.getCell(13).toString();
//
//                String idNumber;
//                if (row.getCell(14) == null) {
//                    idNumber = "null";
//                } else idNumber = row.getCell(14).toString();
//
//                String pinNumber;
//                if (row.getCell(15) == null) {
//                    pinNumber = "null";
//                } else pinNumber = row.getCell(15).toString();
//
//                String address;
//                if (row.getCell(16) == null) {
//                    address = "null";
//                } else address = row.getCell(16).toString();
//
//                String town;
//                if (row.getCell(17) == null) {
//                    town = "null";
//                } else town = row.getCell(17).toString();
//
//                String region;
//                if (row.getCell(18) == null) {
//                    region = "null";
//                } else region = row.getCell(18).toString();
//
//                String subRegion;
//                if (row.getCell(19) == null) {
//                    subRegion = "null";
//                } else subRegion = row.getCell(19).toString();
//
//                String depot;
//                if (row.getCell(20) == null) {
//                    depot = "null";
//                } else depot = row.getCell(20).toString();
//
//                String idType;
//                if (row.getCell(21) == null) {
//                    idType = "null";
//                } else idType = row.getCell(21).toString();
//
//                String residentialAddress;
//                if (row.getCell(22) == null) {
//                    residentialAddress = "null";
//                } else residentialAddress = row.getCell(22).toString();
//
//                String staffNumber;
//                if (row.getCell(23) == null) {
//                    staffNumber = "null";
//                } else staffNumber = row.getCell(23).toString();
//
//                String socialSecurityNumber;
//                if (row.getCell(24) == null) {
//                    socialSecurityNumber = "null";
//                } else socialSecurityNumber = row.getCell(24).toString();
//
//                String building;
//                if (row.getCell(25) == null) {
//                    building = "null";
//                } else building = row.getCell(25).toString();
//
//                String road;
//                if (row.getCell(26) == null) {
//                    road = "null";
//                } else road = row.getCell(26).toString();
//
//                String telephone;
//                if (row.getCell(27) == null) {
//                    telephone = "null";
//                } else telephone = row.getCell(27).toString();
//
//                String cellPhone;
//                if (row.getCell(28) == null) {
//                    cellPhone = "null";
//                } else cellPhone = row.getCell(28).toString();
//
//                String email;
//                if (row.getCell(29) == null) {
//                    email = "null";
//                } else email = row.getCell(29).toString();
//
//
//                String uniqueReferenceNumber;
//                if (row.getCell(30) == null) {
//                    uniqueReferenceNumber = "null";
//                } else uniqueReferenceNumber = row.getCell(30).toString();
//
//
//                String savingsCategoryCode;
//                if (row.getCell(31) == null) {
//                    savingsCategoryCode = "null";
//                } else savingsCategoryCode = row.getCell(31).toString();
//
//                String savingPeriodsMonths;
//                if (row.getCell(32) == null) {
//                    savingPeriodsMonths = "null";
//                } else savingPeriodsMonths = row.getCell(32).toString();
//
//                String dateSubcribedToSavings;
//                if (row.getCell(33) == null) {
//                    dateSubcribedToSavings = "null";
//                } else dateSubcribedToSavings = row.getCell(33).toString();
//
//                String accountNumber;
//                if (row.getCell(34) == null) {
//                    accountNumber = "null";
//                } else accountNumber = row.getCell(34).toString();
//
//                String accountName;
//                if (row.getCell(35) == null) {
//                    accountName = "null";
//                } else accountName = row.getCell(35).toString();
//
//                String bankCode;
//                if (row.getCell(36) == null) {
//                    bankCode = "null";
//                } else bankCode = row.getCell(36).toString();
//
//                String branchCode;
//                if (row.getCell(37) == null) {
//                    branchCode = "null";
//                } else branchCode = row.getCell(37).toString();
//
//                member.setFirstname(firstName);
//                member.setSurname(surname);
//                member.setOthernames(otherNames);
//                member.setDateOfBirth(dob);
//                member.setGender(gender);
//                member.setDateJoinedScheme(doScheme);
//                member.setDepot(depot);
//                member.setDesignation(designation);
//                member.setDepartment(department);
//                member.setSecondaryPhoneNumber(cellPhone);
//                member.setPhoneNumber(telephone);
//                member.setEmailAddress(email);
//                member.setIdNumber(idNumber);
//                member.setIdType(idType);
//                member.setDateJoinedScheme(doScheme);
//                member.setDateOfEmployment(doEmployment);
//                member.setRoad(road);
//                member.setSubRegion(subRegion);
//                member.setNationalPenNo(payrollNo);
//                member.setTitle(title);
//                member.setRegion(region);
//                member.setResidentialAddress(residentialAddress);
//                member.setResidentialAddress(residentialAddress);
//                member.setStaffNo(staffNumber);
//                member.setAnnualPensionableSalary(currentMonthlySalary);
//                member.setTown(town);
//                member.setPinNo(pinNumber);
//                member.setSsnit(socialSecurityNumber);
//                member.setPostalAddress(address);
//                member.setMaritalStatus(maritualStatus);
//
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
//                Date doBirth = simpleDateFormat.parse(member.getDateOfBirth());
//                Date doe = simpleDateFormat.parse(member.getDateOfEmployment());
//                Date doj = simpleDateFormat.parse(member.getDateJoinedScheme());
//
//                //Calculating the age
//                Calendar doB = Calendar.getInstance();
//                Calendar currentYear = Calendar.getInstance();
//
//                doB.setTime(doBirth);
//
//                int age = (currentYear.get(Calendar.YEAR) - doB.get(Calendar.YEAR));
//                if (member.getIdNumber() != null) {
//                    String sponsorId = getSessKey(request, Constants.REQUEST_SPONSOR_ID);
//
//                    JSONObject jsonObject = apiEJB.checkIfIdNumberExists(sponsorId, idType, idNumber, "YES");
//
//                    if (jsonObject != null) {
//                        try {
//                            if (jsonObject.getBoolean(Fields.SUCCESS)) {
//                                member.setIdNumber(idNumber);
//                                if (member.getSurname() == null) {
//                                    String updateExceptions = " Surname is required, check row number:  " + i;
//                                    ;
//
//                                    member.setUpdateExceptions(updateExceptions);
//
//                                    membersBatchAddListExceptions.add(member);
//                                } else if (member.getFirstname() == null) {
//                                    String updateExceptions = " Firstname is required, check row number:  " + i;
//                                    ;
//
//                                    member.setUpdateExceptions(updateExceptions);
//
//                                    membersBatchAddListExceptions.add(member);
//                                } else if (member.getGender() == null) {
//                                    String updateExceptions = " Gender is required, check row number:  " + i;
//                                    ;
//
//                                    member.setUpdateExceptions(updateExceptions);
//
//                                    membersBatchAddListExceptions.add(member);
//                                } else if (member.getDateOfBirth() == null) {
//                                    String updateExceptions = " Date of Birth is required, check row number:  " + i;
//                                    ;
//
//                                    member.setUpdateExceptions(updateExceptions);
//
//                                    membersBatchAddListExceptions.add(member);
//                                } else if (member.getDateOfEmployment() == null) {
//                                    String updateExceptions = " Date of Employment is required, check row number:  " + i;
//                                    ;
//
//                                    member.setUpdateExceptions(updateExceptions);
//
//                                    membersBatchAddListExceptions.add(member);
//                                } else if (member.getDateJoinedScheme() == null) {
//                                    String updateExceptions = " Date Joined scheme  is required, check row number:  " + i;
//                                    ;
//
//                                    member.setUpdateExceptions(updateExceptions);
//
//                                    membersBatchAddListExceptions.add(member);
//                                } else if (member.getTitle() == null) {
//                                    String updateExceptions = " Title  is required, check row number:  " + i;
//                                    ;
//
//                                    member.setUpdateExceptions(updateExceptions);
//
//                                    membersBatchAddListExceptions.add(member);
//                                } else if (age < 18) {
//
//                                    logger.info(" The member's age is below average " + age);
//
//                                    String updateExceptions = " The Members age is below average : " + age;
//
//                                    member.setUpdateExceptions(updateExceptions);
//
//                                    membersBatchAddListExceptions.add(member);
//
//                                } else if (doe.compareTo(doBirth) < 0) {
//                                    logger.info(" The  date of employment = " + doe + "is less than date of birth = " + doBirth);
//
//                                    String updateExceptions = " The  date of employment = " + member.getDateOfEmployment() + " cannot be before date of birth = " + member.getDateOfBirth();
//
//                                    member.setUpdateExceptions(updateExceptions);
//
//                                    membersBatchAddListExceptions.add(member);
//
//
//                                } else if (doj.compareTo(doe) < 0) {
//                                    logger.info(" The date of joining scheme = " + doj + "is less than date of employment = " + doe);
//
//                                    String updateExceptions = " The date of joining scheme = " + member.getDateJoinedScheme() + " cannot be before date of Employment = " + member.getDateOfEmployment();
//
//                                    member.setUpdateExceptions(updateExceptions);
//
//                                    membersBatchAddListExceptions.add(member);
//
//                                } else if (doj.compareTo(doBirth) < 0) {
//                                    logger.info(" The date of joining scheme  = " + member.getDateJoinedScheme() + "is less than date of birth = " + member.getDateOfBirth());
//                                    String updateExceptions = " The date of joining scheme = " + member.getDateJoinedScheme() + " cannot be before date of Birth = " + member.getDateOfBirth();
//
//                                    member.setUpdateExceptions(updateExceptions);
//
//                                    membersBatchAddListExceptions.add(member);
//
//                                } else {
//                                    membersBatchAddList.add(member);
//                                    logger.info(" The Details of the member from the template === MemberNo :" + member.getMemberNo() + " Name: " + member.getFirstname() + " " + member.getSurname() + " " + member.getOthernames() +
//                                            " -> date Of Birth :" + member.getDateOfBirth());
//                                }
//
//
//                            } else {
//                                String updateExceptions = jsonObject.getString(Fields.MESSAGE);
//                                member.setUpdateExceptions(updateExceptions);
//
//                                membersBatchAddListExceptions.add(member);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//
//                } else {
//                    String updateExceptions = " ID NUmber is required, check row number:  " + i;
//
//                    member.setUpdateExceptions(updateExceptions);
//
//                    membersBatchAddListExceptions.add(member);
//                }
//
//
//            }
//
//            logger.info(" Member List size to be added : " + membersBatchAddList.size());
//
//
//            this.respond(response, true, "\"Batch Loaded Successful", null);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            this.respond(response, false, "Sorry, something didn't work out right.", null);
//
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            this.respond(response, false, "Sorry, something didn't work out right.", null);
//
//        }
//
//    }
//
//    void respond(HttpServletResponse resp, boolean status, String message, JSONObject json) {
//        try {
//            String response = helper.result(status, message, json );
//
//            resp.getWriter().write(response);
//
//        } catch (IOException ioe) {
//            logger.info("Problem sending response" + ioe.getMessage());
//        }
//
//
//    }
//    String get(HttpServletRequest req, String param) {
//        return helper.toString(req.getParameter(param));
//    }
//
//    String extractFileName(Part part) {
//        String contentDisp = part.getHeader("content-disposition");
//        String[] items = contentDisp.split(";");
//        for (String s : items) {
//            if (s.trim().startsWith("filename")) {
//                return s.substring(s.indexOf("=") + 2, s.length() - 1);
//            }
//        }
//        return "";
//    }
//    String getSessKey(HttpServletRequest req, String param) {
//        HttpSession session = req.getSession(false);
//        return session != null ? helper.toString(session.getAttribute(param)) : null;
//    }
//}