package com.microservices.projectfinal.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceBasic extends AuthService {
    @Value("${spring.security.user.name}")
    private String username;
    @Value("${spring.security.user.password}")
    private String password;

    @Override
    public Optional<AbstractAuthenticationToken> authenticate(HttpServletRequest request) {
        return extractBasicAuthHeader(request).flatMap(this::check);
    }

    private Optional<AbstractAuthenticationToken> check(Credentials credentials) {
        if (credentials.getUsername().equals(username) && credentials.getPassword().equals(password)) {
            return Optional.of(new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword(), List.of(new SimpleGrantedAuthority("ADMIN"))));
        }
        return Optional.empty();
    }
}
