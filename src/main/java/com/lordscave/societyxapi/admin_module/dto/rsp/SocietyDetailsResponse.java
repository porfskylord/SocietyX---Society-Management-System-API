package com.lordscave.societyxapi.admin_module.dto.rsp;

import java.time.LocalDateTime;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class SocietyDetailsResponse {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String imageUrl;
    private LocalDateTime createdAt;
}
