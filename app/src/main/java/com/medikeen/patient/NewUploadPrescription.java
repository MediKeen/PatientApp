package com.medikeen.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medikeen.patient.R;

public class NewUploadPrescription extends Fragment implements OnClickListener {

    public static int tabIndex = 0;
    FloatingActionButton buttonAddPrescription;
    AttachPrescription newFragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_uploadaprescription,
                container, false);

        TextView medikeen = (TextView) view.findViewById(R.id.medikeen);
        medikeen.setText(Html
                .fromHtml("<font color=\"#1E88E5\">M</font>edi<font color=\"#E53935\">K</font>een"));

        init(view);

        return view;
    }

    private void init(View view) {

        buttonAddPrescription = (FloatingActionButton) view
                .findViewById(R.id.buttonFloat);

        buttonAddPrescription.setOnClickListener(this);

    }

    @Override
    public void onClick(View arg0) {

        int id = arg0.getId();
        if (id == R.id.buttonFloat) {

            Intent attachPrescriptionsIntent = new Intent(
                    NewUploadPrescription.this.getActivity(),
                    AttachPrescription.class);
            startActivity(attachPrescriptionsIntent);

            tabIndex = 1;
        }
    }
}
