package com.textrazor.annotations;

public class Topic extends Annotation {
	
	private int id;
	
	private String label;
	
	private String wikiLink;
	
	private String wikidataId;
	
	private double score;

	/**
	 * @return The ID of this annotation
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return The textual label for this topic.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return Link to the Wikipedia page for this Topic, empty if there is no relevant link for this topic.
	 */
	public String getWikiLink() {
		return wikiLink;
	}
	
	/**
	 * @return The disambiguated Wikidata QID for this topic, or None if either this topic could not be disambiguated, or a Wikidata link doesn’t exist.
	 */
	public String getWikidataId() {
		return wikidataId;
	}

	/**
	 * @return The relevance of this topic to the processed document. This score ranges from 0 to 1, with 1 representing the highest relevance of the topic to the processed document.
	 */
	public double getScore() {
		return score;
	}

}
