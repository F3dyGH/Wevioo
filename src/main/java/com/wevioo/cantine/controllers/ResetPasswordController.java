package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.PasswordResetToken;
import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.repositories.UserRepository;
import com.wevioo.cantine.security.jwt.JwtUtils;
import com.wevioo.cantine.security.services.PasswordResetService;
import com.wevioo.cantine.services.EmailService;
import com.wevioo.cantine.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/auth")
public class ResetPasswordController {
    @Autowired
    PasswordResetService passwordResetService;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    EmailService emailService;

    @Autowired
    IUserService userService;

    private final String TOKEN_URL ="http://localhost:4200/reset-password?token=";
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<?> forgotPassword(@PathVariable("email") String email) {
        User user = userRepository.findByUsername(email).get();

        if (user == null) {
            return ResponseEntity.badRequest().body("No user found with email: " + email);
        }
        try {
            String token = jwtUtils.generatePasswordResetToken(user);
            passwordResetService.createPasswordResetTokenForUser(user,token);
            String resetUrl = TOKEN_URL + token;
            String body = "To reset your password, click the link below:\t";
            emailService.sendEmail(email,"Password reset", resetUrl);

        }catch (MessagingException | IOException e) {
            System.out.println(e);
        }
        return ResponseEntity.ok("Password reset email sent to: " + email);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String password ){
        User user = userRepository.findByResetToken(token).get();
        if (user.getResetToken().equals(token) && user.getResetTokenExpiration().isAfter(LocalDateTime.now()) ){
            userService.updatePassword(user,password);
            return ResponseEntity.ok("Password for " + user.getUsername() + " has been changed successfully");
        }
        else if(user.getResetTokenExpiration().toLocalTime().isBefore(LocalTime.now()) || user.getResetTokenExpiration().toLocalDate().isBefore(LocalDate.now()) ) {
            user.setResetToken(null);
            user.setResetTokenExpiration(null);
            userRepository.save(user);
            return ResponseEntity.badRequest().body("token expired");
        }else {
            return ResponseEntity.badRequest().body("token not found");
        }
    }
}
