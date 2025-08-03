package com.lordscave.societyxapi.security_module.service.impl;

import com.lordscave.societyxapi.core.entity.Flat;
import com.lordscave.societyxapi.core.entity.Resident;
import com.lordscave.societyxapi.core.entity.User;
import com.lordscave.societyxapi.core.exceptions.ResourceNotFoundException;
import com.lordscave.societyxapi.core.repository.FlatRepo;
import com.lordscave.societyxapi.core.repository.ResidentRepo;
import com.lordscave.societyxapi.core.repository.UserRepo;
import com.lordscave.societyxapi.security_module.dto.rsp.FlatDetailResponse;
import com.lordscave.societyxapi.security_module.dto.rsp.FlatResidentDetailsResponse;
import com.lordscave.societyxapi.security_module.dto.rsp.ResidentDetailResponse;
import com.lordscave.societyxapi.security_module.service.interfaces.FlatResidentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class FlatResidentServiceImpl implements FlatResidentService {

    @Autowired
    private FlatRepo flatRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ResidentRepo residentRepo;

    @Override
    public List<FlatResidentDetailsResponse> getAllFlatResidentDetails() {
        return flatRepo.findAll().stream()
                .map(flat -> {
                    if (Boolean.TRUE.equals(flat.getIsOccupied())) {
                        Resident resident = flat.getResident();

                        return FlatResidentDetailsResponse.builder()
                                .flatId(flat.getId())
                                .flatNo(flat.getFlatNo())
                                .isOccupied(flat.getIsOccupied())
                                .residentUserId(resident.getUser().getId())
                                .residentId(resident.getId())
                                .residentName(resident.getFullName())
                                .residentEmail(resident.getUser().getEmail())
                                .residentPhoneNo(resident.getUser().getPhone())
                                .ResidentStatus(resident.getUser().getStatus())
                                .build();
                    } else {
                        return FlatResidentDetailsResponse.builder()
                                .flatId(flat.getId())
                                .flatNo(flat.getFlatNo())
                                .isOccupied(flat.getIsOccupied())
                                .build();
                    }
                })
                .toList();
    }

    @Override
    public FlatResidentDetailsResponse getFlatResidentDetailsByFlatId(Long id) {
        Flat flat = flatRepo.findById(id).orElseThrow(() ->{
            log.error("Flat not found with id: " + id);
            return new ResourceNotFoundException("Flat not found with id: " + id);
        });

        if (Boolean.TRUE.equals(flat.getIsOccupied())) {
            Resident resident = flat.getResident();

            return FlatResidentDetailsResponse.builder()
                    .flatId(flat.getId())
                    .flatNo(flat.getFlatNo())
                    .isOccupied(flat.getIsOccupied())
                    .residentUserId(resident.getUser().getId())
                    .residentId(resident.getId())
                    .residentName(resident.getFullName())
                    .residentEmail(resident.getUser().getEmail())
                    .residentPhoneNo(resident.getUser().getPhone())
                    .ResidentStatus(resident.getUser().getStatus())
                    .build();
        } else {
            return FlatResidentDetailsResponse.builder()
                    .flatId(flat.getId())
                    .flatNo(flat.getFlatNo())
                    .isOccupied(flat.getIsOccupied())
                    .build();
        }
    }

    @Override
    public FlatResidentDetailsResponse getFlatResidentDetailsByResidentUserId(Long id) {
        User user = userRepo.findById(id).orElseThrow(() ->{
            log.error("User not found with id: " + id);
            return new ResourceNotFoundException("User not found with id: " + id);
        });
        Resident resident = residentRepo.findByUserId(id).orElseThrow(() ->{
            log.error("Resident not found with user id: " + id);
            return new ResourceNotFoundException("Resident not found with user id: " + id);
        });
        Flat flat = resident.getFlat();


        if (Boolean.TRUE.equals(flat.getIsOccupied())) {
            return FlatResidentDetailsResponse.builder()
                    .flatId(flat.getId())
                    .flatNo(flat.getFlatNo())
                    .isOccupied(flat.getIsOccupied())
                    .residentUserId(user.getId())
                    .residentId(resident.getId())
                    .residentName(resident.getFullName())
                    .residentEmail(user.getEmail())
                    .residentPhoneNo(user.getPhone())
                    .ResidentStatus(user.getStatus())
                    .build();
        } else {
            return FlatResidentDetailsResponse.builder()
                    .flatId(flat.getId())
                    .flatNo(flat.getFlatNo())
                    .isOccupied(flat.getIsOccupied())
                    .build();
        }
    }

    @Override
    public FlatResidentDetailsResponse getFlatResidentDetailsByFlatNo(String flatNo) {
        Flat flat = flatRepo.findByFlatNo(flatNo).orElseThrow(() ->{
            log.error("Flat not found with flat no: " + flatNo);
            return new ResourceNotFoundException("Flat not found with flat no: " + flatNo);
        });
        if (Boolean.TRUE.equals(flat.getIsOccupied())) {
            Resident resident = flat.getResident();
            User user = resident.getUser();

            return FlatResidentDetailsResponse.builder()
                    .flatId(flat.getId())
                    .flatNo(flat.getFlatNo())
                    .isOccupied(flat.getIsOccupied())
                    .residentUserId(user.getId())
                    .residentId(resident.getId())
                    .residentName(resident.getFullName())
                    .residentEmail(user.getEmail())
                    .residentPhoneNo(user.getPhone())
                    .ResidentStatus(user.getStatus())
                    .build();
        } else {
            return FlatResidentDetailsResponse.builder()
                    .flatId(flat.getId())
                    .flatNo(flat.getFlatNo())
                    .isOccupied(flat.getIsOccupied())
                    .build();
        }
    }

    @Override
    public FlatResidentDetailsResponse getFlatResidentDetailsByResidentName(String residentName) {
        Resident resident = residentRepo.findByFullName(residentName).orElseThrow(() ->{
            log.error("Resident not found with name: " + residentName);
            return new ResourceNotFoundException("Resident not found with name: " + residentName);
        });
        User user = resident.getUser();
        Flat flat = resident.getFlat();

        if (Boolean.TRUE.equals(flat.getIsOccupied())) {
            return FlatResidentDetailsResponse.builder()
                    .flatId(flat.getId())
                    .flatNo(flat.getFlatNo())
                    .isOccupied(flat.getIsOccupied())
                    .residentUserId(user.getId())
                    .residentId(resident.getId())
                    .residentName(resident.getFullName())
                    .residentEmail(user.getEmail())
                    .residentPhoneNo(user.getPhone())
                    .ResidentStatus(user.getStatus())
                    .build();
        } else {
            return FlatResidentDetailsResponse.builder()
                    .flatId(flat.getId())
                    .flatNo(flat.getFlatNo())
                    .isOccupied(flat.getIsOccupied())
                    .build();
        }
    }

    @Override
    public List<FlatDetailResponse> getAllFlatDetails() {
        return flatRepo.findAll().stream()
                .filter(flat -> Boolean.TRUE.equals(flat.getIsOccupied())
                        || "SocietyManagerOffice".equals(flat.getFlatNo()))
                .map(flat -> FlatDetailResponse.builder()
                        .flatId(flat.getId())
                        .flatNo(flat.getFlatNo())
                        .build())
                .toList();
    }

    @Override
    public List<ResidentDetailResponse> getAllResidentDetails() {
        return residentRepo.findAll().stream()
                .map(resident -> ResidentDetailResponse.builder()
                        .residentUserId(resident.getId())
                        .residentName(resident.getFullName())
                        .build())
                .toList();
    }


}
