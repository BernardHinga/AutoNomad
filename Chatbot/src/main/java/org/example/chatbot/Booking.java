package org.example.chatbot;

// Booking management class
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Booking {
    Scanner scanner = new Scanner(System.in);
    CarInventory carInventory = new CarInventory();
    Login login = new Login();

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

    public void bookingProcess() {
        System.out.println("Enter the car IDs you want to book, separated by commas:");
        String[] carIdsInput = scanner.nextLine().split(",");

        //  Use a map to store all instances or car choices.
        Map<Integer, Integer> carIdAndCost = new HashMap<>();

        for (String carIdStr : carIdsInput) {
            try {
                int carId = Integer.parseInt(carIdStr.trim());
                List<Map<String, Object>> cars = carInventory.viewInventory();
                for (Map<String, Object> car : cars) {
                    if ((int) car.get("car_id") == carId) {
                        carIdAndCost.put(carId, (int) car.get("rental_price"));
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid car ID: " + carIdStr);
            }
        }

        //  Ensure user does not exit without selecting a vehicle.
        if (carIdAndCost.isEmpty()) {
            System.out.println("No valid cars selected. Try again.");
        }
        //  Date and time inputs
        Timestamp pickupDate, returnDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        while (true) {
            try {
                System.out.print("Enter Pickup Date (yyyy-MM-dd HH:mm:ss): ");
                pickupDate = new Timestamp(dateFormat.parse(scanner.nextLine()).getTime());
                System.out.print("Enter Return Date (yyyy-MM-dd HH:mm:ss): ");
                returnDate = new Timestamp(dateFormat.parse(scanner.nextLine()).getTime());

                if (returnDate.before(pickupDate)) {
                    System.out.println("Return date must be after pickup date. Try again.");
                    continue;
                }
                break;
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm:ss.");
            }
        }
        //  Calculate total cost off all cars chosen.
        int totalCost = carIdAndCost.values().stream().mapToInt(Integer::intValue).sum();

        if (createBooking(login.loggedInCustomerId, carIdAndCost, pickupDate, returnDate)) {
            System.out.println("Booking successful! Total cost: " + totalCost);
        } else {
            System.out.println("Failed to create booking.");
        }
    }
}
