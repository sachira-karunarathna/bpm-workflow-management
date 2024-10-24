package com.bpm_workflow.bpm_workflow_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeploymentDTO {
    private String id;
    private String name;
    private String key;
    private String deployTime;
    private String version;
}
