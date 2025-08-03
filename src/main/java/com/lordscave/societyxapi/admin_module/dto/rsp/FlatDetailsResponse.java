package com.lordscave.societyxapi.admin_module.dto.rsp;

import com.lordscave.societyxapi.core.entity.enums.FlatType;

import java.time.LocalDateTime;

@lombok.Builder
@lombok.Data
public class FlatDetailsResponse {
    private Long id;
    private String flatNo;
    private FlatType flatType;
    private Boolean isOccupied;
    private Integer floorNo;
    private String buildingNo;
    private Character block;
    private Long societyId;
    private String societyName;
    private LocalDateTime createdAt;
}
