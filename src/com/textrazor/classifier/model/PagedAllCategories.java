package com.textrazor.classifier.model;

import java.util.List;

public class PagedAllCategories {
	
	String id;
	
	int total;
	
	int limit;
	
	int offset;
	
	List<Category> categories;

	public String getClassifierId() {
		return id;
	}
	
	public int getTotal() {
		return total;
	}

	public int getLimit() {
		return limit;
	}

	public int getOffset() {
		return offset;
	}

	public List<Category> getCategories() {
		return categories;
	}
	
}
