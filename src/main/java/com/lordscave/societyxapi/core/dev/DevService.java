package com.lordscave.societyxapi.core.dev;

import com.lordscave.societyxapi.core.entity.Admin;
import com.lordscave.societyxapi.core.entity.Society;
import com.lordscave.societyxapi.core.entity.User;
import com.lordscave.societyxapi.core.entity.enums.Role;
import com.lordscave.societyxapi.core.entity.enums.UserStatus;
import com.lordscave.societyxapi.core.exceptions.BadRequestException;
import com.lordscave.societyxapi.core.exceptions.ConflictException;
import com.lordscave.societyxapi.core.exceptions.ResourceNotFoundException;
import com.lordscave.societyxapi.core.repository.AdminRepo;
import com.lordscave.societyxapi.core.repository.SocietyRepo;
import com.lordscave.societyxapi.core.repository.UserRepo;
import com.lordscave.societyxapi.core.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DevService {

    @Autowired
    private JwtService jwtService;

    @Value("${dev.user.name}")
    private String username;

    @Value("${dev.user.password}")
    private String password;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SocietyRepo societyRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public DevResponseDto createAdmin(CreateAdminDto request) {

        if (userRepo.existsByEmail(request.getEmail())) {
            log.warn("Email already in use: {}", request.getEmail());
            throw new ConflictException("User already exists");
        }

        try {
            Society society = societyRepo.findById(request.getSocietyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Society not found with ID: " + request.getSocietyId()));

            User user = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .phone(request.getPhone())
                    .role(Role.ADMIN)
                    .society(society)
                    .status(UserStatus.ACTIVE)
                    .isOnline(false)
                    .build();

            User savedUser = userRepo.save(user);

            Admin admin = Admin.builder()
                    .user(savedUser)
                    .build();
            adminRepo.save(admin);

            return DevResponseDto.builder()
                    .message("Admin created successfully")
                    .id(savedUser.getId())
                    .email(savedUser.getEmail())
                    .phone(savedUser.getPhone())
                    .role(String.valueOf(savedUser.getRole()))
                    .societyId(savedUser.getSociety().getId())
                    .societyName(savedUser.getSociety().getName())
                    .status(String.valueOf(savedUser.getStatus()))
                    .createdAt(savedUser.getCreatedAt())
                    .build();




        }catch (DataIntegrityViolationException e) {
            log.error("Database error during user registration: {}", e.getMessage());
            throw new RuntimeException("Registration failed due to a database error");
        } catch (Exception e) {
            log.error("Unexpected error during user registration: {}", e.getMessage(), e);
            throw new RuntimeException("Registration failed. Please try again later.");
        }

    }

}
