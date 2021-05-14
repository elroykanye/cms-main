package com.tridiots.cms.utils.modeldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
// import java.sql.Statement;

import com.tridiots.cms.message.Message;
import com.tridiots.cms.models.Submission;
import com.tridiots.cms.utils.dbutils.ConnectionUtils;
import com.tridiots.cms.utils.dbutils.QueryUtils;

public class SubmissionUtils {
	private static PreparedStatement prepStatement = null;
	// private static Statement statement = null;
	private static Connection conn = null;
	
	
	public static Message addSubmission(Submission submission) {
		String sql = "INSERT INTO "
				+ "wtaxy_submission(submission_poem_english, submission_poem_kom, contestant_id, submission_final_grade)"
				+ " VALUES (?,?,?,?)";
		Message message = new Message("Submission unsuccessful", false);
		try {
			conn = ConnectionUtils.openConnection();
			prepStatement = conn.prepareStatement(sql);
			prepStatement.setNString(1,submission.getSubmissionPoemEn());
			prepStatement.setString(2, submission.getSubmissionPoemKom());
			prepStatement.setInt(3, submission.getContestantId());
			prepStatement.setDouble(4, submission.getSubmissionFinalGrade());
			
			prepStatement.execute();
			message.setMessage("Submission successful"); message.setFlag(true);
		} catch(SQLException exception) {
			exception.printStackTrace();
		} finally {
			QueryUtils.closeQueryObject(prepStatement);
			ConnectionUtils.closeConnection(conn);
		}
		return message;
		
	}

}
