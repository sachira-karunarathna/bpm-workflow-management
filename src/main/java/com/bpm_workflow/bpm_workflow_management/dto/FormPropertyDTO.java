package com.bpm_workflow.bpm_workflow_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FormPropertyDTO {
    private String id;
    private String name;
    private String type;
    private boolean isRequired;
    private String variable;

    public FormPropertyDTO(String id, String name, String type, boolean isRequired, String variable) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isRequired = isRequired;
        this.variable = variable;
    }
}
