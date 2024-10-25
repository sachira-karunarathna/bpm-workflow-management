package com.bpm_workflow.bpm_workflow_management.controller;

import com.bpm_workflow.bpm_workflow_management.dto.ProcessDefinitionDTO;
import com.bpm_workflow.bpm_workflow_management.dto.ProcessInstanceDTO;
import com.bpm_workflow.bpm_workflow_management.dto.ResponseModel;
import com.bpm_workflow.bpm_workflow_management.dto.StartProcessDTO;
import com.bpm_workflow.bpm_workflow_management.service.ProcessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/processes")
@Tag(name = "Processes", description = "Process Related Endpoints")
public class ProcessController {

    private final ProcessService processService;

    @Autowired
    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }

    @Operation(summary = "Process Instance", description = "Get a single process instance")
    @GetMapping("/{processInstanceId}")
    public ResponseEntity<ResponseModel<ProcessInstanceDTO>> getProcessInstance(
            @PathVariable String processInstanceId) {
        return processService.getProcessInstance(processInstanceId);
    }

    @Operation(summary = "Deployed Processes", description = "Get all deployed processes")
    @GetMapping(value = "/deployments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<List<ProcessDefinitionDTO>>> getAllDeployedProcesses() {
        return processService.getDeployedProcesses();
    }

    @Operation(summary = "Active Processes", description = "Get all active processes")
    @GetMapping("/active")
    public ResponseEntity<ResponseModel<List<ProcessInstanceDTO>>> getAllActiveProcesses() {
        return processService.getAllActiveProcesses();
    }

    @Operation(summary = "Start Process Instance", description = "Start a process instance")
    @PostMapping("/start/{processDefinitionKey}")
    public ResponseEntity<ResponseModel<ProcessInstanceDTO>> startProcessInstance(
            @PathVariable String processDefinitionKey,
            @RequestBody StartProcessDTO request
    ) {
        System.out.println(request.getBusinessKey());
        return processService.startProcessInstance(processDefinitionKey, request.getBusinessKey());
    }

    @Operation(summary = "Delete a Deployed Process", description = "Delete a deployed process using deployment ID")
    @DeleteMapping("/deployments/{deploymentId}")
    public ResponseEntity<String> deleteDeployment(@PathVariable String deploymentId) {
        return processService.deleteDeployment(deploymentId);
    }

    @Operation(summary = "Delete All Deployed Processes", description = "Delete all deployed processes")
    @DeleteMapping("/deployments")
    public ResponseEntity<String> deleteAllDeployments() {
        return processService.deleteAllDeployments();
    }

}
