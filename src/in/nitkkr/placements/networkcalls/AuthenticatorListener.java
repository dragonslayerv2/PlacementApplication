package in.nitkkr.placements.networkcalls;

import org.json.JSONObject;

public interface AuthenticatorListener {
	void onAuthenticationFailed(String errorMessage);

	void onAuthenticationSucces(int username, String password,
			JSONObject studentData);
}
