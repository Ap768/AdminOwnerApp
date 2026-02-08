package com.basic.adminowner.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;

}
    public void sendOwnerCredentials(String to, String email, String password) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Your Owner Account Credentials");
        msg.setText("Login Email: " + email + "\nPassword: " + password +
                "\nPlease change password on first login.");
        mailSender.send(msg);
    }
    }
