package com.advertis.dto;

import org.slf4j.MDC;

import com.advertis.constant.ApplicationConstants;

public class ErrorResponseDto<T> implements ResponseDto<T> {
	private Integer code = ApplicationConstants.HTTP_RESPONSE_ERROR_CODE;
	private T data;
	private String message;
	private Integer status = ApplicationConstants.ERROR_STATUS_CODE;
	private long timestamp;
	protected String requestId;

	public ErrorResponseDto() {
	}

	public ErrorResponseDto(Integer errorCode, String errorMessage) {
		this.code = errorCode;
		this.message = errorMessage;
		this.timestamp = System.currentTimeMillis();
		this.requestId = MDC.get(ApplicationConstants.REQUEST_ID);
	}

	public ErrorResponseDto(Integer errorCode, String errorMessage, T data) {
		this.data = data;
		this.code = errorCode;
		this.message = errorMessage;
		this.timestamp = System.currentTimeMillis();
		this.requestId = MDC.get(ApplicationConstants.REQUEST_ID);
	}

	@Override
	public Integer getCode() {
		return code;
	}

	@Override
	public T getData() {
		return data;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Integer getStatus() {
		return status;
	}

	@Override
	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public void setData(T data) {
		this.data = data;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public void setTimeStamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getTimestamp() {
		return timestamp;
	}
}
