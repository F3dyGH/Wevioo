package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.services.IUserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")

public class UserManagementController {
    @Autowired
    IUserManagement userManagement;

    @GetMapping("/enabled-users")

    public ResponseEntity<List<User>> getAllEnabledUsers() {
        List<User> users = userManagement.getAllEnabledUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/disabled-users")
    public ResponseEntity<List<User>> getAllDisabledUsers() {
        List<User> users = userManagement.getAllDisabledUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update-role/{id}/roles/{idRole}")
    public User updateRole(@PathVariable Long id, @PathVariable Integer idRole) {
        return userManagement.updateUserRole(id, idRole);
    }

    @GetMapping("/find/{id}")
    public User findById(@PathVariable Long id) {
        return userManagement.getUserById(id);
    }

    @DeleteMapping("/delete-user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userManagement.deleteUser(id);
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<String> disableUser(@PathVariable Long id) {
        try {
            userManagement.disableUser(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<String> enableUser(@PathVariable Long id) {
        try {
            userManagement.enableUser(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
