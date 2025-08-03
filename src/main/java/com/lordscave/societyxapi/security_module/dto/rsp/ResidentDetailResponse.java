package com.lordscave.societyxapi.security_module.dto.rsp;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class ResidentDetailResponse {
    private Long residentUserId;
    private String residentName;
}
