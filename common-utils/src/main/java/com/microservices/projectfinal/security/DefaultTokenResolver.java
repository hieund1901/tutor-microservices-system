package com.microservices.projectfinal.security;

import jakarta.servlet.http.HttpServletRequest;

public class DefaultTokenResolver implements TokenResolver {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String USER_ID_HEADER = "X-User-Id";
    public DefaultTokenResolver() {
    }

    @Override
    public TutorTokenAuthenticationToken resolve(HttpServletRequest request) {
        return null;
    }
}
