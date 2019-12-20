package com.smbc.sg.epix.workflow.controller.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.smbc.sg.epix.workflow.controller.WorkflowController;
import com.smbc.sg.epix.workflow.dto.WorkflowCodeDTO;
import com.smbc.sg.epix.workflow.dto.WorkflowDTO;
import com.smbc.sg.epix.workflow.service.WorkflowService;

@RestController
@Validated
public class WorkflowControllerImpl implements WorkflowController {
  private static final Logger logger = LoggerFactory.getLogger(WorkflowControllerImpl.class);

  @Autowired
  private WorkflowService wfService;

  // Get Workflow records
  @Override
  public ResponseEntity<List<WorkflowDTO>> getWorkflowList(String filter) {
    logger.debug("Get Workflow records...");
    return new ResponseEntity<List<WorkflowDTO>>(wfService.getWorkflowList(filter), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<WorkflowCodeDTO>> getWorkflowCodeList() {
    return new ResponseEntity<List<WorkflowCodeDTO>>(wfService.getWorkflowCodeList(),
        HttpStatus.OK);
  }

  @Override
  public ResponseEntity<WorkflowDTO> getWorkflow(Long uid) {
    return new ResponseEntity<WorkflowDTO>(wfService.getWorkflow(uid), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Long> createWorkflow(String userId, WorkflowDTO wfDTO) {
    return new ResponseEntity<Long>(wfService.createWorkflow(userId, wfDTO), HttpStatus.CREATED);
  }

  @Override
  public void updateWorkflow(Long uid, String userId, WorkflowDTO wfDTO) {
    wfService.updateWorkflow(userId, uid, wfDTO);
  }

  @Override
  public void modifyWorkflow(Long uid, String userId, Map<String, String> values) {
    // TODO Auto-generated method stub

  }

  @Override
  public void deleteWorkflow(Long uid, String userId) {
    logger.debug("++++deleteWorkflow");
    wfService.deleteWorkflow(uid, userId);
  }

}
