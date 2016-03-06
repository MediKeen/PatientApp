/**
 * 
 */
package com.medikeen.patient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.medikeen.R;
import com.medikeen.dataModel.PrescriptionModel;
import com.medikeen.datamodels.User;
import com.medikeen.util.ButtonFloat;
import com.medikeen.util.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Archana
 *
 */
@SuppressLint("NewApi")
public class UploadDescription extends Fragment implements OnClickListener, OnCheckedChangeListener, OnTouchListener{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	ButtonFloat buttonAddPrescription;
	private ImageView mImageView;
	private AlertDialog dialog;
	String filePath,fileName,encodedImage;
	private Uri mImageCaptureUri;
	RadioGroup radioGroupOffer;


	Uri selectedImageUri;
	String  selectedPath;

	private static final int PICK_FROM_CAMERA = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final int PICK_FROM_FILE = 3;

	ImageView prescriptionDoc;
	LinearLayout linearLayoutAddPres;

	ListView listViewPres;
	EditText editTextDoctorName,editTextDoctorPhone,editTextPin;

	AutoCompleteTextView editTextDoctorAdd;
	Button buttonSend,buttonUploadPrescription,buttonNext;
	LinearLayout linearLayoutEmpty;

	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";

	PrescriptionModel objPrescriptionModel;
	RadioButton radioButtonThirty,radioButtonFifteen;
	TextView textViewNote,textViewUploadedPrescrition;
	GridView gridImages;
	LinearLayout layout,layoutAddPhotos;

	private Uri picUri;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private List<String> listOfImagesPath;

	public static final String GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GridView/";

