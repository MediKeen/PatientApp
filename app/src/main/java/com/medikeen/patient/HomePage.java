package com.medikeen.patient;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.medikeen.R;
import com.medikeen.adapters.PrescriptionAdapter;
import com.medikeen.asyncTask.SearchTask;
import com.medikeen.asyncTask.SendPrescriptionTask;
import com.medikeen.dataModel.PatientDescriptionDTO;
import com.medikeen.dataModel.PrescriptionModel;
import com.medikeen.dataModel.SearchModel;
import com.medikeen.util.ButtonFloat;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

@SuppressLint("NewApi")
public class HomePage extends AppCompatActivity implements OnClickListener {

    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    ImageView physician, physician1, physician2, tick, imageViewOrtho, imageViewOrtho1, tick1, imageViewSurgeon, imageViewSurgeon1, imageViewSurgeontick, imageViewDentist, imageViewDentist1, imageViewDentisttick, imageViewPedia, imageViewPedia1, imageViewPediatick, imageViewGyno, imageViewGyno1, imageViewGynotick;
    TextView textViewPhy, textViewPhy1;
    ListView listViewpatient;
    Button mSearch, mShowMap;
    String[] patientList;
    ArrayList<PatientDescriptionDTO> patientslist = new ArrayList<PatientDescriptionDTO>();
    ImageView prescriptionDoc;
    LinearLayout linearLayoutAddPres;
    ListView listViewPres;
    EditText editTextDoctorName, editTextDoctorPhone, editTextPin;
    AutoCompleteTextView editTextDoctorAdd;
    Button buttonSend;
    LinearLayout linearLayoutEmpty;
    Uri selectedImageUri;
    String selectedPath, encodedImage;
    ButtonFloat buttonAddPrescription;
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    private TabHost myTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        init();
        myTabHost = (TabHost) findViewById(R.id.TabHostpatient);
        myTabHost.setup();

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        /**
         * Creating Tabs
         */
        TabSpec liveTrip = myTabHost.newTabSpec("Saved History");
        liveTrip.setIndicator("Saved History");
        liveTrip.setContent(R.id.savedprescription);
        myTabHost.addTab(liveTrip);

        myTabHost.addTab(myTabHost.newTabSpec("Find Doctor").setIndicator("Find Doctor").setContent(R.id.searchdocotr));
        myTabHost.addTab(myTabHost.newTabSpec("Upload Prescription").setIndicator("Upload Prescription").setContent(R.id.uploadprescription));


        physician.setOnClickListener(this);
        imageViewOrtho.setOnClickListener(this);
        imageViewSurgeon.setOnClickListener(this);
        imageViewDentist.setOnClickListener(this);
        imageViewPedia.setOnClickListener(this);
        imageViewGyno.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mShowMap.setOnClickListener(this);

        buttonAddPrescription.setOnClickListener(this);
        prescriptionDoc.setOnClickListener(this);
        buttonSend.setOnClickListener(this);


        /**
         * Address Adapter
         */
        editTextDoctorAdd.setAdapter(new PlacesAutoCompleteAdapter1(this, R.layout.list_item));
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

