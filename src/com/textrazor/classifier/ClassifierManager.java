package com.textrazor.classifier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.textrazor.AnalysisException;
import com.textrazor.NetworkException;
import com.textrazor.classifier.model.AllCategoriesResponse;
import com.textrazor.classifier.model.Category;
import com.textrazor.classifier.model.GetCategoryResponse;
import com.textrazor.classifier.model.PagedAllCategories;
import com.textrazor.net.QueryBuilder;
import com.textrazor.net.TextRazorConnection;
import com.textrazor.net.TextRazorResponse;

public class ClassifierManager extends TextRazorConnection {
	public ClassifierManager(String apiKey) {
		super(apiKey);
	}

	public TextRazorResponse createClassifier(String classifierId, List<Category> categories) throws NetworkException, AnalysisException {
		String encodedId = encodeURLParam(classifierId);

		if (null == encodedId) {
			throw new RuntimeException("Invalid Classifier ID."); 
		}

		return sendRequest("categories/" + encodedId,
				categories, 
				ContentType.JSON,
				"PUT",
				TextRazorResponse.class);
	}
	
	public TextRazorResponse createClassifierWithCSV(String classifierId, String classifierFileName) throws IOException, NetworkException, AnalysisException {
		String encodedId = encodeURLParam(classifierId);

		if (null == encodedId) {
			throw new RuntimeException("Invalid Classifier ID."); 
		}
		
		String csvContents;
		
		BufferedReader br = new BufferedReader(new FileReader(classifierFileName));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append('\n');
		        line = br.readLine();
		    }
		    
		    csvContents = sb.toString();
		} finally {
		    br.close();
		}
	
		return sendRequest("categories/" + encodedId,
				csvContents, 
				ContentType.CSV,
				"PUT",
				TextRazorResponse.class);
	}

	public TextRazorResponse deleteClassifier(String classifierId) throws NetworkException, AnalysisException {
		String encodedId = encodeURLParam(classifierId);

		if (null == encodedId) {
			throw new RuntimeException("Invalid Classifier ID."); 
		}

		return sendRequest("categories/" + encodedId,
				null, 
				ContentType.JSON,
				"DELETE",
				TextRazorResponse.class);
	}

	public TextRazorResponse deleteCategory(String classifierId, String categoryId) throws NetworkException, AnalysisException {
		String encodedClassifierId = encodeURLParam(classifierId);
		if (null == encodedClassifierId) {
			throw new RuntimeException("Invalid Classifier ID."); 
		}

		String encodedCategoryId = encodeURLParam(categoryId);
		if (null == encodedCategoryId) {
			throw new RuntimeException("Invalid Category ID."); 
		}
		
		return sendRequest("categories/" + encodedClassifierId + "/" + encodedCategoryId,
				null, 
				ContentType.JSON,
				"DELETE",
				TextRazorResponse.class);
	}

	public Category getCategory(String classifierId, String categoryId) throws NetworkException, AnalysisException {	
		String encodedClassifierId = encodeURLParam(classifierId);
		if (null == encodedClassifierId) {
			throw new RuntimeException("Invalid Classifier ID."); 
		}

		String encodedCategoryId = encodeURLParam(categoryId);
		if (null == encodedCategoryId) {
			throw new RuntimeException("Invalid Category ID."); 
		}
		
		GetCategoryResponse response = sendRequest("categories/" + encodedClassifierId + "/" + encodedCategoryId,
				null, 
				ContentType.JSON,
				"GET",
				GetCategoryResponse.class);
		
		return response.getResponse();
	}
	
	public PagedAllCategories allCategories(String classifierId, int limit, int offset) throws NetworkException, AnalysisException {	
		String encodedId = encodeURLParam(classifierId);

		if (null == encodedId) {
			throw new RuntimeException("Invalid Category ID."); 
		}

		QueryBuilder paramBuilder = new QueryBuilder();

		try {
			if (limit > 0) {
				paramBuilder.addParam("limit", Integer.toString(limit));
			}

			if (offset > 0) {
				paramBuilder.addParam("offset", Integer.toString(offset));
			}
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unable to encode category paging paramaters."); 
		}

		AllCategoriesResponse response = sendRequest("categories/" + encodedId + "/_all?" + paramBuilder.build(),
				null, 
				ContentType.JSON,
				"GET",
				AllCategoriesResponse.class);
		
		return response.getResponse();
	}

	public PagedAllCategories allCategories(String classifierId, int limit) throws NetworkException, AnalysisException {	
		return allCategories(classifierId, limit, -1);
	}

	public PagedAllCategories allCategories(String classifierId) throws NetworkException, AnalysisException {	
		return allCategories(classifierId, -1);
	}
}
