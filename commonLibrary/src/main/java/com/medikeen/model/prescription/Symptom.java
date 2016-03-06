package com.medikeen.model.prescription;

public class Symptom {
	private String mName;
	private String mDetails;
	private int mSymptomId;
	private int mHistoryId;

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	@Override
	public String toString() {
		return mName;
	}

	public String getDetails() {
		return mDetails;
	}

	public void setDetails(String mDetails) {
		this.mDetails = mDetails;
	}

	public Symptom(String mDetails, int mSymptomId, int mHistoryId) {
		super();
		this.mName = "";
		this.mDetails = mDetails;
		this.mSymptomId = mSymptomId;
		this.mHistoryId = mHistoryId;
	}

	public Symptom() {
		super();
		this.mName = "";
	}
}
