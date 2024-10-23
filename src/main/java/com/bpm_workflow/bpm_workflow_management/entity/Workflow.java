package com.bpm_workflow.bpm_workflow_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "workflows")
public class Workflow {
    @Id
    @GeneratedValue
    private Long id;

    private String workflowId;

    private String workflowName;
}
