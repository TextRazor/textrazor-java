package com.textrazor.annotations;

public class AnalyzedText {

	private double time;
	
	private Response response;

	/** 
	 * @return Total time in seconds TextRazor took to process this request. This does not include any time spent sending or receiving the request/response.
	 */
	public double getTime() {
		return time;
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
