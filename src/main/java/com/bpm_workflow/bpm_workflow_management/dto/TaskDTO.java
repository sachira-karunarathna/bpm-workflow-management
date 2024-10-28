package com.bpm_workflow.bpm_workflow_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
public class TaskDTO {
    private Integer priority;
    private String name;
    private String owner;
    private String tenantId;
    private Integer appVersion;
    private String delegationState;
    private String description;
    private Boolean suspended;
    private String assignee;
    private Date dueDate;
    private String parentTaskId;
    private String category;
    private String formKey;
    private String id;
    private String executionId;
    private Date createTime;
    private String businessKey;
    private String processDefinitionId;
    private Date claimTime;
    private String processInstanceId;
    private String taskDefinitionKey;
    private Map<String, Object> processVariables;
    private Map<String, Object> taskLocalVariables;

    public TaskDTO(Integer priority, String name, String owner, String tenantId, Integer appVersion, String delegationState, String description, Boolean suspended, String assignee, Date dueDate, String parentTaskId, String category, String formKey, String id, String executionId, Date createTime, String businessKey, String processDefinitionId, Date claimTime, String processInstanceId, String taskDefinitionKey, Map<String, Object> processVariables, Map<String, Object> taskLocalVariables) {
        this.priority = priority;
        this.name = name;
        this.owner = owner;
        this.tenantId = tenantId;
        this.appVersion = appVersion;
        this.delegationState = delegationState;
        this.description = description;
        this.suspended = suspended;
        this.assignee = assignee;
        this.dueDate = dueDate;
        this.parentTaskId = parentTaskId;
        this.category = category;
        this.formKey = formKey;
        this.id = id;
        this.executionId = executionId;
        this.createTime = createTime;
        this.businessKey = businessKey;
        this.processDefinitionId = processDefinitionId;
        this.claimTime = claimTime;
        this.processInstanceId = processInstanceId;
        this.taskDefinitionKey = taskDefinitionKey;
        this.processVariables = processVariables;
        this.taskLocalVariables = taskLocalVariables;
    }
}
