/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.systech.mss.fileupload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.controller.impl.BaseController;
import com.systech.mss.domain.Documents;
import com.systech.mss.domain.User;
import com.systech.mss.repository.DocumentRepository;
import com.systech.mss.repository.UserRepository;
import com.systech.mss.seurity.DateUtils;
import com.systech.mss.vm.DocumentsVM;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Aviator
 */
@WebServlet(name = "FileHandler", urlPatterns = {"/FileHandler"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024 * 50 * 5)
public class FileHandler extends HttpServlet {

    @Inject
    private DocumentRepository documentRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private org.slf4j.Logger logger;

    //    private String path;
    private static final long serialVersionUID = 1L;
    private static final String uploadFolderName = BaseController.uploadFolderName;
    private static volatile String uploadFolder = null;

    @Override
    public void init() throws ServletException {
        getUploadFolder();
    }

    /**
     * DO NOT DELETE ANYTHING, METHOD USED TO HANDLE FILE DOWNLOADS
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Map<String, String[]> map = request.getParameterMap();
        /*
          o = originalFileName
          file= fileName
         */
        if (map.containsKey("o") && map.containsKey("file")){
            download(response,out,request.getParameter("o"),request.getParameter("file"));
            return;
        }
        String id = request.getParameter("file");
        if (id != null) {
            try {
                downLoadFile(response, id);
            } catch (Exception ignored) {
            }
            return;
        }
        out.println("Not found");
        out.close();
    }

    private void downLoadFile(HttpServletResponse response, String id) throws IOException {

        PrintWriter out = response.getWriter();
        try {
            Documents documents = documentRepository.find(Long.parseLong(id));
            if (documents == null) {
                out.println("File not found");
                return;
            }
//            String filePath = documents.getFilePath();
            download(response, out, documents.getOriginalFileName(), documents.getFileName());

        } catch (Exception e) {
            out.println("Not found");
        }
        out.close();
    }

    String getFullFilePath(String fileName) {
        return getUploadFolder() + File.separator + fileName;
    }

    void download(HttpServletResponse response, PrintWriter out, String originalFileName, String fileName) {
        try {
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + originalFileName + "\"");
            FileInputStream fileInputStream = new FileInputStream(getFullFilePath(fileName));
            int i;
            while ((i = fileInputStream.read()) != -1) {
                out.write(i);
            }
            fileInputStream.close();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            uploadSingleFile(request, response);
//            doUploadMultiple(request,response);
        } catch (Exception ignored) {
        }
    }

    private User checkUser(String userID) {
        if (userID == null) {
            return null;
        }
        return userRepository.find(Long.parseLong(userID));
    }

    private void doUploadMultiple(HttpServletRequest request, HttpServletResponse response) {

        String path = getUploadFolder() + File.separator;
        response.setContentType("application/json");
        Map<String, Object> map = new HashMap<>();

        String userID = request.getParameter("userId");
        String comments = request.getParameter("comments");
        logger.info(comments);
        User user = checkUser(userID);
        if (user == null) {
            map.put("success", false);
            map.put("message", "Kindly Login");
            printResponse(response, map);
            return;
        }

        List<FileModel> fileModels = new ArrayList<>();
        try {
            for (Part part : request.getParts()) {
                String originalName = part.getSubmittedFileName(),
                        file_name = "File_" + (DateUtils.getMillitime()) + (originalName);
                part.write(path + (file_name));
//                fileName.add(readFileName(part));
                FileModel fileModel = new FileModel();
                fileModel.setFileName(file_name);
                fileModel.setOriginalFileName(originalName);
                fileModel.setFilePath(path + (file_name));
                //set comment if exists
                if (comments != null) {
                    fileModel.setComments(comments);
                }
                fileModels.add(fileModel);
            }
        } catch (IOException | ServletException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        saveFiles(user, fileModels);

        map.put("success", true);
        map.put("files", fileModels);
        response.setStatus(HttpServletResponse.SC_OK);
        printResponse(response, map);
    }

    protected void uploadSingleFile(HttpServletRequest request,
                                    HttpServletResponse response) throws ServletException, IOException {

        String path = getUploadFolder() + File.separator;
        response.setContentType("application/json");
        Map<String, Object> map = new HashMap<>();

        String userID = request.getParameter("userId");
        String comments = request.getParameter("comments");
        logger.info(comments);
        User user = checkUser(userID);
        if (user == null) {
            map.put("success", false);
            map.put("message", "Kindly Login");
            printResponse(response, map);
            return;
        }

        List<FileModel> fileModels = new ArrayList<>();
        final Part filePart = request.getPart("file");
        final String originalName = getFileName(filePart);
        final String fileName = "File_" + (DateUtils.getMillitime()) + (originalName);
        //getUploadFolder() + File.separator + fileName;
        filePart.write(path + (fileName));

        FileModel fileModel = new FileModel();
        fileModel.setFileName(fileName);
        fileModel.setOriginalFileName(originalName);
        fileModel.setFilePath(path + (fileName));
        if (comments != null) {
            fileModel.setComments(comments);
        }
        fileModels.add(fileModel);

        saveFiles(user, fileModels);

        map.put("success", true);
        map.put("files", fileModels);
        response.setStatus(HttpServletResponse.SC_OK);
        printResponse(response, map);
    }

    private void saveFiles(User user, List<FileModel> fileModels) {
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
            documentRepository.create(documents);
        }
    }

    private void printResponse(HttpServletResponse response, Object o) {
        try {
            response.getWriter().println(new ObjectMapper().writeValueAsString(o));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFileName(Part part) {
        String strContent = part.getHeader("content-disposition");
        String[] tokens = strContent.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }

    private String getUploadFolder() {
        if (uploadFolder == null) {
//            String contextRealPath = getServletContext().getRealPath("/");
//            uploadFolder = contextRealPath + File.separator + uploadFolderName;
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
        return uploadFolder;
    }

    private void writeToFileUsingFileOutputStream(InputStream filecontent,
                                                  String filePath) throws IOException {
        OutputStream out = null;
        try {
            out = new FileOutputStream(new File(filePath));
            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } finally {
            if (out != null) {

                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
        }
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        String[] sections = partHeader.split(";");
        for (String content : sections) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
