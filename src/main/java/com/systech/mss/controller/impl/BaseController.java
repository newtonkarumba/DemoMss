package com.systech.mss.controller.impl;

import com.systech.mss.callback.ProcessCallBack;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.Config;
import com.systech.mss.domain.Documents;
import com.systech.mss.domain.User;
import com.systech.mss.fileupload.FileModel;
import com.systech.mss.repository.*;
import com.systech.mss.service.*;
import com.systech.mss.seurity.DateUtils;
import com.systech.mss.util.StringUtil;
import com.systech.mss.vm.DocumentsVM;
import com.systech.mss.vm.ObjectComparisonVM;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.swing.*;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public abstract class BaseController {
    @Inject
    public Logger log;

    @Inject
    public MailService mailService;

    @Inject
    public ActivityTrailRepository trailRepository;

    @Inject
    public FMMemberClient fmMemberClient;

    @Inject
    public FMCREClient fmcreClient;

    @Inject
    public FMCRMClient fmcrmClient;

    @Inject
    public FundMasterClient fundMasterClient;

    @Inject
    public MailConfigRepository mailConfigRepository;

    @Inject
    public ConfigRepository configRepository;

    @Inject
    public SponsorConfigRepository sponsorConfigRepository;

    @Inject
    public DocumentRepository documentRepository;

    @Inject
    public MemberSubmittedDocsRepository memberSubmittedDocsRepository;

    @Inject
    public MemberRepository memberRepository;

    @Inject
    public UserRepository userRepository;

    @Inject
    public UserService userService;

    @Inject
    public ProfileService profileService;

    protected void logActivityTrail(long userId, String msg) {
        if (userId != 0)
            trailRepository.create(trailRepository.getActivityTrail(userId, msg));
    }

    protected Response ErrorMsg(String msg) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        msg
                ).build())
                .build();
    }

    protected Response NotFoundErrorMsg(String msg) {
        return Response.status(Response.Status.NOT_FOUND)
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

    /**
     * SuccessMsg
     *
     * @param token t
     * @param msg   m
     * @param data  d
     * @return Response
     */
    protected Response SuccessMsg(String token, String msg, Object data) {
        return Response.status(Response.Status.OK)
                .entity(SuccessVM.builder().success(true).token(token).msg(msg).data(data).build())
                .build();
    }

    protected String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                return name[1].trim().replaceAll("\"", "");
            }
        }
        return "unknown";
    }

    protected File writeFile(byte[] content, String filename) throws IOException {

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

    public String getUploadFolder() {
        if (uploadFolder == null) {
            JFileChooser jFileChooser = new JFileChooser();
            String path = jFileChooser.getFileSystemView().getDefaultDirectory().toString();
            uploadFolder = path + File.separator + uploadFolderName;
            File dir = new File(uploadFolder);
            if (!dir.exists()) {
                boolean create = dir.mkdir();
                if (create) {
                    System.out.println("Uploads directory created:" + uploadFolder);
                } else {
                    throw new RuntimeException("Directory Cannot Be Created!");
                }
            }
        }
        return uploadFolder + File.separator;
    }

    public List<FileModel> upload(MultipartFormDataInput input) throws ParseException {

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts;
        try {
            inputParts = uploadForm.get("file");
        } catch (Exception e) {
            e.printStackTrace();
            inputParts = uploadForm.get("formFile");
        }
        List<FileModel> fileModels = new ArrayList<>();
        String path = getUploadFolder(); //eg /home/documents/mssuploads/
        for (InputPart inputPart : inputParts) {
            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                final String originalName = getFileName(header);
                //prevent upload invalid file
                if (originalName == null || originalName.isEmpty())
                    continue;

                final String fileName = "File_" + (DateUtils.getTimestamp()) + (originalName);

                InputStream contentStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(contentStream);
                String filePath = path + (fileName);
                writeFile(bytes, filePath);
                FileModel fileModel = new FileModel();
                fileModel.setFileName(fileName);
                fileModel.setOriginalFileName(originalName);
                fileModel.setFilePath(filePath);
                fileModels.add(fileModel);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return fileModels;
    }

    /**
     * WILL UPLOAD SINGLE FILE
     *
     * @param input    I
     * @param fileName F
     * @return List<FileModel>
     * @throws ParseException P
     */
    public List<FileModel> upload(MultipartFormDataInput input, String fileName) throws ParseException {

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts;
        try {
            inputParts = uploadForm.get("file");
        } catch (Exception e) {
            e.printStackTrace();
            inputParts = uploadForm.get("formFile");
        }
        List<FileModel> fileModels = new ArrayList<>();
        String path = getUploadFolder(); //eg /home/documents/mssuploads/
        for (InputPart inputPart : inputParts) {
            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                final String originalName = getFileName(header);
                //prevent upload invalid file
                if (originalName == null || originalName.isEmpty())
                    continue;
                InputStream contentStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(contentStream);
                String filePath = path + (fileName);
                writeFile(bytes, filePath);
                FileModel fileModel = new FileModel();
                fileModel.setFileName(fileName);
                fileModel.setOriginalFileName(originalName);
                fileModel.setFilePath(filePath);
                fileModels.add(fileModel);
                break;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return fileModels;
    }

    public List<Documents> saveFiles(User user, List<FileModel> fileModels) {
        List<Documents> documentsList = new ArrayList<>();
        for (FileModel fileModel :
                fileModels) {
            DocumentsVM documentsVM = new DocumentsVM();
            documentsVM.setUserId(user.getId());
            documentsVM.setFileName(fileModel.getFileName());
            documentsVM.setOriginalFileName(fileModel.getOriginalFileName());
            documentsVM.setForProfileId(user.getProfile().getId());
            documentsVM.setFilePath(fileModel.getFilePath());
            documentsVM.setToUserId(user.getId());
            documentsVM.setComments(fileModel.getComments());

            Documents documents = Documents.getDocumentsInstance(user, documentsVM);
            try {
                Documents documents1 = documentRepository.create(documents);
                if (documents1 != null) {
                    documentsList.add(documents1);
                }
            } catch (Exception ignored) {
            }
        }
        return documentsList;
    }

    public Optional<Config> getFMConfig() {
        return configRepository
                .findAll()
                .stream()
                .findFirst();
    }

    /**
     * @param documents json array
     * @return List<FileModel>
     * @throws Exception e
     */
    public List<FileModel> getFileModelsFromRegMember(String documents) throws Exception {
        if (documents != null) {
            try {
                JSONArray jsonArray = new JSONArray(documents);
                List<FileModel> fileModels = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    org.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject != null) {
                        FileModel fileModel = FileModel.from(jsonObject);
                        if (fileModel != null)
                            fileModels.add(fileModel);
                    }
                }
                return fileModels;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        throw new Exception("Member has no attached document(s)");
    }

    public void doInBackground(ProcessCallBack processCallBack) {
//        long result=0L;
        if (processCallBack != null) {
//            CompletableFuture<Long> completableFuture =
            CompletableFuture.supplyAsync(
                    () -> {
                        processCallBack.start(null);
                        return 1L;
                    }
            );
//            while (!completableFuture.isDone()) {
//                System.out.println("CompletableFuture is not finished yet...");
//            }
//            try {
//                result = completableFuture.get();
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
        }
    }

    public static final String[] country_list = new String[]{"Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Anguilla", "Antigua &amp; Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia &amp; Herzegovina", "Botswana", "Brazil", "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Chad", "Chile", "China", "Colombia", "Congo", "Cook Islands", "Costa Rica", "Cote D Ivoire", "Croatia", "Cruise Ship", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Estonia", "Ethiopia", "Falkland Islands", "Faroe Islands", "Fiji", "Finland", "France", "French Polynesia", "French West Indies", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guam", "Guatemala", "Guernsey", "Guinea", "Guinea Bissau", "Guyana", "Haiti", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Jamaica", "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kuwait", "Kyrgyz Republic", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Mauritania", "Mauritius", "Mexico", "Moldova", "Monaco", "Mongolia", "Montenegro", "Montserrat", "Morocco", "Mozambique", "Namibia", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Norway", "Oman", "Pakistan", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russia", "Rwanda", "Saint Pierre &amp; Miquelon", "Samoa", "San Marino", "Satellite", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "South Africa", "South Korea", "Spain", "Sri Lanka", "St Kitts &amp; Nevis", "St Lucia", "St Vincent", "St. Lucia", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor L'Este", "Togo", "Tonga", "Trinidad &amp; Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks &amp; Caicos", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States of America", "Uruguay", "Uzbekistan", "Venezuela", "Vietnam", "Virgin Islands (US)", "Yemen", "Zambia", "Zimbabwe"};


    public static final String uploadFolderName = "mssuploads";
    private static volatile String uploadFolder = null;
    public String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Documents> setExtraInfo(List<Documents> documents) {
        for (Documents doc :
                documents) {
            doc.setFromUserFullName(userService.getUsersFullNameById(
                    doc.getFromUserId().getId()
            ));
            doc.setFromUserId(doc.getFromUserId().getId());
            doc.setProfileName(profileService.getProfileNameById(doc.getForProfileId()));
            if (doc.getCreatedAt() != null)
                doc.setShortDate(DateUtils.shortDate(doc.getCreatedAt()));
        }
        return documents;
    }

    /**
     * Compare to jsons
     *
     * @param beforeChange old
     * @param afterChange  new
     * @return String
     */
    public List<ObjectComparisonVM> objectComparator(String beforeChange, String afterChange, String... excludes) {
        List<ObjectComparisonVM> vmList = new ArrayList<>();

        List<String> exList = new ArrayList<>();
        if (excludes != null)
            exList = Arrays.asList(excludes);

        try {
            org.json.JSONObject beforeJSON = new org.json.JSONObject(beforeChange);
            Iterator<?> beforeKeys = beforeJSON.keys();
            org.json.JSONObject afterJSON = new org.json.JSONObject(afterChange);
            while (beforeKeys.hasNext()) {
                String beforeKey = (String) beforeKeys.next();
                String formattedBeforeKey = beforeKey.trim().equals("") ? "null" : beforeKey;
                if (exList.contains(formattedBeforeKey))
                    continue;
                if (afterJSON.has(formattedBeforeKey)) {
                    log.warn("Before KEY : {}", formattedBeforeKey);
                    if (!beforeJSON.get(formattedBeforeKey).equals(afterJSON.get(formattedBeforeKey))) {
                        vmList.add(
                                new ObjectComparisonVM(
                                        formattedBeforeKey,
                                        StringUtil.toString(beforeJSON.get(formattedBeforeKey)),
                                        StringUtil.toString(afterJSON.get(formattedBeforeKey))
                                )
                        );
                    }
                }
            }
//            log.info("the compare values gotten are " + vmList);
        } catch (JSONException e) {
            log.error("{}", e.getMessage());
        }
        return vmList;
    }

}
