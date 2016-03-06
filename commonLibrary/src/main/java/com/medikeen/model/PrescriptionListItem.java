package com.medikeen.model;

public class PrescriptionListItem {

	private int patientId;
	private int personId;
	private int doctorId;
	private int historyId;
	private String dateCreated;
	private boolean saved;

	public PrescriptionListItem(int patientId, int personId, int doctorId,
			int historyId, String dateCreated, String saved) {
		this.patientId = patientId;
		this.personId = personId;
		this.doctorId = doctorId;
		this.historyId = historyId;
		this.dateCreated = dateCreated;

		if (saved == "Y") {
			this.saved = true;
		} else if (saved == "N") {
			this.saved = false;
		}

	}

	public int getPatientId() {
		return patientId;
	}

	public int getPersonId() {
		return personId;
	}

	public int getDictorId() {
		return doctorId;
	}

	public int getHistoryId() {
		return historyId;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public boolean isSaved() {
		return saved;
	}

}
