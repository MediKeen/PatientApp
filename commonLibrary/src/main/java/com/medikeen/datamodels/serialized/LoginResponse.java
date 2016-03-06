package com.medikeen.datamodels.serialized;

public class LoginResponse {
	public String tag;
	public int success;
	public int error;
	public String uid;
	public UserResponse user;
	public String error_msg;
	public AddressResponse address;
	public int isActivated;
}
