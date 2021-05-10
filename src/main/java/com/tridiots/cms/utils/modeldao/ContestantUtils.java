package com.tridiots.cms.utils.modeldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tridiots.cms.message.Message;
import com.tridiots.cms.models.Contestant;
import com.tridiots.cms.utils.dbutils.ConnectionUtils;
import com.tridiots.cms.utils.dbutils.QueryUtils;

public class ContestantUtils {
	
	private static Contestant contestant = null;
	
	/*** Table values ***/
	private static final String TABLE_NAME = "wtaxy_contestant";
	
	private static Connection conn;
	private static PreparedStatement prepStatement;
	private static ResultSet resultSet;
	private static Message message = null;
	
	public static Message addContestant (Contestant con) {
		contestant = con;
		conn = ConnectionUtils.openConnection();
		 message = new Message("Contestant registration unsuccessful", false);
		
		String sql = "INSERT INTO ?(user_id,contestant_image_dir) VALUES (?,?)";
		try {
			prepStatement = conn.prepareStatement(sql);
			prepStatement.setString(1, TABLE_NAME);
			prepStatement.setInt(2, contestant.getUserId());
			prepStatement.setString(3, contestant.getContestantImageDir());
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
		String sql = "DELETE FROM ? WHERE contestant_id=?";
		
		try {
			prepStatement = conn.prepareStatement(sql);
			prepStatement.setString(1, TABLE_NAME);
			prepStatement.setInt(2, conId);
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
	
	public static ArrayList<Contestant> getContestants () {
		ArrayList<Contestant> contestants = new ArrayList<>();
		
		conn = ConnectionUtils.openConnection();
		String sql = "SELECT wu.user_name, wu.user_email, wu.user_first_name, wu.user_last_name, wu.user_gender, wu.user_dob, wc.contestant_image_dir\r\n"
				+ "FROM cms.wtaxy_user wu \r\n"
				+ "	INNER JOIN cms.wtaxy_contestant wc ON ( wu.user_id = wc.user_id  )  ";
		try {
			prepStatement = conn.prepareStatement(sql);
			prepStatement.setString(1, TABLE_NAME);
			resultSet = prepStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contestants;
		
	}

}
