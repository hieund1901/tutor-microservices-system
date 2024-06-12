package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;

@JsonRootName("login")
@Builder
@Getter
public class LoginResponse {
    private final String accessToken;
}
