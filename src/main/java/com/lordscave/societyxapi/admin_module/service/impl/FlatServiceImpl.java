package com.lordscave.societyxapi.admin_module.service.impl;

import com.lordscave.societyxapi.admin_module.dto.req.CreateFlat;
import com.lordscave.societyxapi.admin_module.dto.req.UpdateFlat;
import com.lordscave.societyxapi.admin_module.dto.rsp.GeneralResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.FlatDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.FlatResponse;
import com.lordscave.societyxapi.admin_module.service.interfaces.FlatService;
import com.lordscave.societyxapi.core.entity.Flat;
import com.lordscave.societyxapi.core.entity.Society;
import com.lordscave.societyxapi.core.exceptions.ConflictException;
import com.lordscave.societyxapi.core.exceptions.ResourceNotFoundException;
import com.lordscave.societyxapi.core.repository.FlatRepo;
import com.lordscave.societyxapi.core.repository.SocietyRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class FlatServiceImpl implements FlatService {

    @Autowired private FlatRepo flatRepo;

    @Autowired private SocietyRepo societyRepo;

    @Override
    @Transactional
    public FlatResponse createFlat(CreateFlat request) {
        log.info("createFlat {}", request);

        if (flatRepo.existsByFlatNo(request.getFlatNo())) {
            log.error("Flat with flatNo {} already exists", request.getFlatNo());
            throw new ConflictException("Flat with flatNo " + request.getFlatNo() + " already exists");
        }

        try {
            Society society = societyRepo.findAll().get(0);

            Flat flat = Flat.builder()
                    .flatNo(request.getFlatNo())
                    .flatType(request.getFlatType())
                    .floorNo(request.getFloorNo())
                    .IsOccupied(request.getIsOccupied())
                    .block(request.getBlock())
                    .buildingNo(request.getBuildingNo())
                    .society(society)
                    .build();

            flat = flatRepo.save(flat);

            log.info("Flat created successfully");

            return FlatResponse.builder()
                    .message("Flat created successfully")
                    .id(flat.getId())
                    .flatNo(flat.getFlatNo())
                    .flatType(flat.getFlatType())
                    .isOccupied(flat.getIsOccupied())
                    .floorNo(flat.getFloorNo())
                    .block(flat.getBlock())
                    .buildingNo(flat.getBuildingNo())
                    .societyId(flat.getSociety().getId())
                    .societyName(flat.getSociety().getName())
                    .createdAt(flat.getCreatedAt())
                    .updatedAt(flat.getUpdatedAt())
                    .build();

        }  catch (DataIntegrityViolationException e) {
            log.error("Database error during Flat registration: {}", e.getMessage());
            throw new RuntimeException("Registration failed due to a database error");
        } catch (Exception e) {
            log.error("Unexpected error during Flat registration: {}", e.getMessage());
            throw new RuntimeException("Registration failed. Please try again later.");
        }

    }

    @Override
    public List<FlatDetailsResponse> getAllFlats() {
        log.info("getAllFlats");
            return flatRepo.findAll().stream().map(flat -> FlatDetailsResponse.builder()
                    .id(flat.getId())
                    .flatNo(flat.getFlatNo())
                    .flatType(flat.getFlatType())
                    .floorNo(flat.getFloorNo())
                    .isOccupied(flat.getIsOccupied())
                    .block(flat.getBlock())
                    .buildingNo(flat.getBuildingNo())
                    .societyId(flat.getSociety().getId())
                    .societyName(flat.getSociety().getName())
                    .createdAt(flat.getCreatedAt())
                    .build()).toList();
    }

    @Override
    public FlatDetailsResponse getFlat(Long id) {

        log.info("getFlat {}", id);

        Flat flat = flatRepo.findById(id).orElseThrow(() -> {
            log.error("Flat not found with id: {}", id);
            return new ResourceNotFoundException("Flat not found with id: " + id);
        });


        return FlatDetailsResponse.builder()
                .id(flat.getId())
                .flatNo(flat.getFlatNo())
                .flatType(flat.getFlatType())
                .isOccupied(flat.getIsOccupied())
                .floorNo(flat.getFloorNo())
                .block(flat.getBlock())
                .buildingNo(flat.getBuildingNo())
                .societyId(flat.getSociety().getId())
                .societyName(flat.getSociety().getName())
                .createdAt(flat.getCreatedAt())
                .build();


    }

    @Override
    @Transactional
    public FlatResponse updateFlat(Long id, UpdateFlat request) {
        log.info("updateFlat {}", id);

        Flat flat = flatRepo.findById(id).orElseThrow(() ->
        {
            log.error("Flat not found with id: {}", id);
            return new ResourceNotFoundException("Flat not found with id: " + id);
        });

        flat.setFlatNo(request.getFlatNo());
        flat.setFlatType(request.getFlatType());
        flat.setFloorNo(request.getFloorNo());
        flat.setIsOccupied(request.getIsOccupied());
        flat.setBlock(request.getBlock());
        flat.setBuildingNo(request.getBuildingNo());

        flat = flatRepo.save(flat);

        log.info("Flat updated successfully");

        return FlatResponse.builder()
                .message("Flat updated successfully")
                .id(flat.getId())
                .flatNo(flat.getFlatNo())
                .flatType(flat.getFlatType())
                .floorNo(flat.getFloorNo())
                .isOccupied(flat.getIsOccupied())
                .block(flat.getBlock())
                .buildingNo(flat.getBuildingNo())
                .societyId(flat.getSociety().getId())
                .societyName(flat.getSociety().getName())
                .createdAt(flat.getCreatedAt())
                .updatedAt(flat.getUpdatedAt())
                .build();

    }

    @Override
    @Transactional
    public GeneralResponse deleteFlat(Long id) {
        log.info("deleteFlat {}", id);

        Flat flat = flatRepo.findById(id).orElseThrow(() -> {
            log.error("Flat not found with id: {}", id);
            return new ResourceNotFoundException("Flat not found with id: " + id);
        });

        flat.setDeleted(true);
        flatRepo.save(flat);

        log.info("Flat deleted successfully");

        return GeneralResponse.builder()
                .message("Flat deleted successfully")
                .id(flat.getId())
                .build();
    }

    @Override
    @Transactional
    public FlatResponse partialUpdateFlat(Long id, UpdateFlat request) {
        log.info("updateFlat {}", id);

        Flat flat = flatRepo.findById(id).orElseThrow(() -> {
            log.error("Flat not found with id: {}", id);
            return new ResourceNotFoundException("Flat not found with id: " + id);
        });

        if (request.getFlatNo() != null) flat.setFlatNo(request.getFlatNo());
        if (request.getFlatType() != null) flat.setFlatType(request.getFlatType());
        if (request.getFloorNo() != null) flat.setFloorNo(request.getFloorNo());
        if (request.getIsOccupied() != null) flat.setIsOccupied(request.getIsOccupied());
        if (request.getBlock() != null) flat.setBlock(request.getBlock());
        if (request.getBuildingNo() != null) flat.setBuildingNo(request.getBuildingNo());

        flat = flatRepo.save(flat);

        log.info("Flat updated successfully");

        return FlatResponse.builder()
                .message("Flat updated successfully")
                .id(flat.getId())
                .flatNo(flat.getFlatNo())
                .flatType(flat.getFlatType())
                .floorNo(flat.getFloorNo())
                .isOccupied(flat.getIsOccupied())
                .block(flat.getBlock())
                .buildingNo(flat.getBuildingNo())
                .societyId(flat.getSociety().getId())
                .societyName(flat.getSociety().getName())
                .createdAt(flat.getCreatedAt())
                .updatedAt(flat.getUpdatedAt())
                .build();
    }


}
