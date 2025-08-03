package com.lordscave.societyxapi.admin_module.dto.req;

import com.lordscave.societyxapi.core.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@lombok.Data
public class CreateUser {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Invalid phone number format")
    private String phone;
    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(ADMIN|RESIDENT|SECURITY)$", message = "Invalid role")
    private Role role;
    @NotBlank(message = "Society ID is required")
    private Long societyId;

}

