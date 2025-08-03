package com.lordscave.societyxapi.utils.controller;

import com.lordscave.societyxapi.core.entity.Visit;
import com.lordscave.societyxapi.core.repository.VisitRepo;
import com.lordscave.societyxapi.core.security.JwtService;
import com.lordscave.societyxapi.utils.VisitApprovalService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/visit")
@RequiredArgsConstructor
public class VisitApproval {

    private final VisitApprovalService visitApprovalService;
    private final JwtService jwtService;
    @Autowired
    private VisitRepo visitRepo;

    @GetMapping("/approve")
    public String approveVisit(@RequestParam("token") String token, Model model) {
        return processToken(token, model, "APPROVE");
    }

    @GetMapping("/reject")
    public String rejectVisit(@RequestParam("token") String token, Model model) {
        return processToken(token, model, "REJECT");
    }

    private String processToken(String token, Model model, String expectedAction) {
        try {
            if (jwtService.isTokenExpired(token)) {
                model.addAttribute("message", "This link has expired.");
                return "expired";
            }

            Claims claims = jwtService.extractAllClaims(token);
            Long visitId = claims.get("visitId", Long.class);
            String action = claims.get("action", String.class);


            if (!expectedAction.equals(action)) {
                model.addAttribute("message", "Invalid action in token.");
                return "error";
            }


            visitApprovalService.handleVisitApproval(visitId, action);
            model.addAttribute("message", "Visit " + action.toLowerCase() + "ed successfully.");
            return action.equals("APPROVE") ? "approved" : "rejected";

        } catch (Exception e) {
            log.error("Token processing failed", e);
            model.addAttribute("message", "Invalid or malformed token.");
            return "error";
        }
    }


    @GetMapping("/validate")
    public String validatePermit(@RequestParam String code, Model model) {
        Visit visit = visitRepo.findByPermitCode(code).orElse(null);
        if (visit == null || visit.getPermitCodeExpiry().isBefore(LocalDateTime.now())) {
            model.addAttribute("valid", false);
            return "visit_status";
        }

        Duration duration = Duration.between(LocalDateTime.now(), visit.getPermitCodeExpiry());
        model.addAttribute("valid", true);
        model.addAttribute("visitorName", visit.getName());
        model.addAttribute("purpose", visit.getPurpose());
        model.addAttribute("vehicleNumber", visit.getVehicleNumber());
        model.addAttribute("residentName", visit.getResident().getFullName());
        model.addAttribute("flatNo", visit.getResident().getFlat().getFlatNo());
        model.addAttribute("expiryTime", visit.getPermitCodeExpiry());
        model.addAttribute("timeLeft", formatDuration(duration));
        return "visit_status";
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        return hours + " hrs " + minutes + " mins";
    }






}
