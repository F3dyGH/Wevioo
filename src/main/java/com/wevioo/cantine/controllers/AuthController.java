package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.Role;
import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.enums.enumRole;
import com.wevioo.cantine.repositories.RoleRepository;
import com.wevioo.cantine.repositories.UserRepository;
import com.wevioo.cantine.security.jwt.JwtUtils;
import com.wevioo.cantine.security.payloads.request.LoginRequest;
import com.wevioo.cantine.security.payloads.request.SignupRequest;
import com.wevioo.cantine.security.payloads.response.JwtResponse;
import com.wevioo.cantine.security.payloads.response.MessageResponse;
import com.wevioo.cantine.security.services.UserDetailsImpl;
import com.wevioo.cantine.security.services.UserDetailsServiceImpl;
import com.wevioo.cantine.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userService;

    @Autowired
    EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println(jwt);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                /*userDetails.getEmail(),*/
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username exists already!"));
        }

        /*if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email exists already!"));
        }*/

        User user = new User(signUpRequest.getUsername(),
                /*signUpRequest.getEmail(),*/
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getFirstname(), signUpRequest.getLastname() );

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(enumRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(enumRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "staff":
                        Role modRole = roleRepository.findByName(enumRole.ROLE_STAFF)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(enumRole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User signed up successfully!"));
    }
    /*
    @PostMapping("/resetPassword")
    public ResponseEntity<?> PasswordReset(HttpServletRequest request, @RequestParam("email") String email){
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetToken(user,token);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Password Reset!");
        mailMessage.setFrom("test@gmail.com");
        mailMessage.setText("To complete the password reset process, please click here: "
                + "http://localhost:8082/confirm-reset?token="+ token);
        emailService.sendEmail(mailMessage);


        return ResponseEntity.ok(new MessageResponse("Email sent successfully"));
    }*/
}
