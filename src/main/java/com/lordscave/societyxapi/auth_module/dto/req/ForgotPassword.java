package com.lordscave.societyxapi.auth_module.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPassword {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
}
