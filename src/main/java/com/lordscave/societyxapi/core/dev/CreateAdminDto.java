package com.lordscave.societyxapi.core.dev;

import com.lordscave.societyxapi.core.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@lombok.Data
public class CreateAdminDto {
    @NotBlank(message = "Email is required")
    @Email
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @Pattern(regexp = "^\\d{10}$", message = "Invalid phone number format")
    @NotBlank(message = "Phone number is required")
    private String phone;
    @NotBlank(message = "Role is required")
    private Long societyId;
}
