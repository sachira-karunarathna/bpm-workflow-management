package com.bpm_workflow.bpm_workflow_management.service;

import com.bpm_workflow.bpm_workflow_management.dto.ProcessDefinitionDTO;
import com.bpm_workflow.bpm_workflow_management.dto.ResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProcessService {

    ResponseEntity<ResponseModel<List<ProcessDefinitionDTO>>> getDeployedProcesses();

    ResponseEntity<String> getAllActiveProcesses();

    ResponseEntity<String> deployProcess(MultipartFile file);

    ResponseEntity<String> deleteAllDeployments();

    ResponseEntity<String> deleteDeployment(String deploymentId);

}
