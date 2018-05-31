package com.textrazor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.textrazor.annotations.AnalyzedText;
import com.textrazor.net.QueryBuilder;
import com.textrazor.net.TextRazorConnection;

public class TextRazor extends TextRazorConnection {
	private List<String> extractors = new ArrayList<String>();

	private boolean cleanupHTML = false;
	private String cleanupMode;
	private boolean cleanupReturnRaw = false;
	private boolean cleanupReturnCleaned = false;
	private boolean cleanupUseMetadata = true;
	
	private String downloadUserAgent;
	
	private String languageOverride;

	private boolean allowOverlap = true;
	
	private List<String> dbpediaTypeFilters = new ArrayList<String>();
	private List<String> freebaseTypeFilters = new ArrayList<String>();
	
	private List<String> enrichmentQueries = new ArrayList<String>();

	private String rules;

	private List<String> entityDictionaries = new ArrayList<String>();
	
	private List<String> classifiers = new ArrayList<String>();
	private Integer maxCategories;
	
	/**
	 * Creates a new TextRazor client with the specified API Key.
	 * 
	 * @param apiKey
	 */
	public TextRazor(String apiKey) {
		super(apiKey);
	}

	private QueryBuilder generatePOSTBody() {
		QueryBuilder queryBuilder = new QueryBuilder();
		
		try {
			queryBuilder.addParam("cleanupHTML", cleanupHTML ? "true" : "false")
						.addParam("cleanup.mode", cleanupMode)
						.addParam("cleanup.returnRaw", cleanupReturnRaw)
						.addParam("cleanup.returnCleaned", cleanupReturnCleaned)
						.addParam("cleanup.useMetadata", cleanupUseMetadata)
						.addParam("download.userAgent", downloadUserAgent)
						.addParam("extractors", extractors)
						.addParam("entities.filterDbpediaTypes", dbpediaTypeFilters)
						.addParam("entities.filterFreebaseTypes", freebaseTypeFilters)
						.addParam("entities.dictionaries", entityDictionaries)
						.addParam("entities.allowOverlap", allowOverlap)
						.addParam("entities.enrichmentQueries", enrichmentQueries)
						.addParam("languageOverride", languageOverride)
						.addParam("classifiers", classifiers)
						.addParam("classifier.maxCategories", maxCategories)
						.addParam("rules", rules);
			
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Could not url encode form params.");
		}

		return queryBuilder;
	}

	
	/**
	 * Makes a TextRazor request to analyze a string and returning TextRazor metadata.
	 * 
	 * @param text The content to analyze     
	 * @return The TextRazor metadata
	 * @throws NetworkException
	 * @throws AnalysisException
	 */
	public AnalyzedText analyze(String text) throws NetworkException, AnalysisException {
		if (null == text) {
			throw new RuntimeException("text param cannot be null.");
		}

		QueryBuilder requestBody = generatePOSTBody();

		try {
			requestBody.addParam("text", text);
			
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Could not url encode form params.");
		}

		AnalyzedText response = sendRequest(
				"", 
				requestBody.build(),
				ContentType.FORM,
				"POST", 
				AnalyzedText.class);
		
		response.createAnnotationLinks();

		return response;
	}
	
	/**
	 * Makes a TextRazor request to analyze a URL and return TextRazor metadata.
	 * 
	 * @param url The content to analyze     
	 * @return The TextRazor metadata
	 * @throws NetworkException
	 * @throws AnalysisException
	 */
	public AnalyzedText analyzeUrl(String url) throws NetworkException, AnalysisException {
		if (null == url) {
			throw new RuntimeException("url param cannot be null.");
		}
		
		QueryBuilder requestBody = generatePOSTBody();

		try {
			requestBody.addParam("url", url);
			
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Could not url encode form params.");
		}
		
		AnalyzedText response = sendRequest(
				"", 
				requestBody.build(),
				ContentType.FORM,
				"POST", 
				AnalyzedText.class);
					
		response.createAnnotationLinks();

		return response;
	}


	/**
	 * @return The TextRazor preprocessing cleanup mode.
	 */
	public String getCleanupMode() {
		return cleanupMode;
	}
	
	/**
	 * @param cleanupMode
	 * 
	 * Controls the preprocessing cleanup mode that TextRazor will apply to your content before analysis. For all options aside from "raw" any position offsets returned will apply to the final cleaned text, not the raw HTML. If the cleaned text is required please see the cleanup_return_cleaned option.
	 *
	 * Valid Options:
	 * raw         - Content is analyzed "as-is", with no preprocessing.
	 * stripTags   - All Tags are removed from the document prior to analysis. This will remove all HTML, XML tags, but the content of headings, menus will remain. This is a good option for analysis of HTML pages that aren't long form documents.
	 * cleanHTML   - Boilerplate HTML is removed prior to analysis, including tags, comments, menus, leaving only the body of the article.
	 */
	public void setCleanupMode(String cleanupMode) {
		this.cleanupMode = cleanupMode;
	}
	
