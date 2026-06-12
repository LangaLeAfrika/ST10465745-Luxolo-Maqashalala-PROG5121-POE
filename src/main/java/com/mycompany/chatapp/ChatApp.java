/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;
import javax.swing.*;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 *
 * @author Garfield
 * Reference OpenAI (2024) ChatGPT (GPT-4o) – Assistance with Java JUnit testing.
 *           [Online] Available at: https://chat.openai.com/ [Accessed 11 Jun. 2025]
 */
public class ChatApp {
    public static void main(String[] args) {
        ArrayList<Message> sentMessages = new ArrayList<>();
        ArrayList<Message> disregardedMessages = new ArrayList<>();
        JSONArray storedMessages = Message.readStoredMessages();

        // Register user
        Login login = null;
        boolean registered = false;

        while (!registered) {
            String username = JOptionPane.showInputDialog("Register: Enter username (must contain underscore and ≤ 5 characters):");
            String password = JOptionPane.showInputDialog("Register: Enter password (≥8 chars, 1 uppercase, 1 digit, 1 special char):");
            String cellNumber = JOptionPane.showInputDialog("Register: Enter cell number (+27...)");

            login = new Login(username, password, cellNumber);
            String registrationResult = login.registerUser();
            JOptionPane.showMessageDialog(null, registrationResult);

            if (registrationResult.equals("User registered successfully.")) {
                registered = true;
            }
        }

        // Login
        boolean loggedIn = false;
        while (!loggedIn) {
            String inputUser = JOptionPane.showInputDialog("Login: Enter username:");
            String inputPass = JOptionPane.showInputDialog("Login: Enter password:");

            loggedIn = login.loginUser(inputUser, inputPass);

            String firstName = JOptionPane.showInputDialog("Enter your first name:");
            String lastName = JOptionPane.showInputDialog("Enter your last name:");
            String status = login.returnLoginStatus(loggedIn, firstName, lastName);
            JOptionPane.showMessageDialog(null, status);

            if (!loggedIn) {
                int tryAgain = JOptionPane.showConfirmDialog(null, "Try again?", "Login Failed", JOptionPane.YES_NO_OPTION);
                if (tryAgain != JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Exiting...");
                    System.exit(0);
                }
            }
        }

        // Main chat menu
        while (true) {
            String choice = JOptionPane.showInputDialog(
                "Choose an option:\n1) Send Message\n2) Show Report\n3) Search by Recipient\n4) Search by Message ID\n5) Delete by Message Hash\n6) Show Longest Message\n7) Quit");

            switch (choice) {
                case "1":
                    int num = 0;
                    while (true) {
                        try {
                            String input = JOptionPane.showInputDialog("How many messages do you want to send?");
                            num = Integer.parseInt(input);
                            if (num > 0) break;
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Invalid number.");
                        }
                    }

                    for (int i = 0; i < num; i++) {
                        String recipient = JOptionPane.showInputDialog("Enter recipient cell (+27...):");
                        String message = JOptionPane.showInputDialog("Enter message (max 250 chars):");

                        if (message.length() > 250) {
                            JOptionPane.showMessageDialog(null, "Message exceeds 250 characters by " + (message.length() - 250));
                            continue;
                        }

                        Message msg = new Message(recipient, message);

                        if (!msg.checkRecipientCell()) {
                            JOptionPane.showMessageDialog(null, "Invalid phone number.");
                            continue;
                        }

                        String option;
                        do {
                            option = JOptionPane.showInputDialog("Choose:\n- send\n- disregard\n- store").toLowerCase();
                        } while (!option.equals("send") && !option.equals("disregard") && !option.equals("store"));

                        String result = msg.sendMessage(option);
                        JOptionPane.showMessageDialog(null, result);

                        if (option.equals("send")) {
                            sentMessages.add(msg);
                        } else if (option.equals("disregard")) {
                            disregardedMessages.add(msg);
                        }

                        JOptionPane.showMessageDialog(null, msg.printMessage());
                    }
                    break;

                case "2":
                    StringBuilder report = new StringBuilder();
                    report.append("\nSent Messages:\n");
                    for (Message m : sentMessages) {
                        report.append(m.printMessage()).append("\n---\n");
                    }
                    report.append("\nStored Messages (from JSON):\n");
                    for (Object obj : storedMessages) {
                        JSONObject m = (JSONObject) obj;
                        report.append("MessageID: ").append(m.get("messageID")).append("\n")
                              .append("Message Hash: ").append(m.get("messageHash")).append("\n")
                              .append("Recipient: ").append(m.get("recipient")).append("\n")
                              .append("Message: ").append(m.get("message")).append("\n---\n");
                    }
                    JOptionPane.showMessageDialog(null, report.toString());
                    break;

                case "3":
                    String targetRecipient = JOptionPane.showInputDialog("Enter recipient to search:");
                    StringBuilder foundByRecipient = new StringBuilder("Messages sent to " + targetRecipient + ":\n");
                    for (Message m : sentMessages) {
                        if (m.getRecipient().equals(targetRecipient)) {
                            foundByRecipient.append(m.printMessage()).append("\n---\n");
                        }
                    }
                    JOptionPane.showMessageDialog(null, foundByRecipient.toString());
                    break;

                case "4":
                    String targetID = JOptionPane.showInputDialog("Enter message ID:");
                    for (Message m : sentMessages) {
                        if (m.getMessageID().equals(targetID)) {
                            JOptionPane.showMessageDialog(null, m.printMessage());
                            break;
                        }
                    }
                    break;

                case "5":
                    String hashToDelete = JOptionPane.showInputDialog("Enter message hash to delete:");
                    Iterator<Message> it = sentMessages.iterator();
                    boolean deleted = false;
                    while (it.hasNext()) {
                        Message m = it.next();
                        if (m.getMessageHash().equalsIgnoreCase(hashToDelete)) {
                            it.remove();
                            deleted = true;
                            JOptionPane.showMessageDialog(null, "Message successfully deleted.");
                            break;
                        }
                    }
                    if (!deleted) JOptionPane.showMessageDialog(null, "Message not found.");
                    break;

                case "6":
                    Message longest = null;
                    for (Message m : sentMessages) {
                        if (longest == null || m.getMessage().length() > longest.getMessage().length()) {
                            longest = m;
                        }
                    }
                    JOptionPane.showMessageDialog(null, longest != null ? longest.printMessage() : "No messages found.");
                    break;

                case "7":
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    return;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }
}