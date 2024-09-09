package com.clush.test.domain.calendar.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    EmailService emailService;
    @Test
    void sendEmail() {

        String email = "beenzino8821@gmail.com";
        String subject = "테스트 제목";
        String text = "테스트 내용";

//        emailService.sendEmail(email, subject, text);
    }
}