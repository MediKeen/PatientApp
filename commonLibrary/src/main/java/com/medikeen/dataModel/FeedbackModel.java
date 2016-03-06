package com.medikeen.dataModel;

import com.medikeen.datamodels.User;

public class FeedbackModel {

	private String title;
	private String description;
	private User user;
	
	public FeedbackModel(User user, String title, String description) {
		this.user = user;
		this.title = title;
		this.description = description;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}

	public User getUser() {
		return user;
	}	
}
