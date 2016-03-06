package com.medikeen.asyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.acra.ACRA;

import com.medikeen.dataModel.FeedbackModel;
import com.medikeen.util.Constants;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class FeedbackUploadTask extends
		AsyncTask<FeedbackModel, String, String> {

	private Activity mActivity;
	private ProgressDialog pd;
	private static final String TAG = "FeedbackUploadTask";

	public FeedbackUploadTask(Activity activity) {
		mActivity = activity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		Log.i(TAG, "Starting FeedbackUploadTask");

		pd = new ProgressDialog(mActivity);
		pd.setMessage("Please wait..");
		pd.setTitle("Uploading");
		pd.setCancelable(false);
		pd.show();
	}

	@Override
	protected void onPostExecute(String result) {
		pd.dismiss();

		Toast.makeText(mActivity, "Your feedback has been uploaded",
				Toast.LENGTH_LONG).show();
		;

		Log.i(TAG, "Ending FeedbackUploadTask");
	}

	@Override
	protected String doInBackground(FeedbackModel... params) {
		// TODO Auto-generated method stub

		String targetURL = Constants.SERVER_URL
				+ "/android_api/feedback/feedback.php";

		Gson gson = new Gson();
		String requestContent = gson.toJson(params[0], FeedbackModel.class);

		Log.i(TAG, "requestContent: " + requestContent);

		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			// connection.setRequestProperty("Content-Length", "" +
			// Integer.toString(urlParameters.getBytes().length));
			// connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(requestContent);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			String responseString = response.toString();

			Log.i(TAG, "responseString: " + responseString);

			return responseString;

		} catch (Exception e) {

			e.printStackTrace();
			ACRA.getErrorReporter().handleSilentException(e);
			return null;

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}
