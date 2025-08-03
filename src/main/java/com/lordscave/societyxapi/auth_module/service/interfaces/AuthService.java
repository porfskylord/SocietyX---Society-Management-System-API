package com.lordscave.societyxapi.auth_module.service.interfaces;

import com.lordscave.societyxapi.auth_module.dto.req.ForgotPassword;
import com.lordscave.societyxapi.auth_module.dto.req.LoginRequest;
import com.lordscave.societyxapi.auth_module.dto.rsp.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    LoginResponse loginUser(LoginRequest request);

    ResponseEntity<?>  logoutUser();

    void resetPassword(String token, String newPassword);

    ResponseEntity<?>  requestResetPassword();

    ResponseEntity<?> handleForgotPassword(@Valid ForgotPassword request);
}
