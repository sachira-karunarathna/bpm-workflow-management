package com.bpm_workflow.bpm_workflow_management.service.impl;

import com.bpm_workflow.bpm_workflow_management.dto.ProcessInstanceDTO;
import com.bpm_workflow.bpm_workflow_management.dto.ResponseModel;
import com.bpm_workflow.bpm_workflow_management.dto.TaskDTO;
import com.bpm_workflow.bpm_workflow_management.service.TasksService;
import com.bpm_workflow.bpm_workflow_management.util.TaskMapper;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
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

    private final TaskMapper taskMapper;

    @Autowired
    public TasksServiceImpl(TaskService taskService,
                            RuntimeService runtimeService,
                            HistoryService historyService,
                            RepositoryService repositoryService,
                            TaskMapper taskMapper) {
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.historyService = historyService;
        this.repositoryService = repositoryService;
        this.taskMapper = taskMapper;
    }

    @Override
    public ResponseEntity<ResponseModel<List<TaskDTO>>> getAllTasks(String processInstanceId) {
        try {
            List<Task> currentTasks = taskService.createTaskQuery()
                    .processInstanceId(processInstanceId)
                    .list();
            List<TaskDTO> result = currentTasks.stream().map(taskMapper::toDto).toList();
            ResponseModel<List<TaskDTO>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Available task for the given process",
                    result
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            List<TaskDTO> emptyList = new ArrayList<>();
            ResponseModel<List<TaskDTO>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage(),
                    emptyList);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ResponseModel<List<HistoricTaskInstance>>> getAllCompletedTasks(String processInstanceId) {
        try {
            List<HistoricTaskInstance> completedTasks = historyService.createHistoricTaskInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .finished()
                    .list();
            ResponseModel<List<HistoricTaskInstance>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Completed task for the given process instance",
                    completedTasks
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ResponseModel<List<HistoricTaskInstance>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ResponseModel<List<TaskDTO>>> getAllCurrentTasks(String processInstanceId) {
        try {
            List<Task> currentTasks = taskService.createTaskQuery()
                    .processInstanceId(processInstanceId)
                    .list();
            List<TaskDTO> result = currentTasks.stream().map(taskMapper::toDto).toList();
            ResponseModel<List<TaskDTO>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Current task for the given process instance",
                    result
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ResponseModel<List<TaskDTO>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ResponseModel<List<HistoricActivityInstance>>> getAllHistoricActivities(String processInstanceId) {
        try {
            List<HistoricActivityInstance> completedTasks = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .list();
            ResponseModel<List<HistoricActivityInstance>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Historic activities for the given process instance",
                    completedTasks
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ResponseModel<List<HistoricActivityInstance>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ResponseModel<TaskDTO>> completeATask(String taskId) {
        try {
            Task completedTask = taskService.createTaskQuery()
                    .taskId(taskId)
                    .singleResult();
            if (completedTask != null) {
                taskService.complete(taskId);
            }
            TaskDTO result = taskMapper.toDto(completedTask);
            ResponseModel<TaskDTO> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Completed task for the given task ID",
                    result
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ResponseModel<TaskDTO> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
