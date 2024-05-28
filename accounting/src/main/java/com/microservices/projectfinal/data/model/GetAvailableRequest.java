package com.microservices.projectfinal.data.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAvailableRequest {
    private String tutorId;
    private Long dimTimeKey;
}
