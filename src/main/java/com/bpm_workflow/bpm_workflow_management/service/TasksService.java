package com.bpm_workflow.bpm_workflow_management.service;

import com.bpm_workflow.bpm_workflow_management.dto.ResponseModel;
import com.bpm_workflow.bpm_workflow_management.dto.TaskDTO;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TasksService {

    ResponseEntity<ResponseModel<List<TaskDTO>>> getAllTasks(String processInstanceId);

    ResponseEntity<ResponseModel<List<HistoricTaskInstance>>> getAllCompletedTasks(String processInstanceId);

    ResponseEntity<ResponseModel<List<TaskDTO>>> getAllCurrentTasks(String processInstanceId);

    ResponseEntity<ResponseModel<List<HistoricActivityInstance>>> getAllHistoricActivities(String processInstanceId);

    ResponseEntity<ResponseModel<TaskDTO>> completeATask(String taskId);

}
