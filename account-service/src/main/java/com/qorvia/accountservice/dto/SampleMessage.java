package com.qorvia.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SampleMessage {
    public SampleMessage() {
    }

    public SampleMessage(String data) {
        this.data = data;
    }

    private String data;
}
