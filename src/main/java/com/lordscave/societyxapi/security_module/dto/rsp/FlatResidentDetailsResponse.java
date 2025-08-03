package com.lordscave.societyxapi.security_module.dto.rsp;

import com.lordscave.societyxapi.core.entity.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor

@JsonPropertyOrder({
        "flatId", "flatNo", "isOccupied",
        "residentUserId", "residentId",
        "residentName", "residentPhoneNo",
        "residentEmail", "residentStatus"
})
public class FlatResidentDetailsResponse {
    private Long flatId;
    private String flatNo;

    @JsonProperty("isOccupied")
    private boolean isOccupied;

    private Long residentUserId;
    private Long residentId;
    private String residentName;
    private String residentPhoneNo;
    private String residentEmail;

    @JsonProperty("residentStatus")
    private UserStatus ResidentStatus;
}

