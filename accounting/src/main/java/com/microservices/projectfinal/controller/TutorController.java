package com.microservices.projectfinal.controller;

import com.microservices.projectfinal.annotation.UserId;
import com.microservices.projectfinal.dto.TutorCreateRequest;
import com.microservices.projectfinal.dto.TutorResponse;
import com.microservices.projectfinal.service.ITutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tutor")
public class TutorController {
    private final ITutorService tutorService;
    @PostMapping
    public TutorResponse createTutor(@UserId String userId, @RequestBody TutorCreateRequest tutorCreateRequest) {
        return tutorService.createTutor(userId, tutorCreateRequest);
    }

    @GetMapping
    public TutorResponse getTutorByEmail(@UserId String userId) {
        return tutorService.getTutorByUserId(userId);
    }
}
