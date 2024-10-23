package com.bpm_workflow.bpm_workflow_management.service;

import org.springframework.web.multipart.MultipartFile;

public interface WorkflowManagementService {

    String getAllWorkflows();

    String uploadWorkflowFile(MultipartFile file);

}
