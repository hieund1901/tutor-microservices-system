package com.microservices.projectfinal.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeyCloakConfigData {
    private String authServerUrl;
    private String realm;
    private String clientId;
    private String adminClientId;
    private Credentials credentials;

    @Data
    @NoArgsConstructor
    static class Credentials {
        private String secret;
        private boolean useResourceRoleMappings;
        private boolean bearerOnly;
    }
}
