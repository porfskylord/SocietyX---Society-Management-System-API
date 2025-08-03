package com.lordscave.societyxapi.admin_module.dto.req;

import com.lordscave.societyxapi.core.entity.enums.FlatType;
import jakarta.validation.constraints.Pattern;

@lombok.Data
public class UpdateFlat {
    private String flatNo;
    private Integer floorNo;
    private String buildingNo;
    private Boolean isOccupied;
    private Character block;
    @Pattern(regexp = "^(STUDIO|ONE_BHK|TWO_BHK|THREE_BHK|FOUR_BHK|PENTHOUSE)$", message = "Invalid flat type")
    private FlatType flatType;
}
