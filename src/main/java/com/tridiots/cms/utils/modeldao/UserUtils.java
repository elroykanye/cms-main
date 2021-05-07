package com.tridiots.cms.utils.modeldao;

import com.tridiots.cms.message.Message;
import com.tridiots.cms.models.modelmaker.UserMaker;
import com.tridiots.cms.utils.dbutils.ConnectionUtils;
import com.tridiots.cms.utils.dbutils.QueryUtils;
import com.tridiots.cms.models.User;
import com.tridiots.cms.shortcuts.IO;

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
        Message  message = findUser(login);
        
        if(findUser(login).getFlag()) {
            conn = ConnectionUtils.openConnection();
            String sql = "SELECT * FROM wtaxy_user WHERE user_name="+login;
            User user = new User();
            try {
                assert conn != null;
                statement = conn.createStatement();
                resultSet = statement.executeQuery(sql);
                if(resultSet.next()) { return new UserMaker(
                        resultSet.getInt("user_id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("user_email"),
                        resultSet.getString("user_first_name"),
                        resultSet.getString("user_last_name"),
                        resultSet.getString("user_gender"),
                        resultSet.getDate("user_dob"),
                        resultSet.getBoolean("user_verified")
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
                        resultSet.getBoolean("user_verified")
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
        boolean foundLogin = false;
        boolean foundPass = false;

        conn = ConnectionUtils.openConnection();
        try {
            String sql = "SELECT * FROM wtaxy_user";
            assert conn != null;
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);

            // check if the username or email exists
            while(resultSet.next()) {
                if(checkUE(resultSet, user.getUserLogin())) {
                    foundLogin = true;
                    break;
                }
            }
            if(foundLogin) {
                resultSet = statement.executeQuery(sql);
                // check if password exists for the said username
                while(resultSet.next()) {
                    if(checkPass(resultSet, user.getUserPass())) {
                        foundPass = true;
                        break;
                    }
                }
                if(foundPass) {
                    return new Message("Login and Password correct", true);
                } else {
                    return new Message("Password wrong", false);
                }
            } else {
                return new Message("Login incorrect", false);
            }

        } catch (SQLException exception) {
            IO.println(exception);
        } finally {
            QueryUtils.closeQueryObjects(statement, resultSet);
        } ConnectionUtils.closeConnection(conn);
        return new Message("Error", false);
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
    public static Message findUser(String input) {
        conn = ConnectionUtils.openConnection();
        String sqlUsername = "SELECT user_name FROM wtaxy_user WHERE wtaxy_user.user_name=?";
        String sqlEmail = "SELECT user_email FROM wtaxy_user WHERE wtaxy_user.user_email=?";
        try {
            assert conn != null;
            prepStatement = conn.prepareStatement(sqlUsername);
            prepStatement.setString(1, input);
            resultSet = prepStatement.executeQuery();
            
            PreparedStatement prepStatement2 = conn.prepareStatement(sqlEmail);
            prepStatement2.setString(1, input);
            ResultSet resultSet2 = prepStatement2.executeQuery();
            
            if(resultSet.next() || resultSet2.next()) return new Message("User exists", true);
            QueryUtils.closeQueryObjects(prepStatement2, resultSet2);
        } catch (SQLException exception) {
            IO.println(exception);
        } finally {
            QueryUtils.closeQueryObjects(prepStatement, resultSet);
        }
        return new Message("User does not exist", false);
    }

    // Helper Functions
    /***************************************************************************************/
    private static boolean checkUE(ResultSet rs, String ue) throws SQLException {
        return rs.getString("user_name").equals(ue)
                || rs.getString("user_email").equals(ue);
    }
    private static boolean checkPass(ResultSet rs, String pass) throws SQLException {
        return rs.getString("user_pass").equals(pass);
    }
    private static String produceRandom() {
        int min = 10000000; int max = 99999999;
        return String.valueOf((int) ((Math.random() * (max - min)) + min));
    }
    /**************************************************************************************/
}
