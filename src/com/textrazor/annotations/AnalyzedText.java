package com.textrazor.annotations;

import com.textrazor.net.TextRazorResponse;

public class AnalyzedText extends TextRazorResponse {
	
	private Response response;
	
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
