package com.lordscave.societyxapi.security_module.controller;

import com.lordscave.societyxapi.admin_module.dto.rsp.GeneralResponse;
import com.lordscave.societyxapi.security_module.dto.req.CreateVisit;
import com.lordscave.societyxapi.security_module.dto.rsp.VisitDetailsResponse;
import com.lordscave.societyxapi.security_module.dto.rsp.VisitResponse;
import com.lordscave.societyxapi.security_module.service.interfaces.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security/visit")
@PreAuthorize("hasRole('SECURITY')")
@Tag(name = "SECURITY MODULE - Visitor Management", description = "APIs for Visit Management")
public class VisitController {

    @Autowired
    private VisitService visitService;

    //------------------------------------------------VISITOR MANAGEMENT------------------------------------------------

    @Operation(summary = "Create visit", tags = "SECURITY MODULE - Visitor Management")
    @PostMapping
    public ResponseEntity<VisitResponse> createVisit(@RequestBody CreateVisit createVisit)
    { return new ResponseEntity<>(visitService.createVisit(createVisit),HttpStatus.CREATED); }

    @Operation(summary = "Get all visit", tags = "SECURITY MODULE - Visitor Management")
    @GetMapping
    public ResponseEntity<List<VisitDetailsResponse>> getAllVisit(){ return ResponseEntity.ok(visitService.getAllVisit()); }

    @Operation(summary = "Get visit by id", tags = "SECURITY MODULE - Visitor Management")
    @GetMapping("/{id}")
    public ResponseEntity<VisitDetailsResponse> getVisit(@PathVariable Long id){ return ResponseEntity.ok(visitService.getVisit(id)); }

    @Operation(summary = "Delete visit by id", tags = "SECURITY MODULE - Visitor Management")
    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> deleteVisit(@PathVariable Long id){ return ResponseEntity.ok(visitService.deleteVisit(id)); }

    //------------------------------------------------ACTION AFTER APPROVAL---------------------------------------------

    @Operation(summary = "Check in", tags = "SECURITY MODULE - Visitor Management")
    @PutMapping("/{visitId}/checkin")
    public ResponseEntity<GeneralResponse> checkIn(@PathVariable Long visitId)
    { return ResponseEntity.ok(visitService.checkIn(visitId)); }

    @Operation(summary = "Check out", tags = "SECURITY MODULE - Visitor Management")
    @PutMapping("/{visitId}/checkout")
    public ResponseEntity<GeneralResponse> checkOut(@PathVariable Long visitId)
    { return ResponseEntity.ok(visitService.checkOut(visitId)); }





}
