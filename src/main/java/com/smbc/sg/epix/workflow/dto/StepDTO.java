package com.smbc.sg.epix.workflow.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class StepDTO {
  @Getter
  @Setter
  private Long uid;
  @Getter
  @Setter
  private Long wfUid;
  @Getter
  @Setter
  private String currentOwner;
  @Getter
  @Setter
  private String ownerGroup;
  @Getter
  @Setter
  private String stepName;
  @Getter
  @Setter
  private Integer timeLimitDays;
  @Getter
  @Setter
  private Integer stepSeq;
}
