package com.medikeen.model.prescription;

public class Parameter {
	public Parameter(int mParameterId, int mHistoryId, String mParameterType,
			String mParameterValue) {
		super();
		this.mParameterId = mParameterId;
		this.mHistoryId = mHistoryId;
		this.mParameterType = mParameterType;
		this.mParameterValue = mParameterValue;
	}

	private int mParameterId;
	private int mHistoryId;
	private String mParameterType;
	private String mParameterValue;

	public int getmParameterId() {
		return mParameterId;
	}

	public void setmParameterId(int mParameterId) {
		this.mParameterId = mParameterId;
	}

	public int getmHistoryId() {
		return mHistoryId;
	}

	public void setmHistoryId(int mHistoryId) {
		this.mHistoryId = mHistoryId;
	}

	public String getmParameterType() {
		return mParameterType;
	}

	public void setmParameterType(String mParameterType) {
		this.mParameterType = mParameterType;
	}

	public String getmParameterValue() {
		return mParameterValue;
	}

	public void setmParameterValue(String mParameterValue) {
		this.mParameterValue = mParameterValue;
	}

}
