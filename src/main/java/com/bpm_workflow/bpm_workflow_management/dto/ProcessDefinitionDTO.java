package com.bpm_workflow.bpm_workflow_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDefinitionDTO {
    private String id;
    private String name;
    private String description;
    private String version;
    private String deploymentId;
    private boolean isSuspended;
}
