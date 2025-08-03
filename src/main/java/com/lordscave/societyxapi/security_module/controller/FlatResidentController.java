package com.lordscave.societyxapi.security_module.controller;

import com.lordscave.societyxapi.security_module.dto.rsp.ResidentDetailResponse;
import com.lordscave.societyxapi.security_module.dto.rsp.FlatDetailResponse;
import com.lordscave.societyxapi.security_module.dto.rsp.FlatResidentDetailsResponse;
import com.lordscave.societyxapi.security_module.service.interfaces.FlatResidentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/security/flat-resident")
@PreAuthorize("hasRole('SECURITY')")
@Tag(name = "SECURITY MODULE - Flat-Resident Info", description = "Flat-Resident Info APIs")
public class FlatResidentController {

    @Autowired
    private FlatResidentService flatResidentService;

    //-----------------------------------------------GET ALL FLAT DETAILS-----------------------------------------------

    @Operation(summary = "Get all flat details", tags = "SECURITY MODULE - Flat-Resident Info")
    @GetMapping("/flat")
    public ResponseEntity<List<FlatDetailResponse>> getAllFlatDetails()
    { return ResponseEntity.ok(flatResidentService.getAllFlatDetails()); }

    //-----------------------------------------------GET ALL RESIDENT DETAILS-------------------------------------------

    @Operation(summary = "Get all resident details", tags = "SECURITY MODULE - Flat-Resident Info")
    @GetMapping("/resident")
    public ResponseEntity<List<ResidentDetailResponse>> getAllResidentDetails()
    { return ResponseEntity.ok(flatResidentService.getAllResidentDetails()); }


    //-----------------------------------------------GET ALL FLAT-RESIDENT DETAILS--------------------------------------
    @Operation(summary = "Get all flat-resident details", tags = "SECURITY MODULE - Flat-Resident Info")
    @GetMapping
    public ResponseEntity<List<FlatResidentDetailsResponse>> getAllFlatResidentDetails()
    { return ResponseEntity.ok(flatResidentService.getAllFlatResidentDetails()); }


    //-----------------------------------------------GET FLAT-RESIDENT DETAILS BY FLAT----------------------------------

    @Operation(summary = "Get flat-resident details by flat id", tags = "SECURITY MODULE - Flat-Resident Info")
    @GetMapping("/flat/{id}")
    public ResponseEntity<FlatResidentDetailsResponse> getFlatResidentDetailsByFlatId(@PathVariable Long id)
    { return ResponseEntity.ok(flatResidentService.getFlatResidentDetailsByFlatId(id)); }

    @Operation(summary = "Get flat-resident details by flat no", tags = "SECURITY MODULE - Flat-Resident Info")
    @GetMapping("/flatno/{flatNo}")
    public ResponseEntity<FlatResidentDetailsResponse> getFlatResidentDetailsByFlatNo(@PathVariable String flatNo)
    { return ResponseEntity.ok(flatResidentService.getFlatResidentDetailsByFlatNo(flatNo)); }


    //-----------------------------------------------GET FLAT-RESIDENT DETAILS BY RESIDENT------------------------------

    @Operation(summary = "Get flat-resident details by resident user id", tags = "SECURITY MODULE - Flat-Resident Info")
    @GetMapping("/resident-user/{id}")
    public ResponseEntity<FlatResidentDetailsResponse> getFlatResidentDetailsByResidentUserId(@PathVariable Long id)
    { return ResponseEntity.ok(flatResidentService.getFlatResidentDetailsByResidentUserId(id)); }

    @Operation(summary = "Get flat-resident details by resident name", tags = "SECURITY MODULE - Flat-Resident Info")
    @GetMapping("/resident/{residentName}")
    public ResponseEntity<FlatResidentDetailsResponse> getFlatResidentDetailsByResidentName(@PathVariable String residentName)
    { return ResponseEntity.ok(flatResidentService.getFlatResidentDetailsByResidentName(residentName)); }

}
