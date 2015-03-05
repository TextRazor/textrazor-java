package com.textrazor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.textrazor.annotations.AnalyzedText;

public class TextRazor {

	private String apiKey;

	private String textrazorEndpoint;

	private String secureTextrazorEndpoint;

	private List<String> extractors;

	private boolean doCompression;

	private boolean doEncryption;

	private boolean cleanupHTML;
	private String cleanupMode;
	private boolean cleanupReturnRaw;
	private boolean cleanupReturnCleaned;
	private boolean cleanupUseMetadata;
	
	private String downloadUserAgent;
	
	private String languageOverride;

	private boolean allowOverlap;
	
	private List<String> dbpediaTypeFilters;

	private List<String> freebaseTypeFilters;
	
	private List<String> enrichmentQueries;

	private String rules;
	
	/**
	 * Creates a new TextRazor client with the specified API Key.
	 * 
	 * @param apiKey
	 */
	public TextRazor(String apiKey) {
		if (apiKey == null) {
			throw new RuntimeException("You must provide a TextRazor API key");
		}

		this.apiKey = apiKey;

		this.textrazorEndpoint = "http://api.textrazor.com/";
		this.secureTextrazorEndpoint = "https://api.textrazor.com/";

		this.doEncryption = false;
		this.doCompression = true;
		
		this.cleanupHTML = false;
		this.cleanupUseMetadata = true;
		this.cleanupReturnCleaned = false;
		this.cleanupReturnRaw = false;
		
		this.allowOverlap = true;
		
		this.extractors = new ArrayList<String>();
		this.dbpediaTypeFilters = new ArrayList<String>();
		this.freebaseTypeFilters = new ArrayList<String>();
		this.enrichmentQueries = new ArrayList<String>();
		
		
	}

	private StringBuffer generatePOSTBody() {
		StringBuffer payloadBuffer = new StringBuffer();

		try {
			payloadBuffer.append(URLEncoder.encode("apiKey", "UTF-8"));
			payloadBuffer.append("=");
			payloadBuffer.append(URLEncoder.encode(apiKey, "UTF-8"));
			payloadBuffer.append("&");
			payloadBuffer.append(URLEncoder.encode("cleanupHTML", "UTF-8"));
			payloadBuffer.append("=");
			payloadBuffer.append(URLEncoder.encode(cleanupHTML ? "true" : "false", "UTF-8"));
			
			if (null != cleanupMode) {
				payloadBuffer.append("&");
				payloadBuffer.append(URLEncoder.encode("cleanupMode", "UTF-8"));
				payloadBuffer.append("=");
				payloadBuffer.append(URLEncoder.encode(cleanupMode, "UTF-8"));
			}
			
			payloadBuffer.append("&");
			payloadBuffer.append(URLEncoder.encode("cleanupReturnRaw", "UTF-8"));
			payloadBuffer.append("=");
			payloadBuffer.append(URLEncoder.encode(cleanupReturnRaw ? "true" : "false", "UTF-8"));
			payloadBuffer.append("&");
			payloadBuffer.append(URLEncoder.encode("cleanupReturnCleaned", "UTF-8"));
			payloadBuffer.append("=");
			payloadBuffer.append(URLEncoder.encode(cleanupReturnCleaned ? "true" : "false", "UTF-8"));
			payloadBuffer.append("&");
			payloadBuffer.append(URLEncoder.encode("cleanupUseMetadata", "UTF-8"));
			payloadBuffer.append("=");
			payloadBuffer.append(URLEncoder.encode(cleanupUseMetadata ? "true" : "false", "UTF-8"));
			
			if (null != downloadUserAgent) {
				payloadBuffer.append("&");
				payloadBuffer.append(URLEncoder.encode("downloadUserAgent", "UTF-8"));
				payloadBuffer.append("=");
				payloadBuffer.append(URLEncoder.encode(downloadUserAgent, "UTF-8"));
			}
			
			for (String extractor : extractors) {
				payloadBuffer.append("&");
				payloadBuffer.append(URLEncoder.encode("extractors", "UTF-8"));
				payloadBuffer.append("=");
				payloadBuffer.append(URLEncoder.encode(extractor, "UTF-8"));
			}

			if (null != dbpediaTypeFilters) {
				for (String typeFilter : dbpediaTypeFilters) {
					payloadBuffer.append("&");
					payloadBuffer.append(URLEncoder.encode("entities.filterDbpediaTypes", "UTF-8"));
					payloadBuffer.append("=");
					payloadBuffer.append(URLEncoder.encode(typeFilter, "UTF-8"));
				}
			}

			if (null != freebaseTypeFilters) {
				for (String typeFilter : freebaseTypeFilters) {
					payloadBuffer.append("&");
					payloadBuffer.append(URLEncoder.encode("entities.filterFreebaseTypes", "UTF-8"));
					payloadBuffer.append("=");
					payloadBuffer.append(URLEncoder.encode(typeFilter, "UTF-8"));
				}
			}
			
			payloadBuffer.append("&");
			payloadBuffer.append(URLEncoder.encode("entities.allowOverlap", "UTF-8"));
			payloadBuffer.append("=");
			payloadBuffer.append(URLEncoder.encode(allowOverlap ? "true" : "false", "UTF-8"));
			
			if (null != enrichmentQueries) {
				for (String query : enrichmentQueries) {
					payloadBuffer.append("&");
					payloadBuffer.append(URLEncoder.encode("entities.enrichmentQueries", "UTF-8"));
					payloadBuffer.append("=");
					payloadBuffer.append(URLEncoder.encode(query, "UTF-8"));	
				}
			}
			
			if (null != languageOverride && !languageOverride.isEmpty()) {
				payloadBuffer.append("&");
				payloadBuffer.append(URLEncoder.encode("languageOverride", "UTF-8"));
				payloadBuffer.append("=");
				payloadBuffer.append(URLEncoder.encode(languageOverride, "UTF-8"));
			}
			
			if (null != rules && !rules.isEmpty()) {
				payloadBuffer.append("&");
				payloadBuffer.append(URLEncoder.encode("rules", "UTF-8"));
				payloadBuffer.append("=");
				payloadBuffer.append(URLEncoder.encode(rules, "UTF-8"));
			}
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Could not url encode form params.");
		}

		return payloadBuffer;
	}

