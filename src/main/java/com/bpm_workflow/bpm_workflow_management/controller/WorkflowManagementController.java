package com.bpm_workflow.bpm_workflow_management.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workflows")
@Tag(name = "Workflows Management", description = "Workflows Management Endpoints")
public class WorkflowManagementController {

    @GetMapping("/")
    public String getAllWorkflows() {
        return "All workflows!";
    }

}
