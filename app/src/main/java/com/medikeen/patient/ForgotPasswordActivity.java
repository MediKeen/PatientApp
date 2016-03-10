package com.medikeen.patient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.medikeen.patient.R;
import com.medikeen.asyncTask.ForgotPasswordTask;
import com.medikeen.dataModel.ResetPasswordModel;
import com.medikeen.util.ConnectionDetector;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText mEmailForgot;
    Button mforgot;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ResetPasswordModel objResetModel;

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                .matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frogot_password);
        getSupportActionBar().hide();

        mEmailForgot = (EditText) findViewById(R.id.editTextEmailForgot);
        mforgot = (Button) findViewById(R.id.buttonForgot);

        mforgot.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (isValidEmail(mEmailForgot.getText().toString())) {

                    objResetModel = new ResetPasswordModel();
                    objResetModel.setTag("generatecode");
                    objResetModel.setEmail(mEmailForgot.getText().toString());

                    AsyncTask<Void, Boolean, Boolean> taskConn = new ConnectionDetector()
                            .execute();

                    boolean resultConn = false;
                    try {
                        resultConn = taskConn.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (resultConn == true) {
                        new ForgotPasswordTask(ForgotPasswordActivity.this)
                                .execute(objResetModel);
                    } else {
                        createDialogForInternetConnection();
                    }
                } else {
                    Toast.makeText(ForgotPasswordActivity.this,
                            "Please enter a valid email address",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createDialogForInternetConnection() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                ForgotPasswordActivity.this);
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
                            new ForgotPasswordTask(ForgotPasswordActivity.this)
                                    .execute(objResetModel);
                        } else {
                            createDialogForInternetConnection();
                        }
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.frogot_password, menu);
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
