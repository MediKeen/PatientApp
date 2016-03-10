package com.medikeen.patient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.medikeen.patient.R;
import com.medikeen.datamodels.User;
import com.medikeen.util.ConnectionDetector;
import com.medikeen.util.SessionManager;

public class SplashActivity extends AppCompatActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        sessionManager = new SessionManager(getApplicationContext());

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                AsyncTask<Void, Boolean, Boolean> task = new ConnectionDetector()
                        .execute();

                boolean result = false;
                try {
                    result = task.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (result == true) {
                    if (sessionManager.isLoggedIn()) {
                        User user = sessionManager.getUserDetails();
                        Toast.makeText(getApplicationContext(),
                                "Welcome " + user.getFirstName(),
                                Toast.LENGTH_SHORT).show();

//                        Intent intent = new Intent(SplashActivity.this,
//                                LandingActivity.class);

                        Intent intent = new Intent(SplashActivity.this,
                                MainActivity.class);

                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this,
                                Login.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(
                            SplashActivity.this,
                            "Please check your internet connection and try again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, 2000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
