package com.lordscave.societyxapi.admin_module.dto.req;

import com.lordscave.societyxapi.core.entity.enums.FlatType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@lombok.Data
public class CreateFlat {
    @NotBlank(message = "Flat number is required")
    private String flatNo;
    @NotBlank(message = "Floor number is required")
    private Integer floorNo;
    @NotBlank(message = "Building number is required")
    private String buildingNo;
    @NotBlank(message = "Is occupied is required")
    private Boolean isOccupied;
    @NotBlank(message = "Block is required")
    private Character block;
    @Pattern(regexp = "^(STUDIO|ONE_BHK|TWO_BHK|THREE_BHK|FOUR_BHK|PENTHOUSE)$", message = "Invalid flat type")
    private FlatType flatType;
    @NotBlank(message = "Society ID is required")
    private Long societyId;
}
