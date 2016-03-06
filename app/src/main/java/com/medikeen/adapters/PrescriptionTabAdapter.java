package com.medikeen.adapters;


import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.medikeen.R;
import com.medikeen.dataModel.PrescriptionDTO;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionTabAdapter extends BaseAdapter{

	LayoutInflater inflator;
	Context mContext;
	private List<PrescriptionDTO> liveTrip = null;
	private ArrayList<PrescriptionDTO> arrayList ;




	public PrescriptionTabAdapter(Context context,ArrayList<PrescriptionDTO> LiveContact) {

		this.liveTrip = LiveContact;
		Log.d("LiveTrip ", ""+liveTrip);
		mContext = context;
		inflator = LayoutInflater.from(mContext);
		this.arrayList = new ArrayList<PrescriptionDTO>();
		this.arrayList.addAll(LiveContact);

	}


	public class ViewHolderMainHome {
		ImageView LiveUserIcon;
		TextView patientname,patientaddress;
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
			arg1 = inflator.inflate(R.layout.custom_tab_prescription, null);
			
			Typeface roboto = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf" );
			
			holderMain.patientname = (TextView)arg1.findViewById(R.id.textViewTabname);
			holderMain.patientaddress = (TextView)arg1.findViewById(R.id.textViewTabTime);

			arg1.setTag(holderMain);
		}else
		{
			holderMain = (ViewHolderMainHome)arg1.getTag();
		}

		holderMain.patientname.setText(PrescriptionDTO.prescriptionList.get(arg0).tabName);
		holderMain.patientaddress.setText(PrescriptionDTO.prescriptionList.get(arg0).prescriptionTime);

		return arg1;
	}

}








