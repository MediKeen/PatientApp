package com.medikeen.datamodels;

import android.R.string;

public class User {
	private String firstName;
	private String lastName;
	private long personId;
	private String address;
	private String phoneNo;
	private String emailAddress;
	
	public User(long personId2, String firstName2, String lastName2, String emailAddress2, String address2, String phoneNo2){
		
		this.personId = personId2;
		this.firstName = firstName2;
		this.lastName = lastName2;
		this.emailAddress = emailAddress2;
		this.address = address2;
		this.phoneNo = phoneNo2;
		
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
