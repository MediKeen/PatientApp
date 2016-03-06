package com.medikeen.datamodels;

import java.util.ArrayList;

import android.util.Log;

public class MedicinesClinicModel {

	private String MedicineName;
	private String mg;
	private String medicinesId = "";
	public static ArrayList<String> medicinesName = new ArrayList<String>();

	public MedicinesClinicModel(String medicinename) {
		this.MedicineName = medicinename;
	}

	public MedicinesClinicModel() {
	}

	public void setUserNames(String medicinedNames) {
		Log.d("cac", medicinedNames);
		medicinesName.add(medicinedNames);
	}

	public static ArrayList<String> getUserNames() {
		return medicinesName;
	}

	public String getMedicinesId() {
		return medicinesId;
	}

	public void setMedicinesId(String medicinesId) {
		this.medicinesId = medicinesId;
	}

	public String getMedicineName() {
		return MedicineName;
	}

	public void setMedicineName(String medicineName) {
		MedicineName = medicineName;
	}

	public String getMg() {
		return mg;
	}

	public void setMg(String mg) {
		this.mg = mg;
	}

	// public static MedicinesClinicModel getTripuserlist(int index)
	// {
	// if(index < tripuserList.size())
	// {
	//
	// return tripuserList.get(index);
	//
	// }
	// return null;
	// }
}
