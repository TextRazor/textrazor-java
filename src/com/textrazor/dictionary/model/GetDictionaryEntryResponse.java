package com.textrazor.dictionary.model;

import com.textrazor.net.TextRazorResponse;

public class GetDictionaryEntryResponse extends TextRazorResponse {

	private DictionaryEntry response;

	public DictionaryEntry getResponse() {
		return response;
	}
}
