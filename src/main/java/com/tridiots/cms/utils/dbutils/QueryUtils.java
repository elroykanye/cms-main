package com.tridiots.cms.utils.dbutils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryUtils {
    public static void closeQueryObjects(Statement statement, ResultSet resultSet) {
        try {
            if(statement != null) statement.close();
        } catch (SQLException exception) {exception.printStackTrace();}
        try {
            if(resultSet != null) resultSet.close();
        } catch (SQLException exception) {exception.printStackTrace();}
    }
    public static void closeQueryObjects(PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if(preparedStatement != null) preparedStatement.close();
        } catch (SQLException exception) {exception.printStackTrace();}
        try {
            if(resultSet != null) resultSet.close();
        } catch (SQLException exception) {exception.printStackTrace();}
    }
    public static void closeQueryObjects(PreparedStatement preparedStatement,Statement statement, ResultSet resultSet) {
    	closeQueryObject(preparedStatement);
    	closeQueryObject(statement);
    	closeQueryObject(resultSet);
    }

    public static void closeQueryObject(PreparedStatement preparedStatement) {
        try {
            if(preparedStatement != null) preparedStatement.close();
        } catch (SQLException exception) {exception.printStackTrace();}
    }

    public static void closeQueryObject(Statement statement) {
        try {
            if(statement != null) statement.close();
        } catch (SQLException exception) {exception.printStackTrace();}
    }

    public static void closeQueryObject(ResultSet resultSet) {
        try {
            if(resultSet != null) resultSet.close();
        } catch (SQLException exception) {exception.printStackTrace();}
    }
	public static void closeQueryObject(PreparedStatement preparedStatement, Statement statement) {
		closeQueryObject(preparedStatement);
    	closeQueryObject(statement);
	}
}
