package com.textrazor.dictionary.model;

public class Dictionary {
	public static class Builder {
		private String id;
		private String matchType = "token";
		private Boolean caseInsensitive = false;
		private String language = "any";
		
		public Builder setId(String id) {
			this.id = id;
			return this;
		}
		
		public Builder setMatchType(String matchType) {
			this.matchType = matchType;
			return this;
		}
		
		public Builder setCaseInsensitive(Boolean caseInsensitive) {
			this.caseInsensitive = caseInsensitive;
			return this;
		}
		
		public Builder setLanguage(String language) {
			this.language = language;
			return this;
		}
		
		public Dictionary build() {
			return new Dictionary(id, matchType, caseInsensitive, language);
		}	
	}
	
	private String id;
	private String matchType;
	private Boolean caseInsensitive;
	private String language;
	
	public Dictionary() {}
	
	public Dictionary(String id, String matchType, Boolean caseInsensitive, String language) {
		this.id = id;
		this.matchType = matchType;
		this.caseInsensitive = caseInsensitive;
		this.language = language;
	}

	public static Builder builder() {
        return new Builder();
    }
	
	public String getId() {
		return id;
	}
	
	public String getMatchType() {
		return matchType;
	}
	
	public Boolean getCaseInsensitive() {
		return caseInsensitive;
	}
	
	public String getLanguage() {
		return language;
	}
	
	
}
