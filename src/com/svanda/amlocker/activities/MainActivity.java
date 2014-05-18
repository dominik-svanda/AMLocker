package com.svanda.amlocker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.svanda.amlocker.R;
import com.svanda.amlocker.fragments.MainFragment;
import com.svanda.amlocker.lockscreen.*;

public class MainActivity extends FragmentActivity {

	int flag = 0;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
//		if (flag == 0){
//			flag = 1;
			startService(new Intent(this,MyService.class).setAction(Intent.ACTION_SCREEN_OFF));
//		}
		setContentView(R.layout.activity_main);
		
		MainFragment fragment = MainFragment.newInstance();
		
		getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, fragment)
							.commit();
		
	}
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_HOME) {
            event.startTracking();
            
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
protected void onResume(){
//	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
//    	if(LockScreenAppActivity.Locked == false){
//    		Intent intent  = new Intent().addCategory(Intent.CATEGORY_HOME).setAction(Intent.ACTION_MAIN).setPackage("com.miui.home");
//     		intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//     		startActivity(intent);
//    	}
    	super.onResume();
    	startService(new Intent(this,LockScreenAppActivity.class).setAction(Intent.ACTION_SCREEN_OFF));
    }
	
}
