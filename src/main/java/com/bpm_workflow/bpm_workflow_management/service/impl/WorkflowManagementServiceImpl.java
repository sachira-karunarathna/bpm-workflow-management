package com.bpm_workflow.bpm_workflow_management.service.impl;

import com.bpm_workflow.bpm_workflow_management.repository.WorkflowManagementRepository;
import com.bpm_workflow.bpm_workflow_management.service.WorkflowManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class WorkflowManagementServiceImpl implements WorkflowManagementService {

    private final WorkflowManagementRepository workflowManagementRepository;

    @Autowired
    public WorkflowManagementServiceImpl(WorkflowManagementRepository workflowManagementRepository) {
        this.workflowManagementRepository = workflowManagementRepository;
    }

    @Override
    public String getAllWorkflows() {
        return "";
    }

    @Override
    public String uploadWorkflowFile(MultipartFile file) {
        return "";
    }

}
