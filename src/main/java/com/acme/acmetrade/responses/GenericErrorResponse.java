package com.acme.acmetrade.responses;

public class GenericErrorResponse {
    private String errorCode;
    private String errorMessage;
 
    public GenericErrorResponse() {
    }
 
    public String getErrorCode() {
        return errorCode;
    }
 
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
 
    public String getErrorMessage() {
        return errorMessage;
    }
 
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
	

}