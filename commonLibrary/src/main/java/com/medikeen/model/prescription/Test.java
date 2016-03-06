package com.medikeen.model.prescription;

public class Test {
	private String mName;
	private int mTestId;
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

	public Test(String mName, int mTestId, int mHistoryId) {
		super();
		this.mName = mName;
		this.mTestId = mTestId;
		this.mHistoryId = mHistoryId;
	}

	public Test() {
		super();
		this.mName = "";
	}
}
