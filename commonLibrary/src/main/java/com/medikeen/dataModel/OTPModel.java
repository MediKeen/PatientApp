package com.medikeen.dataModel;

public class OTPModel {

	private String tag, otp, authorizationKey;

	public OTPModel() {
		// TODO Auto-generated constructor stub
	}
	
	public OTPModel(String tag, String otp, String authorizationKey) {
		super();
		this.tag = tag;
		this.otp = otp;
		this.authorizationKey = authorizationKey;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getAuthorizationKey() {
		return authorizationKey;
	}

	public void setAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
	}

}