		/*
         * For Fetching a record for a particular login patient just pass the id of user
		 * and pass it to history task.
		 * 		
		 */
		/*
		//----------------For Fetching records of history(jsut pass the patient id)----------

		LoginModel objLoginModel = new LoginModel();

		//------Set userID to find history-------
		objLoginModel.setmUserId("1");

		//new HistoryTask(HomePage.this).execute(objLoginModel);
		 */
    }

    private void init() {

        physician = (ImageView) findViewById(R.id.ImageviewPhysician);
        physician2 = (ImageView) findViewById(R.id.ImageviewPhysician2);
        textViewPhy = (TextView) findViewById(R.id.textViewphy);
        tick = (ImageView) findViewById(R.id.imageViewtick);

        imageViewOrtho = (ImageView) findViewById(R.id.ImageviewOrtho);
        imageViewOrtho1 = (ImageView) findViewById(R.id.ImageviewOrtho1);
        tick1 = (ImageView) findViewById(R.id.imageViewOrtho);

        imageViewSurgeon = (ImageView) findViewById(R.id.Imageviewsurgeon);
        imageViewSurgeon1 = (ImageView) findViewById(R.id.Imageviewsurgeon1);
        imageViewSurgeontick = (ImageView) findViewById(R.id.Imageviewsurgeontick);


        imageViewDentist = (ImageView) findViewById(R.id.ImageviewDentist);
        imageViewDentist1 = (ImageView) findViewById(R.id.ImageviewDentist1);
        imageViewDentisttick = (ImageView) findViewById(R.id.ImageviewDentisttick);


        imageViewPedia = (ImageView) findViewById(R.id.ImageviewPedia);
        imageViewPedia1 = (ImageView) findViewById(R.id.ImageviewPedia1);
        imageViewPediatick = (ImageView) findViewById(R.id.ImageviewPediatick);


        imageViewGyno = (ImageView) findViewById(R.id.Imageviewgyno);
        imageViewGyno1 = (ImageView) findViewById(R.id.Imageviewgyno1);
        imageViewGynotick = (ImageView) findViewById(R.id.Imageviewgynotick);


        mShowMap = (Button) findViewById(R.id.buttonMap);
        mSearch = (Button) findViewById(R.id.buttonSearch);

        //------------------------Find History----------
        listViewpatient = (ListView) findViewById(R.id.listView1);


        buttonAddPrescription = (ButtonFloat) findViewById(R.id.buttonFloat);
        //	prescriptionDoc = (ImageView)findViewById(R.id.imageViewUploadPrescription);
        linearLayoutAddPres = (LinearLayout) findViewById(R.id.linearLayoutAdd);
        listViewPres = (ListView) findViewById(R.id.listViewData);
        editTextDoctorAdd = (AutoCompleteTextView) findViewById(R.id.editTextDoctorAdd);
        buttonSend = (Button) findViewById(R.id.buttonSendPrescription);
        linearLayoutEmpty = (LinearLayout) findViewById(R.id.emptyView);


		/*
		 * To apply a arraylist of history of user copy following code in onPostExecute of HistoryTask
		 * Just pass thr arralist that you got from server
		 */
        //------------Manipulate if any histoy found in historytask onPostExecute----//
        //------------Else shows image only--------------------

        PatientDescriptionDTO objDescriptionDTO = new PatientDescriptionDTO();

        for (int i = 0; i < 5; i++) {
            objDescriptionDTO.setMatchUserDetails(new PatientDescriptionDTO("Test User", "Kothrud,Pune", ""));
        }

        patientList = new String[PatientDescriptionDTO.prescriptionList.size()];

        for (int i = 0; i < patientList.length; i++) {

            patientList[i] = PatientDescriptionDTO.prescriptionList.get(i).patientName;

            PatientDescriptionDTO group = new PatientDescriptionDTO(patientList[i], patientList[i], patientList[i]);
            patientslist.add(group);

        }

        PrescriptionAdapter tripadapter = new PrescriptionAdapter(this, patientslist);
        listViewpatient.setAdapter(tripadapter);

    }

    @Override
    public void onClick(View arg0) {

        int id = arg0.getId();
        if (id == R.id.ImageviewPhysician) {
            if (physician.getTag() != null && physician.getTag().toString().equals("ImageviewPhysician")) {
                physician.setTag("ImageviewPhysician2");
                physician2.setVisibility(View.VISIBLE);
                tick.setVisibility(View.VISIBLE);
            } else {
                physician.setTag("ImageviewPhysician");
                physician2.setVisibility(View.GONE);
                tick.setVisibility(View.GONE);
            }
        } else if (id == R.id.ImageviewOrtho) {
            if (imageViewOrtho.getTag() != null && imageViewOrtho.getTag().toString().equals("ImageviewOrtho")) {
                imageViewOrtho.setTag("ImageviewOrtho1");
                imageViewOrtho1.setVisibility(View.VISIBLE);
                tick1.setVisibility(View.VISIBLE);
            } else {
                imageViewOrtho.setTag("ImageviewOrtho");
                imageViewOrtho1.setVisibility(View.GONE);
                tick1.setVisibility(View.GONE);
            }
        } else if (id == R.id.Imageviewsurgeon) {
            if (imageViewSurgeon.getTag() != null && imageViewSurgeon.getTag().toString().equals("Imageviewsurgeon")) {
                imageViewSurgeon.setTag("Imageviewsurgeon1");
                imageViewSurgeon1.setVisibility(View.VISIBLE);
                imageViewSurgeontick.setVisibility(View.VISIBLE);
            } else {
                imageViewSurgeon.setTag("Imageviewsurgeon");
                imageViewSurgeon1.setVisibility(View.GONE);
                imageViewSurgeontick.setVisibility(View.GONE);
            }
        } else if (id == R.id.ImageviewDentist) {
            if (imageViewDentist.getTag() != null && imageViewDentist.getTag().toString().equals("ImageviewDentist")) {
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
            if (imageViewPedia.getTag() != null && imageViewPedia.getTag().toString().equals("ImageviewPedia")) {
                imageViewPedia.setTag("ImageviewDentist1");
                imageViewPedia1.setVisibility(View.VISIBLE);
                imageViewPediatick.setVisibility(View.VISIBLE);
            } else {

                imageViewPedia.setTag("ImageviewPedia");
                imageViewPedia1.setVisibility(View.GONE);
                imageViewPediatick.setVisibility(View.GONE);
            }
        } else if (id == R.id.Imageviewgyno) {
            if (imageViewGyno.getTag() != null && imageViewGyno.getTag().toString().equals("Imageviewgyno")) {
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
            Intent objSearch = new Intent(HomePage.this, SearchResult.class);
            startActivity(objSearch);
        } else if (id == R.id.buttonMap) {
            SearchModel objSearchModelMap = new SearchModel();
            //----Which specialist is selected pass id by comma sepearated-----------
            objSearchModelMap.setSearchIds("1");
            objSearchModelMap.setFromActivity("1001");
            new SearchTask(HomePage.this).execute(objSearchModelMap);
        } else if (id == R.id.buttonFloat) {
            linearLayoutAddPres.setVisibility(View.VISIBLE);
            listViewPres.setVisibility(View.GONE);
            buttonAddPrescription.setVisibility(View.GONE);
            linearLayoutEmpty.setVisibility(View.GONE);
        } else if (id == R.id.buttonSendPrescription) {
            PrescriptionModel objPrescriptionModel = new PrescriptionModel();
            objPrescriptionModel.setDoctorName(editTextDoctorName.getText().toString());
            objPrescriptionModel.setDoctorPhone(editTextDoctorPhone.getText().toString());
            objPrescriptionModel.setDoctorAdd(editTextDoctorAdd.getText().toString());
            objPrescriptionModel.setPinCodeAvailiablity(editTextPin.getText().toString());
            objPrescriptionModel.setImagePrescription(encodedImage);
            new SendPrescriptionTask(HomePage.this).execute(objPrescriptionModel);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (data.getData() != null) {
                selectedImageUri = data.getData();
            } else {

            }

            if (requestCode == PICK_FROM_CAMERA && resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                //selectedPath = getPath(selectedImageUri);
                prescriptionDoc.setImageBitmap(photo);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                Log.d("image is:", encodedImage);
            }

            if (requestCode == PICK_FROM_FILE) {
                selectedPath = getPath(selectedImageUri);
                //prescriptionDoc.setImageURI(selectedImageUri);
                Log.d("selectedPath1 : ", selectedPath);

                Bitmap bm = BitmapFactory.decodeFile(selectedPath);
                prescriptionDoc.setImageBitmap(bm);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                Log.d("image is:", encodedImage);

            }

        }

    }

    public String getPath(Uri uri) {

        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = managedQuery(uri, projection, null, null, null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);

    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
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

}
