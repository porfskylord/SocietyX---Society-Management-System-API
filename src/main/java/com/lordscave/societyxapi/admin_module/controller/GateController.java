package com.lordscave.societyxapi.admin_module.controller;

import com.lordscave.societyxapi.admin_module.dto.req.CreateGate;
import com.lordscave.societyxapi.admin_module.dto.req.UpdateGate;
import com.lordscave.societyxapi.admin_module.dto.rsp.GateDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.GeneralResponse;
import com.lordscave.societyxapi.admin_module.service.interfaces.GateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/gate")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "ADMIN MODULE - Gate Management" , description = "Gate Management APIs")
public class GateController {

    @Autowired private GateService gateService;

    //------------------------------------------------GATE MANAGEMENT---------------------------------------------------

    @Operation(summary = "Create gate", tags = "ADMIN MODULE - Gate Management")
    @PostMapping
    public ResponseEntity<GeneralResponse> createGate(@RequestBody CreateGate request)
    { return new ResponseEntity<>(gateService.createGate(request), HttpStatus.CREATED); }

    @Operation(summary = "Get all gates" , tags = "ADMIN MODULE - Gate Management")
    @GetMapping
    public ResponseEntity<List<GateDetailsResponse>> getAllGate()
    { return ResponseEntity.ok(gateService.getAllGate()); }

    @Operation(summary = "Get gate details" , tags = "ADMIN MODULE - Gate Management")
    @GetMapping("/{id}")
    public ResponseEntity<GateDetailsResponse> getGate(@PathVariable Long id)
    { return ResponseEntity.ok(gateService.getGate(id)); }

    @Operation(summary = "Update gate", tags = "ADMIN MODULE - Gate Management")
    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse> updateGate(@PathVariable Long id,@RequestBody UpdateGate request)
    { return ResponseEntity.ok(gateService.updateGate(id,request)); }

    @Operation(summary = "Update gate", tags = "ADMIN MODULE - Gate Management")
    @PatchMapping("/{id}")
    public ResponseEntity<GeneralResponse> patchGate(@PathVariable Long id,@RequestBody UpdateGate request)
    { return ResponseEntity.ok(gateService.patchGate(id, request)); }

    @Operation(summary = "Delete gate", tags = "ADMIN MODULE - Gate Management")
    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> deleteGate(@PathVariable Long id)
    { return ResponseEntity.ok(gateService.deleteGate(id)); }

}
