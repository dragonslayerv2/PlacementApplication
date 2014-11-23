package in.nitkkr.placements.networkcalls;

import in.nitkkr.placements.json.JSONConstants;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

public class CompaniesFetcher extends AsyncTask<Void, Void, JSONObject> {
	private final Context context;
	private final int username;
	private final String password;
	private Exception exception;
	private final CompaniesFetcherListener companiesFetcherListener;

	public CompaniesFetcher(Context context, int username, String password,
			CompaniesFetcherListener companiesFetcherListner) {
		this.context = context;
		this.username = username;
		this.password = password;
		this.companiesFetcherListener = companiesFetcherListner;
	}

	@Override
	protected JSONObject doInBackground(Void... params) {
		JSONObject userJSON = new JSONObject();
		try {
			userJSON.put(JSONConstants.USERNAME, username);
			userJSON.put(JSONConstants.PASSWORD, password);
			String response = SimpleHttpClient.executeHttpPost(
					NetworkConstants.URL.SERVER_URL
							+ NetworkConstants.URL.COMPANIES_SUBURL, userJSON);
			return new JSONObject(response);
		} catch (Exception e) {
			this.exception = e;
		}
		return null;
	}

	@Override
	protected void onPostExecute(JSONObject response) {
		super.onPostExecute(response);
		try {
			if (exception != null)
				companiesFetcherListener.onFetchFailed(exception.getMessage());
			else if (response.getBoolean(JSONConstants.IS_AUTHORIZED))
				companiesFetcherListener.onFetchFailed("Not Authorized.");
			else
				companiesFetcherListener.onFetchSuccess(response
						.getJSONObject(JSONConstants.COMPANIES));
		} catch (Exception exp) {
			companiesFetcherListener.onFetchFailed(exp.getMessage());
		}
	}
}
