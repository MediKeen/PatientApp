package com.medikeen.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.medikeen.library.JSONParser;
import com.medikeen.util.Constants;

public class PatientController {
	private JSONParser jsonParser;
	Context mContext;
	//private static String patientURL = Messages.getString("UserFunctions.site") + Messages.getString("UserFunctions.patient_extn"); //$NON-NLS-1$
	private String site;
	private String patientExtension;
	private String debuggerExtension;
	private static String login_tag = "login"; //$NON-NLS-1$
	private static String register_tag = "register"; //$NON-NLS-1$

	// constructor
	public PatientController(Context context) {
		jsonParser = new JSONParser();
		mContext = context.getApplicationContext();
		site = Constants.SITE;
		patientExtension = Constants.PATIENT_EXTENSION;
		debuggerExtension = Constants.DEBUGGER_EXTENSION;
	}

	public JSONObject registerPatient(String name, String email,
			String password, String address, String contact) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag)); //$NON-NLS-1$
		params.add(new BasicNameValuePair("name", name)); //$NON-NLS-1$
		params.add(new BasicNameValuePair("email", email)); //$NON-NLS-1$
		params.add(new BasicNameValuePair("password", password)); //$NON-NLS-1$
		params.add(new BasicNameValuePair("address", address)); //$NON-NLS-1$
		params.add(new BasicNameValuePair("telephone", contact)); //$NON-NLS-1$

		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(site + patientExtension
				+ "?" + debuggerExtension, params);
		// return json
		return json;
	}

	public JSONObject loginPatient(String email) {

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));

		JSONObject json = jsonParser.getJSONFromUrl(site + patientExtension
				+ "?" + debuggerExtension, params);

		return json;
	}

}
