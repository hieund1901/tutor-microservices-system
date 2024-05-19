package com.microservices.projectfinal.kafka.comsumer;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import lombok.Getter;

@Getter
public class KafkaCDCModel {
    @JsonRawValue
    private String before;
    @JsonRawValue
    private String after;
    @JsonRawValue
    private String source;

    public void setBefore(String before) {
        this.before = before;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
