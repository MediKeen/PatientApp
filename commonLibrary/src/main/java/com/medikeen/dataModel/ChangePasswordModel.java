package com.medikeen.dataModel;

public class ChangePasswordModel {

	private String email, currentPassword, newPassword;

	public ChangePasswordModel(String email, String currentPassword, String newPassword) {
		super();
		this.email = email;
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
