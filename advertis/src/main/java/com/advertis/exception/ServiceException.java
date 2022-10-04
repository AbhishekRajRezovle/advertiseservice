package com.advertis.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorCode;
	private HttpStatus httpStatus = HttpStatus.OK;
	private Exception ex;
}
