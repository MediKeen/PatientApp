package com.medikeen.patient;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Window;
import android.view.WindowManager;

import com.medikeen.R;

public class CameraActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide the status bar.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            android.app.FragmentManager manager = getFragmentManager();
            android.app.Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
            if (fragment == null) {
                fragment = Camera2Fragment.newInstance();
            }
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        } else {
            FragmentManager manager = getSupportFragmentManager();
            Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
            if (fragment == null) {
                fragment = new CameraFragment();
            }
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }
}