	private AnalyzedText sendRequest(String requestBody) throws AnalysisException, NetworkException {
		URL url = null;
		HttpURLConnection connection = null;

		try {
			if (doEncryption) {
				url = new URL(secureTextrazorEndpoint);
			}
			else {
				url = new URL(textrazorEndpoint);
			}

			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);

			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", ""+ requestBody.length());

			if (doCompression) {
				connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
			}

			OutputStream os = connection.getOutputStream();
			os.write( requestBody.getBytes() );

			connection.connect();

			InputStream resultingInputStream;
			try {
				String encoding = connection.getContentEncoding();
				
				if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
					resultingInputStream = new GZIPInputStream(connection.getInputStream());
				} 
				else if (encoding != null && encoding.equalsIgnoreCase("deflate")) {
					resultingInputStream = new InflaterInputStream(connection.getInputStream(), new Inflater(true));
				}
				else {
					resultingInputStream = connection.getInputStream();
				}
			}
			catch (IOException ex) {
				ex.printStackTrace();
				resultingInputStream = connection.getErrorStream();
			}
			
			final Reader reader = new InputStreamReader(resultingInputStream);
			final char[] buf = new char[16384];
			int read;
			final StringBuffer sbuff = new StringBuffer();
			while((read = reader.read(buf)) > 0) {
				sbuff.append(buf, 0, read);
			}

			int status = connection.getResponseCode();
			if (status != 200) {
				throw new AnalysisException(status, sbuff.toString());
			}

			connection.disconnect();
			
			ObjectMapper mapper = new ObjectMapper(); 
			
			// Note - removing this is handy for debugging holes in the library, but for production
			// use we don't want to fail on new properties to allow forward compatibility.
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			
			AnalyzedText response = mapper.readValue(sbuff.toString(), AnalyzedText.class);
		
			response.createAnnotationLinks();
					
			return response;
		}
		catch (IOException e) {
			throw new NetworkException("Network Error when connecting to TextRazor", e);
		}
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

		if (null == extractors || 0 == extractors.size()) {
			throw new RuntimeException("You must specify at least 1 extractor.");	
		}

		StringBuffer requestBody = generatePOSTBody();

		try {
			requestBody.append("&");
			requestBody.append(URLEncoder.encode("text", "UTF-8"));
			requestBody.append("=");
			requestBody.append(URLEncoder.encode(text, "UTF-8"));
			
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Could not url encode form params.");
		}

		return sendRequest(requestBody.toString());
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

		if (null == extractors || 0 == extractors.size()) {
			throw new RuntimeException("You must specify at least 1 extractor.");	
		}

		StringBuffer requestBody = generatePOSTBody();

		try {
			requestBody.append("&");
			requestBody.append(URLEncoder.encode("url", "UTF-8"));
			requestBody.append("=");
			requestBody.append(URLEncoder.encode(url, "UTF-8"));
			
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Could not url encode form params.");
		}

		return sendRequest(requestBody.toString());
	}


	/**
	 * @return The API Key used to authenticate requests.
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey The API Key used to authenticate requests
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * @return The TextRazor Endpoint used for requests.
	 */
	public String getTextrazorEndpoint() {
		return textrazorEndpoint;
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
	 * @param textrazorEndpoint The custom TextRazor Endpoint for requests made by this class.
	 */
	public void setTextrazorEndpoint(String textrazorEndpoint) {
		this.textrazorEndpoint = textrazorEndpoint;
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
	 * @return true if compression is enabled on all TextRazor requests.
	 */
	public boolean isDoCompression() {
		return doCompression;
	}

	/**
	 * @param doCompression When true do compression on all TextRazor requests.
	 */
	public void setDoCompression(boolean doCompression) {
		this.doCompression = doCompression;
	}

	/**
	 * @return True if TextRazor requests are encrypted.
	 */
	public boolean isDoEncryption() {
		return doEncryption;
	}
	
	/**
	 * Set to true to encrypt all TextRazor requests.
	 * 
	 * @param doEncryption
	 */
	public void setDoEncryption(boolean doEncryption) {
		this.doEncryption = doEncryption;
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
	 * @param freebaseTypeFilters New List of Freebase types
	 */
	public void setFreebaseTypeFilters(List<String> freebaseTypeFilters) {
		this.freebaseTypeFilters = freebaseTypeFilters;
	}

	/**
	 * @return The TextRazor Endpoint used for making encrypted requests in this class.
	 */
	public String getSecureTextrazorEndpoint() {
		return secureTextrazorEndpoint;
	}

	/**
	 * @param secureTextrazorEndpoint The TextRazor Endpoint used for making encrypted requests.
	 */
	public void setSecureTextrazorEndpoint(String secureTextrazorEndpoint) {
		this.secureTextrazorEndpoint = secureTextrazorEndpoint;
	}

}
