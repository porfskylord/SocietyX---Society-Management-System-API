package com.lordscave.societyxapi.resident_module.dto.rsp;

import com.lordscave.societyxapi.core.entity.enums.FlatType;

import java.time.LocalDateTime;
import java.util.Date;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class ResidentResponse {
    private String message;
    private Long id;
    private String fullName;
    private Integer age;
    private String gender;
    private Date dateOfBirth;
    private String imageUrl;
    private Long userId;
    private String email;
    private String phone;
    private Long flatId;
    private String flatNo;
    private FlatType flatType;
    private Integer floorNo;
    private String buildingNo;
    private Character block;
    private Long societyId;
    private String societyName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
