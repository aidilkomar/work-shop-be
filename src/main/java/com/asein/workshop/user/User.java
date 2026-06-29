package com.asein.workshop.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String role;
    private Boolean isActive;
}
