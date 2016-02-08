package com.textrazor.classifier.model;

import com.textrazor.dictionary.model.DictionaryEntry.Builder;

public class Category {
	
	public static class Builder {
		private String categoryId;
		
		private String label;
		
		private String query;

		public Builder setCategoryId(String categoryId) {
			this.categoryId = categoryId;
			return this;
		}

		public Builder setLabel(String label) {
			this.label = label;
			return this;
		}

		public Builder setQuery(String query) {
			this.query = query;
			return this;
		}
		
		public Category build() {
			return new Category(categoryId, label, query);
		}	
	}
	
	private String categoryId;
	
	private String label;
	
	private String query;

	public Category() {}
	
	private Category(String categoryId, String label, String query) {
		this.categoryId = categoryId;
		this.label = label;
		this.query = query;
	}
	
	public static Builder builder() {
        return new Builder();
    }
	
	/**
	 * @return The unique ID for this category within its classifier
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @return The human readable label for this category.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return The query used to define this category.
	 */
	public String getQuery() {
		return query;
	}
}
