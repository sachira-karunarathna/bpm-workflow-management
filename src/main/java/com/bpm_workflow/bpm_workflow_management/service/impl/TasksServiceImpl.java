package com.bpm_workflow.bpm_workflow_management.service.impl;

import com.bpm_workflow.bpm_workflow_management.dto.FlowElementDTO;
import com.bpm_workflow.bpm_workflow_management.dto.ResponseModel;
import com.bpm_workflow.bpm_workflow_management.dto.TaskDTO;
import com.bpm_workflow.bpm_workflow_management.service.TasksService;
import com.bpm_workflow.bpm_workflow_management.util.FlowElementMapper;
import com.bpm_workflow.bpm_workflow_management.util.TaskMapper;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TasksServiceImpl implements TasksService {
    private static final Logger logger = LoggerFactory.getLogger(TasksServiceImpl.class);

    private final TaskService taskService;

    private final RuntimeService runtimeService;

    private final HistoryService historyService;

    private final RepositoryService repositoryService;

    private final TaskMapper taskMapper;

    private final FlowElementMapper flowElementMapper;

    @Autowired
    public TasksServiceImpl(TaskService taskService,
                            RuntimeService runtimeService,
                            HistoryService historyService,
                            RepositoryService repositoryService,
                            TaskMapper taskMapper,
                            FlowElementMapper flowElementMapper) {
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.historyService = historyService;
        this.repositoryService = repositoryService;
        this.taskMapper = taskMapper;
        this.flowElementMapper = flowElementMapper;
    }

    @Override
    public ResponseEntity<ResponseModel<List<TaskDTO>>> getAllTasks(String processInstanceId) {
        logger.info("Fetching all tasks for processInstanceId: {}", processInstanceId);
        try {
            List<Task> currentTasks = taskService.createTaskQuery()
                    .processInstanceId(processInstanceId)
                    .list();
            List<TaskDTO> result = currentTasks.stream().map(taskMapper::toDto).toList();
            logger.info("Successfully retrieved {} tasks for given processInstanceId: {}", result.size(), processInstanceId);
            ResponseModel<List<TaskDTO>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Available task for the given process",
                    result
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Error fetching tasks for processInstanceId {}: {}", processInstanceId, e.getMessage());
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
        logger.info("Fetching all completed tasks for processInstanceId: {}", processInstanceId);
        try {
            List<HistoricTaskInstance> completedTasks = historyService.createHistoricTaskInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .finished()
                    .list();
            logger.info("Successfully retrieved {} completed tasks for processInstanceId: {}", completedTasks.size(), processInstanceId);
            ResponseModel<List<HistoricTaskInstance>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Completed task for the given process instance",
                    completedTasks
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Error fetching completed tasks for processInstanceId {}: {}", processInstanceId, e.getMessage());
            ResponseModel<List<HistoricTaskInstance>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ResponseModel<List<TaskDTO>>> getAllCurrentTasks(String processInstanceId) {
        logger.info("Fetching current tasks for processInstanceId: {}", processInstanceId);
        try {
            List<Task> currentTasks = taskService.createTaskQuery()
                    .processInstanceId(processInstanceId)
                    .list();
            List<TaskDTO> result = currentTasks.stream().map(taskMapper::toDto).toList();
            logger.info("Successfully retrieved {} current tasks for processInstanceId: {}", result.size(), processInstanceId);
            ResponseModel<List<TaskDTO>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Current task for the given process instance",
                    result
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Error fetching current tasks for processInstanceId {}: {}", processInstanceId, e.getMessage());
            ResponseModel<List<TaskDTO>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ResponseModel<List<FlowElementDTO>>> getNextTasks(String currentTaskId) {
        logger.info("Fetching next tasks for current task ID: {}", currentTaskId);
        try {
            Task currentTask = taskService.createTaskQuery().taskId(currentTaskId).singleResult();
            if (currentTask == null) {
                ResponseModel<List<FlowElementDTO>> response = new ResponseModel<>(
                        true,
                        HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                        "No current tasks found!");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            String processInstanceId = currentTask.getProcessInstanceId();
            String processDefinitionId = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult()
                    .getProcessDefinitionId();

            BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
            List<FlowElement> elmentList = new ArrayList<>();
            List<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements().stream().toList();
            for (FlowElement element : flowElements) {
                if (element instanceof SequenceFlow) {
                    SequenceFlow sequenceFlow = (SequenceFlow) element;
                    if (sequenceFlow.getSourceRef().equals(currentTask.getTaskDefinitionKey())) {
                        String targetRef = sequenceFlow.getTargetRef();
                        for (FlowElement targetElement : flowElements) {
//                            ToDo: Remove instanceof UserTask
                            if (targetElement.getId().equals(targetRef) && targetElement instanceof UserTask) {
                                elmentList.add(targetElement);
                            }
                        }
                    }
                }
            }
            List<FlowElementDTO> results = elmentList.stream().map(flowElementMapper::toDto).toList();
            logger.info("Successfully retrieved {} next tasks for current task ID: {}", results.size(), currentTaskId);
            ResponseModel<List<FlowElementDTO>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Next tasks for the given current task retrieved successfully.",
                    results
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Error fetching next tasks for current task {}: {}", currentTaskId, e.getMessage());
            ResponseModel<List<FlowElementDTO>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @Override
    public ResponseEntity<ResponseModel<List<HistoricActivityInstance>>> getAllHistoricActivities(String processInstanceId) {
        logger.info("Fetching historic activities for processInstanceId: {}", processInstanceId);
        try {
            List<HistoricActivityInstance> completedTasks = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .list();
            logger.info("Successfully retrieved {} historic activities for processInstanceId: {}", completedTasks.size(), processInstanceId);
            ResponseModel<List<HistoricActivityInstance>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Historic activities for the given process instance",
                    completedTasks
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Error fetching historic activities for processInstanceId {}: {}", processInstanceId, e.getMessage());
            ResponseModel<List<HistoricActivityInstance>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ResponseModel<List<FormProperty>>> getTaskFormProperties(String processDefinitionId, String taskId) {
        logger.info("Fetching variables for execution ID: {}", taskId);
        try {
            Map<String, Object> variables = runtimeService.getVariables(taskId);

            BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
            List<FormProperty> formProperties = new ArrayList<>();
            for (Process process : bpmnModel.getProcesses()) {
                UserTask userTask = (UserTask) process.getFlowElement(taskId);
                if (userTask != null) {
                    formProperties = userTask.getFormProperties();
                    break;
                }
            }

            ResponseModel<List<FormProperty>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Task variables retrieved successfully",
                    formProperties
            );
            logger.info("Successfully retrieved variables for execution ID: {}", taskId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ActivitiException activitiException) {
            logger.error("Failed to retrieve task variables. Activiti error: {}", activitiException.getMessage(), activitiException);
            ResponseModel<List<FormProperty>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    activitiException.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            logger.error("Error retrieving task variables for execution ID {}: {}", taskId, e.getMessage(), e);
            ResponseModel<List<FormProperty>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ResponseModel<Map<String, Object>>> getTaskVariablesByTaskId(String taskId) {
        logger.info("Fetching variables for task ID: {}", taskId);
        try {
            Map<String, Object> taskVariables = taskService.getVariables(taskId);
            Map<String, Object> taskFormVariables = taskService.getVariablesLocal(taskId);

            ResponseModel<Map<String, Object>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Task variables retrieved successfully",
                    taskFormVariables
            );
            logger.info("Successfully retrieved variables for task ID: {}", taskId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ActivitiException activitiException) {
            logger.error("Failed to retrieve task variables. Activiti error: {}", activitiException.getMessage(), activitiException);
            ResponseModel<Map<String, Object>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    activitiException.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            logger.error("Error retrieving task variables for task ID {}: {}", taskId, e.getMessage(), e);
            ResponseModel<Map<String, Object>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ResponseModel<TaskDTO>> completeATask(String taskId) {
        logger.info("Completing task with taskId: {}", taskId);
        try {
            Task completedTask = taskService.createTaskQuery()
                    .taskId(taskId)
                    .singleResult();
            if (completedTask != null) {
                taskService.complete(taskId);
                logger.info("Task with taskId {} completed successfully", taskId);
            } else {
                logger.warn("Task with taskId {} not found", taskId);
            }
            TaskDTO result = taskMapper.toDto(completedTask);
            ResponseModel<TaskDTO> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Completed task for the given task ID",
                    result
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ActivitiException activitiException) {
            logger.error("Activiti operation failed: {}", activitiException.getMessage(), activitiException);
            ResponseModel<TaskDTO> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    activitiException.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            logger.error("Error completing task with taskId {}: {}", taskId, e.getMessage());
            ResponseModel<TaskDTO> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ResponseModel<TaskDTO>> completeATaskWithVariables(String taskId, Map<String, Object> variables) {
        logger.info("Completing task with taskId: {}, and variables: {}", taskId, variables);
        try {
            Task completedTask = taskService.createTaskQuery()
                    .taskId(taskId)
                    .singleResult();
            if (completedTask != null) {
                taskService.complete(taskId, variables);
                logger.info("Task with taskId {} completed with variables successfully", taskId);
            } else {
                logger.warn("Task with taskId {} is not found", taskId);
            }
            TaskDTO result = taskMapper.toDto(completedTask);
            ResponseModel<TaskDTO> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Completed task for the given task ID",
                    result
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ActivitiException activitiException) {
            logger.error("Activiti operation failed: {}", activitiException.getMessage(), activitiException);
            ResponseModel<TaskDTO> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    activitiException.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            logger.error("Error completing task with variables taskId {}: {}", taskId, e.getMessage());
            ResponseModel<TaskDTO> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
