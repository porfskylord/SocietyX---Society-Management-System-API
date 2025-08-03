package com.lordscave.societyxapi.admin_module.controller;

import com.lordscave.societyxapi.admin_module.dto.req.UpdateSecurity;
import com.lordscave.societyxapi.admin_module.dto.rsp.SecurityDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.SecurityResponse;
import com.lordscave.societyxapi.admin_module.service.interfaces.SecurityManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/security-management")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "ADMIN MODULE - Security Management", description = "Security Management APIs")
public class SecurityManagementController {

    @Autowired private SecurityManagementService securityManagementService;

    //-------------------------------------------------SECURITY MANAGEMENT----------------------------------------------

    @Operation(summary = "Get all security" , tags = "ADMIN MODULE - Security Management")
    @GetMapping
    public ResponseEntity<List<SecurityDetailsResponse>> getAllSecurity()
    { return ResponseEntity.ok(securityManagementService.getAllSecurity()); }

    @Operation(summary = "Get security by id" , tags = "ADMIN MODULE - Security Management")
    @GetMapping("/{id}")
    public ResponseEntity<SecurityDetailsResponse> getSecurity(@PathVariable Long id)
    { return ResponseEntity.ok(securityManagementService.getSecurity(id)); }

    @Operation(summary = "Update security by id" , tags = "ADMIN MODULE - Security Management")
    @PutMapping("/{id}")
    public ResponseEntity<SecurityResponse> updateSecurity(@PathVariable Long id, @RequestBody UpdateSecurity request)
    { return ResponseEntity.ok(securityManagementService.updateSecurity(id, request)); }

    @Operation(summary = "Patch security by id" , tags = "ADMIN MODULE - Security Management")
    @PatchMapping("/{id}")
    public ResponseEntity<SecurityResponse> patchSecurity(@PathVariable Long id, @RequestBody UpdateSecurity request)
    { return ResponseEntity.ok(securityManagementService.patchSecurity(id, request)); }

}
