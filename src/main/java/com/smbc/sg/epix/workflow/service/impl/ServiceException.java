package com.smbc.sg.epix.workflow.service.impl;

import lombok.Getter;
import lombok.Setter;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter private String code;
	
	public ServiceException(String code) {
		this.code = code;
	}
	
	public ServiceException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public ServiceException(String code, Throwable cause) {
		super(cause);
		this.code = code;
	}
	
	public ServiceException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
}
