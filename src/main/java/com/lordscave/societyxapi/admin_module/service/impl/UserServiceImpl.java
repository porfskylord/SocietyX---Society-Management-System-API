package com.lordscave.societyxapi.admin_module.service.impl;

import com.lordscave.societyxapi.admin_module.dto.req.CreateUser;
import com.lordscave.societyxapi.admin_module.dto.req.UpdateUser;
import com.lordscave.societyxapi.admin_module.dto.rsp.GeneralResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.UserDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.UserResponse;
import com.lordscave.societyxapi.admin_module.service.interfaces.UserService;
import com.lordscave.societyxapi.core.entity.*;
import com.lordscave.societyxapi.core.entity.enums.UserStatus;
import com.lordscave.societyxapi.core.exceptions.BadRequestException;
import com.lordscave.societyxapi.core.exceptions.ConflictException;
import com.lordscave.societyxapi.core.exceptions.ResourceNotFoundException;
import com.lordscave.societyxapi.core.repository.*;
import com.lordscave.societyxapi.utils.EmailVerificationService;
import com.lordscave.societyxapi.utils.MailService;
import com.lordscave.societyxapi.core.entity.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SocietyRepo societyRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailVerificationService emailVerificationService;

    @Autowired
    private MailService mailService;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private ResidentRepo residentRepo;

    @Autowired
    private SecurityRepo securityRepo;


    @Override
    @Transactional
    public UserResponse createUser(CreateUser request) {
        long start = System.currentTimeMillis();
        log.info("Creating user with email: {}", request.getEmail());

        if (userRepo.existsByEmail(request.getEmail())) {
            log.warn("Email already in use: {}", request.getEmail());
            throw new ConflictException("Email already in use");
        }

        try {
            Society society = societyRepo.findById(request.getSocietyId())
                    .orElseThrow(() ->{
                        log.error("Society not found with ID: {}", request.getSocietyId());
                        return new ResourceNotFoundException("Society not found with ID: " + request.getSocietyId());
                    });

            User user = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .phone(request.getPhone())
                    .role(request.getRole())
                    .society(society)
                    .status(UserStatus.PENDING)
                    .isOnline(false)
                    .build();

            User savedUser = userRepo.save(user);
            String verificationToken = emailVerificationService.createVerificationToken(savedUser);
            mailService.sendVerificationMail(request.getEmail(), verificationToken);
            String message = "User created successfully. Please verify your email.";
            log.info("User created and verification email sent: {}", request.getEmail());

            log.info("createUser() completed in {} ms", System.currentTimeMillis() - start);
            return UserResponse.builder()
                    .message(message)
                    .id(savedUser.getId())
                    .email(savedUser.getEmail())
                    .phone(savedUser.getPhone())
                    .role(String.valueOf(savedUser.getRole()))
                    .status(String.valueOf(savedUser.getStatus()))
                    .societyId(savedUser.getSociety().getId())
                    .societyName(savedUser.getSociety().getName())
                    .createdAt(savedUser.getCreatedAt())
                    .updatedAt(savedUser.getUpdatedAt())
                    .build();

        } catch (DataIntegrityViolationException e) {
            log.error("Database error during user registration: {}", e.getMessage());
            throw new RuntimeException("Registration failed due to a database error");
        } catch (Exception e) {
            log.error("Unexpected error during user registration: {}", e.getMessage(), e);
            throw new RuntimeException("Registration failed. Please try again later.");
        }
    }


    @Override
    public List<UserDetailsResponse> getAllUsers() {
        log.info("getAllUsers");
        return userRepo.findAll().stream().map(user -> UserDetailsResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(String.valueOf(user.getRole()))
                .status(String.valueOf(user.getStatus()))
                .societyId(user.getSociety().getId())
                .societyName(user.getSociety().getName())
                .createdAt(user.getCreatedAt())
                .build())
                .toList();
    }

    @Override
    public UserDetailsResponse getUser(Long id) {
        log.info("getUser {}", id);
        User user = userRepo.findById(id).orElseThrow(() ->{
            log.error("User not found with ID: {}", id);
            return new ResourceNotFoundException("User not found with ID: " + id);
        });
        return UserDetailsResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(String.valueOf(user.getRole()))
                .status(String.valueOf(user.getStatus()))
                .societyId(user.getSociety().getId())
                .societyName(user.getSociety().getName())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UpdateUser request) {
        User user = userRepo.findById(id).orElseThrow(() ->{
            log.error("User not found with ID: {}", id);
            return new ResourceNotFoundException("User not found with ID: " + id);
        });
        String message = "User updated successfully";
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setStatus(UserStatus.PENDING);
        String verificationToken = emailVerificationService.createVerificationToken(user);

        mailService.sendVerificationMail(request.getEmail(), verificationToken);

        message = "User updated successfully. Please verify your email.";

        user.setRole(request.getRole());
        user.setStatus(request.getStatus());
        User savedUser = userRepo.save(user);

        return UserResponse.builder()
                .message(message)
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .role(String.valueOf(savedUser.getRole()))
                .status(String.valueOf(savedUser.getStatus()))
                .societyId(savedUser.getSociety().getId())
                .societyName(savedUser.getSociety().getName())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();

    }

    @Override
    @Transactional
    public GeneralResponse deleteUser(Long id) {

        User user = userRepo.findById(id).orElseThrow(() ->{
            log.error("User not found with ID: {}", id);
            return new ResourceNotFoundException("User not found with ID: " + id);
        });
        if (user.getRole() == Role.ADMIN) {
            Admin admin = adminRepo.findByUserId(user.getId()).orElseThrow(() ->{
                log.error("Admin not found with User ID: {}", id);
                return new ResourceNotFoundException("Admin not found with User ID: " + id);
            });
            admin.setDeleted(true);
            adminRepo.save(admin);
        }else if (user.getRole() == Role.SECURITY) {
            Security security = securityRepo.findByUserId(user.getId()).orElseThrow(() ->{
                log.error("Security not found with User ID: {}", id);
                return new ResourceNotFoundException("Security not found with User ID: " + id);
            });
            security.setDeleted(true);
            securityRepo.save(security);
        }else if (user.getRole() == Role.RESIDENT) {
            Resident resident = residentRepo.findByUserId(user.getId()).orElseThrow(() -> {log.error("Resident not found with User ID: {}", id);
                return new ResourceNotFoundException("Resident not found with User ID: " + id);
            });
            resident.setDeleted(true);
            residentRepo.save(resident);
        }else {
            log.error("User role not found with User ID: {}", id);
            throw new BadRequestException("User role not found with User ID: " + id);
        }

        user.setDeleted(true);
        userRepo.save(user);

        return GeneralResponse.builder()
                .message("User deleted successfully")
                .id(user.getId())
                .build();
    }

    @Override
    @Transactional
    public UserResponse patchUser(Long id, UpdateUser request) {
        User user = userRepo.findById(id).orElseThrow(() ->{
            log.error("User not found with ID: {}", id);
            return new ResourceNotFoundException("User not found with ID: " + id);
        });
        String message = "User updated successfully";
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
            user.setStatus(UserStatus.PENDING);
            String verificationToken = emailVerificationService.createVerificationToken(user);
            mailService.sendVerificationMail(request.getEmail(), verificationToken);
            message = "User updated successfully. Please verify your email.";
        }
        if (request.getRole() != null) user.setRole(request.getRole());
        if (request.getStatus() != null) user.setStatus(request.getStatus());

        User savedUser = userRepo.save(user);

        return UserResponse.builder()
                .message(message)
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .role(String.valueOf(savedUser.getRole()))
                .status(String.valueOf(savedUser.getStatus()))
                .societyId(savedUser.getSociety().getId())
                .societyName(savedUser.getSociety().getName())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();
    }

}
