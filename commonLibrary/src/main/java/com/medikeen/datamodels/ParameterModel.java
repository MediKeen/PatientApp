package com.medikeen.datamodels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class ParameterModel {

	public String PrameterName;
	public String PrameterValue;

	Map<String, String> myMap1 = new HashMap<String, String>();
	public static List<Map<String, String>> myMap = new ArrayList<Map<String, String>>();

	public void setPatameters(String parameterName, String parametervalue) {

		myMap1.put("parametername", parameterName);
		myMap1.put("parametervalue", parametervalue);

		myMap.add(myMap1);
		Log.d("Hash map is", "" + myMap);
	}

	public List<Map<String, String>> getparameters() {

		return myMap;

	}

}
