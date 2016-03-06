package com.medikeen.model;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.medikeen.controller.PrescriptionController;
import com.medikeen.model.prescription.Disease;
import com.medikeen.model.prescription.Note;
import com.medikeen.model.prescription.Parameter;
import com.medikeen.model.prescription.PrescriptionMedicine;
import com.medikeen.model.prescription.Symptom;
import com.medikeen.model.prescription.Test;
import com.medikeen.util.Constants;

public class Prescription {

	private int patientId;
	private int doctorId;
	private int historyId;
	private String dateCreated;
	private Context mContext;
	private ArrayList<Symptom> mSymptoms;
	private ArrayList<Parameter> mParamters;
	private ArrayList<Disease> mDiseases;
	private ArrayList<PrescriptionMedicine> mMedicine;
	private ArrayList<Test> mTests;
	private Note mNote;
	private static Prescription mPrescription;
	private PrescriptionController prescriptionController;

	private Prescription(Context context) {
		mContext = context.getApplicationContext();
		prescriptionController = new PrescriptionController(mContext);
	}

	public static Prescription get(Context context) {
		if (mPrescription == null) {
			mPrescription = new Prescription(context);
		}
		return mPrescription;
	}

	public static Prescription getNewPrescription(Context context) {
		if (mPrescription != null) {
			mPrescription = null;
		}
		mPrescription = new Prescription(context);
		return mPrescription;
	}

	public void getPrescriptionFromJSON(JSONObject json)
			throws NumberFormatException, JSONException {
		patientId = Integer.parseInt(json.getString(Constants.key_patient_id));
		doctorId = Integer.parseInt(json.getString(Constants.key_doctor_id));
		historyId = Integer.parseInt(json.getString(Constants.key_history_id));
		dateCreated = json.getString(Constants.key_date_modified);
	}

	public ArrayList<Symptom> getSymptoms() {

		if (mSymptoms == null) {
			try {
				mSymptoms = prescriptionController.getSymptomList(historyId);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mSymptoms;
	}

	public ArrayList<Parameter> getParameters() {

		if (mParamters == null) {
			try {
				mParamters = prescriptionController.getParameterList(historyId);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return mParamters;
	}

	public ArrayList<Disease> getDisease() {

		if (mDiseases == null) {
			try {
				mDiseases = prescriptionController
						.getDiseasesDiagnosedList(historyId);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mDiseases;
	}

	public ArrayList<PrescriptionMedicine> getMedicine() {
		if (mMedicine == null) {
			try {
				mMedicine = prescriptionController
						.getPrescriptionMedicineList(historyId);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mMedicine;
	}

	public ArrayList<Test> getTests() {
		if (mTests == null) {
			try {
				mTests = prescriptionController.getTestList(historyId);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mTests;
	}

	public Note getNote() {
		return mNote;
	}

	public int getHistoryId() {
		return historyId;
	}

	public void setSymptoms(ArrayList<Symptom> mSymptoms) {
		this.mSymptoms = mSymptoms;
	}

	public void setParameters(ArrayList<Parameter> mParameter) {
		this.mParamters = mParameter;
	}

	public void setDiseases(ArrayList<Disease> mDiseases) {
		this.mDiseases = mDiseases;
	}

	public ArrayList<PrescriptionMedicine> getPrescriptionMedicine() {
		return mMedicine;
	}

	public void setPrescriptionedicine(
			ArrayList<PrescriptionMedicine> prescriptionMedicine) {
		this.mMedicine = prescriptionMedicine;
	}

}
