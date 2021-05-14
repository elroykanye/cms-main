package com.tridiots.cms.controllers.judge;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tridiots.cms.models.Submission;
import com.tridiots.cms.models.User;
import com.tridiots.cms.utils.modeldao.ContestantUtils;
import com.tridiots.cms.utils.modeldao.SubmissionUtils;
import com.tridiots.cms.utils.modeldao.UserUtils;

/**
 * Servlet implementation class SubmissionsController
 */
@WebServlet("/SubmissionsController")
public class SubmissionsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmissionsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionSource = request.getParameter("action");
		if(actionSource.equals("view")) {
			int submissionId = Integer.parseInt(request.getParameter("subid"));
			request.setAttribute("submission", SubmissionUtils.getSubmission(submissionId));
			request.getRequestDispatcher("/user/judge/submission.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
