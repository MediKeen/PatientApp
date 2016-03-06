package com.medikeen.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.medikeen.datamodels.User;
import android.R.string;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {

	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	private  String TAG="shareoffer";
	// Context
	Context _context;

	int PRIVATE_MODE = 0;

	// Sharedpref file name
	private static final String PREF_NAME = "LOGGED";

	// All Shared Preferences Keys
	public static final String IS_AUTH = "IsAuth";

	private static final String IS_LOGIN = "IsLoggedIn";

	// User name (make variable public to access from outside)
	public static final String KEY_ADDRESS= "address";

	// Email address (make variable public to access from outside)
	public static final String KEY_EMAIL = "email";

	public static final String KEY_LASTNAME = "lastname";

	public static final String KEY_FIRSTNAME = "firstName";

	public static final String KEY_MOBILENO="mobileno";

	public static final String KEY_PERSON_ID="personid";

	// Constructor
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	/**
	 * Create login session
	 * */
	public void createLoginSession(boolean isLogin,long persinId, String FirstName,String lastName,String email,String address,String phone){

		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		editor.putLong(KEY_PERSON_ID, persinId);
		editor.putString(KEY_EMAIL, email);
		editor.putString(KEY_FIRSTNAME, FirstName);
		editor.putString(KEY_LASTNAME, lastName);
		editor.putString(KEY_ADDRESS, address);
		editor.putString(KEY_MOBILENO, phone);
		// commit changes
		editor.commit();
	}


	public void checkLogin(){
		// Check login status
		Log.d("this.isLoggedIn()","isLoggedIn()"+this.isLoggedIn());
		
		// if(this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            //Intent i = new Intent(_context, CategoryListings.class);

            // Closing all the Activities

            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            //_context.startActivity(i);
        //}
        //else{
         //   Intent i = new Intent(_context, Login.class);
           // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
           // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           // _context.startActivity(i);
	//}
	}
		 


	public User getUserDetails(){
		
		long personId = pref.getLong(KEY_PERSON_ID, -1);
		String firstName = pref.getString(KEY_FIRSTNAME, "");
		String lastName = pref.getString(KEY_LASTNAME, "");
		String emailAddress = pref.getString(KEY_EMAIL, "");
		String address = pref.getString(KEY_ADDRESS, "");
		String phoneNo = pref.getString(KEY_MOBILENO, "");

		User user = new User(personId, firstName, lastName, emailAddress, address, phoneNo);
				
		return user;
	}
	public void logoutUser(){
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();

		// After logout redirect user to Loing Activity
		/*        Intent i = new Intent(_context,LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
		 */    }

	public boolean isLoggedIn(){
		Log.d(TAG, ""+pref.getBoolean(IS_LOGIN, false));
		return pref.getBoolean(IS_LOGIN, false);
	}


	public void setAuthUSer(boolean authUser){

		Log.d(TAG, "Sess authe user"+authUser);

		editor.putBoolean(IS_AUTH, authUser);

		editor.commit();
	}

	public boolean isAuthUser(){

		return pref.getBoolean(IS_AUTH, false);

	}


}
