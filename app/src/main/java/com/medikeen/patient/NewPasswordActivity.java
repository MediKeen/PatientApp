package com.medikeen.patient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.medikeen.R;
import com.medikeen.asyncTask.NewPasswordAsyncTask;
import com.medikeen.dataModel.ResetPasswordModel;
import com.medikeen.util.ConnectionDetector;

public class NewPasswordActivity extends AppCompatActivity {

	EditText mNewPassword, mConfirmPassword;
	Button mSubmitButton;

	SharedPreferences sp;
	Editor edit;
	String emailStr, verificationCodeStr;
	ResetPasswordModel objResetModel;

	private static final String RESET_PASSWORD = "RESET_PASSWORD";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_password);

		sp = getSharedPreferences(RESET_PASSWORD, MODE_PRIVATE);
		edit = sp.edit();

		emailStr = sp.getString("RESET_EMAIL", null);
		verificationCodeStr = sp.getString("VERIFICATION_CODE", null);

		mNewPassword = (EditText) findViewById(R.id.editTextNewPassword);
		mConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);

		mSubmitButton = (Button) findViewById(R.id.buttonSubmitPassword);

		mSubmitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String newPasswordString = mNewPassword.getText().toString();
				String confirmPasswordString = mConfirmPassword.getText()
						.toString();

				if (newPasswordString.equals("") || newPasswordString.isEmpty()
						|| confirmPasswordString.equals("")
						|| confirmPasswordString.isEmpty()) {
					Toast.makeText(NewPasswordActivity.this,
							"Please enter all the fields", Toast.LENGTH_SHORT)
							.show();
				} else {
					if (newPasswordString.compareTo(confirmPasswordString) == 0) {

						objResetModel = new ResetPasswordModel();
						objResetModel.setTag("resetpassword");
						objResetModel.setEmail(emailStr);
						objResetModel.setReset_code(verificationCodeStr);
						objResetModel.setNew_password(newPasswordString);

						AsyncTask<Void, Boolean, Boolean> taskConn = new ConnectionDetector()
								.execute();

						boolean resultConn = false;
						try {
							resultConn = taskConn.get();
						} catch (Exception e) {
							e.printStackTrace();
						}

						if (resultConn == true) {
							new NewPasswordAsyncTask(NewPasswordActivity.this)
									.execute(objResetModel);
						} else {
							createDialogForInternetConnection();
						}
					} else {
						mConfirmPassword.setError("Passwords do not match");
					}
				}

			}
		});
	}

	private void createDialogForInternetConnection() {

		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				NewPasswordActivity.this);
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
							new NewPasswordAsyncTask(NewPasswordActivity.this)
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
		getMenuInflater().inflate(R.menu.new_password, menu);
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
