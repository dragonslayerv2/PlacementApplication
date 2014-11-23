package in.nitkkr.placements.activities;

import in.nitkkr.placements.constants.ApplicationConstants;
import in.nitkkr.placements.json.JSONConstants;
import in.nitkkr.placements.networkcalls.Authenticator;
import in.nitkkr.placements.networkcalls.AuthenticatorListener;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.example.placementapplication.R;

public class LoginActivity extends Activity implements OnClickListener,
		AuthenticatorListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		findViewById(R.id.loginButton).setOnClickListener(this);
		SharedPreferences userSettings = getSharedPreferences(
				ApplicationConstants.SHARED_PREFERENCES, 0);
		if (userSettings.getInt(ApplicationConstants.USERNAME, 0) != 0)
			doLogin();
	}

	@Override
	public void onClick(View v) {
		Intent newIntent = new Intent(LoginActivity.this, MainActivity.class);
		EditText usernameEditText = (EditText) findViewById(R.id.loginUsername);
		EditText passwordEditText = (EditText) findViewById(R.id.loginPassword);
		if (usernameEditText.getText().toString().equals("")) {
			onAuthenticationFailed("Username can't be blank.");
			return;
		}

		else if (passwordEditText.getText().toString().equals("")) {
			onAuthenticationFailed("Password can't be blank");
			return;
		}
		int username;
		try {
			username = Integer.valueOf(usernameEditText.getText().toString());
		} catch (NumberFormatException E) {
			onAuthenticationFailed("Username must be an integer.");
			return;
		}
		String password = passwordEditText.getText().toString();
		Authenticator authenticator = new Authenticator(this, username,
				password, this);
		authenticator.execute();
	}

	@Override
	public void onAuthenticationFailed(String errorMessage) {
		final AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
		errorDialog.setMessage(errorMessage);
		errorDialog.setTitle("Unable to login.");
		errorDialog.setPositiveButton("Close",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		errorDialog.show();
	}

	@Override
	public void onAuthenticationSucces(int username, String password,
			JSONObject studentData) {
		SharedPreferences userSettings = getSharedPreferences(
				ApplicationConstants.SHARED_PREFERENCES, MODE_PRIVATE);
		SharedPreferences.Editor userSettingsEditor = userSettings.edit();
		try {
			userSettingsEditor.putInt(ApplicationConstants.USERNAME, username);
			userSettingsEditor.putString(ApplicationConstants.PASSWORD,
					password);
			userSettingsEditor.putString(ApplicationConstants.NAME,
					studentData.getString(JSONConstants.NAME));
			userSettingsEditor.putString(ApplicationConstants.COURSE,
					studentData.getString(JSONConstants.COURSE));

			userSettingsEditor.putString(ApplicationConstants.MAJORS,
					studentData.getString(JSONConstants.MAJORS));

			userSettingsEditor.putString(ApplicationConstants.RESUME_LINK,
					studentData.getString(JSONConstants.RESUME_LINK));
			userSettingsEditor.putBoolean(ApplicationConstants.BACKLOGS,
					studentData.getBoolean(JSONConstants.BACKLOGS));
			userSettingsEditor.putFloat(ApplicationConstants.CGPA,
					(float) (studentData.getDouble(JSONConstants.CGPA)));
			userSettingsEditor.putString(ApplicationConstants.EMAIL,
					studentData.getString(JSONConstants.EMAIL));
			userSettingsEditor.commit();
		} catch (JSONException e) {
			onAuthenticationFailed("Unable to apply settings.");
		}
		doLogin();
	}

	void doLogin() {
		Intent mainAcitivyIntent = new Intent(this, MainActivity.class);
		startActivity(mainAcitivyIntent);
		finish();
	}
}
