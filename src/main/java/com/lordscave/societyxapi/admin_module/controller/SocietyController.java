package com.lordscave.societyxapi.admin_module.controller;

import com.lordscave.societyxapi.admin_module.dto.req.UpdateSociety;
import com.lordscave.societyxapi.admin_module.dto.rsp.SocietyDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.SocietyResponse;
import com.lordscave.societyxapi.admin_module.service.interfaces.SocietyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/society")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "ADMIN MODULE - Society Management", description = "Society Management APIs")
public class SocietyController {

    @Autowired private SocietyService societyService;

    //------------------------------------------------SOCIETY MANAGEMENT------------------------------------------------

    @Operation(summary = "Get society details" ,tags = "ADMIN MODULE - Society Management")
    @GetMapping
    public ResponseEntity<SocietyDetailsResponse> getSociety()
    { return ResponseEntity.ok(societyService.getSociety()); }

    @Operation(summary = "Update society details" ,tags = "ADMIN MODULE - Society Management")
    @PutMapping
    public ResponseEntity<SocietyResponse> updateSociety(@RequestBody UpdateSociety request)
    { return ResponseEntity.ok(societyService.updateSociety(request)); }

    @Operation(summary = "Patch society details" ,tags = "ADMIN MODULE - Society Management")
    @PatchMapping
    public ResponseEntity<SocietyResponse> patchSociety(@RequestBody UpdateSociety request)
    { return ResponseEntity.ok(societyService.patchSociety(request)); }


}
