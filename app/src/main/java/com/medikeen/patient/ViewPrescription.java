package com.medikeen.patient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.medikeen.patient.R;
import com.medikeen.adapters.PrescriptionTabAdapter;
import com.medikeen.dataModel.PatientDescriptionDTO;
import com.medikeen.dataModel.PrescriptionDTO;

import java.util.ArrayList;

public class ViewPrescription extends AppCompatActivity {

	/* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
	 */

    ListView listTabdescription;
    ArrayList<PrescriptionDTO> patientslist = new ArrayList<PrescriptionDTO>();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_prescription);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //------------------------Find History----------
        listTabdescription = (ListView) findViewById(R.id.listView1Tabdesc);

        //------------Manipulate if any histoy found in historytask onPostExecute----//
        //------------Else shows image only--------------------

        PrescriptionDTO objDescriptionDTO = new PrescriptionDTO();

        for (int i = 0; i < 5; i++) {
            objDescriptionDTO.setMatchUserDetails(new PrescriptionDTO("Tab1", "Morning"));
        }

        String[] patientList = new String[PatientDescriptionDTO.prescriptionList.size()];

        for (int i = 0; i < patientList.length; i++) {

            patientList[i] = PatientDescriptionDTO.prescriptionList.get(i).patientName;

            PrescriptionDTO group = new PrescriptionDTO(patientList[i], patientList[i]);
            patientslist.add(group);
        }

        PrescriptionTabAdapter tripadapter = new PrescriptionTabAdapter(this, patientslist);
        listTabdescription.setAdapter(tripadapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

        }
        return true;
    }
}
