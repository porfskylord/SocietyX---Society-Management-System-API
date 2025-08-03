package com.lordscave.societyxapi.admin_module.service.impl;

import com.lordscave.societyxapi.admin_module.dto.req.UpdateAdminProfile;
import com.lordscave.societyxapi.admin_module.dto.rsp.AdminDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.AdminResponse;
import com.lordscave.societyxapi.admin_module.service.interfaces.AdminService;
import com.lordscave.societyxapi.core.entity.Admin;
import com.lordscave.societyxapi.core.exceptions.ResourceNotFoundException;
import com.lordscave.societyxapi.core.repository.AdminRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {


    @Autowired private AdminRepo adminRepo;

    @Override
    public AdminDetailsResponse getAdmin(Long userId) {

        log.info("Fetching admin_module details for user id: " + userId);

        if (adminRepo.findByUserId(userId).isEmpty()) {
            log.error("Admin not found which user id: " + userId);
            throw new ResourceNotFoundException("Admin not found which user id: " + userId);
        }

        Admin admin = adminRepo.findByUserId(userId).get();
        log.info("Fetched admin_module details for user id: " + userId);

        return AdminDetailsResponse.builder()
                .id(admin.getId())
                .userId(admin.getUser().getId())
                .email(admin.getUser().getEmail())
                .phone(admin.getUser().getPhone())
                .fullName(admin.getFullName())
                .designation(admin.getDesignation())
                .imageUrl(admin.getImageUrl())
                .createdAt(admin.getCreatedAt())
                .build();

    }

    @Override
    @Transactional
    public AdminResponse updateAdmin(Long userId, UpdateAdminProfile request) {

        if (adminRepo.findByUserId(userId).isEmpty()) {
            log.error("Admin not found which user id: " + userId);
            throw new ResourceNotFoundException("Admin not found which user id: " + userId);
        }

        Admin admin = adminRepo.findByUserId(userId).get();
        admin.setFullName(request.getFullName());
        admin.setDesignation(request.getDesignation());
        admin.setImageUrl(request.getImageUrl());
        adminRepo.save(admin);

        log.info("Admin updated successfully for user id: " + userId);
        return AdminResponse.builder()
                .message("Admin updated successfully")
                .id(admin.getId())
                .userId(admin.getUser().getId())
                .email(admin.getUser().getEmail())
                .phone(admin.getUser().getPhone())
                .fullName(admin.getFullName())
                .designation(admin.getDesignation())
                .imageUrl(admin.getImageUrl())
                .createdAt(admin.getCreatedAt())
                .updatedAt(admin.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public AdminResponse patchAdmin(Long userId, UpdateAdminProfile request) {
        if (adminRepo.findByUserId(userId).isEmpty()) {
            log.error("Admin not found which user id: " + userId);
            throw new ResourceNotFoundException("Admin not found which user id: " + userId);
        }

        Admin admin = adminRepo.findByUserId(userId).get();
        if (request.getFullName() != null) admin.setFullName(request.getFullName());
        if (request.getDesignation() != null) admin.setDesignation(request.getDesignation());
        if (request.getImageUrl() != null) admin.setImageUrl(request.getImageUrl());
        adminRepo.save(admin);

        log.info("Admin updated successfully for user id: " + userId);
        return AdminResponse.builder()
                .message("Admin updated successfully")
                .id(admin.getId())
                .userId(admin.getUser().getId())
                .email(admin.getUser().getEmail())
                .phone(admin.getUser().getPhone())
                .fullName(admin.getFullName())
                .designation(admin.getDesignation())
                .imageUrl(admin.getImageUrl())
                .createdAt(admin.getCreatedAt())
                .updatedAt(admin.getUpdatedAt())
                .build();
    }
}
