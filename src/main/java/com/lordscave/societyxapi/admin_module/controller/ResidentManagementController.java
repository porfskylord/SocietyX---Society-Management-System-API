package com.lordscave.societyxapi.admin_module.controller;

import com.lordscave.societyxapi.resident_module.dto.req.UpdateResident;
import com.lordscave.societyxapi.resident_module.dto.rsp.ResidentDetailsResponse;
import com.lordscave.societyxapi.resident_module.dto.rsp.ResidentResponse;
import com.lordscave.societyxapi.resident_module.service.interfaces.ResidentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/resident-management")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "ADMIN MODULE - Resident Management", description = "Resident Management APIs")
public class ResidentManagementController {

    @Autowired private ResidentService residentService;

    //------------------------------------------------RESIDENT MANAGEMENT-----------------------------------------------

    @Operation(summary = "Get all residents", tags = "ADMIN MODULE - Resident Management")
    @GetMapping
    public ResponseEntity<List<ResidentDetailsResponse>> getAllResident()
    { return ResponseEntity.ok(residentService.getAllResident()); }

    @Operation(summary = "Get resident details" , tags = "ADMIN MODULE - Resident Management")
    @GetMapping("/{id}")
    public ResponseEntity<ResidentDetailsResponse> getResident(@PathVariable Long id)
    { return ResponseEntity.ok(residentService.getResident(id)); }

    @Operation(summary = "Update resident details" , tags = "ADMIN MODULE - Resident Management")
    @PutMapping("/{id}")
    public ResponseEntity<ResidentResponse> updateResident(@PathVariable Long id, @RequestBody UpdateResident request)
    { return ResponseEntity.ok(residentService.updateResident(id,request)); }

    @Operation(summary = "Update resident details" , tags = "ADMIN MODULE - Resident Management")
    @PatchMapping("/{id}")
    public ResponseEntity<ResidentResponse> patchResident(@PathVariable Long id,@RequestBody UpdateResident request)
    { return ResponseEntity.ok(residentService.patchResident(id,request)); }
}
