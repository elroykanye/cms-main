package com.tridiots.cms.utils.modeldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tridiots.cms.models.Judge;
import com.tridiots.cms.models.User;
import com.tridiots.cms.utils.dbutils.ConnectionUtils;
import com.tridiots.cms.utils.dbutils.QueryUtils;

public class JudgeUtils {
	private static PreparedStatement prepStatement = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private static Connection conn = null;
	
	public static Judge getJudge(int uid) {
		String query = "SELECT judge_id, judge_level, user_id FROM `wtaxy_judge` WHERE user_id=?";
		Judge judge = new Judge();
		try {
			conn = ConnectionUtils.openConnection();
			prepStatement = conn.prepareStatement(query);
			prepStatement.setInt(1, uid);
			resultSet = prepStatement.executeQuery();
			if(resultSet.next()) {
				judge.setJudgeId(resultSet.getInt("judge_id"));
				judge.setJudgeLevel(resultSet.getInt("judge_level"));
				judge.setUserId(uid);
			}
		} catch(SQLException exception) {
			exception.printStackTrace();
		} finally {
			QueryUtils.closeQueryObjects(prepStatement, resultSet);
			ConnectionUtils.closeConnection(conn);
		}
		
		return judge;
	}
	public static Judge getJudge(User user) {
		Judge theJudge = getJudge(user.getUserId());
		
		Judge judge = (Judge) user;
		judge.setJudgeId(theJudge.getJudgeId());
		judge.setJudgeLevel(theJudge.getJudgeLevel());
		return judge; 
	}
	

}
