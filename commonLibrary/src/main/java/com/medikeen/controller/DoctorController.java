package com.medikeen.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.medikeen.library.DatabaseHandler;
import com.medikeen.library.JSONParser;
import com.medikeen.model.Doctor;
import com.medikeen.util.Constants;

public class DoctorController {

	private JSONParser jsonParser;
	private Context mContext;
	private String site;
	private String doctorExtension;
	private String registerExtension;
	private static String login_tag = "login"; //$NON-NLS-1$
	private static String register_tag = "register"; //$NON-NLS-1$

	// constructor
	public DoctorController(Context context) {
		jsonParser = new JSONParser();
		mContext = context.getApplicationContext();
		site = Constants.SITE;
		doctorExtension = Constants.DOCTOR_EXTENSION;
		registerExtension = Constants.DOCTOR_EXTENSION;
	}

	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn() {
		DatabaseHandler db = new DatabaseHandler(mContext);
		int count = db.getRowCount();
		if (count > 0) {
			// user logged in
			return true;
		}
		return false;
	}

	public Doctor getUser() {

		if (isUserLoggedIn()) {

			Doctor d = Doctor.get(mContext);
			HashMap<String, String> user = new HashMap<String, String>();
			DatabaseHandler db = new DatabaseHandler(mContext);
			user = db.getUserDetails();
			d.setName(user.get("name"));
			d.seteMail(user.get("email"));
			d.setPersonId(Integer.parseInt(user.get("person_id")));
			d.setUid(user.get("uid"));
			d.setCreatedAt(user.get("created_at"));
			d.setDoctorId(user.get("doctor_id"));
			d.setLoginNumber(user.get("login_number"));
			String dateString = user.get("last_login");
			if (dateString != "null") {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				try {
					d.setLastVisitDate(dateFormat.parse(dateString));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				d.setLastVisitDate(null);
			}

			return d;
		} else
			return null;
	}

	/**
	 * function make Login Request
	 * 
	 * @param email
	 * @param password
	 * */
	public JSONObject loginUser(String email, String password) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag)); //$NON-NLS-1$
		params.add(new BasicNameValuePair("email", email)); //$NON-NLS-1$
		params.add(new BasicNameValuePair("password", password)); //$NON-NLS-1$
		JSONObject json = jsonParser.getJSONFromUrl(site + doctorExtension,
				params);
		// Log.e("JSON", json.toString());
		return json;
	}

	/**
	 * Function to logout user Reset Database
	 * */
	public boolean logoutUser(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}

	/**
	 * function make Login Request
	 * 
	 * @param name
	 * @param email
	 * @param password
	 * */
	public JSONObject registerUser(String name, String email, String password,
			String address, String contact) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag)); //$NON-NLS-1$
		params.add(new BasicNameValuePair("name", name)); //$NON-NLS-1$
		params.add(new BasicNameValuePair("email", email)); //$NON-NLS-1$
		params.add(new BasicNameValuePair("password", password)); //$NON-NLS-1$
		params.add(new BasicNameValuePair("address", address)); //$NON-NLS-1$
		params.add(new BasicNameValuePair("contact", contact)); //$NON-NLS-1$

		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(site + doctorExtension,
				params);
		// return json
		return json;
	}

}
