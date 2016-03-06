package com.medikeen.asyctask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.medikeen.datamodels.NewPatientModel;
import com.google.gson.Gson;

public class AddNewPatienTask extends
		AsyncTask<NewPatientModel, String, String> {

	Activity mAddPatient;
	ProgressDialog pd;
	String jsonResponseString;

	public AddNewPatienTask(Activity activity) {
		mAddPatient = activity;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = new ProgressDialog(mAddPatient);
		pd.setMessage("Please Wait..");
		pd.setCancelable(false);
	}

	@Override
	protected String doInBackground(NewPatientModel... params) {
		// TODO Auto-generated method stub
		NewPatientModel objNewPatientModel = params[0];

		Log.d("username", "" + objNewPatientModel.getPatientsName());
		Log.d("address", "" + objNewPatientModel.getPatientsAddress());
		Log.d("contact", "" + objNewPatientModel.getPatientsContact());
		Log.d("dob", "" + objNewPatientModel.getPatientsDOB());

		Gson gson = new Gson();
		String request = gson.toJson(params[0]);
		Log.d("gson is", "" + request);

		HttpResponse response;

		// Creating HTTP clien
		HttpClient httpClient = new DefaultHttpClient();

		// Creating HttpPost
		// Modify your url
		/*
		 * HttpPost httpPost = new HttpPost(Constants.SERVER_URL
		 * +"/controllername");
		 * 
		 * Log.d("Call to servlet", "Call servlet");
		 * 
		 * // Building post parameters, key and value pair List<NameValuePair>
		 * nameValuePair = new ArrayList<NameValuePair>(2);
		 * nameValuePair.add(new BasicNameValuePair("jsondata", request));
		 * 
		 * Log.d("cac", "NameValuePair"+nameValuePair); // Url Encoding the POST
		 * parameters try { httpPost.setEntity(new
		 * UrlEncodedFormEntity(nameValuePair)); } catch
		 * (UnsupportedEncodingException e) { // writing error to Log
		 * e.printStackTrace(); } try {
		 * 
		 * System.out.println("Executing..."); response =
		 * httpClient.execute(httpPost);
		 * 
		 * HttpEntity entity = response.getEntity(); jsonResponseString =
		 * EntityUtils.toString(entity);
		 * Log.d("Http Response:",jsonResponseString);
		 * 
		 * }catch (ClientProtocolException e) { // writing exception to log
		 * e.printStackTrace();
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		return jsonResponseString;

	}

}
