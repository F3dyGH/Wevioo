package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.services.IUserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")

public class UserManagementController {
    @Autowired
    IUserManagement userManagement;

    @GetMapping("/enabled-users")

    public ResponseEntity<?> getAllEnabledUsers() {
        List<User> users = userManagement.getAllEnabledUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/disabled-users")
    public ResponseEntity<?> getAllDisabledUsers() {
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

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/disable/{id}")
    public ResponseEntity<?> disableUser(@PathVariable Long id) {
        try {
            userManagement.disableUser(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<?> enableUser(@PathVariable Long id) {
        try {
            userManagement.enableUser(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
