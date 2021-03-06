package com.smbc.sg.epix.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubtypeDTO {
  @Getter
  @Setter
  private String label;
  @Getter
  @Setter
  private String fieldValue;
}
