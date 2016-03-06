package com.medikeen.asyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.medikeen.dataModel.DoctorDTO;
import com.medikeen.dataModel.SearchModel;
import com.medikeen.patient.HomePage;
import com.medikeen.patient.ShowMap;
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

public class SearchTask extends AsyncTask<SearchModel, String, String> {

    Activity _homePage;
    ProgressDialog pd;
    String jsonResposnseString;
    ArrayList<DoctorDTO> objDtos = new ArrayList<DoctorDTO>();
    SearchModel objSearchResult;

    InputStream inputStream;
    StringBuilder stringBuilder;

    public SearchTask(Activity activity) {

        _homePage = activity;

    }

    public SearchTask(HomePage homePage) {
        // TODO Auto-generated constructor stub
        _homePage = homePage;

    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);

        //	pd.dismiss();

        String[] doctorList = new String[5];
        DoctorDTO objDoctorDTO = new DoctorDTO();
        //SearchModel objSearchResult = new SearchModel();
        /*
         * It will get arraylist of the result then manipulate
		 * it like this to send to custom adapter
		 * 
		 */

        //----------------Instead of static value need to pass list size
        for (int i = 0; i < 5; i++) {

            Log.d("pa", "" + i);
            //-------Manipulated Work----------------
            //-----Remove this line after doinbackground--------------
            //--------pass url for image in last paramenter
            objDoctorDTO.setMatchUserDetails(new DoctorDTO("Test", "MBBS", "Pune", "1234567", ""));

            DoctorDTO group = new DoctorDTO(doctorList[i], doctorList[i], doctorList[i], doctorList[i], doctorList[i]);
            objDtos.add(group);
        }

        String ress = objSearchResult.getFromActivity();

        Log.d("rss", "" + ress);
        if (ress.equalsIgnoreCase("1001")) {
            //--------Pass result to ShowMap-----------
            Intent showmap = new Intent(_homePage, ShowMap.class);
            _homePage.startActivity(showmap);

        }
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        pd = new ProgressDialog(_homePage);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        //pd.show();

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
    protected String doInBackground(SearchModel... params) {
        // TODO Auto-generated method stub

        Log.d("doInBackground", "doInBackground");
        Gson objGson = new Gson();
        String request = objGson.toJson(params[0]);

        objSearchResult = params[0];

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
//        //Creating Http client
//        HttpClient httpclient = new DefaultHttpClient();
//
//        //Building post parametrs key and value pair
//
//        //------Modify your server url in Constants in util package-------
//
//        HttpPost httpPost = new HttpPost(Constants.SERVER_URL + "/urc2");
//        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
//        nameValuePair.add(new BasicNameValuePair("jsondata", request));
//
//        //URl Encoding the POST parametrs
//
//        try {
//
//            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //making http request
//        try {
//            System.out.println("Executing");
//            //			response = httpclient.execute(httpPost);
//            //			System.out.println("check response"+response.toString());
//            //			HttpEntity entity= response.getEntity();
//            //			jsonResposnseString = EntityUtils.toString(entity);
//
//            Log.d("search", "Search");
//            //--------User response Arraylist to go through loop and pass parameter to constuctor------------
//			/*DoctorDTO objDoctorDTO = new DoctorDTO();
//			for(int i=0;i<5;i++){
//				objDoctorDTO.setMatchUserDetails(new DoctorDTO("Test", "MBBS", "Pune", "1234567", ""));
//			}*/
//
//        }
//        //		catch(ClientProtocolException e){
//        //			e.printStackTrace();
//        //		}
//        catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
        return null;
    }
}
