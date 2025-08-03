package com.lordscave.societyxapi.admin_module.service.interfaces;

import com.lordscave.societyxapi.admin_module.dto.req.CreateGate;
import com.lordscave.societyxapi.admin_module.dto.req.UpdateGate;
import com.lordscave.societyxapi.admin_module.dto.rsp.GateDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.GeneralResponse;

import java.util.List;

public interface GateService {
    GeneralResponse createGate(CreateGate request);
    GateDetailsResponse getGate(Long id);
    GeneralResponse updateGate(Long id, UpdateGate request);
    List<GateDetailsResponse> getAllGate();
    GeneralResponse deleteGate(Long id);
    GeneralResponse patchGate(Long id , UpdateGate request);
}
