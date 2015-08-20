package com.textrazor.net;

/**
 * Base class for all responses from the TextRazor API.
 */
public class TextRazorResponse {
	private double time;
	
	private boolean ok;
	
	private String error;
	
	private String message;
	
	/** 
	 * @return Total time in seconds TextRazor took to process this request. This does not include any time spent sending or receiving the request/response.
	 */
	public double getTime() {
		return time;
	}

	/**
	 * @return True if TextRazor successfully analyzed your document, False if there was some error.
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @return Descriptive error message of any problems that may have occurred during analysis, or null if there was no error.
	 */
	public String getError() {
		return error;
	}

	/**
	 * @return Any warning or informational messages returned from the server, or an null if there was no message.
	 */
	public String getMessage() {
		return message;
	}
}
