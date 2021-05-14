package com.tridiots.cms.controllers.contestant;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tridiots.cms.kanye.IO;
import com.tridiots.cms.message.Message;
import com.tridiots.cms.models.Submission;
import com.tridiots.cms.utils.modeldao.SubmissionUtils;

/**
 * Servlet implementation class Submission
 */
@WebServlet("/Submission")
public class SubmitController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitController() {
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
		Submission submission = new Submission();
		
		try {
			int conId = Integer.parseInt(request.getParameter("conid"));
			
			submission.setSubmissionPoemEn(request.getParameter("engPoem"));
			submission.setSubmissionPoemKom(request.getParameter("komPoem"));
			submission.setContestantId(conId);
			submission.setSubmissionFinalGrade(0);
			
			Message submissionAdded = SubmissionUtils.addSubmission(submission);
			if(submissionAdded.getFlag()) {
				IO.println(submissionAdded.getMessage());
			} else {
				IO.println(submissionAdded.getMessage());
				request.setAttribute("errorMessage", submissionAdded.getMessage());
				request.getRequestDispatcher("/user/submit.jsp").forward(request, response);
				
			}
			
		} catch(NumberFormatException exception) {
			exception.printStackTrace();
		}
	
		
	}

}
