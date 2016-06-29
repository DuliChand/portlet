package com.app.dto;

public abstract class BaseWrapper {

	public static final String RESPONSE_FAILURE = "FAILURE";
	public static final String RESPONSE_SUCCESS = "SUCCESS";
	public static final String RESPONSE_PARTIAL_FAILURE = "PARTIAL_FAILURE";

	private String responseCode;

	private String message;

	private String errorCode;

	private String errorMessage;

	public String getResponseCode() {
		return responseCode;
	}

	public String getMessage() {
		return message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}