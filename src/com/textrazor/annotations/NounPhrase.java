package com.textrazor.annotations;

import java.util.ArrayList;
import java.util.List;

public class NounPhrase extends Annotation {

	int id;

	List<Integer> wordPositions;

	List<Word> words = new ArrayList<Word>();

	/**
	 * @return A list of the {@link Word} objects in this phrase within their sentence.
	 */
	public List<Word> getWords() {
		return words;
	}

	protected void setWords(List<Word> words) {
		this.words = words;
	}

	public int getId() {
		return id;
	}

	/**
	 * @return A list of the positions of the words in this phrase within their sentence.
	 */
	public List<Integer> getWordPositions() {
		return wordPositions;
	}

	protected void addLinks(Response response) {
		if (null != wordPositions) {
			for (Integer wordPosition : wordPositions) {
				Word word = response.getWords().get(wordPosition);

				words.add(word);
				word.addNounPhrase(this);
			}
		}
	}

}
