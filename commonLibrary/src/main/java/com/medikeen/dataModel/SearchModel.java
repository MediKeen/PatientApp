package com.medikeen.dataModel;

public class SearchModel {

	public String searchIds;
	public static String fromActivity;

	/**
	 * @return the fromActivity
	 */
	public static String getFromActivity() {
		return fromActivity;
	}

	/**
	 * @param fromActivity the fromActivity to set
	 */
	public static void setFromActivity(String fromActivity) {
		SearchModel.fromActivity = fromActivity;
	}

	/**
	 * @return the searchIds
	 */
	public String getSearchIds() {
		return searchIds;
	}

	/**
	 * @param searchIds the searchIds to set
	 */
	public void setSearchIds(String searchIds) {
		this.searchIds = searchIds;
	}
	
}