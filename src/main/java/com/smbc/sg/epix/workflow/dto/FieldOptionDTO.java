package com.smbc.sg.epix.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FieldOptionDTO {
  @Getter
  @Setter
  private Long uid;
  @Getter
  @Setter
  private String label;
  @Getter
  @Setter
  private String fieldValue;
}
