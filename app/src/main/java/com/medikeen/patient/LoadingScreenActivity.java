package com.medikeen.patient;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.medikeen.patient.R;

public class LoadingScreenActivity extends AppCompatActivity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	System.out.println("LoadingScreenActivity  screen started");
	setContentView(R.layout.loading_screen);
	findViewById(R.id.mainSpinner1).setVisibility(View.VISIBLE);
	}
}
