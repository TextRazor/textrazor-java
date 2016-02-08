package com.textrazor.annotations;

public class ScoredCategory extends Annotation {
	
	private String categoryId;
	
	private String label;
	
	private String classifierId;
	
	private double score;

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
	 * @return The unique identifier for the classifier that matched this category.
	 */
	public String getClassifierId() {
		return classifierId;
	}

	/**
	 * @return The score TextRazor has assigned to this category, between 0 and 1. A score closer to 1 indicates that this is a highly relevant category for our document.  
	 * 
	 * To avoid false positives you might want to ignore categories below a certain score - a good starting point would be 0.5.
	 * The best way to find an appropriate threshold is to run a sample set of your documents through the system and manually inspect the results.
	 */
	public double getScore() {
		return score;
	}
}
