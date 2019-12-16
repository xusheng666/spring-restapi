package com.smbc.sg.epix.workflow.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/**
 * Error response for all REST API call.
 * 
 * @author ITQS
 * @date Dec 16, 2019
 * @version 1.0
 */
public class Error {
	
	@Getter @Setter private String code;
	@Getter @Setter private String message;
	@Getter @Setter private String target;
	@Getter @Setter private List<Error> details;
	@Getter @Setter private InnerError innerError;
	
	public Error(String code) {
		this.code = code;
	}
	
	public Error(String code, String message) {
		this(code);
		this.message = message;
	}

	public Error(String code, String message, String target) {
		this(code, message);
		this.target = target;
	}
	
	public void addDetails(Error error) {
		if (Objects.isNull(details)) 
			details = new ArrayList<Error>();
		
		if (Objects.nonNull(error)) 
			details.add(error);
	}
}
