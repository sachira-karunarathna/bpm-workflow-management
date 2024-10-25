package com.bpm_workflow.bpm_workflow_management.service.impl;

import com.bpm_workflow.bpm_workflow_management.dto.ProcessDefinitionDTO;
import com.bpm_workflow.bpm_workflow_management.dto.ProcessInstanceDTO;
import com.bpm_workflow.bpm_workflow_management.dto.ResponseModel;
import com.bpm_workflow.bpm_workflow_management.service.ProcessService;
import com.bpm_workflow.bpm_workflow_management.util.Helpers;
import com.bpm_workflow.bpm_workflow_management.util.ProcessDefinitionMapper;
import com.bpm_workflow.bpm_workflow_management.util.ProcessInstanceMapper;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessServiceImpl implements ProcessService {

    private final ProcessDefinitionMapper processDefinitionMapper;

    private final ProcessInstanceMapper processInstanceMapper;

    private final RuntimeService runtimeService;

    private final RepositoryService repositoryService;

    @Autowired
    public ProcessServiceImpl(RuntimeService runtimeService,
                              RepositoryService repositoryService,
                              ProcessDefinitionMapper processDefinitionMapper,
                              ProcessInstanceMapper processInstanceMapper) {
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
        this.processDefinitionMapper = processDefinitionMapper;
        this.processInstanceMapper = processInstanceMapper;
    }

    @Override
    public ResponseEntity<ResponseModel<List<ProcessDefinitionDTO>>> getDeployedProcesses() {
        try {
            List<ProcessDefinition> deployedProcesses = repositoryService.createProcessDefinitionQuery().list();
            List<ProcessDefinitionDTO> result = deployedProcesses.stream().map(processDefinitionMapper::toDto).toList();
            ResponseModel<List<ProcessDefinitionDTO>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Deployed processes retrieved successfully",
                    result);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            List<ProcessDefinitionDTO> emptyList = new ArrayList<>();
            ResponseModel<List<ProcessDefinitionDTO>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage(),
                    emptyList);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ResponseModel<List<ProcessInstanceDTO>>> getAllActiveProcesses() {
        try {
            List<ProcessInstance> activeProcesses = runtimeService.createProcessInstanceQuery()
                    .active()
                    .list();
            List<ProcessInstanceDTO> result = activeProcesses.stream().map(processInstanceMapper::toDto).toList();
            ResponseModel<List<ProcessInstanceDTO>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Active process instances retrieved successfully",
                    result
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            List<ProcessInstanceDTO> emptyList = new ArrayList<>();
            ResponseModel<List<ProcessInstanceDTO>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage(),
                    emptyList);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ResponseModel<ProcessInstanceDTO>> getProcessInstance(String processInstanceId) {
        try {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            ProcessInstanceDTO result = processInstanceMapper.toDto(processInstance);
            ResponseModel<ProcessInstanceDTO> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Retrieve process instance successfully",
                    result
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ResponseModel<ProcessInstanceDTO> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ResponseModel<ProcessInstanceDTO>> startProcessInstance(String processDefinitionKey, String businessKey) {
        try {
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey);
            ProcessInstanceDTO result = processInstanceMapper.toDto(processInstance);
            ResponseModel<ProcessInstanceDTO> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Start process instance successfully",
                    result
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ResponseModel<ProcessInstanceDTO> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<String> deployProcess(MultipartFile file) {
        try {
//            if(!Helpers.isValidXmlFile(file)){
//                throw new Exception("Not a valid xml file!");
//            }
            InputStream bpmnStream = file.getInputStream();

            Deployment deployment = repositoryService.createDeployment()
                    .addInputStream(file.getOriginalFilename(), bpmnStream)
                    .name(file.getOriginalFilename())
                    .deploy();

            System.out.println("Deployed process: " + deployment.toString());
            return ResponseEntity.status(HttpStatus.OK).body(deployment.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteAllDeployments() {
        try {
            List<String> deployments = repositoryService.createDeploymentQuery()
                    .list()
                    .stream()
                    .map(Deployment::getId)
                    .toList();
            for(String deploymentId: deployments) {
                repositoryService.deleteDeployment(deploymentId, true);
            }
            return ResponseEntity.ok(deployments.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteDeployment(String deploymentId) {
        try{
            repositoryService.deleteDeployment(deploymentId, true);
            return ResponseEntity.ok("Deployment deleted!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
