package com.microservices.projectfinal.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtConverter implements Converter<DecodedJWT, AbstractAuthenticationToken> {

    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";
    private static final String SCOPE_CLAIM = "scope";
    private static final String USERNAME_CLAIM = "preferred_username";
    private static final String EMAIL_CLAIM = "email";
    private static final String DEFAULT_ROLE_PREFIX = "ROLE_";
    private static final String DEFAULT_SCOPE_PREFIX = "SCOPE_";
    private static final String SCOPE_SEPARATOR = " ";

    private final AuthorDetailsService authorDetailsService;

    @Override
    public AbstractAuthenticationToken convert(DecodedJWT source) {
        var claims = source.getClaims();

        Collection<GrantedAuthority> authorities = getAuthoritiesFromJwt(claims);
        return Optional.ofNullable(
                authorDetailsService.loadUserByUsername(claims.get(USERNAME_CLAIM).asString())).map(userDetails -> {
            ((AuthorDetails) userDetails).setAuthorities(authorities);
            ((AuthorDetails) userDetails).setEmail(claims.get(EMAIL_CLAIM).asString());
            return new UsernamePasswordAuthenticationToken(userDetails, "N/A", authorities);
        }).orElseThrow(() -> new BadCredentialsException("User could not be found!"));
    }

    private Collection<GrantedAuthority> getAuthoritiesFromJwt(Map<String, Claim> jwt) {
        return getCombinedAuthorities(jwt).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Collection<String> getCombinedAuthorities(Map<String, Claim> jwt) {
        Collection<String> authorities = getRoles(jwt);
        authorities.addAll(getScopes(jwt));
        return authorities;
    }

    @SuppressWarnings("unchecked")
    private Collection<String> getRoles(Map<String, Claim> jwt) {
        Object roles = jwt.get(REALM_ACCESS_CLAIM).asMap().get(ROLES_CLAIM);

        if (roles instanceof Collection) {
            return ((Collection<String>) roles).stream()
                    .map(authority -> DEFAULT_ROLE_PREFIX + authority.toUpperCase())
                    .collect(Collectors.toSet());
        }

        return Collections.emptyList();
    }

    private Collection<String> getScopes(Map<String, Claim> jwt) {
        String scopes = jwt.get(SCOPE_CLAIM).asString();
        return Arrays.stream(scopes.split(SCOPE_SEPARATOR))
                .map(scope -> DEFAULT_SCOPE_PREFIX + scope.toUpperCase())
                .collect(Collectors.toSet());
    }
}
