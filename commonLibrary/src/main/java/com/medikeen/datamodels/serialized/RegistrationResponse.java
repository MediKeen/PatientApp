package com.medikeen.datamodels.serialized;

public class RegistrationResponse {
	public int error;
	public String error_msg;
	public int success;
	public String uid;
	public UserResponse user;
	public int isActivated;
}
