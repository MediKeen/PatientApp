package com.medikeen.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.medikeen.library.JSONParser;
import com.medikeen.model.Medicine;
import com.medikeen.util.Constants;

public class MedicineController {
	private JSONParser jsonParser;
	Context mContext;
	private String site;
	private String medicineExtension;
	private String debuggerExtension;

	// constructor
	public MedicineController(Context context) {
		jsonParser = new JSONParser();
		mContext = context.getApplicationContext();
		site = Constants.SITE;
		medicineExtension = Constants.MEDICINE_EXTENSION;
		debuggerExtension = Constants.DEBUGGER_EXTENSION;
	}

	public ArrayList<Medicine> getMedicineAllList()
			throws NumberFormatException, JSONException {
		JSONObject json, jsonMedicine;
		JSONArray jsonMedicineList;
		Medicine tempMedicine;
		ArrayList<Medicine> medicineList = new ArrayList<Medicine>();
		int length;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "get_medicine_all"));

		json = jsonParser.getJSONFromUrl(site + medicineExtension, params);

		if (Integer.parseInt(json.getString("success")) == 1) {
			jsonMedicineList = json.getJSONArray("medicines");
			length = jsonMedicineList.length();

			for (int i = 0; i < length; i++) {
				jsonMedicine = jsonMedicineList.getJSONObject(i);
				tempMedicine = new Medicine(jsonMedicine.getInt("medicine_id"),
						jsonMedicine.getString("medicine_name"),
						jsonMedicine.getString("type"),
						jsonMedicine.getString("dose"),
						jsonMedicine.getString("manufacturer"));
				medicineList.add(tempMedicine);
			}
			return medicineList;
		} else {
			return medicineList;
		}

	}

}
