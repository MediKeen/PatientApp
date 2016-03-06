package com.medikeen.datamodels;

public class PatientsMedicinesModel {

	static String medicineName;
	static String noOfDosage;
	static Boolean morning;
	static Boolean afternoon;
	static Boolean eve;
	static Boolean night;

	public static String getMedicineName() {
		return medicineName;
	}

	public static void setMedicineName(String medicineName) {
		PatientsMedicinesModel.medicineName = medicineName;
	}

	public static String getNoOfDosage() {
		return noOfDosage;
	}

	public static void setNoOfDosage(String noOfDosage) {
		PatientsMedicinesModel.noOfDosage = noOfDosage;
	}

	public static Boolean getMorning() {
		return morning;
	}

	public static void setMorning(Boolean morning) {
		PatientsMedicinesModel.morning = morning;
	}

	public static Boolean getAfternoon() {
		return afternoon;
	}

	public static void setAfternoon(Boolean afternoon) {
		PatientsMedicinesModel.afternoon = afternoon;
	}

	public static Boolean getEve() {
		return eve;
	}

	public static void setEve(Boolean eve) {
		PatientsMedicinesModel.eve = eve;
	}

	public static Boolean getNight() {
		return night;
	}

	public static void setNight(Boolean night) {
		PatientsMedicinesModel.night = night;
	}

}
