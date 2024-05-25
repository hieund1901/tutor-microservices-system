package com.microservices.projectfinal.service;

import com.microservices.projectfinal.dto.VideoCallCreateDTO;
import com.microservices.projectfinal.dto.VideoCallDTO;

public interface IVideoCallService {
    VideoCallDTO createVideoCall(String callerId, VideoCallCreateDTO videoCallCreateDTO);
}
