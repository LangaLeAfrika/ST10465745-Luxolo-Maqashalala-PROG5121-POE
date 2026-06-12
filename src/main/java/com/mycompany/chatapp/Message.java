/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;
import java.util.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author Garfield
 *  Reference OpenAI (2024) ChatGPT (GPT-4o) – Assistance with Java JUnit testing.
 *           [Online] Available at: https://chat.openai.com/ [Accessed 11 Jun. 2025]
 */
public class Message {
    private static int messageCount = 0;



    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;

    public Message(String recipient, String message) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
        messageCount++;
    }

    private String generateMessageID() {
        Random rand = new Random();
        return String.format("%010d", rand.nextInt(1_000_000_000));
    }

    public boolean checkMessageID() {
        return this.messageID.length() == 10;
    }

    public boolean checkRecipientCell() {
        return this.recipient.matches("^\\+27\\d{9}$");
    }

    public final String createMessageHash() {
        String[] words = message.split(" ");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : "";
        return (messageID.substring(0, 2) + ":" + messageCount + ":" + firstWord + lastWord).toUpperCase();
    }

    public String sendMessage(String option) {
        switch (option.toLowerCase()) {
            case "send": return "Message successfully sent.";
            case "disregard": return "Press 0 to delete message.";
            case "store":
                storeMessage();
                return "Message successfully stored.";
            default: return "Invalid option.";
        }
    }

    public synchronized void storeMessage() {
        JSONObject msg = new JSONObject();
        msg.put("messageID", messageID);
        msg.put("recipient", recipient);
        msg.put("message", message);
        msg.put("messageHash", messageHash);

        JSONArray messages = new JSONArray();

        try (FileReader reader = new FileReader("messages.json")) {
            Object obj = new JSONParser().parse(reader);
            messages = (JSONArray) obj;
        } catch (IOException | ParseException e) {
            // Ignore if file doesn't exist yet
        }

        messages.add(msg);

        try (FileWriter file = new FileWriter("messages.json")) {
            file.write(messages.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONArray readStoredMessages() {
        try (FileReader reader = new FileReader("messages.json")) {
            Object obj = new JSONParser().parse(reader);
            return (JSONArray) obj;
        } catch (IOException | ParseException e) {
            return new JSONArray();
        }
    }

    public String printMessage() {
        return "MessageID: " + messageID +
               "\nMessage Hash: " + messageHash +
               "\nRecipient: " + recipient +
               "\nMessage: " + message;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public static int returnTotalMessages() {
        return messageCount;
    }
}