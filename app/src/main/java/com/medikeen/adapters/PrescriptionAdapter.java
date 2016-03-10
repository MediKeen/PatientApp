package com.medikeen.adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.medikeen.patient.R;
import com.medikeen.dataModel.PatientDescriptionDTO;
import com.medikeen.patient.DoctorProfile;
import com.medikeen.patient.ViewPrescription;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionAdapter extends BaseAdapter{

	LayoutInflater inflator;
	Context mContext;
	private List<PatientDescriptionDTO> liveTrip = null;
	private ArrayList<PatientDescriptionDTO> arrayList ;




	public PrescriptionAdapter(Context context,ArrayList<PatientDescriptionDTO> LiveContact) {

		this.liveTrip = LiveContact;
		Log.d("LiveTrip ", ""+liveTrip);
		mContext = context;
		inflator = LayoutInflater.from(mContext);
		this.arrayList = new ArrayList<PatientDescriptionDTO>();
		this.arrayList.addAll(LiveContact);

	}


	public class ViewHolderMainHome {
		ImageView LiveUserIcon;
		TextView patientname,patientaddress;
		Button viewPrescription,viewpofile;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.d("Size is:", ""+liveTrip.size());
		return liveTrip.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return liveTrip.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub



		final ViewHolderMainHome holderMain;
		if(arg1 == null)
		{

			holderMain = new ViewHolderMainHome();
			arg1 = inflator.inflate(R.layout.custom_list_item_history, null);

			Typeface roboto = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf" );

			holderMain.LiveUserIcon = (ImageView)arg1.findViewById(R.id.imageViewPatient);
			holderMain.patientname = (TextView)arg1.findViewById(R.id.textViewPatientname);
			holderMain.patientaddress = (TextView)arg1.findViewById(R.id.textViewPatientAddress);
			holderMain.viewPrescription = (Button)arg1.findViewById(R.id.buttonPres);
			holderMain.viewpofile = (Button)arg1.findViewById(R.id.buttonProfile);

			arg1.setTag(holderMain);
		}else
		{
			holderMain = (ViewHolderMainHome)arg1.getTag();
		}

		holderMain.patientname.setText(PatientDescriptionDTO.prescriptionList.get(arg0).patientName);
		holderMain.patientaddress.setText(PatientDescriptionDTO.prescriptionList.get(arg0).patientAddress);

		holderMain.viewPrescription.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent prescription = new Intent(mContext,ViewPrescription.class);
				mContext.startActivity(prescription);

			}
		});

		holderMain.viewpofile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent prescription = new Intent(mContext,DoctorProfile.class);
				
				//----------------------Manipulate the strings that to be passed to profile
				prescription.putExtra("Docname", "");
				prescription.putExtra("Docspec", "");
				prescription.putExtra("DocImage", "");
				prescription.putExtra("DocAddress", "");
				prescription.putExtra("DocContact", "");

				
				mContext.startActivity(prescription);

			}
		});

		return arg1;
	}

}








