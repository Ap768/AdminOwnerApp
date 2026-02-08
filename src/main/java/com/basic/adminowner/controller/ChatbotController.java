package com.basic.adminowner.controller;

import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/chatbot")
public class ChatbotController {

    @PostMapping("/ask")
    public String ask(@RequestBody String message) {

        message = message.toLowerCase();

        if (message.contains("hi") || message.contains("hello")) {
            return "Hello! How can I help you?";
        }
        if (message.contains("add property")) {
            return "To add property, go to Owner Dashboard and fill the form.";
        }
        if (message.contains("login")) {
            return "Use your email and password to login.";
        }
        if (message.contains("forgot password")) {
            return "Contact admin to reset your password.";
        }

        return "Sorry, I didn't understand. Try asking about login or add property.";
    }
}
