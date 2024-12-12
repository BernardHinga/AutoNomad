package org.example.chatbot;

import java.sql.*;

/*
    This class manages the connection to the database.It encapsulates the connection logic,
    allowing the program to easily connect and disconnect with the database without malfunctioning.
*/
public class databaseConnection {

    //  Static variables to hold the URL, username and password of rhe database.
    private static String db_url;   //jdbc: localhost url with port number 5432(PostgreSQL).
    private static String db_user;  //server username.
    private static String db_pass;  //server password.

    //  Static variable that stores the database connection instance.
    public static Connection db_Connection;

    public databaseConnection(String db_url, String db_user, String db_pass) {
        //  Initialize the variables using the values provided in the parameters.
        databaseConnection.db_url = db_url;
        databaseConnection.db_user = db_user;
        databaseConnection.db_pass = db_pass;
    }

    /*
        Establish a connection to the database, and log appropriate console output.
        If connection is successful, set the `db_Connection` variable.
        If it fails, set the variable to null.
    */
    public void start_connection() {
        try{
            //  Use DriverManager class to try and establish a connection to the database.
            databaseConnection.db_Connection = DriverManager.getConnection(db_url, db_user, db_pass);
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
                databaseConnection.db_Connection.close();   // Close connection.
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


/*
    public void sql_update(String statement) {
        try{
            Statement sql_statement = databaseConnection.db_Connection.createStatement();
            sql_statement.executeUpdate(statement);
            System.out.println("SQL Updated to " + statement);
        }catch (SQLException e){
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    public ResultSet sql_execute(String statement) {
        try {
            Statement sql_statement = databaseConnection.db_Connection.createStatement();
            ResultSet rs = sql_statement.executeQuery(statement);
            System.out.println("SQL Executed to " + statement);
            return rs;
        }catch (SQLException e){
            System.out.println("SQL Error: " + e.getMessage());
        }
        return null;
    }


    public void result_printer(ResultSet output) {
        try {
            while (output.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i < output.getMetaData().getColumnCount(); i++) {
                    row.append(output.getString(i)).append(", ");
                }
                System.out.println(row);
            }
        }catch (SQLException e){
            System.out.println("Could not get data: " + e.getMessage());
        }
    }

    public ResultSet prep_string_execute(String prep_statement, String value) {
        try {
            PreparedStatement prepStat = databaseConnection.db_Connection.prepareStatement(prep_statement);
            prepStat.setString(1, value);
            ResultSet rs = prepStat.executeQuery();
            while(rs.next()) {
                System.out.println(rs.getString(1));
            }
            System.out.println("SQL Executed to " + prep_statement + value);
            return rs;
        }catch (SQLException e){
            System.out.println("SQL Error: " + e.getMessage());
        }
        return null;
    }
    public ResultSet prep_int_execute(String prep_statement, int value) {
        try {
            PreparedStatement prepStat = databaseConnection.db_Connection.prepareStatement(prep_statement);
            prepStat.setInt(1, value);
            ResultSet rs = prepStat.executeQuery();
            System.out.println("SQL Executed to " + prep_statement + value);
            return rs;
        }catch (SQLException e){
            System.out.println("SQL Error: " + e.getMessage());
        }
        return null;
    }

    public ResultSet prep_console_print(String query, String value) {
        try {
            PreparedStatement consolePrep = databaseConnection.db_Connection.prepareStatement(query);
            consolePrep.setString(1, value);
            ResultSet consoleOutput = consolePrep.executeQuery();
            while (consoleOutput.next()) {
                System.out.println(consoleOutput.getString("registration_number"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return null;
    }
    */
}