package in.nitkkr.placements.fragments;

import in.nitkkr.placements.activities.JobDetailsActivity;
import in.nitkkr.placements.adapters.CompanyListCursorAdapter;
import in.nitkkr.placements.database.DatabaseContract.COMPANY_TABLE;
import in.nitkkr.placements.database.DatabaseContract.JOB_TABLE;
import in.nitkkr.placements.database.DatabaseHandler;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.placementapplication.R;

public class CompanyListFragment extends Fragment implements
		OnItemClickListener {
	public static final int ELIGIBLE = 1;
	public static final int ALL = 2;
	public static final int APPLIED = 3;
	public int type;
	private ListView listView;
	private SimpleCursorAdapter listAdapter;
	private Cursor cursor = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View fragmentView = inflater.inflate(R.layout.fragment_company_list,
				container, false);
		listView = (ListView) fragmentView.findViewById(R.id.company_list);

		cursor = getCursor();
		String from[] = { COMPANY_TABLE.COMPANY_NAME, JOB_TABLE.JOB_POSITION,
				JOB_TABLE.JOB_LOCATION, JOB_TABLE.JOB_PACKAGE_STATUS };
		int to[] = { R.id.company_name, R.id.job_position, R.id.job_location,
				R.id.job_package_status };

		listAdapter = new CompanyListCursorAdapter(getActivity(),
				R.layout.adapter_company_list, cursor, from, to,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		listView.setAdapter(listAdapter);

		listView.setOnItemClickListener(this);
		return fragmentView;
	}

	public void doQuery(int type) {
		this.type = type;
	}

	Cursor getCursor() {
		switch (type) {
		case ELIGIBLE:
			return DatabaseHandler.getInstance(getActivity()).getJobs(
					DatabaseHandler.ELIGIBLE);
		case ALL:
			return DatabaseHandler.getInstance(getActivity()).getJobs(
					DatabaseHandler.ALL);
		case APPLIED:
			return DatabaseHandler.getInstance(getActivity()).getJobs(
					DatabaseHandler.APPLIED);
		default:
			throw new IllegalArgumentException("Wrong value for type");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		cursor.moveToPosition(position);
		Intent jobActivityIntent = new Intent(getActivity(),
				JobDetailsActivity.class);
		jobActivityIntent.putExtra(JOB_TABLE.JOBS_TABLE_NAME + "."
				+ JOB_TABLE.JOB_ID,
				cursor.getInt(cursor.getColumnIndex(JOB_TABLE.JOB_ID)));

		jobActivityIntent.putExtra(COMPANY_TABLE.COMPANY_TABLE_NAME + "."
				+ COMPANY_TABLE.COMPANY_ID,
				cursor.getInt(cursor.getColumnIndex(JOB_TABLE.JOB_COMPANY_ID)));

		startActivity(jobActivityIntent);
	}
}
