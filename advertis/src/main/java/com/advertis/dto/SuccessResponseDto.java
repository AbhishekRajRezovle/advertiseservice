package com.advertis.dto;

import org.slf4j.MDC;

import com.advertis.constant.ApplicationConstants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponseDto<T> implements ResponseDto<T> {

	protected Integer code = ApplicationConstants.HTTP_RESPONSE_SUCCESS_CODE;
	protected T data;
	protected String message = null;
	protected Integer status = ApplicationConstants.SUCCESS_STATUS_CODE;
	protected long timestamp;
	protected String requestId;

	public SuccessResponseDto() {
		this(null, ApplicationConstants.SUCCESS);
	}

	public SuccessResponseDto(T data) {
		this(data, ApplicationConstants.SUCCESS);
	}

	public SuccessResponseDto(String message) {
		this.message = message;
	}

	public SuccessResponseDto(T data2, String message2) {
		this.data = data2;
		this.message = message2;
		this.timestamp = System.currentTimeMillis();
		this.requestId = MDC.get(ApplicationConstants.REQUEST_ID);
	}

	public SuccessResponseDto(T data2, String message2, Integer status) {
		this(data2, message2);
		this.status = status;
	}

	public SuccessResponseDto(Integer status) {
		this.status = status;
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

}
