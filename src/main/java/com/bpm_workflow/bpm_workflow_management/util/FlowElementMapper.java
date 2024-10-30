package com.bpm_workflow.bpm_workflow_management.util;

import com.bpm_workflow.bpm_workflow_management.dto.FlowElementDTO;
import org.activiti.bpmn.model.FlowElement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlowElementMapper {
    private final ModelMapper modelMapper;

    @Autowired FlowElementMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FlowElementDTO toDto(FlowElement flowElement) {
        return new FlowElementDTO(flowElement.getId(), flowElement.getName(), flowElement.getExtensionElements(), flowElement.getAttributes());
    }
}
