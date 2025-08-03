package com.lordscave.societyxapi.security_module.dto.req;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.Builder
public class VIsitMailData {
    private Long visitId;
    private String toEmail;
    private String residentName;
    private String flatNo;
    private String visitorName;
    private String purpose;
    private String contactNumber;
    private LocalDateTime createdAt;
}
