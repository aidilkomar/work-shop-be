package com.asein.workshop.auth;

import com.asein.workshop.auth.dto.LoginRequest;
import com.asein.workshop.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@Validated
@RequestMapping("api/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequest req) {
        var token = authenticationService.login(req);
        if (token.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(false, "login failed", null, LocalDateTime.now()));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "login success", token, LocalDateTime.now()));
    }
}
