package com.smbc.sg.epix.workflow.common;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smbc.sg.epix.workflow.dto.FieldDTO;
import com.smbc.sg.epix.workflow.dto.FieldOptionDTO;
import com.smbc.sg.epix.workflow.dto.StepDTO;
import com.smbc.sg.epix.workflow.dto.SubtypeDTO;
import com.smbc.sg.epix.workflow.dto.WorkflowCodeDTO;
import com.smbc.sg.epix.workflow.dto.WorkflowDTO;
import com.smbc.sg.epix.workflow.entity.Workflow;

public class WorkflowData {
  public static Workflow prepareWorkflow() {
    Workflow wf = new Workflow();
    wf.setUid(workflowUid);
    wf.setBranchEnabled(false);
    wf.setCloseInDays(11);
    wf.setCreatedBy("SYS");
    wf.setCreatedDate(LocalDateTime.now());
    wf.setDeleteFlag(false);
    wf.setExpiryAction("RETURN");
    wf.setExpiryInDays(30);
    wf.setUpdatedBy("SYS");
    wf.setUpdatedDate(LocalDateTime.now());
    wf.setVersion(1);
    wf.setWfCode("WF_ISG");
    wf.setWfName("Workflow for ISG");
    wf.setWfReconfig(false);

    return wf;
  }

  public static WorkflowDTO prepareWorkflowDTO() {
    WorkflowDTO wfDTO = new WorkflowDTO();
    wfDTO.setUid(workflowUid);
    wfDTO.setBranchEnabled(false);
    wfDTO.setCloseInDays(11);
    wfDTO.setCreatedBy("SYS");
    wfDTO.setCreatedDate(LocalDateTime.now());
    wfDTO.setExpiryAction("RETURN");
    wfDTO.setExpiryInDays(30);
    wfDTO.setUpdatedBy("SYS");
    wfDTO.setUpdatedDate(LocalDateTime.now());
    wfDTO.setVersion(1);
    wfDTO.setWfCode("WF_ISG");
    wfDTO.setWfName("Workflow for ISG");
    wfDTO.setWfReconfig(false);

    wfDTO.setSubtypeList(prepareSubtypeDTO());

    wfDTO.setStepList(prepareStepDTO());

    wfDTO.setFieldList(prepareFieldDTO());

    return wfDTO;
  }

  public static List<SubtypeDTO> prepareSubtypeDTO() {
    List<SubtypeDTO> subtypeList = new ArrayList<SubtypeDTO>();
    subtypeList.add(new SubtypeDTO("Subtype 1", "ST1"));
    subtypeList.add(new SubtypeDTO("Subtype 2", "ST2"));

    return subtypeList;
  }

  public static List<StepDTO> prepareStepDTO() {


    StepDTO stepDTO1 = new StepDTO();
    stepDTO1.setUid(Long.parseLong("1"));
    stepDTO1.setCurrentOwner("User1");
    stepDTO1.setOwnerGroup("ISG");
    stepDTO1.setStepName("Step 1");
    stepDTO1.setStepSeq(1);
    stepDTO1.setTimeLimitDays(10);
    stepDTO1.setWfUid(workflowUid);

    StepDTO stepDTO2 = new StepDTO();
    stepDTO2.setUid(Long.parseLong("2"));
    stepDTO2.setCurrentOwner("User1");
    stepDTO2.setOwnerGroup("ISG");
    stepDTO2.setStepName("Step 2");
    stepDTO2.setStepSeq(2);
    stepDTO2.setTimeLimitDays(10);
    stepDTO2.setWfUid(workflowUid);

    return Arrays.asList(stepDTO1, stepDTO2);
  }

  public static List<FieldDTO> prepareFieldDTO() {
    FieldDTO fieldDTO1 = new FieldDTO();
    fieldDTO1.setUid(Long.parseLong("1"));
    fieldDTO1.setWfUid(workflowUid);
    fieldDTO1.setFieldLabel("Field 1");
    fieldDTO1.setFieldName("field1");
    fieldDTO1.setFieldSeq(1);
    fieldDTO1.setFieldType("Input");
    fieldDTO1.setMandatory(true);

    FieldDTO fieldDTO2 = new FieldDTO();
    fieldDTO2.setUid(Long.parseLong("2"));
    fieldDTO2.setWfUid(workflowUid);
    fieldDTO2.setFieldLabel("Field 2");
    fieldDTO2.setFieldName("field2");
    fieldDTO2.setFieldSeq(2);
    fieldDTO2.setFieldType("Dropdown");
    fieldDTO2.setMandatory(true);

    FieldOptionDTO fieldOptionDTO1 = new FieldOptionDTO(Long.parseLong("1"), "Male", "MALE");
    FieldOptionDTO fieldOptionDTO2 = new FieldOptionDTO(Long.parseLong("1"), "Female", "FEMALE");

    fieldDTO2.setFieldOptionList(Arrays.asList(fieldOptionDTO1, fieldOptionDTO2));


    return Arrays.asList(fieldDTO1, fieldDTO2);
  }

  public static WorkflowCodeDTO prepareWorkflowCodeDTO() {
    WorkflowCodeDTO wfCodeDTO = new WorkflowCodeDTO();
    wfCodeDTO.setUid(workflowUid);
    wfCodeDTO.setWfCode("WF_ISG");

    return wfCodeDTO;
  }

  public static String currentUserId = "User1";
  public static Long workflowUid = Long.parseLong("1");
  public static String uri = "/v1.0/workflow";
}
