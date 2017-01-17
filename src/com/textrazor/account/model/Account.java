package com.textrazor.account.model;

public class Account {
	
	private String plan;
	
	private int concurrentRequestLimit;
	
	private int concurrentRequestsUsed;

	private int planDailyRequestsIncluded;

	private int requestsUsedToday;

	/**
	 * @return The ID of your current subscription plan.
	 */	
	public String getPlan() {
		return plan;
	}

	/**
	 * @return The maximum number of requests your account can make at the same time.
	 */	
	public int getConcurrentRequestLimit() {
		return concurrentRequestLimit;
	}

	/**
	 * @return The number of requests currently being processed by your account.
	 */
	public int getConcurrentRequestsUsed() {
		return concurrentRequestsUsed;
	}

	/**
	 * @return The daily number of requests included with your subscription plan.
	 */
	public int getPlanDailyRequestsIncluded() {
		return planDailyRequestsIncluded;
	}

	/**
	 * @return The total number of requests that have been made today.
	 */
	public int getRequestsUsedToday() {
		return requestsUsedToday;
	}
	
}
