package com.textrazor.dictionary.model;

import java.util.List;

public class PagedAllEntries {
	
	int total;
	
	int limit;
	
	int offset;
	
	List<DictionaryEntry> entries;

	public int getTotal() {
		return total;
	}

	public int getLimit() {
		return limit;
	}

	public int getOffset() {
		return offset;
	}

	public List<DictionaryEntry> getEntries() {
		return entries;
	}
	
}
