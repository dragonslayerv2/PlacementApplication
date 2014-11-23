package in.nitkkr.placements.adapters;

import in.nitkkr.placements.fragments.CompanyListFragment;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.placementapplication.R;

//Since this is an object collection, use a FragmentStatePagerAdapter,
//and NOT a FragmentPagerAdapter.
public class CompanyPagerAdapter extends FragmentStatePagerAdapter {
	Context context;
	CompanyListFragment appliedCompanyFragment, eligibleCompanyFragment,
			allCompanyFragment;

	public CompanyPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		appliedCompanyFragment = new CompanyListFragment();
		eligibleCompanyFragment = new CompanyListFragment();
		allCompanyFragment = new CompanyListFragment();

		appliedCompanyFragment.doQuery(CompanyListFragment.APPLIED);
		eligibleCompanyFragment.doQuery(CompanyListFragment.ELIGIBLE);
		allCompanyFragment.doQuery(CompanyListFragment.ALL);
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return appliedCompanyFragment;
		case 1:
			return eligibleCompanyFragment;
		case 2:
			return allCompanyFragment;
		default:
			throw new IllegalArgumentException("Unknown Value for position");
		}
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return context.getResources()
				.getStringArray(R.array.company_tab_labels)[position];
	}
}