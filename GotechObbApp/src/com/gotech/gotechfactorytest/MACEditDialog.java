/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gotech.gotechfactorytest;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class MACEditDialog extends AlertDialog {

	public static final String	TAG	= "EthernetDialog";

	private Context				mContext;
	private String				mMacAddress;

	private boolean				mEditing;

	private View				mView;

	private EditText			mMacaddr;

	private Button				btnOk;
	private Button				btnCancel;

	public MACEditDialog(Context context, String mac, boolean editing) {
		super(context);

		this.mContext = context;
		mEditing = editing;
		mMacAddress = mac;
	}

	@Override
	protected void onCreate(Bundle savedState) {
		mView = getLayoutInflater().inflate(R.layout.changemac_dialog, null);
		setView(mView);
		setInverseBackgroundForced(true);

		// First, find out all the fields.
		mMacaddr = (EditText) mView.findViewById(R.id.macaddr_edit);

		// Second, copy values from the profile.
		mMacaddr.requestFocus();
		mMacaddr.setEnabled(true);

		// Third, add listeners to required fields.
		btnOk = (Button) mView.findViewById(R.id.change_ok);
		btnCancel = (Button) mView.findViewById(R.id.change_cancel);

		mView.findViewById(R.id.eth_conf_editor).setVisibility(View.VISIBLE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		mMacaddr.addTextChangedListener(macTextWatcher);

		super.onCreate(savedState);
	}

	private TextWatcher	macTextWatcher	= new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method
			// stub
			System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber() + "    "
					+ Thread.currentThread().getStackTrace()[2].getFileName() + "  " + s + " start  " + start
					+ "  before " + before + "  count " + count);

			if (count == 1 && before == 0) {
				String temp = s.toString();

				if (temp.charAt(start) != ':') {
					if (start == 1 || start == 4 || start == 7 || start == 10 || start == 13) {
						temp = temp + ":";
						start++;
					}
				}
				
				if (temp.length() > 17) {
					temp = temp.substring(0, 17);
				}

				mMacaddr.setText(temp.toUpperCase());
				mMacaddr.setSelection(temp.length());
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method
			// stub
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method
			// stub

		}
	};

	boolean isEditing() {
		return mEditing;
	}

	public void setMacaddress() {
		mMacAddress = mMacaddr.getText().toString().toUpperCase();
	}

	public String getMacAddress() {

		return mMacAddress;
	}

	public void setPositiveButton(Button.OnClickListener listener) {
		btnOk.setOnClickListener(listener);
	}

	public void setNegativeButton(Button.OnClickListener listener) {
		btnCancel.setOnClickListener(listener);
	}
}
