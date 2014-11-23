package in.nitkkr.placements.mockdatagenerator;

import in.nitkkr.placements.database.DatabaseContract;
import in.nitkkr.placements.database.DatabaseContract.COMPANY_TABLE;
import in.nitkkr.placements.database.DatabaseContract.JOB_TABLE;
import in.nitkkr.placements.database.DatabaseContract.NOTIFICATIONS_TABLE;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class MockDataGenerator {
	static String companyName[] = { "HCC", "Novel India Developement Center",
			"Intel Inc.", "Marvell", "Newgen" };

	static String companyUrl[] = { "http://www.hccindia.com",
			"http://www.novelincorporated.com/", "http://www.intel.in",
			"http://www.marvell.com", "http://newgensoft.com" };

	static String companyLogoUrl[] = { "http://www.hcc.com",
			"http://www.novell.com", "http://www.intel.com",
			"http://www.marvell.com", "http://newgen.com" };
	static String description[] = {
			"HCC is a business group of global scale developing and building responsible infrastructure through next practices. With an engineering heritage of nearly 100 years, HCC has executed a majority of Indias landmark infrastructure projects, having constructed 25% of India's Hydel Power generation and over 50% of Indias Nuclear Power generation capacities, over 3,300 lane km of Expressways and Highways, more than 200 km of complex Tunneling and over 324 Bridges.",
			"Novell India Development Center, Bangalore contributes to the next generation technology evolution by owning end-to-end engineering responsibility for key Novell, NetIQ, SUSE products & technologies. The teams here contribute to award-winning Novell products - leading to several innovations, product initiatives & patents. They develop products & technologies relating to Endpoint Life Cycle and Security Management, Security Information and Event Management, Intelligent Workload and Identity Management, Core Platform Services - File Systems & Storage Protocols, Directory Services & Open Enterprise and SUSE Linux Platforms.",
			"The secret is out Intel is not just a chip company there is more inside to it. Intel is starting up a culture to provide an open environment to explore the best out the technology around and work in the most challenging software developments globally. But there is more inside to it; Intel India gives you the freedom and space to build completely new softwares, write codes, test and match make with your chosen hardware. It gives you the opportunity to work with the legends of software, creating, learning, thinking….like them. If you are looking for a workplace that will bring out the best in you, WE are in pursuit of high-tech engineers, looking to be challenged to reach new heights; it will provide you a role that challenges. Roles which will make you feel incredible in your own ways. Are you Passionate about making the best of yourself?? Explore the site. Theres something different in software at Intel. Its called the future Know more about it on <a href=\"http://www.intel.com/jobs/\">http://www.intel.com/jobs/</a>",
			"A Santa Clara based leading fabless semiconductor company with expertise in microprocessor architecture and digital signal processing, storage solutions, mobile and wireless technology, networking and consumer products is hiring in India. With an outstanding history of delivering next generation products that are revolutionizing the way the world works, we're looking for smart and talented people to join us on this adventure in designing products of the future. Fostering your personal and professional growth we provide an advanced research environment where your work can really make a difference.",
			"Newgen Software is a leading global provider of Business Process Management (BPM), Enterprise Content Management (ECM) and Customer Communication Management (CCM), with a global footprint of 850 installations in over 45 countries with large, mission-critical solutions deployed at the world's leading Banks, Insurance firms, BPO\"s, Healthcare Organizations, Government, Telecom Companies & Shared Service Centers. Newgen Software has been positioned in the Magic Quadrant for Business Process Management (BPM) and Enterprise Content Management (ECM)." };

	static String jobDescription[] = {
			"Develops information systems by designing, developing, and installing software solutions.",
			"Business analysts are sometimes thought of as the bridge between the troubles within a firm and the answers to fixing them. And when you look at this job description, its easy to see why theyd have this reputation.",
			"A challenging and varied role in IT, a network manager is a job of two halves. You are responsible for both installing and maintaining your company's computer networks." };
	static String eligibilityCriteria[] = { "CGPA above 6.00",
			"CGPA above 7.00", "CGPA above 9.00", "No Backlogs",
			"Backlogs allowed" };
	static String location[] = { "Bangalore", "Delhi", "Chandigarh",
			"Hyderabad", "Pune" };
	static String salary[] = { "Rs. 12,00,000", "Rs. 6,00,0000",
			"Rs. 17,00,0000" };
	static String packageStatus[] = { "Dream", "Normal", "Super Dream" };
	static String position[] = { "Software Engineer", "Mechanical Engineer",
			"Civil Engineer" };
	static String status[] = { "Eligible" };
	static String notificationTitle[] = { "Dates Extended",
			"Bring your Resume", "Presentation Timings Changed" };
	static String notificationMessage[] = {
			"Dates of the resume submission are increased to 10-10-12 ",
			"All shortlisted candidates have to bring their resumes to Senate as soon as possible",
			"Presentation Timings Changed of the company has been changed to adads weq 123 132 13 1213" };

	public static void createMockCompanyData(SQLiteDatabase db) {
		for (int i = 0; i < 5; i++)
			insertCompany(db, i, companyName[i], companyUrl[i],
					companyLogoUrl[i], description[i]);
	}

	public static void createMockJobData(SQLiteDatabase db) {
		for (int i = 0; i < 25; i++) {
			int eligible = (int) (Math.random() * 2);
			int applied = eligible * (int) (Math.random() * 2);
			insertJob(
					db,
					i,
					(int) (Math.random() * companyName.length),
					jobDescription[(int) (Math.random() * jobDescription.length)],
					eligibilityCriteria[(int) (Math.random() * eligibilityCriteria.length)],
					location[(int) (Math.random() * location.length)],
					salary[(int) (Math.random() * salary.length)],
					packageStatus[(int) (Math.random() * packageStatus.length)],
					position[(int) (Math.random() * position.length)],
					status[(int) (Math.random() * status.length)], eligible,
					applied);
		}
	}

	public static void insertCompany(SQLiteDatabase db, int id,
			String companyName, String url, String logo_url, String description) {
		ContentValues values = new ContentValues();
		values.put(COMPANY_TABLE.COMPANY_ID, id);
		values.put(COMPANY_TABLE.COMPANY_NAME, companyName);
		values.put(COMPANY_TABLE.COMPANY_URL, url);
		values.put(COMPANY_TABLE.COMPANY_LOGO_URL, logo_url);
		values.put(COMPANY_TABLE.COMPANY_DESCRIPTION, description);
		db.insert(DatabaseContract.COMPANY_TABLE.COMPANY_TABLE_NAME, null,
				values);
	}

	public static void insertJob(SQLiteDatabase db, int jobid, int companyid,
			String description, String eligibilityCriteria, String location,
			String packge, String packageStatus, String position,
			String status, int eligible, int applied) {
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
		db.insert(JOB_TABLE.JOBS_TABLE_NAME, null, values);
	}

	public static void createMockNotificationData(SQLiteDatabase db) {
		for (int i = 0; i < 50; i++) {
			int companyId = (int) (Math.random() * companyName.length);
			int jobId = (int) (Math.random() * 25);
			insertNotification(
					db,
					i,
					companyId,
					jobId,
					notificationMessage[(int) (Math.random() * notificationMessage.length)],
					notificationTitle[(int) (Math.random() * notificationTitle.length)]);
		}
	}

	public static void insertNotification(SQLiteDatabase database,
			int notificationId, int companyId, int jobId, String message,
			String title) {
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

}
