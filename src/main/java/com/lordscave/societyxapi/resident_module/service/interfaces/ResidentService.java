package com.lordscave.societyxapi.resident_module.service.interfaces;

import com.lordscave.societyxapi.resident_module.dto.req.UpdateResident;
import com.lordscave.societyxapi.resident_module.dto.rsp.ResidentDetailsResponse;
import com.lordscave.societyxapi.resident_module.dto.rsp.ResidentResponse;

import java.util.List;

public interface ResidentService {
    ResidentDetailsResponse getResident(Long id);

    ResidentResponse updateResident(Long id, UpdateResident request);

    ResidentResponse patchResident(Long id, UpdateResident request);

    List<ResidentDetailsResponse> getAllResident();
}
