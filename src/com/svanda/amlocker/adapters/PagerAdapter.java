package com.svanda.amlocker.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.svanda.amlocker.fragments.FragmentOne;
import com.svanda.amlocker.fragments.FragmentThree;
import com.svanda.amlocker.fragments.FragmentTwo;

public class PagerAdapter extends FragmentPagerAdapter {

	public PagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		
		switch (position) {
		case 0:
			return FragmentOne.newInstance();
		case 1:
			return FragmentTwo.newInstance();
		case 2:
			return FragmentThree.newInstance();
		default:
			return FragmentOne.newInstance();
		}
		
	}

	@Override
	public int getCount() {
		return 3;
	}

	

}
