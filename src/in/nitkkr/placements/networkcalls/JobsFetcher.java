package in.nitkkr.placements.networkcalls;

import in.nitkkr.placements.json.JSONConstants;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

public class JobsFetcher extends AsyncTask<Void, Void, JSONObject> {
	private final Context context;
	private final int username;
	private final String password;
	private Exception exception;
	private final JobsFetcherListener jobsFetcherListener;

	public JobsFetcher(Context context, int username, String password,
			JobsFetcherListener jobsFetcherListner) {
		this.context = context;
		this.username = username;
		this.password = password;
		this.jobsFetcherListener = jobsFetcherListner;
	}

	@Override
	protected JSONObject doInBackground(Void... params) {
		JSONObject userJSON = new JSONObject();
		try {
			userJSON.put(JSONConstants.USERNAME, username);
			userJSON.put(JSONConstants.PASSWORD, password);
			String response = SimpleHttpClient.executeHttpPost(
					NetworkConstants.URL.SERVER_URL
							+ NetworkConstants.URL.JOBS_SUBURL, userJSON);
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
				jobsFetcherListener.onFetchFailed(exception.getMessage());
			else if (response.getBoolean(JSONConstants.IS_AUTHORIZED))
				jobsFetcherListener.onFetchFailed("Not Authorized.");
			else
				jobsFetcherListener.onFetchSuccess(response
						.getJSONObject(JSONConstants.JOBS));
		} catch (Exception exp) {
			jobsFetcherListener.onFetchFailed(exp.getMessage());
		}
	}
}
