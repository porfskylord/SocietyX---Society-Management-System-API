package com.lordscave.societyxapi.admin_module.service.impl;

import com.lordscave.societyxapi.admin_module.dto.req.UpdateSociety;
import com.lordscave.societyxapi.admin_module.dto.rsp.GeneralResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.SocietyDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.SocietyResponse;
import com.lordscave.societyxapi.admin_module.service.interfaces.SocietyService;
import com.lordscave.societyxapi.core.entity.Society;
import com.lordscave.societyxapi.core.exceptions.BadRequestException;
import com.lordscave.societyxapi.core.repository.SocietyRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SocietyServiceImpl implements SocietyService {

    @Autowired
    private SocietyRepo societyRepo;

    @Override
    @Transactional
    public SocietyResponse createSociety(UpdateSociety request) {

        log.info("Creating society");

        if(societyRepo.count() > 0) {
            log.error("A society already exists. You cannot create more than one.");
            throw new BadRequestException("A society already exists. You cannot create more than one.");
        }

        try {
            Society society = Society.builder()
                    .name(request.getName())
                    .address(request.getAddress())
                    .city(request.getCity())
                    .state(request.getState())
                    .pincode(request.getPincode())
                    .imageUrl(request.getImageUrl())
                    .build();

            Society savedSociety = societyRepo.save(society);

            log.info("Society created successfully");

            return SocietyResponse.builder()
                    .message("Society created successfully")
                    .id(savedSociety.getId())
                    .name(savedSociety.getName())
                    .address(savedSociety.getAddress())
                    .city(savedSociety.getCity())
                    .state(savedSociety.getState())
                    .pincode(savedSociety.getPincode())
                    .imageUrl(savedSociety.getImageUrl())
                    .createdAt(savedSociety.getCreatedAt())
                    .updatedAt(savedSociety.getUpdatedAt())
                    .build();
        } catch (DataIntegrityViolationException e) {
            log.error("Database error during Society registration: {}", e.getMessage());
            throw new RuntimeException("Registration failed due to a database error");
        } catch (Exception e) {
            log.error("Unexpected error during Society registration: {}", e.getMessage());
            throw new RuntimeException("Registration failed. Please try again later.");
        }

    }

    @Override
    public SocietyDetailsResponse getSociety() {

        log.info("Getting society details");

        Society society = societyRepo.findAll().get(0);

        log.info("Society details fetched successfully");

        return SocietyDetailsResponse.builder()
                .id(society.getId())
                .name(society.getName())
                .address(society.getAddress())
                .city(society.getCity())
                .state(society.getState())
                .pincode(society.getPincode())
                .imageUrl(society.getImageUrl())
                .createdAt(society.getCreatedAt())
                .build();

    }

    @Override
    @Transactional
    public SocietyResponse updateSociety(UpdateSociety request) {

        log.info("Updating society");

        Society society = societyRepo.findAll().get(0);
        society.setName(request.getName());
        society.setAddress(request.getAddress());
        society.setCity(request.getCity());
        society.setState(request.getState());
        society.setPincode(request.getPincode());
        society.setImageUrl(request.getImageUrl());


        Society savedSociety = societyRepo.save(society);

        log.info("Society updated successfully");

        return SocietyResponse.builder()
                .message("Society updated successfully")
                .id(savedSociety.getId())
                .name(savedSociety.getName())
                .address(savedSociety.getAddress())
                .city(savedSociety.getCity())
                .state(savedSociety.getState())
                .pincode(savedSociety.getPincode())
                .imageUrl(savedSociety.getImageUrl())
                .createdAt(savedSociety.getCreatedAt())
                .updatedAt(savedSociety.getUpdatedAt())
                .build();

    }

    @Override
    @Transactional
    public GeneralResponse deleteSociety() {

        log.info("Deleting society");

        Society society = societyRepo.findAll().get(0);
        societyRepo.delete(society);

        log.info("Society deleted successfully");

        return GeneralResponse.builder()
                .message("Society deleted successfully")
                .id(society.getId())
                .build();
    }

    @Override
    public SocietyResponse patchSociety(UpdateSociety request) {
        log.info("Updating society");

        Society society = societyRepo.findAll().get(0);
        if(request.getName() != null) society.setName(request.getName());
        if(request.getAddress() != null) society.setAddress(request.getAddress());
        if(request.getCity() != null) society.setCity(request.getCity());
        if(request.getState() != null) society.setState(request.getState());
        if(request.getPincode() != null) society.setPincode(request.getPincode());
        if(request.getImageUrl() != null) society.setImageUrl(request.getImageUrl());


        Society savedSociety = societyRepo.save(society);

        log.info("Society updated successfully");

        return SocietyResponse.builder()
                .message("Society updated successfully")
                .id(savedSociety.getId())
                .name(savedSociety.getName())
                .address(savedSociety.getAddress())
                .city(savedSociety.getCity())
                .state(savedSociety.getState())
                .pincode(savedSociety.getPincode())
                .imageUrl(savedSociety.getImageUrl())
                .createdAt(savedSociety.getCreatedAt())
                .updatedAt(savedSociety.getUpdatedAt())
                .build();
    }


}
