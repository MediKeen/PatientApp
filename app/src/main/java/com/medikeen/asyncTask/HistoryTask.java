package com.medikeen.asyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.medikeen.dataModel.LoginModel;
import com.medikeen.dataModel.PatientDescriptionDTO;
import com.medikeen.patient.HomePage;
import com.medikeen.util.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class HistoryTask extends AsyncTask<LoginModel, String, String> {

    Activity _homePage;
    ProgressDialog pd;
    String jsonResposnseString;
    ArrayList<PatientDescriptionDTO> patientslist = new ArrayList<PatientDescriptionDTO>();

    InputStream inputStream;
    StringBuilder stringBuilder;

    public HistoryTask(HomePage homePage) {

        _homePage = homePage;

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
        // pd.dismiss();

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
        pd = new ProgressDialog(_homePage);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        // pd.show();
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


    @Override
    protected String doInBackground(LoginModel... params) {
        // TODO Auto-generated method stub

        Gson objGson = new Gson();
        String request = objGson.toJson(params[0]);

        try {
            HashMap<String, String> keyValuePair = new HashMap<>();
            keyValuePair.put("jsondata", request);

            String requestParams = getParams(keyValuePair);

            URL url = new URL(Constants.SERVER_URL + "/urc2");

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
//        // Building post parametrs key and value pair
//
//        // ------Modify your server url in Constants in util package-------
//
//        HttpPost httpPost = new HttpPost(Constants.SERVER_URL + "/urc2");
//        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
//        nameValuePair.add(new BasicNameValuePair("jsondata", request));
//
//        // URl Encoding the POST parametrs
//
//        try {
//
//            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
//        } catch (Exception e) {
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
//            /**
//             * In 'jsonResposnseString' you will get response that you sent form
//             * server.
//             *
//             */
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
        return null;

    }

}
