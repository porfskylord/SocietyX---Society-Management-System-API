package com.lordscave.societyxapi.utils.controller;

import com.lordscave.societyxapi.auth_module.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class PasswordResetController {

    @Autowired
    private final AuthService authService;

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(
            @RequestParam("token") String token,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            RedirectAttributes redirectAttributes
    ) {
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
            return "redirect:/auth/reset-password?token=" + token;
        }

        try {
            authService.resetPassword(token, newPassword);
            redirectAttributes.addFlashAttribute("success", "Your password has been changed successfully.");
            return "redirect:/auth/reset-password-success";
        } catch (Exception e) {
            log.error("Password reset failed: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/auth/reset-password?token=" + token;
        }
    }

}
