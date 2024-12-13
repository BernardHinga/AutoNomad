package org.example.chatbot;

import java.util.Scanner;

public class Login {
    Scanner scanner = new Scanner(System.in);
    User user = new User();
    ChatSession session = new ChatSession();

    public int loggedInCustomerId;
    public String loggedInCustomerName;

    public void loginProcess() {
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
                session.sessionId = session.createSession(loggedInCustomerId);
                System.out.println("Login successful! Welcome, " + loggedInCustomerName);
            } else {
                System.out.println("Invalid email or password.");
            }
        } else {
            System.out.println("Invalid input. Please log in or create an account.");
        }
    }
}
