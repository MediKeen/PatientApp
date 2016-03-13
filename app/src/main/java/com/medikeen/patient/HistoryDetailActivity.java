package com.medikeen.patient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public class HistoryDetailActivity extends AppCompatActivity {

    private String TAG = "HISTORY DETAIL ACTIVITY";

    ImageViewTouch historyImage;

    String orderNumberStr, orderStatusStr, recipientName, cost;

    LinearLayout statusHolder;
    TextView orderNumber, orderStatus, patientName, orderCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderNumberStr = getIntent().getStringExtra("ORDER NUMBER");
        orderStatusStr = getIntent().getStringExtra("ORDER STATUS");
        recipientName = getIntent().getStringExtra("RECIPIENT NAME");
        cost = getIntent().getStringExtra("COST");

        statusHolder = (LinearLayout) findViewById(R.id.status_holder);
        orderNumber = (TextView) findViewById(R.id.order_number);
        orderStatus = (TextView) findViewById(R.id.order_status);
        patientName = (TextView) findViewById(R.id.patient_name);
        orderCost = (TextView) findViewById(R.id.order_cost);

        if (Double.valueOf(cost) == 0.0) {
            orderCost.setVisibility(View.GONE);
        } else {
            orderCost.setVisibility(View.VISIBLE);
        }

        orderNumber.setText("Order Number: " + orderNumberStr);
        orderStatus.setText("Order Status: " + orderStatusStr);
        patientName.setText("Patient Name: " + recipientName);
        orderCost.setText("Order Cost: Rs. " + Double.valueOf(cost));

        historyImage = (ImageViewTouch) findViewById(R.id.history_image);

        Picasso.with(HistoryDetailActivity.this)
                .load("http://www.medikeen.com/android_api/prescription/uploads/"
                        + orderNumberStr).into(historyImage);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.history_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
