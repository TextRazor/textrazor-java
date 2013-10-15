package com.textrazor.annotations;

import java.util.ArrayList;
import java.util.List;

public class Sentence extends Annotation {

	private List<Word> words = new ArrayList<Word>();
	
	private int position;
	
	/**
	 * @return The position of this sentence in your text.
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @return List of {@link Word} objects that are part of this sentence.
	 */
	public List<Word> getWords() {
		return words;
	}

}
