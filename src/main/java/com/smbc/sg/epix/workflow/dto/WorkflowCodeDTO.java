package com.smbc.sg.epix.workflow.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class WorkflowCodeDTO {
  @Getter
  @Setter
  private Long uid;
  @Getter
  @Setter
  private String wfCode;
}
