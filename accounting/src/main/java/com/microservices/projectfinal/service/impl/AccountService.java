package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.config.KeyCloakConfigData;
import com.microservices.projectfinal.config.KeycloakProvider;
import com.microservices.projectfinal.dto.AccountResponseDTO;
import com.microservices.projectfinal.dto.AppointmentResponseDTO;
import com.microservices.projectfinal.dto.CreateUserRequest;
import com.microservices.projectfinal.entity.AccountEntity;
import com.microservices.projectfinal.entity.BookingEntity;
import com.microservices.projectfinal.exception.ResponseException;
import com.microservices.projectfinal.mapper.AccountMapper;
import com.microservices.projectfinal.repository.AccountRepository;
import com.microservices.projectfinal.repository.BookingRepository;
import com.microservices.projectfinal.service.IAccountService;
import com.microservices.projectfinal.service.IAvailabilityScheduleService;
import com.microservices.projectfinal.util.MediaFileUtils;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AccountService implements IAccountService {
    private final AccountRepository accountRepository;
    private final BookingRepository bookingRepository;
    private final IAvailabilityScheduleService availabilityScheduleService;

    private final AccountMapper accountMapper;
    private final KeycloakProvider keycloakProvider;
    private final KeyCloakConfigData keyCloakConfigData;
    private final MediaFileUtils mediaFileUtils;


    @Override
    public void createAccount(CreateUserRequest user, String userId) {
        AccountEntity account = accountMapper.fromRegisterToAccountEntity(user);
        account.setUserId(userId);
        accountRepository.save(account);
    }

    @Override
    public void updateAccount() {

    }

    @Override
    public void deleteAccount() {

    }

    @Override
    public AccountEntity getAccountByEmail(String email) {
        var account = accountRepository.findByEmail(email);
        return account.orElseThrow(
                () -> new ResponseException("Account not found", HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public AccountResponseDTO getAccountByUserId(String id) {
        var account = accountRepository.findByUserId(id).orElseThrow(
                () -> new ResponseException("Account not found", HttpStatus.NOT_FOUND)
        );

        var roles = getRolesById(account.getUserId());
        var realRoles = roles.stream().map(
                role -> {
                    if (Objects.equals(role.getName(), "student")) {
                        return "STUDENT";
                    } else if (Objects.equals(role.getName(), "tutor")) {
                        return "TUTOR";
                    } else if (Objects.equals(role.getName(), "admin")) {
                        return "ADMIN";
                    }
                    return null;
                }
        ).filter(Objects::nonNull).findFirst();
        return AccountResponseDTO.builder()
                .id(account.getId())
                .email(account.getEmail())
                .firstname(account.getFirstname())
                .lastname(account.getLastname())
                .birth(account.getBirth())
                .userId(account.getUserId())
                .gender(String.valueOf(account.getGender()))
                .address(account.getAddress())
                .avatarPath(account.getAvatarPath())
                .role(realRoles.orElse(null))
                .createdAt(account.getCreatedAt())
                .modifiedAt(account.getModifiedAt())
                .build();
    }

    @Override
    public void getAllAccounts() {

    }

    @Override
    public AccountEntity getById(Long id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new ResponseException("Account not found", HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public InputStream getAvatar(String avatarPath) {
        return mediaFileUtils.getImage(avatarPath);
    }

    @Override
    public AppointmentResponseDTO getAppointments(String userId, int page, int size) {
        var bookings = bookingRepository.findByStudentIdAndStatusIs(userId, BookingEntity.BookingStatus.CONFIRMED, PageRequest.of(page - 1, size));
        var bookingIds = bookings.stream().map(BookingEntity::getAvailabilityId).toList();
        var availabilities = availabilityScheduleService.getByIds(bookingIds);
        return AppointmentResponseDTO.builder()
                .availabilities(availabilities.getAvailabilities())
                .page(page)
                .size(size)
                .totalPages(bookings.getTotalPages())
                .total(bookings.getTotalElements())
                .build();
    }

    public List<RoleRepresentation> getRolesById(String userId) {
        Keycloak keycloak = keycloakProvider.getInstance();
        RealmResource realmResource = keycloak.realm(keyCloakConfigData.getRealm());
        UserResource userResource = realmResource.users().get(userId);
        return userResource.roles().getAll().getRealmMappings();
    }
}
