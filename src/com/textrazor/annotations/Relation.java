package com.textrazor.annotations;

import java.util.List;

public class Relation extends Annotation {

	private int id;

	private List<Integer> wordPositions;

	private List<Word> predicateWords;

	private List<RelationParam> params;

	/**
	 * @return The ID of this Annotation
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return A list of the positions of the predicate words in this relation within their sentence.
	 */
	public List<Integer> getWordPositions() {
		return wordPositions;
	}

	/**
	 * @return A list of the {@link Word} objects in this relation within their sentence.
	 */
	public List<Word> getPredicateWords() {
		return predicateWords;
	}

	/**
	 * @return A list of the {@link RelationParam} object that a paramaters to the predicate.
	 */
	public List<RelationParam> getParams() {
		return params;
	}

	protected void addLinks(Response response) {
		if (null != wordPositions) {
			for (Integer position : wordPositions) {
				Word word = response.getWords().get(position);
				predicateWords.add(word);
				
				word.addRelation(this);
			}
		}
		
		if (null != params) {
			for (RelationParam param : params) {
				param.addLinks(response);
			}
		}
	}

}
