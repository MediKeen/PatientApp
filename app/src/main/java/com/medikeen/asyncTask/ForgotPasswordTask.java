package com.medikeen.asyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.medikeen.dataModel.ResetPasswordModel;
import com.medikeen.datamodels.serialized.ResetPasswordResponse;
import com.medikeen.patient.ForgotPasswordActivity;
import com.medikeen.patient.PasswordResetCodeVerificationActivity;
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

public class ForgotPasswordTask extends AsyncTask<ResetPasswordModel, String, String> {

    private static final String RESET_PASSWORD = "RESET_PASSWORD";
    Activity _forgotPassword;
    ProgressDialog pd;
    String jsonResposnseString;
    ResetPasswordModel objResetModel;
    SharedPreferences sp;
    Editor edit;

    InputStream inputStream;
    StringBuilder stringBuilder;

    public ForgotPasswordTask(ForgotPasswordActivity frogotPasswordActivity) {
        // TODO Auto-generated constructor stub

        _forgotPassword = frogotPasswordActivity;

        sp = _forgotPassword.getSharedPreferences(RESET_PASSWORD, _forgotPassword.MODE_PRIVATE);
        edit = sp.edit();

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

        ResetPasswordResponse response = gson.fromJson(result, ResetPasswordResponse.class);

        if (response.success == 1) {

            edit.putString("RESET_EMAIL", response.email);
            edit.commit();

            Intent resetPasswordIntent = new Intent(_forgotPassword, PasswordResetCodeVerificationActivity.class);
            _forgotPassword.startActivity(resetPasswordIntent);
            _forgotPassword.finish();
        } else {
            Toast.makeText(_forgotPassword, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

        pd.dismiss();
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        pd = new ProgressDialog(_forgotPassword);
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
    protected String doInBackground(ResetPasswordModel... params) {
        // TODO Auto-generated method stub

        objResetModel = params[0];

        Log.e("TAG: ", "TAG: " + objResetModel.getTag());
        Log.e("EMAIL: ", "EMAIL: " + objResetModel.getEmail());

        Gson objGson = new Gson();
        String request = objGson.toJson(params[0]);

        try {
            HashMap<String, String> keyValuePair = new HashMap<>();
            keyValuePair.put("tag", objResetModel.getTag());
            keyValuePair.put("email", objResetModel.getEmail());

            String requestParams = getParams(keyValuePair);

            URL url = new URL(Constants.SERVER_URL
                    + "/android_api/userprofile/updatepassword.php");

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

        return jsonResposnseString;
    }
}