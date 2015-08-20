package com.textrazor.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class QueryBuilder {

	private StringBuilder path = new StringBuilder();
	
	private boolean firstComponent = true;
	
	public QueryBuilder addParam(String name, String value) throws UnsupportedEncodingException {
		if (null == name || null == value || name.isEmpty() || value.isEmpty()) {
			return this;
		}
		
		if (firstComponent) {
			firstComponent = false;
		}
		else {
			path.append("&");
		}
		
		path.append(URLEncoder.encode(name, "UTF-8"));
		path.append("=");
		path.append(URLEncoder.encode(value, "UTF-8"));
		
		return this;
	}
	
	public QueryBuilder addParam(String name, boolean value) throws UnsupportedEncodingException {
		return addParam(name, value ? "true" : "false");
	}
	
	public QueryBuilder addParam(String name, List<String> values) throws UnsupportedEncodingException {
		if (null != values) {
			for (String value : values) {
				addParam(name, value);
			}
		}
		
		return this;
	}
	
	public String build() {
		return path.toString();
	}
	
}