	/**
	 * @return true the TextRazor response will contain the raw_text property, the text it analyzed after preprocessing.
	 */
	public boolean getCleanupReturnRaw() {
		return cleanupReturnRaw;
	}
	
	/**
	 * @param cleanupReturnRaw When true, the TextRazor response will contain the raw_text property, the original text TextRazor received or downloaded before cleaning.
	 *   	  To save bandwidth, only set this to true if you need it in your application. Defaults to false.
	 */
	public void setCleanupReturnRaw(boolean cleanupReturnRaw) {
		this.cleanupReturnRaw = cleanupReturnRaw;
	}
	
	/**
	 * @return true the TextRazor response will contain the cleaned_text property, the text it analyzed after preprocessing.
	 */
	public boolean getCleanupReturnCleaned() {
		return cleanupReturnCleaned;
	}
	
	/**
	 * @param cleanupReturnCleaned When true, the TextRazor response will contain the cleaned_text property, the text it analyzed after preprocessing.
	 * 		  To save bandwidth, only set this to true if you need it in your application. Defaults to false.
	 */
	public void setCleanupReturnCleaned(boolean cleanupReturnCleaned) {
		this.cleanupReturnCleaned = cleanupReturnCleaned;
	}
	
	/**
	 * @return true TextRazor will use metadata extracted from your document to help in the disambiguation/extraction process.
	 */
	public boolean getCleanupUseMetadata() {
		return cleanupUseMetadata;
	}
	
	/**
	 * @param cleanupUseMetadata When true TextRazor will use metadata extracted from your document to help in the disambiguation/extraction process.
	 * 		  This include HTML titles and metadata, and can significantly improve results for shorter documents without much other content.
     *		  This option has no effect when cleanup_mode is 'raw'. Defaults to True.
	 */
	public void setCleanupUseMetadata(boolean cleanupUseMetadata) {
		this.cleanupUseMetadata = cleanupUseMetadata;
	}
	
	/**
	 * @return The User-Agent header to be used when downloading over HTTP.
	 */
	public String getDownloadUserAgent() {
		return downloadUserAgent;
	}
	
	/**
	 * @param downloadUserAgent The User-Agent header to be used when downloading over HTTP. This should be a descriptive string identifying your application, or an end user's browser user agent if you are performing live requests from a given user.
	 *
	 * 	      Defaults to "TextRazor Downloader (https://www.textrazor.com)"
	 */
	public void setDownloadUserAgent(String downloadUserAgent) {
		this.downloadUserAgent = downloadUserAgent;
	}
	
	/**
	 * @return List of "extractors" used to extract data from requests sent from this class.
	 */
	public List<String> getExtractors() {
		return extractors;
	}

	/**
	 * Sets the set of "extractors" used to extract data from requests sent from this class.
	 * 
	 * Valid options: entities, topics, words, dependency-trees, relations, entailments, senses 
	 * 
	 * @param extractors List of string extractor names.
	 */
	public void setExtractors(List<String> extractors) {
		this.extractors = extractors;
	}

	/**
	 * Adds a new "extractor" to the request.
	 * 
	 * @param extractor The new extractor name
	 */
	public void addExtractor(String extractor) {
		if (null == this.extractors) {
			this.extractors = new ArrayList<String>();
		}

		this.extractors.add(extractor);
	}

	/**
	 * Sets a string containing Prolog logic.  All rules matching an extractor name listed in the request will be evaluated 
     * and all matching param combinations linked in the response.
	 * 
	 * @param rules Custom Prolog rules
	 */
	public void setRules(String rules) {
		this.rules = rules;
	}
	
	/**
	 * @return Custom rules to apply to this request.
	 */
	public String getRules() {
		return rules;
	}
	
	/**
	 * @return true if boilerplate HTML is filtered before processing by TextRazor.
	 */
	public boolean isCleanupHTML() {
		return cleanupHTML;
	}

	/**
	 * @param cleanupHTML to true to clean boilerplate HTML before processing by TextRazor.
	 */
	public void setCleanupHTML(boolean cleanupHTML) {
		this.cleanupHTML = cleanupHTML;
	}

	/**
	 * @return true if TextRazor is allowed to return overlapping entities.
	 */
	public boolean isAllowOverlap() {
		return allowOverlap;
	}

	/**
	 * @param allowOverlap to true to allow TextRazor to return overlapping entities.  Default true.
	 */
	public void setAllowOverlap(boolean allowOverlap) {
		this.allowOverlap = allowOverlap;
	}
	
