package org.example.chatbot;

// Main application class
// Handles chatbot instance
import java.sql.*;
import java.util.*;
import java.text.*;

public class ChatBot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = new User();
        CarInventory carInventory = new CarInventory();
        Booking booking = new Booking();
        Support support = new Support();
        ChatSession session = new ChatSession();

        System.out.println("Welcome to the Chatbot Application!");

        //String loggedInEmail;
        String loggedInCustomerName;
        int loggedInCustomerId = -1;
        int sessionId = -1;

        while (true) {
            if (loggedInCustomerId == -1) {
                System.out.println("Please log in or create an account to proceed.");
                System.out.println("Type 'Create account' to register or 'Login' to log in. Type 'Exit' to quit.");
                System.out.print("You: ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("Exit")) {
                    System.out.println("Exiting application. Goodbye!");
                    db_connection.disconnect();
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
                        loggedInCustomerId = user.getCustomerId(loginEmail);
                        loggedInCustomerName = user.getCustomerName(loggedInCustomerId);
                        sessionId = session.createSession(loggedInCustomerId);
                        System.out.println("Login successful! Welcome, " + loggedInCustomerName);
                    } else {
                        System.out.println("Invalid email or password.");
                    }
                } else {
                    System.out.println("Invalid input. Please log in or create an account.");
                }
                continue;
            }

            System.out.println("How can I assist you today?");
            System.out.println("Options: View inventory, Book cars, Support, Exit");
            System.out.print("You: ");
            String input = scanner.nextLine();

            session.logMessage(sessionId, "User", input, 1);

            if (input.equalsIgnoreCase("Exit")) {
                System.out.println("Exiting application. Goodbye!");
                session.endSession(sessionId);
                db_connection.disconnect();
                scanner.close();
                return;
            }

            if (input.equalsIgnoreCase("Logout")) {
                //loggedInEmail = null;
                loggedInCustomerId = -1;
                sessionId = -1;
                System.out.println("You have been logged out.");
                continue;
            }

            if (input.toLowerCase().contains("view") && input.toLowerCase().contains("cars") || input.toLowerCase().contains("inventory")) {
                List<Map<String, Object>> cars = carInventory.viewInventory();
                System.out.printf("%-10s %-15s %-15s %-10s%n", "Car ID", "Make", "Model", "Rental Price");
                System.out.println("-----------------------------------------------------------");

                for (Map<String, Object> car : cars) {
                    System.out.printf(
                            "%-10s %-15s %-15s %-10s%n",
                            car.get("car_id"),
                            car.get("car_make"),
                            car.get("car_model"),
                            car.get("rental_price")
                    );
                }
            } else if (input.toLowerCase().contains("book") && input.toLowerCase().contains("cars")) {
                System.out.println("Enter the car IDs you want to book, separated by commas:");
                String[] carIdsInput = scanner.nextLine().split(",");
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

                if (carIdAndCost.isEmpty()) {
                    System.out.println("No valid cars selected. Try again.");
                    continue;
                }

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

                int totalCost = carIdAndCost.values().stream().mapToInt(Integer::intValue).sum();

                if (booking.createBooking(loggedInCustomerId, carIdAndCost, pickupDate, returnDate)) {
                    System.out.println("Booking successful! Total cost: " + totalCost);
                } else {
                    System.out.println("Failed to create booking.");
                }
            } else if (input.toLowerCase().contains("support") || input.toLowerCase().contains("help")) {
                System.out.print("Topic ID: ");
                int topicId = scanner.nextInt();
                System.out.print("Status ID: ");
                int statusId = scanner.nextInt();

                if (support.createSupportTicket(loggedInCustomerId, topicId, statusId)) {
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
