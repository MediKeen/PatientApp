package com.medikeen.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.medikeen.patient.R;
import com.medikeen.dataModel.DoctorDTO;
import com.medikeen.patient.DoctorProfile;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GridViewCustomAdapter extends BaseAdapter{

	Context context;
	ArrayList<DoctorDTO>oArrayList;
	private List<DoctorDTO> tripList = null;

	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public GridViewCustomAdapter(Context context, ArrayList<DoctorDTO> objDtos) 
	{
		this.context=context;
		this.tripList =objDtos;
		this.oArrayList = new ArrayList<DoctorDTO>();
		this.oArrayList.addAll(objDtos);

		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.images)
		.showImageForEmptyUri(R.drawable.images)
		.showImageOnFail(R.drawable.images)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.build();
	}

	public int getCount() 
	{
		return oArrayList.size();
	}

	static class RecordHolder {
		TextView textViewTitle,textViewSpec;
		ImageView imageViewIte;
		Button buttonProfile;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View row = convertView;
		RecordHolder holder = null;
		ImageLoader imageLoader = ImageLoader.getInstance();

		if (row == null) 
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(R.layout.custom_fragment_find_doctor, parent, false);

			holder = new RecordHolder();
			holder.textViewTitle = (TextView) row.findViewById(R.id.docName);
			holder.textViewSpec = (TextView) row.findViewById(R.id.docSpec);
			holder.buttonProfile= (Button)row.findViewById(R.id.buttonProfile);
			holder.imageViewIte = (ImageView) row.findViewById(R.id.Imageview);

			row.setTag(holder);

			holder.textViewTitle.setText(DoctorDTO.doctorList.get(position).getDoctorName());
			holder.textViewSpec.setText(DoctorDTO.doctorList.get(position).getDocSpec());

			holder.buttonProfile.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent docProfile = new Intent(context,DoctorProfile.class);
					context.startActivity(docProfile);
				}
			});

			if(!DoctorDTO.doctorList.get(position).getDoctorImage().equalsIgnoreCase("")){

				imageLoader.displayImage("", holder.imageViewIte, options, animateFirstListener);

			}else{
				holder.imageViewIte.setImageResource(R.drawable.images);
			}

		} else {
			holder = (RecordHolder) row.getTag();
		}
		return row;

	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

}