package com.medikeen.util;

import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class ConnectionDetector extends AsyncTask<Void, Boolean, Boolean> {

	@Override
	protected Boolean doInBackground(Void... params) {

		try {
			URL url = new URL("http://www.medikeen.com/android_api/index.php");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-type", "application/json");

			int responseCode = conn.getResponseCode();

			if (responseCode == 200) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			Log.e("STRING ENTITY ERROR: ", "STRING ENTITY ERROR: " + e);
			return false;
		}
	}
}
