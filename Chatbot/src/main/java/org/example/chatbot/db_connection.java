package org.example.chatbot;

import java.sql.Connection;
import java.sql.SQLException;

public class db_connection {
    static database current_connection = new database("jdbc:postgresql://localhost:5432/AutoNomad", "postgres", "root");

    public static Connection connect() throws SQLException {
        current_connection.start_connection();
        return database.db_Connection;
    }
}
