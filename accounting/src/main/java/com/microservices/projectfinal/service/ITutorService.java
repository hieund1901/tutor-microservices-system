package com.microservices.projectfinal.service;

import com.microservices.projectfinal.dto.TutorCreateRequest;
import com.microservices.projectfinal.dto.TutorListResponseDTO;
import com.microservices.projectfinal.dto.TutorResponseDTO;

public interface ITutorService {
    TutorResponseDTO createTutor(String userId, TutorCreateRequest tutorCreateRequest);
    TutorResponseDTO updateTutor(long id, TutorCreateRequest tutorCreateRequest);
    TutorResponseDTO getTutor(long id);
    void deleteTutor(long id);
    TutorResponseDTO getTutorByUserId(String userId);
    TutorListResponseDTO getListTutor(int page);

}
