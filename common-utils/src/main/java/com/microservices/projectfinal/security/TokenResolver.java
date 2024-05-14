package com.microservices.projectfinal.security;

import jakarta.servlet.http.HttpServletRequest;

public interface TokenResolver {
    TutorTokenAuthenticationToken resolve(HttpServletRequest request);
}
