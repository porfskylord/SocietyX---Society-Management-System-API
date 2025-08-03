package com.lordscave.societyxapi.admin_module.dto.req;

@lombok.Data
@lombok.Builder
public class UpdateSociety {
    private String name;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String imageUrl;
}
