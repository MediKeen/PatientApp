package com.medikeen.patient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medikeen.patient.R;

public class AboutUs extends Fragment {

    /*
     * (non-Javadoc)
     *
     * @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_aboutus, container,
                false);

        TextView aboutUs = (TextView) view.findViewById(R.id.about_us);
        aboutUs.setText(Html
                .fromHtml("Medikeen helps you purchase prescription drugs from the comfort of your home.<br> We at Medikeen understand how important medicines and prescription drugs are to sometimes just lead a normal life. We understand the pain and inconvenience caused while purchasing these medicines and the high costs of refilling prescriptions.<br> Medikeen aims at eliminating the inefficient links in the pharmaceutical distribution chain to bring you the best service and prices. Armed with the latest in web technology and a reliable network of pharmacy partners, Medikeen will revolutionize the way patients order and consume medicines.<br> <br> How it works:<br> 1) Upload a photo of your prescription<br> 2) Receive your order and a registered bill within 8 hours of placing the order<br> 3) Be pleasantly surprised that the bill amount is at least 10% lower than at your local pharmacy<br> <br> SIMPLE."));

        return view;
    }

}
