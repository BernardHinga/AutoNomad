package org.example.chatbot;

// Booking management class
import java.sql.*;
import java.util.Map;

class Booking {
    public boolean createBooking(int customerId, Map<Integer, Integer> carIdAndCost, Timestamp pickupDate, Timestamp returnDate) {
        try (Connection conn = db_connection.connect()) {
            String query = "INSERT INTO booking (customer_id, car_id, pickup_date, return_date, total_cost) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            for (Map.Entry<Integer, Integer> entry : carIdAndCost.entrySet()) {
                stmt.setInt(1, customerId);
                stmt.setInt(2, entry.getKey()); // car_id
                stmt.setTimestamp(3, pickupDate);
                stmt.setTimestamp(4, returnDate);
                stmt.setInt(5, entry.getValue()); // cost for the specific car
                stmt.addBatch();
            }
            stmt.executeBatch();
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't create a booking" + e.getMessage());
            return false;
        }
    }
}
