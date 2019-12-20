package com.smbc.sg.epix.workflow.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class FieldDTO {
  @Getter
  @Setter
  private Long uid;
  @Getter
  @Setter
  private Long wfUid;
  @Getter
  @Setter
  private String fieldLabel;
  @Getter
  @Setter
  private String fieldType;
  @Getter
  @Setter
  private String fieldName;
  @Getter
  @Setter
  private Integer fieldSeq;
  @Getter
  @Setter
  private Boolean mandatory;
  @Getter
  @Setter
  private List<FieldOptionDTO> fieldOptionList;
}
