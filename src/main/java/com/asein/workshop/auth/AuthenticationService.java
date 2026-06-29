package com.asein.workshop.auth;

import com.asein.workshop.auth.dto.LoginRequest;
import jakarta.validation.Valid;

public interface AuthenticationService {
    String login(@Valid LoginRequest req);
}
