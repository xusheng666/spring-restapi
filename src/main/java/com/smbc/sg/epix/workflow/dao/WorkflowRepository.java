package com.smbc.sg.epix.workflow.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.smbc.sg.epix.workflow.entity.Workflow;

public interface WorkflowRepository 
	extends JpaRepository<Workflow, Long>, JpaSpecificationExecutor<Workflow> {
	
	public Optional<Workflow> findByWfCode(String wfCode);
}
