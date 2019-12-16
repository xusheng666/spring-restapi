package com.smbc.sg.epix.workflow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smbc.sg.epix.workflow.dto.WorkflowDTO;

@RequestMapping("/v1.0/workflow")
public interface WorkflowController {
	
	@GetMapping("/")
    public ResponseEntity<List<WorkflowDTO>> getWorkflowList(
    		@RequestParam String filter);
}
