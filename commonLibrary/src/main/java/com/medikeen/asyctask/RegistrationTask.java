package com.medikeen.asyctask;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.medikeen.dataModel.RegisterModel;
import com.medikeen.datamodels.RegistrationModel;
import com.medikeen.datamodels.serialized.RegistrationResponse;
import com.medikeen.util.Constants;
import com.google.gson.Gson;

public class RegistrationTask extends
		AsyncTask<RegisterModel, String, String> {

	Activity activity;
	ProgressDialog pd;
	String jsonResponseString;
	SharedPreferences sp;
	RegisterModel objRegistrationModel;

	public RegistrationTask(Activity registration) {

		activity = registration;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = new ProgressDialog(activity);
		pd.setMessage("Signing Up..!");
		pd.setTitle("Please Wait..");
		pd.show();
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pd.dismiss();
		Log.d("response json is ", "" + result);
	}

	@Override
	protected String doInBackground(RegisterModel... params) {

		/*
		 * try { Thread.sleep(5000); } catch (InterruptedException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); }
		 */

		objRegistrationModel = params[0];

		Log.d("username", "" + objRegistrationModel.getUserName());
		Log.d("password", "" + objRegistrationModel.getPassword());
		Log.d("address", "" + objRegistrationModel.getAddress());
		Log.d("email", "" + objRegistrationModel.getEmailId());
		Log.d("phone", "" + objRegistrationModel.getContactNo());

		Log.d("object", "" + params[0]);
		Gson gson = new Gson();
		String request = gson.toJson(params[0]);
		Log.d("gson is", "" + request);

		HttpResponse response;

		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();

		// Creating HttpPost
		// Modify your url
		HttpPost httpPost = new HttpPost(com.medikeen.util.Constants.SITE
				+ "/android_api/doctor.php");

		Log.d("Call to servlet", "Call servlet");

		// Building post parameters, key and value pair
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		nameValuePair.add(new BasicNameValuePair("tag", "register"));
		nameValuePair.add(new BasicNameValuePair("name", objRegistrationModel
				.getUserName()));
		nameValuePair.add(new BasicNameValuePair("email", objRegistrationModel
				.getEmailId()));
		nameValuePair.add(new BasicNameValuePair("password",
				objRegistrationModel.getPassword()));
		nameValuePair.add(new BasicNameValuePair("address",
				objRegistrationModel.getAddress()));
		nameValuePair.add(new BasicNameValuePair("contact",
				objRegistrationModel.getContactNo()));

		Log.d("cac", "NameValuePair" + nameValuePair);
		// Url Encoding the POST parameters
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// writing error to Log
			e.printStackTrace();
		}
		try {

			System.out.println("Executing...");
			response = httpClient.execute(httpPost);

			HttpEntity entity = response.getEntity();
			jsonResponseString = EntityUtils.toString(entity);
			Log.d("Http Response:", jsonResponseString);

		} catch (ClientProtocolException e) {
			// writing exception to log
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResponseString;

	}

}
