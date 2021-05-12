package com.tridiots.cms.utils.modeldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.tridiots.cms.kanye.DoubleObject;
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
		conn = ConnectionUtils.openConnection();
		message = new Message("Contestant registration unsuccessful", false);
		Message conExists = findContestant(userId);
		String sql = "INSERT INTO wtaxy_contestant(user_id,contestant_image_dir) VALUES (?,?)";
		
		if(!conExists.getFlag()) {
			try {
				prepStatement = conn.prepareStatement(sql);
				prepStatement.setInt(1, uid);
				prepStatement.setString(2, avatarDir);
				prepStatement.executeUpdate();
				message.setMessage("Contestant registration successful"); message.setFlag(true);
			} catch (SQLException exception) {
				// TODO add logger
				exception.printStackTrace();
			} finally {
				ConnectionUtils.closeConnection(conn);
				QueryUtils.closeQueryObject(prepStatement);
			}
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
			ConnectionUtils.closeConnection(conn);
			QueryUtils.closeQueryObject(prepStatement);
		}
		
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
	
	public static Contestant getContestant (int conId) {
		return null;
		
	}
	
	public static ArrayList<DoubleObject<Contestant,User>> getContestants () {
		
		
		ArrayList<DoubleObject<Contestant,User>> contestants = new ArrayList<DoubleObject<Contestant, User>>();
		
		
		
		conn = ConnectionUtils.openConnection();
		String sql = "SELECT wu.user_name, wu.user_email, wu.user_first_name, wu.user_last_name, wu.user_gender, wu.user_dob, wc.contestant_image_dir\r\n"
				+ "FROM cms.wtaxy_user wu \r\n"
				+ "	INNER JOIN cms.wtaxy_contestant wc ON ( wu.user_id = wc.user_id  )  ";
		try {
			prepStatement = conn.prepareStatement(sql);
			resultSet = prepStatement.executeQuery();
			Contestant contestant = new Contestant();
			User user = new User();
			while(resultSet.next()) {
				contestant.setContestantImageDir(resultSet.getString("wc.contestant_image_dir"));
				user.setUserName(resultSet.getString("wu.user_name"));
				user.setUserEmail(resultSet.getString("wu.user_email"));
				user.setUserFirstName(resultSet.getString("wu.user_first_name"));
				user.setUserLastName(resultSet.getString("wu_user_gender"));
				user.setUserDob(resultSet.getDate("wu.user_dob"));
				contestants.add(new DoubleObject<Contestant,User>(contestant,user));
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
			ConnectionUtils.closeConnection(conn);
			QueryUtils.closeQueryObjects(prepStatement, resultSet);
		}
		return new Message("Contestant does not exist", false);
	}

}
