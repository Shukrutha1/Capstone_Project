package com.project.payroll.controller;

import com.project.payroll.dto.CreateUserRequest;
import com.project.payroll.dto.UpdateStatusRequest;
import com.project.payroll.dto.UserDTO;
import com.project.payroll.entity.User;
import com.project.payroll.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Get current user details", description = "Retrieves the details of the currently logged-in user")
    public ResponseEntity<List<User>> getUsersList(Principal principal) {
    	System.out.println("principal" + principal);
        return ResponseEntity.ok(userService.getUsers(principal.getName()));
    }
   
    @GetMapping("/me")
    @Operation(summary = "Get current user details", description = "Retrieves the details of the currently logged-in user")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
    	System.out.println("principal" + principal);
        return ResponseEntity.ok(userService.getCurrentUser(principal.getName()));
    }

    @PostMapping
    @Operation(summary = "Create new user", description = "Creates a new user with specified role (Admin/Employee)")
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user status", description = "Activates or deactivates a user account")
    public ResponseEntity<User> updateUserStatus(
            @PathVariable Long id, 
            @RequestBody @Valid UpdateStatusRequest request) {
        return ResponseEntity.ok(userService.updateUserStatus(id, request.isActive()));
    }
}
