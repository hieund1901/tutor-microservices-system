package com.microservices.projectfinal.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
