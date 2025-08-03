package com.lordscave.societyxapi.utils.controller;

import com.lordscave.societyxapi.core.entity.enums.VerificationStatus;
import com.lordscave.societyxapi.utils.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class EmailVerificationController {

    @Autowired
    private EmailVerificationService emailVerificationService;

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("token") String token, Model model) {
        VerificationStatus status = emailVerificationService.verifyToken(token);

        if (status == VerificationStatus.SUCCESS) {
            return "verify-success";
        } else {
            String errorMessage;
            switch (status) {
                case TOKEN_NOT_FOUND -> errorMessage = "The verification link is invalid.";
                case TOKEN_EXPIRED -> errorMessage = "The verification link has expired.";
                default -> errorMessage = "Verification failed.";
            }
            model.addAttribute("error", errorMessage);
            return "verify-failed";
        }


    }
}
