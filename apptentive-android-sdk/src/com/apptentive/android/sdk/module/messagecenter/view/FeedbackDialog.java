/*
 * Copyright (c) 2013, Apptentive, Inc. All Rights Reserved.
 * Please refer to the LICENSE file for the terms and conditions
 * under which redistribution and use of this file is permitted.
 */

package com.apptentive.android.sdk.module.messagecenter.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.apptentive.android.sdk.R;
import com.apptentive.android.sdk.module.rating.view.ApptentiveBaseDialog;

/**
 * @author Sky Kelsey
 */
public class FeedbackDialog extends ApptentiveBaseDialog {

	private OnSendListener onSendListener;
	private boolean emailRequired = false;
	private boolean messageRequired = true;
	private CharSequence email;
	private CharSequence message;

	public FeedbackDialog(Context context) {
		super(context, R.layout.apptentive_feedback_dialog);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final EditText emailText = (EditText) findViewById(R.id.email);
		final EditText messageText = (EditText) findViewById(R.id.message);
		final Button noThanksButton = (Button) findViewById(R.id.no_thanks);
		final Button sendButton = (Button) findViewById(R.id.send);

		emailText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
				email = charSequence;
				setSendButton(sendButton);
			}

			@Override
			public void afterTextChanged(Editable editable) {
			}
		});

		messageText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
				message = charSequence;
				setSendButton(sendButton);
			}

			@Override
			public void afterTextChanged(Editable editable) {
			}
		});

		noThanksButton.setEnabled(true);
		noThanksButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				cancel();
			}
		});

		sendButton.setEnabled(false);
		sendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (FeedbackDialog.this.onSendListener != null) {
					onSendListener.onSend(emailText.getText().toString(), messageText.getText().toString());
				}
			}
		});
	}

	@Override
	public void setTitle(int titleId) {
		setTitle(getContext().getResources().getString(titleId));
	}

	@Override
	public void setTitle(CharSequence title) {
		TextView textView = (TextView) findViewById(R.id.title);
		textView.setText(title);
	}

	public void setBody(int bodyId) {
		setBody(getContext().getResources().getString(bodyId));
	}

	public void setBody(CharSequence body) {
		TextView textView = (TextView) findViewById(R.id.body);
		textView.setText(body);
	}

	public void setEmailFieldHidden(boolean hidden) {
		EditText email = (EditText) findViewById(R.id.email);
		if (hidden) {
			email.setVisibility(View.GONE);
		} else {
			email.setVisibility(View.VISIBLE);
		}
	}

	public boolean isEmailFieldVisible() {
		EditText email = (EditText) findViewById(R.id.email);
		return email.getVisibility() == View.VISIBLE;
	}

	public void prePopulateEmail(String email) {
		EditText emailEditText = (EditText) findViewById(R.id.email);
		emailEditText.setText(email);
	}

	public void setEmailRequired(boolean emailRequired) {
		this.emailRequired = emailRequired;
	}

	private void setSendButton(Button sendButton) {
		boolean passedEmail = true;
		boolean passedMessage = true;
		if (emailRequired) {
			passedEmail = !(email == null || email.length() == 0);
		}
		if (messageRequired) {
			passedMessage = !(message == null || message.length() == 0);
		}
		sendButton.setEnabled(passedEmail && passedMessage);
	}

	public void setOnSendListener(OnSendListener onSendListener) {
		this.onSendListener = onSendListener;
	}

	public interface OnSendListener {
		public void onSend(String email, String message);
	}
}
