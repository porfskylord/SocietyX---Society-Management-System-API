package com.lordscave.societyxapi.admin_module.dto.rsp;

import com.lordscave.societyxapi.core.entity.enums.FlatType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FlatResponse {
    private String message;
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
    private LocalDateTime updatedAt;
}
