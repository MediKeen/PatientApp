package com.medikeen.asyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.medikeen.dataModel.OTPModel;
import com.medikeen.datamodels.serialized.OTPResponse;
import com.medikeen.patient.Login;
import com.medikeen.patient.OtpActivity;
import com.medikeen.util.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

public class OTPVerificationAsyncTask extends
        AsyncTask<OTPModel, String, String> {

    private static final String RESET_PASSWORD = "RESET_PASSWORD";
    Activity _passwordReset;
    ProgressDialog pd;
    String jsonResposnseString;
    OTPModel objOTPModel;
    InputStream inputStream;
    StringBuilder stringBuilder;

    public OTPVerificationAsyncTask(OtpActivity otpAsyncTask) {
        // TODO Auto-generated constructor stub

        _passwordReset = otpAsyncTask;

    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);

        Gson gson = new Gson();

        OTPResponse response = gson.fromJson(result, OTPResponse.class);

        if (response.success == 1) {

            Intent resetPasswordIntent = new Intent(_passwordReset,
                    Login.class);
            resetPasswordIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            _passwordReset.startActivity(resetPasswordIntent);
            _passwordReset.finish();
        } else {
            Toast.makeText(_passwordReset, "Something went wrong",
                    Toast.LENGTH_SHORT).show();
        }

        pd.dismiss();
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        pd = new ProgressDialog(_passwordReset);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.show();
    }

    private String getParams(HashMap<String, String> keyValuePair) {
        StringBuilder stringBuilder = new StringBuilder();
        String params = null;

        Iterator<String> keyIterator = keyValuePair.keySet().iterator();

        while (keyIterator.hasNext()) {
            String key = (String) keyIterator.next();
            String value = keyValuePair.get(key);

            stringBuilder.append(key + "=" + value + "&");
        }

        params = stringBuilder.substring(0, stringBuilder.length() - 1);

        return params;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected String doInBackground(OTPModel... params) {
        // TODO Auto-generated method stub

        objOTPModel = params[0];

        Gson objGson = new Gson();
        String request = objGson.toJson(params[0]);

        try {
            HashMap<String, String> keyValuePair = new HashMap<>();
            keyValuePair.put("tag", objOTPModel.getTag());
            keyValuePair.put("otp", objOTPModel.getOtp());
            keyValuePair.put("authorizationKey",
                    objOTPModel.getAuthorizationKey());

            String requestParams = getParams(keyValuePair);

            URL url = new URL(Constants.SERVER_URL
                    + "/android_api/patient.php");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-type",
                    "application/x-www-form-urlencoded");

            OutputStream os = conn.getOutputStream();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    os, "UTF-8"));
            writer.write(requestParams);

            writer.flush();
            writer.close();
            os.close();

            inputStream = conn.getInputStream();

        } catch (Exception e) {
            Log.e("STRING ENTITY ERROR: ", "STRING ENTITY ERROR: " + e);
        }

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream), 1000);
            stringBuilder = new StringBuilder();
            stringBuilder.append(reader.readLine() + "\n");

            String line = "0";
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            inputStream.close();
            jsonResposnseString = stringBuilder.toString();

        } catch (Exception e) {
            Log.e("STRING BUILDER ERROR: ", "STRING BUILDER ERROR: " + e);
        }

//        HttpResponse response;
//
//        // Creating Http client
//        HttpClient httpclient = new DefaultHttpClient();
//
//        HttpPost httpPost = new HttpPost(Constants.SERVER_URL
//                + "/android_api/patient.php");
//        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
//        nameValuePair.add(new BasicNameValuePair("tag", objOTPModel.getTag()));
//        nameValuePair.add(new BasicNameValuePair("otp", objOTPModel.getOtp()));
//        nameValuePair.add(new BasicNameValuePair("authorizationKey",
//                objOTPModel.getAuthorizationKey()));
//
//        // URl Encoding the POST parametrs
//
//        try {
//            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
//        } catch (UnsupportedEncodingException e) {
//            // writing error to Log
//            e.printStackTrace();
//        }
//
//        // making http request
//        try {
//            System.out.println("Executing");
//            response = httpclient.execute(httpPost);
//            System.out.println("check response" + response.toString());
//            HttpEntity entity = response.getEntity();
//            jsonResposnseString = EntityUtils.toString(entity);
//
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }

        return jsonResposnseString;
    }
}