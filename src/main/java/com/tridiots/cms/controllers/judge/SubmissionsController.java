package com.tridiots.cms.controllers.judge;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tridiots.cms.message.Message;
import com.tridiots.cms.models.Contestant;
import com.tridiots.cms.models.Grade;
import com.tridiots.cms.models.Submission;
import com.tridiots.cms.models.User;
import com.tridiots.cms.utils.modeldao.ContestantUtils;
import com.tridiots.cms.utils.modeldao.GradeUtils;
import com.tridiots.cms.utils.modeldao.SubmissionUtils;
import com.tridiots.cms.utils.modeldao.UserUtils;

/**
 * Servlet implementation class SubmissionsController
 */
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
		Message message = null;
		if(actionSource.equals("view")) {
			int submissionId = Integer.parseInt(request.getParameter("subid"));
			int conid = Integer.parseInt(request.getParameter("conid"));
			
			User user = UserUtils.getUser(ContestantUtils.getUserIdFromConId(conid));
			Contestant contestant = new Contestant();
			contestant.setContestantId(conid);
			contestant.setUserFirstName(user.getUserFirstName());
			contestant.setUserLastName(user.getUserLastName());
			contestant.setUserName(user.getUserName());
			
			request.setAttribute("submission", SubmissionUtils.getSubmission(submissionId));
			request.setAttribute("contestant", contestant);
			request.getRequestDispatcher("/user/judge/submission.jsp").forward(request, response);
		} else if (actionSource.equals("submitScore") || actionSource.equals("updateScore")) {
			int judgeId = Integer.parseInt(request.getParameter("jid"));
			int subId = Integer.parseInt(request.getParameter("subid"));
			int conId = Integer.parseInt(request.getParameter("conid"));
			double score = Double.parseDouble(request.getParameter("score"));
			Grade grade = new Grade();
			grade.setJudgeId(judgeId);
			grade.setSubmissionId(subId);
			grade.setSubmissionGrade(score);
			message = GradeUtils.addGrade(grade);
			
			int uid = ContestantUtils.getUserIdFromConId(conId);
			Contestant contestant = ContestantUtils.getContestant(uid);
			Submission submission = SubmissionUtils.getSubmission(subId);
			request.setAttribute("submission", submission);
			request.setAttribute("contestant", contestant);
			request.setAttribute("message", message.getMessage());
			
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
