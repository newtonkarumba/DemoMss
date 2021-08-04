package com.systech.mss.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.controller.ProcessExceptionBillController;
import com.systech.mss.controller.vm.*;
import com.systech.mss.domain.Config;
import com.systech.mss.domain.common.Clients;
import com.systech.mss.fileupload.FileModel;
import com.systech.mss.service.ActivityTrailService;
import com.systech.mss.service.dto.*;
import com.systech.mss.util.ExcellExtractor;
import com.systech.mss.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class ProcessExceptionBillControllerImpl extends BaseController implements ProcessExceptionBillController {
    private final String UPLOADED_FILE_PATH = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;

    @Inject
    ActivityTrailService activityTrailService;


    Map<String, Object> validBillImports = new HashMap<>();
    Map<String, Object> invalidBillImports = new HashMap<>();

    List<ContributionBillingDTO> validBills = new ArrayList<>();
    List<ContributionBillingDTO> billsException = new ArrayList<>();


    @Override
    public Response uploadFile(long mssuserid, long billId, long sponsorId, long schemeId, MultipartFormDataInput input) throws ParseException {
        activityTrailService.logActivityTrail(mssuserid, "Uploaded a Bill Exception");

        Config config = configRepository.getActiveConfig();

        if (!config.getClient().equals(Clients.ETL)) {
            return extractcontributionBillingValidationImport(config, input);
        }

        List<FileModel> fileModels = upload(input);
        if (fileModels == null) {
            return ErrorMsg("Failed to upload file");
        }
        FileModel fileModel = fileModels.get(0);
        String fileName = fileModel.getFilePath();

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                return ErrorMsg("File not uploaded");
            }
            List<Vector<String>> lines = ExcellExtractor.extract(file.getPath(), 2);

            log.error("THE LINES HERE:" +StringUtil.toJsonString(lines));


            JSONObject jsonObject=
                    fundMasterClient.processListLinesRecursion(lines,billId,
                    sponsorId,schemeId);
//            log.error("JSON OBJECT PROCESS: " +StringUtil.toJsonString(jsonObject));
            BIllsEtlVM bIllsEtlVM=new BIllsEtlVM();
            InvalidImports invalidImports;
            ValidImports validImports;
            if (jsonObject!=null && jsonObject.has("success")){
                if (jsonObject.getBoolean("success")) {
                    bIllsEtlVM.setSuccess(true);
                    JSONObject validImportsJson=jsonObject.getJSONObject("validImports");
                    JSONObject inValidImportsJson=jsonObject.getJSONObject("invalidImports");
                    validImports=ValidImports.from(validImportsJson);
                    if (validImports!=null)
                        bIllsEtlVM.setValidImports(validImports);
                    invalidImports=InvalidImports.from(inValidImportsJson);
                    if (invalidImports!=null)
                        bIllsEtlVM.setInvalidImports(invalidImports);
                    return SuccessMsg("Done", bIllsEtlVM);
                }
            }
            return ErrorMsg("Bill processing failed");
        } catch (Exception e) {
            return ErrorMsg("Failed to process request");
        }
    }


    public Response uploadFile_depricated(long mssuserid, long sponsorId, long schemeId,
                                          MultipartFormDataInput input) throws ParseException {
        activityTrailService.logActivityTrail(mssuserid, "Uploaded a Bill Exception");

        Config config = configRepository.getActiveConfig();

        if (!config.getClient().equals(Clients.ETL)) {
            return extractcontributionBillingValidationImport(config, input);
        }

        Map<String, Object> json = null;

//        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
//        List<InputPart> inputParts = uploadForm.get("file");


        //GET LIST OF MEMBERS UNDER SPONSOR
        FmListDTO fmListDTO = fundMasterClient.getSponsorMemberListing(sponsorId, "SPONSOR", schemeId, 0, 0);
        List<MemberDetailsVM> memberDetailsVMS = new ArrayList<>();
        if (fmListDTO.isSuccess()) {
            List<Object> rows = fmListDTO.getRows();
            for (Object row : rows) {
                MemberDetailsVM memberDetailsVM = MemberDetailsVM.from(row);
                if (memberDetailsVM != null) {
                    memberDetailsVMS.add(memberDetailsVM);
                }
            }
        }

        List<FileModel> fileModels = upload(input);
        if (fileModels == null) {
            return ErrorMsg("Failed to upload file");
        }
        FileModel fileModel = fileModels.get(0);
        String fileName = fileModel.getFilePath();

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                return ErrorMsg("File not uploaded");
            }

            //process File
            FileInputStream inputStream = new FileInputStream(file);
            Workbook memberWorkBook = null;
            String fileExtensionName = fileName.substring(fileName.indexOf("."));
            if (fileExtensionName.equals(".xlsx")) {
                memberWorkBook = new XSSFWorkbook(inputStream);
            } else if (fileExtensionName.equals(".xls")) {
                memberWorkBook = new HSSFWorkbook(inputStream);
            }
            if (memberWorkBook != null) {
                json = convertToCSV(config, memberDetailsVMS, memberWorkBook.getSheetAt(0), sponsorId, schemeId, mssuserid);
            }

            return Response.status(200)
                    .entity(SuccessVM.builder().success(true).data(json).build()).build();
        } catch (Exception e) {
            return ErrorMsg("Failed to process request");
        }
    }


    /**
     * @param mssuserid mssuserid
     * @param input     input
     * @return Response
     * @throws ParseException
     * @Depricated
     */
    public Response uploadFile_D(long mssuserid, MultipartFormDataInput input) throws ParseException {
        activityTrailService.logActivityTrail(mssuserid, "Uploaded a Bill Exception");

        Config config = configRepository.getActiveConfig();
        if (!config.getClient().equals(Clients.ETL)) {
            return extractcontributionBillingValidationImport(config, input);
        }

        String fileName = "";
        Map<String, Object> json = null;

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");

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
//                    json = convertToCSV(config, memberWorkBook.getSheetAt(0), mssuserid);
                }

                memberWorkBook.close();
                inputStream.close();
                file.delete();

            } catch (IOException e) {
                e.printStackTrace();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ErrorVM.builder().success(false)
                        .msg("Failed. Please try Again.").build()
                ).build();
            }
        }

        return Response.status(200)
                .entity(SuccessVM.builder().success(true).data(json).build()).build();
    }


    private Map<String, Object> convertToCSV(Config config, List<MemberDetailsVM> memberDetailsVMS, Sheet sheet, long sponsorId, long schemeId, long mssuserid) throws IOException {
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
        return parseLines(config, memberDetailsVMS, sheetValues, sponsorId, schemeId, mssuserid);
    }


    //create DTOS
    Map<String, Integer> titles = new HashMap<>();

    BigDecimal convertStringToBd(String currency) {
        if (currency.equals("")) {
            return BigDecimal.ZERO;
        }
        currency = currency.replace(",", "");
        if (currency.startsWith("'")) {
            currency = currency.replace("'", "");
        }
        return new BigDecimal(currency);
    }

    BigDecimal convertStringToBd2(String currency) {
        if (currency.equals("")) {
            return null;
        }
        currency = currency.replace(",", "");
        String firstNumberAsString = String.format("%s", currency);
        return BigDecimal.valueOf(Long.parseLong(firstNumberAsString));
    }

    long convertStringToLong(String number) {
        if (number.equals("")) {
            return 0L;
        }
        Float stringFloat = new Float(number);
        return stringFloat.longValue();
    }

    private Map<String, Object> parseLines(Config config, List<MemberDetailsVM> memberDetailsVMS, List<List<String>> lines, long sponsorId, long schemeId, long userId) {
        ObjectMapper objectMapper = new ObjectMapper();
        String monthYear = String.valueOf(lines.get(0).get(1));
//        String month = monthYear.substring(0, 3);
//        String year = monthYear.substring(4);
        validBills.clear();
        //USE RECURSION METHOD

        log.error("Lines Sent: " +StringUtil.toJsonString(lines.remove(0).remove(1)));
        processListLinesRecursion(config, memberDetailsVMS, lines, sponsorId, schemeId, 0);



        Map<String, Object> jsonMap = new HashMap<>();
        validBillImports.put("totalCount", validBills.size());
        validBillImports.put("rows", validBills);
        invalidBillImports.put("totalCount", billsException.size());
        invalidBillImports.put("rows", billsException);

        jsonMap.put("success", true);

        jsonMap.put("validImports", validBillImports);

        jsonMap.put("invalidImports", invalidBillImports);

        log.error("JSON MAP Is>> " + jsonMap);
        return jsonMap;

    }

    private void processListLinesRecursion(Config config, List<MemberDetailsVM> memberDetailsVMS, List<List<String>> lines, long sponsorId, long schemeId, int i) {
        if (i == lines.size()) {
            return;
        }
        BigDecimal eeValidated, erValidated, avcValidated, avcerValidated, lifecover, penaltyValidated, adminFeeValdiated, brokerageValidated, groupLifeValdiated, augmentaryValidated, salaryValidated, adminfeeWoTaxValidated, vatOnAdminFeeValidated;
        if (i == 1) {
            //add all titles to map
            for (int j = 0; j < lines.get(i).size(); j++) {
                titles.put(String.valueOf(lines.get(i).get(j)), j);
            }

            processListLinesRecursion(config, memberDetailsVMS, lines, sponsorId, schemeId, i + 1);
            return;
        }
        ContributionBillingDTO contributionBillingDTO = new ContributionBillingDTO();
        List<String> rowValues = lines.get(i);
        log.error("Titles are Here:" + titles.toString());
//        if (config.getClient().equals(Clients.OTHERS)) {
//            try {
//                contributionBillingDTO.setId(convertStringToLong(rowValues.get(titles.get("Counter")) == null ? "" : rowValues.get(titles.get("Counter"))));
//                contributionBillingDTO.setMemberNo(convertStringToLong(rowValues.get(titles.get("Member No.")) == null ? "" : rowValues.get(titles.get("Member No."))));
//                if (contributionBillingDTO.getMemberNo() == 0) {
//                    contributionBillingDTO.setException("Member Number Missing");
//                }
//                MemberDTO memberDTO = fundMasterClient.checkMemberIfExists("MEMBER_NO", String.valueOf(contributionBillingDTO.getMemberNo()), "MEMBER");
//                if (!memberDTO.isSuccess()) {
//                    contributionBillingDTO.setException("Member No does not exist.");
//                }
//                contributionBillingDTO.setTotalContribution(convertStringToBd(rowValues.get(titles.get("Total Contribution")) == null ? "" : rowValues.get(titles.get("Total Contribution"))));
//                log.info("THIS IS TOTAL CON:>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> :" + contributionBillingDTO.getTotalContribution());
//                contributionBillingDTO.setMemberName(rowValues.get(titles.get("Full Name")) == null ? "" : rowValues.get(titles.get("Full Name")));
//                if (contributionBillingDTO.getMemberName() == null || contributionBillingDTO.getMemberName() == "") {
//                    contributionBillingDTO.setException("Member Name Missing");
//
//                }
//                contributionBillingDTO.setSalary(convertStringToBd(rowValues.get(titles.get("Salary")) == null ? "" : rowValues.get(titles.get("Salary"))));
//                if (contributionBillingDTO.getSalary().equals(BigDecimal.ZERO)) {
//                    contributionBillingDTO.setException("Salary is required");
//
//                }
//                contributionBillingDTO.setEe(convertStringToBd(rowValues.get(titles.get("Employee")) == null ? "" : rowValues.get(titles.get("Employee"))));
//                contributionBillingDTO.setEr(convertStringToBd(rowValues.get(titles.get("Employer")) == null ? "" : rowValues.get(titles.get("Employer"))));
//                contributionBillingDTO.setAvc(convertStringToBd(rowValues.get(titles.get("Employee Voluntary (AVC)")) == null ? "" : rowValues.get(titles.get("Employee Voluntary (AVC)"))));
//                contributionBillingDTO.setAvcer(convertStringToBd(rowValues.get(titles.get("Employer Voluntary (AVCER)")) == null ? "" : rowValues.get(titles.get("Employer Voluntary (AVCER)"))));
//                contributionBillingDTO.setPenaltyPayment(convertStringToBd(rowValues.get(titles.get("Penalty Payment")) == null ? "" : rowValues.get(titles.get("Penalty Payment"))));
//                contributionBillingDTO.setAdminFees(convertStringToBd(rowValues.get(titles.get("Administration Fee")) == null ? "" : rowValues.get(titles.get("Administration Fee"))));
//                contributionBillingDTO.setBrokerageFee(convertStringToBd(rowValues.get(titles.get("Brokerage Fee")) == null ? "" : rowValues.get(titles.get("Brokerage Fee"))));
//                contributionBillingDTO.setGroupLife(convertStringToBd(rowValues.get(titles.get("Group Life Assurance")) == null ? "" : rowValues.get(titles.get("Group Life Assurance"))));
//                contributionBillingDTO.setAugmentary(convertStringToBd(rowValues.get(titles.get("Augmentary Contribution")) == null ? "" : rowValues.get(titles.get("Augmentary Contribution"))));
//                contributionBillingDTO.setAdminFeesWithoutTax(convertStringToBd(rowValues.get(titles.get("Admin Fees Without VAT")) == null ? "" : rowValues.get(titles.get("Admin Fees Without VAT"))));
//                contributionBillingDTO.setTaxOnAdminFees(convertStringToBd(rowValues.get(titles.get("VAT On Admin Fees")) == null ? "" : rowValues.get(titles.get("VAT On Admin Fees"))));
//                contributionBillingDTO.setEe(convertStringToBd(rowValues.get(titles.get("Employee")) == null ? "" : rowValues.get(titles.get("Employee"))));
//                contributionBillingDTO.setEr(convertStringToBd(rowValues.get(titles.get("Employer")) == null ? "" : rowValues.get(titles.get("Employer"))));
//                //  contributionBillingDTO.setCounter(convertStringToInt(rowValues.get(titles.get("Counter")) == null ? "" : rowValues.get(titles.get("Counter"))));
//
//
//                eeValidated = contributionBillingDTO.getEe();
//                erValidated = contributionBillingDTO.getEr();
//                avcValidated = contributionBillingDTO.getAvc();
//                avcerValidated = contributionBillingDTO.getAvcer();
//                brokerageValidated = contributionBillingDTO.getBrokerageFee();
//                adminFeeValdiated = contributionBillingDTO.getAdminFees();
//                augmentaryValidated = contributionBillingDTO.getAugmentary();
//                penaltyValidated = contributionBillingDTO.getPenaltyPayment();
//                groupLifeValdiated = contributionBillingDTO.getGroupLife();
//                salaryValidated = contributionBillingDTO.getSalary();
//                adminfeeWoTaxValidated = contributionBillingDTO.getAdminFeesWithoutTax();
//                vatOnAdminFeeValidated = contributionBillingDTO.getTaxOnAdminFees();
//
//                contributionBillingDTO.setEeValidated(eeValidated);
//                contributionBillingDTO.setErValidated(erValidated);
//                contributionBillingDTO.setAvcValidated(avcValidated);
//                contributionBillingDTO.setAvcerValidated(avcerValidated);
//                contributionBillingDTO.setBrokerageFeeValidated(brokerageValidated);
//                contributionBillingDTO.setAdminFeesValidated(adminFeeValdiated);
//                contributionBillingDTO.setAugmentaryValidated(augmentaryValidated);
//                contributionBillingDTO.setPenaltyPaymentValidated(penaltyValidated);
//                contributionBillingDTO.setGroupLifeValidated(groupLifeValdiated);
//                contributionBillingDTO.setSalaryValidated(salaryValidated);
//                contributionBillingDTO.setAdminFeesWithoutTaxValidated(adminfeeWoTaxValidated);
//                contributionBillingDTO.setTaxOnAdminFeesValidated(vatOnAdminFeeValidated);
//
//
//                if (contributionBillingDTO.getException() != null) {
//                    billsException.add(contributionBillingDTO);
//
//                } else {
//                    validBills.add(contributionBillingDTO);
//                }
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }
//        } else {
        try {
            contributionBillingDTO.setSchemeId(schemeId);
            contributionBillingDTO.setSponsorId(sponsorId);
            contributionBillingDTO.setId(convertStringToLong(rowValues.get(titles.get("#")) == null ? "" : rowValues.get(titles.get("#"))));
            contributionBillingDTO.setMemberIdEtl(rowValues.get(titles.get("MEMBER ID")) == null ? "" : rowValues.get(titles.get("MEMBER ID")));
            if (contributionBillingDTO.getMemberIdEtl() == null) {
                contributionBillingDTO.setException("Member Number Missing");
            }

            MemberDetailsVM detailsVM = null;
            if (!memberDetailsVMS.isEmpty()) {

                boolean isFound = false;
                for (MemberDetailsVM memberDetailsVM : memberDetailsVMS) {
                    if (memberDetailsVM.getMemberNo().equalsIgnoreCase(contributionBillingDTO.getMemberIdEtl())) {
                        isFound = true;
                        detailsVM = memberDetailsVM;
                        break;
                    }
                }
                if (!isFound) {
                    contributionBillingDTO.setException("Member No Invalid. The Member Doesnt Exist.");
                }

            }


            contributionBillingDTO.setTotalContribution(convertStringToBd(rowValues.get(titles.get("TOTAL")) == null ? "" : rowValues.get(titles.get("TOTAL"))));
            log.info("THIS IS TOTAL CON:>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> :" + contributionBillingDTO.getTotalContribution());
            contributionBillingDTO.setMemberName(rowValues.get(titles.get("NAME OF CONTRIBUTOR")) == null ? "" : rowValues.get(titles.get("NAME OF CONTRIBUTOR")));

            if (contributionBillingDTO.getMemberName() == null || contributionBillingDTO.getMemberName().equals("")) {
                contributionBillingDTO.setException("Member Name Missing");

            }
            contributionBillingDTO.setSalary(convertStringToBd(rowValues.get(titles.get("MONTHLY BASIC")) == null ? "" : rowValues.get(titles.get("MONTHLY BASIC"))));
            if (contributionBillingDTO.getSalary().equals(BigDecimal.ZERO)) {
                contributionBillingDTO.setException("Salary is required");

            }
            contributionBillingDTO.setEe(convertStringToBd(rowValues.get(titles.get("EE")) == null ? "" : rowValues.get(titles.get("EE"))));
            contributionBillingDTO.setEr(convertStringToBd(rowValues.get(titles.get("ER")) == null ? "" : rowValues.get(titles.get("ER"))));
            contributionBillingDTO.setAvc(convertStringToBd(rowValues.get(titles.get("ACV")) == null ? "" : rowValues.get(titles.get("ACV"))));
            contributionBillingDTO.setPenaltyPayment(convertStringToBd(rowValues.get(titles.get("OUTSTANDING PENALTY")) == null ? "" : rowValues.get(titles.get("OUTSTANDING PENALTY"))));

            contributionBillingDTO.setDob(rowValues.get(titles.get("D.O.B")) == null ? "" : rowValues.get(titles.get("D.O.B")));

            contributionBillingDTO.setSsnitNumber(rowValues.get(titles.get("SSNIT NUMBER")) == null ? "" : rowValues.get(titles.get("SSNIT NUMBER")));
            if (detailsVM != null) {
                if (!detailsVM.getNationalPenNo().equalsIgnoreCase(contributionBillingDTO.getSsnitNumber())) {
                    contributionBillingDTO.setException("Ssnit Number is wrong. Please check and validate.");
                }
            }


            eeValidated = contributionBillingDTO.getEe();
            erValidated = contributionBillingDTO.getEr();
            avcValidated = contributionBillingDTO.getAvc();
            salaryValidated = contributionBillingDTO.getSalary();


            contributionBillingDTO.setEeValidated(eeValidated);
            contributionBillingDTO.setErValidated(erValidated);
            contributionBillingDTO.setAvcValidated(avcValidated);
            contributionBillingDTO.setSalaryValidated(salaryValidated);


            if (contributionBillingDTO.getException() != null) {
                billsException.add(contributionBillingDTO);

            } else {
                validBills.add(contributionBillingDTO);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
//        }
        processListLinesRecursion(config, memberDetailsVMS, lines, sponsorId, schemeId, i + 1);
    }

    public String uploadDirectory() throws Exception {
        String uploadDir = System.getProperty("user.home") + File.separator + "XI_Fundmaster_scheme_docs";

        File file = new File(uploadDir);
        //Creating the directory
        boolean bool1 = file.mkdir();
        boolean bool = file.mkdirs();
        if (bool) {
            file.setWritable(true);
            file.setReadable(true);
            log.info("Directory created successfully");
        } else {
            log.warn("Sorry could not create specified directory");
        }

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdir();
            directory.setWritable(true);
            directory.setReadable(true);
        }
        return uploadDir;


    }

    @Override
    public Response saveFile(long mssuserid, SaveBillVm saveBillVm, long schemeId, long sponsorId) {
        Config config = configRepository.getActiveConfig();
        try {
            long batch = saveBillVm.getBatch(); //batch
            String json = saveBillVm.getJson(); //json

            ContributionBillingBatchDto billingBatchDto = new ContributionBillingBatchDto();
            billingBatchDto.setId(batch);

            JSONArray jsonArray = new JSONArray(json);
//            List<ContributionBillingDTO> contributionBillingDTOS = new ArrayList<>();
            List<Object> contributionBillingDTOS = new ArrayList<>();

            if (config.getClient().getName().equalsIgnoreCase("ETL")) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ContributionBillingDTO billingDTO =
                            new ObjectMapper().readValue(jsonObject.toString(), ContributionBillingDTO.class);
                    if (billingDTO != null) {
                        billingDTO.setBatch(billingBatchDto);
                        billingDTO.setSchemeId(schemeId);
                        billingDTO.setSponsorId(sponsorId);
                        String xi_scheme_doc_folder = uploadDirectory();
                        billingDTO.setFinalPath(xi_scheme_doc_folder);
                        contributionBillingDTOS.add(billingDTO);
                    }
                }
            } else {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    XeContributionDTO billingDTO =
                            new ObjectMapper().readValue(jsonObject.toString(), XeContributionDTO.class);
                    if (billingDTO != null) {
                        billingDTO.setBatch(billingBatchDto);
                        contributionBillingDTOS.add(billingDTO);
                    }
                }
            }

            String finalJson = new ObjectMapper().writeValueAsString(contributionBillingDTOS);
            log.error("FINAL JSON :" + finalJson);
            MessageModelDTO success = fundMasterClient.saveBill(finalJson);

            if (success.isSuccess()) {
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true).msg(success.getMessage()).build())
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Failed to validate bill"
                ).build())
                .build();
    }


    public String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                return name[1].trim().replaceAll("\"", "");
            }
        }
        return "unknown";
    }

    //save to somewhere
    public File writeFile(byte[] content, String filename) throws IOException {

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

    /**
     * XE CONTRIBUTION BILLING VALIDATION
     */

    public Response extractcontributionBillingValidationImport(Config config, MultipartFormDataInput input) {
        try {
            List<FileModel> fileModels = upload(input);
            if (fileModels == null) {
                return ErrorMsg("Failed to get file model");
            }
            String filename = fileModels.get(0).getFilePath();
            List<Vector<String>> res = ExcellExtractor.extract(filename, 1);
            int count = 1;


            int size = res.size();

            log.error(res.toString());

            for (Vector<String> v : res) {
                ContributionBillingDTO contributionBillingDTO = new ContributionBillingDTO();
                count++;

//                [1,  --counter0
//                3.5286034E7,1
//                673,2
//                Abraham B Smitham, --fullname3
//                Smitham, --firstname4
//                Abraham, --secondName5
//                B, --othernames6
//                425829.0, --staffNo7
//                43833.3,--salary8
//                2191.665,--ee9
//                4383.33,--er10
//                0,--avc11
//                0,--sever12
//                0,--life13
//                0,--penalty14
//                0,--admin fee15
//                0,--vat admin fee16
//                0,--broker17
//                0,--gla18
//                0,--augmentary19
//                ,--status20
//                ,--comment21
//                6574.995,--total contribution22
//                , ]

                String counter = v.get(0);
                String idNo = v.get(1);
                String memberNoStr = v.get(2);
                String fullName = v.get(3);
                String surname = v.get(4);
                String firstName = v.get(5);
                String otherNames = v.get(6);
                String staffNo = v.get(7);
                String salaryStr = v.get(8);
                String eeStr = v.get(9);
                String erStr = v.get(10);
                String avcStr = v.get(11);
                String severeStr = v.get(12);
                String lifeCoverPremiumStr = v.get(13);
                String penaltyStr = v.get(13);
                String adminFeeWithoutVatStr = v.get(15);
                String taxOnAdminFeesStr = v.get(16);
                String brokerageFeeStr = v.get(17);
                String groupLifeStr = v.get(18);
                String augStr = v.get(19);
                String statusStr = v.get(20);
                String commentStr = v.get(21);
                String totalContributionStr = v.get(22);

                try {
//                    contributionBillingDTO.setId();
                    contributionBillingDTO.setMemberIdEtl(memberNoStr);
                    contributionBillingDTO.setMemberNo(convertStringToLong(memberNoStr));
                    if (contributionBillingDTO.getMemberNo() == 0) {
                        contributionBillingDTO.setException("Member Number Missing");
                    }
//                    MemberDTO memberDTO = fundMasterClient.checkMemberIfExists("MEMBER_NO", String.valueOf(contributionBillingDTO.getMemberNo()), "MEMBER");
//                    if (!memberDTO.isSuccess()) {
//                        contributionBillingDTO.setException("Member No does not exist.");
//                    }
//                    contributionBillingDTO.setTotalContribution();
                    log.info("THIS IS TOTAL CON:>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> :" + contributionBillingDTO.getTotalContribution());
                    contributionBillingDTO.setMemberName(fullName);
                    if (contributionBillingDTO.getMemberName() == null || contributionBillingDTO.getMemberName().equals("")) {
                        contributionBillingDTO.setException("Member Name Missing");
                    }
                    contributionBillingDTO.setSalary(convertStringToBd(salaryStr));
                    if (contributionBillingDTO.getSalary().equals(BigDecimal.ZERO)) {
                        contributionBillingDTO.setException("Salary is required");

                    }
                    contributionBillingDTO.setEe(convertStringToBd(eeStr));
                    contributionBillingDTO.setEr(convertStringToBd(erStr));
                    contributionBillingDTO.setAvc(convertStringToBd(avcStr));
//                   contributionBillingDTO.setAvcer(convertStringToBd());
                    contributionBillingDTO.setPenaltyPayment(convertStringToBd(penaltyStr));
                    contributionBillingDTO.setAdminFees(convertStringToBd(adminFeeWithoutVatStr));
                    contributionBillingDTO.setBrokerageFee(convertStringToBd(brokerageFeeStr));
                    contributionBillingDTO.setGroupLife(convertStringToBd(groupLifeStr));
                    contributionBillingDTO.setAugmentary(convertStringToBd(augStr));
                    contributionBillingDTO.setAdminFeesWithoutTax(convertStringToBd(adminFeeWithoutVatStr));
                    contributionBillingDTO.setTaxOnAdminFees(convertStringToBd(taxOnAdminFeesStr));
                    contributionBillingDTO.setEe(convertStringToBd(eeStr));
                    contributionBillingDTO.setEr(convertStringToBd(erStr));
                    contributionBillingDTO.setId(convertStringToLong(counter));
                    contributionBillingDTO.setGroupLife(convertStringToBd(groupLifeStr));
                    contributionBillingDTO.setStatus(statusStr);
                    contributionBillingDTO.setComments(commentStr);
                    contributionBillingDTO.setSeveranceDue(convertStringToBd(severeStr));
                    contributionBillingDTO.setTotalContribution(convertStringToBd(totalContributionStr));
                    contributionBillingDTO.setStaffNo(convertStringToLong(staffNo));

                    BigDecimal eeValidated, erValidated, avcValidated, avcerValidated, lifecover, penaltyValidated,
                            adminFeeValdiated, brokerageValidated, groupLifeValdiated, augmentaryValidated, salaryValidated, adminfeeWoTaxValidated, vatOnAdminFeeValidated;
                    eeValidated = contributionBillingDTO.getEe();
                    erValidated = contributionBillingDTO.getEr();
                    avcValidated = contributionBillingDTO.getAvc();
                    avcerValidated = contributionBillingDTO.getAvcer();
                    brokerageValidated = contributionBillingDTO.getBrokerageFee();
                    adminFeeValdiated = contributionBillingDTO.getAdminFees();
                    augmentaryValidated = contributionBillingDTO.getAugmentary();
                    penaltyValidated = contributionBillingDTO.getPenaltyPayment();
                    groupLifeValdiated = contributionBillingDTO.getGroupLife();
                    salaryValidated = contributionBillingDTO.getSalary();
                    adminfeeWoTaxValidated = contributionBillingDTO.getAdminFeesWithoutTax();
                    vatOnAdminFeeValidated = contributionBillingDTO.getTaxOnAdminFees();


                    contributionBillingDTO.setEeValidated(eeValidated);
                    contributionBillingDTO.setErValidated(erValidated);
                    contributionBillingDTO.setAvcValidated(avcValidated);
                    contributionBillingDTO.setAvcerValidated(avcerValidated);
                    contributionBillingDTO.setBrokerageFeeValidated(brokerageValidated);
                    contributionBillingDTO.setAdminFeesValidated(adminFeeValdiated);
                    contributionBillingDTO.setAugmentaryValidated(augmentaryValidated);
                    contributionBillingDTO.setPenaltyPaymentValidated(penaltyValidated);
                    contributionBillingDTO.setGroupLifeValidated(groupLifeValdiated);
                    contributionBillingDTO.setSalaryValidated(salaryValidated);
                    contributionBillingDTO.setAdminFeesWithoutTaxValidated(adminfeeWoTaxValidated);
                    contributionBillingDTO.setTaxOnAdminFeesValidated(vatOnAdminFeeValidated);


                    if (contributionBillingDTO.getException() != null) {
                        billsException.add(contributionBillingDTO);

                    } else {
                        validBills.add(contributionBillingDTO);
                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }

                log.info(count + "/" + size + " done ");

            }

            Map<String, Object> jsonMap = new HashMap<>();
            validBillImports.put("totalCount", validBills.size());
            validBillImports.put("rows", validBills);
            invalidBillImports.put("totalCount", billsException.size());
            invalidBillImports.put("rows", billsException);

            jsonMap.put("success", true);
            jsonMap.put("validImports", validBillImports);
            jsonMap.put("invalidImports", invalidBillImports);
            return SuccessMsg("Done", jsonMap);

        } catch (Exception e) {
            e.printStackTrace();
            return ErrorMsg(e.getMessage());
        }

    }
}
