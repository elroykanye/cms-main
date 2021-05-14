package com.tridiots.cms.controllers.contestant;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tridiots.cms.models.Submission;

/**
 * Servlet implementation class Submission
 */
@WebServlet("/Submission")
public class SubmissionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmissionController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uploadsDir = "";
		Submission submission = new Submission();
		
		try {
			int conId = Integer.parseInt(request.getParameter("cid"));
			
			submission.setSubmissionDate((java.sql.Date) new Date());
			submission.setSubmissionFileEn(uploadsDir);
			submission.setSubmissionFileKom(uploadsDir);
			submission.setContestantId(conId);
			submission.setSubmissionFinalGrade(0);
		} catch(NumberFormatException exception) {
			exception.printStackTrace();
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
