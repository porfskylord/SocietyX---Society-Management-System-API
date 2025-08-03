package com.lordscave.societyxapi.admin_module.dto.req;

import jakarta.validation.constraints.NotBlank;

@lombok.Data
public class CreateGate {
    @NotBlank(message = "Gate name is required")
    private String gateName;
    @NotBlank(message = "Society ID is required")
    private Long societyId;
    @NotBlank(message = "Is active is required")
    private boolean isActive;
}
