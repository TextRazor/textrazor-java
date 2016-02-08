package com.textrazor.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.textrazor.AnalysisException;
import com.textrazor.NetworkException;

public class TextRazorConnection {
	
	protected enum ContentType {
		FORM, JSON, CSV
	}
	
	private String apiKey;

	private String textrazorEndpoint;
	private String secureTextrazorEndpoint;
	
	private boolean doCompression;
	private boolean doEncryption;
	
	private Proxy proxy = null;
	
	public TextRazorConnection(String apiKey) {
		if (apiKey == null) {
			throw new RuntimeException("You must provide a TextRazor API key");
		}
		
		this.apiKey = apiKey;

		this.textrazorEndpoint = "http://api.textrazor.com/";
		this.secureTextrazorEndpoint = "https://api.textrazor.com/";
		
		this.doEncryption = true;
		this.doCompression = true;
	}
	
	protected <ResponseType> ResponseType sendRequest(
			String path, 
			String requestBody,
			ContentType contentType,
			String method, 
			Class<ResponseType> responseClass) throws AnalysisException, NetworkException {
		URL url = null;
		HttpURLConnection connection = null;
		
		try {
			if (doEncryption) {
				url = new URL(secureTextrazorEndpoint + path);
			}
			else {
				url = new URL(textrazorEndpoint + path);
			}
			
			if (null != proxy) {
				connection = (HttpURLConnection)url.openConnection(proxy);
			}
			else {
				connection = (HttpURLConnection)url.openConnection();
			}
			
			connection.setRequestMethod(method);
			connection.setDoOutput(true);
			
			switch (contentType) {
			case JSON:
				connection.setRequestProperty("Content-Type", "application/json");
				break;
			case FORM:
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				break;
			case CSV:
				connection.setRequestProperty("Content-Type", "application/csv");
				break;
			}
			
			if (null != requestBody) {
				connection.setRequestProperty("Content-Length", ""+ requestBody.length());
			}
			
			connection.setRequestProperty("X-TextRazor-Key", apiKey);

			if (doCompression) {
				connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
			}
			
			if (null != requestBody) {
				OutputStream os = connection.getOutputStream();
				os.write( requestBody.getBytes("utf-8") );
			}
			
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
			
			try {
				return mapper.readValue(sbuff.toString(), responseClass);
			} catch (IOException e) {
				 e.printStackTrace();
				throw new RuntimeException("Unable to decode TextRazor response: ");
			}
		}
		catch (IOException e) {
			throw new NetworkException("Network Error when connecting to TextRazor", e);
		}
	}
	
	protected <RequestType, ResponseType> ResponseType sendRequest(
			String path, 
			RequestType requestBody,
			ContentType contentType,
			String method, 
			Class<ResponseType> responseClass) throws AnalysisException, NetworkException {
		ObjectMapper mapper = new ObjectMapper();

		// Optional params are represented by "null" in the build objects.
		// We don't need to actually send a null to the server.
		mapper.setSerializationInclusion(Include.NON_NULL);
		
		try {
			String jsonString = mapper.writeValueAsString(requestBody);
			return sendRequest(path, jsonString, contentType, method, responseClass);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Unable to encode TextRazor request.");
		}
	}
	
	protected String encodeURLParam(String param) {
		if (null == param) {
			return null; 
		}
		
		try {
			return URLEncoder.encode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
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
	public TextRazorConnection setApiKey(String apiKey) {
		this.apiKey = apiKey;
		return this;
	}

	/**
	 * @return The TextRazor Endpoint used for requests.
	 */
	public String getTextrazorEndpoint() {
		return textrazorEndpoint;
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
	public TextRazorConnection setDoCompression(boolean doCompression) {
		this.doCompression = doCompression;
		return this;
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
	public TextRazorConnection setDoEncryption(boolean doEncryption) {
		this.doEncryption = doEncryption;
		return this;
	}
	
	/**
	 * @param textrazorEndpoint The custom TextRazor Endpoint for requests made by this class.
	 */
	public TextRazorConnection setTextrazorEndpoint(String textrazorEndpoint) {
		this.textrazorEndpoint = textrazorEndpoint;
		return this;
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
	
	/**
	 * @param proxy The java.net.Proxy instance containing proxy settings for this connection.
	 * Example: 
	 * 
	 * textrazor.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_IP, PROXY_PORT));
	 * 
	 * When null, the default JVM proxy settings are used. If they are not set no proxy is used. These can be set with:
	 * 
	 * System.setProperty("https.proxyHost", textRazorProxyHost);
	 * System.setProperty("https.proxyPort", textRazorProxyPort);
	 */
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
}
