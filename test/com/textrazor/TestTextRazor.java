package com.textrazor;

import com.textrazor.annotations.Custom;
import com.textrazor.annotations.Entity;
import com.textrazor.annotations.AnalyzedText;
import com.textrazor.annotations.Sentence;
import com.textrazor.annotations.Word;
import com.textrazor.annotations.Word.Sense;


public class TestTextRazor {

	/**
	 * @param args
	 * @throws NetworkException 
	 */
	public static void main(String[] args) throws NetworkException, AnalysisException {
		
		// Sample request, showcasing a couple of TextRazor features
		String API_KEY = "YOUR_TEXTRAZOR_API_KEY_HERE";
		
		TextRazor client = new TextRazor(API_KEY);
		
		client.addExtractor("words");
		client.addExtractor("entities");
		client.addExtractor("entailments");
		client.addExtractor("senses");
		client.addExtractor("entity_companies");
		
		String rules = "entity_companies(CompanyEntity) :- entity_type(CompanyEntity, 'Company').";
		
		client.setRules(rules);
		
		AnalyzedText response = client.analyze("Barclays misled shareholders and the public RBS about one of the biggest investments in the bank's history, a BBC Panorama investigation has found.");
		
		for (Sentence sentence : response.getResponse().getSentences()) {
			for (Word word : sentence.getWords()) {
				System.out.println("----------------");
				System.out.println("Word: " + word.getLemma());
				
				for (Entity entity : word.getEntities()) {
					System.out.println("Matched Entity: " + entity.getEntityId());
				}
				
				for (Sense sense: word.getSenses()) {
					System.out.println("Word sense: " + sense.getSynset() + " has score: " + sense.getScore());
				}		
			}
		}
		
		// Use a custom rule to match 'Company' type entities
		
		for (Custom custom : response.getResponse().getCustomAnnotations()) {
			
			for (Custom.BoundVariable variable : custom.getContents()) {
				
				if (null != variable.getEntityValue()) {
					for (Entity entity : variable.getEntityValue()) {
						
						System.out.println("Variable: " + variable.getKey() + " Value:" + entity.getEntityId());
					}
				}
			}
		}
		
	}

}
