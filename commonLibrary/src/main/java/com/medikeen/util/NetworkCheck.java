package com.medikeen.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkCheck {
	public static int TYPE_WIFI = 1;
	public static int TYPE_MOBILE = 2;
	public static int TYPE_NOT_CONNECTED = 0;


//	public static int getConnectivityStatus(Context context) {
//		ConnectivityManager cm = (ConnectivityManager) context
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//		if (null != activeNetwork) {
//			if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
//				return TYPE_WIFI;
//
//			if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
//				return TYPE_MOBILE;
//		} 
//
//
//		return TYPE_NOT_CONNECTED;
//
//	}
//
//	public static Boolean getConnectivityStatusString(Context context) {
//		int conn = NetworkCheck.getConnectivityStatus(context);
//		Boolean status=false ;
//		if (conn == NetworkCheck.TYPE_WIFI) {
//			status = true;
//		} else if (conn == NetworkCheck.TYPE_MOBILE) {
//			status = true;
//		} else if (conn == NetworkCheck.TYPE_NOT_CONNECTED) {
//			status = false;
//		}
//		return status;
//	}
	
	 private Context _context;
     
	    public NetworkCheck(Context context){
	        this._context = context;
	    }
	 
	    public boolean isConnectingToInternet(){
	        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
	          if (connectivity != null) 
	          {
	              NetworkInfo[] info = connectivity.getAllNetworkInfo();
	              if (info != null) 
	                  for (int i = 0; i < info.length; i++) 
	                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
	                      {
	                          return true;
	                      }
	 
	          }
	          return false;
	    }
	
}
