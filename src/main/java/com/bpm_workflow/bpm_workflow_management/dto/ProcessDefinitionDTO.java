package com.bpm_workflow.bpm_workflow_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProcessDefinitionDTO {

    private String id;
    private String name;
    private String description;
    private String key;
    private int version;
    private String deploymentId;
    private boolean isSuspended;

    public ProcessDefinitionDTO(String id, String name, String description, String key, int version, String deploymentId, boolean isSuspended) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.key = key;
        this.version = version;
        this.deploymentId = deploymentId;
        this.isSuspended = isSuspended;
    }

}
