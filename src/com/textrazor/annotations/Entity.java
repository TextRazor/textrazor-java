package com.textrazor.annotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity extends Annotation {
	
	private int id;
	
	private String entityId;

	private String entityEnglishId;
	
	private String freebaseId;
	
	private String customEntityId;
	
	private String wikiLink;
	
	private String matchedText;
	
	private int startingPos;
	
	private int endingPos;
	
	private List<Integer> matchingTokens;
	
	private List<Word> matchingWords = new ArrayList<Word>();
	
	private List<String> freebaseTypes;
	
	private List<String> type;
	
	private double relevanceScore;
	
	private double confidenceScore;
	
	private Map<String, List<String>> data = new HashMap<String, List<String>>();

	/**
	 * @return List of the {@link Word} objects that make up this entity.
	 */
	public List<Word> getMatchingWords() {
		return matchingWords;
	}

	/**
	 * @return The ID of this annotation.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the ID for this entity, or null if this entity could not be disambiguated. This ID is from the localized Wikipedia for this document's language.
	 */
	public String getEntityId() {
		return entityId;
	}

	/**
	 * @return the disambiguated Freebase ID for this entity, or null if either this entity could not be disambiguated, or a Freebase link doesn't exist.
	 */
	public String getFreebaseId() {
		return freebaseId;
	}

	/**
	 * @return The custom entity DictionaryEntry ID that matched this Entity, if this entity was matched in a custom dictionary.
	 */
	public String getCustomEntityId() {
		return customEntityId;
	}
	
	/**
	 * @return the full canonical link to Wikipedia for this entity, or null if either this entity could not be disambiguated or a Wikipedia link doesn't exist.
	 */
	public String getWikiLink() {
		return wikiLink;
	}

	/**
	 * @return the source text string that matched this entity.
	 */
	public String getMatchedText() {
		return matchedText;
	}

	/**
	 * @return The start offset in the input text for this entity. Note that TextRazor treats multi byte utf8 characters as a single position.
	 */
	public int getStartingPos() {
		return startingPos;
	}

	/**
	 * @return The end offset in the input text for this entity. Note that TextRazor treats multi byte utf8 characters as a single position.
	 */
	public int getEndingPos() {
		return endingPos;
	}


	/**
	 * @return List of Freebase types for this entity, or an empty list if there are none.
	 */
	public List<String> getFreebaseTypes() {
		return freebaseTypes;
	}

	/**
	 * @return List of DBPedia types for this entity, or an empty list if there are none.
	 */
	public List<String> getDBPediaTypes() {
		return type;
	}

	/**
	 * @return The relevance this entity has to the source text. This is a float on a scale of 0 to 1, with 1 being the most relevant. Relevance is determined by the contextual similarity between the entities context and facts in the TextRazor knowledgebase.
	 */
	public double getRelevanceScore() {
		return relevanceScore;
	}

	/**
	 * @return The confidence that TextRazor is correct that this is a valid entity. TextRazor uses an ever increasing number of signals to help spot valid entities, all of which contribute to this score. These include the contextual agreement between the words in the source text and our knowledgebase, agreement between other entities in the text, agreement between the expected entity type and context, prior probabilities of having seen this entity across wikipedia and other web datasets. The score ranges from 0.5 to 10, with 10 representing the highest confidence that this is a valid entity.
	 */
	public double getConfidenceScore() {
		return confidenceScore;
	}

	/**
	 * @return List of the word positions in the current sentence that make up this entity.
	 */
	public List<Integer> getMatchingTokens() {
		return matchingTokens;
	}

	/** 
	 * @return List of DBPedia types for this entity, or an empty array if there are none.
	 */
	public List<String> getType() {
		return type;
	}

	/**
	 * @return The disambiguated entityId in the English Wikipedia, where a link between localized and English ID could be found. Null if either the entity could not be linked, or where a language link did not exist.
	 */
	public String getEntityEnglishId() {
		return entityEnglishId;
	}
	
	/**
	 * @return A map of Query : List of result with data from enrichment queries that match this entity.
	 */
	public Map<String, List<String>> getData() {
		return data;
	}
	
	protected void addLinks(Response response) {
		if (null != matchingTokens) {
			for (Integer position : matchingTokens) {
				Word word = response.getWords().get(position);
				
				matchingWords.add(word);
				word.addEntity(this);
			}
		}
	}
		
}
