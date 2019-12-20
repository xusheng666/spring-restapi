package com.smbc.sg.epix.workflow.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.smbc.sg.epix.workflow.dto.WorkflowCodeDTO;
import com.smbc.sg.epix.workflow.dto.WorkflowDTO;
import com.smbc.sg.epix.workflow.service.impl.ServiceException;

public interface WorkflowService {

  /**
   * Retrieve Workflow records.
   * 
   * Any Workflow record fulfill the search criteria (filter parameter) Return empty list if no
   * record fulfill the criteria.
   * 
   * 
   * @param filter Search criteria.
   * @return return Workflow list.
   * 
   * @throws ServiceException Will throw ServiceException if invalid search criteria provide.
   */
  public List<WorkflowDTO> getWorkflowList(String filter) throws ServiceException;

  /**
   * Get all defined Workflow codes
   * 
   * @return Workflow code list
   */
  public List<WorkflowCodeDTO> getWorkflowCodeList();

  /**
   * Get Workflow details by given Workflow uinque id.
   * 
   * @param uid Workflow unique id
   * @return Workflow details
   * @throws ServiceException Throw the exception when the Workflow not found in db.
   */
  public WorkflowDTO getWorkflow(Long uid) throws ServiceException;

  /**
   * Create a new Workflow
   * 
   * @param wfDTO New Workflow details
   * @param userId Current user id
   * @return Newly created Workflow unique id.
   * @throws ServiceException Throw the exception when same Workflow code already existed in
   *         database.
   */
  public Long createWorkflow(String userId, WorkflowDTO wfDTO) throws ServiceException;

  /**
   * Update an existing Workflow record.
   * 
   * @param wfDTO Workflow details.
   * @param userId Current user id
   * @param uid Workflow unique id
   * @return Return true if updated successful, otherwise return false.
   * @throws ServiceException Throw the exception if the Workflow cannot be found in databsae
   */
  public Boolean updateWorkflow(String userId, Long uid, WorkflowDTO wfDTO) throws ServiceException;

  /**
   * Modifi certain values of an existing Workflow record.
   * 
   * @param wfDTO Workflow details.
   * @param userId Current user id
   * @return Return true if updated successful, otherwise return false.
   * @throws ServiceException Throw the exeption when 1. The Workflow cannot be found in database 2.
   *         The provided field names are invalid.
   */
  public Boolean modifyWorkflow(String userId, Long uid, Map<String, String> workflow)
      throws ServiceException;

  /**
   * Delete Workflow by given Workflow unique id
   * 
   * @param uid Workflow unique id
   * @param userId Current user id
   * @return Return true if deletion successful, otherwise return false.
   * @throws ServiceException Throw the exception if the Workflow cannot be found in database
   */
  public Boolean deleteWorkflow(Long uid, String userId) throws ServiceException;
}
