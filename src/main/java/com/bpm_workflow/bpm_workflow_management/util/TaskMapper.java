package com.bpm_workflow.bpm_workflow_management.util;

import com.bpm_workflow.bpm_workflow_management.dto.FormPropertyDTO;
import com.bpm_workflow.bpm_workflow_management.dto.TaskDTO;
import org.activiti.engine.task.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public TaskMapper () {
        this.modelMapper = new ModelMapper();
    }

    public TaskDTO toDto(Task task) {
        return new TaskDTO(
                task.getPriority(),
                task.getName(),
                task.getOwner(),
                task.getTenantId(),
                task.getAppVersion(),
                task.getDelegationState() != null ? task.getDelegationState().name() : null,
                task.getDescription(),
                task.isSuspended(),
                task.getAssignee(),
                task.getDueDate(),
                task.getParentTaskId(),
                task.getCategory(),
                task.getFormKey(),
                task.getId(),
                task.getExecutionId(),
                task.getCreateTime(),
                task.getBusinessKey(),
                task.getProcessDefinitionId(),
                task.getClaimTime(),
                task.getProcessInstanceId(),
                task.getTaskDefinitionKey(),
                task.getProcessVariables(),
                task.getTaskLocalVariables()
        );
//        return modelMapper.map(task, TaskDto.class);
    }

    public TaskDTO toDtoWithVariables(Task task, List<FormPropertyDTO> variables) {
        return new TaskDTO(
                task.getPriority(),
                task.getName(),
                task.getOwner(),
                task.getTenantId(),
                task.getAppVersion(),
                task.getDelegationState() != null ? task.getDelegationState().name() : null,
                task.getDescription(),
                task.isSuspended(),
                task.getAssignee(),
                task.getDueDate(),
                task.getParentTaskId(),
                task.getCategory(),
                task.getFormKey(),
                task.getId(),
                task.getExecutionId(),
                task.getCreateTime(),
                task.getBusinessKey(),
                task.getProcessDefinitionId(),
                task.getClaimTime(),
                task.getProcessInstanceId(),
                task.getTaskDefinitionKey(),
                task.getProcessVariables(),
                task.getTaskLocalVariables(),
                variables
        );
//        return modelMapper.map(task, TaskDto.class);
    }

//    public Task toEntity(TaskDto taskDto) {
//        return modelMapper.map(taskDto, Task.class);
//    }
}
