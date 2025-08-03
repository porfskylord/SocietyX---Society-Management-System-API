package com.lordscave.societyxapi.auth_module.controller;

import com.lordscave.societyxapi.auth_module.dto.req.ForgotPassword;
import com.lordscave.societyxapi.auth_module.dto.req.LoginRequest;
import com.lordscave.societyxapi.auth_module.dto.rsp.LoginResponse;
import com.lordscave.societyxapi.auth_module.service.interfaces.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "AUTHENTICATION MODULE" , description = "Authentication Api")
public class AuthController {

    @Autowired private AuthService authService;

    //-----------------------------------------------LOGIN--------------------------------------------------------------

    @Operation(summary = "Login user", tags = "AUTHENTICATION MODULE")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request)
    { return ResponseEntity.ok(authService.loginUser(request)); }

    //-----------------------------------------------BEFORE LOGIN-------------------------------------------------------

    @Operation(summary = "Forgot password" , tags = "AUTHENTICATION MODULE")
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPassword request)
    { return authService.handleForgotPassword(request); }

    //-----------------------------------------------AFTER LOGIN--------------------------------------------------------

    @Operation(summary = "Request reset password" , tags = "AUTHENTICATION MODULE")
    @PutMapping("/request-reset-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> requestResetPassword()
    { return authService.requestResetPassword(); }

    //-----------------------------------------------LOGOUT--------------------------------------------------------------

    @Operation(summary = "Reset password" , tags = "AUTHENTICATION MODULE")
    @DeleteMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> logout()
    { return ResponseEntity.ok(authService.logoutUser());}

}
