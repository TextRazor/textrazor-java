package com.textrazor.dictionary.model;

import java.util.ArrayList;
import java.util.List;

import com.textrazor.net.TextRazorResponse;

public class AllDictionariesResponse extends TextRazorResponse {
	private List<Dictionary> dictionaries = new ArrayList<Dictionary>();

	public List<Dictionary> getDictionaries() {
		return dictionaries;
	}	
}
