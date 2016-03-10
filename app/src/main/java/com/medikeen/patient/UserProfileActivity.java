package com.medikeen.patient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.medikeen.patient.R;
import com.medikeen.asyncTask.ChangePasswordAsyncTask;
import com.medikeen.asyncTask.UserProfileAsyncTask;
import com.medikeen.dataModel.ChangePasswordModel;
import com.medikeen.dataModel.UserProfileModel;
import com.medikeen.datamodels.User;
import com.medikeen.util.ConnectionDetector;
import com.medikeen.util.SessionManager;

public class UserProfileActivity extends Fragment {

    EditText mFirstName, mLastName, mEmailId, mContactNo, mPassword,
            mConfirmPassword;
    AutoCompleteTextView mAddress;
    Button mSave, mChangePassword;

    SessionManager sessionManager;
    User user;
    Dialog changePasswordDialog;
    UserProfileModel userProfile;
    ChangePasswordModel changePasswordModel;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_profile_2);
//
////        getSupportActionBar().hide();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        init();
//
//        mFirstName.setText(user.getFirstName());
////        mLastName.setText(user.getLastName());
//        mEmailId.setText(user.getEmailAddress());
//        mContactNo.setText(user.getPhoneNo());
//        mAddress.setText(user.getAddress());
//
//        mSave.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                sessionManager.createLoginSession(true, user.getPersonId(),
//                        mFirstName.getText().toString(), mLastName.getText()
//                                .toString(), mEmailId.getText().toString(),
//                        mAddress.getText().toString(), mContactNo.getText()
//                                .toString());
//
//                userProfile = new UserProfileModel("" + user.getPersonId(),
//                        mEmailId.getText().toString(), mFirstName.getText()
//                        .toString(), mLastName.getText().toString(),
//                        mContactNo.getText().toString(), mAddress.getText()
//                        .toString());
//
//                AsyncTask<Void, Boolean, Boolean> taskConn = new ConnectionDetector()
//                        .execute();
//
//                boolean resultConn = false;
//                try {
//                    resultConn = taskConn.get();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                if (resultConn == true) {
//
//                    new UserProfileAsyncTask(UserProfileActivity.this)
//                            .execute(userProfile);
//                } else {
//                    createDialogForInternetConnection();
//                }
//            }
//        });
//
//        mChangePassword.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                changePasswordDialog();
//            }
//        });
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_user_profile_2, container, false);

        init(rootView);

        mFirstName.setText(user.getFirstName());
