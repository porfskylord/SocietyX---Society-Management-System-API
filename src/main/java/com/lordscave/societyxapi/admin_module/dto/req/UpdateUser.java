package com.lordscave.societyxapi.admin_module.dto.req;

import com.lordscave.societyxapi.core.entity.enums.Role;
import com.lordscave.societyxapi.core.entity.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@lombok.Data
public class UpdateUser {
    @Email(message = "Invalid email format")
    private String email;
    @Pattern(regexp = "^\\d{10}$", message = "Invalid phone number format")
    private String phone;
    @Pattern(regexp = "^(ADMIN|RESIDENT|SECURITY)$", message = "Invalid role")
    private Role role;
    @Pattern(regexp = "^(ACTIVE|INACTIVE|BLOCKED|PENDING)$", message = "Invalid status")
    private UserStatus status;
}
