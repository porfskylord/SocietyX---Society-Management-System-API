package com.lordscave.societyxapi.utils;

import com.lordscave.societyxapi.core.entity.Visit;
import com.lordscave.societyxapi.core.entity.enums.VisitStatus;
import com.lordscave.societyxapi.core.exceptions.ResourceNotFoundException;
import com.lordscave.societyxapi.core.repository.VisitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VisitApprovalService {

    @Autowired
    private VisitRepo visitRepo;

    @Autowired
    private MailService mailService;

    public void handleVisitApproval(Long visitId, String action) {
        Visit visit = visitRepo.findById(visitId).orElseThrow(() ->
                new IllegalArgumentException("Visit not found with ID: " + visitId));

        switch (action.toUpperCase()) {
            case "APPROVE":
                visit.setStatus(VisitStatus.APPROVED);
                visit.setUpdatedAt(LocalDateTime.now());
                visitRepo.save(visit);

                mailService.notifyAssignedSecurity(visit);
                break;

            case "REJECT":
                visit.setStatus(VisitStatus.REJECTED);
                visit.setUpdatedAt(LocalDateTime.now());
                visitRepo.save(visit);
                break;

            default:
                throw new IllegalArgumentException("Invalid action type: " + action);
        }
    }
}
