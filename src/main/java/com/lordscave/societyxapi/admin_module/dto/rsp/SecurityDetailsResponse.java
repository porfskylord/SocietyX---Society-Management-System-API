package com.lordscave.societyxapi.admin_module.dto.rsp;

import com.lordscave.societyxapi.core.entity.enums.ShiftType;

import java.time.LocalDateTime;
import java.util.Date;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class SecurityDetailsResponse {
    private Long id;
    private String fullName;
    private Integer age;
    private String gender;
    private Date dateOfBirth;
    private String imageUrl;
    private Long userId;
    private String email;
    private String phone;
    private Long gateId;
    private String gateName;
    private ShiftType shift;
    private Long societyId;
    private String societyName;
    private LocalDateTime createdAt;
}
