package com.smbc.sg.epix.workflow.controller.impl;

import java.util.Objects;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  /**
   * 
   * This method handle the ConstraintViolationException which cased by input validation failed for
   * path variable or request parameter.
   * 
   * @param cve
   * @return
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Error> handleValidationException(ConstraintViolationException cve) {
    Error error = new Error(ResponseCode.BadArgument.toString());
    logger.debug("++============ Come to handleValidationException");
    ResponseEntity<Error> responseEntity = new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);

    for (ConstraintViolation<?> s : cve.getConstraintViolations()) {
      String fieldName = ((PathImpl) s.getPropertyPath()).getLeafNode().getName();

      error.addDetails(new Error("", s.getMessage(), fieldName));
    }

    return responseEntity;
  }

  /**
   * 
   * This method handle the MethodArgumentNotValidException which cased by input validation failed
   * for request body
   * 
   * @param cve
   * @return
   */
  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    Error error = new Error(ResponseCode.BadArgument.toString());

    if (Objects.nonNull(ex) && Objects.nonNull(ex.getBindingResult())) {
      ex.getBindingResult().getAllErrors().forEach(objError -> {
        error.addDetails(new Error("", objError.getDefaultMessage(), getFieldName(objError)));
      });
    }

    return new ResponseEntity<>(error, headers, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle service level exception
   * 
   * @param se
   * @param webRequest
   * @return
   */
  @ExceptionHandler({ServiceException.class})
  public ResponseEntity<Error> handleServiceException(ServiceException se, WebRequest webRequest) {
    logger.debug("+++==handleServiceException");
    Error error = new Error(se.getCode(), se.getMessage());
    return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle general exception
   * 
   * @param ex
   * @param webRequest
   * @return
   */
  @ExceptionHandler({Exception.class, RuntimeException.class})
  public ResponseEntity<Error> handleSystemException(Exception e, WebRequest webRequest) {
    e.printStackTrace();
    logger.debug("+++==System exception " + e.getStackTrace().toString());
    Error error = new Error("", e.getMessage());
    return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private String getFieldName(ObjectError objError) {
    String fieldName = "";

    if (Objects.nonNull(objError))
      fieldName = ((FieldError) objError).getField();

    return fieldName;
  }
}
