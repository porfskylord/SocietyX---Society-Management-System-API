package com.lordscave.societyxapi.resident_module.dto.req;

import java.util.Date;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class UpdateResident {
    private String fullName;
    private Integer age;
    private String gender;
    private Date dateOfBirth;
    private String imageUrl;
    private Long flatId;
}
