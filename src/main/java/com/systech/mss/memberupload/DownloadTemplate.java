package com.systech.mss.memberupload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet(name = "download", urlPatterns = {"/download" +
        ""})
@MultipartConfig()
public class DownloadTemplate extends HttpServlet {
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
            String filePath =id; //"/home/opc/servers/johnteServer/wildfly/XI_Fundmaster_scheme_docs/SYSTECH DEFINED CONTRIBUTION SCHEME COST CENTRE_SDCSCC_OCT-2021_BILL.xls";
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + new Date().getTime() +  ".xls" + "\"");

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
