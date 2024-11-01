package com.bpm_workflow.bpm_workflow_management.util;

import com.bpm_workflow.bpm_workflow_management.dto.FormPropertyDTO;
import org.activiti.bpmn.model.FormProperty;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormPropertyMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public FormPropertyMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FormPropertyDTO toDto(FormProperty formProperty) {
        return new FormPropertyDTO(formProperty.getId(), formProperty.getName(), formProperty.getType(), formProperty.isRequired(), formProperty.getVariable());
    }
}
