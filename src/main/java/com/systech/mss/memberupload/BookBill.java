
package com.systech.mss.memberupload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.systech.mss.config.Helper;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.Documents;
import com.systech.mss.domain.User;
import com.systech.mss.fileupload.FileHandler;
import com.systech.mss.fileupload.FileModel;
import com.systech.mss.service.FundMasterClient;
import com.systech.mss.service.dto.MemberUploadDTO;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet(name = "bookBill", urlPatterns = {"/bookBill"})
@MultipartConfig()
public class BookBill extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getParameter("id");

        downLoadFile(resp,path);
    }

    private void downLoadFile(HttpServletResponse response, String id) throws IOException {

        PrintWriter out = response.getWriter();
        try {
//            Documents documents = documentRepository.find(Long.parseLong(id));
//            if (documents == null) {
//                out.println("File not found");
//                return;
//            }
            String[] strings= id.split("/");
            String fileName = strings[strings.length - 1];
            fileName = fileName.substring(0,fileName.length()-4);
            String filePath =id; //"/home/opc/servers/johnteServer/wildfly/XI_Fundmaster_scheme_docs/SYSTECH DEFINED CONTRIBUTION SCHEME COST CENTRE_SDCSCC_OCT-2021_BILL.xls";
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    +fileName +".xls" + "\"");

            FileInputStream fileInputStream = new FileInputStream(filePath);

            int i;
            while ((i = fileInputStream.read()) != -1) {
                out.write(i);
            }
            fileInputStream.close();
        } catch (Exception e) {
            out.println("Not found");
        }
        out.close();
    }
}
