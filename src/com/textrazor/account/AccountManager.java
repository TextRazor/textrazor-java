package com.textrazor.account;

import com.textrazor.AnalysisException;
import com.textrazor.NetworkException;
import com.textrazor.account.model.Account;
import com.textrazor.account.model.AccountResponse;
import com.textrazor.net.TextRazorConnection;

public class AccountManager extends TextRazorConnection {

	public AccountManager(String apiKey) {
		super(apiKey);
	}
	
	public Account getAccount() throws NetworkException, AnalysisException {
		AccountResponse response = sendRequest("account/",
				null, 
				ContentType.JSON,
				"GET",
				AccountResponse.class);
		
		return response.getResponse();
	}

}
