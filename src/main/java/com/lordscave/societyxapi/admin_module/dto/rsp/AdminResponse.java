package com.lordscave.societyxapi.admin_module.dto.rsp;

import java.time.LocalDateTime;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class AdminResponse {
    private String message;
    private Long id;
    private Long userId;
    private String email;
    private String phone;
    private String fullName;
    private String designation;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
