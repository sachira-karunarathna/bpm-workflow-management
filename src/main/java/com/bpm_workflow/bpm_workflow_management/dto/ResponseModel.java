package com.bpm_workflow.bpm_workflow_management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseModel<T> {
    @JsonProperty("isError")
    private boolean isError;

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    public ResponseModel(boolean isError, String status, String message, T data) {
        this.isError = isError;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseModel(boolean isError, String status, String message) {
        this.isError = isError;
        this.status = status;
        this.message = message;
    }
}
