package com.lordscave.societyxapi.auth_module.dto.rsp;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class LoginResponse {
    private String message;
    private String token;
    private String email;
    private String role;
}

