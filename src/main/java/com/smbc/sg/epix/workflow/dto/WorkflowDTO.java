package com.smbc.sg.epix.workflow.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class WorkflowDTO {
  @Getter
  @Setter
  private Long uid;

  @NotEmpty(message = "The Workflow Code cannot be empty.")
  @Getter
  @Setter
  private String wfCode;

  @NotEmpty(message = "The Workflow Name cannot be empty.")
  @Getter
  @Setter
  private String wfName;

  @PositiveOrZero(message = "The Expiry In Days must be a positive number or zero.")
  @Getter
  @Setter
  private Integer expiryInDays;

  @Pattern(regexp = "^(RETURN|HIGHLIGHT)$",
      message = "The Expiry Action value should be either RETURN or HIGHLIGHT.")
  @Getter
  @Setter
  private String expiryAction;

  @PositiveOrZero(message = "The Close In Days must be a positive number or zero.")
  @Getter
  @Setter
  private Integer closeInDays;

  @NotNull(message = "The Workflow Reconfig value must be true or false")
  @Getter
  @Setter
  private Boolean wfReconfig;

  @NotNull(message = "The Branch Enabled value must be true or false")
  @Getter
  @Setter
  private Boolean branchEnabled;

  @Getter
  @Setter
  private Integer version;

  @Getter
  @Setter
  private String createdBy;

  @Getter
  @Setter
  private LocalDateTime createdDate;

  @Getter
  @Setter
  private String updatedBy;

  @Getter
  @Setter
  private LocalDateTime updatedDate;

  @NotNull(message = "The Subtype list cannot be empty.")
  @Getter
  @Setter
  private List<SubtypeDTO> subtypeList;

  @Getter
  @Setter
  private List<FieldDTO> fieldList;

  @Getter
  @Setter
  private List<StepDTO> stepList;
}
