package in.nitkkr.placements.activities;

import in.nitkkr.placements.database.DatabaseContract.COMPANY_TABLE;
import in.nitkkr.placements.database.DatabaseContract.JOB_TABLE;
import in.nitkkr.placements.database.DatabaseHandler;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.placementapplication.R;

public class JobDetailsActivity extends Activity implements OnClickListener {
	int jobId;
	int companyId;

	Cursor jobCursor, companyCursor;
	TextView companyName, jobStatus, postedDate, jobProfile, jobLocation,
			jobPackage, jobPackageStatus, jobDescription, companyDescription,
			jobEligibilityCriteria;
	ImageView companyLogo;
	View logoBackground;
	Button applyButton;
	Dialog applyDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job_details);
		getViews();
		initiateCursors();
		showData();
	}

	void getViews() {
		companyName = (TextView) findViewById(R.id.job_details_company_name);
		jobStatus = (TextView) findViewById(R.id.job_details_status);
		postedDate = (TextView) findViewById(R.id.job_details_posted_date);
		jobProfile = (TextView) findViewById(R.id.job_details_job_profile);
		jobLocation = (TextView) findViewById(R.id.job_details_location);
		jobPackage = (TextView) findViewById(R.id.job_details_package);
		jobPackageStatus = (TextView) findViewById(R.id.job_details_package_status);
		jobDescription = (TextView) findViewById(R.id.job_details_additional_info);
		companyDescription = (TextView) findViewById(R.id.job_details_company_description);
		jobEligibilityCriteria = (TextView) findViewById(R.id.job_details_eligibility_criteria);
		companyLogo = (ImageView) findViewById(R.id.jobs_details_company_icon);
		logoBackground = findViewById(R.id.jobs_details_logo_background);

		applyButton = (Button) findViewById(R.id.jobs_details_apply_button);
	}

	void initiateCursors() {
		jobId = getIntent().getExtras().getInt(
				JOB_TABLE.JOBS_TABLE_NAME + "." + JOB_TABLE.JOB_ID);
		companyId = getIntent().getExtras().getInt(
				COMPANY_TABLE.COMPANY_TABLE_NAME + "."
						+ COMPANY_TABLE.COMPANY_ID);

		jobCursor = DatabaseHandler.getInstance(this).getJobDetails(jobId);
		companyCursor = DatabaseHandler.getInstance(this).getCompanyDetails(
				companyId);
		jobCursor.moveToPosition(0);
		companyCursor.moveToPosition(0);
	}

	void showData() {
		String companyName = companyCursor.getString(companyCursor
				.getColumnIndex(COMPANY_TABLE.COMPANY_NAME));
		String jobStatus = jobCursor.getString(jobCursor
				.getColumnIndex(JOB_TABLE.JOB_STATUS));
		String companyURL = companyCursor.getString(companyCursor
				.getColumnIndex(COMPANY_TABLE.COMPANY_URL));
		String postedDate = jobCursor.getString(jobCursor
				.getColumnIndex(JOB_TABLE.JOB_POSTED_DATE));
		String jobProfile = jobCursor.getString(jobCursor
				.getColumnIndex(JOB_TABLE.JOB_POSITION));
		String jobLocation = jobCursor.getString(jobCursor
				.getColumnIndex(JOB_TABLE.JOB_LOCATION));
		String jobPackage = jobCursor.getString(jobCursor
				.getColumnIndex(JOB_TABLE.JOB_PACKAGE));
		String jobPackageStatus = jobCursor.getString(jobCursor
				.getColumnIndex(JOB_TABLE.JOB_PACKAGE_STATUS));
		String jobDescription = jobCursor.getString(jobCursor
				.getColumnIndex(JOB_TABLE.JOB_DESCRIPTION));
		String companyDescription = companyCursor.getString(companyCursor
				.getColumnIndex(COMPANY_TABLE.COMPANY_DESCRIPTION));
		String jobEligibilityCriteria = jobCursor.getString(jobCursor
				.getColumnIndex(JOB_TABLE.JOB_ELIGIBILITY_CRITERIA));
		int eligible = jobCursor.getInt(jobCursor
				.getColumnIndex(JOB_TABLE.JOB_ELIGIBLE));
		int applied = jobCursor.getInt(jobCursor
				.getColumnIndex(JOB_TABLE.JOB_APPLIED));

		getActionBar().setSubtitle(companyName);

		this.companyName.setText(companyName);
		this.jobStatus.setText(jobStatus);
		this.postedDate.setText(postedDate);
		this.jobProfile.setText(jobProfile);
		this.jobLocation.setText(jobLocation);
		this.jobPackage.setText(jobPackage);
		this.jobPackageStatus.setText(jobPackageStatus);
		this.jobDescription.setMovementMethod(LinkMovementMethod.getInstance());
		this.jobDescription.setText(Html.fromHtml(jobDescription, null, null));
		this.companyDescription.setMovementMethod(LinkMovementMethod
				.getInstance());
		this.companyDescription.setText(Html.fromHtml(companyDescription, null,
				null));
		this.jobEligibilityCriteria.setText(jobEligibilityCriteria);

		byte[] logoImageArray = companyCursor.getBlob(companyCursor
				.getColumnIndex(COMPANY_TABLE.COMPANY_DESCRIPTION));
		Log.i("Setting up the logo","Setting up the logo. Is database logo null? "+String.valueOf(logoImageArray==null));
		byte[] logoImage;
		if ((logoImage = companyCursor
				.getBlob(companyCursor.getColumnIndex(COMPANY_TABLE.COMPANY_LOGO))) != null) {
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inSampleSize=2;
			Bitmap logoBitmap = BitmapFactory.decodeByteArray(logoImage, 0,
					logoImage.length,o);
			if (logoBitmap != null)
				companyLogo.setImageBitmap(logoBitmap);
			else
				companyLogo.setImageDrawable(getResources()
						.getDrawable(R.drawable.default_company_logo));
		} else
			companyLogo.setImageDrawable(getResources().getDrawable(
					R.drawable.default_company_logo));
		if (eligible == 1 && applied == 0) {
			this.applyButton.setOnClickListener(this);
		}
		if (eligible == 0) {
			this.applyButton.setVisibility(View.INVISIBLE);
			this.jobStatus.setText("Not Eligible");
			this.jobStatus.setTextColor(Color.RED);
		} else if (applied > 0) {
			this.applyButton.setText("Applied");
			this.applyButton.setClickable(false);
		}

	}

	@Override
	public void onClick(View v) {
		applyDialog = new Dialog(this);
		applyDialog.setCancelable(false);
		applyDialog.setContentView(R.layout.dialog_apply);
		applyDialog.setTitle("Confirm");
		Button noButton = (Button) applyDialog
				.findViewById(R.id.apply_dialog_no_button);
		Button yesButton = (Button) applyDialog
				.findViewById(R.id.apply_dialog_yes_button);

		noButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				applyDialog.dismiss();
			}
		});
		yesButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(JobDetailsActivity.this,
						"Applied Successfully.", Toast.LENGTH_SHORT).show();
				applyButton.setText("Applied");
				applyButton.setClickable(false);
				applyDialog.dismiss();

			}

		});
		applyDialog.show();
	}
}
