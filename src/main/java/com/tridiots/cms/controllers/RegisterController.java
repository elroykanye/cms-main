package com.tridiots.cms.controllers;

import com.tridiots.cms.message.Message;
import com.tridiots.cms.models.User;
import com.tridiots.cms.utils.modeldao.UserUtils;
import java.io.IOException;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;

public class RegisterController extends Controller {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getMethod().toLowerCase(Locale.ROOT).equals("post")) {
            try {
                doPost(request, response);
            } catch (IOException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(request.getMethod().toLowerCase(Locale.ROOT).equals("get")) {
            doGet(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputFirstName = request.getParameter("first_name");
        String inputLastName = request.getParameter("last_name");
        String inputEmail = request.getParameter("email");
        String inputUsername = request.getParameter("username");
        String inputPassword = request.getParameter("password");
        String inputRPassword = request.getParameter("password_repeat");
        String inputDob = request.getParameter("date_of_birth");
        String inputGender = request.getParameter("gender");
        
        if(!inputPassword.equals(inputRPassword)) {
            request.setAttribute("errorMessage", "Password mismatch!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            User newUser = new User();
            newUser.setUserFirstName(inputFirstName);
            newUser.setUserLastName(inputLastName);
            newUser.setUserEmail(inputEmail);
            newUser.setUserName(inputUsername);
            newUser.setUserGender(inputGender);
            newUser.setUserDob(Date.valueOf(inputDob));
            newUser.setUserPass(inputPassword);

            Message message = UserUtils.registerUser(newUser);
            if(message.getFlag()) response.sendRedirect("index.jsp");
            else {
                request.setAttribute("errorMessage", message.getMessage());
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        }
    }

}
