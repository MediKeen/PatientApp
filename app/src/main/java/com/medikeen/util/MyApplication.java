package com.medikeen.util;

import android.app.Application;

import com.medikeen.patient.R;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(
        formUri = "http://www.medikeen.com/android_api/reports/reportupload.php",
//formUriBasicAuthLogin = "yourlogin", // optional
//formUriBasicAuthPassword = "y0uRpa$$w0rd", // optional
        httpMethod = org.acra.sender.HttpSender.Method.POST,
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // The following line triggers the initialization of ACRA
        ACRA.init(this);
    }
}