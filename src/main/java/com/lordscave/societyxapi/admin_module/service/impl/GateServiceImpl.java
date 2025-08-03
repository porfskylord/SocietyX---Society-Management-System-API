package com.lordscave.societyxapi.admin_module.service.impl;

import com.lordscave.societyxapi.admin_module.dto.req.CreateGate;
import com.lordscave.societyxapi.admin_module.dto.req.UpdateGate;
import com.lordscave.societyxapi.admin_module.dto.rsp.GateDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.GeneralResponse;
import com.lordscave.societyxapi.admin_module.service.interfaces.GateService;
import com.lordscave.societyxapi.core.entity.Gate;
import com.lordscave.societyxapi.core.entity.Society;
import com.lordscave.societyxapi.core.exceptions.ConflictException;
import com.lordscave.societyxapi.core.exceptions.ResourceNotFoundException;
import com.lordscave.societyxapi.core.repository.GateRepo;
import com.lordscave.societyxapi.core.repository.SocietyRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class GateServiceImpl implements GateService {

    @Autowired private GateRepo gateRepo;

    @Autowired private SocietyRepo societyRepo;

    @Override
    @Transactional
    public GeneralResponse createGate(CreateGate request) {
        log.info("Creating gate");
        if(gateRepo.existsByGateName(request.getGateName())) {
            throw new ConflictException("Gate with name " + request.getGateName() + " already exists");
        }
        Society society = societyRepo.findAll().get(0);
        Gate gate = Gate.builder()
                .gateName(request.getGateName())
                .isActive(request.isActive())
                .society(society)
                .build();
        Gate savedGate = gateRepo.save(gate);
        log.info("Gate created successfully");
        return GeneralResponse.builder()
                .message("Gate created successfully")
                .id((Long) savedGate.getId())
                .build();

    }

    @Override
    public GateDetailsResponse getGate(Long id) {
        Gate gate = gateRepo.findById(id).orElseThrow(() -> {
            log.error("Gate not found");
           return new ResourceNotFoundException("Gate not found");
        });
        return GateDetailsResponse.builder()
                .id(gate.getId())
                .gateName(gate.getGateName())
                .isActive(gate.isActive())
                .societyId(gate.getSociety().getId())
                .societyName(gate.getSociety().getName())
                .createdAt(gate.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public GeneralResponse updateGate(Long id, UpdateGate request) {
        Gate gate = gateRepo.findById(id).orElseThrow(() -> {
            log.error("Gate not found");
            return new ResourceNotFoundException("Gate not found");
        });
        gate.setGateName(request.getGateName());
        gate.setActive(request.isActive());
        gateRepo.save(gate);
        return GeneralResponse.builder()
                .message("Gate updated successfully")
                .id(gate.getId())
                .build();
    }

    @Override
    public List<GateDetailsResponse> getAllGate() {
        return gateRepo.findAll().stream().map(gate -> GateDetailsResponse.builder()
                .id(gate.getId())
                .gateName(gate.getGateName())
                .isActive(gate.isActive())
                .societyId(gate.getSociety().getId())
                .societyName(gate.getSociety().getName())
                .createdAt(gate.getCreatedAt())
                .build()).toList();

    }

    @Override
    @Transactional
    public GeneralResponse deleteGate(Long id) {

        Gate gate = gateRepo.findById(id).orElseThrow(() -> {
            log.error("Gate not found");
            return new ResourceNotFoundException("Gate not found");
        });
        gate.setDeleted(true);
        gateRepo.save(gate);
        return GeneralResponse.builder()
                .message("Gate deleted successfully")
                .id(gate.getId())
                .build();
    }

    @Override
    @Transactional
    public GeneralResponse patchGate(Long id , UpdateGate request) {
        Gate gate = gateRepo.findById(id).orElseThrow(() -> {
            log.error("Gate not found");
            return new ResourceNotFoundException("Gate not found");
        });
        if(request.getGateName() != null) gate.setGateName(request.getGateName());
        if(request.isActive()) gate.setActive(true);
        gateRepo.save(gate);
        return GeneralResponse.builder()
                .message("Gate updated successfully")
                .id(gate.getId())
                .build();
    }
}
