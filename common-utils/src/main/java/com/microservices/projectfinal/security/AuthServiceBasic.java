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
    private static final String X_USER_ID = "X-User-ID";

    @Override
    public Optional<AbstractAuthenticationToken> authenticate(HttpServletRequest request) {
        return extractBasicAuthHeader(request).flatMap(credentials -> check(credentials, request));
    }

    private Optional<AbstractAuthenticationToken> check(Credentials credentials, HttpServletRequest request) {
        if (credentials.getUsername().equals(username) && credentials.getPassword().equals(password)) {
            var userId = request.getHeader(X_USER_ID);
            if (userId == null) {
                throw new IllegalStateException("Can't get user id of current context");
            }
            var authorDetail = AuthorDetails.builder()
                    .username(userId)
                    .authorities(List.of(new SimpleGrantedAuthority(PermissionType.ADMIN.getType())))
                    .build();
            return Optional.of(new UsernamePasswordAuthenticationToken(authorDetail, null, authorDetail.getAuthorities()));
        }
        return Optional.empty();
    }
}
