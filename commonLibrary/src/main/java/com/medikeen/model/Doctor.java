package com.medikeen.model;

import java.util.Date;

import android.content.Context;

public class Doctor {

	private static Doctor sDoctor;
	private Context mContext;
	private int personId;
	private String name;
	private String eMail;
	private String uid;
	private String createdAt;
	private int doctorId;
	private Date lastVisitDate;
	private int loginNumber;

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = Integer.parseInt(doctorId);
	}

	private Doctor(Context context) {
		this.mContext = context.getApplicationContext();
	}

	public static Doctor get(Context context) {
		if (sDoctor == null) {
			sDoctor = new Doctor(context);
		}
		return sDoctor;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Date getLastVisitDate() {
		return lastVisitDate;
	}

	public void setLastVisitDate(Date lastVisitDate) {
		this.lastVisitDate = lastVisitDate;
	}

	public int getLoginNumber() {
		return loginNumber;
	}

	public void setLoginNumber(String loginNumber) {
		this.loginNumber = Integer.parseInt(loginNumber) + 1;
	}

}
