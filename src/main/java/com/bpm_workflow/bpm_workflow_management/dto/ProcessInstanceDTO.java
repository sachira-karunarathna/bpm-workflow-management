package com.bpm_workflow.bpm_workflow_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessInstanceDTO {
    private String processDefinitionId;
    private String processDefinitionName;
    private String processDefinitionKey;
    private String deploymentId;
    private String businessKey;
    private boolean isSuspended;
}
