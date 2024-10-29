package com.bpm_workflow.bpm_workflow_management.service;

import com.bpm_workflow.bpm_workflow_management.dto.ProcessDefinitionDTO;
import com.bpm_workflow.bpm_workflow_management.dto.ProcessInstanceDTO;
import com.bpm_workflow.bpm_workflow_management.dto.ResponseModel;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProcessService {

    ResponseEntity<ResponseModel<List<ProcessDefinitionDTO>>> getDeployedProcesses();

    ResponseEntity<ResponseModel<List<ProcessInstanceDTO>>> getAllActiveProcesses();

    ResponseEntity<ResponseModel<List<HistoricProcessInstance>>> getAllCompletedProcesses();

    ResponseEntity<ResponseModel<ProcessInstanceDTO>> getProcessInstance(String processInstanceId);

    ResponseEntity<ResponseModel<List<ProcessInstanceDTO>>> getProcessesByBusinessKey(String businessKey);

    ResponseEntity<ResponseModel<ProcessInstanceDTO>> startProcessInstance(String processDefinitionKey, String businessKey);

    ResponseEntity<ResponseModel<ProcessInstanceDTO>> startProcessInstance(String processDefinitionKey, String businessKey, Map<String, Object> variables);

    ResponseEntity<String> deployProcess(MultipartFile file);

    ResponseEntity<String> deleteAllDeployments();

    ResponseEntity<String> deleteProcessInstance(String processInstanceId, String deleteReason);

    ResponseEntity<String> deleteDeployment(String deploymentId);

}
