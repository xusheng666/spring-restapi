package com.smbc.sg.epix.workflow.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smbc.sg.epix.workflow.common.BaseTest;
import com.smbc.sg.epix.workflow.common.WorkflowData;
import com.smbc.sg.epix.workflow.controller.impl.ControllerExceptionHandler;
import com.smbc.sg.epix.workflow.controller.impl.ResponseCode;
import com.smbc.sg.epix.workflow.controller.impl.WorkflowControllerImpl;
import com.smbc.sg.epix.workflow.dto.WorkflowCodeDTO;
import com.smbc.sg.epix.workflow.dto.WorkflowDTO;
import com.smbc.sg.epix.workflow.service.WorkflowService;
import com.smbc.sg.epix.workflow.service.impl.ServiceException;

public class WorkflowControllerTest extends BaseTest {

  @Autowired
  private MockMvc mockMvc;

  @Mock
  private WorkflowService wfService;

  @InjectMocks
  private WorkflowControllerImpl wfController;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static final ObjectMapper om = new ObjectMapper();

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    this.mockMvc = MockMvcBuilders.standaloneSetup(wfController)
        .setControllerAdvice(new ControllerExceptionHandler()).build();
  }

  @Test
  public void getWorkflowList() throws Exception {
    WorkflowDTO wf1 = WorkflowData.prepareWorkflowDTO();
    WorkflowDTO wf2 = WorkflowData.prepareWorkflowDTO();
    wf2.setUid(Long.parseLong("2"));
    wf2.setWfCode("WF_IPG");

    List<WorkflowDTO> wfList = Arrays.asList(wf1, wf2);

    when(wfService.getWorkflowList(org.mockito.ArgumentMatchers.anyString())).thenReturn(wfList);

    mockMvc.perform(get(WorkflowData.uri + "/?filter="))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$[0].uid", is(wf1.getUid().intValue())))
        .andExpect(jsonPath("$[0].wfCode", is(wf1.getWfCode())))
        .andExpect(jsonPath("$[0].expiryInDays", is(wf1.getExpiryInDays())))
        .andExpect(jsonPath("$[0].expiryAction", is(wf1.getExpiryAction())))
        .andExpect(jsonPath("$[0].closeInDays", is(wf1.getCloseInDays())))
        .andExpect(jsonPath("$[0].wfReconfig", is(wf1.getWfReconfig())))
        .andExpect(jsonPath("$[0].branchEnabled", is(wf1.getBranchEnabled())))
        .andExpect(jsonPath("$[0].version", is(wf1.getVersion())))
        .andExpect(jsonPath("$[0].createdBy", is(wf1.getCreatedBy())))
        .andExpect(jsonPath("$[0].createdDate[0]", is(wf1.getCreatedDate().getYear())))
        .andExpect(jsonPath("$[0].createdDate[1]", is(wf1.getCreatedDate().getMonthValue())))
        .andExpect(jsonPath("$[0].createdDate[2]", is(wf1.getCreatedDate().getDayOfMonth())))
        .andExpect(jsonPath("$[0].updatedBy", is(wf1.getUpdatedBy())))
        .andExpect(jsonPath("$[0].updatedDate[0]", is(wf1.getUpdatedDate().getYear())))
        .andExpect(jsonPath("$[0].updatedDate[1]", is(wf1.getUpdatedDate().getMonthValue())))
        .andExpect(jsonPath("$[0].updatedDate[2]", is(wf1.getUpdatedDate().getDayOfMonth())))
        .andExpect(
            jsonPath("$[0].subtypeList[0].label", is(wf1.getSubtypeList().get(0).getLabel())))
        .andExpect(jsonPath("$[0].subtypeList[0].fieldValue",
            is(wf1.getSubtypeList().get(0).getFieldValue())))
        .andExpect(
            jsonPath("$[0].subtypeList[1].label", is(wf1.getSubtypeList().get(1).getLabel())))
        .andExpect(jsonPath("$[0].subtypeList[1].fieldValue",
            is(wf1.getSubtypeList().get(1).getFieldValue())))
        .andExpect(jsonPath("$[1].uid", is(wf2.getUid().intValue())))
        .andExpect(jsonPath("$[1].wfCode", is(wf2.getWfCode())))
        .andExpect(jsonPath("$[1].expiryInDays", is(wf2.getExpiryInDays())))
        .andExpect(jsonPath("$[1].expiryAction", is(wf2.getExpiryAction())))
        .andExpect(jsonPath("$[1].closeInDays", is(wf2.getCloseInDays())))
        .andExpect(jsonPath("$[1].wfReconfig", is(wf2.getWfReconfig())))
        .andExpect(jsonPath("$[1].branchEnabled", is(wf2.getBranchEnabled())))
        .andExpect(jsonPath("$[1].version", is(wf2.getVersion())))
        .andExpect(jsonPath("$[1].createdBy", is(wf2.getCreatedBy())))
        .andExpect(jsonPath("$[1].createdDate[0]", is(wf2.getCreatedDate().getYear())))
        .andExpect(jsonPath("$[1].createdDate[1]", is(wf2.getCreatedDate().getMonthValue())))
        .andExpect(jsonPath("$[1].createdDate[2]", is(wf2.getCreatedDate().getDayOfMonth())))
        .andExpect(jsonPath("$[1].updatedBy", is(wf2.getUpdatedBy())))
        .andExpect(jsonPath("$[1].updatedDate[0]", is(wf2.getUpdatedDate().getYear())))
        .andExpect(jsonPath("$[1].updatedDate[1]", is(wf2.getUpdatedDate().getMonthValue())))
        .andExpect(jsonPath("$[1].updatedDate[2]", is(wf2.getUpdatedDate().getDayOfMonth())))
        .andExpect(
            jsonPath("$[1].subtypeList[0].label", is(wf2.getSubtypeList().get(0).getLabel())))
        .andExpect(jsonPath("$[1].subtypeList[0].fieldValue",
            is(wf2.getSubtypeList().get(0).getFieldValue())))
        .andExpect(
            jsonPath("$[1].subtypeList[1].label", is(wf2.getSubtypeList().get(1).getLabel())))
        .andExpect(jsonPath("$[1].subtypeList[1].fieldValue",
            is(wf2.getSubtypeList().get(1).getFieldValue())));

    verify(wfService, times(1)).getWorkflowList("");
  }

  @Test
  public void getWorkflowListWithFilter() throws Exception {
    String filter = "wfCode==WF_ISG and expiryAction==RETURN";
    WorkflowDTO wf1 = WorkflowData.prepareWorkflowDTO();
    List<WorkflowDTO> wfList = Arrays.asList(wf1);

    when(wfService.getWorkflowList(org.mockito.ArgumentMatchers.contains(filter)))
        .thenReturn(wfList);

    mockMvc.perform(get(WorkflowData.uri + "/?filter=" + filter))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(1)))
        .andExpect(jsonPath("$[0].uid", is(wf1.getUid().intValue())))
        .andExpect(jsonPath("$[0].wfCode", is(wf1.getWfCode())))
        .andExpect(jsonPath("$[0].expiryInDays", is(wf1.getExpiryInDays())))
        .andExpect(jsonPath("$[0].expiryAction", is(wf1.getExpiryAction())))
        .andExpect(jsonPath("$[0].closeInDays", is(wf1.getCloseInDays())))
        .andExpect(jsonPath("$[0].wfReconfig", is(wf1.getWfReconfig())))
        .andExpect(jsonPath("$[0].branchEnabled", is(wf1.getBranchEnabled())))
        .andExpect(jsonPath("$[0].version", is(wf1.getVersion())))
        .andExpect(jsonPath("$[0].createdBy", is(wf1.getCreatedBy())))
        .andExpect(jsonPath("$[0].createdDate[0]", is(wf1.getCreatedDate().getYear())))
        .andExpect(jsonPath("$[0].createdDate[1]", is(wf1.getCreatedDate().getMonthValue())))
        .andExpect(jsonPath("$[0].createdDate[2]", is(wf1.getCreatedDate().getDayOfMonth())))
        .andExpect(jsonPath("$[0].updatedBy", is(wf1.getUpdatedBy())))
        .andExpect(jsonPath("$[0].updatedDate[0]", is(wf1.getUpdatedDate().getYear())))
        .andExpect(jsonPath("$[0].updatedDate[1]", is(wf1.getUpdatedDate().getMonthValue())))
        .andExpect(jsonPath("$[0].updatedDate[2]", is(wf1.getUpdatedDate().getDayOfMonth())))
        .andExpect(
            jsonPath("$[0].subtypeList[0].label", is(wf1.getSubtypeList().get(0).getLabel())))
        .andExpect(jsonPath("$[0].subtypeList[0].fieldValue",
            is(wf1.getSubtypeList().get(0).getFieldValue())))
        .andExpect(
            jsonPath("$[0].subtypeList[1].label", is(wf1.getSubtypeList().get(1).getLabel())))
        .andExpect(jsonPath("$[0].subtypeList[1].fieldValue",
            is(wf1.getSubtypeList().get(1).getFieldValue())));

    verify(wfService, times(1)).getWorkflowList(filter);
  }

  @Test
  public void getWorkflowCodeList() throws Exception {
    WorkflowCodeDTO wfc1 = WorkflowData.prepareWorkflowCodeDTO();
    WorkflowCodeDTO wfc2 = WorkflowData.prepareWorkflowCodeDTO();
    wfc2.setUid(Long.parseLong("2"));
    wfc2.setWfCode("WF_IPG");

    List<WorkflowCodeDTO> wfcList = Arrays.asList(wfc1, wfc2);

    when(wfService.getWorkflowCodeList()).thenReturn(wfcList);

    mockMvc.perform(get(WorkflowData.uri + "/codes"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(2)))
        .andExpect(jsonPath("$[0].uid", is(wfc1.getUid().intValue())))
        .andExpect(jsonPath("$[0].wfCode", is(wfc1.getWfCode())))
        .andExpect(jsonPath("$[1].uid", is(wfc2.getUid().intValue())))
        .andExpect(jsonPath("$[1].wfCode", is(wfc2.getWfCode())));

    verify(wfService, times(1)).getWorkflowCodeList();
  }

  @Test
  public void getWorkflow() throws Exception {
    WorkflowDTO wfDTO = WorkflowData.prepareWorkflowDTO();

    when(wfService.getWorkflow(org.mockito.ArgumentMatchers.anyLong())).thenReturn(wfDTO);

    mockMvc.perform(get(WorkflowData.uri + "/1"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$.uid", is(wfDTO.getUid().intValue())))
        .andExpect(jsonPath("$.wfCode", is(wfDTO.getWfCode())))
        .andExpect(jsonPath("$.expiryInDays", is(wfDTO.getExpiryInDays())))
        .andExpect(jsonPath("$.expiryAction", is(wfDTO.getExpiryAction())))
        .andExpect(jsonPath("$.closeInDays", is(wfDTO.getCloseInDays())))
        .andExpect(jsonPath("$.wfReconfig", is(wfDTO.getWfReconfig())))
        .andExpect(jsonPath("$.branchEnabled", is(wfDTO.getBranchEnabled())))
        .andExpect(jsonPath("$.version", is(wfDTO.getVersion())))
        .andExpect(jsonPath("$.createdBy", is(wfDTO.getCreatedBy())))
        .andExpect(jsonPath("$.createdDate[0]", is(wfDTO.getCreatedDate().getYear())))
        .andExpect(jsonPath("$.createdDate[1]", is(wfDTO.getCreatedDate().getMonthValue())))
        .andExpect(jsonPath("$.createdDate[2]", is(wfDTO.getCreatedDate().getDayOfMonth())))
        .andExpect(jsonPath("$.updatedBy", is(wfDTO.getUpdatedBy())))
        .andExpect(jsonPath("$.updatedDate[0]", is(wfDTO.getUpdatedDate().getYear())))
        .andExpect(jsonPath("$.updatedDate[1]", is(wfDTO.getUpdatedDate().getMonthValue())))
        .andExpect(jsonPath("$.updatedDate[2]", is(wfDTO.getUpdatedDate().getDayOfMonth())))
        .andExpect(jsonPath("$.subtypeList[0].label", is(wfDTO.getSubtypeList().get(0).getLabel())))
        .andExpect(jsonPath("$.subtypeList[0].fieldValue",
            is(wfDTO.getSubtypeList().get(0).getFieldValue())))
        .andExpect(jsonPath("$.subtypeList[1].label", is(wfDTO.getSubtypeList().get(1).getLabel())))
        .andExpect(jsonPath("$.subtypeList[1].fieldValue",
            is(wfDTO.getSubtypeList().get(1).getFieldValue())));

    verify(wfService, times(1)).getWorkflow(WorkflowData.workflowUid);

  }

  @Test
  public void getWorkflowRecordNotFound() throws Exception {

    when(wfService.getWorkflow(org.mockito.ArgumentMatchers.anyLong()))
        .thenThrow(new ServiceException(ResponseCode.RecordNotFound.toString(),
            "The Workflow 1 cannot be found."));

    mockMvc.perform(get(WorkflowData.uri + "/1"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", is(ResponseCode.RecordNotFound.toString())))
        .andExpect(jsonPath("$.message", is("The Workflow 1 cannot be found.")));

    verify(wfService, times(1)).getWorkflow(WorkflowData.workflowUid);

  }


  @Test
  public void createWorkflowInvalidInput() throws Exception {
    WorkflowDTO wfDTO = WorkflowData.prepareWorkflowDTO();
    wfDTO.setWfCode("");
    wfDTO.setWfName("");
    wfDTO.setCloseInDays(-10);
    wfDTO.setExpiryAction("RTN");
    wfDTO.setExpiryInDays(-20);
    wfDTO.setSubtypeList(null);
    wfDTO.setCreatedDate(null);
    wfDTO.setUpdatedDate(null);

    mockMvc
        .perform(post(WorkflowData.uri + "/?userId=" + WorkflowData.currentUserId)
            .content(om.writeValueAsString(wfDTO))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$.code", is(ResponseCode.BadArgument.toString())))
        .andExpect(jsonPath("$.details[?(@.target == 'wfCode')].message",
            equalTo(Arrays.asList("The Workflow Code cannot be empty."))))
        .andExpect(jsonPath("$.details[?(@.target == 'wfName')].message",
            equalTo(Arrays.asList("The Workflow Name cannot be empty."))))
        .andExpect(jsonPath("$.details[?(@.target == 'expiryInDays')].message",
            equalTo(Arrays.asList("The Expiry In Days must be a positive number or zero."))))
        .andExpect(jsonPath("$.details[?(@.target == 'expiryAction')].message",
            equalTo(
                Arrays.asList("The Expiry Action value should be either RETURN or HIGHLIGHT."))))
        .andExpect(jsonPath("$.details[?(@.target == 'subtypeList')].message",
            equalTo(Arrays.asList("The Subtype list cannot be empty."))));
  }

  @Test
  public void createWorkflowOk() throws Exception {

    WorkflowDTO wfDTO = WorkflowData.prepareWorkflowDTO();
    wfDTO.setCreatedDate(null);
    wfDTO.setUpdatedDate(null);

    when(wfService.createWorkflow(org.mockito.ArgumentMatchers.anyString(),
        org.mockito.ArgumentMatchers.any(WorkflowDTO.class))).thenReturn(wfDTO.getUid());

    mockMvc
        .perform(post(WorkflowData.uri + "/?userId=" + WorkflowData.currentUserId)
            .content(om.writeValueAsString(wfDTO))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$", is(wfDTO.getUid().intValue())));

    verify(wfService, times(1)).createWorkflow(org.mockito.ArgumentMatchers.anyString(),
        org.mockito.ArgumentMatchers.any(WorkflowDTO.class));
  }

  @Test
  public void updateWorkflowOk() throws Exception {
    WorkflowDTO wfDTO = WorkflowData.prepareWorkflowDTO();
    wfDTO.setCreatedDate(null);
    wfDTO.setUpdatedDate(null);

    when(wfService.updateWorkflow(org.mockito.ArgumentMatchers.anyString(),
        org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.any(WorkflowDTO.class))).thenReturn(true);

    mockMvc
        .perform(put(WorkflowData.uri + "/1?userId=" + WorkflowData.currentUserId)
            .content(om.writeValueAsString(wfDTO))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent()).andDo(MockMvcResultHandlers.print());

    verify(wfService, times(1)).updateWorkflow(org.mockito.ArgumentMatchers.anyString(),
        org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.any(WorkflowDTO.class));
  }

  @Test
  public void updateWorkflowInvalidInput() throws Exception {
    WorkflowDTO wfDTO = WorkflowData.prepareWorkflowDTO();
    wfDTO.setWfCode("");
    wfDTO.setWfName("");
    wfDTO.setCloseInDays(-10);
    wfDTO.setExpiryAction("RTN");
    wfDTO.setExpiryInDays(-20);
    wfDTO.setSubtypeList(null);
    wfDTO.setCreatedDate(null);
    wfDTO.setUpdatedDate(null);

    mockMvc
        .perform(put(WorkflowData.uri + "/1?userId=" + WorkflowData.currentUserId)
            .content(om.writeValueAsString(wfDTO))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$.code", is(ResponseCode.BadArgument.toString())))
        .andExpect(jsonPath("$.details[?(@.target == 'wfCode')].message",
            equalTo(Arrays.asList("The Workflow Code cannot be empty."))))
        .andExpect(jsonPath("$.details[?(@.target == 'wfName')].message",
            equalTo(Arrays.asList("The Workflow Name cannot be empty."))))
        .andExpect(jsonPath("$.details[?(@.target == 'expiryInDays')].message",
            equalTo(Arrays.asList("The Expiry In Days must be a positive number or zero."))))
        .andExpect(jsonPath("$.details[?(@.target == 'expiryAction')].message",
            equalTo(
                Arrays.asList("The Expiry Action value should be either RETURN or HIGHLIGHT."))))
        .andExpect(jsonPath("$.details[?(@.target == 'subtypeList')].message",
            equalTo(Arrays.asList("The Subtype list cannot be empty."))));
  }

  @Test
  public void deleteWorkflowRecordNotFound() throws Exception {
    when(wfService.deleteWorkflow(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString()))
            .thenThrow(new ServiceException(ResponseCode.RecordNotFound.toString(),
                "The Workflow 1 cannot be found."));

    mockMvc.perform(delete(WorkflowData.uri + "/1?userId=" + WorkflowData.currentUserId))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", is(ResponseCode.RecordNotFound.toString())))
        .andExpect(jsonPath("$.message", is("The Workflow 1 cannot be found.")));

    verify(wfService, times(1)).deleteWorkflow(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
  }

  @Test
  public void deleteWorkflowOk() throws Exception {
    when(wfService.deleteWorkflow(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString())).thenReturn(true);

    mockMvc.perform(delete(WorkflowData.uri + "/1?userId=" + WorkflowData.currentUserId))
        .andExpect(status().isNoContent());

    verify(wfService, times(1)).deleteWorkflow(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
  }
}


