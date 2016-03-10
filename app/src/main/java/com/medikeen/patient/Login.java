package com.medikeen.patient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.gsm.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.medikeen.patient.R;
import com.medikeen.asyncTask.LoginTask;
import com.medikeen.dataModel.LoginModel;
import com.medikeen.datamodels.User;
import com.medikeen.util.ConnectionDetector;
import com.medikeen.util.SessionManager;

import java.util.Random;

public class Login extends AppCompatActivity implements OnClickListener {

    EditText mUsername, mPassword, mEmailForgot;
    TextView textViewCreateAccount, textViewforgot;
    Button mLogin, mforgot, mCreateAccountBtn;
    ImageButton buttonSearch;
    SharedPreferences sp;
    LoginModel loginModel;

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                .matches();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        init();

        // textViewCreateAccount.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        textViewforgot.setOnClickListener(this);
        mCreateAccountBtn.setOnClickListener(this);

        // check if user is already logged in and try to login again.
        SessionManager sessionManager = new SessionManager(
                getApplicationContext());

        // we need to validate if the password is changed or it is right.
        if (sessionManager.isLoggedIn()) {
            User user = sessionManager.getUserDetails();
            Toast.makeText(getApplicationContext(),
                    "Welcome " + user.getFirstName(), Toast.LENGTH_SHORT)
                    .show();
//			Intent intent = new Intent(Login.this, LandingActivity.class);
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void init() {
        mCreateAccountBtn = (Button) findViewById(R.id.textViewCreateBtn);
        mUsername = (EditText) findViewById(R.id.editTextUsernameLogin);
        mPassword = (EditText) findViewById(R.id.editTextPasswordLogin);
        mLogin = (Button) findViewById(R.id.buttonLoginLogin);
        textViewCreateAccount = (TextView) findViewById(R.id.textViewCreate);
        textViewforgot = (TextView) findViewById(R.id.textViewforgot);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonLoginLogin) {
            loginModel = new LoginModel();
            String userName = mUsername.getText().toString();
            String password = mPassword.getText().toString();

            loginModel.setmPassword(password);
            loginModel.setmUsername(userName);

            if (userName.compareTo("") == 0 || password.compareTo("") == 0) {
                if (password.compareTo("") == 0) {
                    mPassword.setError("Please Enter a password");
                }
                if (userName.compareTo("") == 0) {
                    mUsername.setError("Please Enter a username");
                }
                return;
            }

            if (isValidEmail(userName)) {

                AsyncTask<Void, Boolean, Boolean> connectionDetectorTask = new ConnectionDetector()
                        .execute();

                boolean result = false;
                try {
                    result = connectionDetectorTask.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (result) {
                    AsyncTask<LoginModel, String, String> task = new LoginTask(
                            Login.this).execute(loginModel);
                } else {
                    createDialogForInternetConnection();
                }

            } else {
                Toast.makeText(Login.this,
                        "Please enter a valid email address",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.textViewCreate) {

            Intent registrationPageIntent = new Intent(Login.this,
                    RegistrationActivity.class);
            startActivity(registrationPageIntent);

        } else if (id == R.id.textViewforgot) {
            Intent forgotPasswordIntent = new Intent(Login.this,
                    ForgotPasswordActivity.class);
            startActivity(forgotPasswordIntent);
        } else if (id == R.id.textViewCreateBtn) {

            Intent registrationPageIntent = new Intent(Login.this,
                    RegistrationActivity.class);
            startActivity(registrationPageIntent);

        }
    }

    private void createDialogForInternetConnection() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                Login.this);
        alertDialog.setTitle("No internet connection.");
        alertDialog
                .setMessage("Please check your internet setting and try again!");

        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.setNegativeButton("RETRY",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AsyncTask<Void, Boolean, Boolean> connectionDetectorTask = new ConnectionDetector()
                                .execute();

                        boolean result = false;
                        try {
                            result = connectionDetectorTask.get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (result) {
                            AsyncTask<LoginModel, String, String> task = new LoginTask(
                                    Login.this).execute(loginModel);
                        } else {
                            createDialogForInternetConnection();
                        }
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    @SuppressWarnings({"deprecation", "unused"})
    private void sendOTP(String stringPhone) {

        /**
         * Send Message with OTP
         */
        int randomInt = 0;
        Random randomGenerator = new Random();
        for (int idx = 1; idx <= 10; ++idx) {
            randomInt = randomGenerator.nextInt(9000000) + 1000000;
        }

        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(
                SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        // ---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        // ---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(stringPhone, null, "Your OTP is: " + randomInt,
                sentPI, deliveredPI);

        createDialogForOTP(randomInt);
    }

    private void createDialogForOTP(final int OTP) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);
        alertDialog.setTitle("One Time Password");
        alertDialog.setMessage("Enter OTP");

        final EditText input = new EditText(Login.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        input.setLayoutParams(lp);

        alertDialog.setView(input);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        int OTPpassword = Integer.parseInt(input.getText()
                                .toString());

                        Log.d("OTP", "" + OTPpassword);

                        if (OTPpassword != -1) {

                            Log.d("OTP1", "" + OTPpassword);

                            if (OTP == OTPpassword) {
                                Toast.makeText(getApplicationContext(),
                                        "OTP Matched", Toast.LENGTH_SHORT)
                                        .show();
                                Intent objLanding = new Intent(Login.this,
                                        LandingActivity.class);
                                startActivity(objLanding);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Wrong OTP!", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

}
