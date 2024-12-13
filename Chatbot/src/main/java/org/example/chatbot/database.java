package org.example.chatbot;

import java.sql.*;

/*
    This class manages the connection to the database.It encapsulates the connection logic,
    allowing the program to easily connect and disconnect with the database without malfunctioning.
*/
public class database {

    //  Static variables to hold the URL, username and password of rhe database.
    private static String db_url;   //jdbc: localhost url with port number 5432(PostgreSQL).
    private static String db_user;  //server username.
    private static String db_pass;  //server password.

    //  Static variable that stores the database connection instance.
    public static Connection db_Connection;

    public database(String db_url, String db_user, String db_pass) {
        //  Initialize the variables using the values provided in the parameters.
        database.db_url = db_url;
        database.db_user = db_user;
        database.db_pass = db_pass;
    }

    /*
        Establish a connection to the database, and log appropriate console output.
        If connection is successful, set the `db_Connection` variable.
        If it fails, set the variable to null.
    */
    public void start_connection() {
        try{
            //  Use DriverManager class to try and establish a connection to the database.
            database.db_Connection = DriverManager.getConnection(db_url, db_user, db_pass);
            System.out.println("Connected to database");
        }catch(SQLException e){
            // Handle SQL exceptions and display the error message.
            System.out.println("Connection failed\n" + e.getMessage());
            db_Connection = null;   // Ensure connection variable is null if connection fails.
        }
    }

    /*
        Close current database connection.
        Check with `db_Connection`if a connection exists before attempting the closing procedure.
        Log the appropriate message for success or failure
     */
    public void close_connection() {
        //  Check for an existing connection before proceeding.
        if(db_Connection != null) {
            try {
                database.db_Connection.close();   // Close connection.
                System.out.println("Database disconnected");
            } catch (SQLException e) {
                //  Handle SQL exceptions and display the error message.
                System.out.println("Could not disconnect database\n" + e.getMessage());
            }
        }else {
            //  If no connection exists, display a message.
            System.out.println("No database connection found");
        }
    }
}