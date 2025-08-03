package com.lordscave.societyxapi.admin_module.dto.req;

import com.lordscave.societyxapi.core.entity.enums.ShiftType;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

@lombok.Data
public class UpdateSecurity {
    private String fullName;
    private Integer age;
    private String gender;
    private Date dateOfBirth;
    private String imageUrl;
    @Pattern(regexp = "^(DAY|EVENING|NIGHT)$", message = "Invalid shift type")
    private ShiftType shift;
    private Long gateId;

}
