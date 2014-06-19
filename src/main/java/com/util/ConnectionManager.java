package com.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles connection to access database
 *
 * @author sherman tab
 */
public class ConnectionManager {

    private static String dbUser;
    private static String dbPassword;
    private static String dbURL;

    static {
        // grab environment variable
        String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");

        if (host != null) {

            String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
            String dbName = System.getenv("OPENSHIFT_APP_NAME");
            dbUser = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
            dbPassword = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");

            dbURL = "jdbc:mysql://" + host + ":" + port + "/" + dbName;

        } else {

            try {
                dbUser = "root";
                dbPassword = "1234";
                dbURL = "jdbc:mysql://localhost:3306/tweetboard";

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } // Make sure to close this before the try block below

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // unable to load properties file
            String message = "Unable to find JDBC driver for MySQL.";

            System.out.println(message);
        }
    }

    /**
     * Gets a connection to the database
     *
     * @return the connection
     * @throws SQLException if an error occurs when connecting
     */
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(dbURL, dbUser, dbPassword);

    }

    /**
     * close the given connection, statement and resultset
     *
     * @param conn the connection object to be closed
     * @param stmt the statement object to be closed
     * @param rs the resultset object to be closed
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("Result set close - " + ex.getMessage());
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            System.out.println("Statement set close - " + ex.getMessage());
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("Connection set close - " + ex.getMessage());
        }
    }
}
