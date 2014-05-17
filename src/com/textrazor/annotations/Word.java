package com.textrazor.annotations;

import java.util.ArrayList;
import java.util.List;

public class Word extends Annotation {

	public static class Sense {
		
		private String synset;
		
		private float score;
		
		/**
		 * @return The WordNet 3.1 synset of this sense.
		 */
		public String getSynset() {
			return synset;
		}
		
		/**
		 * @return Score between 0 to 1 corresponding to the likelyhood this sense applies to this word.
		 */
		public float getScore() {
			return score;
		}
	}
	
	private int position;
	
	private int startingPos;
	
	private int endingPos;

	private String stem;
		
	private String lemma;
	
	private String token;
	
	private String partOfSpeech;
	
	private int parentPosition;
	
	private Word parentWord;
	
	private List<Word> children = new ArrayList<Word>();
	
	private String relationToParent;
	
	private List<Sense> senses = new ArrayList<Sense>();
	
	// Links to other annotations that this word is a part of.
	
	private List<Entailment> entailments = new ArrayList<Entailment>();
	
	private List<Entity> entities = new ArrayList<Entity>();
	
	private List<NounPhrase> nounPhrases = new ArrayList<NounPhrase>();
	
	private List<Property> propertyProperties = new ArrayList<Property>();
	
	private List<Property> propertyPredicates = new ArrayList<Property>();
	
	private List<Relation> relations = new ArrayList<Relation>();
	
	private List<RelationParam> relationParams = new ArrayList<RelationParam>();
	
	private List<Sentence> sentences = new ArrayList<Sentence>();
	
	/**
	 * @return The position of this word in its sentence.
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @return The start offset in the input text for this token. Note that TextRazor treats multi byte utf8 characters as a single position.
	 */
	public int getStartingPos() {
		return startingPos;
	}

	/**
	 * @return The end offset in the input text for this token. Note that TextRazor treats multi byte utf8 characters as a single position.
	 */
	public int getEndingPos() {
		return endingPos;
	}

	/**
	 * @return The stem of this word.
	 */
	public String getStem() {
		return stem;
	}

	/** 
	 * @return Returns the <a href="http://en.wikipedia.org/wiki/Lemma_(morphology)">morphological root</a> of this word
	 */
	public String getLemma() {
		return lemma;
	}

	/**
	 * @return The raw token string that matched this word in the source text.
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @return The Part of Speech that applies to this word. We use the <a href="http://www.comp.leeds.ac.uk/ccalas/tagsets/upenn.html">Penn treebank</a> tagset.
	 */
	public String getPartOfSpeech() {
		return partOfSpeech;
	}

	/**
	 * @return The position of the grammatical parent of this word, or null if this word is either at the root of the sentence or the "dependency-trees" extractor was not requested.
	 */
	public int getParentPosition() {
		return parentPosition;
	}

	/**
	 * @return The {@link Word} object of the grammatical parent of this word, or null if this word is either at the root of the sentence or the "dependency-trees" extractor was not requested.
	 */
	public Word getParentWord() {
		return parentWord;
	}

	/**
	 * @return List of the {@link Word} objects that are children of this word, empty list if the word has no children or the "dependency-trees" extractor was not requested.
	 */
	public List<Word> getChildren() {
		return children;
	}
	
	protected void addChild(Word child) {
		children.add(child);
	}
	
	/**
	 * @return List of {@link Sense} objects of this word.
	 */
	public List<Sense> getSenses() {
		return senses;
	}
	
	/**
	 * @return The Grammatical relation between this word and it's parent, or null if this word is either at the root of the sentence or the "dependency-trees" extractor was not requested. TextRazor parses into <a href="http://nlp.stanford.edu/software/dependencies_manual.pdf">Stanford uncollapsed dependencies</a>.
	 */
	public String getRelationToParent() {
		return relationToParent;
	}

	/**
	 * @return List of the {@link Entailment} objects that this word is a part of.
	 */
	public List<Entailment> getEntailments() {
		return entailments;
	}

	protected void addEntailment(Entailment entailment) {
		entailments.add(entailment);
	}
	
	/**
	 * @return List of the {@link Entity} objects that this word is a part of.
	 */
	public List<Entity> getEntities() {
		return entities;
	}
	
	protected void addEntity(Entity entity) {
		entities.add(entity);
	}

	/**
	 * @return List of the {@link NounPhrase} objects that this word is a part of.
	 */
	public List<NounPhrase> getNounPhrases() {
		return nounPhrases;
	}
	
	protected void addNounPhrase(NounPhrase phrase) {
		nounPhrases.add(phrase);
	}

	/**
	 * @return List of the {@link Relation} objects that this word is a part of.
	 */
	public List<Relation> getRelations() {
		return relations;
	}
	
	protected void addRelation(Relation relation) {
		relations.add(relation);
	}

	/**
	 * @return List of the {@link Sentence} objects that this word is a part of.
	 */
	public List<Sentence> getSentences() {
		return sentences;
	}
	
	protected void addSentence(Sentence sentence) {
		sentences.add(sentence);
	}

	/**
	 * @return List of the {@link Property} objects that this word is a part of.
	 */
	public List<Property> getPropertyProperties() {
		return propertyProperties;
	}
	
	protected void addPropertyProperty(Property property) {
		propertyProperties.add(property);
	}

	/**
	 * @return List of the {@link Property} objects that this word is a part of.
	 */
	public List<Property> getPropertyPredicates() {
		return propertyPredicates;
	}

	protected void addPropertyPredicate(Property property) {
		propertyPredicates.add(property);
	}
	
	/**
	 * @return List of the {@link RelationParam} objects that this word is a part of.
	 */
	public List<RelationParam> getRelationParams() {
		return relationParams;
	}
	
	protected void addRelationParam(RelationParam param) {
		relationParams.add(param);
	}
	
	protected void addLinks(Response response) {
		if (0 <= parentPosition) {
			parentWord = response.getWords().get(parentPosition);
			parentWord.addChild(this);
		}
	}
}
