package in.nitkkr.placements.networkcalls;

import org.json.JSONObject;

public interface JobsFetcherListener {

	void onFetchFailed(String errorMessage);

	void onFetchSuccess(JSONObject jobsJSON);

}
