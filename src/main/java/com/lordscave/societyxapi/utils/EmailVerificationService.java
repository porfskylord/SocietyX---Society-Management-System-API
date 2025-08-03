package com.lordscave.societyxapi.utils;

import com.lordscave.societyxapi.core.entity.*;
import com.lordscave.societyxapi.core.entity.enums.Role;
import com.lordscave.societyxapi.core.entity.enums.UserStatus;
import com.lordscave.societyxapi.core.entity.enums.VerificationStatus;
import com.lordscave.societyxapi.core.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class EmailVerificationService {

    @Autowired
    private UserEmailVerificationRepo userEmailVerificationRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private ResidentRepo residentRepo;

    @Autowired
    private SecurityRepo securityRepo;

    @Autowired
    private MailService mailService;

    @Autowired
    private GateRepo gateRepo;

    public String createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        UserEmailVerification userEmailVerification = UserEmailVerification.builder()
                .token(token)
                .user(user)
                .expiresAt(LocalDateTime.now().plusHours(24))
                .build();

        userEmailVerificationRepo.save(userEmailVerification);

        return token;
    }

    public VerificationStatus verifyToken(String token) {
        UserEmailVerification userEmailVerification = userEmailVerificationRepo.findByToken(token);

        if (userEmailVerification == null) {
            return VerificationStatus.TOKEN_NOT_FOUND;
        }

        if (userEmailVerification.getExpiresAt().isBefore(LocalDateTime.now())) {
            return VerificationStatus.TOKEN_EXPIRED;
        }

        User user = userEmailVerification.getUser();
        user.setStatus(UserStatus.ACTIVE);

        if (user.getRole().equals(Role.ADMIN)) {
            Admin admin = Admin.builder()
                    .user(user)
                    .build();
            adminRepo.save(admin);
        } else if (user.getRole().equals(Role.RESIDENT)) {
            Resident resident = Resident.builder()
                    .user(user)
                    .build();
            residentRepo.save(resident);
        } else if (user.getRole().equals(Role.SECURITY)) {
            Security security = Security.builder()
                    .user(user)
                    .gate(gateRepo.findAll().get(0))
                    .build();
            securityRepo.save(security);
        }

        userRepo.save(user);
        userEmailVerificationRepo.delete(userEmailVerification);

        return VerificationStatus.SUCCESS;
    }
}
