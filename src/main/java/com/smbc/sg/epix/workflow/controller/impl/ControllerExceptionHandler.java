package com.smbc.sg.epix.workflow.controller.impl;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.smbc.sg.epix.workflow.service.impl.ServiceException;

/**
 * Global Exception Handler
 * 
 * @author ITQS
 * @date Dec 16, 2019
 * @version 1.0
 */
@ControllerAdvice(annotations = RestController.class)
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler{
	
	/**
	 * Handle general exception
	 * 
	 * @param ex
	 * @param webRequest
	 * @return
	 */
	@ExceptionHandler({Exception.class, RuntimeException.class})
	public ResponseEntity<Error> handleSystemException(Exception e, WebRequest webRequest) {
		Error error = new Error("", e.getMessage());
		return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * Handle input agrument validation exception.
	 * 
	 * @param cve
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Error> handleValidationException(ConstraintViolationException cve) {
		Error error = new Error(ResponseCode.BadArgument.toString());
		
		ResponseEntity<Error> responseEntity = new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
		
		for (ConstraintViolation<?> s:cve.getConstraintViolations()) {
			String fieldName = ((PathImpl)s.getPropertyPath()).getLeafNode().getName();
			
			error.addDetails(new Error("","",fieldName));
		}
		
		return responseEntity;
	}
	
	/**
	 * Handle business exception
	 * 
	 * @param se
	 * @param webRequest
	 * @return
	 */
	@ExceptionHandler({ServiceException.class})
	public ResponseEntity<Error> handleServiceException(ServiceException se, WebRequest webRequest) {
		Error error = new Error(se.getCode(), se.getMessage());
		return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
