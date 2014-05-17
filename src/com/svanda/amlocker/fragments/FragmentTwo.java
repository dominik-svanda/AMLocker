package com.svanda.amlocker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.svanda.amlocker.adapters.AppInfoApadpter;
import com.svanda.amlocker.activities.Utilities;
import com.svanda.amlocker.R;

public class FragmentTwo extends Fragment {

	@InjectView(R.id.apps_launcher)
	Spinner spinner_launcher;
	@InjectView(R.id.apps_shortcut1)
	Spinner spinner_shortcut1;
	@InjectView(R.id.apps_shortcut2)
	Spinner spinner_shortcut2;
	@InjectView(R.id.apps_shortcut3)
	Spinner spinner_shortcut3;
	
	@InjectView(R.id.apps_text_launcher)
	TextView text_launcher;
	
	public static FragmentTwo newInstance() {
		FragmentTwo fragment = new FragmentTwo();
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_two, container, false);
		ButterKnife.inject(this, view);
		AppInfoApadpter adapter1 = new AppInfoApadpter(getActivity(), Utilities.getLaunchers(getActivity()), getActivity().getPackageManager());
        AppInfoApadpter adapter2 = new AppInfoApadpter(getActivity(), Utilities.getInstalledApplications(getActivity()), getActivity().getPackageManager());
        // set adapter to list view
        spinner_launcher.setAdapter(adapter1);
        spinner_shortcut1.setAdapter(adapter2);
        spinner_shortcut2.setAdapter(adapter2);
        spinner_shortcut3.setAdapter(adapter2);
		
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	@OnClick(R.id.apps_text_launcher)
	void spinner1Clicked(View v){
		ResolveInfo entry = (ResolveInfo)spinner_launcher.getSelectedItem();
		Toast.makeText(getActivity(), String.format(entry.activityInfo.packageName), Toast.LENGTH_LONG).show();
		
	}
	
}
