package com.acme.acmetrade.endpoints;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.acme.acmetrade.responses.GenericErrorResponse;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ IllegalArgumentException.class })
	protected ResponseEntity<GenericErrorResponse> handleConflict(RuntimeException ex, WebRequest request) {
		GenericErrorResponse response = new GenericErrorResponse();
		response.setErrorMessage(ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

}