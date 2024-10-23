package com.bpm_workflow.bpm_workflow_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/workflows")
@Tag(name = "Workflows Management", description = "Workflows Management Endpoints")
public class WorkflowManagementController {

    @Operation(summary = "Get All Workflows", description = "Get all available workflows")
    @GetMapping(name = "/")
    public String getAllWorkflows() {
        return "All workflows!";
    }

    @Operation(summary = "Upload Workflow", description = "Upload an XML file for workflow processing")
    @PostMapping(name = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadWorkflow(
            @Parameter(description = "XML file to upload", required = true)
            @RequestPart("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded!");
        }

        try {
            String fileName = file.getOriginalFilename();

            if (!fileName.endsWith(".xml")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File should be a XML file!");
            }

            return ResponseEntity.ok("Workflow uploaded: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file: " + e.getMessage());
        }
    }

}
