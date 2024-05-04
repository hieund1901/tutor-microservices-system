package com.microservices.projectfinal.service;

import com.microservices.projectfinal.dto.TutorCreateRequest;
import com.microservices.projectfinal.dto.TutorResponse;

public interface ITutorService {
    TutorResponse createTutor(TutorCreateRequest tutorCreateRequest);
    TutorResponse updateTutor(long id, TutorCreateRequest tutorCreateRequest);
    TutorResponse getTutor(long id);
    void deleteTutor(long id);
    TutorResponse getTutorByEmail(String email);
    TutorResponse getTutorByAccountId(long accountId);
}
