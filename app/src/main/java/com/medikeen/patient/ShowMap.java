package com.medikeen.patient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.medikeen.patient.R;
import com.medikeen.dataModel.DoctorDTO;

import java.io.IOException;
import java.util.ArrayList;

public class ShowMap extends FragmentActivity{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	ArrayList<DoctorDTO>objArrayList = new ArrayList<DoctorDTO>();
	ArrayList<String>obj = new ArrayList<String>();

	private GoogleMap googleMap;
	double longitude,latitude;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		try {
			// Loading map
			initilizeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressLint("NewApi")
	private void initilizeMap() {
		// TODO Auto-generated method stub
		if (googleMap != null) {
			return;
		}
		googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		if (googleMap == null) {

			return;
		}
		
		// Check also the arraylist is empty or not
		if(objArrayList.size()!=0){
			
		//Manipulate when list comes here get address from doctorDTo obj.
		DoctorDTO objDoctorDTO  = new  DoctorDTO();

		//--------In objArraylist Contains list came from search result---------------
		for(int i=0;i<objArrayList.size();i++)
		{
			String address = objDoctorDTO.getDoctorAddress();
			Geocoder coder = new Geocoder(this);
			try {
				ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(address, 50);
				for(Address add : adresses){
					longitude = add.getLongitude();
					latitude = add.getLatitude();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			adddMarkerToTop(new LatLng(latitude,longitude));
		}
			 
		}
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		googleMap.getUiSettings().setZoomGesturesEnabled(true);
		googleMap.getUiSettings().setCompassEnabled(true);
		googleMap.getUiSettings().setMyLocationButtonEnabled(true);
		googleMap.getUiSettings().setRotateGesturesEnabled(true);

		
		/*
		 * For lattiude longitude set the parameter that you want to be constant position
		 * 
		 */
		CameraPosition cameraPosition = new CameraPosition.Builder().target(
				new LatLng(latitude,longitude)).zoom(14
						).bearing(0).build();

		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		googleMap.getUiSettings().setZoomGesturesEnabled(true);


	}
	private void adddMarkerToTop(LatLng latLng) {

		
		Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng)
				.title("Example")
				.snippet("Example"));
	
	}
	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
            case android.R.id.home:

            	Intent homepage = new Intent(ShowMap.this,HomePage.class);
            	startActivity(homepage);
            	
            }
            return true;
    }

}
