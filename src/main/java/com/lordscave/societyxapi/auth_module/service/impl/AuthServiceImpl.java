package com.lordscave.societyxapi.auth_module.service.impl;

import com.lordscave.societyxapi.auth_module.dto.req.ForgotPassword;
import com.lordscave.societyxapi.auth_module.dto.req.LoginRequest;
import com.lordscave.societyxapi.auth_module.dto.rsp.LoginResponse;
import com.lordscave.societyxapi.auth_module.service.interfaces.AuthService;
import com.lordscave.societyxapi.core.entity.User;
import com.lordscave.societyxapi.core.entity.enums.UserStatus;
import com.lordscave.societyxapi.core.exceptions.BadRequestException;
import com.lordscave.societyxapi.core.exceptions.ResourceNotFoundException;
import com.lordscave.societyxapi.core.security.JwtService;
import com.lordscave.societyxapi.utils.MailService;
import com.lordscave.societyxapi.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lordscave.societyxapi.core.repository.UserRepo;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    @Transactional
    public LoginResponse loginUser(LoginRequest request) {
        log.info("Trying to login user with email: {}", request.getEmail());

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", request.getEmail());
                    return new ResourceNotFoundException("User not found");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Login failed: incorrect password for email {}", request.getEmail());
            throw new BadRequestException("Invalid email or password");
        }

        if (user.getStatus() == UserStatus.BLOCKED) {
            log.warn("Login failed: BLOCKED account - {}", request.getEmail());
            throw new BadRequestException("Your account has been blocked. Please contact admin_module.");
        }

        if (user.getStatus() == UserStatus.INACTIVE) {
            log.warn("Login failed: INACTIVE account - {}", request.getEmail());
            throw new BadRequestException("Your account is inactive. Please contact admin_module.");
        }

        boolean isFirstLogin = user.getLastLoginAt() == null;
        String loginMessage = "Login successful";

        if (isFirstLogin) {
            String resetToken = jwtService.generateToken(
                    org.springframework.security.core.userdetails.User
                            .withUsername(user.getEmail())
                            .password(user.getPassword())
                            .authorities("ROLE_" + user.getRole().name())
                            .build()
            );

            log.info("Sending reset password mail to {}", user.getEmail());
            mailService.sendResetPasswordMail(user.getEmail(), resetToken);
            loginMessage = "Welcome! Please reset your password. We've sent a reset link to your email.";
        }

        user.setLastLoginAt(LocalDateTime.now());
        user.setOnline(true);
        userRepo.save(user);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name())
                .build();

        String token = jwtService.generateToken(userDetails);

        log.info("Login successful for email: {}", request.getEmail());

        return LoginResponse.builder()
                .message(loginMessage)
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();

    }

    @Override
    @Transactional
    public ResponseEntity<?> logoutUser() {
        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new ResourceNotFoundException("User not found");
                });

        if (!user.isOnline()) {
            throw new BadRequestException("User is already logged out");
        }

        user.setLastLogout(LocalDateTime.now());
        user.setOnline(false);
        userRepo.save(user);

        log.info("Logout successful for email: {}", email);

        return ResponseEntity.ok(Map.of("message", "Logout successful"));
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {

        String email;
        try {
            email = jwtService.extractUsername(token);
        } catch (Exception e) {
            throw new BadRequestException("Invalid or expired reset token.");
        }


        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->{
                    log.error("No user found for reset token.");
                    return new ResourceNotFoundException("No user found for reset token.");
                });


        user.setPassword(passwordEncoder.encode(newPassword));

        userRepo.save(user);

        log.info("Password reset successful for user: {}", email);
    }

    @Override
    @Transactional
    public ResponseEntity<?> requestResetPassword() {
        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->{
                    log.error("User not found");
                    return new ResourceNotFoundException("User not found");
                });

        Map<String, Object> claims = Map.of("purpose", "reset-password");

        String token = jwtService.generateShortLivedToken(claims,
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .authorities("ROLE_" + user.getRole().name())
                        .build()
        );

        log.info("Sending reset password mail to {}", user.getEmail());

        mailService.sendResetPasswordMail(user.getEmail(), token);

        return ResponseEntity.ok(Map.of("message", "We've sent a reset link to your email."));
    }


    @Override
    @Transactional
    public ResponseEntity<?> handleForgotPassword(ForgotPassword request) {
        log.info("Received forgot password request for email: {}", request.getEmail());
        User user = userRepo.findByEmail(request.getEmail()).orElseThrow(() ->{
            log.error("User not found");
            return new ResourceNotFoundException("User not found");
        });

        if(user.getStatus() != UserStatus.ACTIVE) {
            log.warn("Login failed: account not active - {}", request.getEmail());
            throw new BadRequestException("User account is inactive or blocked");
        }

        Map<String, Object> claims = Map.of("purpose", "forgot-password");

        String token = jwtService.generateShortLivedToken(claims,
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .authorities("ROLE_" + user.getRole().name())
                        .build()
        );


        mailService.sendResetPasswordMail(user.getEmail(), token);

        log.info("Sent reset password mail to {}", user.getEmail());
        return ResponseEntity.ok(Map.of(
                "message", "If your email is registered with us, we've sent a reset link to your email."
        ));

    }
}
