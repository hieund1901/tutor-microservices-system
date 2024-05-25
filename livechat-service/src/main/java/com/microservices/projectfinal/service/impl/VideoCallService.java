package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.constant.CommonConstant;
import com.microservices.projectfinal.dto.VideoCallCreateDTO;
import com.microservices.projectfinal.dto.VideoCallDTO;
import com.microservices.projectfinal.entity.VideoCallEntity;
import com.microservices.projectfinal.exception.ResponseException;
import com.microservices.projectfinal.repository.VideoCallRepository;
import com.microservices.projectfinal.service.IUserRedisService;
import com.microservices.projectfinal.service.IVideoCallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VideoCallService implements IVideoCallService {
    private VideoCallRepository videoCallRepository;
    private IUserRedisService userRedisService;

    @Override
    public VideoCallDTO createVideoCall(String callerId, VideoCallCreateDTO videoCallCreateDTO) {
        var userStatus = userRedisService.getUserStatus(videoCallCreateDTO.getCalleeId());
        if (userStatus.equals(CommonConstant.UserStatus.BUSY.name())
            || userStatus.equals(CommonConstant.UserStatus.OFFLINE.name())) {
           throw new ResponseException("User is busy or offline", HttpStatus.BAD_REQUEST);
        }

        VideoCallEntity videoCallEntity = VideoCallEntity.builder()
                .callerId(callerId)
                .calleeId(videoCallCreateDTO.getCalleeId())
                .recorded(videoCallCreateDTO.isRecorded())
                .build();

        VideoCallEntity savedVideoCall = videoCallRepository.save(videoCallEntity);
        return VideoCallDTO.builder()
                .calleeId(savedVideoCall.getCalleeId())
                .callSessionId(savedVideoCall.getCallSessionId().toString())
                .callerId(savedVideoCall.getCallerId())
                .build();
    }
}
