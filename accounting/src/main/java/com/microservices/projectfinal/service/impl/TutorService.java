package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.dto.TutorCreateRequest;
import com.microservices.projectfinal.dto.TutorResponse;
import com.microservices.projectfinal.entity.AccountEntity;
import com.microservices.projectfinal.entity.TutorEntity;
import com.microservices.projectfinal.repository.TutorRepository;
import com.microservices.projectfinal.security.AuthenticationFacade;
import com.microservices.projectfinal.service.ITutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TutorService implements ITutorService {
    private final TutorRepository tutorRepository;
    private final AuthenticationFacade authenticationFacade;
    private final AccountService accountService;

    @Override
    public TutorResponse createTutor(TutorCreateRequest tutorCreateRequest) {
        var email = authenticationFacade.getEmail();
        AccountEntity account = accountService.getAccount(email);

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
        return buildTutorResponse(tutorCreated);
    }

    private TutorResponse buildTutorResponse(TutorEntity tutorEntity) {
        AccountEntity account = tutorEntity.getAccount();
        return TutorResponse.builder()
                .teachFee(tutorEntity.getTeachFee())
                .subject(tutorEntity.getSubject())
                .timePerOnePurchase(tutorEntity.getTimePerOnePurchase())
                .avatarPath(account.getAvatarPath())
                .address(account.getAddress())
                .email(account.getEmail())
                .firstName(account.getFirstname())
                .lastName(account.getLastname())
                .accountId(account.getId())
                .build();
    }

    @Override
    public TutorResponse updateTutor(long id, TutorCreateRequest tutorCreateRequest) {
        return null;
    }

    @Override
    public TutorResponse getTutor(long id) {
        return null;
    }

    @Override
    public void deleteTutor(long id) {

    }

    @Override
    public TutorResponse getTutorByEmail(String email) {
        AccountEntity account = accountService.getAccount(email);
        TutorEntity tutor = tutorRepository.getByAccountId(account.getId());
        if (tutor != null) {
            return buildTutorResponse(tutor);
        }
        return null;
    }

    @Override
    public TutorResponse getTutorByAccountId(long accountId) {
        TutorEntity tutor = tutorRepository.getByAccountId(accountId);
        if (tutor != null) {
            return buildTutorResponse(tutor);
        }
        return null;
    }
}
