package com.lordscave.societyxapi.admin_module.service.interfaces;

import com.lordscave.societyxapi.admin_module.dto.req.UpdateSecurity;
import com.lordscave.societyxapi.admin_module.dto.rsp.GeneralResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.SecurityDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.SecurityResponse;

import java.util.List;

public interface SecurityManagementService {
    List<SecurityDetailsResponse> getAllSecurity();
    SecurityDetailsResponse getSecurity(Long id);
    SecurityResponse updateSecurity(Long id, UpdateSecurity request);
    SecurityResponse patchSecurity(Long id, UpdateSecurity request);
    GeneralResponse deleteSecurity(Long id);
}
