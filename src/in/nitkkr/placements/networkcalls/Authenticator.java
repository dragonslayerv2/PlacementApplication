package in.nitkkr.placements.networkcalls;

import in.nitkkr.placements.json.JSONConstants;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class Authenticator extends AsyncTask<Void, Void, JSONObject> {
	private final Context context;
	private final int username;
	private final String password;
	private Exception exception;
	private final AuthenticatorListener authenticatorListener;
	private final ProgressDialog progressDialog;

	public Authenticator(Context context, int username, String password,
			AuthenticatorListener authenticatorListener) {
		this.context = context;
		this.username = username;
		this.password = password;
		this.authenticatorListener = authenticatorListener;

		this.progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("Signing in. Please wait.");
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog.show();
	}

	@Override
	protected JSONObject doInBackground(Void... params) {
		JSONObject userJSON = new JSONObject();
		try {
			userJSON.put(JSONConstants.USERNAME, username);
			userJSON.put(JSONConstants.PASSWORD, password);
			String response = SimpleHttpClient.executeHttpPost(
					NetworkConstants.URL.SERVER_URL
							+ NetworkConstants.URL.LOGIN_SUBURL, userJSON);
			return new JSONObject(response);
		} catch (Exception e) {
			this.exception = e;
		}
		return null;
	}

	@Override
	protected void onPostExecute(JSONObject response) {
		progressDialog.dismiss();
		super.onPostExecute(response);
		try {

			if (exception != null)
				authenticatorListener.onAuthenticationFailed(exception
						.getMessage());
			else if (response.getBoolean(JSONConstants.IS_AUTHORIZED) == false)
				authenticatorListener
						.onAuthenticationFailed("Invalid username or password.");
			else if (response.getBoolean(JSONConstants.IS_AUTHORIZED) == true)
				authenticatorListener.onAuthenticationSucces(username,
						password,
						response.getJSONObject(JSONConstants.STUDENT_DATA));
			else
				authenticatorListener
						.onAuthenticationFailed("Unknown Error Occured.");

		} catch (Exception e) {
			authenticatorListener.onAuthenticationFailed(e.getMessage());
		}
	}
}
