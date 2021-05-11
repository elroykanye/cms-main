package com.tridiots.cms.controllers.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tridiots.cms.message.Message;
import com.tridiots.cms.utils.modeldao.ContestantUtils;

/**
 * Servlet implementation class ApplyContest
 */
///@WebServlet("/ApplyContest")
public class ApplyContest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplyContest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("uid"));
		
		Message addConSuccess = ContestantUtils.addContestant(userId);
		if(addConSuccess.getFlag()) {
			response.sendRedirect(request.getContextPath() + "/user/submit.html");
		} else {
			request.setAttribute("errorMessage", addConSuccess.getMessage());
			request.getRequestDispatcher( "/user/apply-contest.jsp").forward(request, response);
		}
	}

}
