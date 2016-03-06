package com.medikeen.model.prescription;

public class Disease {

	private int diseaseId;
	private int historyId;
	private String disease;

	public Disease(int diseaseId, int historyId, String disease) {
		super();
		this.diseaseId = diseaseId;
		this.historyId = historyId;
		this.disease = disease;
	}

	public int getDiseaseId() {
		return diseaseId;
	}

	public void setDiseaseId(int diseaseId) {
		this.diseaseId = diseaseId;
	}

	public int getHistoryId() {
		return historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

}
