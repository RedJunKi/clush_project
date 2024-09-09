package com.clush.test.domain.calendar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private final JavaMailSender mailSender;

    public void sendEmail(String email, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(subject);
        msg.setText(text);
        mailSender.send(msg);
    }
}
