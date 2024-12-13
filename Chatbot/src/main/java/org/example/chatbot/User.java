package org.example.chatbot;

// User management class
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    public boolean createAccount(String firstName, String secondName, String email, long phone, String password) {
        try (Connection conn = db_connection.connect()) {
            String query = "INSERT INTO customer (first_name, second_name, email, phone, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, firstName);
            stmt.setString(2, secondName);
            stmt.setString(3, email);
            stmt.setLong(4, phone);
            stmt.setString(5, password);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Could not create user. Please retry" + e.getMessage());
            return false;
        }
    }

    public boolean login(String email, String password) {
        try (Connection conn = db_connection.connect()) {
            String query = "SELECT * FROM customer WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Could not get your account. Please retry or SignUp" + e.getMessage());
            return false;
        }
    }
}