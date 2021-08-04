package com.systech.mss.controller.impl;

import com.systech.mss.controller.FormsController;
import com.systech.mss.domain.Forms;
import com.systech.mss.domain.User;
import com.systech.mss.fileupload.FileModel;
import com.systech.mss.repository.FormsRepository;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FormsContollerImpl extends BaseController implements FormsController {

    @Inject
    FormsRepository formsRepository;

    @Override
    public Response getAll(long mssUserId) {
        logActivityTrail(mssUserId, "Get download forms");
        List<Forms> forms = formsRepository.findAll();
        return SuccessMsg("Done", forms != null ? forms : new ArrayList<>());
    }

    @Override
    public Response create(long mssUserId, MultipartFormDataInput input) {
        User user = userRepository.find(mssUserId);
        if (user == null)
            return ErrorMsg("Kindly login in");

        logActivityTrail(mssUserId, "Upload download form");

        try {
            Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

            String title = (uploadForm.get("title").get(0).getBodyAsString());
            String description = (uploadForm.get("description").get(0).getBodyAsString());

            List<FileModel> fileModels = upload(input);
            if (fileModels == null || fileModels.isEmpty()) {
                return ErrorMsg("Unable to upload file");
            }
            FileModel fileModel = fileModels.get(0);

            Forms forms = new Forms();
            forms.setTitle(title);
            forms.setDescription(description);
            forms.setFileName(fileModel.getFileName());
            forms.setOriginalFileName(fileModel.getOriginalFileName());

            forms = formsRepository.create(forms);
            if (forms != null)
                return SuccessMsg("Done", null);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return ErrorMsg("System error, please try again");
    }

    @Override
    public Response remove(long mssUserId, long id) {
        logActivityTrail(mssUserId, "Deleted download document");
        Forms forms = formsRepository.find(id);
        if (forms != null) {
            formsRepository.remove(forms);
            return SuccessMsg("Done", null);
        }
        return ErrorMsg("Failed to remove record");
    }
}
