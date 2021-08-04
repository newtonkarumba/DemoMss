package com.systech.mss.urlhandler;

import com.systech.mss.domain.User;
import com.systech.mss.service.UserService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * @author Aviator
 */
@WebServlet(name = "UrlHandler", urlPatterns = {"/handle"})
public class UrlHandler extends HttpServlet {


    @Inject
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String action = req.getParameter("action");
        if (action == null) {
            out.write("<h3>Please try again</h3>");
            out.close();
            return;
        }

        switch (action) {
            case "activate":
                activateAccount(req, resp,out);
                break;
            default:
        }

        out.close();
    }

    private void activateAccount(HttpServletRequest req, HttpServletResponse resp,PrintWriter out) throws IOException {
        String key=req.getParameter("key");
        if (key==null){
            out.write("<h3>Please try again</h3>");
            out.close();
            return;
        }
        Optional<User> user=userService.activateRegistration(key);
        if (user.isPresent()){
            out.write("<h3>Account activated</h3>");
            resp.sendRedirect("./");
        }else{
            out.write("<h3>Please try again</h3>");
        }
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

}
