package in.nitkkr.placements.database;

public class DatabaseContract {
	public static final String CREATE_TABLE_QUERY = "CREATE TABLE";
	public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS";
	public static final String COMMA_SEPARATOR = ", ";
	public static final String STRING_FORMATOR = "%s";
	public static final String SPACE_SEPARATOR = " ";
	public static final String STRING_TYPE = "VARCHAR";
	public static final String INTEGER_TYPE = "INTEGER";
	public static final String BOOLEAN_TYPE = "INTEGER";
	public static final String VARCHAR_TYPE = "VARCHAR(%d)";
	public static final String TEXT_TYPE = "TEXT";
	public static final String BLOB_TYPE = "BLOB";
	public static final String DATE_TIME_TYPE = "DATETIME";

	private static String getStringFormator(int count) {
		StringBuilder query = new StringBuilder();
		query.append('(');
		for (int i = 1; i <= count; i++) {
			query.append(STRING_FORMATOR + SPACE_SEPARATOR + STRING_FORMATOR);
			if (i != count)
				query.append(COMMA_SEPARATOR + SPACE_SEPARATOR);
		}
		query.append(");");
		return query.toString();

	}

	public static class COMPANY_TABLE {
		public static final String COMPANY_TABLE_NAME = "COMPANIES";
		public static final String COMPANY_LOGO = "company_logo";
		public static final String COMPANY_ID = "_id";
		public static final String COMPANY_NAME = "company_name";
		public static final String COMPANY_DESCRIPTION = "company_description";
		public static final String COMPANY_LOGO_URL = "company_logo_url";
		public static final String COMPANY_URL = "company_url";

		public static String getCreateQuery() {
			return String.format(CREATE_TABLE_QUERY + SPACE_SEPARATOR
					+ COMPANY_TABLE_NAME + SPACE_SEPARATOR
					+ getStringFormator(6), COMPANY_ID, INTEGER_TYPE,
					COMPANY_NAME, String.format(VARCHAR_TYPE, 50),
					COMPANY_DESCRIPTION, TEXT_TYPE, COMPANY_LOGO_URL,
					TEXT_TYPE, COMPANY_LOGO, BLOB_TYPE, COMPANY_URL, TEXT_TYPE);
		}
	}

	public static class JOB_TABLE {
		public static final String JOBS_TABLE_NAME = "JOBS";
		public static final String JOB_ID = "_id";
		public static final String JOB_COMPANY_ID = "job_company_id";
		public static final String JOB_POSITION = "job_position";
		public static final String JOB_ELIGIBILITY_CRITERIA = "job_eligibility_criteria";
		public static final String JOB_APPLIED = "job_applied";
		public static final String JOB_LOCATION = "job_location";
		public static final String JOB_PACKAGE = "job_package";
		public static final String JOB_DESCRIPTION = "job_description";
		public static final String JOB_ELIGIBLE = "job_eligible";
		public static final String JOB_POSTED_DATE = "job_posted_date";
		public static final String JOB_PACKAGE_STATUS = "job_package_status";
		public static final String JOB_STATUS = "job_status";

		public static String getCreateQuery() {
			return String.format(
					CREATE_TABLE_QUERY + SPACE_SEPARATOR + JOBS_TABLE_NAME
							+ SPACE_SEPARATOR + getStringFormator(12), JOB_ID,
					INTEGER_TYPE, JOB_POSITION,
					String.format(VARCHAR_TYPE, 30), JOB_COMPANY_ID,
					INTEGER_TYPE, JOB_LOCATION,
					String.format(VARCHAR_TYPE, 20), JOB_PACKAGE, STRING_TYPE,
					JOB_APPLIED, BOOLEAN_TYPE, JOB_DESCRIPTION, TEXT_TYPE,
					JOB_ELIGIBLE, BOOLEAN_TYPE, JOB_ELIGIBILITY_CRITERIA,
					STRING_TYPE, JOB_POSTED_DATE, DATE_TIME_TYPE,
					JOB_PACKAGE_STATUS, String.format(VARCHAR_TYPE, 20),
					JOB_STATUS, String.format(VARCHAR_TYPE, 50));
		}
	}

	public static class NOTIFICATIONS_TABLE {
		public static final String NOTIFICATIONS_TABLE_NAME = "NOTIFICATIONS";
		public static final String NOTIFICATION_ID = "_id";
		public static final String NOTIFICATION_MESSAGE = "notification_message";
		public static final String NOTIFICATION_TIME = "notification_time";
		public static final String NOTIFICATION_TITLE = "notification_title";
		public static final String NOTIFICATION_COMPANY_ID = "notification_company_id";
		public static final String NOTIFICATION_JOB_ID = "notification_job_id";

		public static String getCreateQuery() {
			return String.format(CREATE_TABLE_QUERY + SPACE_SEPARATOR
					+ NOTIFICATIONS_TABLE_NAME + SPACE_SEPARATOR
					+ getStringFormator(6), NOTIFICATION_ID, INTEGER_TYPE,
					NOTIFICATION_MESSAGE, TEXT_TYPE, NOTIFICATION_TIME,
					INTEGER_TYPE, NOTIFICATION_TITLE, TEXT_TYPE,
					NOTIFICATION_COMPANY_ID, INTEGER_TYPE, NOTIFICATION_JOB_ID,
					INTEGER_TYPE);
		}
	}

	public static class REMINDER_TABLE {
		public static final String REMINDER_TABLE_NAME = "REMINDER";
		public static final String REMINDER_ID = "_id";
		public static final String REMINDER_SET_TIME = "reminder_set_time";
		public static final String REMINDER_ALARM_TIME = "reminder_alarm_time";
		public static final String REMINDER_MESSAGE = "reminder_message";

		public static String getCreateQuery() {
			return String.format(CREATE_TABLE_QUERY + SPACE_SEPARATOR
					+ REMINDER_TABLE_NAME + SPACE_SEPARATOR
					+ getStringFormator(4), REMINDER_ID, INTEGER_TYPE,
					REMINDER_SET_TIME, INTEGER_TYPE, REMINDER_ALARM_TIME,
					INTEGER_TYPE, REMINDER_MESSAGE, TEXT_TYPE);
		}
	}
}