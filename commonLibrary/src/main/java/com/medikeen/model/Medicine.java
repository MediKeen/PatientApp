package com.medikeen.model;

public class Medicine {
	int medicineId;
	String medicineName;
	String type;
	String dose;
	String Manufacturer;

	public Medicine(int medicineId, String medicineName, String type,
			String dose, String manufacturer) {
		super();
		this.medicineId = medicineId;
		this.medicineName = medicineName;
		this.type = type;
		this.dose = dose;
		Manufacturer = manufacturer;
	}

	public int getMedicineId() {
		return medicineId;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public String getType() {
		return type;
	}

	public String getDose() {
		return dose;
	}

	public String getManufacturer() {
		return Manufacturer;
	}

}
