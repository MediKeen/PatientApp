package com.medikeen.patient;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.medikeen.R;
import com.medikeen.adapters.UploadedPrescriptionAdapter;
import com.medikeen.dataModel.UploadedPrescriptionData;

import java.util.ArrayList;

public class UploadedPrescription extends Fragment{


	private ListView objListView;

	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_uploaded_prescription, container,
				false);

		init(view);

		/**
		 * For Demo 
		 * Update it when get data from server
		 */
		ArrayList<UploadedPrescriptionData> objArrayList = new ArrayList<UploadedPrescriptionData>();
		UploadedPrescriptionData objPrescriptionData = new UploadedPrescriptionData();

		for(int i=0;i<5;i++){

			objPrescriptionData.setDeliveryAddress("Pune,Maharashtra,India");
			objPrescriptionData.setOrderId("123456");
			objPrescriptionData.setOrderAmount("1500");
			/*
			 * We can use here ENUM type data for status
			 */
			objPrescriptionData.setOrderStatus("Placed");
			objPrescriptionData.setOrderPlaceDate("21 Feb 2015");
			
			objArrayList.add(objPrescriptionData);
		}

		UploadedPrescriptionAdapter objPrescriptionAdapter = new UploadedPrescriptionAdapter(getActivity(), objArrayList);

		objListView.setAdapter(objPrescriptionAdapter);
		return view;
	}

	private void init(View view) {
		objListView = (ListView)view.findViewById(R.id.listViewData);

	}


}
