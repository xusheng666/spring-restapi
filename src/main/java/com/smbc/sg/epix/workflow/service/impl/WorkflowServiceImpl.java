package com.smbc.sg.epix.workflow.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.smbc.sg.epix.workflow.controller.impl.ResponseCode;
import com.smbc.sg.epix.workflow.dao.WorkflowRepository;
import com.smbc.sg.epix.workflow.dto.WorkflowCodeDTO;
import com.smbc.sg.epix.workflow.dto.WorkflowDTO;
import com.smbc.sg.epix.workflow.entity.Subtype;
import com.smbc.sg.epix.workflow.entity.Workflow;
import com.smbc.sg.epix.workflow.service.WorkflowService;

@Service
public class WorkflowServiceImpl extends BaseService<Workflow> implements WorkflowService {
  private static final Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);

  @Autowired
  private WorkflowRepository workflowRepo;

  @Override
  public List<WorkflowDTO> getWorkflowList(String filter) throws ServiceException {
    logger.info("listWorkflow: filter=" + filter);
    // Parse search criteria and convert to JPA Specification object.
    Specification<Workflow> spec = parseSearchCriteria(filter);

    // Retrieve Workflow list.
    List<Workflow> wfList = new ArrayList<Workflow>();

    if (Objects.nonNull(spec))
      wfList = workflowRepo.findAll(spec);
    else
      wfList = workflowRepo.findAll();

    logger.info("inside service, the list size = " + wfList.size());
    // Convert Workflow List to WorkflowDTO list.
    ModelMapper modelMapper = new ModelMapper();
    List<WorkflowDTO> wfDtoList = Arrays.asList(modelMapper.map(wfList, WorkflowDTO[].class));

    logger.info("Workflow DTO list= " + wfDtoList);
    return wfDtoList;
  }

  @Override
  public List<WorkflowCodeDTO> getWorkflowCodeList() {

    List<Workflow> wfList = workflowRepo.findAll();

    ModelMapper modelMapper = new ModelMapper();
    List<WorkflowCodeDTO> wfCodeDTOList =
        Arrays.asList(modelMapper.map(wfList, WorkflowCodeDTO[].class));

    return wfCodeDTOList;
  }

  @Override
  public WorkflowDTO getWorkflow(Long uid) throws ServiceException {
    Optional<Workflow> wfo = workflowRepo.findById(uid);

    WorkflowDTO wfDTO = null;

    if (wfo.isPresent()) {
      Workflow wf = wfo.get();

      ModelMapper modelMapper = new ModelMapper();
      wfDTO = modelMapper.map(wf, WorkflowDTO.class);
    } else {
      throw new ServiceException(ResponseCode.RecordNotFound.toString(),
          "The Workflow " + uid + " cannot be found.");
    }


    return wfDTO;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Long createWorkflow(String userId, WorkflowDTO wfDTO) throws ServiceException {
    Long uid = null;

    if (Objects.nonNull(wfDTO)) {

      logger.debug("Start saving Workflow object.");

      // Check whether the Workflow Code is duplicate

      if (checkWorkflowCode(wfDTO)) {
        throw new ServiceException(ResponseCode.DuplicateWorkflowCode.toString(),
            "The Workflow code is already existed.");
      }

      // Convert DTO to Entity
      Workflow wf = new ModelMapper().map(wfDTO, Workflow.class);

      setupEntityRelationship(wf, userId);

      logger.debug(" the Workflow object to be saved: " + wf);
      // Set CreatedBy, CreatedDate, UpdatedBy and UpdatedDate
      setCreatedUpdatedByDate(wf, userId);
      logger.debug("current date time: " + wf.getCreatedDate());

      // Save Workflow object including the related Steps, Fields and Subtypes
      logger.debug("Before save workflow object.");

      Workflow workflow = workflowRepo.save(wf);


      logger.debug("Save Workflow object successful." + workflow);

      if (Objects.nonNull(workflow))
        uid = workflow.getUid();

    }

    return uid;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Boolean updateWorkflow(String userId, Long uid, WorkflowDTO wfDTO)
      throws ServiceException {

    if (Objects.nonNull(wfDTO) && Objects.nonNull(uid)) {

      wfDTO.setUid(uid);
      Optional<Workflow> wfo = workflowRepo.findById(uid);
      if (wfo.isEmpty())
        throw new ServiceException(ResponseCode.RecordNotFound.toString(),
            "The Workflow " + uid + " cannot be found.");

      // Check whether the Workflow Code duplicate
      if (checkWorkflowCode(wfDTO))
        throw new ServiceException(ResponseCode.DuplicateWorkflowCode.toString(),
            "The Workflow code is already existed.");

      // Convert DTO to Entity
      Workflow wf = new ModelMapper().map(wfDTO, Workflow.class);

      // Soft delete all Subtype of current Workflow.
      List<Subtype> subtypeList = wfo.get().getSubtypeList();
      if (Objects.nonNull(subtypeList)) {
        subtypeList.forEach(subtype -> {
          subtype.setDeleteFlag(true);
          setUpdatedByDate(subtype, userId);
        });
      }


      if (Objects.nonNull(wf)) {

        setupEntityRelationship(wf, userId);

        setUpdatedByDate(wf, userId);
        wf.setCreatedBy(wfo.get().getCreatedBy());
        wf.setCreatedDate(wfo.get().getCreatedDate());

        // Update Workflow
        workflowRepo.saveAndFlush(wf);
      }
    }

    return true;
  }

  @Override
  public Boolean modifyWorkflow(String userId, Long uid, Map<String, String> workflow)
      throws ServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Boolean deleteWorkflow(Long uid, String userId) throws ServiceException {
    Optional<Workflow> wfo = workflowRepo.findById(uid);

    if (wfo.isEmpty())
      throw new ServiceException(ResponseCode.RecordNotFound.toString(),
          "The Workflow " + uid + " cannot be found.");

    Workflow wf = wfo.get();

    if (Objects.nonNull(wf)) {

      // Soft delete all Steps
      if (Objects.nonNull(wf.getStepList())) {
        wf.getStepList().forEach(step -> {
          step.setDeleteFlag(true);
          setUpdatedByDate(step, userId);
        });
      }

      // Soft delete all Subtypes
      if (Objects.nonNull(wf.getSubtypeList())) {
        wf.getSubtypeList().forEach(subtype -> {
          subtype.setDeleteFlag(true);
          setUpdatedByDate(subtype, userId);
        });
      }

      // Soft delete all Fields
      if (Objects.nonNull(wf.getFieldList())) {
        wf.getFieldList().forEach(field -> {
          field.setDeleteFlag(true);
          setUpdatedByDate(field, userId);

          // Soft delete all Field Options
          if (Objects.nonNull(field.getFieldOptionList())) {
            field.getFieldOptionList().forEach(fieldOption -> {
              fieldOption.setDeleteFlag(true);
              setUpdatedByDate(fieldOption, userId);

            });
          }
        });
      }


      setUpdatedByDate(wf, userId);
      wf.setDeleteFlag(true);

      workflowRepo.saveAndFlush(wf);
    }

    return true;
  }

  /**
   * Check if the Workflow code already existing in database.
   * 
   * @param wfCode Workflow Code
   * @return Return true if existed, otherwise return false.
   */
  private Boolean checkWorkflowCode(WorkflowDTO wfd) {

    if (Objects.nonNull(wfd) && Objects.nonNull(wfd.getWfCode())) {
      Optional<Workflow> wfo = workflowRepo.findByWfCode(wfd.getWfCode());

      if (wfo.isPresent()) {
        // Exclude current Workflow itself when checking duplicate Workflow code.
        if (Objects.nonNull(wfd.getUid()) && wfd.getUid().equals(wfo.get().getUid()))
          return false;

        return true;
      }
    }

    return false;
  }

  private void setupEntityRelationship(Workflow wf, String userId) {
    if (Objects.nonNull(wf)) {
      // Set Workflow to Subtype
      if (Objects.nonNull(wf.getSubtypeList())) {
        wf.getSubtypeList().forEach(subtype -> {
          subtype.setWorkflow(wf);
          setCreatedUpdatedByDate(subtype, userId);
        });
      }

      // Set Workflow to Step
      if (Objects.nonNull(wf.getStepList())) {
        wf.getStepList().forEach(step -> {
          step.setWorkflow(wf);
          setCreatedUpdatedByDate(step, userId);
        });
      }

      // Set Workflow to Step
      if (Objects.nonNull(wf.getFieldList())) {
        wf.getFieldList().forEach(field -> {
          field.setWorkflow(wf);
          setCreatedUpdatedByDate(field, userId);

          if (Objects.nonNull(field.getFieldOptionList())) {
            field.getFieldOptionList().forEach(fieldOption -> {
              fieldOption.setField(field);
              setCreatedUpdatedByDate(fieldOption, userId);
            });
          }
        });
      }
    }
  }
}