	BitmapFactory.Options bfOptions;
	FileInputStream fs = null;
	Bitmap bm;
	ImageView imageViewClose;
	HorizontalScrollView objHorizontalScrollView;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_uploadaprescription, container,
				false);

		init(view);

		editTextDoctorAdd.setAdapter(new PlacesAutoCompleteAdapter1(getActivity(), R.layout.list_item));
		editTextDoctorAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

				String strLocations = (String) adapterView.getItemAtPosition(i);
				editTextDoctorAdd.setText(strLocations);

				String s = strLocations;
				if (s.contains(",")) {
					String parts[] = s.split("\\,");
					System.out.print(parts[0]);
					String s1 = parts[0];
				}
			}
		});

		return view;
	}

	private void init(View view) {

		objPrescriptionModel  =new PrescriptionModel();

		buttonAddPrescription =(ButtonFloat)view.findViewById(R.id.buttonFloat);
		buttonUploadPrescription =(Button)view.findViewById(R.id.buttonUPloadPrescription);

		//	prescriptionDoc = (ImageView)view.findViewById(R.id.imageViewUploadPrescription);
		linearLayoutAddPres = (LinearLayout)view.findViewById(R.id.linearLayoutAdd);
		//listViewPres = (ListView)view.findViewById(R.id.listViewData);
		editTextDoctorAdd = (AutoCompleteTextView)view.findViewById(R.id.editTextDoctorAdd);
		buttonSend = (Button)view.findViewById(R.id.buttonSendPrescription);
		linearLayoutEmpty = (LinearLayout)view.findViewById(R.id.emptyView);
		layoutAddPhotos = (LinearLayout)view.findViewById(R.id.linearLayoutUploadPhotos);

		radioGroupOffer = (RadioGroup)view.findViewById(R.id.radioGroupOffer);
		radioButtonFifteen = (RadioButton)view.findViewById(R.id.homdeDeleiveryThree);
		radioButtonThirty = (RadioButton)view.findViewById(R.id.homdeDeleiveryeight);
		textViewNote = (TextView)view.findViewById(R.id.textViewNote);
		buttonNext = (Button)view.findViewById(R.id.buttonNext);
		textViewUploadedPrescrition = (TextView)view.findViewById(R.id.textViewUploadedPrescription);


		// Let's get the root layout and add our ImageView
		layout = (LinearLayout) view.findViewById(R.id.root);
		objHorizontalScrollView = (HorizontalScrollView)view.findViewById(R.id.horizontalScrollView1);
		//imageViewClose = (ImageView)view.findViewById(R.id.imageViewClose);

		listOfImagesPath = null;
		listOfImagesPath = RetriveCapturedImagePath();


		if(listOfImagesPath!=null && listOfImagesPath.size()!=0){

			/*			Log.d("list", "size in creaetView"+listOfImagesPath.size());

			//gridImages.setAdapter(new CustomGridImage(getActivity(),listOfImagesPath));

			for(int i=0;i<listOfImagesPath.size();i++){

				ImageView image = new ImageView(getActivity());

				// Now the layout parameters, these are a little tricky at first
				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
						300,
						400);

				FrameLayout framelayout = new FrameLayout(getActivity());
				framelayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
						LayoutParams.MATCH_PARENT));


				ImageView imageView = new ImageView(getActivity());
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setImageResource(R.drawable.ic_account_search_grey600_36dp);
				imageView.setRight(1);

				image.setScaleType
				(ImageView.ScaleType.FIT_XY);
				image.setPadding(10, 0, 10, 0);

				try {
					fs = new FileInputStream(new File(listOfImagesPath.get(i).toString()));

					if(fs!=null) {
						bm=BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
						image.setImageBitmap(bm);
						image.setId(i);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally{
					if(fs!=null) {
						try {
							fs.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				framelayout.addView(image);
				framelayout.addView(imageView);

				layout.addView(framelayout, 0, params);
				buttonUploadPrescription.setText("Add another prescription");
			}
			 */		
		}else{

			textViewUploadedPrescrition.setText("No Prescription Uploded Yet!!!");

			buttonSend.setEnabled(false);

		}

		buttonAddPrescription.setOnClickListener(this);
		buttonSend.setOnClickListener(this);
		buttonUploadPrescription.setOnClickListener(this);
		buttonNext.setOnClickListener(this);

		radioGroupOffer.setOnCheckedChangeListener(this);

		textViewNote.setText
		(Html.fromHtml("Ensure the prescription image captures the doctors details clearly"));
		textViewNote.setMovementMethod(LinkMovementMethod.getInstance());

		SessionManager session = new SessionManager(getActivity());
		User user = session.getUserDetails();

		String address= user.getAddress();
		editTextDoctorAdd.setText(address);

		bfOptions=new BitmapFactory.Options();
		bfOptions.inDither=false;                     //Disable Dithering mode
		bfOptions.inPurgeable=true;                   //Tell to gc that whether it needs free memory, the Bitmap can be cleared
		bfOptions.inInputShareable=true;              //Which kind of reference will be used to recover the Bitmap data after being clear, when it will be used in the future
		bfOptions.inTempStorage=new byte[32 * 1024];

	}

	@Override
	public void onClick(View v) {

		int id = v.getId();
		if (id == R.id.buttonFloat) {
			linearLayoutAddPres.setVisibility(View.VISIBLE);
			buttonAddPrescription.setVisibility(View.GONE);
			linearLayoutEmpty.setVisibility(View.GONE);
			layoutAddPhotos.setVisibility(View.GONE);
			textViewNote.setVisibility(View.GONE);
		} else if (id == R.id.buttonUPloadPrescription) {
			//dialog.show();
			selectImage();
		} else if (id == R.id.buttonSendPrescription) {
			objPrescriptionModel.setDoctorAdd(editTextDoctorAdd.getText().toString());
			objPrescriptionModel.setImagePrescription(encodedImage);
			deleteExistingDirectory();
		} else if (id == R.id.buttonNext) {
			linearLayoutAddPres.setVisibility(View.GONE);
			textViewNote.setVisibility(View.VISIBLE);
			layoutAddPhotos.setVisibility(View.VISIBLE);
		}
	}

	private void deleteExistingDirectory() {

		File dir = new File(GridViewDemo_ImagePath); 
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				new File(dir, children[i]).delete();
			}
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		deleteExistingDirectory();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == PICK_FROM_CAMERA && resultCode == getActivity().RESULT_OK) {  

			Bitmap photo = (Bitmap) data.getExtras().get("data"); 

			String imgcurTime = dateFormat.format(new Date());
			File imageDirectory = new File(GridViewDemo_ImagePath);
			imageDirectory.mkdirs();
			String _path = GridViewDemo_ImagePath + imgcurTime+".jpg";

			Log.d("_path", "_path"+_path);
			try {
				FileOutputStream out = new FileOutputStream(_path);
				photo.compress(Bitmap.CompressFormat.JPEG, 90, out);
				out.close();
			} catch (FileNotFoundException e) {
				e.getMessage();
			} catch (IOException e) {
				e.printStackTrace();
			}

			listOfImagesPath = RetriveCapturedImagePath();

			Log.d("lisofImages", "size in camera view"+listOfImagesPath.size());

			if(listOfImagesPath!=null && listOfImagesPath.size()!=0){

				for(int i=0;i<listOfImagesPath.size();i++){
					// Let's create the missing ImageView
					ImageView image = new ImageView(getActivity());

					// Now the layout parameters, these are a little tricky at first
					FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
							400,
							500);

					FrameLayout framelayout = new FrameLayout(getActivity());
					framelayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 
							LayoutParams.MATCH_PARENT));

					ImageView imageView = new ImageView(getActivity());
					imageView.setScaleType(ImageView.ScaleType.MATRIX);
					imageView.setImageResource(R.drawable.ic_close_white_18dp);
					LinearLayout.LayoutParams layoutParamsImage= new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					layoutParamsImage.gravity = Gravity.LEFT;
					imageView.setLayoutParams(layoutParamsImage);
					imageView.setId(i);

					image.setScaleType
					(ImageView.ScaleType.FIT_XY);
					image.setPadding(10, 0, 10, 0);

					try {
						fs = new FileInputStream(new File(listOfImagesPath.get(i).toString()));

						if(fs!=null) {
							bm=BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
							image.setImageBitmap(bm);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally{
						if(fs!=null) {
							try {
								fs.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}

					framelayout.addView(image);
					framelayout.addView(imageView);

					layout.addView(framelayout, 0, params);

					imageView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							int idImageView = v.getId();
							Log.d("id is:", "id"+idImageView);

							deleteRespectiveImage(idImageView-1);

						}
					});

				}
				buttonUploadPrescription.setText("Add another prescription");

				/**
				 * Convert Bitmap to String
				 */
				ByteArrayOutputStream baos = new ByteArrayOutputStream();  
				photo.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object   
				byte[] b = baos.toByteArray();
				encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
			} 
			else{

				textViewUploadedPrescrition.setText("Uploded Prescriptions");

				buttonSend.setEnabled(false);
			}
		}
		else if (resultCode == getActivity().RESULT_OK && requestCode==PICK_FROM_FILE) {

			layout.removeAllViews();			
			if(data.getData() != null){

				selectedImageUri = data.getData();
				selectedPath = getPath(selectedImageUri,getActivity());

				//listOfImagesPath=RetriveCapturedImagePath();
				listOfImagesPath.add(selectedPath);

				Log.d("lisofImages", "size"+listOfImagesPath.size());				
				Log.d("lisofImages", "size"+listOfImagesPath);				


				if(listOfImagesPath!=null && listOfImagesPath.size()!=0){

					for(int i=0;i<listOfImagesPath.size();i++){

						ImageView image = new ImageView(getActivity());

						// Now the layout parameters, these are a little tricky at first
						FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
								400,
								500);

						FrameLayout framelayout = new FrameLayout(getActivity());
						framelayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
								LayoutParams.MATCH_PARENT));

						ImageView imageView = new ImageView(getActivity());

						imageView.setScaleType(ImageView.ScaleType.MATRIX);
						imageView.setImageResource(R.drawable.ic_close_white_18dp);
						LinearLayout.LayoutParams layoutParamsImage= new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
						layoutParamsImage.gravity = Gravity.RIGHT;
						imageView.setLayoutParams(layoutParamsImage);

						imageView.setId(i);

						image.setScaleType
						(ImageView.ScaleType.MATRIX);
						image.setPadding(10, 0, 10, 0);

						imageView.setOnTouchListener(this);    

						try {
							fs = new FileInputStream(new File(listOfImagesPath.get(i).toString()));

							if(fs!=null) {
								bm=BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
								image.setImageBitmap(bm);
								//image.setId(count);
							}
						} catch (IOException e) {
							e.printStackTrace();
						} finally{
							if(fs!=null) {
								try {
									fs.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
						framelayout.addView(image);
						framelayout.addView(imageView);

						layout.addView(framelayout, 0, params);

						imageView.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {

								int idImageView = v.getId();
								Log.d("id is:", "id"+idImageView);

								deleteRespectiveImage(idImageView);

							}
						});
					}
					buttonUploadPrescription.setText("Add another prescription");
				} 
				else{

					textViewUploadedPrescrition.setText("Uploded Prescriptions");

					buttonSend.setEnabled(false);
				}
			}
		}
	}

	protected void deleteRespectiveImage(int position) {

		listOfImagesPath.remove(position);

		ViewGroup parentLayout = (ViewGroup) objHorizontalScrollView.getChildAt(0);

		if (parentLayout.getChildCount() > 0) {

			Log.d("parent", "parent"+parentLayout.getChildCount());
			if(parentLayout.getChildCount() ==1 ){

				parentLayout.removeView(parentLayout.getChildAt(position));
				Log.d("parent", "parent after"+parentLayout.getChildCount());

			}else{
				Log.d("parent", ""+position);
				parentLayout.removeView(parentLayout.getChildAt((parentLayout.getChildCount()-1)-position));
				Log.d("parent", "parent after"+parentLayout.getChildCount());

			}
		}
	}

	private List<String> RetriveCapturedImagePath() {
		List<String> tFileList = new ArrayList<String>();
		File f = new File(GridViewDemo_ImagePath);
		if (f.exists()) {
			File[] files=f.listFiles();
			Arrays.sort(files);

			for(int i=0; i<files.length; i++){
				File file = files[i];
				if(file.isDirectory())
					continue;
				tFileList.add(file.getPath());
			}
		}
		return tFileList;
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 * @author paulburke
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 * @author paulburke
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 * @author paulburke
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}

	public String getPath(Uri uri, Activity context) {

		/*Log.d("uri", ""+uri);

		String result;
		Cursor cursor = getActivity().getContentResolver().
				query(uri, null, null, null, null);
		if (cursor == null) { // Source is Dropbox or other similar local file path
			result = uri.getPath();
		} else { 
			cursor.moveToFirst(); 
			int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
			result = cursor.getString(idx);
			cursor.close();
		}
		return result;
		if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();


		 String[] largeFileProjection = {MediaStore.Images.ImageColumns.DATA};
	        String largeFileSort =
	        		MediaStore.Images.ImageColumns._ID + "!= 0";
	        Cursor myCursor = getActivity().getContentResolver().query(
	        		uri,
	                largeFileProjection, null, null, null);
	        String largeImagePath = "";
	        try {
	            myCursor.moveToFirst();
	            largeImagePath = myCursor
	                    .getString(myCursor
	                            .getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA));
	        } finally {
	            myCursor.close();
	        }
	        return largeImagePath;*/


		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// LocalStorageProvider
			/*if (isLocalStorageDocument(uri)) {
                // The path is the id
                return DocumentsContract.getDocumentId(uri);
            }*/
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}

				// TODO handle non-primary volumes
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] {
						split[1]
				};

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}else{

			String result;
			Cursor cursor = getActivity().getContentResolver().
					query(uri, null, null, null, null);
			if (cursor == null) { // Source is Dropbox or other similar local file path
				result = uri.getPath();
			} else { 
				cursor.moveToFirst(); 
				int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
				result = cursor.getString(idx);
				cursor.close();
			}
			return result;
		}

		return null;
	}
	private static final boolean DEBUG = false; // Set to true to enable logging

	public static String getDataColumn(Context context, Uri uri, String selection,
			String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = {
				column
		};

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
					null);
			if (cursor != null && cursor.moveToFirst()) {
				if (DEBUG)
					DatabaseUtils.dumpCursor(cursor);

				final int column_index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(column_index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	private void selectImage() {

		final CharSequence[] items = { "Take Photo", "Choose from Library","Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Take Photo")) {
					Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(cameraIntent, PICK_FROM_CAMERA); 
				} else if (items[item].equals("Choose from Library")) {
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(
							Intent.createChooser(intent, "Select File"),
							PICK_FROM_FILE);
				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});

		builder.show();

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		if(checkedId==R.id.homdeDeleiveryThree){

			/**
			 * Save value as per off
			 */
			objPrescriptionModel.setFifteenOff("");

		}else if(checkedId==R.id.homdeDeleiveryeight){

			/**
			 * Save value as per off
			 */
			objPrescriptionModel.setThirtyOff("");

		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {





		return false;
	}
}