//        mLastName.setText(user.getLastName());
        mEmailId.setText(user.getEmailAddress());
        mContactNo.setText(user.getPhoneNo());
        mAddress.setText(user.getAddress());

        mSave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                sessionManager.createLoginSession(true, user.getPersonId(),
                        mFirstName.getText().toString(), "", mEmailId.getText().toString(),
                        mAddress.getText().toString(), mContactNo.getText()
                                .toString());

                userProfile = new UserProfileModel("" + user.getPersonId(),
                        mEmailId.getText().toString(), mFirstName.getText()
                        .toString(), "",
                        mContactNo.getText().toString(), mAddress.getText()
                        .toString());

                AsyncTask<Void, Boolean, Boolean> taskConn = new ConnectionDetector()
                        .execute();

                boolean resultConn = false;
                try {
                    resultConn = taskConn.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (resultConn == true) {

                    new UserProfileAsyncTask((MainActivity) UserProfileActivity.this.getActivity())
                            .execute(userProfile);
                } else {
                    createDialogForInternetConnection();
                }
            }
        });

        mChangePassword.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                changePasswordDialog();
            }
        });


        return rootView;
    }

    private void createDialogForInternetConnection() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                UserProfileActivity.this.getActivity());
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

                            new UserProfileAsyncTask((MainActivity) UserProfileActivity.this.getActivity())
                                    .execute(userProfile);
                        } else {
                            createDialogForInternetConnection();
                        }
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    private void createDialogForInternetConnectionChangePassword() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                UserProfileActivity.this.getActivity());
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

                            new ChangePasswordAsyncTask((MainActivity) UserProfileActivity.this.getActivity())
                                    .execute(changePasswordModel);
                        } else {
                            createDialogForInternetConnectionChangePassword();
                        }
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    private void changePasswordDialog() {
        changePasswordDialog = new Dialog(UserProfileActivity.this.getActivity());
        changePasswordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        changePasswordDialog.setContentView(R.layout.change_password_dialog);
        changePasswordDialog.setCancelable(false);
        changePasswordDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final EditText currentPassword = (EditText) changePasswordDialog
                .findViewById(R.id.current_password_edit);
        final EditText newPassword = (EditText) changePasswordDialog
                .findViewById(R.id.new_password_edit);
        final EditText confirmNewPassword = (EditText) changePasswordDialog
                .findViewById(R.id.confirm_new_password_edit);

        Button mChangePasswordOk = (Button) changePasswordDialog
                .findViewById(R.id.change_password_ok);
        Button mChangePasswordCancel = (Button) changePasswordDialog
                .findViewById(R.id.change_password_cancel);

        mChangePasswordCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                changePasswordDialog.dismiss();
            }
        });

        // LOGIN BUTTON
        mChangePasswordOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String currentPasswordStr = currentPassword.getText()
                        .toString();
                String newPasswordStr = newPassword.getText().toString();
                String confirmNewPasswordStr = confirmNewPassword.getText()
                        .toString();

                if (currentPasswordStr.isEmpty() || newPasswordStr.isEmpty()
                        || confirmNewPasswordStr.isEmpty()) {
                    Toast.makeText(UserProfileActivity.this.getActivity(),
                            "Please enter all the field", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    if (newPasswordStr.compareTo(confirmNewPasswordStr) == 0) {

                        changePasswordModel = new ChangePasswordModel(
                                sessionManager.getUserDetails()
                                        .getEmailAddress(), currentPasswordStr,
                                newPasswordStr);

                        AsyncTask<Void, Boolean, Boolean> taskConn = new ConnectionDetector()
                                .execute();

                        boolean resultConn = false;
                        try {
                            resultConn = taskConn.get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (resultConn == true) {

                            new ChangePasswordAsyncTask((MainActivity) UserProfileActivity.this.getActivity())
                                    .execute(changePasswordModel);
                        } else {
                            createDialogForInternetConnectionChangePassword();
                        }
                    } else {
                        confirmNewPassword.setError("Passwords do not match");
                    }
                }

                changePasswordDialog.dismiss();
            }
        });

        changePasswordDialog.show();
    }

    private void init(View v) {

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
                "SofiaProLight.otf");

        sessionManager = new SessionManager(UserProfileActivity.this.getActivity());
        user = sessionManager.getUserDetails();

        mSave = (Button) v.findViewById(R.id.profile_buttonsave);
        mFirstName = (EditText) v.findViewById(R.id.profile_editTextFirstName);
//        mLastName = (EditText) findViewById(R.id.profile_editTextLastName);
        mEmailId = (EditText) v.findViewById(R.id.profile_editTextEmailId);
        mContactNo = (EditText) v.findViewById(R.id.profile_editTextContactNo);
        mAddress = (AutoCompleteTextView) v.findViewById(R.id.profile_editTextAddress);
        mPassword = (EditText) v.findViewById(R.id.profile_editTextPassword);
        mConfirmPassword = (EditText) v.findViewById(R.id.profile_editTextConfirmPassword);
        mChangePassword = (Button) v.findViewById(R.id.profile_changePassword);

        mAddress.setTypeface(font);

        mAddress.setAdapter(new PlacesAutoCompleteAdapter1(this.getActivity(),
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        // getMenuInflater().inflate(R.menu.user_profile, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == android.R.id.home) {
//            getActivity().onBackPressed();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
