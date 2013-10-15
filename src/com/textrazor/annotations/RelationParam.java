package com.textrazor.annotations;

import java.util.ArrayList;
import java.util.List;

public class RelationParam {
	
	private String relation;

	private List<Integer> wordPositions;

	private List<Word> paramWords = new ArrayList<Word>();

	public String getRelation() {
		return relation;
	}

	public List<Integer> getWordPositions() {
		return wordPositions;
	}

	public List<Word> getParamWords() {
		return paramWords;
	}

	public void addLinks(Response response) {
		if (null != wordPositions) {
			for (Integer position : wordPositions) {
				Word word = response.getWords().get(position);
				
				paramWords.add(word);
				word.addRelationParam(this);
			}
		}		
	}

}
