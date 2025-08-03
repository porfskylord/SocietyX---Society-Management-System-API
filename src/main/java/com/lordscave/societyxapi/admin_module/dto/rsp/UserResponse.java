package com.lordscave.societyxapi.admin_module.dto.rsp;

import java.time.LocalDateTime;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class UserResponse {
    private String message;
    private Long id;
    private String email;
    private String role;
    private String status;
    private String phone;
    private Long societyId;
    private String societyName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
