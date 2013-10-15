package com.textrazor;

public class AnalysisException extends Exception {
	private static final long serialVersionUID = 9063173251339905559L;
	
	private int responseCode;
    private String responseBody;

    public AnalysisException(int responseCode, String responseBody) {
        this.responseBody = responseBody;
        this.responseCode = responseCode;
    }

    @Override
    public String getMessage() {
        return String.format("TextRazor returned HTTP Code %d with response: %s", responseCode, responseBody);
    }

    public String getResponseBody() {
        return responseBody;
    }
    
    public int getResponseCode() {
        return responseCode;
    }
}