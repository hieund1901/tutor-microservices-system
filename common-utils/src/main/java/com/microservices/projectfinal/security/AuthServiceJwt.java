package com.microservices.projectfinal.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceJwt extends AuthService {
    private final JwtTokenValidator jwtTokenValidator;

    public AuthServiceJwt(JwtTokenValidator jwtTokenValidator) {
        this.jwtTokenValidator = jwtTokenValidator;
    }

    @Override
    public Optional<Authentication> authenticate(HttpServletRequest request) {
        return extractBearerTokenHeader(request, jwtTokenValidator).flatMap(this::verify);
    }

    private Optional<? extends Authentication> verify(AbstractAuthenticationToken accessToken) {
        return Optional.of(accessToken);
    }
}
