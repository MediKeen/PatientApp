package com.medikeen.dataModel;

public class HistoryModel {

	private String tag, success, error, personId;

	private String resourceId, resourceType, recepientName, recepientAddress, recepientNumber, offerType,
			isImageUploaded, isEmailSent, created_Date, updatedDate;

	public HistoryModel(String personId, String resourceId, String resourceType, String recepientName,
			String recepientAddress, String recepientNumber, String offerType, String isImageUploaded,
			String isEmailSent, String created_Date, String updatedDate) {
		super();
		this.personId = personId;
		this.resourceId = resourceId;
		this.resourceType = resourceType;
		this.recepientName = recepientName;
		this.recepientAddress = recepientAddress;
		this.recepientNumber = recepientNumber;
		this.offerType = offerType;
		this.isImageUploaded = isImageUploaded;
		this.isEmailSent = isEmailSent;
		this.created_Date = created_Date;
		this.updatedDate = updatedDate;
	}

	public HistoryModel(String tag, String success, String error, String personId, String resourceId,
			String resourceType, String recepientName, String recepientAddress, String recepientNumber,
			String offerType, String isImageUploaded, String isEmailSent, String created_Date, String updatedDate) {
		super();
		this.tag = tag;
		this.success = success;
		this.error = error;
		this.personId = personId;
		this.resourceId = resourceId;
		this.resourceType = resourceType;
		this.recepientName = recepientName;
		this.recepientAddress = recepientAddress;
		this.recepientNumber = recepientNumber;
		this.offerType = offerType;
		this.isImageUploaded = isImageUploaded;
		this.isEmailSent = isEmailSent;
		this.created_Date = created_Date;
		this.updatedDate = updatedDate;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getRecepientName() {
		return recepientName;
	}

	public void setRecepientName(String recepientName) {
		this.recepientName = recepientName;
	}

	public String getRecepientAddress() {
		return recepientAddress;
	}

	public void setRecepientAddress(String recepientAddress) {
		this.recepientAddress = recepientAddress;
	}

	public String getRecepientNumber() {
		return recepientNumber;
	}

	public void setRecepientNumber(String recepientNumber) {
		this.recepientNumber = recepientNumber;
	}

	public String getOfferType() {
		return offerType;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public String getIsImageUploaded() {
		return isImageUploaded;
	}

	public void setIsImageUploaded(String isImageUploaded) {
		this.isImageUploaded = isImageUploaded;
	}

	public String getIsEmailSent() {
		return isEmailSent;
	}

	public void setIsEmailSent(String isEmailSent) {
		this.isEmailSent = isEmailSent;
	}

	public String getCreated_Date() {
		return created_Date;
	}

	public void setCreated_Date(String created_Date) {
		this.created_Date = created_Date;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

}
