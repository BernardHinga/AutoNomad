package org.example.chatbot;

//import java.sql.*;

public class Main {
    public static void main(String[] args) {
        databaseConnection connection = new databaseConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");

        connection.start_connection();

        connection.close_connection();
    }
}