package com.medikeen.patient;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.medikeen.patient.R;
import com.medikeen.adapters.PrescriptionAdapter;
import com.medikeen.dataModel.PatientDescriptionDTO;

import java.util.ArrayList;

public class SavedPrescription extends Fragment {


    ListView listViewpatient;
    String[] patientList;
    ArrayList<PatientDescriptionDTO> patientslist = new ArrayList<PatientDescriptionDTO>();

    /* (non-Javadoc)
     * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container,
                false);

        init(view);

		/*
         * To apply a arraylist of histoy of user copy following code in onPostExecute of HistoryTask
		 * Just pass the arralist that you got from server
		 * 
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

        PrescriptionAdapter tripadapter = new PrescriptionAdapter(getActivity(), patientslist);
        listViewpatient.setAdapter(tripadapter);

        return view;

    }

    private void init(View view) {
        //------------------------Find History----------
        listViewpatient = (ListView) view.findViewById(R.id.listView1);

    }


}
