package com.tridiots.cms.utils.modeldao;

import com.tridiots.cms.kanye.IO;
import com.tridiots.cms.message.Message;
import com.tridiots.cms.models.modelmaker.UserMaker;
import com.tridiots.cms.utils.dbutils.ConnectionUtils;
import com.tridiots.cms.utils.dbutils.QueryUtils;
import com.tridiots.cms.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tridiots
 * Data access class for the User model.
 */
@SuppressWarnings("DanglingJavadoc")
public class UserUtils {
    private static PreparedStatement prepStatement = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;
    private static Connection conn = null;

    /**
     * Registers a user into a database using the user bean and ORM..
     * @param user the bean to be inserted.
     * @return a message object with a flag and string indicating success or failure
     */
    public static Message registerUser(User user) {
        
        Message checkEmail = findUser(user.getUserEmail());
        Message checkUsername = findUser(user.getUserName());
        
        if(checkEmail.getFlag()) return new Message("Email already registered on this site!", false);
        else if(checkUsername.getFlag()) return new Message("Username already taken!", false);
        else {
            conn = ConnectionUtils.openConnection();
            String registerString =
                    "INSERT INTO wtaxy_user(user_name,user_email,user_pass,user_first_name,user_last_name,user_verified) "
                    + "VALUES (?,?,?,?,?,?)";
            try {
                assert conn != null;
                prepStatement = conn.prepareStatement(registerString);
                prepStatement.setString(1, user.getUserName());
                prepStatement.setString(2, user.getUserEmail());
                prepStatement.setString(3, user.getUserPass());
                prepStatement.setString(4, user.getUserFirstName());
                prepStatement.setString(5, user.getUserLastName());
                prepStatement.setInt(6, 0);
                prepStatement.execute();
            } catch (SQLException exception) {
                IO.println(exception);
            } finally {
                QueryUtils.closeQueryObject(prepStatement);
            }
            ConnectionUtils.closeConnection(conn);
            return new Message("User registered", true);
        }
    }

