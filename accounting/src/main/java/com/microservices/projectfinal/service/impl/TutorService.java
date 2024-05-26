package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.dto.TutorCreateRequest;
import com.microservices.projectfinal.dto.TutorResponseDTO;
import com.microservices.projectfinal.entity.AccountEntity;
import com.microservices.projectfinal.entity.TutorEntity;
import com.microservices.projectfinal.exception.ResponseException;
import com.microservices.projectfinal.repository.AccountRepository;
import com.microservices.projectfinal.repository.TutorRepository;
import com.microservices.projectfinal.security.AuthenticationFacade;
import com.microservices.projectfinal.security.PermissionType;
import com.microservices.projectfinal.service.ITutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TutorService implements ITutorService {
    private final TutorRepository tutorRepository;
    private final AuthenticationFacade authenticationFacade;
    private final AccountService accountService;
    private final KeycloakAdminClientService keycloakAdminClientService;
    private final AccountRepository accountRepository;

    @Override
    public TutorResponseDTO createTutor(String userId, TutorCreateRequest tutorCreateRequest) {
        AccountEntity account = accountRepository.findByUserId(userId).orElseThrow(
                () -> new ResponseException("Account not found", HttpStatus.NOT_FOUND)
        );

        TutorEntity tutor = tutorRepository.getByAccountId(account.getId());

        if (tutor != null) {
            return buildTutorResponse(tutor);
        }

        TutorEntity tutorEntity = TutorEntity.builder()
                .subject(tutorCreateRequest.getSubject())
                .teachFee(tutorCreateRequest.getTeachFee())
                .account(account)
                .timePerOnePurchase(tutorCreateRequest.getTimePerSession())
                .build();
        var tutorCreated = tutorRepository.save(tutorEntity);
        keycloakAdminClientService.assignRoleToUser(account.getUserId(), PermissionType.TUTOR.getType());
        return buildTutorResponse(tutorCreated);
    }

    private TutorResponseDTO buildTutorResponse(TutorEntity tutorEntity) {
        AccountEntity account = tutorEntity.getAccount();
        return TutorResponseDTO.builder()
                .teachFee(tutorEntity.getTeachFee())
                .subject(tutorEntity.getSubject())
                .avatarPath(account.getAvatarPath())
                .address(account.getAddress())
                .email(account.getEmail())
                .firstName(account.getFirstname())
                .lastName(account.getLastname())
                .build();
    }

    @Override
    public TutorResponseDTO updateTutor(long id, TutorCreateRequest tutorCreateRequest) {
        return null;
    }

    @Override
    public TutorResponseDTO getTutor(long id) {
        return null;
    }

    @Override
    public void deleteTutor(long id) {

    }

    @Override
    public TutorResponseDTO getTutorByUserId(String userId) {
        AccountEntity account = accountRepository.findByUserId(userId).orElseThrow(
                () -> new ResponseException("Account not found", HttpStatus.NOT_FOUND)
        );

        TutorEntity tutor = tutorRepository.getByAccountId(account.getId());
        if (tutor != null) {
            return buildTutorResponse(tutor);
        }
        return null;
    }

//    @Override
//    public TutorResponse getTutorByEmail(String email) {
//        AccountEntity account = accountService.getAccountByEmail(email);
//        TutorEntity tutor = tutorRepository.getByAccountId(account.getId());
//        if (tutor != null) {
//            return buildTutorResponse(tutor);
//        }
//        return null;
//    }
//
//    @Override
//    public TutorResponse getTutorByAccountId(long accountId) {
//        TutorEntity tutor = tutorRepository.getByAccountId(accountId);
//        if (tutor != null) {
//            return buildTutorResponse(tutor);
//        }
//        return null;
//    }
}
