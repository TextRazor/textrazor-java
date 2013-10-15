package com.textrazor.annotations;

import java.util.ArrayList;
import java.util.List;

public class Custom {

	public static class BoundVariable {

		public static class LinkedAnnotation {

			private String annotationName;

			private int linkedId;

			public String getAnnotationName() {
				return annotationName;
			}

			public int getLinkedId() {
				return linkedId;
			}
		}

		private String key;

		private List<Integer> intValue;

		private List<Float> floatValue;

		private List<String> stringValue;

		private List<byte[]> byteValue;

		private List<LinkedAnnotation> links;

		private List<Annotation> annotationValue = new ArrayList<Annotation>();
		
		private List<Word> wordValue = new ArrayList<Word>();
		private List<Sentence> sentenceValue = new ArrayList<Sentence>();
		private List<Relation> relationValue = new ArrayList<Relation>();
		private List<Entity> entityValue = new ArrayList<Entity>();
		private List<Property> propertyValue = new ArrayList<Property>();
		private List<NounPhrase> nounPhraseValue = new ArrayList<NounPhrase>();
		private List<Entailment> entailmentValue = new ArrayList<Entailment>();
		private List<Topic> topicValue = new ArrayList<Topic>();
		private List<Topic> coarseTopicValue = new ArrayList<Topic>();

		protected void addLinks(Response response) {
			if (null != links) {
				for (LinkedAnnotation link : links) {
					String annotationName = link.getAnnotationName();
					int linkedId = link.getLinkedId();

					if (annotationName.equals("word")) {
						Word word = response.getWords().get(linkedId);
						annotationValue.add(word);
						wordValue.add(word);
					}
					else if (annotationName.equals("sentence")) {
						Sentence sentence = response.getSentences().get(linkedId);
						annotationValue.add(sentence);
						sentenceValue.add(sentence);
					}
					else if (annotationName.equals("relation")) {
						Relation relation = response.getRelations().get(linkedId);
						annotationValue.add(relation);
						relationValue.add(relation);
					}
					else if (annotationName.equals("property")) {
						Property property = response.getProperties().get(linkedId);
						annotationValue.add(property);
						propertyValue.add(property);
					}
					else if (annotationName.equals("nounPhrase")) {
						NounPhrase nounPhrase = response.getNounPhrases().get(linkedId);
						annotationValue.add(nounPhrase);
						nounPhraseValue.add(nounPhrase);
					}
					else if (annotationName.equals("entailment")) {
						Entailment entailment = response.getEntailments().get(linkedId);
						annotationValue.add(entailment);
						entailmentValue.add(entailment);
					}
					else if (annotationName.equals("entity")) {
						Entity entity = response.getEntities().get(linkedId);
						annotationValue.add(entity);
						entityValue.add(entity);
					}
					else if (annotationName.equals("topic")) {
						Topic topic = response.getTopics().get(linkedId);
						annotationValue.add(topic);
						topicValue.add(topic);
					}
					else if (annotationName.equals("coarseTopic")) {
						Topic topic = response.getCoarseTopics().get(linkedId);
						annotationValue.add(topic);
						coarseTopicValue.add(topic);
					}
				}
			}
		}

		public String getKey() {
			return key;
		}

		public List<Integer> getIntValue() {
			return intValue;
		}

		public List<Float> getFloatValue() {
			return floatValue;
		}

		public List<String> getStringValue() {
			return stringValue;
		}

		public List<byte[]> getByteValue() {
			return byteValue;
		}

		public List<LinkedAnnotation> getLinks() {
			return links;
		}

		public List<Annotation> getAnnotationValue() {
			return annotationValue;
		}

		public List<Word> getWordValue() {
			return wordValue;
		}

		public List<Sentence> getSentenceValue() {
			return sentenceValue;
		}

		public List<Relation> getRelationValue() {
			return relationValue;
		}

		public List<Property> getPropertyValue() {
			return propertyValue;
		}

		public List<NounPhrase> getNounPhraseValue() {
			return nounPhraseValue;
		}

		public List<Entailment> getEntailmentValue() {
			return entailmentValue;
		}

		public List<Topic> getTopicValue() {
			return topicValue;
		}

		public List<Topic> getCoarseTopicValue() {
			return coarseTopicValue;
		}

		public List<Entity> getEntityValue() {
			return entityValue;
		}
	}

	private String name;

	private List<BoundVariable> contents;

	protected void addLinks(Response response) {
		if (null != contents) {
			for (BoundVariable variable : contents) {
				variable.addLinks(response);
			}
		}
	}

	public String getName() {
		return name;
	}

	public List<BoundVariable> getContents() {
		return contents;
	}

}
