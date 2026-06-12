package com.mycompany.chatapp;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Garfield
 */


public class LoginTest {

    @Test
    public void testValidUsername() {
        Login login = new Login("kyl_1", "Password1!", "+27838968976");
        assertTrue(login.checkUserName());
    }

    @Test
    public void testInvalidUsername() {
        Login login = new Login("kyle!!!!!!!", "Password1!", "+27838968976");
        assertFalse(login.checkUserName());
    }

    @Test
    public void testValidPassword() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testInvalidPassword() {
        Login login = new Login("kyl_1", "password", "+27838968976");
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    public void testValidCellNumber() {
        Login login = new Login("kyl_1", "Password1!", "+27838968976");
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testInvalidCellNumber() {
        Login login = new Login("kyl_1", "Password1!", "08966553");
        assertFalse(login.checkCellPhoneNumber());
    }

    @Test
    public void testLoginSuccess() {
        Login login = new Login("kyl_1", "Password1!", "+27838968976");
        assertTrue(login.loginUser("kyl_1", "Password1!"));
    }

    @Test
    public void testLoginFail() {
        Login login = new Login("kyl_1", "Password1!", "+27838968976");
        assertFalse(login.loginUser("wrong", "wrong"));
    }
}
