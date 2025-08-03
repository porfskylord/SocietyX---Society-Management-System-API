package com.lordscave.societyxapi.core.dev;

import com.lordscave.societyxapi.admin_module.dto.req.UpdateSociety;
import com.lordscave.societyxapi.admin_module.dto.rsp.SocietyResponse;
import com.lordscave.societyxapi.admin_module.service.interfaces.SocietyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev")
@Tag(name = "DEV MODULE - Dev Controller", description = "Dev Controller")
public class DevController {

    @Autowired
    private DevService devService;

    @Autowired
    private SocietyService societyService;

    //--------------------------------------------------DEV CONTROLLER--------------------------------------------------

    @Operation(summary = "Create society", tags = "DEV MODULE - Dev Controller")
    @PostMapping("/create-society")
    public ResponseEntity<SocietyResponse> createSociety(@RequestBody UpdateSociety request)
    { return new ResponseEntity<>(societyService.createSociety(request), HttpStatus.CREATED); }

    @Operation(summary = "Create admin" , tags = "DEV MODULE - Dev Controller")
    @PostMapping("/create-admin")
    public ResponseEntity<DevResponseDto> createAdmin(@RequestBody CreateAdminDto request)
    { return new ResponseEntity<>(devService.createAdmin(request), HttpStatus.CREATED); }


}
