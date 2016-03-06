package com.medikeen.dataModel;

public class SaveImageDetailsModel {

	private String resourceType;
	private long personId;
	private String recepientName;
	private String recepientAddress;
	private String recepientNumber;
	private String offerType;
	
	public SaveImageDetailsModel(String resourceType, long personId, String recepientName, String recepientAddress, String recepientNumber, String offerType){
		this.resourceType = resourceType;
		this.personId = personId;
		this.recepientName = recepientName;
		this.recepientAddress = recepientAddress;
		this.recepientNumber = recepientNumber;
		this.offerType = offerType;
	}

	public String getResourceType() {
		return resourceType;
	}

	public long getPersonId() {
		return personId;
	}

	public String getRecepientName() {
		return recepientName;
	}

	public String getRecepientAddress() {
		return recepientAddress;
	}

	public String getOfferType() {
		return offerType;
	}

	public String getRecepientNumber() {
		return recepientNumber;
	}
}
