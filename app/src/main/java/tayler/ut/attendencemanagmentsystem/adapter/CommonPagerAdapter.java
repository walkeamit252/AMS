package tayler.ut.attendencemanagmentsystem.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sibaprasad on 23/12/16.
 */

public class CommonPagerAdapter extends FragmentPagerAdapter {
    private static final String           TAG             = "CommonPagerAdapter";
	private final List< Fragment > mFragments      = new ArrayList<>();
	private final List< String >   mFragmentTitles = new ArrayList<>();

	public CommonPagerAdapter( FragmentManager fm ) {
		super( fm );
	}

	public void addFragment( Fragment fragment, String title ) {
		mFragments.add( fragment );
		mFragmentTitles.add( title );
	}

	@Override
	public Fragment getItem( int position ) {
		return mFragments.get( position );
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	@Override
	public CharSequence getPageTitle( int position ) {
		return mFragmentTitles.get( position );
	}
}