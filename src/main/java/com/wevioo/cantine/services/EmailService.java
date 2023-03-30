package com.wevioo.cantine.services;


import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;

public interface EmailService {
    void sendEmail(String to, String subject, String body)throws MessagingException;
}
