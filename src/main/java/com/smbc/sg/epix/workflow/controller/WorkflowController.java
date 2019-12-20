package com.smbc.sg.epix.workflow.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.smbc.sg.epix.workflow.dto.WorkflowCodeDTO;
import com.smbc.sg.epix.workflow.dto.WorkflowDTO;

@RequestMapping("/v1.0/workflow")
public interface WorkflowController {

  @GetMapping("/")
  public ResponseEntity<List<WorkflowDTO>> getWorkflowList(@RequestParam String filter);

  @GetMapping("/codes")
  public ResponseEntity<List<WorkflowCodeDTO>> getWorkflowCodeList();

  @GetMapping("/{uid}")
  public ResponseEntity<WorkflowDTO> getWorkflow(@PathVariable Long uid);

  @PostMapping("/")
  public ResponseEntity<Long> createWorkflow(@NotEmpty(
      message = "The User Id cannot be empty.") @RequestParam(required = true) String userId,
      @Valid @RequestBody WorkflowDTO wfDTO);

  @PutMapping("/{uid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateWorkflow(@PathVariable Long uid, @RequestParam String userId,
      @Valid @RequestBody WorkflowDTO wfDTO);

  @PatchMapping("/{uid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void modifyWorkflow(@PathVariable Long uid, @RequestParam String userId,
      @RequestParam Map<String, String> values);

  @DeleteMapping("/{uid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteWorkflow(@PathVariable Long uid, @RequestParam String userId);
}
