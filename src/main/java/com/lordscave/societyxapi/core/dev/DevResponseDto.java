package com.lordscave.societyxapi.core.dev;

import java.time.LocalDateTime;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class DevResponseDto {
    private String message;
    private Long id;
    private String email;
    private String phone;
    private String role;
    private String status;
    private Long societyId;
    private String societyName;
    private LocalDateTime createdAt;
}
