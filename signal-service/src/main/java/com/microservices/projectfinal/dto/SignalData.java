package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("signal_data")
public class SignalData {
    private String fromUserId;
    private String toUserId;
    private SignalType signalType;
    private String data;

    public enum SignalType {
        Offer,Answer,Ice
    }
}
