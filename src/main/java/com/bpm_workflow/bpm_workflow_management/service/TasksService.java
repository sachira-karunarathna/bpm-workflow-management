package com.bpm_workflow.bpm_workflow_management.service;

import com.bpm_workflow.bpm_workflow_management.dto.ResponseModel;
import org.activiti.engine.task.Task;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TasksService {

    ResponseEntity<ResponseModel<List<Task>>> getAllTasks(String processInstanceId);

}
