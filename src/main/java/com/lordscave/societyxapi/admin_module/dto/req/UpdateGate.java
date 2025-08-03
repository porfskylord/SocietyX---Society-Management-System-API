package com.lordscave.societyxapi.admin_module.dto.req;

import jakarta.validation.constraints.NotNull;

@lombok.Data
public class UpdateGate {
    private String gateName;
    @NotNull(message = "Active is required")
    private boolean isActive;
}
