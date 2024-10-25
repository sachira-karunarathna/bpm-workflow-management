package com.bpm_workflow.bpm_workflow_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProcessInstanceDTO {

    private String processDefinitionId;
    private String processDefinitionName;
    private String processDefinitionKey;
    private String deploymentId;
    private String businessKey;
    private boolean isSuspended;

    public ProcessInstanceDTO(String processDefinitionId, String processDefinitionName, String processDefinitionKey, String deploymentId, String businessKey, boolean isSuspended) {
        this.processDefinitionId = processDefinitionId;
        this.processDefinitionName = processDefinitionName;
        this.processDefinitionKey = processDefinitionKey;
        this.deploymentId = deploymentId;
        this.businessKey = businessKey;
        this.isSuspended = isSuspended;
    }

}
