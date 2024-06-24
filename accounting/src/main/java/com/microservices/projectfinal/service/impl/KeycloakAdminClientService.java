package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.config.KeyCloakConfigData;
import com.microservices.projectfinal.config.KeycloakProvider;
import com.microservices.projectfinal.dto.CreateUserRequest;
import com.microservices.projectfinal.entity.AccountEntity;
import com.microservices.projectfinal.exception.ResponseException;
import com.microservices.projectfinal.security.PermissionType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class KeycloakAdminClientService {
    private final KeyCloakConfigData keyCloakConfigData;
    private final KeycloakProvider keycloakProvider;
    private final AccountService accountService;

    @Transactional
    public CreateUserRequest createKeycloakUser(CreateUserRequest createUserRequest) {
        UsersResource usersResource = keycloakProvider.getInstance().realm(keyCloakConfigData.getRealm()).users();
        CredentialRepresentation credential = createPasswordCredentials(createUserRequest.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(createUserRequest.getUsername());
        user.setEmail(createUserRequest.getEmail());
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        user.setCredentials(List.of(credential));
        user.setEnabled(true);
        user.setEmailVerified(true);
        Response response = usersResource.create(user);

        if (Objects.equals(201, response.getStatus())) {
            var userId = extractUserId(response);
            accountService.createAccount(createUserRequest, userId);
            assignRoleToUser(userId, PermissionType.STUDENT.getType());

            return createUserRequest;
        }

        throw new ResponseException("Error while creating user", HttpStatus.BAD_REQUEST);
    }

    public void changeUserRole(String userId, String roleName) {
        // Get Keycloak instance
        Keycloak keycloak = keycloakProvider.getInstance();

        // Get realm
        RealmResource realmResource = keycloak.realm(keyCloakConfigData.getRealm());

        // Get user
        UserResource userResource = realmResource.users().get(userId);

        // Get role
        RoleRepresentation roleRepresentation = realmResource.roles().get(roleName).toRepresentation();

        // Assign role to user
        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
    }

    public AccountEntity getAccount(String email) {
        return accountService.getAccountByEmail(email);
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        return credential;
    }

    private String extractUserId(Response response) {
        URI location = response.getLocation();
        String[] pathSegments = location.getPath().split("/");
        return pathSegments[pathSegments.length - 1];
    }

    public void assignRoleToUser(String userId, String roleName) {
        Keycloak keycloak = keycloakProvider.getInstance();
        RealmResource realmResource = keycloak.realm(keyCloakConfigData.getRealm());
        RoleRepresentation roleRepresentation = realmResource.roles().get(roleName).toRepresentation();
        UserResource userResource = realmResource.users().get(userId);
        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
    }

    public String getRole(String userId) {
        Keycloak keycloak = keycloakProvider.getInstance();
        RealmResource realmResource = keycloak.realm(keyCloakConfigData.getRealm());
        UserResource userResource = realmResource.users().get(userId);
        return userResource.roles().realmLevel().listEffective().stream().findFirst().get().getName();
    }


}
