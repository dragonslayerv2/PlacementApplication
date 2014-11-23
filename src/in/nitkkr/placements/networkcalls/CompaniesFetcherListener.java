package in.nitkkr.placements.networkcalls;

import org.json.JSONObject;

public interface CompaniesFetcherListener {
	void onFetchSuccess(JSONObject comapniesJSON);

	void onFetchFailed(String errorMessage);
}
