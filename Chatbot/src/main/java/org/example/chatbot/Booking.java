package org.example.chatbot;

// Booking management class
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

class Booking {
    public boolean createBooking(int customerId, int carId, Timestamp pickupDate, Timestamp returnDate, int totalCost) {
        try (Connection conn = db_connection.connect()) {
            String query = "INSERT INTO booking (customer_id, car_id, pickup_date, return_date, total_cost) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customerId);
            stmt.setInt(2, carId);
            stmt.setTimestamp(3, pickupDate);
            stmt.setTimestamp(4, returnDate);
            stmt.setInt(5, totalCost);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Sorry. I couldn't complete your booking" + e.getMessage());
            return false;
        }
    }
}
