package com.textrazor.annotations;

import java.util.ArrayList;
import java.util.List;

public class Entailment extends Annotation {

	public static class EntailedTree {
		private String word;

		private int wordId;

		private int parentRelation;

		/**
		 * @return the entailed word.
		 */
		public String getWord() {
			return word;
		}
		
		public int getWordId() { 
			return wordId;
		}
		
		public int getParentRelation() {
			return parentRelation;
		}
	}

	private int id;

	private List<Integer> wordPositions;

	private List<Word> matchedWords = new ArrayList<Word>();

	private double priorScore;

	private double contextScore;

	private double score;

	private EntailedTree entailedTree;

	private List<String> entailedWords;

	/**
	 * @return a List of the {@link Word} objects that generated this entailment.
	 */
	public List<Word> getMatchedWords() {
		return matchedWords;
	}

	/**
	 * @return the ID of this annotation.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the indexes of words that generated this entailment.
	 */
	public List<Integer> getWordPositions() {
		return wordPositions;
	}

	/**
	 * @return the score of this entailment independent of the context it is used in this sentence.
	 */
	public double getPriorScore() {
		return priorScore;
	}

	/**
	 * @return the score of agreement between the source word's usage in this sentence and the entailed words usage in our knowledgebase.
	 */
	public double getContextScore() {
		return contextScore;
	}
	
	/**
	 * @return the overall confidence that TextRazor is correct that this is a valid entailment, a combination of the prior and context score.
	 */
	public double getScore() {
		return score;
	}

	/**
	 * @return the words that are entailed by the source word.
	 */
	public EntailedTree getEntailedTree() {
		return entailedTree;
	}

	/**
	 * @return a list of String lemmas that make up words entailed by the source word
	 */
	public List<String> getEntailedWords() {
		return entailedWords;
	}

	protected void addLinks(Response response) {
		if (null != wordPositions) {
			for (Integer wordPosition : wordPositions) {
				Word word = response.getWords().get(wordPosition);

				matchedWords.add(word);
				word.addEntailment(this);
			}
		}
	}

}
