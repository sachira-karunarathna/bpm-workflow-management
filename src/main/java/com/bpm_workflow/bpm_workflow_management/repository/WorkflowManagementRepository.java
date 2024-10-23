package com.bpm_workflow.bpm_workflow_management.repository;

import com.bpm_workflow.bpm_workflow_management.entity.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowManagementRepository extends JpaRepository<Workflow, Long> {
}
