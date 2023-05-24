package com.sourcery.clinicapp.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationConfig {

    @Autowired
    private JavaMailSender mailSender;

    public String sendEmail(String toEmail, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("clinicgunit2023@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        try {
            mailSender.send(message);
        } catch (MailException e) {
            return "Error: " + e.getMessage();
        }
        return "Mail sent.";
    }

}
