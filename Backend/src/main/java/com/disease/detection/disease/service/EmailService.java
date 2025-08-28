package com.disease.detection.disease.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Password Reset OTP for Heart Care");
        message.setText("Your One-Time Password (OTP) for resetting your password is: " + otp +
                "\n\nThis OTP is valid for 5 minutes. If you did not request this, please ignore this email.");
        mailSender.send(message);
    }
}
