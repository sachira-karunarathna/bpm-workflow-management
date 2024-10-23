package com.bpm_workflow.bpm_workflow_management.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface WorkflowManagementService {

    ResponseEntity<String> getAllWorkflows();

    ResponseEntity<String> uploadWorkflowFile(MultipartFile file);

}
