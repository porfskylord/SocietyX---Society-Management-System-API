package com.lordscave.societyxapi.resident_module.controller;

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

@RestController
@RequestMapping("/api/resident/profile")
@PreAuthorize("hasRole('RESIDENT')")
@Tag(name = "RESIDENT MODULE - Resident Profile", description = "Resident Profile APIs")
public class ResidentProfileController {

    @Autowired
    private ResidentService residentService;

    //-----------------------------------------------RESIDENT PROFILE MANAGEMENT BY RESIDENT----------------------------

    @Operation(summary = "Get resident details", tags = "RESIDENT MODULE - Resident Profile")
    @GetMapping("/{id}")
    public ResponseEntity<ResidentDetailsResponse> getResident(@PathVariable Long id)
    { return ResponseEntity.ok(residentService.getResident(id)); }

    @Operation(summary = "Update resident details", tags = "RESIDENT MODULE - Resident Profile")
    @PutMapping("/{id}")
    public ResponseEntity<ResidentResponse> updateResident(@PathVariable Long id,@RequestBody UpdateResident request)
    { return ResponseEntity.ok(residentService.updateResident(id,request)); }

    @Operation(summary = "Update resident details", tags = "RESIDENT MODULE - Resident Profile")
    @PatchMapping("/{id}")
    public ResponseEntity<ResidentResponse> patchResident(@PathVariable Long id,@RequestBody UpdateResident request)
    { return ResponseEntity.ok(residentService.patchResident(id,request)); }

}
