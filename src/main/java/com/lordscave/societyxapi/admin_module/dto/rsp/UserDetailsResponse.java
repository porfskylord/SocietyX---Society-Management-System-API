package com.lordscave.societyxapi.admin_module.dto.rsp;


import java.time.LocalDateTime;

@lombok.Builder
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class UserDetailsResponse {
    private Long id;
    private String email;
    private String phone;
    private String role;
    private String status;
    private Long societyId;
    private String societyName;
    private LocalDateTime createdAt;
}
