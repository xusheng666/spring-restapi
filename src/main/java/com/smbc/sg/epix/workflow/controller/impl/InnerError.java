package com.smbc.sg.epix.workflow.controller.impl;

import lombok.Getter;
import lombok.Setter;

/**
 * An object containing more specific information than the Error object.
 * 
 * @author ITQS
 * @date Dec 16, 2019
 * @version 1.0
 */
public class InnerError {
  @Getter
  @Setter
  private String code;
  @Getter
  @Setter
  private InnerError innerError;
}
