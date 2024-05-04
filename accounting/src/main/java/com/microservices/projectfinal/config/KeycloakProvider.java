package com.microservices.projectfinal.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@Getter
public class KeycloakProvider {
    private final KeyCloakConfigData keyCloakConfigData;

    private static Keycloak keycloak = null;
    private static final String TOKEN_URL = "%s/realms/%s/protocol/openid-connect/token";

    public Keycloak getInstance() {
        if (keycloak == null) {
            return KeycloakBuilder.builder()
                    .realm(keyCloakConfigData.getRealm())
                    .serverUrl(keyCloakConfigData.getAuthServerUrl())
                    .clientId(keyCloakConfigData.getClientId())
                    .clientSecret(keyCloakConfigData.getCredentials().getSecret())
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    .build();
        }
        return keycloak;
    }

    public KeycloakBuilder newKeycloakBuilderWithPasswordCredentials(
            String username,
            String password
    ) {
        return KeycloakBuilder.builder()
                .realm(keyCloakConfigData.getRealm())
                .serverUrl(keyCloakConfigData.getAuthServerUrl())
                .clientId(keyCloakConfigData.getClientId())
                .clientSecret(keyCloakConfigData.getCredentials().getSecret())
                .username(username)
                .password(password);
    }

}
