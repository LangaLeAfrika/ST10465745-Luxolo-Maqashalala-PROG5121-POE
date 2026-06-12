/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Garfield
 *  Reference OpenAI (2024) ChatGPT (GPT-4o) – Assistance with Java JUnit testing.
 *           [Online] Available at: https://chat.openai.com/ [Accessed 11 Jun. 2025]
 */

public class MessageTest {

    @Test
    public void testRecipientValid() {
        Message msg = new Message("+27834557896", "Did you get the cake?");
        assertTrue(msg.checkRecipientCell());
    }

    @Test
    public void testRecipientInvalid() {
        Message msg = new Message("08575975889", "Hi");
        assertFalse(msg.checkRecipientCell());
    }

    @Test
    public void testMessageTooLong() {
        String longMessage = "x".repeat(260);
        assertTrue(longMessage.length() > 250);
    }

    @Test
public void testMessageHashCreation() {
    Message msg = new Message("+27834557896", "Hi Mike, can you join us for dinner tonight");
    String hash = msg.createMessageHash();
    assertTrue(hash.matches("^\\d{2}:\\d+:HITONIGHT$"));
}

    
    @Test
    public void testMessageSendOption() {
        Message msg = new Message("+27834557896", "Hi Keegan, did you receive the payment?");
        assertEquals("Message successfully sent.", msg.sendMessage("send"));
        assertEquals("Press 0 to delete message.", msg.sendMessage("disregard"));
        assertEquals("Message successfully stored.", msg.sendMessage("store"));
    }
}
