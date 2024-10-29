package com.bpm_workflow.bpm_workflow_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class SelectProcessDTO {

    private String businessKey;

    private Map<String, Object> variables;

    public String getBusinessKey() {
        return this.businessKey;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public SelectProcessDTO(String businessKey) {
        this.businessKey = businessKey;
    }

    public SelectProcessDTO(String businessKey, Map<String, Object> variables) {
        this.businessKey = businessKey;
        this.variables = variables;
    }

}
