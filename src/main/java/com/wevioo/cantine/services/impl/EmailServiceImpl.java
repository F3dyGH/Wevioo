package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.security.services.PasswordResetService;
import com.wevioo.cantine.services.EmailService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender mailSender;
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    PasswordResetService passwordResetService;

    @Override
    public void sendEmail(String to, String subject, String resetUrl) throws MessagingException, IOException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        Resource resource = resourceLoader.getResource("classpath:templates/Reset-password-mail.html");
        File file = resource.getFile();
        String html = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        html = html.replace("[resetUrl]", resetUrl);
        helper.setFrom("noreply@wevioo.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);

        mailSender.send(mimeMessage);
    }
}
