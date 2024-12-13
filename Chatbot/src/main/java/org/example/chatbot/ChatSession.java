package org.example.chatbot;

import java.sql.*;

public class ChatSession {
    public int createSession(int customerId) {
        try (Connection conn = db_connection.connect()) {
            String query = "INSERT INTO chat_session (customer_id, start_time) VALUES (?, ?) RETURNING session_id";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customerId);
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("session_id");
            }
        } catch (SQLException e) {
            System.out.println("Sorry. Couldn't log session" + e.getMessage());
        }
        return -1;
    }

    public void endSession(int session) {
        try (Connection conn = db_connection.connect()) {
            String query = "UPDATE chat_session SET end_time = ? WHERE session_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(2, session);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Sorry. Couldn't end session" + e.getMessage());
        }
    }

    public void logMessage(int sessionId, String sender, String message, int topicId) {
        try (Connection conn = db_connection.connect()) {
            String query = "INSERT INTO chat_log (session_id, sender, message, log_time, topic_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, sessionId);
            stmt.setString(2, sender);
            stmt.setString(3, message);
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(5, topicId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Sorry. Couldn't log session" + e.getMessage());
        }
    }
}
