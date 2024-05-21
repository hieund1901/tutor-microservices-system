package com.microservices.projectfinal.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceJwt extends AuthService {
    private final JwtTokenValidator jwtTokenValidator;

    public AuthServiceJwt(JwtTokenValidator jwtTokenValidator) {
        this.jwtTokenValidator = jwtTokenValidator;
    }

    @Override
    public Optional<AbstractAuthenticationToken> authenticate(HttpServletRequest request) {
        return extractBearerTokenHeader(request, jwtTokenValidator);
    }

}
