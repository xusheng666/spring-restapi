package com.smbc.sg.epix.workflow.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;

import com.smbc.sg.epix.workflow.common.BaseTest;
import com.smbc.sg.epix.workflow.common.WorkflowData;
import com.smbc.sg.epix.workflow.controller.impl.ResponseCode;
import com.smbc.sg.epix.workflow.dao.FieldOptionRepository;
import com.smbc.sg.epix.workflow.dao.FieldRepository;
import com.smbc.sg.epix.workflow.dao.StepRepository;
import com.smbc.sg.epix.workflow.dao.SubtypeRepository;
import com.smbc.sg.epix.workflow.dao.WorkflowRepository;
import com.smbc.sg.epix.workflow.dto.WorkflowCodeDTO;
import com.smbc.sg.epix.workflow.dto.WorkflowDTO;
import com.smbc.sg.epix.workflow.entity.Workflow;
import com.smbc.sg.epix.workflow.service.impl.ServiceException;

public class WorkflowServiceTest extends BaseTest {

  @MockBean
  private WorkflowRepository wfRepo;

  @MockBean
  private SubtypeRepository subtypeRepo;

  @MockBean
  private FieldRepository fieldRepo;

  @MockBean
  private StepRepository stepRepo;

  @MockBean
  private FieldOptionRepository fieldOptionRepo;

  @Autowired
  private WorkflowService wfService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void getWorkflowListWithFilter() {
    String filter = "wfCode==WF_ISG and expiryAction==RETURN";

    Workflow wf1 = WorkflowData.prepareWorkflow();
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
    Workflow wf1 = WorkflowData.prepareWorkflow();
    Workflow wf2 = WorkflowData.prepareWorkflow();
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
    Workflow wf1 = WorkflowData.prepareWorkflow();
    Workflow wf2 = WorkflowData.prepareWorkflow();
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
    Optional<Workflow> wfo = Optional.ofNullable(WorkflowData.prepareWorkflow());
    when(wfRepo.findById(WorkflowData.workflowUid)).thenReturn(wfo);

    WorkflowDTO workflowDTO = wfService.getWorkflow(WorkflowData.workflowUid);
    assertTrue(Objects.nonNull(workflowDTO));
    assertEquals("WF_ISG", workflowDTO.getWfCode());
  }

  @Test
  public void getWorkflowRecordNotFound() {
    thrown.expect(ServiceException.class);
    thrown.expect(hasProperty("code", is(ResponseCode.RecordNotFound.toString())));

    Optional<Workflow> wfo = Optional.ofNullable(WorkflowData.prepareWorkflow());
    when(wfRepo.findById(WorkflowData.workflowUid)).thenReturn(wfo);

    wfService.getWorkflow(Long.parseLong("2"));
  }

  @Test
  public void createWorkflowDuplicateWfCode() {
    Optional<Workflow> wfo = Optional.ofNullable(WorkflowData.prepareWorkflow());
    when(wfRepo.findByWfCode(org.mockito.ArgumentMatchers.anyString())).thenReturn(wfo);

    WorkflowDTO wfd = WorkflowData.prepareWorkflowDTO();
    wfd.setUid(null);

    thrown.expect(ServiceException.class);
    thrown.expect(hasProperty("code", is(ResponseCode.DuplicateWorkflowCode.toString())));
    wfService.createWorkflow(WorkflowData.currentUserId, wfd);
  }

  @Test
  public void createWorkflow() {
    Workflow wf = WorkflowData.prepareWorkflow();
    when(wfRepo.save(org.mockito.ArgumentMatchers.any(Workflow.class))).thenReturn(wf);

    Long uid =
        wfService.createWorkflow(WorkflowData.currentUserId, WorkflowData.prepareWorkflowDTO());
    assertNotNull(uid);
    assertEquals(1, uid);
  }

  @Test
  public void updateWorkflowRecordNotFound() {
    when(wfRepo.findById(WorkflowData.workflowUid)).thenReturn(Optional.empty());

    thrown.expect(ServiceException.class);
    thrown.expect(hasProperty("code", is(ResponseCode.RecordNotFound.toString())));

    wfService.updateWorkflow(WorkflowData.currentUserId, WorkflowData.workflowUid,
        WorkflowData.prepareWorkflowDTO());
  }

  @Test
  public void updateWorkflowDuplicateWfCode() {

    when(wfRepo.findById(WorkflowData.workflowUid))
        .thenReturn(Optional.of(WorkflowData.prepareWorkflow()));

    Optional<Workflow> wfo = Optional.ofNullable(WorkflowData.prepareWorkflow());
    wfo.get().setUid(Long.parseLong("2"));
    when(wfRepo.findByWfCode(org.mockito.ArgumentMatchers.anyString())).thenReturn(wfo);

    thrown.expect(ServiceException.class);
    thrown.expect(hasProperty("code", is(ResponseCode.DuplicateWorkflowCode.toString())));
    wfService.updateWorkflow(WorkflowData.currentUserId, WorkflowData.workflowUid,
        WorkflowData.prepareWorkflowDTO());
  }

  @Test
  public void updateWorkflow() {
    when(wfRepo.findById(WorkflowData.workflowUid))
        .thenReturn(Optional.of(WorkflowData.prepareWorkflow()));

    Boolean result = wfService.updateWorkflow(WorkflowData.currentUserId, WorkflowData.workflowUid,
        WorkflowData.prepareWorkflowDTO());

    assertTrue(result);
  }

  @Test
  public void deleteWorkflowOk() {

    Optional<Workflow> wfo = Optional.ofNullable(WorkflowData.prepareWorkflow());
    when(wfRepo.findById(WorkflowData.workflowUid)).thenReturn(wfo);

    Boolean result = wfService.deleteWorkflow(WorkflowData.workflowUid, WorkflowData.currentUserId);
    assertTrue(result);
  }

  @Test
  public void deleteWorkflowRecordNotFound() {
    thrown.expect(ServiceException.class);
    thrown.expect(hasProperty("code", is(ResponseCode.RecordNotFound.toString())));
    wfService.deleteWorkflow(WorkflowData.workflowUid, WorkflowData.currentUserId);
  }
}
