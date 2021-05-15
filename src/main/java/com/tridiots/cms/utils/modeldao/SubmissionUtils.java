package com.tridiots.cms.utils.modeldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.tridiots.cms.message.Message;
import com.tridiots.cms.models.Submission;
import com.tridiots.cms.utils.dbutils.ConnectionUtils;
import com.tridiots.cms.utils.dbutils.QueryUtils;

public class SubmissionUtils {
	private static final int JUDGE_NUMBER = 3;
	private static PreparedStatement prepStatement = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private static Connection conn = null;
	
	
	public static Message addSubmission(Submission submission) {
		String sql = "INSERT INTO "
				+ "wtaxy_submission(submission_poem_english, submission_poem_kom, contestant_id, submission_final_grade, submission_poem_title)"
				+ " VALUES (?,?,?,?)";
		Message message = new Message("Submission unsuccessful", false);
		try {
			conn = ConnectionUtils.openConnection();
			prepStatement = conn.prepareStatement(sql);
			
			prepStatement.setString(1,submission.getSubmissionPoemEn());
			prepStatement.setString(2, submission.getSubmissionPoemKom());
			prepStatement.setInt(3, submission.getContestantId());
			prepStatement.setDouble(4, submission.getSubmissionFinalGrade());
			prepStatement.setString(5, submission.getSubmissionPoemTitle());
			
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

	public static Submission getSubmission(int submissionId) {
		Submission submission = new Submission();
		
		String query = "SELECT * FROM wtaxy_submission WHERE submission_id=?";
		
		try {
			conn = ConnectionUtils.openConnection();
			prepStatement = conn.prepareStatement(query);
			prepStatement.setInt(1, submissionId);
			resultSet = prepStatement.executeQuery();
			if(resultSet.next()) {
				submission.setSubmissionId(resultSet.getInt("submission_id"));
				submission.setSubmissionPoemTitle(resultSet.getString("submission_poem_title"));
				submission.setSubmissionPoemEn(resultSet.getString("submission_poem_english"));
				submission.setSubmissionPoemKom(resultSet.getString("submission_poem_kom"));
				submission.setSubmissionDate(resultSet.getDate("submission_date"));
				submission.setSubmissionFinalGrade(resultSet.getDouble("submission_final_grade"));
				submission.setContestantId(resultSet.getInt("contestant_id"));
			} else {
				submission = null;
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		} finally {
			QueryUtils.closeQueryObjects(prepStatement, resultSet);
			ConnectionUtils.closeConnection(conn);
		}
		
		return submission;
	}
	public static ArrayList<Submission> getSubmissions () {
		ArrayList<Submission> submissions = new ArrayList<>();
		
		String query = "SELECT * FROM wtaxy_submission";
		try {
			conn = ConnectionUtils.openConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				Submission submission = new Submission();
				submission.setSubmissionId(resultSet.getInt("submission_id"));
				submission.setSubmissionPoemTitle(resultSet.getString("submission_poem_title"));
				submission.setSubmissionPoemEn(resultSet.getString("submission_poem_english"));
				submission.setSubmissionPoemKom(resultSet.getString("submission_poem_kom"));
				submission.setSubmissionDate(resultSet.getDate("submission_date"));
				submission.setSubmissionFinalGrade(resultSet.getDouble("submission_final_grade"));
				submission.setContestantId(resultSet.getInt("contestant_id"));
				
				// updates the submission final grade while getting
				updateSubmissionFinalGrade(submission.getSubmissionId());
				
				submissions.add(submission);
			}
		} catch(SQLException exception) {
			exception.printStackTrace();
		} finally {
			QueryUtils.closeQueryObjects(statement, resultSet);
			ConnectionUtils.closeConnection(conn);
		}
		return submissions;
	}
	
	public static void updateAllSubmissions() {
		ArrayList<Integer> allSubIds = new ArrayList<>();
		String sql = "SELECT submission_id FROM wtaxy_submission";
		try {
			conn = ConnectionUtils.openConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			while(resultSet.next()) allSubIds.add(resultSet.getInt("submission_id"));
		} catch (SQLException exception) {
			exception.printStackTrace();
		} finally {
			QueryUtils.closeQueryObject(prepStatement, statement);
			ConnectionUtils.closeConnection(conn);
		} 
		if(allSubIds.size() > 0) {
			for(Integer subId : allSubIds) {
				updateSubmissionFinalGrade(subId);
			}
		}
	}
	
	public static void updateSubmissionFinalGrade(int subId) {
		String sql = "UPDATE wtaxy_submission SET submission_final_grade=? WHERE submission_id=";
		double updatedGrade = calcSubmissionFinalGrade(subId);
		try {
			conn = ConnectionUtils.openConnection();
			prepStatement = conn.prepareStatement(sql);
			prepStatement.setDouble(1, updatedGrade);
			prepStatement.setInt(2, subId);
			prepStatement.execute();
		} catch (SQLException exception) {
			exception.printStackTrace();
		} finally {
			QueryUtils.closeQueryObject(prepStatement);
			ConnectionUtils.closeConnection(conn);
		}
	}
	
	private static double calcSubmissionFinalGrade(int subid) {
		String query = "SELECT grade_value FROM wtaxy_grade WHERE submission_id=?";
		ArrayList<Integer> gradeValues = new ArrayList<>();
		try {
			conn = ConnectionUtils.openConnection();
			prepStatement = conn.prepareStatement(query);
			prepStatement.setInt(1, subid);
			resultSet = prepStatement.executeQuery();
			while(resultSet.next()) {
				gradeValues.add(resultSet.getInt("grade_value"));
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		} finally {
			QueryUtils.closeQueryObjects(prepStatement, resultSet);
			ConnectionUtils.closeConnection(conn);
		}
		return calcAverage(gradeValues);
	}
	
	private static double calcAverage(ArrayList<Integer> values) {
		if(values.size() == 0) return 0;
		int sum = 0;
		for(Integer val : values) {
			sum += val;
		}
		return sum/JUDGE_NUMBER;
	}
}
