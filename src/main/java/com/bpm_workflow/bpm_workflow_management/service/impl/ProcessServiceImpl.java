package com.bpm_workflow.bpm_workflow_management.service.impl;

import com.bpm_workflow.bpm_workflow_management.dto.ProcessDefinitionDTO;
import com.bpm_workflow.bpm_workflow_management.dto.ProcessInstanceDTO;
import com.bpm_workflow.bpm_workflow_management.dto.ResponseModel;
import com.bpm_workflow.bpm_workflow_management.service.ProcessService;
import com.bpm_workflow.bpm_workflow_management.util.Helpers;
import com.bpm_workflow.bpm_workflow_management.util.ProcessDefinitionMapper;
import com.bpm_workflow.bpm_workflow_management.util.ProcessInstanceMapper;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProcessServiceImpl implements ProcessService {
    private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);

    private final ProcessDefinitionMapper processDefinitionMapper;

    private final ProcessInstanceMapper processInstanceMapper;

    private final RuntimeService runtimeService;

    private final RepositoryService repositoryService;

    private final HistoryService historyService;

    @Autowired
    public ProcessServiceImpl(RuntimeService runtimeService,
                              RepositoryService repositoryService,
                              HistoryService historyService,
                              ProcessDefinitionMapper processDefinitionMapper,
                              ProcessInstanceMapper processInstanceMapper) {
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
        this.historyService = historyService;
        this.processDefinitionMapper = processDefinitionMapper;
        this.processInstanceMapper = processInstanceMapper;
    }

    @Override
    public ResponseEntity<ResponseModel<List<ProcessDefinitionDTO>>> getDeployedProcesses() {
        logger.info("Starting to retrieve deployed process definitions...");
        try {
            List<ProcessDefinition> deployedProcesses = repositoryService.createProcessDefinitionQuery().list();
            List<ProcessDefinitionDTO> result = deployedProcesses.stream().map(processDefinitionMapper::toDto).toList();
            ResponseModel<List<ProcessDefinitionDTO>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Deployed processes retrieved successfully",
                    result);
            logger.info("Successfully retrieved {} deployed processes.", result.size());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ActivitiException activitiException) {
            logger.error("Activiti operation failed: {}", activitiException.getMessage(), activitiException);
            List<ProcessDefinitionDTO> emptyList = new ArrayList<>();
            ResponseModel<List<ProcessDefinitionDTO>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    activitiException.getMessage(),
                    emptyList);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            logger.error("Failed to retrieve deployed processes. Error: {}", e.getMessage(), e);
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
        logger.info("Fetching all active processes...");
        try {
            List<ProcessInstance> activeProcesses = runtimeService.createProcessInstanceQuery()
                    .active()
                    .list();
            List<ProcessInstanceDTO> result = activeProcesses.stream().map(processInstanceMapper::toDto).toList();
            logger.info("Successfully retrieved {} active processes.", result.size());
            ResponseModel<List<ProcessInstanceDTO>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Active process instances retrieved successfully",
                    result
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ActivitiException activitiException) {
            logger.error("Activiti operation failed: {}", activitiException.getMessage(), activitiException);
            List<ProcessInstanceDTO> emptyList = new ArrayList<>();
            ResponseModel<List<ProcessInstanceDTO>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    activitiException.getMessage(),
                    emptyList);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            logger.error("Error fetching active processes: {}", e.getMessage());
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
    public ResponseEntity<ResponseModel<List<HistoricProcessInstance>>> getAllCompletedProcesses() {
        logger.info("Fetching all completed processes...");
        try {
            List<HistoricProcessInstance> completedProcesses = historyService.createHistoricProcessInstanceQuery().finished().list();

            ResponseModel<List<HistoricProcessInstance>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Retrieve completed process instance successfully",
                    completedProcesses
            );
            logger.info("Successfully retrieved {} completed processes.", completedProcesses.size());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Error fetching completed processes: {}", e.getMessage());
            ResponseModel<List<HistoricProcessInstance>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ResponseModel<ProcessInstanceDTO>> getProcessInstance(String processInstanceId) {
        logger.info("Fetching process instance with ID: {}", processInstanceId);
        try {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            ProcessInstanceDTO result = processInstanceMapper.toDto(processInstance);
            logger.info("Successfully retrieved process instance: {}", processInstanceId);
            ResponseModel<ProcessInstanceDTO> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Retrieve process instance successfully",
                    result
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Error fetching process instance {}: {}", processInstanceId, e.getMessage());
            ResponseModel<ProcessInstanceDTO> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ResponseModel<List<ProcessInstanceDTO>>> getProcessesByBusinessKey(String businessKey) {
        logger.info("Fetching processes by business key: {}", businessKey);
        try {
            List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
                    .processInstanceBusinessKey(businessKey)
                    .list();
            List<ProcessInstanceDTO> results = processInstances.stream().map(processInstanceMapper::toDto).toList();
            logger.info("Successfully retrieved {} processes by business key: {}", results.size(), businessKey);
            ResponseModel<List<ProcessInstanceDTO>> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Retrieve process instance by business key successfully",
                    results
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error("Error fetching processes by business key {}: {}", businessKey, e.getMessage());
            ResponseModel<List<ProcessInstanceDTO>> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ResponseModel<ProcessInstanceDTO>> startProcessInstance(String processDefinitionKey, String businessKey) {
        logger.info("Starting process instance with definition key: {} and business key: {}", processDefinitionKey, businessKey);
        try {
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey);
            ProcessInstanceDTO result = processInstanceMapper.toDto(processInstance);
            logger.info("Successfully started process instance: {}", processInstance.getId());
            ResponseModel<ProcessInstanceDTO> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Start process instance successfully",
                    result
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ActivitiException activitiException) {
            logger.error("Activiti operation failed: {}", activitiException.getMessage(), activitiException);
            ResponseModel<ProcessInstanceDTO> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    activitiException.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            logger.error("Error starting process instance with definition key {} and business key {}: {}", processDefinitionKey, businessKey, e.getMessage());
            ResponseModel<ProcessInstanceDTO> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ResponseModel<ProcessInstanceDTO>> startProcessInstance(String processDefinitionKey, String businessKey, Map<String, Object> variables) {
        logger.info("Starting process instance with definition key: {}, business key: {}, and variables: {}", processDefinitionKey, businessKey, variables);
        try {
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
//            System.out.println(variables);
//            System.out.println("Variables: " + runtimeService.getVariables(processInstance.getId()));
//            runtimeService.setVariables(processInstance.getId(), variables);
            ProcessInstanceDTO result = processInstanceMapper.toDto(processInstance);
            logger.info("Successfully started process instance with variables: {}", processInstance.getId());
            ResponseModel<ProcessInstanceDTO> response = new ResponseModel<>(
                    false,
                    HttpStatus.OK.toString(),
                    "Start process instance with variables successfully",
                    result
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ActivitiException activitiException) {
            logger.error("Activiti operation failed: {}", activitiException.getMessage(), activitiException);
            ResponseModel<ProcessInstanceDTO> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    activitiException.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            logger.error("Error starting process instance with definition key {}, business key {}, and variables {}: {}", processDefinitionKey, businessKey, variables, e.getMessage());
            ResponseModel<ProcessInstanceDTO> response = new ResponseModel<>(
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<String> deployProcess(MultipartFile file) {
        logger.info("Deploying process with file: {}", file.getOriginalFilename());
        try {
//            if(!Helpers.isValidXmlFile(file)){
//                throw new Exception("Not a valid xml file!");
//            }
            InputStream bpmnStream = file.getInputStream();

            Deployment deployment = repositoryService.createDeployment()
                    .addInputStream(file.getOriginalFilename(), bpmnStream)
                    .name(file.getOriginalFilename())
                    .deploy();

            logger.info("Successfully deployed process: {}", deployment.getId());
            return ResponseEntity.status(HttpStatus.OK).body(deployment.toString());
        } catch (ActivitiException activitiException) {
            logger.error("Activiti operation failed: {}", activitiException.getMessage(), activitiException);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(activitiException.getMessage());
        } catch (Exception e) {
            logger.error("Error deploying process with file {}: {}", file.getOriginalFilename(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteAllDeployments() {
        logger.info("Deleting all deployments...");
        try {
            List<String> deployments = repositoryService.createDeploymentQuery()
                    .list()
                    .stream()
                    .map(Deployment::getId)
                    .toList();
            for(String deploymentId: deployments) {
                repositoryService.deleteDeployment(deploymentId, true);
            }
            logger.info("Successfully deleted all deployments.");
            return ResponseEntity.ok(deployments.toString());
        } catch (ActivitiException activitiException) {
            logger.error("Activiti operation failed: {}", activitiException.getMessage(), activitiException);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(activitiException.getMessage());
        } catch (Exception e) {
            logger.error("Error deleting all deployments: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteProcessInstance(String processInstanceId, String deleteReason) {
        logger.info("Deleting process instance with ID: {} for reason: {}", processInstanceId, deleteReason);
        try{
            runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
            logger.info("Successfully deleted process instance: {}", processInstanceId);
            return ResponseEntity.ok("Process instance deleted!");
        } catch (ActivitiException activitiException) {
            logger.error("Activiti operation failed: {}", activitiException.getMessage(), activitiException);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(activitiException.getMessage());
        } catch (Exception e) {
            logger.error("Error deleting process instance {}: {}", processInstanceId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteDeployment(String deploymentId) {
        logger.info("Deleting deployment with ID: {}", deploymentId);
        try{
            repositoryService.deleteDeployment(deploymentId, true);
            logger.info("Successfully deleted deployment: {}", deploymentId);
            return ResponseEntity.ok("Deployment deleted!");
        } catch (ActivitiException activitiException) {
            logger.error("Activiti operation failed: {}", activitiException.getMessage(), activitiException);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(activitiException.getMessage());
        } catch (Exception e) {
            logger.error("Error deleting deployment {}: {}", deploymentId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
