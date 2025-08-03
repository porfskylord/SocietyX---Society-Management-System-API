package com.lordscave.societyxapi.security_module.service.interfaces;

import com.lordscave.societyxapi.security_module.dto.rsp.FlatDetailResponse;
import com.lordscave.societyxapi.security_module.dto.rsp.FlatResidentDetailsResponse;
import com.lordscave.societyxapi.security_module.dto.rsp.ResidentDetailResponse;

import java.util.List;

public interface FlatResidentService {
    List<FlatResidentDetailsResponse>  getAllFlatResidentDetails();

    FlatResidentDetailsResponse getFlatResidentDetailsByFlatId(Long id);

    FlatResidentDetailsResponse getFlatResidentDetailsByResidentUserId(Long id);

    FlatResidentDetailsResponse getFlatResidentDetailsByFlatNo(String flatNo);

    FlatResidentDetailsResponse getFlatResidentDetailsByResidentName(String residentName);

    List<FlatDetailResponse> getAllFlatDetails();

    List<ResidentDetailResponse> getAllResidentDetails();
}
