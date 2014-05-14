package com.svanda.amlocker.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.svanda.amlocker.R;
import com.svanda.amlocker.fragments.MainFragment;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.activity_main);
		
		MainFragment fragment = MainFragment.newInstance();
		
		getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, fragment)
							.commit();
	}
	
	
}
