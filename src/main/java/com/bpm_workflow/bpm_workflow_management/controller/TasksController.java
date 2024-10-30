package com.bpm_workflow.bpm_workflow_management.controller;

import com.bpm_workflow.bpm_workflow_management.dto.FlowElementDTO;
import com.bpm_workflow.bpm_workflow_management.dto.ResponseModel;
import com.bpm_workflow.bpm_workflow_management.dto.TaskDTO;
import com.bpm_workflow.bpm_workflow_management.service.TasksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Tasks", description = "Tasks Related Endpoints")
public class TasksController {

    private final TasksService tasksService;

    @Autowired
    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @Operation(summary = "Tasks of a Process", description = "Get all tasks of a given process")
    @GetMapping("/{processInstanceId}")
    public ResponseEntity<ResponseModel<List<TaskDTO>>> getAllTasks(
            @PathVariable String processInstanceId) {
        return tasksService.getAllTasks(processInstanceId);
    }

    @Operation(summary = "Completed Tasks of a Process", description = "Get all completed tasks of a given process")
    @GetMapping("/completed")
    public ResponseEntity<ResponseModel<List<HistoricTaskInstance>>> getAllCompletedTasks(
            @RequestParam String processInstanceId
    ) {
        return tasksService.getAllCompletedTasks(processInstanceId);
    }

    @Operation(summary = "Current Tasks of a Process", description = "Get all current tasks of a given process")
    @GetMapping("/current")
    public ResponseEntity<ResponseModel<List<TaskDTO>>> getAllCurrentTasks(
            @RequestParam String processInstanceId
    ) {
        return tasksService.getAllCurrentTasks(processInstanceId);
    }

    @Operation(summary = "Next Tasks of the Process", description = "Get next tasks of the given process and current task")
    @GetMapping("/next")
    public ResponseEntity<ResponseModel<List<FlowElementDTO>>> getNextTasks(
            @RequestParam String currentTaskId
    ) {
        return tasksService.getNextTasks(currentTaskId);
    }

    @Operation(summary = "Historic Activities of a Process", description = "Get all historic activities of a given process")
    @GetMapping("/historic-activities")
    public ResponseEntity<ResponseModel<List<HistoricActivityInstance>>> getAllHistoricActivities(
            @RequestParam String processInstanceId
    ) {
        return tasksService.getAllHistoricActivities(processInstanceId);
    }

    @Operation(summary = "Form Properties of a Task", description = "Get all form properties of a given task ID.")
    @GetMapping("/task-form-properties/{processDefinitionId}")
    public ResponseEntity<ResponseModel<List<FormProperty>>> getTaskFormProperties(
            @PathVariable String processDefinitionId,
            @RequestParam String taskId
    ) {
        return tasksService.getTaskFormProperties(processDefinitionId, taskId);
    }

    @Operation(summary = "Variables of a Task by Task ID", description = "Get all variables of a given task ID.")
    @GetMapping("/task-variables-by-task-id/{taskId}")
    public ResponseEntity<ResponseModel<Map<String, Object>>> getVariablesOfATaskByTaskId(
            @PathVariable String taskId
    ) {
        return tasksService.getTaskVariablesByTaskId(taskId);
    }

    @Operation(summary = "Complete a Task", description = "Completed a task of a process instance")
    @PostMapping("/complete/{taskId}")
    public ResponseEntity<ResponseModel<TaskDTO>> completeATask(
            @PathVariable String taskId) {
        return tasksService.completeATask(taskId);
    }

    @Operation(summary = "Complete a Task with Variables", description = "Complete a task of a process instance with variables")
    @PostMapping("/complete-with-variables/{taskId}")
    public ResponseEntity<ResponseModel<TaskDTO>> completeATaskWithVariables(
            @PathVariable String taskId,
            @RequestBody Map<String, Object> variables
    ) {
        return tasksService.completeATaskWithVariables(taskId, variables);
    }

}
