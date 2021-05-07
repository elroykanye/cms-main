package com.tridiots.cms.controllers;

import com.tridiots.cms.models.User;
import com.tridiots.cms.utils.modeldao.UserUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersController extends Controller {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getMethod().toLowerCase(Locale.ROOT).equals("post")) doPost(request, response);
        if(request.getMethod().toLowerCase(Locale.ROOT).equals("get")) doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getParameter("uid"));
        Integer gottenUserId = null;
        if(request.getParameter("uid") != null) gottenUserId = Integer.parseInt(request.getParameter("uid"));
        switch (request.getParameter("action").toLowerCase()) {
            case "edit":
                doEditUser(request, response, (int)gottenUserId);
                break;
            case "delete":
                try {
                    doDeleteUser(request, response, (int) gottenUserId);
                } catch (SQLException ex) {
                    Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            case "delete all":
                doDeleteAll(request, response);
                break;
            default:
                break;
        }
     
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    
    private void doEditUser(HttpServletRequest request, HttpServletResponse response, int uid) throws ServletException, IOException {
        User user = UserUtils.getUser(uid);
        request.getSession().setAttribute("currentUser", user);
        response.sendRedirect("user/admin/user.jsp");
    }
    
    private void doDeleteUser(HttpServletRequest request, HttpServletResponse response, int uid) throws ServletException, IOException, SQLException {
        User user = new User();
        user.setUserId(uid);
        UserUtils.deleteUser(user);
        response.sendRedirect("user/admin/users.jsp");
    }
    private void doDeleteAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserUtils.deleteAllUsers();
        response.sendRedirect("user/admin/users.jsp");
    }

    
}
