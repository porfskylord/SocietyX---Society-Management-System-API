package com.lordscave.societyxapi.security_module.dto.rsp;

import java.time.LocalDateTime;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class VisitDetailsResponse {
    private Long id;
    private String name;
    private String phoneNumber;
    private  String email;
    private String address;

    private String purpose;

    private String vehicleNumber;

    private String visitStatus;

    private String permitCode;
    private LocalDateTime permitCodeExpiry;

    private boolean checkedIn;
    private LocalDateTime checkInTime;
    private boolean checkedOut;
    private LocalDateTime checkOutTime;

    private Long flatId;
    private String flatNo;
    private Long residentUserId;
    private String residentName;
    private Long securityUserId;
    private String securityName;
    private Long gateId;
    private String gateName;

    private LocalDateTime createdAt;
}
