package com.textrazor.dictionary.model;

import com.textrazor.net.TextRazorResponse;

public class GetDictionaryResponse extends TextRazorResponse  {
	private Dictionary dictionary;

	public Dictionary getDictionary() {
		return dictionary;
	}
}
