package com.tridiots.cms.controllers.judge;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tridiots.cms.kanye.IO;
import com.tridiots.cms.models.User;
import com.tridiots.cms.utils.modeldao.ContestantUtils;
import com.tridiots.cms.utils.modeldao.UserUtils;

/**
 * Servlet implementation class ConsController
 */
public class ConsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        Integer gottenConId = null;
        if(request.getParameter("conid") != null) {
        	gottenConId = Integer.parseInt(request.getParameter("conid"));
        	doViewContestant(request, response, gottenConId);
        } else {
        	response.sendRedirect(request.getContextPath() + "/user/judge/contestants.jsp");
        }
        
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	private void doViewContestant(HttpServletRequest request, HttpServletResponse response, int conid) throws ServletException, IOException {
		int uid = ContestantUtils.getUserIdFromConId(conid);
		IO.println(conid + " param tap");
        User user = UserUtils.getUser(uid);
        IO.println(user.getUserFirstName() + " Contestant");
        request.getSession().setAttribute("currentUser", user);
        response.sendRedirect(request.getContextPath() + "/user/judge/contestant.jsp");
    }
}
