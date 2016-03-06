package com.medikeen.datamodels;

public class NewPatientModel {

	private String PatientsName;
	private String PatientsAddress;
	private String PatientsContact;
	private String PatientsDOB;
	private String PatientsDP;
	private String Gender;
	private String PatientsFirstName;

	/**
	 * @return the patientsFirstName
	 */
	public String getPatientsFirstName() {
		return PatientsFirstName;
	}

	/**
	 * @param patientsFirstName
	 *            the patientsFirstName to set
	 */
	public void setPatientsFirstName(String patientsFirstName) {
		PatientsFirstName = patientsFirstName;
	}

	private String PatientsLastName;

	/**
	 * @return the patientsLastName
	 */
	public String getPatientsLastName() {
		return PatientsLastName;
	}

	/**
	 * @param patientsLastName
	 *            the patientsLastName to set
	 */
	public void setPatientsLastName(String patientsLastName) {
		PatientsLastName = patientsLastName;
	}

	public String getPatientsName() {
		return PatientsName;
	}

	public void setPatientsName(String patientsName) {
		PatientsName = patientsName;
	}

	public String getPatientsAddress() {
		return PatientsAddress;
	}

	public void setPatientsAddress(String patientsAddress) {
		PatientsAddress = patientsAddress;
	}

	public String getPatientsContact() {
		return PatientsContact;
	}

	public void setPatientsContact(String patientsContact) {
		PatientsContact = patientsContact;
	}

	public String getPatientsDOB() {
		return PatientsDOB;
	}

	public void setPatientsDOB(String patientsDOB) {
		PatientsDOB = patientsDOB;
	}

	public String getPatientsDP() {
		return PatientsDP;
	}

	public void setPatientsDP(String patientsDP) {
		PatientsDP = patientsDP;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

}
