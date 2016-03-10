package com.medikeen.patient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.medikeen.patient.R;
import com.medikeen.asyncTask.RegisterTask;
import com.medikeen.dataModel.RegisterModel;
import com.medikeen.datamodels.serialized.RegistrationResponse;
import com.medikeen.util.ConnectionDetector;
import com.medikeen.util.SessionManager;

import java.util.concurrent.ExecutionException;

public class RegistrationActivity extends AppCompatActivity implements OnClickListener {

    EditText mFirstName, mLastName, mEmailId, mContactNo, mPassword,
            mConfirmPassword;
    AutoCompleteTextView mAddress;
    Button mRegister, mLoginRegister;

    RegisterModel objRegisterModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().hide();

        init();

        mLoginRegister.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }

    private void init() {

        mRegister = (Button) findViewById(R.id.buttonRegister);
        mLoginRegister = (Button) findViewById(R.id.buttonLogin);
        mFirstName = (EditText) findViewById(R.id.editTextFirstName);
        mLastName = (EditText) findViewById(R.id.editTextLastName);
        mEmailId = (EditText) findViewById(R.id.editTextEmailId);
        mContactNo = (EditText) findViewById(R.id.editTextContactNo);
        mAddress = (AutoCompleteTextView) findViewById(R.id.editTextAddress);
        mPassword = (EditText) findViewById(R.id.editTextPassword);
        mConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);

        mAddress.setAdapter(new PlacesAutoCompleteAdapter1(this,
                R.layout.list_item));
        mAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int i, long l) {

                String strLocations = (String) adapterView.getItemAtPosition(i);
                mAddress.setText(strLocations);

                String s = strLocations;
                if (s.contains(",")) {
                    String parts[] = s.split("\\,");
                    System.out.print(parts[0]);
                    String s1 = parts[0];
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registration, menu);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonRegister) {
            if (mFirstName.getText().toString().equalsIgnoreCase("")) {
                showdialog();
            } else if (mLastName.getText().toString().equalsIgnoreCase("")) {
                showdialog();

            } else if (mEmailId.getText().toString().equalsIgnoreCase("")) {
                showdialog();

            } else if (mPassword.getText().toString().equalsIgnoreCase("")) {
                showdialog();

            } else if (mContactNo.getText().toString().equalsIgnoreCase("")) {
                showdialog();

            } else if (mAddress.getText().toString().equalsIgnoreCase("")) {
                showdialog();

            } else if (mConfirmPassword.getText().toString()
                    .equalsIgnoreCase("")) {
                showdialog();

            } else {

                objRegisterModel = new RegisterModel();
                objRegisterModel.setFirstName(mFirstName.getText().toString());
                objRegisterModel.setLastName(mLastName.getText().toString());
                objRegisterModel.setEmailId(mEmailId.getText().toString());
                objRegisterModel.setPassword(mPassword.getText().toString());
                objRegisterModel.setAddress(mAddress.getText().toString());
                objRegisterModel.setUserName(mConfirmPassword.getText()
                        .toString());

                String contactStr = mContactNo.getText().toString();

                if (contactStr.length() < 10) {
                    mContactNo.setError("Please enter a valid number");
                } else {
                    objRegisterModel.setContactNo("91"
                            + mContactNo.getText().toString());

                    AsyncTask<Void, Boolean, Boolean> taskConn = new ConnectionDetector()
                            .execute();

                    boolean resultConn = false;
                    try {
                        resultConn = taskConn.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (resultConn == true) {
                        final AsyncTask<RegisterModel, String, String> task = new RegisterTask(
                                RegistrationActivity.this)
                                .execute(objRegisterModel);

                        String result = "";
                        try {
                            result = task.get();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        Gson gson = new Gson();

                        RegistrationResponse response = gson.fromJson(result,
                                RegistrationResponse.class);

                        if (response.success == 1) {

                            //SessionManager session = new SessionManager(
                            //			getApplicationContext());
                            //	session.createLoginSession(true,
                            //			response.user.person_id, mFirstName.getText()
                            //					.toString(), mLastName.getText()
                            //			.toString(), mEmailId.getText()
                            //					.toString(), mAddress.getText()
                            // .toString(), mContactNo.getText()
                            //					.toString());

                            Intent intent = new Intent(
                                    RegistrationActivity.this,
                                    OtpActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else if (response.success == 0) {
                            showErrorDialog(response.error_msg, "Login",
                                    "Cancel");
                        }
                    } else {
                        createDialogForInternetConnection();
                    }
                }

            }
        } else if (id == R.id.buttonLogin) {
            RegistrationActivity.this.onBackPressed();
        }
    }

    private void createDialogForInternetConnection() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                RegistrationActivity.this);
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
                        AsyncTask<Void, Boolean, Boolean> taskConn = new ConnectionDetector()
                                .execute();

                        boolean resultConn = false;
                        try {
                            resultConn = taskConn.get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (resultConn == true) {
                            final AsyncTask<RegisterModel, String, String> task = new RegisterTask(
                                    RegistrationActivity.this)
                                    .execute(objRegisterModel);

                            String result = "";
                            try {
                                result = task.get();
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            Gson gson = new Gson();

                            RegistrationResponse response = gson.fromJson(
                                    result, RegistrationResponse.class);

                            if (response.success == 1) {

                                SessionManager session = new SessionManager(
                                        getApplicationContext());
                                session.createLoginSession(true,
                                        response.user.person_id, mFirstName
                                                .getText().toString(),
                                        mLastName.getText().toString(),
                                        mEmailId.getText().toString(), mAddress
                                                .getText().toString(),
                                        mContactNo.getText().toString());

                                Intent intent = new Intent(
                                        RegistrationActivity.this,
                                        OtpActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else if (response.success == 0) {
                                showErrorDialog(response.error_msg, "Login",
                                        "Cancel");
                            }
                        } else {
                            createDialogForInternetConnection();
                        }
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    private void showErrorDialog(String error_msg, String ok_button_string,
                                 String cancel_button_string) {
        final Dialog mErrorDialog = new Dialog(RegistrationActivity.this);
        mErrorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mErrorDialog.setContentView(R.layout.register_error_dialog);
        mErrorDialog.setCancelable(false);
        mErrorDialog.setCanceledOnTouchOutside(false);
        mErrorDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView mErrorMsg = (TextView) mErrorDialog
                .findViewById(R.id.error_msg);
        mErrorMsg.setText(error_msg);

        Button mButtonOk = (Button) mErrorDialog.findViewById(R.id.button_ok);
        Button mButtonCancel = (Button) mErrorDialog
                .findViewById(R.id.button_cancel);

        mButtonOk.setText(ok_button_string);
        mButtonCancel.setText(cancel_button_string);

        // LOGIN BUTTON
        mButtonOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // LOGIN BUTTON
                RegistrationActivity.this.onBackPressed();
                mErrorDialog.dismiss();
            }
        });

        mButtonCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mErrorDialog.dismiss();
            }
        });

        mErrorDialog.show();
    }

    @SuppressWarnings("deprecation")
    private void showdialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(
                RegistrationActivity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Warning");

        // Setting Dialog Message
        alertDialog.setMessage("Please enter all the information");

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
            }
        });
        alertDialog.show();
    }
}
