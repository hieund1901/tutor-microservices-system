package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.config.KeyCloakConfigData;
import com.microservices.projectfinal.config.KeycloakProvider;
import com.microservices.projectfinal.dto.CreateUserRequest;
import com.microservices.projectfinal.entity.AccountEntity;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class KeycloakAdminClientService {
//    private final KeyCloakConfigData keyCloakConfigData;
//    private final KeycloakProvider keycloakProvider;
//    private final AccountService accountService;
//
//    public CreateUserRequest createKeycloakUser(CreateUserRequest createUserRequest) {
//        UsersResource usersResource = keycloakProvider.getInstance().realm(keyCloakConfigData.getRealm()).users();
//        CredentialRepresentation credential = createPasswordCredentials(createUserRequest.getPassword());
//        UserRepresentation user = new UserRepresentation();
//        user.setUsername(createUserRequest.getUsername());
//        user.setEmail(createUserRequest.getEmail());
//        user.setFirstName(createUserRequest.getFirstName());
//        user.setLastName(createUserRequest.getLastName());
//        user.setCredentials(List.of(credential));
//        user.setEnabled(true);
//        user.setEmailVerified(true);
//        Response response = usersResource.create(user);
//
//        if (Objects.equals(201, response.getStatus())) {
//            accountService.createAccount(createUserRequest);
//            return createUserRequest;
//        }
//
//        return null;
//    }
//
//    public void changeUserRole(String userId, String roleName) {
//        // Get Keycloak instance
//        Keycloak keycloak = keycloakProvider.getInstance();
//
//        // Get realm
//        RealmResource realmResource = keycloak.realm(keyCloakConfigData.getRealm());
//
//        // Get user
//        UserResource userResource = realmResource.users().get(userId);
//
//        // Get role
//        RoleRepresentation roleRepresentation = realmResource.roles().get(roleName).toRepresentation();
//
//        // Assign role to user
//        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
//    }
//
//    public AccountEntity getAccount(String email) {
//        return accountService.getAccount(email);
//    }
//
//    private static CredentialRepresentation createPasswordCredentials(String password) {
//        CredentialRepresentation credential = new CredentialRepresentation();
//        credential.setType(CredentialRepresentation.PASSWORD);
//        credential.setValue(password);
//        credential.setTemporary(false);
//        return credential;
//    }
}
