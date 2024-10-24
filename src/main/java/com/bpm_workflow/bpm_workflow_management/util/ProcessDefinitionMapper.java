package com.bpm_workflow.bpm_workflow_management.util;

import com.bpm_workflow.bpm_workflow_management.dto.ProcessDefinitionDTO;
import org.activiti.engine.repository.ProcessDefinition;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessDefinitionMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public ProcessDefinitionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProcessDefinitionDTO toDto(ProcessDefinition processDefinition) {
        return modelMapper.map(processDefinition, ProcessDefinitionDTO.class);
    }

    public ProcessDefinition toEntity(ProcessDefinitionDTO processDefinitionDTO) {
        return modelMapper.map(processDefinitionDTO, ProcessDefinition.class);
    }

}
