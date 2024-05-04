package com.microservices.projectfinal.controller;

import com.microservices.projectfinal.config.KeycloakProvider;
import com.microservices.projectfinal.dto.CreateUserRequest;
import com.microservices.projectfinal.dto.LoginRequest;
import com.microservices.projectfinal.service.impl.KeycloakAdminClientService;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final KeycloakAdminClientService keycloakAdminClientService;
    private final KeycloakProvider keycloakProvider;


    @PostMapping("/create")
    public ResponseEntity<CreateUserRequest> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
//        CreateUserRequest created = keycloakAdminClientService.createKeycloakUser(createUserRequest);
        return ResponseEntity.ok(createUserRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        AccessTokenResponse accessTokenResponse = null;

        try (Keycloak keycloak = keycloakProvider.newKeycloakBuilderWithPasswordCredentials(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ).build()) {
            accessTokenResponse = keycloak.tokenManager().getAccessToken();
            return ResponseEntity.ok(accessTokenResponse);
        } catch (NotAuthorizedException exception) {
            log.error("Error while logging in: {}", exception.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenResponse);
        }
    }
}
