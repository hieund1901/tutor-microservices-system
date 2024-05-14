package com.microservices.projectfinal.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TutorTokenAuthenticationToken extends AbstractAuthenticationToken {
    private final String tokenType;
    private final String token;
    private Object credential;
    private Map<String, Object> attributes = new HashMap<>();

    public TutorTokenAuthenticationToken(String tokenType, String token, Object tenantPrincipal, Map<String, Object> attributes) {
        super(Collections.emptyList());
        this.tokenType = tokenType;
        this.token = token;
        this.credential = tenantPrincipal;
        this.attributes.putAll(attributes);
    }



    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
