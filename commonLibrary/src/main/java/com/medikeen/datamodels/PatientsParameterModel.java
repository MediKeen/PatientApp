package com.medikeen.datamodels;

public class PatientsParameterModel {

	private String ParameterName;
	private String patientsName = "";
	private String patientsAddress = "";
	private String patientsContact = "";
	private String patientsLastVisited = "";
	private String patientsId = "";
	private String treatmentDate = "";
	private String prescriptionImage = "";

	public String getPrescriptionImage() {
		return prescriptionImage;
	}

	public void setPrescriptionImage(String prescriptionImage) {
		this.prescriptionImage = prescriptionImage;
	}

	public String getTreatmentDate() {
		return treatmentDate;
	}

	public void setTreatmentDate(String treatmentDate) {
		this.treatmentDate = treatmentDate;
	}

	public String getPatientsId() {
		return patientsId;
	}

	public void setPatientsId(String patientsId) {
		this.patientsId = patientsId;
	}

	public String getPatientsName() {
		return patientsName;
	}

	public void setPatientsName(String patientsName) {
		this.patientsName = patientsName;
	}

	public String getPatientsAddress() {
		return patientsAddress;
	}

	public void setPatientsAddress(String patientsAddress) {
		this.patientsAddress = patientsAddress;
	}

	public String getPatientsContact() {
		return patientsContact;
	}

	public void setPatientsContact(String patientsContact) {
		this.patientsContact = patientsContact;
	}

	public String getPatientsLastVisited() {
		return patientsLastVisited;
	}

	public void setPatientsLastVisited(String patientsLastVisited) {
		this.patientsLastVisited = patientsLastVisited;
	}

	public String getParameterName() {
		return ParameterName;
	}

	public void setParameterName(String parameterName) {
		ParameterName = parameterName;
	}
}
