package org.example.chatbot;

// Car inventory management class
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CarInventory {
    public List<Map<String, Object>> viewInventory() {
        List<Map<String, Object>> cars = new ArrayList<>();
        try (Connection conn = db_connection.connect()) {
            String query = "SELECT * FROM car";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> car = new HashMap<>();
                car.put("car_id", rs.getInt("car_id"));
                car.put("car_make", rs.getString("car_make"));
                car.put("car_model", rs.getString("car_model"));
                car.put("rental_price", rs.getInt("rental_price"));
                cars.add(car);
            }
        } catch (SQLException e) {
            System.out.println("Can't retrieve inventory" + e.getMessage());
        }
        return cars;
    }

    public boolean addCar(String make, String model, int typeId, int year, String regNumber, int rentalPrice) {
        try (Connection conn = db_connection.connect()) {
            String query = "INSERT INTO car (car_make, car_model, car_type_id, year, registration_number, rental_price) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, make);
            stmt.setString(2, model);
            stmt.setInt(3, typeId);
            stmt.setInt(4, year);
            stmt.setString(5, regNumber);
            stmt.setInt(6, rentalPrice);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Can't add car to inventory!" + e.getMessage());
            return false;
        }
    }
}
