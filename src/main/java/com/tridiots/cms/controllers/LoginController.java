package com.tridiots.cms.controllers;

import com.tridiots.cms.message.Message;
import com.tridiots.cms.models.Contestant;
import com.tridiots.cms.models.Judge;
import com.tridiots.cms.models.User;
import com.tridiots.cms.utils.modeldao.ContestantUtils;
import com.tridiots.cms.utils.modeldao.JudgeUtils;
import com.tridiots.cms.utils.modeldao.UserUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * author Tridiots
 * This controller gets parameters from the login view and processes the inputs using
 * data access objects.
 *
 * The service() method forwards control to the corresponding method handler
 */
public class LoginController extends Controller{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        if(request.getMethod().toLowerCase(Locale.ROOT).equals("post")) {
            doPost(request, response);
        }
        if(request.getMethod().toLowerCase(Locale.ROOT).equals("get")) {
            doGet(request, response);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String inputLogin = request.getParameter("login");
        String inputPass = request.getParameter("password");

        // TODO add validation objects and methods here for DAO and Utils


        User user = new User();
        user.setUserPass(inputPass);
        user.setUserLogin(inputLogin);
        Message loggedIn = UserUtils.loginUser(user);
        System.out.println(loggedIn.getMessage());
        if(loggedIn.getFlag())  {
            try {
            	user = UserUtils.getUser(inputLogin);
            	switch(user.getUserRole()) {
            	case 111:
            		request.getSession().setAttribute("loggedInAdmin", user);
            		break;
            	case 222:
            		Judge loggedInJudge = JudgeUtils.getJudge(user.getUserId());
            		request.getSession().setAttribute("loggedInJudge", loggedInJudge);
            		break;
            	case 333:
            		Contestant loggedInContestant = ContestantUtils.getContestant(user.getUserId());
            		request.getSession().setAttribute("loggedInContestant", loggedInContestant);
            		break;
            	case 444:
            		break;
            	default:
            		request.setAttribute("errorMessage", "Invalid user!!!");
            		try {
						request.getRequestDispatcher(request.getContextPath() + "/login.jsp").forward(request, response);
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} break;
            	}
            	// added correct assign user session attribs to session object
            	request.getSession().setAttribute("loggedInUser", user);

            	response.sendRedirect(request.getContextPath() + "/user/profile.jsp");
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                request.setAttribute("errorMessage", loggedIn.getMessage());
                request.getRequestDispatcher( "/login.jsp").forward(request, response);
                
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }



    }
}
