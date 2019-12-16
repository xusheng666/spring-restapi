package com.smbc.sg.epix.workflow.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.smbc.sg.epix.workflow.configuration.StartGWorkflowApplication;
import com.smbc.sg.epix.workflow.controller.impl.ResponseCode;
import com.smbc.sg.epix.workflow.dao.WorkflowRepository;
import com.smbc.sg.epix.workflow.dto.WorkflowCodeDTO;
import com.smbc.sg.epix.workflow.dto.WorkflowDTO;
import com.smbc.sg.epix.workflow.entity.Workflow;
import com.smbc.sg.epix.workflow.service.impl.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartGWorkflowApplication.class)
@ActiveProfiles("test")
//@SpringBootApplication(scanBasePackages = "com.smbc")
//@EntityScan(basePackages = "com.smbc.sg.epix.workflow.entity")
//@EnableJpaRepositories("com.smbc.sg.epix.workflow.dao")
public class WorkflowServiceTest {
	
	@MockBean
	private WorkflowRepository wfRepo;
	
	@Autowired
	private WorkflowService wfService;
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();

	@Test
	public void getWorkflowListWithFilter() {
		String filter = "wfCode==WF_ISG and expiryAction==RETURN";
	    
		Workflow wf1 = prepareWorkflow();		
		List<Workflow> wfList = Arrays.asList(wf1);
				
		when(wfRepo.findAll(org.mockito.ArgumentMatchers.isA(Specification.class))).thenReturn(wfList);
		
		List<WorkflowDTO> wfDTOList = wfService.getWorkflowList(filter);
		
		assertTrue(Objects.nonNull(wfDTOList));
		assertEquals(1, wfDTOList.size());
		assertEquals(1, wfDTOList.get(0).getUid());
		assertEquals("WF_ISG", wfDTOList.get(0).getWfCode());
	}
	
	@Test
	public void getWorkflowListWithoutFilter() {
		Workflow wf1 = prepareWorkflow();
		Workflow wf2 = prepareWorkflow();
		wf2.setUid(Long.parseLong("2"));
		wf2.setWfCode("WF_IPG");
		
		List<Workflow> wfList = Arrays.asList(wf1, wf2);
				
		when(wfRepo.findAll()).thenReturn(wfList);
		
		List<WorkflowDTO> wfDTOList = wfService.getWorkflowList(null);
		
		assertTrue(Objects.nonNull(wfDTOList));
		assertEquals(2, wfDTOList.size());
		assertEquals(1, wfDTOList.get(0).getUid());
		assertEquals("WF_ISG", wfDTOList.get(0).getWfCode());
		assertEquals(2, wfDTOList.get(1).getUid());
		assertEquals("WF_IPG", wfDTOList.get(1).getWfCode());
	}
	
	@Test
	public void getWorkflowListWithoutRecord() {		
		List<Workflow> wfList = new ArrayList<Workflow>();
				
		when(wfRepo.findAll()).thenReturn(wfList);
		
		wfService.getWorkflowList(null);
		
		List<WorkflowDTO> wfDTOList = wfService.getWorkflowList(null);
		
		assertTrue(Objects.isNull(wfDTOList) || wfDTOList.size() == 0);
	}
	
	@Test
	public void getWorkflowCodeList() {
		Workflow wf1 = prepareWorkflow();
		Workflow wf2 = prepareWorkflow();
		wf2.setUid(Long.parseLong("2"));
		wf2.setWfCode("WF_IPG");
		
		List<Workflow> wfList = Arrays.asList(wf1, wf2);
				
		when(wfRepo.findAll()).thenReturn(wfList);
		
		List<WorkflowCodeDTO> wfcDTOList = wfService.getWorkflowCodeList();
		assertTrue(Objects.nonNull(wfcDTOList));
		assertEquals(2, wfcDTOList.size());
		assertEquals(1, wfcDTOList.get(0).getUid());
		assertEquals("WF_ISG", wfcDTOList.get(0).getWfCode());
		assertEquals(2, wfcDTOList.get(1).getUid());
		assertEquals("WF_IPG", wfcDTOList.get(1).getWfCode());
		
	}
	
	@Test 
	public void getWorkflowOk() {
		Optional<Workflow> wfo = Optional.ofNullable(prepareWorkflow());
		when(wfRepo.findById(workflowUid)).thenReturn(wfo);
		
		Optional<WorkflowDTO> workflowDTO = wfService.getWorkflow(workflowUid);
		assertTrue(workflowDTO.isPresent());
		assertEquals("WF_ISG", workflowDTO.get().getWfCode());
	}
	
	@Test 
	public void getWorkflowRecordNotFound() {
		thrown.expect(ServiceException.class);
		thrown.expect(hasProperty("code", is(ResponseCode.RecordNotFound.toString())));
		
		Optional<Workflow> wfo = Optional.ofNullable(prepareWorkflow());
		when(wfRepo.findById(workflowUid)).thenReturn(wfo);
		
		wfService.getWorkflow(Long.parseLong("2"));
	}
	
	@Test
	public void deleteWorkflowOk() {
		
		Optional<Workflow> wfo = Optional.ofNullable(prepareWorkflow());
		when(wfRepo.findById(workflowUid)).thenReturn(wfo);
		
		Boolean result = wfService.deleteWorkflow(workflowUid, currentUserId);
		assertTrue(result);
	}
	
	@Test
	public void deleteWorkflowRecordNotFound() {
		thrown.expect(ServiceException.class);
		thrown.expect(hasProperty("code", is(ResponseCode.RecordNotFound.toString())));
		wfService.deleteWorkflow(workflowUid, currentUserId);
	}
	
	private Workflow prepareWorkflow() {
		Workflow wf = new Workflow();
		wf.setUid(workflowUid);
		wf.setBranchEnabled(false);
		wf.setCloseInDays(11);
		wf.setCreatedBy("SYS");
		wf.setCreatedDate(LocalDate.now());
		wf.setDeleteFlag(false);
		wf.setExpiryAction("RETURN");
		wf.setExpiryInDays(30);
		wf.setUpdatedBy("SYS");
		wf.setUpdatedDate(LocalDate.now());
		wf.setVersion(1);
		wf.setWfCode("WF_ISG");
		wf.setWfName("Workflow for ISG");
		wf.setWfReconfig(false);
		
		return wf;
	}
	
	private String currentUserId = "User1";
	private Long workflowUid = Long.parseLong("1");
}
