package in.nitkkr.placements.fragments;

import in.nitkkr.placements.adapters.NotificationListCursorAdapter;
import in.nitkkr.placements.database.DatabaseContract.COMPANY_TABLE;
import in.nitkkr.placements.database.DatabaseContract.NOTIFICATIONS_TABLE;
import in.nitkkr.placements.database.DatabaseHandler;
import android.app.AlertDialog;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.placementapplication.R;

public class NotifactionListFragment extends Fragment implements
		OnItemClickListener {
	private ListView listView;
	private SimpleCursorAdapter listAdapter;
	private Cursor cursor = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View fragmentView = inflater.inflate(
				R.layout.fragment_notification_list, null);
		listView = (ListView) fragmentView.findViewById(R.id.notification_list);

		cursor = getCursor();
		String[] from = { COMPANY_TABLE.COMPANY_NAME,
				NOTIFICATIONS_TABLE.NOTIFICATION_MESSAGE };
		int[] to = { R.id.notification_title, R.id.notification_description };
		listAdapter = new NotificationListCursorAdapter(getActivity(),
				R.layout.adapter_notification_list, cursor, from, to,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(this);
		return fragmentView;
	}

	Cursor getCursor() {
		return DatabaseHandler.getInstance(getActivity()).getNotification();
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View parentView,
			int position, long arg3) {
		cursor.moveToPosition(position);
		String title = cursor.getString(cursor
				.getColumnIndex(NOTIFICATIONS_TABLE.NOTIFICATION_TITLE));
		String description = cursor.getString(cursor
				.getColumnIndex(NOTIFICATIONS_TABLE.NOTIFICATION_MESSAGE));

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());
		alertDialogBuilder.setTitle(title);
		alertDialogBuilder.setMessage(description);
		alertDialogBuilder.setNeutralButton("Close", null);
		alertDialogBuilder.show();

	}
}
