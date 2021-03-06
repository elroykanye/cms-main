package com.tridiots.cms.utils.modeldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
				String query = "UPDATE wtaxy_grade SET grade_value=? WHERE submission_id=? AND judge_id=?";
				conn = ConnectionUtils.openConnection();
				prepStatement = conn.prepareStatement(query);
				prepStatement.setDouble(1, grade.getSubmissionGrade());
				prepStatement.setInt(2, grade.getSubmissionId());
				prepStatement.setInt(3, grade.getJudgeId());
				
				prepStatement.execute();
				message.setFlag(true); message.setMessage("Update score success");
			} else {
				String query = "INSERT INTO wtaxy_grade(submission_id, judge_id, grade_value) VALUES (?,?,?) ";
				conn = ConnectionUtils.openConnection();
				prepStatement = conn.prepareStatement(query);
				prepStatement.setInt(1, grade.getSubmissionId());
				prepStatement.setInt(2, grade.getJudgeId());
				prepStatement.setDouble(3, grade.getSubmissionGrade());
				
				prepStatement.execute();
				message.setFlag(true); message.setMessage("Adding score success");
			}
			SubmissionUtils.updateSubmissionFinalGrade(grade.getSubmissionId());
			
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
	
	public static Grade getGrade(int jid, int subid) {
		String query = "SELECT grade_value FROM wtaxy_grade WHERE judge_id=? AND submission_id=?";
		Grade grade = new Grade();
		try {
			conn = ConnectionUtils.openConnection();
			prepStatement = conn.prepareStatement(query);
			prepStatement.setInt(1, jid);
			prepStatement.setInt(2, subid);
			resultSet = prepStatement.executeQuery();
			if(resultSet.next()) {
				grade.setJudgeId(jid);
				grade.setSubmissionId(subid);
				grade.setSubmissionGrade(resultSet.getDouble("grade_value"));
			} 
		} catch (SQLException exception) {
			exception.printStackTrace();
		} finally {
			QueryUtils.closeQueryObjects(prepStatement, resultSet);
			ConnectionUtils.closeConnection(conn);
		} return grade;
	}
	
	public static ArrayList<Grade> getGrades(int subid) {
		String query = "SELECT * FROM wtaxy_grade WHERE submission_id=?";
		ArrayList<Grade> grades = new ArrayList<>();
		try {
			conn = ConnectionUtils.openConnection();
			prepStatement = conn.prepareStatement(query);
			prepStatement.setInt(1, subid);
			resultSet = prepStatement.executeQuery();
			while (resultSet.next()) {
				Grade grade = new Grade();
				grade.setJudgeId(resultSet.getInt("judge_id"));
				grade.setSubmissionId(subid);
				grade.setSubmissionGrade(resultSet.getDouble("grade_value"));
				grades.add(grade);
			} 
		} catch (SQLException exception) {
			exception.printStackTrace();
		} finally {
			QueryUtils.closeQueryObjects(prepStatement, resultSet);
			ConnectionUtils.closeConnection(conn);
		} return grades;
	}
}
