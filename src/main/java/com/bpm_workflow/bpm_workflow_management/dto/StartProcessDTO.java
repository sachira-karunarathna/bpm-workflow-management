package com.bpm_workflow.bpm_workflow_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StartProcessDTO {

    private String businessKey;

    public String getBusinessKey() {
        return this.businessKey;
    }

    public StartProcessDTO(String businessKey) {
        this.businessKey = businessKey;
    }

}
