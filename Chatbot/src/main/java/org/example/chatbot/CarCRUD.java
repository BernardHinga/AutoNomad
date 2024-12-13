//package org.example.chatbot;
//
//import java.sql.*;
//
//public class CarCRUD {
//
//    public void createCar(String make, String model, int carTypeId, int year, String regNumber, int rentalPrice) {
//        String sql = "INSERT INTO car (car_make, car_model, car_type_id, year, registration_number, rental_price) VALUES (?, ?, ?, ?, ?, ?)";
//
//        try (Connection conn = connect(); PreparedStatement prepStat = conn.prepareStatement(sql)) {
//            prepStat.setString(1, make);
//            prepStat.setString(2, model);
//            prepStat.setInt(3, carTypeId);
//            prepStat.setInt(4, year);
//            prepStat.setString(5, regNumber);
//            prepStat.setInt(6, rentalPrice);
//            prepStat.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("Could not add data" + e.getMessage());
//        }
//    }
//
//    public void updateCar(int carId, String make, String model, int carTypeId, int year, String regNumber, int rentalPrice) {
//        String sql = "UPDATE car SET car_make = ?, car_model = ?, car_type_id = ?, year = ?, registration_number = ?, rental_price = ? WHERE car_id = ?";
//
//        try (Connection conn = connect(); PreparedStatement prepStat = conn.prepareStatement(sql)) {
//            prepStat.setString(1, make);
//            prepStat.setString(2, model);
//            prepStat.setInt(3, carTypeId);
//            prepStat.setInt(4, year);
//            prepStat.setString(5, regNumber);
//            prepStat.setInt(6, rentalPrice);
//            prepStat.setInt(7, carId);
//            prepStat.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteCar(int carId) {
//        String sql = "DELETE FROM car WHERE car_id = ?";
//
//        try (Connection conn = connect(); PreparedStatement prepStat = conn.prepareStatement(sql)) {
//            prepStat.setInt(1, carId);
//            prepStat.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void getCarDetails(int carId) {
//        String carSql = "SELECT c.car_make, c.car_model, c.year, c.registration_number, c.rental_price, ct.car_type FROM car c INNER JOIN car_type ct ON c.car_type_id = ct.car_type_id WHERE c.car_id = ?";
//        String featuresSql = "SELECT f.feature FROM car_features cf INNER JOIN features f ON cf.feature_id = f.feature_id WHERE cf.car_id = ?";
//
//        try (Connection conn = connect(); PreparedStatement carStmt = conn.prepareStatement(carSql); PreparedStatement featuresStmt = conn.prepareStatement(featuresSql)) {
//
//            carStmt.setInt(1, carId);
//            ResultSet carRs = carStmt.executeQuery();
//
//            if (carRs.next()) {
//                System.out.println("Make: " + carRs.getString("car_make"));
//                System.out.println("Model: " + carRs.getString("car_model"));
//                System.out.println("Year: " + carRs.getInt("year"));
//                System.out.println("Registration Number: " + carRs.getString("registration_number"));
//                System.out.println("Rental Price: " + carRs.getInt("rental_price"));
//                System.out.println("Type: " + carRs.getString("car_type"));
//            } else {
//                System.out.println("Car not found.");
//                return;
//            }
//
//            featuresStmt.setInt(1, carId);
//            ResultSet featuresRs = featuresStmt.executeQuery();
//
//            System.out.println("Features:");
//            while (featuresRs.next()) {
//                System.out.println("- " + featuresRs.getString("feature"));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
