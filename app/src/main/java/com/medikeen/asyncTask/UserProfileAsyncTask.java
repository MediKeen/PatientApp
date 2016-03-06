package com.medikeen.asyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.medikeen.dataModel.UserProfileModel;
import com.medikeen.patient.MainActivity;
import com.medikeen.patient.UserProfileActivity;
import com.medikeen.util.Constants;
import com.medikeen.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@SuppressWarnings("deprecation")
public class UserProfileAsyncTask extends
        AsyncTask<UserProfileModel, String, String> {

    Activity userProfile;
    ProgressDialog pd;
    String jsonResponseString;
    UserProfileModel objUserProfileModel;
    SharedPreferences sp;

    InputStream inputStream;
    StringBuilder stringBuilder;

    SessionManager sessionManager;

    public UserProfileAsyncTask(MainActivity userProfile) {
        this.userProfile = userProfile;
        this.sessionManager = new SessionManager(userProfile);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(userProfile);
        pd.setMessage("Please wait while we are updating your profile");
        pd.setTitle("Updating Profile");
        pd.setCancelable(false);
        pd.show();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected String doInBackground(UserProfileModel... params) {

        objUserProfileModel = params[0];

        JSONStringer userProfileJsonStringer = new JSONStringer();

        Gson gson = new Gson();
        String request = gson.toJson(params[0]);

        try {
            URL url = new URL(Constants.SERVER_URL
                    + "/android_api/userprofile/updateprofile.php");

            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-type", "application/json");

            OutputStream os = conn.getOutputStream();

            userProfileJsonStringer.object().key("personId")
                    .value(objUserProfileModel.getPersonId()).key("email")
                    .value(objUserProfileModel.getEmail()).key("firstName")
                    .value(objUserProfileModel.getFirstName()).key("lastName")
                    .value(objUserProfileModel.getLastName()).key("phone")
                    .value(objUserProfileModel.getPhone()).key("address")
                    .value(objUserProfileModel.getAddress()).endObject();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(userProfileJsonStringer.toString());

            writer.flush();
            writer.close();
            os.close();

            inputStream = conn.getInputStream();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream), 1000);
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

//        // String response = null;
//        HttpResponse response;
//
//        // Creating HTTP client
//        HttpClient httpClient = new DefaultHttpClient();
//
//        // Creating HttpPost
//        // Modify your url
//        HttpPost httpPost = new HttpPost(Constants.SERVER_URL
//                + "/android_api/userprofile/updateprofile.php");
//        httpPost.setHeader("Content-type", "application/json");
//
//        try {
//            StringEntity stringEntity = new StringEntity(
//                    userProfileJsonStringer.toString());
//
//            httpPost.setEntity(stringEntity);
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

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            JSONObject userProfileJsonResponse = new JSONObject(
                    jsonResponseString);

            String success = userProfileJsonResponse.getString("success");
            String error = userProfileJsonResponse.getString("error");
            String userJson = userProfileJsonResponse.getString("user");
            String addressJson = userProfileJsonResponse.getString("address");
            String errorMessage = userProfileJsonResponse
                    .getString("errorMessage");

            if (success.contains("true")) {

                // USER
                JSONObject userJsonResponse = new JSONObject(userJson);

                String personId = userJsonResponse.getString("person_id");
                String name = userJsonResponse.getString("name");
                String email = userJsonResponse.getString("email");
                String patient_id = userJsonResponse.getString("patient_id");
                String phone = userJsonResponse.getString("telephone");

                String[] nameSplit = name.split(" ");
                String firstName = nameSplit[0];
                String lastName = nameSplit[1];

                JSONObject addressJsonResponse = new JSONObject(addressJson);

                String address = addressJsonResponse.getString("1");

                sessionManager.createLoginSession(true,
                        Long.parseLong(personId), firstName, lastName, email,
                        address, phone);

                Toast.makeText(userProfile, "User profile updated",
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(userProfile, errorMessage, Toast.LENGTH_SHORT)
                        .show();
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        pd.dismiss();

    }
}
