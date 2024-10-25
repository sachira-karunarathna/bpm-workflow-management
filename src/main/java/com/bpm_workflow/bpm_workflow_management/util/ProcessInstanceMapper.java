package com.bpm_workflow.bpm_workflow_management.util;

import com.bpm_workflow.bpm_workflow_management.dto.ProcessInstanceDTO;
import org.activiti.engine.runtime.ProcessInstance;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessInstanceMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public ProcessInstanceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProcessInstanceDTO toDto(ProcessInstance processInstance) {
        return new ProcessInstanceDTO(
                processInstance.getProcessDefinitionId(),
                processInstance.getProcessDefinitionName(),
                processInstance.getProcessDefinitionKey(),
                processInstance.getDeploymentId(),
                processInstance.getBusinessKey(),
                processInstance.isSuspended()
        );
//        return modelMapper.map(processInstance, ProcessInstanceDTO.class);
    }

//    public ProcessInstance toEntity(ProcessInstanceDTO processInstanceDTO) {
//        return modelMapper.map(processInstanceDTO, ProcessInstance.class);
//    }
}
