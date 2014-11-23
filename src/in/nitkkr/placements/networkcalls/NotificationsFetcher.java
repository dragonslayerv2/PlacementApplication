package in.nitkkr.placements.networkcalls;

import in.nitkkr.placements.json.JSONConstants;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

public class NotificationsFetcher extends AsyncTask<Void, Void, JSONObject> {
	private final Context context;
	private final int username;
	private final String password;
	private Exception exception;
	private final NotificationsFetcherListener notificationsFetcherListener;

	public NotificationsFetcher(Context context, int username, String password,
			NotificationsFetcherListener notificationsFetcherListner) {
		this.context = context;
		this.username = username;
		this.password = password;
		this.notificationsFetcherListener = notificationsFetcherListner;
	}

	@Override
	protected JSONObject doInBackground(Void... params) {
		JSONObject userJSON = new JSONObject();
		try {
			userJSON.put(JSONConstants.USERNAME, username);
			userJSON.put(JSONConstants.PASSWORD, password);
			String response = SimpleHttpClient.executeHttpPost(
					NetworkConstants.URL.SERVER_URL
							+ NetworkConstants.URL.NOTIFICATIONS_SUBURL,
					userJSON);
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
				notificationsFetcherListener.onFetchFailed(exception
						.getMessage());
			else if (response.getBoolean(JSONConstants.IS_AUTHORIZED))
				notificationsFetcherListener.onFetchFailed("Not Authorized.");
			else
				notificationsFetcherListener.onFetchSuccess(response
						.getJSONObject(JSONConstants.NOTIFICATIONS));
		} catch (Exception exp) {
			notificationsFetcherListener.onFetchFailed(exp.getMessage());
		}
	}
}
