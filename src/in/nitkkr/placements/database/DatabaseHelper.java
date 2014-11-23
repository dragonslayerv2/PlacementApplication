package in.nitkkr.placements.database;

import in.nitkkr.placements.mockdatagenerator.MockDataGenerator;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "PlacementDatabase";
	private static final int DATABASE_VERSION = 121;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DatabaseContract.COMPANY_TABLE.getCreateQuery());
		db.execSQL(DatabaseContract.JOB_TABLE.getCreateQuery());
		db.execSQL(DatabaseContract.NOTIFICATIONS_TABLE.getCreateQuery());
		db.execSQL(DatabaseContract.REMINDER_TABLE.getCreateQuery());
		/*
		 * MockDataGenerator.createMockCompanyData(db);
		 * MockDataGenerator.createMockJobData(db);
		 * MockDataGenerator.createMockNotificationData(db);
		 */
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DatabaseContract.DROP_TABLE_QUERY
				+ DatabaseContract.SPACE_SEPARATOR
				+ DatabaseContract.COMPANY_TABLE.COMPANY_TABLE_NAME);
		db.execSQL(DatabaseContract.DROP_TABLE_QUERY
				+ DatabaseContract.SPACE_SEPARATOR
				+ DatabaseContract.JOB_TABLE.JOBS_TABLE_NAME);
		db.execSQL(DatabaseContract.DROP_TABLE_QUERY
				+ DatabaseContract.SPACE_SEPARATOR
				+ DatabaseContract.NOTIFICATIONS_TABLE.NOTIFICATIONS_TABLE_NAME);
		db.execSQL(DatabaseContract.DROP_TABLE_QUERY
				+ DatabaseContract.SPACE_SEPARATOR
				+ DatabaseContract.REMINDER_TABLE.REMINDER_TABLE_NAME);
		onCreate(db);
	}
}
