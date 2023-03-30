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
import java.time.LocalDateTime;

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

    /*@PostMapping("/forget-password")
    public ResponseEntity<?> resetPassword(@RequestBody String email) {
        User user = passwordResetService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        String token = jwtUtils.generatePasswordResetToken(user);
        passwordResetService.createPasswordResetTokenForUser(user, token);

        // Send email with password reset link
        String resetUrl = "http://localhost:8082/reset-password?token=" + token;
        String body = "To reset your password, click the link below:\n" + resetUrl;
        try {
            emailService.sendEmail(user.getEmail(), "Password reset", body);
        }catch (MessagingException e){
            return ResponseEntity.badRequest().body(e);
        }
        return ResponseEntity.ok().build();
    }
*/
    /*@PostMapping("/confirm")
    public ResponseEntity<?> confirmPasswordReset(@RequestParam String token, @RequestParam String newPassword) {
        PasswordResetToken resetToken = passwordResetService.getPasswordResetToken(token);
        if (resetToken == null) {
            return ResponseEntity.badRequest().build();
        }

        User user = resetToken.getUser();
        if (jwtUtils.validatePasswordResetToken(token, user)) {
            passwordResetService.changeUserPassword(user, newPassword);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }*/
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<?> forgotPassword(@PathVariable("email") String email) throws MessagingException {
        User user = userRepository.findByUsername(email).get();

        if (user == null) {
            return ResponseEntity.badRequest().body("No user found with email: " + email);
        }
        try {
            String token = jwtUtils.generatePasswordResetToken(user);
            passwordResetService.createPasswordResetTokenForUser(user,token);
            String resetUrl = "http://localhost:8082/reset-password?token=" + token;
            String body = "To reset your password, click the link below:\t";
            emailService.sendEmail(email,"Password reset",  body + resetUrl);

        }catch (MessagingException e) {
            System.out.println(e);
        }
        // send email to user with reset password link

        return ResponseEntity.ok("Password reset email sent to: " + email);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam("newPassword") String password ){
        User user = userRepository.findByResetToken(token).get();
        if (user.getResetToken().equals(token) && user.getResetTokenExpiration().isAfter(LocalDateTime.now()) ){
            userService.updatePassword(user,password);
            return ResponseEntity.ok("Password for" + user.getUsername() + "has been changed successfully");
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/sendmail")
   public String sendmail(){

        try {
            emailService.sendEmail("test@example.com", "Test Subject", "Test mail");
        }catch (MessagingException e){
            System.out.println("yekhdemch");
        }

        return "email sent";
    }
}
