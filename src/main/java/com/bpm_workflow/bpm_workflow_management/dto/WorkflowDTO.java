package com.bpm_workflow.bpm_workflow_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowDTO {
    private String workflowId;
    private String workflowName;
}
