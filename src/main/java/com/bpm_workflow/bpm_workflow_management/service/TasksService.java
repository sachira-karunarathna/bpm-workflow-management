package com.bpm_workflow.bpm_workflow_management.service;

import com.bpm_workflow.bpm_workflow_management.dto.FlowElementDTO;
import com.bpm_workflow.bpm_workflow_management.dto.FormPropertyDTO;
import com.bpm_workflow.bpm_workflow_management.dto.ResponseModel;
import com.bpm_workflow.bpm_workflow_management.dto.TaskDTO;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TasksService {

    ResponseEntity<ResponseModel<List<TaskDTO>>> getAllTasks(String processInstanceId);

    ResponseEntity<ResponseModel<List<HistoricTaskInstance>>> getAllCompletedTasks(String processInstanceId);

    ResponseEntity<ResponseModel<List<TaskDTO>>> getAllCurrentTasks(String processInstanceId);

    ResponseEntity<ResponseModel<List<FlowElementDTO>>> getPossibleNextTasks(String currentTaskId);

    ResponseEntity<ResponseModel<List<FlowElementDTO>>> getNextTasks(String currentTaskId);

    ResponseEntity<ResponseModel<List<HistoricActivityInstance>>> getAllHistoricActivities(String processInstanceId);

    ResponseEntity<ResponseModel<List<FormPropertyDTO>>> getTaskFormProperties(String taskId);

    List<FormPropertyDTO> getFormPropertiesOfTask(String taskId);

    Task getTaskByTaskDefinitionKey(String taskDefinitionKey);

    ResponseEntity<ResponseModel<Map<String, Object>>> getTaskVariablesByTaskId(String taskId);

    ResponseEntity<ResponseModel<TaskDTO>> completeATask(String taskId);

    ResponseEntity<ResponseModel<TaskDTO>> completeATaskWithVariables(String taskId, Map<String, Object> variables);

}
