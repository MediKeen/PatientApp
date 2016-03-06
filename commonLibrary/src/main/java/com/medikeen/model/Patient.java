package com.medikeen.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.medikeen.util.Constants;

public class Patient {

	private static Patient sPatient;
	private Context mContext;
	private int personId;
	private int patientId;
	private String name;
	private String eMail;
	private String uid;
	private String createdAt;

	private Patient(Context context) {
		this.mContext = context.getApplicationContext();
	}

	public static Patient get(Context context) {
		if (sPatient == null) {
			sPatient = new Patient(context);
		}
		return sPatient;
	}

	public static Patient getNewPatient(Context context) {
		if (sPatient != null) {
			sPatient = null;
			System.gc();
		}
		sPatient = new Patient(context);
		return sPatient;
	}

	public Patient getPatientFromJSON(JSONObject json) throws JSONException {

		JSONObject json_user = json.getJSONObject("user");

		name = json_user.getString(Constants.key_name);
		eMail = json_user.getString(Constants.key_email);
		personId = Integer.parseInt(json_user.getString(Constants.key_person_id));
		patientId = Integer.parseInt(json_user.getString(Constants.key_patient_id));

		return sPatient;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getName() {
		return name;
	}

	public int getPatientId() {
		return patientId;
	}

}
