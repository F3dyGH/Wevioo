package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.services.IUserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserManagementController {
    @Autowired
    IUserManagement userManagement;

    @GetMapping("/all-users")

    public List<User> getAllUsers(){
        return userManagement.getAllUsers();
    }

    @PutMapping("/update-role/{id}")
    public User updateRole(@PathVariable Long id ,@Valid @RequestBody User user){
        return userManagement.updateUserRole(id,user);
        /* ResponseEntity.ok("User Role Updated Successfully");*/
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userManagement.deleteUser(id);
        return ResponseEntity.ok("User deleted sucessfully");
    }
}
