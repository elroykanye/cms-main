package com.tridiots.cms.utils.modeldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.tridiots.cms.kanye.DoubleObject;
import com.tridiots.cms.kanye.IO;
import com.tridiots.cms.message.Message;
import com.tridiots.cms.models.Contestant;
import com.tridiots.cms.models.User;
import com.tridiots.cms.utils.dbutils.ConnectionUtils;
import com.tridiots.cms.utils.dbutils.QueryUtils;

public class ContestantUtils {
	
	private static Contestant contestant = null;
	
	
	private static Connection conn;
	private static PreparedStatement prepStatement;
	private static ResultSet resultSet;
	private static Message message = null;
	
	public static Message addContestant(int userId) {
		int uid = userId;
		String avatarDir = "dir";
		
		message = new Message("Contestant registration unsuccessful", false);
		Message conExists = findContestant(userId);
		String insertSql = "INSERT INTO wtaxy_contestant(user_id,contestant_image_dir) VALUES (?,?)";
		String updateSQL = "UPDATE wtaxy_user SET user_role=333 WHERE user_id=?";
		if(!conExists.getFlag()) {
			try {
				conn = ConnectionUtils.openConnection();
				prepStatement = conn.prepareStatement(insertSql);
				prepStatement.setInt(1, uid);
				prepStatement.setString(2, avatarDir);
				prepStatement.executeUpdate();
				
				prepStatement = conn.prepareStatement(updateSQL);
				prepStatement.setInt(1, uid);
				prepStatement.executeUpdate();
				
				message.setMessage("Contestant registration successful"); message.setFlag(true);
			} catch (SQLException exception) {
				// TODO add logger
				exception.printStackTrace();
			} finally {
				
				QueryUtils.closeQueryObject(prepStatement);
			} ConnectionUtils.closeConnection(conn);
		} else {
			message.setMessage("Contestant account already registered"); message.setFlag(false);
		}
		return message;
	}
	
	public static Message addContestant (Contestant con) {
		contestant = con;
		conn = ConnectionUtils.openConnection();
		 message = new Message("Contestant registration unsuccessful", false);
		
		String sql = "INSERT INTO wtaxy_contestant(user_id,contestant_image_dir) VALUES (?,?);";
		try {
			prepStatement = conn.prepareStatement(sql);
			prepStatement.setInt(1, contestant.getUserId());
			prepStatement.setString(2, contestant.getContestantImageDir());
			prepStatement.executeUpdate();
			message.setMessage("Contestant registration successful"); message.setFlag(true);
		} catch (SQLException exception) {
			// TODO add logger
			exception.printStackTrace();
		} finally {
			
			QueryUtils.closeQueryObject(prepStatement);
		} ConnectionUtils.closeConnection(conn);
		
		return message;
		
	}
	
	public static Message removeContestant (Contestant con) {
		message = new Message("Remove contestant: Operation unsuccessful", false);
		int conId = con.getContestantId();
		conn = ConnectionUtils.openConnection();
		String sql = "DELETE FROM wtaxy_contestant WHERE contestant_id=?;";
		
		try {
			prepStatement = conn.prepareStatement(sql);
			prepStatement.setInt(1, conId);
			message.setFlag(true); message.setMessage("Remove contestant: Operation successful");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(conn);
			QueryUtils.closeQueryObject(prepStatement);
		} return message;
		
	}
	
	public static Contestant getContestant (int uid) {
		Message foundContestant = findContestant(uid);
		Contestant contestant = new Contestant();
		if(foundContestant.getFlag()) {
			String sql = "SELECT wu.user_id, wu.user_name, wu.user_email, wu.user_pass, wu.user_first_name, wu.user_last_name, wu.user_gender, wu.user_dob, wu.user_verified, wu.user_role, wu.user_joined_date, wc.contestant_id, wc.contestant_image_dir\r\n"
					+ "FROM cms.wtaxy_user wu \r\n"
					+ "	INNER JOIN cms.wtaxy_contestant wc ON ( wu.user_id = wc.user_id  ) "
					+ "WHERE wu.user_id=?";
			try {
				prepStatement = conn.prepareStatement(sql);
				prepStatement.setInt(1, uid);
				resultSet = prepStatement.executeQuery();
				while(resultSet.next()) {
					contestant.setUserName(resultSet.getString("wu.user_name"));
					contestant.setContestantId(resultSet.getInt("wc.contestant_id"));
					contestant.setContestantImageDir(resultSet.getString("wc.contestant_image_dir"));
					contestant.setUserName(resultSet.getString("wu.user_name"));
					contestant.setUserEmail(resultSet.getString("wu.user_email"));
					contestant.setUserFirstName(resultSet.getString("wu.user_first_name"));
					contestant.setUserLastName(resultSet.getString("wu.user_last_name"));
					contestant.setUserGender(resultSet.getString("wu.user_gender"));
					contestant.setUserDob(resultSet.getDate("wu.user_dob"));
					contestant.setUserJoinDate(resultSet.getDate("wu.user_joined_date"));
					IO.println(contestant.getUserName() + " " + contestant.getUserFirstName() + " " + contestant.getUserLastName());
				} 
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		} 
		
		return contestant;
	}
	
	public static ArrayList<Contestant> getContestants () {
		ArrayList<Contestant> contestants = new ArrayList<Contestant>();
		conn = ConnectionUtils.openConnection();
		String sql = "SELECT wu.user_id, wu.user_name, wu.user_email, wu.user_pass, wu.user_first_name, wu.user_last_name, wu.user_gender, wu.user_dob, wu.user_verified, wu.user_role, wu.user_joined_date, wc.contestant_image_dir\r\n"
				+ "FROM cms.wtaxy_user wu \r\n"
				+ "	INNER JOIN cms.wtaxy_contestant wc ON ( wu.user_id = wc.user_id  ) ";
		try {
			prepStatement = conn.prepareStatement(sql);
			resultSet = prepStatement.executeQuery();
			while(resultSet.next()) {
				Contestant contestant = new Contestant();
				contestant.setUserName(resultSet.getString("wu.user_name"));
				
				contestant.setContestantImageDir(resultSet.getString("wc.contestant_image_dir"));
				contestant.setUserName(resultSet.getString("wu.user_name"));
				contestant.setUserEmail(resultSet.getString("wu.user_email"));
				contestant.setUserFirstName(resultSet.getString("wu.user_first_name"));
				contestant.setUserLastName(resultSet.getString("wu.user_last_name"));
				contestant.setUserGender(resultSet.getString("wu.user_gender"));
				contestant.setUserDob(resultSet.getDate("wu.user_dob"));
				contestant.setUserJoinDate(resultSet.getDate("wu.user_joined_date"));
				contestants.add(contestant);
				
				IO.println(contestant.getUserName() + " " + contestant.getUserFirstName() + " " + contestant.getUserLastName());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(conn);
			QueryUtils.closeQueryObjects(prepStatement, resultSet);
		}
		return contestants;
		
	}
	
	public static Message findContestant(int userId) {
		String sql  = "SELECT user_id FROM wtaxy_contestant WHERE user_id=?;";
		conn = ConnectionUtils.openConnection();
		
		try {
			prepStatement = conn.prepareStatement(sql);
			prepStatement.setInt(1, userId);
			resultSet = prepStatement.executeQuery();
			if(resultSet.next()) {
				return new Message("Contestant exists", true);
			}
			
		} catch (SQLException exception) {
			exception.printStackTrace();
		} finally {
			
			QueryUtils.closeQueryObjects(prepStatement, resultSet);
		} ConnectionUtils.closeConnection(conn);
		return new Message("Contestant does not exist", false);
	}

}
