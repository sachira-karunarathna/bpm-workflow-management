package com.bpm_workflow.bpm_workflow_management.service.impl;

import com.bpm_workflow.bpm_workflow_management.dto.ProcessDefinitionDTO;
import com.bpm_workflow.bpm_workflow_management.dto.ResponseModel;
import com.bpm_workflow.bpm_workflow_management.service.TasksService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TasksServiceImpl implements TasksService {

    private final TaskService taskService;

    private final RuntimeService runtimeService;

    private final HistoryService historyService;

    private final RepositoryService repositoryService;

    @Autowired
    public TasksServiceImpl(TaskService taskService,
                            RuntimeService runtimeService,
                            HistoryService historyService,
                            RepositoryService repositoryService) {
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.historyService = historyService;
        this.repositoryService = repositoryService;
    }

    @Override
    public ResponseEntity<ResponseModel<List<Task>>> getAllTasks(String processInstanceId) {
        try {
            List<Task> currentTasks = taskService.createTaskQuery()
                    .processInstanceId(processInstanceId)
                    .list();
            ResponseModel<List<Task>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Available task for the given process",
                    currentTasks
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            List<Task> emptyList = new ArrayList<>();
            ResponseModel<List<Task>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage(),
                    emptyList);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
