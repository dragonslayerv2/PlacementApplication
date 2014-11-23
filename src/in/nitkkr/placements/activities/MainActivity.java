package in.nitkkr.placements.activities;

import in.nitkkr.placements.adapters.NavigationDrawerAdapter;
import in.nitkkr.placements.constants.ApplicationConstants;
import in.nitkkr.placements.fragments.CompanyMainFragment;
import in.nitkkr.placements.fragments.NotifactionListFragment;
import in.nitkkr.placements.fragments.ProfileFragment;
import in.nitkkr.placements.networkcalls.CompanyLogoFetcher;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.placementapplication.R;

public class MainActivity extends FragmentActivity {
	public static final String FRAGMENT_TO_OPEN = "fragment_to_open";
	public static final int JOB_FRAGMENT = 1;
	public static final int NOTIFICATIONS_FRAGMENT = 2;

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	String currentTitle;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		currentTitle = getResources().getStringArray(R.array.drawer_items)[0];
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(currentTitle);
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(
						getResources().getString(R.string.app_name));
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(new NavigationDrawerAdapter(this, getResources()
				.getStringArray(R.array.drawer_items), getResources()
				.obtainTypedArray(R.array.navigation_icons)));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		Intent callingIntent = getIntent();
		int fragmentToOpen = 1;
		if (callingIntent.getExtras() != null)
			fragmentToOpen = callingIntent.getExtras().getInt(FRAGMENT_TO_OPEN,
					1);
		selectItem(fragmentToOpen);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {

		if (position > 0) {
			getActionBar().removeAllTabs();
			getActionBar()
					.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			Fragment fragment = null;
			switch (position) {
			case 1:
				fragment = new CompanyMainFragment();
				break;
			case 2:
				fragment = new NotifactionListFragment();
				break;
			case 3:
				fragment = new ProfileFragment();
				break;
			case 4:
				logout();
				return;
			}

			getFragmentManager().beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			mDrawerList.setItemChecked(position, true);

			setTitle(getResources().getStringArray(R.array.drawer_items)[position - 1]);
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}

	void logout() {
		SharedPreferences userSettings = getSharedPreferences(
				ApplicationConstants.SHARED_PREFERENCES, 0);
		SharedPreferences.Editor userSettingsEditor = userSettings.edit();
		userSettingsEditor.clear();
		userSettingsEditor.commit();
		Intent loginActivytIntent = new Intent(this, LoginActivity.class);
		startActivity(loginActivytIntent);
		finish();
	}

	@Override
	public void setTitle(CharSequence title) {
		currentTitle = String.valueOf(title);
		getActionBar().setTitle(currentTitle);
	}
}