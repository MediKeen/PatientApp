package com.medikeen.patient;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.medikeen.patient.R;
import com.medikeen.asyncTask.PasswordResetCodeVerificationAsyncTask;
import com.medikeen.dataModel.ResetPasswordModel;

public class PasswordResetCodeVerificationActivity extends AppCompatActivity {

    private static final String RESET_PASSWORD = "RESET_PASSWORD";
    EditText mVerificationCode;
    Button mButtonVerifyCode;
    SharedPreferences sp;
    Editor edit;
    String emailStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset_code_verification);
        getSupportActionBar().hide();

        sp = getSharedPreferences(RESET_PASSWORD, MODE_PRIVATE);
        edit = sp.edit();

        emailStr = sp.getString("RESET_EMAIL", null);

        mVerificationCode = (EditText) findViewById(R.id.editTextVerificationCode);
        mButtonVerifyCode = (Button) findViewById(R.id.buttonVerifyCode);

        mButtonVerifyCode.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String verficationCodeStr = mVerificationCode.getText().toString();

                Log.e("VERIFICATION CODE: ", "VERIFICATION CODE: " + verficationCodeStr);

                if (verficationCodeStr.equalsIgnoreCase("") || verficationCodeStr.isEmpty()) {
                    mVerificationCode.setError("Please enter the code");
                } else {

                    ResetPasswordModel objResetModel = new ResetPasswordModel();
                    objResetModel.setTag("validatecode");
                    objResetModel.setEmail(emailStr);
                    objResetModel.setReset_code(mVerificationCode.getText().toString());

                    new PasswordResetCodeVerificationAsyncTask(PasswordResetCodeVerificationActivity.this)
                            .execute(objResetModel);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.password_reset_code_verification, menu);
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
