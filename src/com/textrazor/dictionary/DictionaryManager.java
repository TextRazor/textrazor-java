package com.textrazor.dictionary;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.textrazor.AnalysisException;
import com.textrazor.NetworkException;
import com.textrazor.dictionary.model.AllDictionariesResponse;
import com.textrazor.dictionary.model.AllEntriesResponse;
import com.textrazor.dictionary.model.Dictionary;
import com.textrazor.dictionary.model.DictionaryEntry;
import com.textrazor.dictionary.model.GetDictionaryEntryResponse;
import com.textrazor.dictionary.model.GetDictionaryResponse;
import com.textrazor.dictionary.model.PagedAllEntries;
import com.textrazor.net.QueryBuilder;
import com.textrazor.net.TextRazorConnection;
import com.textrazor.net.TextRazorResponse;

public class DictionaryManager extends TextRazorConnection {

	public DictionaryManager(String apiKey) {
		super(apiKey);
	}

	public TextRazorResponse createDictionary(Dictionary dictionary) throws NetworkException, AnalysisException {
		String encodedId = encodeURLParam(dictionary.getId());

		if (null == encodedId) {
			throw new RuntimeException("Invalid Dictionary ID."); 
		}

		return sendRequest("entities/" + encodedId,
				dictionary, 
				ContentType.JSON,
				"PUT",
				TextRazorResponse.class);
	}

	public TextRazorResponse deleteDictionary(String id) throws NetworkException, AnalysisException {
		String encodedId = encodeURLParam(id);

		if (null == encodedId) {
			throw new RuntimeException("Invalid Dictionary ID."); 
		}

		return sendRequest("entities/" + encodedId,
				null, 
				ContentType.JSON,
				"DELETE",
				TextRazorResponse.class);
	}

	public TextRazorResponse deleteDictionary(Dictionary dictionary) throws NetworkException, AnalysisException {
		return deleteDictionary(dictionary.getId());
	}

	public List<Dictionary> allDictionaries() throws NetworkException, AnalysisException {	
		return sendRequest("entities/",
				null, 
				ContentType.JSON,
				"GET",
				AllDictionariesResponse.class).getDictionaries();
	}

	public Dictionary getDictionary(String id) throws NetworkException, AnalysisException {	
		String encodedId = encodeURLParam(id);

		if (null == encodedId) {
			throw new RuntimeException("Invalid Dictionary ID."); 
		}

		GetDictionaryResponse response = sendRequest("entities/" + encodedId,
				null, 
				ContentType.JSON,
				"GET",
				GetDictionaryResponse.class);
		
		return response.getResponse();
	}

	public PagedAllEntries allEntries(String id, int limit, int offset) throws NetworkException, AnalysisException {	
		String encodedId = encodeURLParam(id);

		if (null == encodedId) {
			throw new RuntimeException("Invalid Dictionary ID."); 
		}

		QueryBuilder paramBuilder = new QueryBuilder();

		try {
			if (limit > 0) {
				paramBuilder.addParam("limit", Integer.toString(limit));
			}

			if (offset > 0) {
				paramBuilder.addParam("offset", Integer.toString(offset));
			}
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unable to encode dictionary paging paramaters."); 
		}

		AllEntriesResponse response = sendRequest("entities/" + encodedId + "/_all?" + paramBuilder.build(),
				null, 
				ContentType.JSON,
				"GET",
				AllEntriesResponse.class);
		
		return response.getResponse();
	}

	public PagedAllEntries allEntries(String id, int limit) throws NetworkException, AnalysisException {	
		return allEntries(id, limit, -1);
	}

	public PagedAllEntries allEntries(String id) throws NetworkException, AnalysisException {	
		return allEntries(id, -1);
	}

	public TextRazorResponse addEntries(String id, List<DictionaryEntry> entries) throws NetworkException, AnalysisException {	
		String encodedId = encodeURLParam(id);

		if (null == encodedId) {
			throw new RuntimeException("Invalid Dictionary ID."); 
		}

		return sendRequest("entities/" + encodedId + "/",
				entries, 
				ContentType.JSON,
				"POST",
				TextRazorResponse.class);
	}

	public DictionaryEntry getEntry(String dictionaryId, String entryId) throws NetworkException, AnalysisException {	
		String encodedDictionaryId = encodeURLParam(dictionaryId);
		String encodedEntryId = encodeURLParam(entryId);

		if (null == encodedDictionaryId) {
			throw new RuntimeException("Invalid Dictionary ID."); 
		}

		if (null == encodedEntryId) {
			throw new RuntimeException("Invalid Entry ID."); 
		}

		GetDictionaryEntryResponse response = sendRequest("entities/" + encodedDictionaryId + "/" + encodedEntryId,
				null, 
				ContentType.JSON,
				"GET",
				GetDictionaryEntryResponse.class);
		
		return response.getResponse();
	}

	public TextRazorResponse deleteEntry(String dictionaryId, String entryId) throws NetworkException, AnalysisException {	
		String encodedDictionaryId = encodeURLParam(dictionaryId);
		String encodedEntryId = encodeURLParam(entryId);

		if (null == encodedDictionaryId) {
			throw new RuntimeException("Invalid Dictionary ID."); 
		}

		if (null == encodedEntryId) {
			throw new RuntimeException("Invalid Entry ID."); 
		}

		return sendRequest("entities/" + encodedDictionaryId + "/" + encodedEntryId,
				null, 
				ContentType.JSON,
				"DELETE",
				TextRazorResponse.class);
	}

}
