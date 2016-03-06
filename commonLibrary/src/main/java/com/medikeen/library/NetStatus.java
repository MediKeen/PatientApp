package com.medikeen.library;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetStatus {

	private static NetStatus instance = new NetStatus();
	static Context context;

	public static NetStatus getInstance(Context ctx) {
		context = ctx;
		return instance;
	}

	ConnectivityManager connectivityManager;
	NetworkInfo wifiInfo, mobileInfo;

	boolean connected = false;

	public boolean isOnline(Context con) {
		try {
			connectivityManager = (ConnectivityManager) con
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();
			connected = networkInfo != null && networkInfo.isAvailable()
					&& networkInfo.isConnected();
			return connected;

		} catch (Exception e) {
			System.out
					.println("CheckConnectivity Exception: " + e.getMessage());
			Log.v("connectivity", e.toString());
		}
		return connected;
	}
}