	/**
	 * Gets the ISO-639-2 language code to use to analyze content.
	 * If null TextRazor will use the automatically identified language.
	 * 
	 * @return the ISO-639-2 language code
	 */
	public String getLanguageOverride() {
		return languageOverride;
	}

	/**
	 * When set to a ISO-639-2 language code, force TextRazor to analyze content with this language.  
	 * If null TextRazor will use the automatically identified language.
	 * 
	 * @param languageOverride the new ISO-639-2 language code
	 */
	public void setLanguageOverride(String languageOverride) {
		this.languageOverride = languageOverride;
	}

	/**
	 * Get a list of "Enrichment Queries", used to enrich the entity response with structured linked data.
     * The syntax for these queries is documented at https://www.textrazor.com/enrichment. 
	 */
	public List<String> getEnrichmentQueries() {
		return enrichmentQueries;
	}
	
	/**
	 * Set a list of "Enrichment Queries", used to enrich the entity response with structured linked data.
     * The syntax for these queries is documented at https://www.textrazor.com/enrichment. 
	 */
	public void setEnrichmentQueries(List<String> enrichmentQueries) {
		this.enrichmentQueries = enrichmentQueries;
	}
	
	/**
	 * Gets the list of DBPedia types to filter entity extraction. All returned entities must match at least one of these types. See the <a href="https://www.textrazor.com/types">Type Dictionary</a> for more details on supported types.
	 * 
	 * @return List of DBPedia types
	 */
	public List<String> getDbpediaTypeFilters() {
		return dbpediaTypeFilters;
	}

	/**
	 * Sets the list of DBPedia types to filter entity extraction. All returned entities must match at least one of these types. See the <a href="https://www.textrazor.com/types">Type Dictionary</a> for more details on supported types.
	 * 
	 * @param dbpediaTypeFilters  New list of DBPedia types
	 */
	public void setDbpediaTypeFilters(List<String> dbpediaTypeFilters) {
		this.dbpediaTypeFilters = dbpediaTypeFilters;
	}

	/**
	 * Gets the list of Freebase types to filter entity extraction. All returned entities must match at least one of these types. See the <a href="https://www.textrazor.com/types">Type Dictionary</a> for more details on supported types.
	 * 
	 * @return List of Freebase types
	 */
	public List<String> getFreebaseTypeFilters() {
		return freebaseTypeFilters;
	}

	/**
	 * Sets the list of Freebase types to filter entity extraction. All returned entities must match at least one of these types. See the <a href="https://www.textrazor.com/types">Type Dictionary</a> for more details on supported types.
	 * 
	 * @param freebaseTypeFilters List of Freebase types
	 */
	public void setFreebaseTypeFilters(List<String> freebaseTypeFilters) {
		this.freebaseTypeFilters = freebaseTypeFilters;
	}
	
	/**
	 * Sets a list of the custom entity dictionaries to match against your content. Each item should be a string ID corresponding to dictionaries you have previously configured through the Dictionary interface.
	 * 
	 * @param entityDictionaryIds List of dictionary IDs.
	 */
	public void setEntityDictionaries(List<String> entityDictionaryIds) {
		this.entityDictionaries = entityDictionaryIds;
	}
	
	/**
	 * Gets a list of the custom entity dictionaries to match against your content. Each item should be a string ID corresponding to dictionaries you have previously configured through the Dictionary interface.
	 * 
	 * return List of dictionary IDs.
	 */
	public List<String> getEntityDictionaries() {
		return this.entityDictionaries;
	}
	
	/**
	 * Sets a list of classifiers to evaluate against your document. Each entry should be a string ID corresponding to either one of TextRazor's default classifiers, or one you have previously configured through the ClassifierManager interface.
	 *
	 * Valid options: textrazor_iab, textrazor_newscodes, custom classifier name
	 *
	 * @param classifierIds List of classifier IDs.
	 */
	public void setClassifiers(List<String> classifierIds) {
		this.classifiers = classifierIds;
	}
	
	/**
	 * Gets a list of classifiers to evaluate against your document. Each entry should be a string ID corresponding to either one of TextRazor's default classifiers, or one you have previously configured through the ClassifierManager interface.
	 *
	 * return List of classifier IDs.
	 */
	public List<String> getClassifiers() {
		return this.classifiers;
	}
	
	/**
	 * Sets the maximum number of matching categories that TextRazor will return from classifiers requested in the "classifiers" request property. Default '10'.
	 * 
	 * @param maxCategories Number of categories
	 */
	public void setMaxCategories(Integer maxCategories) {
		this.maxCategories = maxCategories;
	}
	
	/**
	 * Gets the maximum number of matching categories that TextRazor will return from classifiers requested in the "classifiers" request property.
	 * 
	 * @param maxCategories Number of categories
	 */
	public Integer getMaxCategories() {
		return this.maxCategories;
	}
}
