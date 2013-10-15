package com.textrazor.annotations;

import java.util.ArrayList;
import java.util.List;

public class Property extends Annotation {

	private int id;
	
	private List<Integer> wordPositions;
	
	private List<Word> predicateWords = new ArrayList<Word>();
	
	private List<Integer> propertyPositions;
	
	private List<Word> propertyWords = new ArrayList<Word>();

	/**
	 * @return a list of the positions of the words in the predicate (or focus) of this property.
	 */
	public List<Integer> getWordPositions() {
		return wordPositions;
	}

	/**
	 * @return a list of the {@link Word} in the predicate (or focus) of this property.
	 */
	public List<Word> getPredicateWords() {
		return predicateWords;
	}
	
	/**
	 * @return The ID of this annotation.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return a list of the word positions that make up the modifier of the predicate of this property.
	 */
	public List<Integer> getPropertyPositions() {
		return propertyPositions;
	}

	/**
	 * @return a list of the {@link Word} objects that make up the modifier of the predicate of this property.
	 */
	public List<Word> getPropertyWords() {
		return propertyWords;
	}
	
	protected void addLinks(Response response) {
		if (null != wordPositions) {
			for (Integer position : wordPositions) {
				Word word = response.getWords().get(position);
				
				predicateWords.add(word);
				word.addPropertyPredicate(this);
			}
		}
		
		if (null != propertyPositions) {
			for (Integer position : propertyPositions) {
				Word word = response.getWords().get(position);
				
				propertyWords.add(word);
				word.addPropertyProperty(this);
			}
		}
	}
}
