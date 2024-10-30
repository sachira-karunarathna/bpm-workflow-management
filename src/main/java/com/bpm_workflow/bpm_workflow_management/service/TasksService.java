package com.bpm_workflow.bpm_workflow_management.service;

import com.bpm_workflow.bpm_workflow_management.dto.FlowElementDTO;
import com.bpm_workflow.bpm_workflow_management.dto.ResponseModel;
import com.bpm_workflow.bpm_workflow_management.dto.TaskDTO;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TasksService {

    ResponseEntity<ResponseModel<List<TaskDTO>>> getAllTasks(String processInstanceId);

    ResponseEntity<ResponseModel<List<HistoricTaskInstance>>> getAllCompletedTasks(String processInstanceId);

    ResponseEntity<ResponseModel<List<TaskDTO>>> getAllCurrentTasks(String processInstanceId);

    ResponseEntity<ResponseModel<List<FlowElementDTO>>> getNextTasks(String currentTaskId);

    ResponseEntity<ResponseModel<List<HistoricActivityInstance>>> getAllHistoricActivities(String processInstanceId);

    ResponseEntity<ResponseModel<List<FormProperty>>> getTaskFormProperties(String processDefinitionId, String taskId);

    ResponseEntity<ResponseModel<Map<String, Object>>> getTaskVariablesByTaskId(String taskId);

    ResponseEntity<ResponseModel<TaskDTO>> completeATask(String taskId);

    ResponseEntity<ResponseModel<TaskDTO>> completeATaskWithVariables(String taskId, Map<String, Object> variables);

}
