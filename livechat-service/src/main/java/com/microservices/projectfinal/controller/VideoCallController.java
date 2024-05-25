package com.microservices.projectfinal.controller;

import com.microservices.projectfinal.annotation.UserId;
import com.microservices.projectfinal.dto.VideoCallCreateDTO;
import com.microservices.projectfinal.service.IVideoCallService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/video-call")
public class VideoCallController {
    private final IVideoCallService videoCallService;

    @PostMapping
    public ResponseEntity<?> createVideoCall(@UserId String callerId, @RequestBody @Valid VideoCallCreateDTO videoCallCreateDTO) {
        return ResponseEntity.ok(videoCallService.createVideoCall(callerId, videoCallCreateDTO));
    }
}