    /**
     * deletes a specified user from the database
     * @param user the user object to be mapped for deletion
     */
    public static void deleteUser(User user) {
        conn = ConnectionUtils.openConnection();
        try {
            if(findUser(user.getUserId()).getFlag()) {
                String sql = "DELETE FROM wtaxy_user WHERE wtaxy_user.user_id=" + user.getUserId();
                assert conn != null;
                conn.createStatement().execute(sql);
                System.out.println("User deleted");
            }
        } catch (SQLException exception) {
            IO.println(exception);
        } ConnectionUtils.closeConnection(conn);
    }
    
    
    public static void deleteAllUsers() {
        conn = ConnectionUtils.openConnection();
        try {
            resultSet = conn.createStatement().executeQuery("SELECT * FROM wtaxy_user");
            while(resultSet.next()) {
                int uid = resultSet.getInt("user_id");
                conn.createStatement().execute("DELETE FROM wtaxy_user WHERE wtaxy_user.user_id=" + uid);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            QueryUtils.closeQueryObject(resultSet);
        } ConnectionUtils.closeConnection(conn);
    }
    
    
    public static User getUser(String login) {
        Message foundUser = findUser(login);
        if(foundUser.getFlag()) {
            conn = ConnectionUtils.openConnection();
            String query = "SELECT * FROM wtaxy_user WHERE user_name=? OR user_email=?";
            try {
                assert conn != null;
                statement = conn.createStatement();
                prepStatement = conn.prepareStatement(query);
                prepStatement.setString(1, login); prepStatement.setString(2, login);
                resultSet = prepStatement.executeQuery();
                if(resultSet.next()) { return new UserMaker(
                        resultSet.getInt("user_id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("user_email"),
                        resultSet.getString("user_first_name"),
                        resultSet.getString("user_last_name"),
                        resultSet.getString("user_gender"),
                        resultSet.getDate("user_dob"),
                        resultSet.getBoolean("user_verified"),
                        resultSet.getInt("user_role")
                    ).getUser();
                } 
            } catch(SQLException exception) {
                IO.println(exception);
            } 
        }
        
        return null;
    }
    
    /**
     * Gets a user from the users table using the provided userID 
     * @param userId the int value of the user ID
     * @return the object containing the user information
     */
    public static User getUser(int userId) {
        conn = ConnectionUtils.openConnection();
        String sql = "SELECT * FROM wtaxy_user WHERE user_id = ?";
        User user = new User();
        try {
            assert conn != null;
            prepStatement = conn.prepareStatement(sql);
            prepStatement.setInt(1, userId);
            resultSet = prepStatement.executeQuery();
            if(resultSet.next()) {
                user.setUserName(resultSet.getString("user_name"));
                user.setUserEmail(resultSet.getString("user_email"));
                user.setUserFirstName(resultSet.getString("user_first_name"));
                user.setUserLastName(resultSet.getString("user_last_name"));
                user.setUserGender(resultSet.getString("user_gender"));
                user.setUserDob(resultSet.getDate("user_dob"));
            }
        } catch(SQLException exception) {
            IO.println(exception);
        } finally {
            QueryUtils.closeQueryObjects(prepStatement, resultSet);
        }
        ConnectionUtils.closeConnection(conn);
        return user;
    }

    /**
     * extracts all users from the database and stores in a collection
     * @return a list collection of user beans
     */
    public static ArrayList<User> getUsers() {
        conn = ConnectionUtils.openConnection();
        String sql = "SELECT * FROM wtaxy_user";
        ArrayList<User> users = new ArrayList<>();
        try {
            assert conn != null;
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                users.add(new UserMaker(
                        resultSet.getInt("user_id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("user_email"),
                        resultSet.getString("user_first_name"),
                        resultSet.getString("user_last_name"),
                        resultSet.getString("user_gender"),
                        resultSet.getDate("user_dob"),
                        resultSet.getBoolean("user_verified"),
                        resultSet.getInt("user_role")
                        ).getUser()
                );
            }

        } catch (SQLException exception) {
            IO.println(exception);
        } finally {
            QueryUtils.closeQueryObjects(statement, resultSet);
        } ConnectionUtils.closeConnection(conn);
        return users;
    }

    /**
     * Logs in a user into the system and returns and AuthMessage object depending on
     * success.
     * @param user : the bean to be logged in
     * @return AuthMessage instance with corresponding flag.
     */
    public static Message loginUser(User user) {
        Message foundUser = findUser(user.getUserLogin());
        if(foundUser.getFlag()) {
        	String sql = "SELECT user_pass FROM wtaxy_user WHERE user_name=? OR user_email=?";
        	
        	try {
        		prepStatement = conn.prepareStatement(sql);
            	prepStatement.setString(1, user.getUserLogin());
				prepStatement.setString(2, user.getUserLogin());
				resultSet = prepStatement.executeQuery();
				if(resultSet.next()) {
					if(resultSet.getString("user_pass").equals(user.getUserPass())) 
						return new Message("Login successful", true);
				} else return new Message("Wrong Password", false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				QueryUtils.closeQueryObjects(prepStatement, resultSet);
				ConnectionUtils.closeConnection(conn);
			} 
        } else {
        	return new Message("User not found", false);
        } return new Message("Login unsuccessful",false);
    }

    public static Message recoverUser(String login) {
        conn = ConnectionUtils.openConnection();
        String sql = "SELECT * FROM wtaxy_user";

        try {
            assert conn != null;
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                if(resultSet.getString("user_name").equals(login) || resultSet.getString("user_email").equals(login)) {
                    String authCode = produceRandom();
                    return new Message(authCode, true);
                }
            }
        } catch (SQLException exception) {
            IO.println(exception);
        } finally {
            QueryUtils.closeQueryObjects(prepStatement, resultSet);
        }
        ConnectionUtils.closeConnection(conn);
        return new Message("Account not exist", false);
    }
    

    public static Message findUser(int uid) {
        conn = ConnectionUtils.openConnection();
        try {
            resultSet = conn.createStatement().executeQuery(  "SELECT user_id FROM wtaxy_user WHERE user_id=" + uid);
            if(resultSet.next()) return new Message("User exists", true );
        } catch (SQLException ex) {
            Logger.getLogger(UserUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally { QueryUtils.closeQueryObject(resultSet); }
        return new Message("User does not exist", false);
    }
    
    
    public static Message findUser(String login) {
        conn = ConnectionUtils.openConnection();
        String sqlUsername = "SELECT user_name,user_email FROM wtaxy_user WHERE user_name=? OR user_email=?";
        try {
            assert conn != null;
            prepStatement = conn.prepareStatement(sqlUsername);
            prepStatement.setString(1, login);
            prepStatement.setString(2, login);
            resultSet = prepStatement.executeQuery();
            
            if(resultSet.next()) return new Message("User exists sah mon frere", true);
        } catch (SQLException exception) {
            IO.println(exception);
        } finally {
            QueryUtils.closeQueryObjects(prepStatement, resultSet);
        }
        return new Message("User does not exist", false);
    }

    // Helper Functions
    /***************************************************************************************/
    
    private static String produceRandom() {
        int min = 10000000; int max = 99999999;
        return String.valueOf((int) ((Math.random() * (max - min)) + min));
    }
    /**************************************************************************************/
}
