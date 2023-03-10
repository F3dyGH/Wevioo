package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {


    JavaMailSender mailSender;

    @Override
    public void sendEmail(SimpleMailMessage email) {
       mailSender.send(email);
    }
}
