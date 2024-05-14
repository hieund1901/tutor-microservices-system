package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonRootName("user")
public class LoginRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
