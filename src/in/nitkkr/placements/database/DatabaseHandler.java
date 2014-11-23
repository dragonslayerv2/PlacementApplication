package in.nitkkr.placements.database;

import in.nitkkr.placements.database.DatabaseContract.COMPANY_TABLE;
import in.nitkkr.placements.database.DatabaseContract.JOB_TABLE;
import in.nitkkr.placements.database.DatabaseContract.NOTIFICATIONS_TABLE;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseHandler {
	private static DatabaseHandler instance;
	Context CONTEXT;
	DatabaseHelper DBHelper;
	SQLiteDatabase database;

	public static final int ELIGIBLE = 1;
	public static final int ALL = 2;
	public static final int APPLIED = 3;

	public static DatabaseHandler getInstance(Context context) {
		if (instance == null) {
			instance = new DatabaseHandler(context);
		}
		return instance;
	}

	private DatabaseHandler(Context context) {
		CONTEXT = context;
		DBHelper = new DatabaseHelper(context);
		database = null;
		open();
	}

	public SQLiteDatabase open() {
		if (database == null)
			database = DBHelper.getWritableDatabase();
		return database;
	}

	public void close() {
		DBHelper.close();
	}

	public int insertLogo(int companyId, byte[] logoImage) {
		ContentValues values = new ContentValues();
		values.put(COMPANY_TABLE.COMPANY_LOGO, logoImage);

		String whereFilter = COMPANY_TABLE.COMPANY_ID + "=" + companyId;
		return database.update(COMPANY_TABLE.COMPANY_TABLE_NAME, values, whereFilter,
				null);
	}

	public Cursor getJobs(int type) {
		StringBuilder query = new StringBuilder();
		query.append(String
				.format("SELECT %s.%s, %s.%s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s INNER JOIN %s ON %s.%s = %s.%s",
						JOB_TABLE.JOBS_TABLE_NAME, JOB_TABLE.JOB_COMPANY_ID,
						JOB_TABLE.JOBS_TABLE_NAME, JOB_TABLE.JOB_ID,
						COMPANY_TABLE.COMPANY_NAME, JOB_TABLE.JOB_STATUS,
						JOB_TABLE.JOB_POSITION, JOB_TABLE.JOB_LOCATION,
						JOB_TABLE.JOB_PACKAGE, COMPANY_TABLE.COMPANY_LOGO,
						COMPANY_TABLE.COMPANY_LOGO_URL,
						JOB_TABLE.JOB_PACKAGE_STATUS,
						COMPANY_TABLE.COMPANY_TABLE_NAME,
						JOB_TABLE.JOBS_TABLE_NAME,
						COMPANY_TABLE.COMPANY_TABLE_NAME,
						COMPANY_TABLE.COMPANY_ID, JOB_TABLE.JOBS_TABLE_NAME,
						JOB_TABLE.JOB_COMPANY_ID));
		if (type == ELIGIBLE)
			query.append(" WHERE " + JOB_TABLE.JOB_ELIGIBLE + "=1 AND "
					+ JOB_TABLE.JOB_APPLIED + "=0");
		else if (type == APPLIED) {
			query.append(" WHERE " + JOB_TABLE.JOB_APPLIED + "=1");
		} else {
			query.append(" WHERE " + JOB_TABLE.JOB_ELIGIBLE + "=0 AND "
					+ JOB_TABLE.JOB_APPLIED + "=0");
		}

		query.append(" ORDER BY " + JOB_TABLE.JOB_POSTED_DATE);
		Log.i("Database Query ", "Database Query " + query.toString());
		return database.rawQuery(query.append(';').toString(), null);
	}

	public Cursor getNotification() {
		StringBuilder query = new StringBuilder();
		query.append(String
				.format("SELECT %s.%s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s INNER JOIN %s ON %s.%s = %s",
						NOTIFICATIONS_TABLE.NOTIFICATIONS_TABLE_NAME,
						NOTIFICATIONS_TABLE.NOTIFICATION_ID,
						NOTIFICATIONS_TABLE.NOTIFICATION_COMPANY_ID,
						COMPANY_TABLE.COMPANY_NAME, COMPANY_TABLE.COMPANY_LOGO,
						COMPANY_TABLE.COMPANY_URL,
						NOTIFICATIONS_TABLE.NOTIFICATION_JOB_ID,
						NOTIFICATIONS_TABLE.NOTIFICATION_MESSAGE,
						NOTIFICATIONS_TABLE.NOTIFICATION_TIME,
						NOTIFICATIONS_TABLE.NOTIFICATION_TITLE,
						NOTIFICATIONS_TABLE.NOTIFICATIONS_TABLE_NAME,
						COMPANY_TABLE.COMPANY_TABLE_NAME,
						COMPANY_TABLE.COMPANY_TABLE_NAME,
						COMPANY_TABLE.COMPANY_ID,
						NOTIFICATIONS_TABLE.NOTIFICATION_COMPANY_ID));
		query.append(" ORDER BY " + NOTIFICATIONS_TABLE.NOTIFICATION_TIME);
		Log.i("Database Query ", "Database Query " + query.toString());
		return database.rawQuery(query.append(';').toString(), null);
	}

	public Cursor getJobDetails(int jobId) {
		String query = String.format("SELECT * FROM %s WHERE %s=%d;",
				JOB_TABLE.JOBS_TABLE_NAME, JOB_TABLE.JOB_ID, jobId);
		return database.rawQuery(query, null);
	}

	public Cursor getCompanyDetails(int companyId) {
		String query = String.format("SELECT * FROM %s WHERE %s=%d;",
				COMPANY_TABLE.COMPANY_TABLE_NAME, COMPANY_TABLE.COMPANY_ID,
				companyId);
		return database.rawQuery(query, null);
	}

	boolean deleteCompany(int companyID) {
		return database.delete(
				DatabaseContract.COMPANY_TABLE.COMPANY_TABLE_NAME,
				DatabaseContract.COMPANY_TABLE.COMPANY_ID + " = " + companyID,
				null) > 0;
	}

	public void insertNotification(int notificationId, int companyId,
			int jobId, String message, String title) {
		ContentValues values = new ContentValues();
		values.put(NOTIFICATIONS_TABLE.NOTIFICATION_COMPANY_ID, companyId);
		values.put(NOTIFICATIONS_TABLE.NOTIFICATION_ID, notificationId);
		values.put(NOTIFICATIONS_TABLE.NOTIFICATION_JOB_ID, jobId);
		values.put(NOTIFICATIONS_TABLE.NOTIFICATION_MESSAGE, message);
		values.put(NOTIFICATIONS_TABLE.NOTIFICATION_TITLE, title);
		values.put(NOTIFICATIONS_TABLE.NOTIFICATION_TIME, 132122);
		database.insert(NOTIFICATIONS_TABLE.NOTIFICATIONS_TABLE_NAME, null,
				values);
	}

	public void insertJob(int jobid, int companyid, String description,
			String eligibilityCriteria, String location, String packge,
			String packageStatus, String position, String status, int eligible,
			int applied) {
		ContentValues values = new ContentValues();
		values.put(JOB_TABLE.JOB_APPLIED, applied);
		values.put(JOB_TABLE.JOB_ID, jobid);
		values.put(JOB_TABLE.JOB_COMPANY_ID, companyid);
		values.put(JOB_TABLE.JOB_DESCRIPTION, description);
		values.put(JOB_TABLE.JOB_ELIGIBILITY_CRITERIA, eligibilityCriteria);
		values.put(JOB_TABLE.JOB_ELIGIBLE, eligible);

		values.put(JOB_TABLE.JOB_LOCATION, location);
		values.put(JOB_TABLE.JOB_PACKAGE, packge);
		values.put(JOB_TABLE.JOB_PACKAGE_STATUS, packageStatus);
		values.put(JOB_TABLE.JOB_POSITION, position);
		values.put(JOB_TABLE.JOB_STATUS, status);
		database.insert(JOB_TABLE.JOBS_TABLE_NAME, null, values);
	}

	public void insertCompany(int id, String companyName, String url,
			String logo_url, String description) {
		ContentValues values = new ContentValues();
		values.put(COMPANY_TABLE.COMPANY_ID, id);
		values.put(COMPANY_TABLE.COMPANY_NAME, companyName);
		values.put(COMPANY_TABLE.COMPANY_URL, url);
		values.put(COMPANY_TABLE.COMPANY_LOGO_URL, logo_url);
		values.put(COMPANY_TABLE.COMPANY_DESCRIPTION, description);
		database.insert(DatabaseContract.COMPANY_TABLE.COMPANY_TABLE_NAME,
				null, values);
	}
}
