package com.svanda.amlocker.fragments;

import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.svanda.amlocker.R;
import com.svanda.amlocker.activities.Utilities;
import com.svanda.amlocker.adapters.AppInfoApadpter;

public class FragmentTwo extends Fragment {

	@InjectView(R.id.apps_launcher)
	Spinner spinner_launcher;
	@InjectView(R.id.apps_shortcut1)
	Spinner spinner_shortcut1;
	@InjectView(R.id.apps_shortcut2)
	Spinner spinner_shortcut2;
	@InjectView(R.id.apps_shortcut3)
	Spinner spinner_shortcut3;
	@InjectView(R.id.apps_save_button)
	Button apps_save;
	
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
		SharedPreferences settings = getActivity().getSharedPreferences("AppShortcuts", 0);
		Toast.makeText(getActivity(), String.format(settings.getString("Launcher", "").toString()), Toast.LENGTH_LONG).show();
		
	}
	@OnClick(R.id.apps_save_button)
	void saveClicked(View v){
		ResolveInfo entry1 = (ResolveInfo)spinner_launcher.getSelectedItem();
		ResolveInfo entry2 = (ResolveInfo)spinner_shortcut1.getSelectedItem();
		ResolveInfo entry3 = (ResolveInfo)spinner_shortcut2.getSelectedItem();
		ResolveInfo entry4 = (ResolveInfo)spinner_shortcut3.getSelectedItem();
		
		SharedPreferences settings = getActivity().getSharedPreferences("AppShortcuts", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("Launcher",entry1.activityInfo.packageName);
		editor.putString("Shortcut1",entry2.activityInfo.packageName);
		editor.putString("Shortcut2",entry3.activityInfo.packageName);
		editor.putString("Shortcut3",entry4.activityInfo.packageName);
		editor.commit();
		
		Toast.makeText(getActivity(), String.format(getString(R.string.save_settings_prompt)), Toast.LENGTH_LONG).show();
	}
}
