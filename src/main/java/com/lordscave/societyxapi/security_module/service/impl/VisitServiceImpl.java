package com.lordscave.societyxapi.security_module.service.impl;

import com.lordscave.societyxapi.admin_module.dto.rsp.GeneralResponse;
import com.lordscave.societyxapi.core.entity.*;
import com.lordscave.societyxapi.core.entity.enums.VisitStatus;
import com.lordscave.societyxapi.core.exceptions.ResourceNotFoundException;
import com.lordscave.societyxapi.core.repository.*;
import com.lordscave.societyxapi.core.security.JwtService;
import com.lordscave.societyxapi.security_module.dto.req.CreateVisit;
import com.lordscave.societyxapi.security_module.dto.req.VIsitMailData;
import com.lordscave.societyxapi.security_module.dto.rsp.VisitDetailsResponse;
import com.lordscave.societyxapi.security_module.dto.rsp.VisitResponse;
import com.lordscave.societyxapi.security_module.service.interfaces.VisitService;
import com.lordscave.societyxapi.utils.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    private VisitRepo visitRepo;

    @Autowired
    private ResidentRepo residentRepo;

    @Autowired
    private SecurityRepo securityRepo;

    @Autowired
    private MailService mailService;

    @Autowired
    private JwtService jwtService;

    @Override
    public VisitResponse createVisit(CreateVisit createVisit) {
        log.info("createVisit {}",createVisit.getContactNumber());

        if(visitRepo.existsByContactNumber(createVisit.getContactNumber())) {
            log.info("createVisit {} already exists",createVisit.getContactNumber());
            throw new ResourceNotFoundException("createVisit " + createVisit.getContactNumber() + " already exists");
        }

        String permitCode = UUID.randomUUID().toString();

        log.info("createVisit permitCode {}",permitCode);

        Security security = securityRepo.findByFullName(createVisit.getSecurityName()).orElseThrow(() -> new ResourceNotFoundException("security_module " + createVisit.getSecurityName() + " not found"));
        Resident resident = residentRepo.findByFullName(createVisit.getResidentName()).orElseThrow(() -> new ResourceNotFoundException("resident_module " + createVisit.getResidentName() + " not found"));
        Flat flat = resident.getFlat();

        Visit visit = Visit.builder()
                .name(createVisit.getName())
                .contactNumber(createVisit.getContactNumber())
                .email(createVisit.getEmail())
                .address(createVisit.getAddress())
                .purpose(createVisit.getPurpose())
                .vehicleNumber(createVisit.getVehicleNumber())
                .flat(flat)
                .resident(resident)
                .security(security)
                .gate(security.getGate())
                .permitCode(permitCode)
                .permitCodeExpiry(LocalDateTime.now().plusHours(createVisit.getVisitHours()))
                .status(VisitStatus.PENDING)
                .checkedIn(false)
                .checkedOut(false)
                .build();

        Visit savedVisit = visitRepo.save(visit);

        log.info("createVisit savedVisit {}",savedVisit.getName());

        User residentUser = savedVisit.getResident().getUser();
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(residentUser.getEmail())
                .password(residentUser.getPassword())
                .authorities("ROLE_RESIDENT")
                .build();


        Map<String, Object> approveClaims = Map.of("visitId", savedVisit.getId(), "action", "APPROVE");
        Map<String, Object> rejectClaims = Map.of("visitId", savedVisit.getId(), "action", "REJECT");


        String approveToken = jwtService.generateShortLivedToken(approveClaims, userDetails);
        String rejectToken = jwtService.generateShortLivedToken(rejectClaims, userDetails);


        VIsitMailData visitMailData = VIsitMailData.builder()
                .toEmail(residentUser.getEmail())
                .residentName(savedVisit.getResident().getFullName())
                .visitorName(savedVisit.getName())
                .purpose(savedVisit.getPurpose())
                .contactNumber(savedVisit.getContactNumber())
                .createdAt(savedVisit.getCreatedAt())
                .build();


        mailService.sendVisitMail(visitMailData, approveToken, rejectToken);



        return VisitResponse.builder()
                .message("Visit created successfully Please wait for approval")
                .id(savedVisit.getId())
                .name(savedVisit.getName())
                .phoneNumber(savedVisit.getContactNumber())
                .email(savedVisit.getEmail())
                .address(savedVisit.getAddress())
                .purpose(savedVisit.getPurpose())
                .vehicleNumber(savedVisit.getVehicleNumber())
                .visitStatus(savedVisit.getStatus().name())
                .permitCode(savedVisit.getPermitCode())
                .permitCodeExpiry(savedVisit.getPermitCodeExpiry())
                .checkedIn(savedVisit.isCheckedIn())
                .checkInTime(savedVisit.getCheckInTime())
                .checkedOut(savedVisit.isCheckedOut())
                .checkOutTime(savedVisit.getCheckOutTime())
                .flatId(savedVisit.getFlat().getId())
                .flatNo(savedVisit.getFlat().getFlatNo())
                .residentUserId(savedVisit.getResident().getUser().getId())
                .residentName(savedVisit.getResident().getFullName())
                .securityUserId(savedVisit.getSecurity().getUser().getId())
                .securityName(savedVisit.getSecurity().getFullName())
                .gateId(savedVisit.getGate().getId())
                .gateName(savedVisit.getGate().getGateName())
                .createdAt(savedVisit.getCreatedAt())
                .updatedAt(savedVisit.getUpdatedAt())
                .build();
    }

    @Override
    public List<VisitDetailsResponse> getAllVisit() {
        return visitRepo.findAll().stream().map(visit -> VisitDetailsResponse.builder()
                .id(visit.getId())
                .name(visit.getName())
                .phoneNumber(visit.getContactNumber())
                .email(visit.getEmail())
                .address(visit.getAddress())
                .purpose(visit.getPurpose())
                .vehicleNumber(visit.getVehicleNumber())
                .visitStatus(visit.getStatus().name())
                .permitCode(visit.getPermitCode())
                .permitCodeExpiry(visit.getPermitCodeExpiry())
                .checkedIn(visit.isCheckedIn())
                .checkInTime(visit.getCheckInTime())
                .checkedOut(visit.isCheckedOut())
                .checkOutTime(visit.getCheckOutTime())
                .flatId(visit.getFlat().getId())
                .flatNo(visit.getFlat().getFlatNo())
                .residentUserId(visit.getResident().getUser().getId())
                .residentName(visit.getResident().getFullName())
                .securityUserId(visit.getSecurity().getUser().getId())
                .securityName(visit.getSecurity().getFullName())
                .gateId(visit.getGate().getId())
                .gateName(visit.getGate().getGateName())
                .createdAt(visit.getCreatedAt())
                .build()).collect(Collectors.toList());
    }

    @Override
    public VisitDetailsResponse getVisit(Long id) {
        Visit visit = visitRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Visit not found with ID: " + id));
        return VisitDetailsResponse.builder()
                .id(visit.getId())
                .name(visit.getName())
                .phoneNumber(visit.getContactNumber())
                .email(visit.getEmail())
                .address(visit.getAddress())
                .purpose(visit.getPurpose())
                .vehicleNumber(visit.getVehicleNumber())
                .visitStatus(visit.getStatus().name())
                .permitCode(visit.getPermitCode())
                .permitCodeExpiry(visit.getPermitCodeExpiry())
                .checkedIn(visit.isCheckedIn())
                .checkInTime(visit.getCheckInTime())
                .checkedOut(visit.isCheckedOut())
                .checkOutTime(visit.getCheckOutTime())
                .flatId(visit.getFlat().getId())
                .flatNo(visit.getFlat().getFlatNo())
                .residentUserId(visit.getResident().getUser().getId())
                .residentName(visit.getResident().getFullName())
                .securityUserId(visit.getSecurity().getUser().getId())
                .securityName(visit.getSecurity().getFullName())
                .gateId(visit.getGate().getId())
                .gateName(visit.getGate().getGateName())
                .createdAt(visit.getCreatedAt())
                .build();
    }

    @Override
    public GeneralResponse deleteVisit(Long id) {
        Visit visit = visitRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Visit not found with ID: " + id));
        visit.setDeleted(true);
        visitRepo.save(visit);
        return GeneralResponse.builder().message("Visit deleted successfully").id(visit.getId()).build();
    }


    @Override
    public GeneralResponse checkIn(Long visitId) {
        Visit visit = visitRepo.findById(visitId)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found with ID: " + visitId));

        if(!(visit.getStatus() == VisitStatus.APPROVED) || visit.isCheckedIn() || visit.isCheckedOut() || visit.getPermitCodeExpiry().isBefore(LocalDateTime.now())) {
            return GeneralResponse.builder()
                    .message("You cannot check-in this visit. Please contact the security team.")
                    .build();
        }

        visit.setCheckInTime(LocalDateTime.now());
        visit.setCheckedIn(true);


        if (visit.getPermitCode() == null || visit.getPermitCodeExpiry() == null || visit.getPermitCodeExpiry().isBefore(LocalDateTime.now())) {
            String newPermitCode = UUID.randomUUID().toString();
            visit.setPermitCode(newPermitCode);
            visit.setPermitCodeExpiry(LocalDateTime.now().plusHours(4));
        }

        visitRepo.save(visit);

        VIsitMailData visitMailData = VIsitMailData.builder()
                .visitId(visit.getId())
                .toEmail(visit.getEmail())
                .residentName(visit.getResident().getFullName())
                .flatNo(visit.getFlat().getFlatNo())
                .visitorName(visit.getName())
                .purpose(visit.getPurpose())
                .contactNumber(visit.getContactNumber())
                .createdAt(visit.getCheckInTime())
                .build();
        try {
            mailService.sendVisitingPassToVisitor(visitMailData);
        } catch (Exception e) {
            log.error("Failed to send visiting pass email to visitor for visitId: {}", visitId, e);
        }

        log.info("Check-in successful for visitId: {}", visitId);
        return GeneralResponse.builder()
                .message("Check-in successful. Your visiting pass has been sent to your email.")
                .id(visit.getId())
                .build();
    }

    @Override
    public GeneralResponse checkOut(Long visitId) {
        Visit visit = visitRepo.findById(visitId).orElseThrow(() -> new IllegalArgumentException("Visit not found with ID: " + visitId));
        visit.setCheckOutTime(LocalDateTime.now());
        visit.setCheckedOut(true);
        visitRepo.save(visit);
        return GeneralResponse.builder().message("Visit checked out successfully").id(visit.getId()).build();
    }
}
