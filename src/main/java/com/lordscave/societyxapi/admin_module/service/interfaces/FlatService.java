package com.lordscave.societyxapi.admin_module.service.interfaces;

import com.lordscave.societyxapi.admin_module.dto.req.CreateFlat;
import com.lordscave.societyxapi.admin_module.dto.req.UpdateFlat;
import com.lordscave.societyxapi.admin_module.dto.rsp.GeneralResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.FlatDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.FlatResponse;

import java.util.List;

public interface FlatService {
    FlatResponse createFlat(CreateFlat request);
    List<FlatDetailsResponse> getAllFlats();
    FlatDetailsResponse getFlat(Long id);
    FlatResponse updateFlat(Long id,UpdateFlat request);
    GeneralResponse deleteFlat(Long id);
    FlatResponse partialUpdateFlat(Long id, UpdateFlat request);
}
