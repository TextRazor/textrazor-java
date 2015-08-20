package com.textrazor.dictionary.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryEntry {

	public static class Builder {
		private String id;
		
		private String text;
		
		private Map<String, List<String>> data = new HashMap<String, List<String>>();

		public Builder setId(String id) {
			this.id = id;
			return this;
		}

		public Builder setText(String text) {
			this.text = text;
			return this;
		}

		public Builder addData(String key, List<String> value) {
			this.data.put(key, value);
			return this;
		}
		
		public DictionaryEntry build() {
			return new DictionaryEntry(id, text, data);
		}	
	}
	
	private String id;
	
	private String text;
	
	private Map<String, List<String>> data = new HashMap<String, List<String>>();

	public DictionaryEntry() {}
	
	private DictionaryEntry(String id, String text, Map<String, List<String>> data) {
		this.id = id;
		this.text = text;
		this.data = data;
	}
	
	public static Builder builder() {
        return new Builder();
    }
	
	public String getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public Map<String, List<String>> getData() {
		return data;
	}
}
