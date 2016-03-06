package com.medikeen.patient;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.medikeen.R;
import com.medikeen.asyncTask.SearchTask;
import com.medikeen.dataModel.SearchModel;

public class FindDoctor extends Fragment implements OnClickListener {

	ImageView physician,physician1,physician2,tick,imageViewOrtho,imageViewOrtho1,tick1,imageViewSurgeon,imageViewSurgeon1,imageViewSurgeontick,imageViewDentist,imageViewDentist1,imageViewDentisttick,imageViewPedia,imageViewPedia1,imageViewPediatick,imageViewGyno,imageViewGyno1,imageViewGynotick;
	TextView textViewPhy, textViewPhy1;
	ListView listViewpatient;
	Button mSearch,mShowMap;

	ImageView prescriptionDoc;
	LinearLayout linearLayoutAddPres;


	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_search_doctor, container,
				false);

		init(view);


		physician.setOnClickListener(this);
		imageViewOrtho.setOnClickListener(this);
		imageViewSurgeon.setOnClickListener(this);
		imageViewDentist.setOnClickListener(this);
		imageViewPedia.setOnClickListener(this);
		imageViewGyno.setOnClickListener(this);
		mSearch.setOnClickListener(this);
		mShowMap.setOnClickListener(this);
		 

		return view;
	}


	private void init(View view) {

		physician = (ImageView)view.findViewById(R.id.ImageviewPhysician);
		physician2 = (ImageView)view.findViewById(R.id.ImageviewPhysician2);
		textViewPhy = (TextView)view.findViewById(R.id.textViewphy);
		tick = (ImageView)view.findViewById(R.id.imageViewtick);

		imageViewOrtho = (ImageView)view.findViewById(R.id.ImageviewOrtho);
		imageViewOrtho1 =(ImageView)view.findViewById(R.id.ImageviewOrtho1);
		tick1 = (ImageView)view.findViewById(R.id.imageViewOrtho);

		imageViewSurgeon=  (ImageView)view.findViewById(R.id.Imageviewsurgeon);
		imageViewSurgeon1=  (ImageView)view.findViewById(R.id.Imageviewsurgeon1);
		imageViewSurgeontick = (ImageView)view.findViewById(R.id.Imageviewsurgeontick);


		imageViewDentist = (ImageView)view.findViewById(R.id.ImageviewDentist);
		imageViewDentist1 = (ImageView)view.findViewById(R.id.ImageviewDentist1);
		imageViewDentisttick = (ImageView)view.findViewById(R.id.ImageviewDentisttick);


		imageViewPedia = (ImageView)view.findViewById(R.id.ImageviewPedia);
		imageViewPedia1 = (ImageView)view.findViewById(R.id.ImageviewPedia1);
		imageViewPediatick = (ImageView)view.findViewById(R.id.ImageviewPediatick);


		imageViewGyno = (ImageView)view.findViewById(R.id.Imageviewgyno);
		imageViewGyno1 = (ImageView)view.findViewById(R.id.Imageviewgyno1);
		imageViewGynotick = (ImageView)view.findViewById(R.id.Imageviewgynotick);


		mShowMap  = (Button)view.findViewById(R.id.buttonMap);
		mSearch  =(Button)view.findViewById(R.id.buttonSearch);

	}

	@Override
	public void onClick(View arg0) {

		int id = arg0.getId();
		if (id == R.id.ImageviewPhysician) {
			if(physician.getTag() != null && physician.getTag().toString().equals("ImageviewPhysician")){
				physician.setTag("ImageviewPhysician2");
				physician2.setVisibility(View.VISIBLE);
				tick.setVisibility(View.VISIBLE);
			} else {
				physician.setTag("ImageviewPhysician");
				physician2.setVisibility(View.GONE);
				tick.setVisibility(View.GONE);
			}
		} else if (id == R.id.ImageviewOrtho) {
			if(imageViewOrtho.getTag() != null && imageViewOrtho.getTag().toString().equals("ImageviewOrtho")){
				imageViewOrtho.setTag("ImageviewOrtho1");
				imageViewOrtho1.setVisibility(View.VISIBLE);
				tick1.setVisibility(View.VISIBLE);
			} else {
				imageViewOrtho.setTag("ImageviewOrtho");
				imageViewOrtho1.setVisibility(View.GONE);
				tick1.setVisibility(View.GONE);
			}
		} else if (id == R.id.Imageviewsurgeon) {
			if(imageViewSurgeon.getTag() != null && imageViewSurgeon.getTag().toString().equals("Imageviewsurgeon")){
				imageViewSurgeon.setTag("Imageviewsurgeon1");
				imageViewSurgeon1.setVisibility(View.VISIBLE);
				imageViewSurgeontick.setVisibility(View.VISIBLE);
			} else {
				imageViewSurgeon.setTag("Imageviewsurgeon");
				imageViewSurgeon1.setVisibility(View.GONE);
				imageViewSurgeontick.setVisibility(View.GONE);
			}
		} else if (id == R.id.ImageviewDentist) {
			if(imageViewDentist.getTag() != null && imageViewDentist.getTag().toString().equals("ImageviewDentist")){
				Log.d("in den", "dentist");
				imageViewDentist.setTag("ImageviewDentist1");
				imageViewDentist1.setVisibility(View.VISIBLE);
				imageViewDentisttick.setVisibility(View.VISIBLE);
			} else {
				Log.d("in den", "else dentist");

				imageViewDentist.setTag("ImageviewDentist");
				imageViewDentist1.setVisibility(View.GONE);
				imageViewDentisttick.setVisibility(View.GONE);
			}
		} else if (id == R.id.ImageviewPedia) {
			if(imageViewPedia.getTag() != null && imageViewPedia.getTag().toString().equals("ImageviewPedia")){
				imageViewPedia.setTag("ImageviewDentist1");
				imageViewPedia1.setVisibility(View.VISIBLE);
				imageViewPediatick.setVisibility(View.VISIBLE);
			} else {

				imageViewPedia.setTag("ImageviewPedia");
				imageViewPedia1.setVisibility(View.GONE);
				imageViewPediatick.setVisibility(View.GONE);
			}
		} else if (id == R.id.Imageviewgyno) {
			if(imageViewGyno.getTag() != null && imageViewGyno.getTag().toString().equals("Imageviewgyno")){
				imageViewGyno.setTag("ImageviewDentist1");
				imageViewGyno1.setVisibility(View.VISIBLE);
				imageViewGynotick.setVisibility(View.VISIBLE);
			} else {

				imageViewGyno.setTag("Imageviewgyno");
				imageViewGyno1.setVisibility(View.GONE);
				imageViewGynotick.setVisibility(View.GONE);
			}
		} else if (id == R.id.buttonSearch) {
			//--------Pass result to SearchResult-----------
			Intent objSearch = new Intent(getActivity(),SearchResult.class);
			startActivity(objSearch);
		} else if (id == R.id.buttonMap) {
			SearchModel objSearchModelMap = new SearchModel();
			//----Which specialist is selected pass id by comma sepearated-----------
			objSearchModelMap.setSearchIds("1");
			objSearchModelMap.setFromActivity("1001");
			new SearchTask(getActivity()).execute(objSearchModelMap);
		}
	}
}
