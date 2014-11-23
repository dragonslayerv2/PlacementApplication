package in.nitkkr.placements.networkcalls;

import org.json.JSONObject;

public interface NotificationsFetcherListener {
	void onFetchSuccess(JSONObject comapniesJSON);

	void onFetchFailed(String errorMessage);
}
