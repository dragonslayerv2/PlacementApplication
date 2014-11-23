package in.nitkkr.placements.adapters;

import in.nitkkr.placements.database.DatabaseContract.COMPANY_TABLE;
import in.nitkkr.placements.database.DatabaseContract.JOB_TABLE;
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

public class CompanyListCursorAdapter extends SimpleCursorAdapter implements
		Filterable {
	final int LAYOUT;
	final Context CONTEXT;

	public CompanyListCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		CONTEXT = context;
		LAYOUT = layout;
	}

	void setDataInView(View view, Cursor c) {
		TextView companyName = (TextView) view.findViewById(R.id.company_name);
		TextView jobPosition = (TextView) view.findViewById(R.id.job_position);
		TextView jobStatus = (TextView) view
				.findViewById(R.id.job_package_status);
		TextView jobLocation = (TextView) view.findViewById(R.id.job_location);
		ImageView companyLogo = (ImageView) view
				.findViewById(R.id.company_logo_icon);

		
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
		companyName.setText(c.getString(c
				.getColumnIndex(COMPANY_TABLE.COMPANY_NAME)));
		jobPosition.setText(c.getString(c
				.getColumnIndex(JOB_TABLE.JOB_POSITION)));
		jobStatus.setText(c.getString(c
				.getColumnIndex(JOB_TABLE.JOB_PACKAGE_STATUS)));
		jobLocation.setText(c.getString(c
				.getColumnIndex(JOB_TABLE.JOB_LOCATION)));
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

	/*
	 * @Override public Cursor runQueryOnBackgroundThread(CharSequence
	 * constraint) { if (getFilterQueryProvider() != null) { return
	 * getFilterQueryProvider().runQuery(constraint); }
	 * 
	 * StringBuilder buffer = null; String[] args = null; if (constraint !=
	 * null) { buffer = new StringBuilder(); buffer.append("UPPER(");
	 * buffer.append(People.NAME); buffer.append(") GLOB ?"); args = new
	 * String[] { constraint.toString().toUpperCase() + "*" }; }
	 * 
	 * return context.getContentResolver().query(People.CONTENT_URI, null,
	 * buffer == null ? null : buffer.toString(), args, People.NAME + " ASC"); }
	 */
}