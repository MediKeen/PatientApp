package com.medikeen.asyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.medikeen.dataModel.RegisterModel;
import com.medikeen.patient.RegistrationActivity;
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

public class RegisterTask extends AsyncTask<RegisterModel, String, String> {

    Activity _login;
    ProgressDialog pd;
    String jsonResposnseString;

    RegisterModel objRegisterModel;

    InputStream inputStream;
    StringBuilder stringBuilder;

    public RegisterTask(RegistrationActivity registrationActivity) {
        _login = registrationActivity;
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
        Log.d("response json is ", "" + result);
        pd.dismiss();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        pd = new ProgressDialog(_login);
        pd.setTitle("Registering");
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
    protected String doInBackground(RegisterModel... params) {
        // TODO Auto-generated method stub

        objRegisterModel = params[0];

        Gson objGson = new Gson();
        String request = objGson.toJson(params[0]);

        try {
            HashMap<String, String> keyValuePair = new HashMap<>();
            keyValuePair.put("tag", "register");
            keyValuePair.put("name", objRegisterModel
                    .getFirstName());
            keyValuePair.put("email", objRegisterModel
                    .getEmailId());
            keyValuePair.put("password", objRegisterModel
                    .getPassword());
            keyValuePair.put("address", objRegisterModel
                    .getAddress());
            keyValuePair.put("telephone", objRegisterModel
                    .getContactNo());

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
//        // Building post parametrs key and value pair
//
//        // ------Modify your server url in Constants in util package-------
//
//        // URL url = new URL(Constants.SERVER_URL
//        // + Constants.PATIENT_EXTENSION);
//        //
//        // HttpURLConnection urlConnection = (HttpURLConnection)
//        // url.openConnection();
//        //
//        // try{
//        // urlConnection.setDoOutput(true);
//        // urlConnection.setChunkedStreamingMode(0);
//        //
//        // OutputStream out = new
//        // BufferedOutputStream(urlConnection.getOutputStream());
//        //
//        // writeStream(out);
//        // }
//
//        HttpPost httpPost = new HttpPost(Constants.SERVER_URL
//                + Constants.PATIENT_EXTENSION);
//
//        Log.d("Call to servlet", "Call servlet");
//
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
//
//        nameValuePairs.add(new BasicNameValuePair("tag", "register"));
//        nameValuePairs.add(new BasicNameValuePair("name", objRegisterModel
//                .getFirstName()));
//        nameValuePairs.add(new BasicNameValuePair("email", objRegisterModel
//                .getEmailId()));
//        nameValuePairs.add(new BasicNameValuePair("password", objRegisterModel
//                .getPassword()));
//        nameValuePairs.add(new BasicNameValuePair("address", objRegisterModel
//                .getAddress()));
//        nameValuePairs.add(new BasicNameValuePair("telephone", objRegisterModel
//                .getContactNo()));
//
//        // URl Encoding the POST parametrs
//
//        try {
//
//            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // making http request
//        try {
//            Log.d("RegisterTask", "Sending NameValuePair" + nameValuePairs);
//            response = httpclient.execute(httpPost);
//            Log.d("RegisterTask", "check response" + response.toString());
//            HttpEntity entity = response.getEntity();
//            jsonResposnseString = EntityUtils.toString(entity);
//
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return jsonResposnseString;
    }
}