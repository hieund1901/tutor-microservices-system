package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonRootName("register")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Register {
    @JsonProperty("user_id")
    private String userId;
}
