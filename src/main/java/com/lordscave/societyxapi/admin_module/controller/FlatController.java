package com.lordscave.societyxapi.admin_module.controller;

import com.lordscave.societyxapi.admin_module.dto.req.CreateFlat;
import com.lordscave.societyxapi.admin_module.dto.req.UpdateFlat;
import com.lordscave.societyxapi.admin_module.dto.rsp.GeneralResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.FlatDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.FlatResponse;
import com.lordscave.societyxapi.admin_module.service.interfaces.FlatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/flat")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "ADMIN MODULE - Flat Management", description = "Flat Management APIs")
public class FlatController {

    @Autowired private FlatService flatService;

    //-----------------------------------------------FLAT MANAGEMENT----------------------------------------------------

    @Operation(summary = "Create Flat", tags = "ADMIN MODULE - Flat Management")
    @PostMapping
    public ResponseEntity<FlatResponse> createFlat(@RequestBody CreateFlat request)
    { return new ResponseEntity<>(flatService.createFlat(request) ,HttpStatus.CREATED);}

    @Operation(summary = "Get all Flats", tags = "ADMIN MODULE - Flat Management")
    @GetMapping
    public ResponseEntity<List<FlatDetailsResponse>> getAllFlat()
    { return ResponseEntity.ok(flatService.getAllFlats()); }

    @Operation(summary = "Get Flat details", tags = "ADMIN MODULE - Flat Management")
    @GetMapping("/{id}")
    public ResponseEntity<FlatDetailsResponse> getFlat(@PathVariable Long id)
    { return ResponseEntity.ok(flatService.getFlat(id)); }

    @Operation(summary = "Update Flat details", tags = "ADMIN MODULE - Flat Management")
    @PutMapping("/{id}")
    public ResponseEntity<FlatResponse> updateFlat(@PathVariable Long id,@RequestBody UpdateFlat request)
    { return ResponseEntity.ok(flatService.updateFlat(id,request)); }

    @Operation(summary = "Partial update Flat details", tags = "ADMIN MODULE - Flat Management")
    @PatchMapping("/{id}")
    public ResponseEntity<FlatResponse> partialUpdateFlat(@PathVariable Long id,@RequestBody UpdateFlat request)
    { return ResponseEntity.ok(flatService.partialUpdateFlat(id,request)); }

    @Operation(summary = "Delete Flat", tags = "ADMIN MODULE - Flat Management")
    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> deleteFlat(@PathVariable Long id)
    { return ResponseEntity.ok(flatService.deleteFlat(id)); }

}
