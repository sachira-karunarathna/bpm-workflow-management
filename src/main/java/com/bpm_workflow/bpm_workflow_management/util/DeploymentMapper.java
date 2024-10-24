package com.bpm_workflow.bpm_workflow_management.util;

import com.bpm_workflow.bpm_workflow_management.dto.DeploymentDTO;
import org.activiti.engine.repository.Deployment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeploymentMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public DeploymentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DeploymentDTO toDto(Deployment deployment) {
        return modelMapper.map(deployment, DeploymentDTO.class);
    }

    public Deployment toEntity(DeploymentDTO deploymentDTO) {
        return modelMapper.map(deploymentDTO, Deployment.class);
    }

}
