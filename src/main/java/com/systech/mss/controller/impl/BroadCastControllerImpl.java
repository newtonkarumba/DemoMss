package com.systech.mss.controller.impl;

import com.systech.mss.callback.ProcessCallBack;
import com.systech.mss.controller.BroadCastController;
import com.systech.mss.domain.*;
import com.systech.mss.fileupload.FileModel;
import com.systech.mss.repository.BroadCastMessagesInboxRepository;
import com.systech.mss.repository.BroadCastMessagesOutboxRepository;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.JSONArray;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;

public class BroadCastControllerImpl extends BaseController implements BroadCastController {
    @Inject
    BroadCastMessagesInboxRepository inboxRepository;
    @Inject
    BroadCastMessagesOutboxRepository outboxRepository;


    @Override
    public Response sendBroadCast(long mssUserId, MultipartFormDataInput input) {
        User user = userRepository.find(mssUserId);
        if (user == null)
            return ErrorMsg("Kindly login in");

        try {
            Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

            String subject = (uploadForm.get("subject").get(0).getBodyAsString());
            String profile = (uploadForm.get("profile").get(0).getBodyAsString());
            String users = (uploadForm.get("users").get(0).getBodyAsString());
            String message = (uploadForm.get("message").get(0).getBodyAsString());

            List<FileModel> fileModels = upload(input);
            if (fileModels == null) {
                fileModels = new ArrayList<>();
            }
            JSONArray documents = new JSONArray();
            for (FileModel fileModel : fileModels) {
                documents.put(fileModel.getJson());
            }

            Profile toProfile = profileService.getProfileByName(profile);
            BroadCastMessages broadCastMessages = BroadCastMessages.from(
                    subject, message, documents.toString(), user, toProfile
            );
//            log.error(users);
            broadCastMessages.setReceiversOutbox(
                    users.equals("null") || users.isEmpty() ? "ALL" : users
            );
//            log.error(broadCastMessages.toString());
            outboxRepository.create(
                    BroadCastMessagesOutbox.from(broadCastMessages)
            );
            List<User> list = new ArrayList<>();
            if (users.equals("null") || users.isEmpty()) {
                list = userRepository.findByProfile(toProfile);
                if (list == null)
                    list = new ArrayList<>();
            } else {
                String[] strings = users.split(",");
                for (String string : strings) {
                    Optional<User> user1 = userRepository.findOneByLogin(string.trim());
                    if (user1.isPresent())
                        list.add(user1.get());
                }
            }

            broadCastMessages.setReceiversOutbox("");
            List<User> finalList = list;
            doInBackground(new ProcessCallBack() {
                @Override
                public void start(Object o) {
                    for (User user1 : finalList) {
                        broadCastMessages.setToId(user1.getId());
                        broadCastMessages.setToName(user1.getUserDetails().getName());
                        broadCastMessages.setLogin(user1.getLogin());
                        broadCastMessages.setToProfile(user1.getProfile());
                        inboxRepository.create(
                                BroadCastMessagesInbox.from(broadCastMessages)
                        );
                    }
                }
            });
            return SuccessMsg("Done", null);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return ErrorMsg("System error, please try again");
    }

    @Override
    public Response getInboxMessagesForUser(long mssUserId) {
        User user = userRepository.find(mssUserId);
        if (user == null)
            return ErrorMsg("Session timeout");
        List<BroadCastMessagesInbox> list = inboxRepository.findByUser(user);
        if (list != null) {
            for (BroadCastMessagesInbox broadCastMessagesInbox : list) {
                BroadCastMessages broadCastMessages = broadCastMessagesInbox.getMessages();
                broadCastMessages.setFromProfileName(broadCastMessages.getFromProfile().getName());
                broadCastMessages.setToProfile(null);
                broadCastMessages.setFromProfile(null);
            }
            return SuccessMsg("Done", list);
        }

        return ErrorMsg("Please try again");
    }

    @Override
    public Response getOutboxMessagesForUser(long mssUserId) {
        User user = userRepository.find(mssUserId);
        if (user == null)
            return ErrorMsg("Session timeout");
        List<BroadCastMessagesOutbox> list = outboxRepository.findByUser(user);
        if (list != null) {
            for (BroadCastMessagesOutbox broadCastMessagesOutbox : list) {
                BroadCastMessages broadCastMessages = broadCastMessagesOutbox.getMessages();
                broadCastMessages.setToProfileName(broadCastMessages.getToProfile().getName());
                broadCastMessages.setToProfile(null);
                broadCastMessages.setFromProfile(null);
            }
            return SuccessMsg("Done", list);
        }

        return ErrorMsg("Please try again");
    }

    @Override
    public Response setMessageRead(long id) {
        BroadCastMessagesInbox broadCastMessagesInbox = inboxRepository.find(id);
        if (broadCastMessagesInbox == null)
            return ErrorMsg("Message deleted");
        BroadCastMessages broadCastMessages = broadCastMessagesInbox.getMessages();
        broadCastMessages.setRead(true);
        broadCastMessages.setUpdatedAt(Calendar.getInstance(Locale.getDefault()).getTime());
        broadCastMessagesInbox.setMessages(broadCastMessages);
        inboxRepository.edit(broadCastMessagesInbox);
        return SuccessMsg("Done", null);
    }

    @Override
    public Response getUnreadMessage(long mssUserId) {
        User user = userRepository.find(mssUserId);
        if (user == null)
            return ErrorMsg("Session timeout");
        List<BroadCastMessagesInbox> list = inboxRepository.findUnreadByUser(user);
        if (list != null) {
            for (BroadCastMessagesInbox broadCastMessagesInbox : list) {
                BroadCastMessages broadCastMessages = broadCastMessagesInbox.getMessages();
                broadCastMessages.setFromProfileName(broadCastMessages.getFromProfile().getName());
                broadCastMessages.setToProfile(null);
                broadCastMessages.setFromProfile(null);
            }
            return SuccessMsg("Done", list);
        }
        return ErrorMsg("Please try again");
    }

}
