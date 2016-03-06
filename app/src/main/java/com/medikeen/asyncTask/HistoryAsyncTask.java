package com.medikeen.asyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.medikeen.adapters.HistoryAdapter;
import com.medikeen.dataModel.HistoryModel;
import com.medikeen.datamodels.serialized.HistoryResponse;
import com.medikeen.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@SuppressWarnings("deprecation")
public class HistoryAsyncTask extends AsyncTask<String[], String, String> {

    private static final String RESET_PASSWORD = "RESET_PASSWORD";
    Activity historyFragment;
    ProgressDialog pd;
    String jsonResposnseString;
    String[] historyInputParams;
    SharedPreferences sp;
    Editor edit;
    // TEST
    ListView mHistoryListView;
    JSONArray prescriptionsArray;
    ArrayList<HistoryModel> historyList;
    HistoryAdapter adapter;

    InputStream inputStream;
    StringBuilder stringBuilder;

    public HistoryAsyncTask(Activity historyFragment,
                            ListView mHistoryListView, ArrayList<HistoryModel> historyList) {
        // TODO Auto-generated constructor stub

        this.mHistoryListView = mHistoryListView;
        this.historyList = historyList;
        this.historyFragment = historyFragment;

        sp = historyFragment.getSharedPreferences(RESET_PASSWORD,
                historyFragment.MODE_PRIVATE);
        edit = sp.edit();
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();

        if (historyFragment == null) {
            return;
        }

        pd = new ProgressDialog(historyFragment);
        pd.setMessage("Please wait while we are loading your history");
        pd.setTitle("History");
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

    @SuppressWarnings("deprecation")
    @Override
    protected String doInBackground(String[]... params) {
        // TODO Auto-generated method stub

        historyInputParams = params[0];

        Log.e("TAG: ", "TAG: " + historyInputParams[0]);

        Gson objGson = new Gson();
        String request = objGson.toJson(params[0]);

        try {

            URL url = new URL(Constants.SERVER_URL
                    + "/android_api/prescription/prescriptions.php?id=" + historyInputParams[0]);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-type",
                    "application/x-www-form-urlencoded");

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

        // HttpResponse response;
        //
        // // Creating Http client
        // HttpClient httpclient = new DefaultHttpClient();
        //
        // // Building post parametrs key and value pair
        //
        // // ------Modify your server url in Constants in util package-------
        //
        // // HttpPost httpPost = new HttpPost(Constants.SERVER_URL + "/urc2");
        // // List<NameValuePair> nameValuePair = new
        // ArrayList<NameValuePair>(2);
        // // nameValuePair.add(new BasicNameValuePair("jsondata", request));
        //
        // HttpPost httpPost = new HttpPost(Constants.SERVER_URL
        // + "/android_api/prescription/prescriptions.php?id="
        // + historyInputParams[0]);
        // // List<NameValuePair> nameValuePair = new
        // // ArrayList<NameValuePair>();
        // // nameValuePair.add(new BasicNameValuePair("id",
        // // historyInputParams[0]));
        //
        // // URl Encoding the POST parametrs
        // try {
        // // httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        //
        // // making http request
        // try {
        // System.out.println("Executing");
        // response = httpclient.execute(httpPost);
        // System.out.println("check response" + response.toString());
        // HttpEntity entity = response.getEntity();
        // jsonResposnseString = EntityUtils.toString(entity);
        //
        // } catch (ClientProtocolException e) {
        // e.printStackTrace();
        // } catch (Exception e) {
        // // TODO: handle exception
        // e.printStackTrace();
        // }

        return jsonResposnseString;
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

        // Gson gson = new Gson();
        //
        // ResetPasswordResponse response = gson.fromJson(result,
        // ResetPasswordResponse.class);
        //
        // if (response.success == 1) {
        //
        // edit.putString("RESET_EMAIL", response.email);
        // edit.putString("VERIFICATION_CODE", objResetModel.getReset_code());
        // edit.commit();
        //
        // Intent resetPasswordIntent = new Intent(_passwordReset,
        // NewPasswordActivity.class);
        // _passwordReset.startActivity(resetPasswordIntent);
        // _passwordReset.finish();
        // } else {
        // Toast.makeText(_passwordReset, "Something went wrong",
        // Toast.LENGTH_SHORT).show();
        // }

        Gson gson = new Gson();

        HistoryResponse response = gson.fromJson(result, HistoryResponse.class);

        if (response.success == 1) {

            Log.e("TAG", "HISTORY RESPONSE: " + response.success);

            try {
                JSONObject historyJsonObject = new JSONObject(result);

                prescriptionsArray = historyJsonObject
                        .getJSONArray("prescriptions");

                for (int i = 0; i < prescriptionsArray.length(); i++) {
                    JSONObject prescriptionsObject = prescriptionsArray
                            .getJSONObject(i);

                    String resource_id = prescriptionsObject
                            .getString("resource_id");
                    String resource_type = prescriptionsObject
                            .getString("resource_type");
                    String person_id = prescriptionsObject
                            .getString("person_id");
                    String recepient_name = prescriptionsObject
                            .getString("recepient_name");
                    String recepient_address = prescriptionsObject
                            .getString("recepient_address");
                    String recepient_number = prescriptionsObject
                            .getString("recepient_number");
                    String offer_type = prescriptionsObject
                            .getString("offer_type");
                    String is_image_uploaded = prescriptionsObject
                            .getString("is_image_uploaded");
                    String is_email_sent = prescriptionsObject
                            .getString("is_email_sent");
                    String created_date = prescriptionsObject
                            .getString("created_date");
                    String updated_date = prescriptionsObject
                            .getString("updated_date");

                    historyList.add(new HistoryModel(person_id, resource_id,
                            resource_type, recepient_name, recepient_address,
                            recepient_number, offer_type, is_image_uploaded,
                            is_email_sent, created_date, updated_date));
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else if (response.success == 0) {
            Log.e("TAG", "HISTORY RESPONSE: " + response.error);
        }

        adapter = new HistoryAdapter(historyFragment, historyList);
        mHistoryListView.setAdapter(adapter);

        if (pd.isShowing()) {
            // pd.dismiss();
        }
    }
}