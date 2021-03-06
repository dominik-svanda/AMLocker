// Akcie lockscreenu po vypnuti obrazovky a pri starte zariadenia
package com.svanda.amlocker.lockscreen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.svanda.amlocker.lockscreen.LockScreenAppActivity;

/**
 * Make actions when screen is of and on
 * Start lockscreen activity window when screen of device is on
 * @author Dominik Svanda
 *
 */
public class lockScreenReceiver extends BroadcastReceiver  {
	 public static boolean wasScreenOn = true;
	 //Vibrator v = (Vibrator) this.C.getSystemService(Context.VIBRATOR_SERVICE);
	 
	 /**
	  * Receive intents ACTION_SCREEN_OFF, ACTION_SCREEN_ON and ACTION_BOOT_COMPLETED to react on these states
	  * and run LockScreeAppActivity
	  */
	@Override
	public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
        	//v.vibrate(500);
        	wasScreenOn=false;
        	LockScreenAppActivity.Locked = true;
        	Intent intent11 = new Intent(context,LockScreenAppActivity.class);
        	intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        	context.startActivity(intent11);        	
            // do whatever you need to do here
            //wasScreenOn = false;
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
        	wasScreenOn=true;
        	Intent intent11 = new Intent(context,LockScreenAppActivity.class);
        	intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // and do whatever you need to do here
           // wasScreenOn = true;
        }
       else if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            
        	Intent intent11 = new Intent(context, LockScreenAppActivity.class);

        	intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	context.startActivity(intent11);

        }

    }


}
