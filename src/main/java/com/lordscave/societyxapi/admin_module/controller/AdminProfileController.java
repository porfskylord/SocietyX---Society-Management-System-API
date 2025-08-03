package com.lordscave.societyxapi.admin_module.controller;

import com.lordscave.societyxapi.admin_module.dto.req.UpdateAdminProfile;
import com.lordscave.societyxapi.admin_module.dto.rsp.AdminDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.AdminResponse;
import com.lordscave.societyxapi.admin_module.service.interfaces.AdminService;
import com.lordscave.societyxapi.core.entity.User;
import com.lordscave.societyxapi.core.exceptions.BadRequestException;
import com.lordscave.societyxapi.core.exceptions.dto.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/profile")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "ADMIN MODULE - Admin Profile" , description = "Admin Profile Management APIs")
public class AdminProfileController {

    @Autowired private AdminService adminService;

    //-------------------------------------------------ADMIN PROFILE MANAGEMENT-----------------------------------------

    @Operation(summary = "Get admin details", tags = "ADMIN MODULE - Admin Profile" )
    @GetMapping("/{id}")
    public ResponseEntity<AdminDetailsResponse> getAdmin(@PathVariable Long id)
    { return ResponseEntity.ok(adminService.getAdmin(id)); }

    @Operation(summary = "Update admin details", tags = "ADMIN MODULE - Admin Profile" )
    @PutMapping("/{id}")
    public ResponseEntity<AdminResponse> updateAdmin(@PathVariable Long id,@RequestBody UpdateAdminProfile request)
    { return ResponseEntity.ok(adminService.updateAdmin(id,request)); }

    @Operation(summary = "Partial update admin details", tags = "ADMIN MODULE - Admin Profile" )
    @PatchMapping("/{id}")
    public ResponseEntity<AdminResponse> patchAdmin(@PathVariable Long id,@RequestBody UpdateAdminProfile request)
    { return ResponseEntity.ok(adminService.patchAdmin(id,request)); }



}
