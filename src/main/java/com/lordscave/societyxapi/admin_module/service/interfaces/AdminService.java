package com.lordscave.societyxapi.admin_module.service.interfaces;

import com.lordscave.societyxapi.admin_module.dto.req.UpdateAdminProfile;
import com.lordscave.societyxapi.admin_module.dto.rsp.AdminDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.AdminResponse;

public interface AdminService {
    AdminDetailsResponse getAdmin(Long id);
    AdminResponse updateAdmin(Long id, UpdateAdminProfile request);
    AdminResponse patchAdmin(Long id, UpdateAdminProfile request);
}
