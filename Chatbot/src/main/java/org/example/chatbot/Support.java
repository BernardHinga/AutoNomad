package org.example.chatbot;

// Support class
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

class Support {
    public boolean createSupportTicket(int customerId, int topicId, int statusId) {
        try (Connection conn = db_connection.connect()) {
            String query = "INSERT INTO support_ticket (customer_id, topic_id, status_id, ticket_date) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customerId);
            stmt.setInt(2, topicId);
            stmt.setInt(3, statusId);
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Sorry. I couldn't open a support ticket" + e.getMessage());
            return false;
        }
    }
}
