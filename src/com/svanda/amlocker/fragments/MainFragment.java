package com.svanda.amlocker.fragments;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.svanda.amlocker.R;
 /**
  * Create tabs and layout for other fragments in this activity
  * @author Dominik Svanda
  *
  */
public class MainFragment extends Fragment implements TabListener {

	@InjectView(R.id.pager) ViewPager mPager;
	private PagerAdapter mPagerAdapter;
	
	String[] mTabNames;
	
	public static MainFragment newInstance() {
		MainFragment fragment = new MainFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mTabNames = getActivity().getResources().getStringArray(R.array.tab_names);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		
		ButterKnife.inject(this, view);
		
		mPagerAdapter = new com.svanda.amlocker.adapters.PagerAdapter(getChildFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		
		return view;
	}
	/**
	 * Create tabs at the top at activity window
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		 
		final ActionBar actionBar = getActivity().getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		for(int i = 0; i < 3; i++){
			ActionBar.Tab tab = actionBar.newTab().setText(mTabNames[i]).setTabListener(this);
			actionBar.addTab(tab);
		}
		
		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
			@Override
			/**
			 * Action when tab is selected
			 */
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
}
