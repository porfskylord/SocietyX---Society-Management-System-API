package com.lordscave.societyxapi.admin_module.service.interfaces;

import com.lordscave.societyxapi.admin_module.dto.req.UpdateSociety;
import com.lordscave.societyxapi.admin_module.dto.rsp.GeneralResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.SocietyDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.SocietyResponse;

public interface SocietyService {
    SocietyResponse createSociety(UpdateSociety request);
    SocietyDetailsResponse getSociety();
    SocietyResponse updateSociety(UpdateSociety request);
    GeneralResponse deleteSociety();
    SocietyResponse patchSociety(UpdateSociety request);
}
