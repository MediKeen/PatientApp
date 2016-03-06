package com.medikeen.dataModel;

public class ResetPasswordModel {

	private String tag, email, reset_code, new_password;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReset_code() {
		return reset_code;
	}

	public void setReset_code(String reset_code) {
		this.reset_code = reset_code;
	}

	public String getNew_password() {
		return new_password;
	}

	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}
}
