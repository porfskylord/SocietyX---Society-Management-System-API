package com.lordscave.societyxapi.admin_module.dto.rsp;


import java.time.LocalDateTime;

@lombok.Builder
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class GateDetailsResponse {
    private Long id;
    private String gateName;
    private Boolean isActive;
    private Long societyId;
    private String societyName;
    private LocalDateTime createdAt;
}
