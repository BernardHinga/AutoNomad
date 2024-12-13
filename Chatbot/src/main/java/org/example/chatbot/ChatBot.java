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
        System.out.println("Type your query to begin (e.g., 'Create account', 'View cars', 'Book car', 'Support'). Type 'Exit' to quit.");

        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("Exit")) {
                System.out.println("Exiting application. Goodbye!");
                scanner.close();
                return;
            }

            if (input.toLowerCase().contains("create") && input.toLowerCase().contains("account")) {
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
            } else if (input.toLowerCase().contains("login")) {
                System.out.print("Email: ");
                String loginEmail = scanner.nextLine();
                System.out.print("Password: ");
                String loginPassword = scanner.nextLine();

                if (user.login(loginEmail, loginPassword)) {
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Invalid email or password.");
                }
            } else if (input.toLowerCase().contains("view") && input.toLowerCase().contains("cars")) {
                List<Map<String, Object>> cars = carInventory.viewInventory();
                for (Map<String, Object> car : cars) {
                    System.out.println("Car ID: " + car.get("car_id") + ", Make: " + car.get("car_make") + ", Model: " + car.get("car_model") + ", Rental Price: " + car.get("rental_price"));
                }
            } else if (input.toLowerCase().contains("book") && input.toLowerCase().contains("car")) {
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
            } else if (input.toLowerCase().contains("support") || input.toLowerCase().contains("help")) {
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
            } else {
                System.out.println("Sorry, I didn't understand that. Please try again.");
            }
        }
    }
}
