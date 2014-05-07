package com.textrazor.annotations;

import java.util.ArrayList;
import java.util.List;

public class Response {

	private List<Sentence> sentences = new ArrayList<Sentence>();

	private List<Word> words = new ArrayList<Word>();

	private List<Relation> relations;

	private List<Topic> topics;

	private List<Topic> coarseTopics;

	private List<Entity> entities;

	private List<Entailment> entailments;

	private List<NounPhrase> nounPhrases;

	private List<Property> properties;

	private List<Custom> customAnnotations;

	private String customAnnotationOutput;

	private String language;

	private boolean languageIsReliable;


	/**
	 * @return List of {@link Sentence} objects that were extracted from your text.
	 */
	public List<Sentence> getSentences() {
		return sentences;
	}

	/**
	 * @return List of {@link Relation} objects that were extracted from your text.
	 */
	public List<Relation> getRelations() {
		return relations;
	}

	/**
	 * @return List of {@link Topic} objects that were extracted from your text.
	 */
	public List<Topic> getTopics() {
		return topics;
	}

	/**
	 * @return List of {@link Entity} objects that were extracted from your text.
	 */
	public List<Entity> getEntities() {
		return entities;
	}

	/**
	 * @return List of Coarse {@link Topic} objects that were extracted from your text.
	 */
	public List<Topic> getCoarseTopics() {
		return coarseTopics;
	}

	/**
	 * @return List of {@link Entailment} objects that were extracted from your text.
	 */
	public List<Entailment> getEntailments() {
		return entailments;
	}

	/**
	 * @return List of {@link NounPhrase} objects that were extracted from your text.
	 */
	public List<NounPhrase> getNounPhrases() {
		return nounPhrases;
	}

	/**
	 * @return List of {@link Property} objects that were extracted from your text.
	 */
	public List<Property> getProperties() {
		return properties;
	}

	/**
	 * @return List of {@link Custom} objects, custom rule matches.
	 */
	public List<Custom> getCustomAnnotations() {
		return customAnnotations;
	}

	/**
	 * @return Any output generated as part of the custom annotation process.
	 */
	public String getCustomAnnotationOutput() {
		return customAnnotationOutput;
	}

	/**
	 * @return The ISO-639-2 language used to analyze this document, either explicitly provided as the languageOverride, or as detected by the language detector.
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @return Boolean indicating whether the language detector was confident of its classification. This may be false for shorter or ambiguous content.
	 */
	public boolean isLanguageIsReliable() {
		return languageIsReliable;
	}

	protected void createAnnotationLinks() {
		// If the user has requested the "words" extractor we can link the various annotations together for
		// easy traversal.
		if (null != sentences && !sentences.isEmpty()) {
			for (Sentence sentence : sentences) {
				for (Word word : sentence.getWords()) {
					words.add(word);
				}
			}

			if (null != nounPhrases) {
				for (NounPhrase nounPhrase : nounPhrases) {
					nounPhrase.addLinks(this);
				}
			}

			if (null != entities) { 
				for (Entity entity : entities) {
					entity.addLinks(this);
				}
			}

			if (null != entailments) {
				for (Entailment entailment : entailments) {
					entailment.addLinks(this);
				}
			}

			if (null != properties) {
				for (Property property : properties) {
					property.addLinks(this);
				}
			}

			if (null != relations) {
				for (Relation relation : relations) {
					relation.addLinks(this);
				}
			}

			if (null != customAnnotations) {
				for (Custom custom : customAnnotations) {
					custom.addLinks(this);
				}
			}
		}
	}

	/**
	 * @return List of {@link Word} objects that were extracted from your text.
	 */
	public List<Word> getWords() {
		return words;
	}


}
