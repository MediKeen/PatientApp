package com.medikeen.patient;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.FloatMath;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medikeen.R;
import com.squareup.picasso.Picasso;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public class HistoryDetailActivity extends AppCompatActivity {

    private String TAG = "HISTORY DETAIL ACTIVITY";

    String resourceId, resourceType;

    ImageViewTouch historyImage;

    String orderNumberStr, orderStatusStr;

    LinearLayout statusHolder;
    TextView orderNumber, orderStatus, patientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderNumberStr = getIntent().getStringExtra("ORDER NUMBER");
        orderStatusStr = getIntent().getStringExtra("ORDER STATUS");

        resourceId = getIntent().getStringExtra("resource_id");
        resourceType = getIntent().getStringExtra("resource_type");

        statusHolder = (LinearLayout) findViewById(R.id.status_holder);
        orderNumber = (TextView) findViewById(R.id.order_number);
        orderStatus = (TextView) findViewById(R.id.order_status);
        patientName = (TextView) findViewById(R.id.patient_name);

//        orderNumber.setText("Order Number: " + orderNumberStr);
//        orderStatus.setText("Order Status: " + orderStatusStr);
//        patientName.setText("Patient Name: " + "");

        historyImage = (ImageViewTouch) findViewById(R.id.history_image);

        Picasso.with(HistoryDetailActivity.this)
                .load("http://www.medikeen.com/android_api/prescription/uploads/"
                        + resourceId + "." + resourceType).into(historyImage);

//        historyImage.setOnTouchListener(this);

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
