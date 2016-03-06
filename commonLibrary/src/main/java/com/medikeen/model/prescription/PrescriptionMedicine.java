package com.medikeen.model.prescription;

public class PrescriptionMedicine {

	private int medicineDataId;
	private int medicineId;
	// private int prescriptionId;
	private int historyId;
	private String medicineName;

	private boolean morning;
	private boolean afternoon;
	private boolean evening;
	private boolean night;

	public PrescriptionMedicine(int medicineDataId, int medicineId,
			int historyId, String medicineName, String morning,
			String afternoon, String evening, String night) {
		super();
		this.medicineDataId = medicineDataId;
		this.medicineId = medicineId;
		this.historyId = historyId;
		this.medicineName = medicineName;
		if (morning.equals("Y"))
			this.morning = true;
		else if (morning.equals("N"))
			this.morning = false;
		if (afternoon.equals("Y"))
			this.afternoon = true;
		else if (afternoon.equals("N"))
			this.afternoon = false;
		if (evening.equals("Y"))
			this.evening = true;
		else if (evening.equals("N"))
			this.evening = false;
		if (night.equals("Y"))
			this.night = true;
		else if (night.equals("N"))
			this.night = false;
	}

	public int getMedicineDataId() {
		return medicineDataId;
	}

	public void setMedicineDataId(int medicineDataId) {
		this.medicineDataId = medicineDataId;
	}

	public int getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(int medicineId) {
		this.medicineId = medicineId;
	}

	public int getHistoryId() {
		return historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public boolean isMorning() {
		return morning;
	}

	public void setMorning(boolean morning) {
		this.morning = morning;
	}

	public boolean isAfternoon() {
		return afternoon;
	}

	public void setAfternoon(boolean afternoon) {
		this.afternoon = afternoon;
	}

	public boolean isEvening() {
		return evening;
	}

	public void setEvening(boolean evening) {
		this.evening = evening;
	}

	public boolean isNight() {
		return night;
	}

	public void setNight(boolean night) {
		this.night = night;
	}

}
