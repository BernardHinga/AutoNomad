package org.example.chatbot;

/*
    Main application class.
    Has the chatbot instance and handles all user interactions.
*/

import java.util.*;

public class ChatBot {
    public static void main(String[] args) {

        //  Initialize all essential components of the chatbot.
        Scanner scanner = new Scanner(System.in);
        CarInventory carInventory = new CarInventory();
        Booking booking = new Booking();
        Support support = new Support();
        ChatSession session = new ChatSession();
        Login login = new Login();

        System.out.println("Welcome to the Chatbot Application!");

        // Variables for managing user state and session.
        login.loggedInCustomerId = -1;    // Indicates the user's state. (-1 ~ Not logged in)
        session.sessionId = -1;     // Indicates the chatbot state. (-1 ~ No active session)

        //  Application 'recursive' loop.
        while (true) {
            //  Ensure user logs in or creates account before proceeding with the application.(User Authentication)
            if (login.loggedInCustomerId == -1) {
                login.loginProcess();
            }

            //  Proceeding with the application after users successfully log in.
            System.out.println("How can I assist you today?");
            System.out.println("Options: View inventory, Book cars, Support, Exit");
            System.out.print("You: ");
            String input = scanner.nextLine();

            //  Log user conversation to the database.
            session.logMessage(session.sessionId, "User", input, 1);

            // Exit the program once user is done with the application.
            if (input.equalsIgnoreCase("Exit")) {
                System.out.println("Exiting application. Goodbye!");
                session.endSession(session.sessionId);
                db_connection.disconnect();     // Terminate database connection when the application closes.
                scanner.close();
                return;
            }

            // Handle potential user logout.
            if (input.equalsIgnoreCase("Logout")) {
                //loggedInEmail = null;
                login.loggedInCustomerId = -1;
                session.sessionId = -1;
                System.out.println("You have been logged out.");
                continue;
            }

            //  Display car inventory
            if (input.toLowerCase().contains("view") && input.toLowerCase().contains("cars") || input.toLowerCase().contains("inventory")) {
                carInventory.displayInventory();
            }
            //  Handle car Booking process
            else if (input.toLowerCase().contains("book") && input.toLowerCase().contains("cars")) {
                booking.bookingProcess();
            }
            //  Trigger support escalation.
            else if (input.toLowerCase().contains("support") || input.toLowerCase().contains("help")) {
                System.out.print("Topic ID: ");
                int topicId = scanner.nextInt();
                System.out.print("Status ID: ");
                int statusId = scanner.nextInt();

                if (support.createSupportTicket(login.loggedInCustomerId, topicId, statusId)) {
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
