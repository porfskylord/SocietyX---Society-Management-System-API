package com.lordscave.societyxapi.admin_module.controller;

import com.lordscave.societyxapi.admin_module.dto.req.CreateUser;
import com.lordscave.societyxapi.admin_module.dto.req.UpdateUser;
import com.lordscave.societyxapi.admin_module.dto.rsp.GeneralResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.UserDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.UserResponse;
import com.lordscave.societyxapi.admin_module.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "ADMIN MODULE - User Management", description = "User Management APIs")
public class UserController {

    @Autowired private UserService userService;

    //-----------------------------------------------USER MANAGEMENT----------------------------------------------------

    @Operation(summary = "Create user" , tags = "ADMIN MODULE - User Management")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUser request)
    { return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED); }

    @Operation(summary = "Get all users" , tags = "ADMIN MODULE - User Management")
    @GetMapping
    public ResponseEntity<List<UserDetailsResponse>> getAllUsers()
    { return ResponseEntity.ok(userService.getAllUsers()); }

    @Operation(summary = "Get user details" , tags = "ADMIN MODULE - User Management")
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsResponse> getUser(@PathVariable Long id)
    { return ResponseEntity.ok(userService.getUser(id)); }

    @Operation(summary = "Update user details" , tags = "ADMIN MODULE - User Management")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,@RequestBody UpdateUser request)
    { return ResponseEntity.ok(userService.updateUser(id,request)); }

    @Operation(summary = "Update user details", tags = "ADMIN MODULE - User Management")
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> patchUser(@PathVariable Long id,@RequestBody UpdateUser request)
    { return ResponseEntity.ok(userService.patchUser(id,request)); }

    @Operation(summary = "Delete user", tags = "ADMIN MODULE - User Management")
    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> deleteUser(@PathVariable Long id)
    { return ResponseEntity.ok(userService.deleteUser(id)); }

}
