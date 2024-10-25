package com.bpm_workflow.bpm_workflow_management.service;

import com.bpm_workflow.bpm_workflow_management.dto.ProcessDefinitionDTO;
import com.bpm_workflow.bpm_workflow_management.dto.ProcessInstanceDTO;
import com.bpm_workflow.bpm_workflow_management.dto.ResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProcessService {

    ResponseEntity<ResponseModel<List<ProcessDefinitionDTO>>> getDeployedProcesses();

    ResponseEntity<ResponseModel<List<ProcessInstanceDTO>>> getAllActiveProcesses();

    ResponseEntity<ResponseModel<ProcessInstanceDTO>> getProcessInstance(String processInstanceId);

    ResponseEntity<ResponseModel<ProcessInstanceDTO>> startProcessInstance(String processDefinitionKey, String businessKey);

    ResponseEntity<String> deployProcess(MultipartFile file);

    ResponseEntity<String> deleteAllDeployments();

    ResponseEntity<String> deleteDeployment(String deploymentId);

}
