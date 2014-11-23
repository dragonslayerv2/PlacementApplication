package in.nitkkr.placements.fragments;

import in.nitkkr.placements.adapters.CompanyPagerAdapter;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.placementapplication.R;

public class CompanyMainFragment extends Fragment {
	CompanyPagerAdapter mCompanyPagerAdapter;
	ViewPager mViewPager;
	FragmentManager fragmentManager;

	// public CompanyMainFragment(FragmentManager fragmentManager) {
	// this.fragmentManager = fragmentManager;
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View fragmentView = inflater.inflate(R.layout.fragment_company, null);
		fragmentManager = ((FragmentActivity) getActivity())
				.getSupportFragmentManager();
		mCompanyPagerAdapter = new CompanyPagerAdapter(fragmentManager,
				getActivity());
		mViewPager = (ViewPager) fragmentView.findViewById(R.id.pager);
		mViewPager.setAdapter(mCompanyPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						getActivity().getActionBar().setSelectedNavigationItem(
								position);
					}
				});

		getActivity().getActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_TABS);

		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			@Override
			public void onTabReselected(Tab arg0,
					android.app.FragmentTransaction arg1) {
			}

			@Override
			public void onTabUnselected(Tab tab,
					android.app.FragmentTransaction ft) {
			}

			@Override
			public void onTabSelected(Tab tab,
					android.app.FragmentTransaction ft) {
				mViewPager.setCurrentItem(tab.getPosition());
			}
		};
		for (int i = 0; i < 3; i++) {
			getActivity().getActionBar().addTab(
					getActivity()
							.getActionBar()
							.newTab()
							.setText(
									getResources().getStringArray(
											R.array.company_tab_labels)[i])
							.setTabListener(tabListener));
		}
		return fragmentView;
	}
}
