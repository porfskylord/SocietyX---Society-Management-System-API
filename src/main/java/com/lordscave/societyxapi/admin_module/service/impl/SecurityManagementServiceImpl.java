package com.lordscave.societyxapi.admin_module.service.impl;

import com.lordscave.societyxapi.admin_module.dto.req.UpdateSecurity;
import com.lordscave.societyxapi.admin_module.dto.rsp.GeneralResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.SecurityDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.SecurityResponse;
import com.lordscave.societyxapi.admin_module.service.interfaces.SecurityManagementService;
import com.lordscave.societyxapi.core.entity.Gate;
import com.lordscave.societyxapi.core.entity.Security;
import com.lordscave.societyxapi.core.exceptions.ResourceNotFoundException;
import com.lordscave.societyxapi.core.repository.GateRepo;
import com.lordscave.societyxapi.core.repository.SecurityRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SecurityManagementServiceImpl implements SecurityManagementService {

    @Autowired
    private SecurityRepo securityRepo;

    @Autowired private GateRepo gateRepo;


    @Override
    public List<SecurityDetailsResponse> getAllSecurity() {
        log.info("Getting all security_module");
        return securityRepo.findAll().stream().map(security -> SecurityDetailsResponse.builder()
                .id(security.getId())
                .fullName(security.getFullName())
                .age(security.getAge())
                .gender(security.getGender())
                .dateOfBirth(security.getDateOfBirth())
                .imageUrl(security.getImageUrl())
                .userId(security.getUser().getId())
                .email(security.getUser().getEmail())
                .phone(security.getUser().getPhone())
                .shift(security.getShift())
                .gateId(security.getGate().getId())
                .gateName(security.getGate().getGateName())
                .societyId(security.getGate().getSociety().getId())
                .societyName(security.getGate().getSociety().getName())
                .createdAt(security.getCreatedAt())
                .build()).toList();
    }

    @Override
    public SecurityDetailsResponse getSecurity(Long userId) {
        log.info("Fetching security_module details for user id: " + userId);
        Security security = securityRepo.findByUserId(userId).orElseThrow(() ->{
            log.error("Security not found which user id: " + userId);
            return new ResourceNotFoundException("Security not found which user id: " + userId);
        });

        return SecurityDetailsResponse.builder()
                .id(security.getId())
                .fullName(security.getFullName())
                .age(security.getAge())
                .gender(security.getGender())
                .dateOfBirth(security.getDateOfBirth())
                .imageUrl(security.getImageUrl())
                .userId(security.getUser().getId())
                .email(security.getUser().getEmail())
                .phone(security.getUser().getPhone())
                .shift(security.getShift())
                .gateId(security.getGate().getId())
                .gateName(security.getGate().getGateName())
                .societyId(security.getGate().getSociety().getId())
                .societyName(security.getGate().getSociety().getName())
                .createdAt(security.getCreatedAt())
                .build();

    }

    @Override
    public SecurityResponse updateSecurity(Long userId, UpdateSecurity request) {
        log.info("Updating security_module details for security_module id: " + userId);

        Security security = securityRepo.findByUserId(userId).orElseThrow(() ->{
            log.error("Security not found which user id: " + userId);
            return new ResourceNotFoundException("Security not found which user id: " + userId);
        });

        Gate gate = gateRepo.findById(request.getGateId()).orElseThrow(() -> {
            log.error("Gate not found which id: " + request.getGateId());
            return new ResourceNotFoundException("Gate not found which id: " + request.getGateId());
        });

        security.setFullName(request.getFullName());
        security.setAge(request.getAge());
        security.setGender(request.getGender());
        security.setDateOfBirth(request.getDateOfBirth());
        security.setImageUrl(request.getImageUrl());
        security.setShift(request.getShift());
        security.setGate(gate);

        security = securityRepo.save(security);

        log.info("Security updated successfully");

        return SecurityResponse.builder()
                .message("Security updated successfully")
                .id(security.getId())
                .fullName(security.getFullName())
                .age(security.getAge())
                .gender(security.getGender())
                .dateOfBirth(security.getDateOfBirth())
                .imageUrl(security.getImageUrl())
                .userId(security.getUser().getId())
                .email(security.getUser().getEmail())
                .phone(security.getUser().getPhone())
                .shift(security.getShift())
                .gateId(security.getGate().getId())
                .gateName(security.getGate().getGateName())
                .societyId(security.getGate().getSociety().getId())
                .societyName(security.getGate().getSociety().getName())
                .createdAt(security.getCreatedAt())
                .updatedAt(security.getUpdatedAt())
                .build();



    }

    @Override
    public SecurityResponse patchSecurity(Long userId, UpdateSecurity request) {
        log.info("Updating security_module details for security_module id: " + userId);

        Security security = securityRepo.findByUserId(userId).orElseThrow(() ->{
            log.error("Security not found which user id: " + userId);
            return new ResourceNotFoundException("Security not found which user id: " + userId);
        });

        Gate gate = gateRepo.findById(request.getGateId()).orElseThrow(() -> {
            log.error("Gate not found which id: " + request.getGateId());
            return new ResourceNotFoundException("Gate not found which id: " + request.getGateId());
        });

        if (request.getFullName() != null) security.setFullName(request.getFullName());
        if (request.getAge() != null) security.setAge(request.getAge());
        if (request.getGender() != null) security.setGender(request.getGender());
        if (request.getDateOfBirth() != null) security.setDateOfBirth(request.getDateOfBirth());
        if (request.getImageUrl() != null) security.setImageUrl(request.getImageUrl());
        if (request.getShift() != null) security.setShift(request.getShift());
        if (request.getGateId() != null) security.setGate(gate);

        security = securityRepo.save(security);

        log.info("Security updated successfully");

        return SecurityResponse.builder()
                .message("Security updated successfully")
                .id(security.getId())
                .fullName(security.getFullName())
                .age(security.getAge())
                .gender(security.getGender())
                .dateOfBirth(security.getDateOfBirth())
                .imageUrl(security.getImageUrl())
                .userId(security.getUser().getId())
                .email(security.getUser().getEmail())
                .phone(security.getUser().getPhone())
                .shift(security.getShift())
                .gateId(security.getGate().getId())
                .gateName(security.getGate().getGateName())
                .societyId(security.getGate().getSociety().getId())
                .societyName(security.getGate().getSociety().getName())
                .createdAt(security.getCreatedAt())
                .updatedAt(security.getUpdatedAt())
                .build();
    }

    @Override
    public GeneralResponse deleteSecurity(Long userId) {
        log.info("Deleting security_module details for security_module id: " + userId);
        Security security = securityRepo.findByUserId(userId).orElseThrow(() ->{
            log.error("Security not found which user id: " + userId);
            return new ResourceNotFoundException("Security not found which user id: " + userId);
        });
        securityRepo.delete(security);
        return GeneralResponse.builder()
                .message("Security deleted successfully")
                .id(security.getId())
                .build();
    }
}
