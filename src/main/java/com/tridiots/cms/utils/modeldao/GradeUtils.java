package com.tridiots.cms.utils.modeldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tridiots.cms.message.Message;
import com.tridiots.cms.models.Grade;
import com.tridiots.cms.utils.dbutils.ConnectionUtils;
import com.tridiots.cms.utils.dbutils.QueryUtils;

public class GradeUtils {

	private static PreparedStatement prepStatement = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private static Connection conn = null;
	
	public static Message addGrade(Grade grade) {
		Message message = new Message("Adding score failed", false);
		
		try {
			Message foundGrade = findGrade(grade);
			if(foundGrade.getFlag()) {
				String query = "UPDATE wtaxy_grade SET grade_value=? WHERE submission_id=? AND judge_id=";
				conn = ConnectionUtils.openConnection();
				prepStatement = conn.prepareStatement(query);
				prepStatement.setDouble(1, grade.getSubmissionGrade());
				prepStatement.setInt(2, grade.getSubmissionId());
				prepStatement.setInt(3, grade.getJudgeId());
				
				prepStatement.executeQuery();
				message.setFlag(true); message.setMessage("Adding score success");
			} else {
				String query = "INSERT INTO wtaxy_grade(submission_id, judge_id, grade_value) VALUES (?,?,?) ";
				conn = ConnectionUtils.openConnection();
				prepStatement = conn.prepareStatement(query);
				prepStatement.setInt(1, grade.getSubmissionId());
				prepStatement.setInt(2, grade.getJudgeId());
				prepStatement.setDouble(3, grade.getSubmissionGrade());
				
				prepStatement.executeQuery();
				message.setFlag(true); message.setMessage("Adding score success");
			}
			
		} catch (SQLException exception) {
			exception.printStackTrace();
		} finally {
			QueryUtils.closeQueryObject(prepStatement);
			ConnectionUtils.closeConnection(conn);
		} return message;
	}
	
	public static Message findGrade(Grade grade) {
		String query = "SELECT submission_id, judge_id, grade_value FROM wtaxy_grade WHERE judge_id=? AND submission_id=?";
		Message message = new Message("Grade not found", false);
		try {
			conn = ConnectionUtils.openConnection();
			prepStatement = conn.prepareStatement(query);
			prepStatement.setInt(1, grade.getJudgeId());
			prepStatement.setInt(2, grade.getSubmissionId());
			resultSet = prepStatement.executeQuery();
			if(resultSet.next()) {
				message.setMessage("Grade already set by this judge. Modify only."); message.setFlag(true);
			} else {
				message.setFlag(false); message.setMessage("Grade not set by this judge");
			}
		} catch(SQLException exception) {
			exception.printStackTrace();
		} finally {
			QueryUtils.closeQueryObjects(prepStatement, resultSet);
			ConnectionUtils.closeConnection(conn);
		} return message;
	}
}
