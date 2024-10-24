package com.bpm_workflow.bpm_workflow_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseModel<T> {
    private boolean isError;
    private String status;
    private String message;
    private T data;

    public ResponseModel(boolean isError, String status, String message, T data) {
        this.isError = isError;
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
