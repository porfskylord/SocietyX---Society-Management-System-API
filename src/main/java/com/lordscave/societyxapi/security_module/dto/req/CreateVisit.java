package com.lordscave.societyxapi.security_module.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@lombok.Data
public class CreateVisit {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Invalid contact number format")
    private String contactNumber;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Purpose is required")
    private String purpose;

    private LocalDateTime visitDate;
    @NotBlank(message = "Visit hours is required")
    private Integer visitHours;

    private String vehicleNumber;

    @NotBlank(message = "ResidentName is required")
    private String ResidentName;
    @NotBlank(message = "SecurityName is required")
    private String SecurityName;
    @NotBlank(message = "GateName is required")
    private String GateName;
}
