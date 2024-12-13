package org.example.chatbot;

// Main application class
// Handles chatbot instance
import java.sql.*;
import java.util.*;

public class ChatBot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = new User();
        CarInventory carInventory = new CarInventory();
        Booking booking = new Booking();
        Support support = new Support();

        System.out.println("Welcome to the Chatbot Application!");
        System.out.println("1. Create Account\n2. Login\n3. View Car Inventory\n4. Book a Car\n5. Support\n6. Exit");

        while (true) {
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Create account
                    System.out.print("First Name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Second Name: ");
                    String secondName = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Phone: ");
                    long phone = scanner.nextLong();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    if (user.createAccount(firstName, secondName, email, phone, password)) {
                        System.out.println("Account created successfully!");
                    } else {
                        System.out.println("Failed to create account.");
                    }
                    break;

                case 2: // Login
                    System.out.print("Email: ");
                    String loginEmail = scanner.nextLine();
                    System.out.print("Password: ");
                    String loginPassword = scanner.nextLine();

                    if (user.login(loginEmail, loginPassword)) {
                        System.out.println("Login successful!");
                    } else {
                        System.out.println("Invalid email or password.");
                    }
                    break;

                case 3: // View car inventory
                    List<Map<String, Object>> cars = carInventory.viewInventory();
                    for (Map<String, Object> car : cars) {
                        System.out.println("Car ID: " + car.get("car_id") + ", Make: " + car.get("car_make") + ", Model: " + car.get("car_model") + ", Rental Price: " + car.get("rental_price"));
                    }
                    break;

                case 4: // Book a car
                    System.out.print("Customer ID: ");
                    int customerId = scanner.nextInt();
                    System.out.print("Car ID: ");
                    int carId = scanner.nextInt();
                    System.out.print("Pickup Date (yyyy-MM-dd HH:mm:ss): ");
                    scanner.nextLine(); // Consume newline
                    Timestamp pickupDate = Timestamp.valueOf(scanner.nextLine());
                    System.out.print("Return Date (yyyy-MM-dd HH:mm:ss): ");
                    Timestamp returnDate = Timestamp.valueOf(scanner.nextLine());
                    System.out.print("Total Cost: ");
                    int totalCost = scanner.nextInt();

                    if (booking.createBooking(customerId, carId, pickupDate, returnDate, totalCost)) {
                        System.out.println("Booking created successfully!");
                    } else {
                        System.out.println("Failed to create booking.");
                    }
                    break;

                case 5: // Support
                    System.out.print("Customer ID: ");
                    int supportCustomerId = scanner.nextInt();
                    System.out.print("Topic ID: ");
                    int topicId = scanner.nextInt();
                    System.out.print("Status ID: ");
                    int statusId = scanner.nextInt();

                    if (support.createSupportTicket(supportCustomerId, topicId, statusId)) {
                        System.out.println("Support ticket created successfully!");
                    } else {
                        System.out.println("Failed to create support ticket.");
                    }
                    break;

                case 6: // Exit
                    System.out.println("Exiting application. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
