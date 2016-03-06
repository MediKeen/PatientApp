package com.medikeen.asyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.medikeen.dataModel.ImageUploadArgs;
import com.medikeen.dataModel.SaveImageDetailsModel;
import com.medikeen.datamodels.serialized.SaveImageDetailsResponse;
import com.medikeen.interfaces.IImageUploadedEventListener;
import com.medikeen.patient.AddressPrescription;
import com.medikeen.util.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

public class SaveImageDetailsTask extends
        AsyncTask<SaveImageDetailsModel, String, String> {

    Activity mActivity;
    ProgressDialog pd;
    String jsonResponseString;
    SaveImageDetailsModel saveImageDetailsModel;
    SharedPreferences sp;
    IImageUploadedEventListener uploadedEventListener;

    File file;

    InputStream inputStream;
    StringBuilder stringBuilder;

    public SaveImageDetailsTask(AddressPrescription activity, File file, IImageUploadedEventListener uploadedEventListener) {
        mActivity = activity;
        this.file = file;
        this.uploadedEventListener = uploadedEventListener;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("response json is ", "" + result);

        Gson gson = new Gson();
        SaveImageDetailsResponse imageDetails = gson.fromJson(result,
                SaveImageDetailsResponse.class);

        if (imageDetails.success == 1) {
            Log.d("filename", imageDetails.resourceid + ".jpg");
        } else {
            Toast.makeText(mActivity,
                    "Image upload failed. Message: " + imageDetails.error_msg,
                    Toast.LENGTH_LONG).show();
            return;
        }

        ImageUploadArgs args = new ImageUploadArgs();
        args.file = file;
        args.filename = imageDetails.resourceid + ".jpg";
        args.mimeType = "image/jpeg";
        try {
            args.url = new URL(Constants.SERVER_URL
                    + "/android_api/prescription/upload.php");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        AsyncTask<ImageUploadArgs, String, String> task = new ImageUploadTask(
                mActivity);
        ((ImageUploadTask) task).setOnImageUploadedEventListener(uploadedEventListener);
        task.execute(args);
        pd.dismiss();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(mActivity);
        pd.setMessage("Uploading Prescription details..");
        pd.setTitle("Please wait");
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
    protected String doInBackground(SaveImageDetailsModel... params) {

        saveImageDetailsModel = params[0];

        Gson gson = new Gson();
        String request = gson.toJson(params[0]);
        Log.d("gson is", "" + request);

        try {
            HashMap<String, String> keyValuePair = new HashMap<>();

            keyValuePair.put("tag", "save");
            keyValuePair.put("resourcetype",
                    saveImageDetailsModel.getResourceType());
            keyValuePair.put("personid", Long
                    .toString(saveImageDetailsModel.getPersonId()));
            keyValuePair.put("recepientname",
                    saveImageDetailsModel.getRecepientName());
            keyValuePair.put("recepientaddress",
                    saveImageDetailsModel.getRecepientAddress());
            keyValuePair.put("recepientnumber",
                    saveImageDetailsModel.getRecepientNumber());
            keyValuePair.put("offertype",
                    saveImageDetailsModel.getOfferType());

            String requestParams = getParams(keyValuePair);

            URL url = new URL(Constants.SERVER_URL
                    + Constants.IMAGE_EXTENSION);

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
//                + Constants.IMAGE_EXTENSION);
//
//        Log.d("Call to servlet", "Call servlet");
//
//        // Building post parameters, key and value pair
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//        nameValuePairs.add(new BasicNameValuePair("tag", "save"));
//        nameValuePairs.add(new BasicNameValuePair("resourcetype",
//                saveImageDetailsModel.getResourceType()));
//        nameValuePairs.add(new BasicNameValuePair("personid", Long
//                .toString(saveImageDetailsModel.getPersonId())));
//        nameValuePairs.add(new BasicNameValuePair("recepientname",
//                saveImageDetailsModel.getRecepientName()));
//        nameValuePairs.add(new BasicNameValuePair("recepientaddress",
//                saveImageDetailsModel.getRecepientAddress()));
//        nameValuePairs.add(new BasicNameValuePair("recepientnumber",
//                saveImageDetailsModel.getRecepientNumber()));
//        nameValuePairs.add(new BasicNameValuePair("offertype",
//                saveImageDetailsModel.getOfferType()));
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
//            Log.d("Http Response:", jsonResponseString);
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
