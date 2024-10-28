package com.bpm_workflow.bpm_workflow_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ProcessInstanceDTO {

    private String id;
    private String processDefinitionId;
    private String processDefinitionName;
    private String processDefinitionKey;
    private String deploymentId;
    private String businessKey;
    private boolean isSuspended;
    private Map<String, Object> processVariables;

    public ProcessInstanceDTO(String id, String processDefinitionId, String processDefinitionName, String processDefinitionKey, String deploymentId, String businessKey, boolean isSuspended, Map<String, Object> processVariables) {
        this.id = id;
        this.processDefinitionId = processDefinitionId;
        this.processDefinitionName = processDefinitionName;
        this.processDefinitionKey = processDefinitionKey;
        this.deploymentId = deploymentId;
        this.businessKey = businessKey;
        this.isSuspended = isSuspended;
        this.processVariables = processVariables;
    }

}
