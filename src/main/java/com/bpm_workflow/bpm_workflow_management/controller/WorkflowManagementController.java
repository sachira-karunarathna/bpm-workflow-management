package com.bpm_workflow.bpm_workflow_management.controller;

import com.bpm_workflow.bpm_workflow_management.service.WorkflowManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/workflows")
@Tag(name = "Workflows Management", description = "Workflows Management Endpoints")
public class WorkflowManagementController {

    private final WorkflowManagementService workflowManagementService;

    @Autowired
    public WorkflowManagementController(WorkflowManagementService workflowManagementService) {
        this.workflowManagementService = workflowManagementService;
    }

    @Operation(summary = "Get All Workflows", description = "Get all available workflows")
    @GetMapping(name = "/")
    public ResponseEntity<String> getAllWorkflows() {
        return workflowManagementService.getAllWorkflows();
    }

    @Operation(summary = "Upload Workflow", description = "Upload an XML file for workflow processing")
    @PostMapping(name = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadWorkflow(
            @Parameter(description = "XML file to upload", required = true)
            @RequestPart("file") MultipartFile file) {

        return workflowManagementService.uploadWorkflowFile(file);

    }

}
