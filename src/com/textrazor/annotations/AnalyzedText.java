package com.textrazor.annotations;

public class AnalyzedText {

	private double time;
	
	private boolean ok;
	
	private String error;
	
	private String message;
	
	private Response response;

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
	
	/**
	 * @return The {@link Response} containing extracted TextRazor metadata.
	 */
	public Response getResponse() {
		return response;
	}
	
	public void createAnnotationLinks() {
		response.createAnnotationLinks();
	}
}
