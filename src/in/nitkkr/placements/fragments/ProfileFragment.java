package in.nitkkr.placements.fragments;

import in.nitkkr.placements.constants.ApplicationConstants;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.placementapplication.R;

public class ProfileFragment extends Fragment {
	TextView name, username, course, majors, backlogs, cgpa, email;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View profileFragment = inflater.inflate(R.layout.fragment_profile,
				container, false);
		name = (TextView) profileFragment
				.findViewById(R.id.fragment_profile_name);
		username = (TextView) profileFragment
				.findViewById(R.id.fragment_profile_username);
		course = (TextView) profileFragment
				.findViewById(R.id.fragment_profile_course);
		majors = (TextView) profileFragment
				.findViewById(R.id.fragment_profile_majors);
		backlogs = (TextView) profileFragment
				.findViewById(R.id.fragment_profile_backlogs);
		cgpa = (TextView) profileFragment
				.findViewById(R.id.fragment_profile_cgpa);
		email = (TextView) profileFragment
				.findViewById(R.id.fragment_profile_email);
		showData();
		return profileFragment;
	}

	void showData() {
		String name, username, course, majors, email;
		boolean backlogs;
		float cgpa;
		SharedPreferences userSettings = getActivity().getSharedPreferences(
				ApplicationConstants.SHARED_PREFERENCES, 0);
		name = userSettings.getString(ApplicationConstants.NAME, "");
		username = String.valueOf(userSettings.getInt(
				ApplicationConstants.USERNAME, 0));
		course = userSettings.getString(ApplicationConstants.COURSE, "");
		majors = userSettings.getString(ApplicationConstants.MAJORS, "");
		email = userSettings.getString(ApplicationConstants.EMAIL, "");
		backlogs = userSettings
				.getBoolean(ApplicationConstants.BACKLOGS, false);
		cgpa = userSettings.getFloat(ApplicationConstants.CGPA, (float) 0.00);
		this.name.setText(name);
		this.username.setText(username);
		this.course.setText(course);
		this.majors.setText(majors);
		this.email.setText(email);
		if (backlogs)
			this.backlogs.setText("Yes");
		else
			this.backlogs.setText("No");
		this.cgpa.setText(String.valueOf(cgpa));
	}
}
