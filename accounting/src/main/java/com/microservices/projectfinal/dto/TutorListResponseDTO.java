package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonRootName("tutors")
@Builder
@Getter
public class TutorListResponseDTO {
    @JsonProperty("tutors")
    private List<TutorResponseDTO> tutors;
    private int total;
    private int page;
}
