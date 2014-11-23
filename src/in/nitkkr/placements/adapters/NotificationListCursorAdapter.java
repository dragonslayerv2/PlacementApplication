package in.nitkkr.placements.adapters;

import in.nitkkr.placements.database.DatabaseContract.COMPANY_TABLE;
import in.nitkkr.placements.database.DatabaseContract.NOTIFICATIONS_TABLE;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.placementapplication.R;

public class NotificationListCursorAdapter extends SimpleCursorAdapter
		implements Filterable {
	final int LAYOUT;
	final Context CONTEXT;

	public NotificationListCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		CONTEXT = context;
		LAYOUT = layout;
	}

	void setDataInView(View view, Cursor c) {
		TextView notificationTitle = (TextView) view
				.findViewById(R.id.notification_title);
		TextView notificationDescription = (TextView) view
				.findViewById(R.id.notification_description);
		ImageView companyLogo = (ImageView) view.findViewById(R.id.notification_company_logo_icon);
		byte[] logoImage;
		if ((logoImage = c
				.getBlob(c.getColumnIndex(COMPANY_TABLE.COMPANY_LOGO))) != null) {
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inSampleSize=6;
			Bitmap logoBitmap = BitmapFactory.decodeByteArray(logoImage, 0,
					logoImage.length,o);
			if (logoBitmap != null)
				companyLogo.setImageBitmap(logoBitmap);
			else
				companyLogo.setImageDrawable(CONTEXT.getResources()
						.getDrawable(R.drawable.default_company_logo));
		} else
			companyLogo.setImageDrawable(CONTEXT.getResources().getDrawable(
					R.drawable.default_company_logo));
		notificationTitle.setText(c.getString(c
				.getColumnIndex(NOTIFICATIONS_TABLE.NOTIFICATION_TITLE)));
		notificationDescription.setText(c.getString(c
				.getColumnIndex(NOTIFICATIONS_TABLE.NOTIFICATION_MESSAGE)));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		Cursor c = getCursor();
		final LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(LAYOUT, parent, false);
		setDataInView(view, c);
		return view;
	}

	@Override
	public void bindView(View v, Context context, Cursor c) {
		setDataInView(v, c);
	}
}