package com.lordscave.societyxapi.security_module.service.interfaces;

import com.lordscave.societyxapi.admin_module.dto.rsp.GeneralResponse;
import com.lordscave.societyxapi.security_module.dto.req.CreateVisit;
import com.lordscave.societyxapi.security_module.dto.rsp.VisitDetailsResponse;
import com.lordscave.societyxapi.security_module.dto.rsp.VisitResponse;

import java.util.List;

public interface VisitService {
    VisitResponse createVisit(CreateVisit createVisit);

    List<VisitDetailsResponse> getAllVisit();

    VisitDetailsResponse getVisit(Long id);

    GeneralResponse deleteVisit(Long id);

    GeneralResponse checkIn(Long visitId);

    GeneralResponse checkOut(Long visitId);
}
