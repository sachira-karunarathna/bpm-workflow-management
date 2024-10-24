package com.bpm_workflow.bpm_workflow_management.service.impl;

import com.bpm_workflow.bpm_workflow_management.repository.WorkflowManagementRepository;
import com.bpm_workflow.bpm_workflow_management.service.ProcessService;
import com.bpm_workflow.bpm_workflow_management.service.WorkflowManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class WorkflowManagementServiceImpl implements WorkflowManagementService {

    private final WorkflowManagementRepository workflowManagementRepository;

    private final ProcessService processService;

    @Autowired
    public WorkflowManagementServiceImpl(WorkflowManagementRepository workflowManagementRepository, ProcessService processService) {
        this.workflowManagementRepository = workflowManagementRepository;
        this.processService = processService;
    }

    @Override
    public ResponseEntity<String> getAllWorkflows() {
        return ResponseEntity.ok("All workflows retrieved!");
    }

    @Override
    public ResponseEntity<String> uploadWorkflowFile(MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded!");
        }

        try {
            String fileName = file.getOriginalFilename();

            if (!fileName.endsWith(".xml")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File should be a XML file!");
            }

            ResponseEntity<String> data = processService.deployProcess(file);

            return ResponseEntity.ok("Workflow uploaded: " + fileName + " and deployed!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file: " + e.getMessage());
        }

    }

}
