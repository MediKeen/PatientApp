package com.medikeen.asyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.medikeen.R;
import com.medikeen.dataModel.LoginModel;
import com.medikeen.datamodels.serialized.LoginResponse;
import com.medikeen.datamodels.serialized.UserResponse;
import com.medikeen.patient.Login;
import com.medikeen.patient.MainActivity;
import com.medikeen.patient.OtpActivity;
import com.medikeen.util.Constants;
import com.medikeen.util.SessionManager;

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

public class LoginTask extends AsyncTask<LoginModel, String, String> {

    Activity mLogin;
    ProgressDialog pd;
    String jsonResponseString;
    LoginModel objLoginModel;
    SharedPreferences sp;

    InputStream inputStream;
    StringBuilder stringBuilder;

    public LoginTask(Login login) {
        mLogin = login;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.e("response json is ", "" + result);
        pd.dismiss();

        Gson gson = new Gson();

        LoginResponse response = gson.fromJson(result, LoginResponse.class);

        if (response.success == 1) {

            if (response.isActivated == 1) {
                SessionManager sessionManager = new SessionManager(
                        mLogin.getApplicationContext());
                UserResponse user = response.user;

                sessionManager.createLoginSession(true, user.person_id, user.name,
                        "", user.email, response.address.house_no, user.telephone);

                // Show a welcome message to the user through toast
                Toast.makeText(mLogin, "Welcome " + user.name, Toast.LENGTH_SHORT)
                        .show();

//                Intent intent = new Intent(mLogin,
//                        LandingActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                mLogin.startActivity(intent);
//                mLogin.finish();


                Intent intent = new Intent(mLogin,
                        MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mLogin.startActivity(intent);
                mLogin.finish();

            } else {
                Intent intent = new Intent(mLogin,
                        OtpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mLogin.startActivity(intent);
                mLogin.finish();
            }

        } else {

            EditText mPassword = (EditText) mLogin
                    .findViewById(R.id.editTextPasswordLogin);
            mPassword.setError(response.error_msg);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(mLogin);
        pd.setMessage("Please wait while we are signing you in..");
        pd.setTitle("Signing in");
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
    protected String doInBackground(LoginModel... params) {

        objLoginModel = params[0];

        Log.d("username", "" + objLoginModel.getmUsername());
        Log.d("password", "" + objLoginModel.getmPassword());

        Gson gson = new Gson();
        String request = gson.toJson(params[0]);
        Log.d("gson is", "" + request);

        try {
            HashMap<String, String> keyValuePair = new HashMap<>();
            keyValuePair.put("tag", "login");
            keyValuePair.put("email", objLoginModel
                    .getmUsername());
            keyValuePair.put("password", objLoginModel
                    .getmPassword());

            String requestParams = getParams(keyValuePair);

            URL url = new URL(Constants.SERVER_URL
                    + Constants.PATIENT_EXTENSION);

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
            jsonResponseString = stringBuilder.toString();

        } catch (Exception e) {
            Log.e("STRING BUILDER ERROR: ", "STRING BUILDER ERROR: " + e);
        }

//        HttpResponse response;
//
//        // Creating HTTP client
//        HttpClient httpClient = new DefaultHttpClient();
//
//        // Creating HttpPost
//        // Modify your url
//        HttpPost httpPost = new HttpPost(Constants.SERVER_URL
//                + Constants.PATIENT_EXTENSION);
//
//        Log.d("Call to servlet", "Call  servlet");
//
//        // Building post parameters, key and value pair
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//        nameValuePairs.add(new BasicNameValuePair("tag", "login"));
//        nameValuePairs.add(new BasicNameValuePair("email", objLoginModel
//                .getmUsername()));
//        nameValuePairs.add(new BasicNameValuePair("password", objLoginModel
//                .getmPassword()));
//
//        Log.d("cac", "NameValuePair" + nameValuePairs);
//        // Url Encoding the POST parameters
//        try {
//            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//        } catch (UnsupportedEncodingException e) {
//            // writing error to Log
//            e.printStackTrace();
//        }
//        try {
//
//            System.out.println("Executing...");
//            response = httpClient.execute(httpPost);
//
//            HttpEntity entity = response.getEntity();
//            jsonResponseString = EntityUtils.toString(entity);
//            Log.e("Http Response:", jsonResponseString);
//
//        } catch (ClientProtocolException e) {
//            // writing exception to log
//            e.printStackTrace();
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return jsonResponseString;
    }

}