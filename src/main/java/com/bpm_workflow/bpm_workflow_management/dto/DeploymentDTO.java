package com.bpm_workflow.bpm_workflow_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class DeploymentDTO {

    private String id;
    private String name;
    private String key;
    private Date deployTime;
    private Integer version;

    public DeploymentDTO(String id, String name, String key, Date deployTime, Integer version) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.deployTime = deployTime;
        this.version = version;
    }

}
