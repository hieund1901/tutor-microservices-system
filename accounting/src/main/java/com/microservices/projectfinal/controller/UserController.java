package com.microservices.projectfinal.controller;

import com.microservices.projectfinal.annotation.UserId;
import com.microservices.projectfinal.config.KeycloakProvider;
import com.microservices.projectfinal.dto.CreateUserRequest;
import com.microservices.projectfinal.dto.LoginRequest;
import com.microservices.projectfinal.dto.LoginResponse;
import com.microservices.projectfinal.service.IAccountService;
import com.microservices.projectfinal.service.impl.KeycloakAdminClientService;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final KeycloakAdminClientService keycloakAdminClientService;
    private final KeycloakProvider keycloakProvider;
    private final IAccountService accountService;


    @PostMapping("/create")
    public ResponseEntity<CreateUserRequest> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        CreateUserRequest created = keycloakAdminClientService.createKeycloakUser(createUserRequest);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        AccessTokenResponse accessTokenResponse = null;

        try (Keycloak keycloak = keycloakProvider.newKeycloakBuilderWithPasswordCredentials(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ).build()) {
            accessTokenResponse = keycloak.tokenManager().getAccessToken();
            return ResponseEntity.ok(
                    LoginResponse.builder()
                            .accessToken(accessTokenResponse.getToken())
                            .build()
            );
        } catch (NotAuthorizedException exception) {
            log.error("Error while logging in: {}", exception.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getUserByToken(@UserId String userId) {
        return ResponseEntity.ok(accountService.getAccountByUserId(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) {
        return ResponseEntity.ok(accountService.getAccountByUserId(userId));
    }

    @GetMapping("/avatar")
    public ResponseEntity<InputStreamResource> getAvatar(@RequestParam String avatarPath) throws IOException {
        InputStream thumbnail = accountService.getAvatar(avatarPath);
        String contentType = null;
        if (avatarPath.endsWith(".png")) {
            contentType = "image/png";
        } else if (avatarPath.endsWith(".jpg") || avatarPath.endsWith(".jpeg")) {
            contentType = "image/jpeg";
        }


        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(new InputStreamResource(thumbnail));
    }
}
