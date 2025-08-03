package com.lordscave.societyxapi.resident_module.service.impl;

import com.lordscave.societyxapi.core.entity.Flat;
import com.lordscave.societyxapi.core.entity.Resident;
import com.lordscave.societyxapi.core.entity.Society;
import com.lordscave.societyxapi.core.exceptions.ResourceNotFoundException;
import com.lordscave.societyxapi.core.repository.FlatRepo;
import com.lordscave.societyxapi.core.repository.ResidentRepo;
import com.lordscave.societyxapi.resident_module.dto.req.UpdateResident;
import com.lordscave.societyxapi.resident_module.dto.rsp.ResidentDetailsResponse;
import com.lordscave.societyxapi.resident_module.dto.rsp.ResidentResponse;
import com.lordscave.societyxapi.resident_module.service.interfaces.ResidentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ResidentServiceImpl implements ResidentService {

    @Autowired
    private ResidentRepo residentRepo;

    @Autowired
    private FlatRepo flatRepo;

    @Override
    public ResidentDetailsResponse getResident(Long userId) {

        log.info("Fetching Resident details for user id: " + userId);

        Resident resident = residentRepo.findByUserId(userId).orElseThrow(() ->{
            log.error("Resident not found which user id: " + userId);
            return new ResourceNotFoundException("Resident not found which user id: " + userId);
        });

        Flat flat = resident.getFlat();
        Society society = (flat != null) ? flat.getSociety() : null;

        return ResidentDetailsResponse.builder()
                .id(resident.getId())
                .userId(resident.getUser().getId())
                .email(resident.getUser().getEmail())
                .phone(resident.getUser().getPhone())
                .fullName(resident.getFullName())
                .age(resident.getAge())
                .gender(resident.getGender())
                .dateOfBirth(resident.getDateOfBirth())
                .imageUrl(resident.getImageUrl())
                .flatId(flat != null ? flat.getId() : null)
                .flatNo(flat != null ? flat.getFlatNo() : null)
                .floorNo(flat != null ? flat.getFloorNo() : null)
                .flatType(flat != null ? flat.getFlatType() : null)
                .block(flat != null ? flat.getBlock() : null)
                .buildingNo(flat != null ? flat.getBuildingNo() : null)
                .societyId(society != null ? society.getId() : null)
                .societyName(society != null ? society.getName() : null)
                .createdAt(resident.getCreatedAt())
                .build();
    }

    @Override
    public ResidentResponse updateResident(Long userId, UpdateResident request) {

        log.info("Fetching Resident details for user id: " + userId);

        Resident resident = residentRepo.findByUserId(userId).orElseThrow(() ->{
            log.error("Resident not found which user id: " + userId);
            return new ResourceNotFoundException("Resident not found which user id: " + userId);
        });

        resident.setFullName(request.getFullName());
        resident.setAge(request.getAge());
        resident.setGender(request.getGender());
        resident.setDateOfBirth(request.getDateOfBirth());
        resident.setImageUrl(request.getImageUrl());

        Flat flat = flatRepo.findById(request.getFlatId()).orElseThrow(() ->{
            log.error("Flat not found which id: " + request.getFlatId());
            return new ResourceNotFoundException("Flat not found which id: " + request.getFlatId());
        });
        resident.setFlat(flat);

        resident = residentRepo.save(resident);

        flat.setIsOccupied(true);
        flatRepo.save(flat);

        return ResidentResponse.builder()
                .message("Resident updated successfully")
                .id(resident.getId())
                .userId(resident.getUser().getId())
                .email(resident.getUser().getEmail())
                .phone(resident.getUser().getPhone())
                .fullName(resident.getFullName())
                .age(resident.getAge())
                .gender(resident.getGender())
                .dateOfBirth(resident.getDateOfBirth())
                .imageUrl(resident.getImageUrl())
                .flatId(resident.getFlat().getId())
                .flatNo(resident.getFlat().getFlatNo())
                .floorNo(resident.getFlat().getFloorNo())
                .flatType(resident.getFlat().getFlatType())
                .buildingNo(resident.getFlat().getBuildingNo())
                .block(resident.getFlat().getBlock())
                .societyId(resident.getFlat().getSociety().getId())
                .societyName(resident.getFlat().getSociety().getName())
                .createdAt(resident.getCreatedAt())
                .updatedAt(resident.getUpdatedAt())
                .build();


    }

    @Override
    public ResidentResponse patchResident(Long userId, UpdateResident request) {
        log.info("Fetching Resident details for user id: " + userId);

        Resident resident = residentRepo.findByUserId(userId).orElseThrow(() ->{
            log.error("Resident not found which user id: " + userId);
            return new ResourceNotFoundException("Resident not found which user id: " + userId);
        });

        if (request.getFullName() != null) resident.setFullName(request.getFullName());
        if (request.getAge() != null) resident.setAge(request.getAge());
        if (request.getGender() != null) resident.setGender(request.getGender());
        if (request.getDateOfBirth() != null) resident.setDateOfBirth(request.getDateOfBirth());
        if (request.getImageUrl() != null) resident.setImageUrl(request.getImageUrl());
        if (request.getFlatId() != null) {
            Flat flat = flatRepo.findById(request.getFlatId()).get();
            resident.setFlat(flat);
        }

        resident = residentRepo.save(resident);

        return ResidentResponse.builder()
                .message("Resident updated successfully")
                .id(resident.getId())
                .userId(resident.getUser().getId())
                .email(resident.getUser().getEmail())
                .phone(resident.getUser().getPhone())
                .fullName(resident.getFullName())
                .age(resident.getAge())
                .gender(resident.getGender())
                .dateOfBirth(resident.getDateOfBirth())
                .imageUrl(resident.getImageUrl())
                .flatId(resident.getFlat().getId())
                .flatNo(resident.getFlat().getFlatNo())
                .floorNo(resident.getFlat().getFloorNo())
                .flatType(resident.getFlat().getFlatType())
                .buildingNo(resident.getFlat().getBuildingNo())
                .block(resident.getFlat().getBlock())
                .societyId(resident.getFlat().getSociety().getId())
                .societyName(resident.getFlat().getSociety().getName())
                .createdAt(resident.getCreatedAt())
                .updatedAt(resident.getUpdatedAt())
                .build();
    }

    @Override
    public List<ResidentDetailsResponse> getAllResident() {
        return residentRepo.findAll().stream()
                .map(resident -> {
                    Flat flat = resident.getFlat();
                    Society society = resident.getUser().getSociety();

                    return ResidentDetailsResponse.builder()
                            .id(resident.getId())
                            .userId(resident.getUser().getId())
                            .email(resident.getUser().getEmail())
                            .phone(resident.getUser().getPhone())
                            .fullName(resident.getFullName())
                            .age(resident.getAge())
                            .gender(resident.getGender())
                            .dateOfBirth(resident.getDateOfBirth())
                            .imageUrl(resident.getImageUrl())
                            .flatId(flat != null ? flat.getId() : null)
                            .flatNo(flat != null ? flat.getFlatNo() : null)
                            .floorNo(flat != null ? flat.getFloorNo() : null)
                            .flatType(flat != null ? flat.getFlatType() : null)
                            .block(flat != null ? flat.getBlock() : null)
                            .buildingNo(flat != null ? flat.getBuildingNo() : null)
                            .societyId(society != null ? society.getId() : null)
                            .societyName(society != null ? society.getName() : null)
                            .build();
                })
                .toList();
    }


}
