package com.medikeen.patient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.medikeen.R;
import com.medikeen.asyncTask.FeedbackUploadTask;
import com.medikeen.dataModel.FeedbackModel;
import com.medikeen.util.SessionManager;

public class Feedback extends Fragment {

	EditText editTextTitle, editTextDesc;
	Button btnSend;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_feedback, container, false);

		init(view);

		return view;
	}

	private void init(View view) {

		editTextDesc = (EditText) view.findViewById(R.id.editTextFeedbackDescription);
		editTextTitle = (EditText) view.findViewById(R.id.editTextFeedbackTitle);

		btnSend = (Button) view.findViewById(R.id.buttonSend);

		btnSend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (editTextTitle.getText().toString().isEmpty()) {
					Toast.makeText(getActivity(), "Please enter the title.", Toast.LENGTH_SHORT).show();
					return;
				}

				if (editTextDesc.getText().toString().isEmpty()) {
					Toast.makeText(getActivity(), "Please enter description.", Toast.LENGTH_SHORT).show();
					return;
				}

				SessionManager session = new SessionManager(getActivity().getApplicationContext());
				FeedbackModel feedback = new FeedbackModel(session.getUserDetails(), editTextTitle.getText().toString(),
						editTextDesc.getText().toString());

				FeedbackUploadTask uploadTask = new FeedbackUploadTask(getActivity());
				uploadTask.execute(feedback);
			}
		});

	}
}
