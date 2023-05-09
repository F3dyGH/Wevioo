package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;
    @PreAuthorize("hasRole('USER')")
    @PutMapping(value ="/update/{id}",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public User updateUser(@ModelAttribute User user, @RequestParam(value = "file", required = false) MultipartFile file, @PathVariable Long id)  throws IOException{
        return userService.updateUser(id, user, file);
    }
    @PreAuthorize("hasRole('ADMIN')" + " || hasRole('USER')" + "|| hasRole('STAFF')")
    @PutMapping("/changePassword/{id}")
    public ResponseEntity<?> updateUser(@RequestParam String password, @PathVariable Long id) {
        userService.updateAdminPassword(id,password);
        return ResponseEntity.ok().build();
    }


}
