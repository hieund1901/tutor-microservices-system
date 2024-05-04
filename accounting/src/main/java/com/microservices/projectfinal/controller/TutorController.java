package com.microservices.projectfinal.controller;

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
    public TutorResponse createTutor(@RequestBody TutorCreateRequest tutorCreateRequest) {
        return tutorService.createTutor(tutorCreateRequest);
    }

    @GetMapping("/by-email")
    public TutorResponse getTutorByEmail(@RequestParam(name = "email") String email) {
        return tutorService.getTutorByEmail(email);
    }

    @GetMapping("/by-account-id")
    public TutorResponse getTutorByAccountId(@RequestParam(name = "accountId") Long accountId) {
        return tutorService.getTutorByAccountId(accountId);
    }
}
