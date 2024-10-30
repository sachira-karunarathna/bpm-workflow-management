package com.bpm_workflow.bpm_workflow_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowElementDTO {
    private String id;
    private String name;
    private Map<String, List<ExtensionElement>> extentionElements;
    private Map<String, List<ExtensionAttribute>> attributes;
}
