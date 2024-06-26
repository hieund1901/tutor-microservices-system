package com.microservices.projectfinal.controller;

import com.microservices.projectfinal.annotation.UserId;
import com.microservices.projectfinal.dto.TutorCreateRequest;
import com.microservices.projectfinal.dto.TutorResponseDTO;
import com.microservices.projectfinal.service.ITutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tutor")
public class TutorController {
    private final ITutorService tutorService;
    @PostMapping
    public TutorResponseDTO createTutor(@UserId String userId, @RequestBody TutorCreateRequest tutorCreateRequest) {
        return tutorService.createTutor(userId, tutorCreateRequest);
    }

    @GetMapping
    public ResponseEntity<?> getTutors(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(tutorService.getListTutor(page, size));
    }

    @GetMapping("{userId}")
    public TutorResponseDTO getTutorByEmail(@PathVariable String userId) {
        return tutorService.getTutorByUserId(userId);
    }
}
