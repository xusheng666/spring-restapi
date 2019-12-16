package com.smbc.sg.epix.workflow.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.smbc.sg.epix.workflow.controller.impl.ResponseCode;
import com.smbc.sg.epix.workflow.dao.WorkflowRepository;
import com.smbc.sg.epix.workflow.dto.WorkflowCodeDTO;
import com.smbc.sg.epix.workflow.dto.WorkflowDTO;
import com.smbc.sg.epix.workflow.entity.Workflow;
import com.smbc.sg.epix.workflow.service.WorkflowService;

@Service
public class WorkflowServiceImpl extends BaseService<Workflow> implements WorkflowService {
	private static final Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);
	
	@Autowired
	private WorkflowRepository wfRepo;

	@Override
	public List<WorkflowDTO> getWorkflowList(String filter) throws ServiceException {
		logger.info("listWorkflow: filter="+filter);
		//Parse search criteria and convert to JPA Specification object.
		Specification<Workflow> spec = parseSearchCriteria(filter);
		
	    //Retrieve Workflow list.
		List<Workflow> wfList = new ArrayList<Workflow>();

		if (Objects.nonNull(spec))
			wfList = wfRepo.findAll(spec);
		else
			wfList = wfRepo.findAll();
		
		logger.info("inside service, the list size = "+wfList.size());
		//Convert Workflow List to WorkflowDTO list.
		ModelMapper modelMapper = new ModelMapper();
		List<WorkflowDTO> wfDtoList = Arrays.asList(
				modelMapper.map(wfList, WorkflowDTO[].class));
		
		logger.info("Workflow DTO list= "+wfDtoList);
		return wfDtoList;
	}

	@Override
	public List<WorkflowCodeDTO> getWorkflowCodeList() {
		
		List<Workflow> wfList = wfRepo.findAll();
		
		ModelMapper modelMapper = new ModelMapper();
		List<WorkflowCodeDTO> wfCodeDTOList = Arrays.asList(
				modelMapper.map(wfList, WorkflowCodeDTO[].class));
		
		return wfCodeDTOList;
	}

	@Override
	public Optional<WorkflowDTO> getWorkflow(Long uid) throws ServiceException {
		Optional<Workflow> wfo = wfRepo.findById(uid);
		
		WorkflowDTO wfDTO = null;
		
		if (wfo.isPresent()) {
			Workflow wf = wfo.get();
			
			ModelMapper modelMapper = new ModelMapper();
			wfDTO = modelMapper.map(wf, WorkflowDTO.class);
		} else {
			throw new ServiceException(ResponseCode.RecordNotFound.toString(), 
					"The Workflow " + uid+ " cannot be found.");
		}
		
		
		return Optional.ofNullable(wfDTO);
	}

	@Override
	public Long createWorkflow(String userId, WorkflowDTO wfDTO) throws ServiceException {
		Long uid = null;
		
		if (Objects.nonNull(wfDTO)) {
			
			 //Throw exception if provided Workflow Code existed.
			 if (checkWorkflowCode(wfDTO.getWfCode()))
				 throw new ServiceException(
						 ResponseCode.DuplicateWorkflowCode.toString(), 
						 "The Workflow code is already existed.");
			 
			 Workflow wf = new ModelMapper().map(wfDTO, Workflow.class);
			 
			 setCreatedUpdatedByDate(wf, userId);
			 uid = wfRepo.save(wf).getUid();
		}
		
		return uid;
	}

	@Override
	public Boolean updateWorkflow(String userId, Long uid, WorkflowDTO wfDTO) throws ServiceException {
		if (Objects.nonNull(wfDTO) && Objects.nonNull(uid)) {
			
			if (wfRepo.findById(uid).isEmpty())
				throw new ServiceException(ResponseCode.RecordNotFound.toString(), 
						"The Workflow " + uid+ " cannot be found.");
				
			 //Throw exception if provided Workflow Code existed.
			 if (checkWorkflowCode(wfDTO.getWfCode()))
				 throw new ServiceException(
						 ResponseCode.DuplicateWorkflowCode.toString(), 
						 "The Workflow code is already existed.");
			 
			 Workflow wf = new ModelMapper().map(wfDTO, Workflow.class);
			 setUpdatedByDate(wf, userId);
			 uid = wfRepo.save(wf).getUid();
		}
		
		return true;
	}

	@Override
	public Boolean modifyWorkflow(String userId, Long uid, 
			Map<String, String> workflow) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteWorkflow(Long uid, String userId) throws ServiceException {
		Optional<Workflow> wfo = wfRepo.findById(uid);
		
		if (wfo.isEmpty())
			throw new ServiceException(ResponseCode.RecordNotFound.toString(), 
					"The Workflow " + uid+ " cannot be found.");
		
		Workflow wf = wfo.get();
		setUpdatedByDate(wf, userId);
		wf.setDeleteFlag(true);
		
		wfRepo.saveAndFlush(wf);
		
		return true;
	}
	
	/**
	 * Check if the Workflow code already existing in database.
	 * 
	 * @param wfCode Workflow Code
	 * @return Return true if existed, otherwise return false.
	 */
	private Boolean checkWorkflowCode(String wfCode) {
		
		if (Objects.nonNull(wfCode)) {
			Optional<Workflow> wfo = wfRepo.findByWfCode(wfCode);
			return wfo.isPresent();
		}
		
		return false;
	}
}
