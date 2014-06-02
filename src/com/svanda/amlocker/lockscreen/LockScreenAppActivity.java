package com.svanda.amlocker.lockscreen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.PorterDuff.Mode;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.InjectView;

import com.dfki.GestureFramework.IGestureRecognitionListener;
import com.dfki.GestureFramework.IGestureRecognitionService;
import com.dfki.GestureFramework.classifier.Distribution;
import com.svanda.amlocker.R;

/**
 * Activity of lockscreen which provide access only for authorized users
 * @author Dominik Svanda
 *
 */
public class LockScreenAppActivity extends Activity {

    /** Called when the activity is first created. */
	  KeyguardManager.KeyguardLock k1;
	  public static boolean Locked;
	// To keep track of activity's window focus
	  boolean currentFocus;
	// To keep track of activity's foreground/background status
	boolean isPaused;
	Handler collapseNotificationHandler;
	
	IGestureRecognitionService recognitionService;
	String activeTrainingSet;
	public static Button UnlockGestureButton;
	public TextView LockTime;
	
	//PasswordFiled element
	EditText password_field;
//	private final ServiceConnection serviceConnection = new ServiceConnection() {
//
//		@Override
//		public void onServiceConnected(ComponentName className, IBinder service) {
//			recognitionService = IGestureRecognitionService.Stub.asInterface(service);
//			try {
//				recognitionService.startClassificationMode(activeTrainingSet);
//				recognitionService.registerListener(IGestureRecognitionListener.Stub.asInterface(gestureListenerStub));
//			} catch (RemoteException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
//
//		@Override
//		public void onServiceDisconnected(ComponentName className) {
//			recognitionService = null;
//		}
//	};
//	
//	IBinder gestureListenerStub = new IGestureRecognitionListener.Stub() {
//
//
//		@Override
//		public void onGestureRecognized(final Distribution distribution) throws RemoteException {
//			SharedPreferences settings = getSharedPreferences("Gestures", 0);
//			final int tolerance = settings.getInt("Tolerance", 0);
//			runOnUiThread(new Runnable() {
//				@Override
//				public void run() {
//					if (distribution.getBestDistance() <  tolerance  ){
//				    	start_shortcut(distribution.getBestMatch());
//						//gestureAroundStart.setBackgroundColor(0xFF00FF00);
//						Toast.makeText(LockScreenAppActivity.this, String.format("%s: %f", distribution.getBestMatch(), distribution.getBestDistance()), Toast.LENGTH_LONG).show();
//					}else{
//						//gestureAroundStart.setBackgroundColor(0xFFFF0000);
//						Toast.makeText(LockScreenAppActivity.this, String.format("%s: %f", distribution.getBestMatch(), distribution.getBestDistance()), Toast.LENGTH_LONG).show();
//					}
//				}
//			});
//		}
//
//		@Override
//		public void onGestureLearned(String gestureName) throws RemoteException {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onTrainingSetDeleted(String trainingSet)
//				throws RemoteException {
//			// TODO Auto-generated method stub
//			
//		}
//	};
	/**
	 * Start applications which are bind to gesture 
	 * @param bestMatch Name of trained gesture which has best match to current created gesture
	 */
	public void start_shortcut(String bestMatch){
		SharedPreferences settings = getSharedPreferences("AppShortcuts", 0);
		if ("Launcher".equals(bestMatch )) {
			Locked = false;
			Intent intent  = new Intent().addCategory(Intent.CATEGORY_HOME).setAction(Intent.ACTION_MAIN).setPackage(settings.getString("Launcher", "").toString());
	 		intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
	 		startActivity(intent);
		}
		else if ("Shortcut App1".equals(bestMatch )) {
			Intent intent  = new Intent().setAction(Intent.ACTION_MAIN).setPackage(settings.getString("Shortcut1", "").toString());;
	 		startActivity(intent);
		}
		else if ("Shortcut App2".equals(bestMatch )) {
			Intent intent  = new Intent().setAction(Intent.ACTION_MAIN).setPackage(settings.getString("Shortcut2", "").toString());
	 		startActivity(intent);
		}
		else if ("Shortcut App3".equals(bestMatch )) {
			Intent intent  = new Intent().setAction(Intent.ACTION_MAIN).setPackage(settings.getString("Shortcut3", "").toString());
	 		startActivity(intent);
		}
	}
	@Override
	  public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activeTrainingSet = getResources().getString(R.string.gesture_train_set);
		  requestWindowFeature(Window.FEATURE_NO_TITLE);
		  getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
	      getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_FULLSCREEN/
	                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);	        
	        setContentView(R.layout.lockscreen);
	        password_field = (EditText) findViewById(R.id.PasswordField);
	        UnlockGestureButton = (Button)findViewById(R.id.unlock);
	        LockTime = (TextView) findViewById(R.id.lock_time);
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	        String currentDateandTime = sdf.format(new Date());
	        LockTime.setText(currentDateandTime);
	        
		      
		      startService(new Intent(this,MyService.class).setAction(Intent.ACTION_SCREEN_OFF));
	        
	        
    	   try {
    		   // initialize receiver
    		   //startService(new Intent(this,MyService.class));
    		   StateListener phoneStateListener = new StateListener();
    		   TelephonyManager telephonyManager =(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
    		   telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);
 

           }catch (Exception e) {
   			// TODO: handle exception
           } 
	  }
	/**
	 * State listener for call and telephone manager
	 * @author Dominik Svanda
	 *
	 */
	  class StateListener extends PhoneStateListener{
        	@Override
        	public void onCallStateChanged(int state, String incomingNumber) {

            	super.onCallStateChanged(state, incomingNumber);
            	switch(state){
                	case TelephonyManager.CALL_STATE_RINGING:
                		break;
                	case TelephonyManager.CALL_STATE_OFFHOOK:
                		System.out.println("call Activity off hook");
                		finish();
                		break;
                	case TelephonyManager.CALL_STATE_IDLE:
                		break;
            	}
        	}
        }
	  /**
	   * Unlock device when user insert correct password and hit unlock button
	   * @param view Current view
	   */
     public void UnLock(View view){
//    	
    	Locked = false;
		SharedPreferences settings = getSharedPreferences("AppShortcuts", 0);
		Intent intent  = new Intent().addCategory(Intent.CATEGORY_HOME).setAction(Intent.ACTION_MAIN).setPackage(settings.getString("Launcher", "").toString());
		intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
 		startActivity(intent);
 		startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
     }
     /*public void OtherClick(View view){
    	 //Toast.makeText(this, "Huraaa", Toast.LENGTH_SHORT).show();
     }*/
     /**
      * Disable to show recent application list notification bar pull down when lockscreen activity is on
      */
     public void collapseNow() {

    	    // Initialize 'collapseNotificationHandler'
    	    if (collapseNotificationHandler == null) {
    	        collapseNotificationHandler = new Handler();
    	    }

    	    // If window focus has been lost && activity is not in a paused state
    	    // Its a valid check because showing of notification panel
    	    // steals the focus from current activity's window, but does not 
    	    // 'pause' the activity
    	    if (!currentFocus && !isPaused) {

    	        // Post a Runnable with some delay - currently set to 300 ms
    	        collapseNotificationHandler.postDelayed(new Runnable() {

    	            @Override
    	            public void run() {

    	                // Use reflection to trigger a method from 'StatusBarManager'                

    	                Object statusBarService = getSystemService("statusbar");
    	                Class<?> statusBarManager = null;

    	                try {
    	                    statusBarManager = Class.forName("android.app.StatusBarManager");
    	                } catch (ClassNotFoundException e) {
    	                    e.printStackTrace();
    	                }

    	                Method collapseStatusBar = null;

    	                try {

    	                    // Prior to API 17, the method to call is 'collapse()'
    	                    // API 17 onwards, the method to call is `collapsePanels()`

    	                    if (Build.VERSION.SDK_INT > 16) {
    	                        collapseStatusBar = statusBarManager.getMethod("collapsePanels");
    	                    } else {
    	                        collapseStatusBar = statusBarManager.getMethod("collapse");
    	                    }
    	                } catch (NoSuchMethodException e) {
    	                    e.printStackTrace();
    	                }

    	                collapseStatusBar.setAccessible(true);

    	                try {
    	                    collapseStatusBar.invoke(statusBarService);
    	                } catch (IllegalArgumentException e) {
    	                    e.printStackTrace();
    	                } catch (IllegalAccessException e) {
    	                    e.printStackTrace();
    	                } catch (InvocationTargetException e) {
    	                    e.printStackTrace();
    	                }

    	                // Check if the window focus has been returned
    	                // If it hasn't been returned, post this Runnable again
    	                // Currently, the delay is 100 ms. You can change this
    	                // value to suit your needs.
    	                if (!currentFocus && !isPaused) {
    	                    collapseNotificationHandler.postDelayed(this, 5L);
    	                    //collapseNotificationHandler.postAtTime(this, 0L);
    	                }

    	            }
    	        }, 5L);
    	    }   
     }
     /**
      * Make action when focus of lockscreen window was changed
      */
     @Override
     public void onWindowFocusChanged(boolean hasFocus) {
    		//super.onWindowFocusChanged(hasFocus);
    		currentFocus = hasFocus;
    		if(!hasFocus) { 
    			collapseNow();
    			/*Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
    			sendBroadcast(closeDialog);*/
    		}
    }
     /**
      * Show and hide password field when back button was pressed when locskscreen is on
      */
    @Override
    public void onBackPressed() {
    	
    	if(password_field.getVisibility() == View.GONE){
    		password_field.setVisibility(View.VISIBLE);
    	}else{
    		password_field.setVisibility(View.GONE);
    	}
//    	if (recognitionService != null){
//    			Toast.makeText(this, "Huraaa", Toast.LENGTH_SHORT).show();
//    	}
    	return;
    }

	/**
	 * Unbind gesture recognition service when lockscreen goes to background
	 */
    @Override
    protected void onPause() {
        isPaused = true;
    	Vibrator v = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
    	//v.vibrate(100);
    	//TODO Problem s pause ked sa snazim vypnut servis
//		try {
//			recognitionService.unregisterListener(IGestureRecognitionListener.Stub.asInterface(gestureListenerStub));
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//		recognitionService = null;
//		unbindService(serviceConnection);
        super.onPause();

    }
	/**
	 * Bind gesture recognition service when lockscreen resume from background
	 */
    @Override
    protected void onResume(){
    	Vibrator v = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
    	//v.vibrate(500);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    	isPaused = false;
    	if(Locked == false){
    		SharedPreferences settings = getSharedPreferences("AppShortcuts", 0);
    		Intent intent  = new Intent().addCategory(Intent.CATEGORY_HOME).setAction(Intent.ACTION_MAIN).setPackage(settings.getString("Launcher", "").toString());
     		intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
     		startActivity(intent);
    	}
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentDateandTime = sdf.format(new Date());
        LockTime.setText(currentDateandTime);
    	super.onResume();
//		activeTrainingSet = getResources().getString(R.string.gesture_train_set);
//		Intent bindIntent = new Intent("com.dfki.GestureFramework.GESTURE_RECOGNIZER");
//		bindService(bindIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    /**
     * Handle press of keys (Back button, recent button, menu button)
     */
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
        	
            event.startTracking();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
    /**
     * Handle long press of keys (Back button, recent button, menu button)
     */
    @Override
    public boolean onKeyLongPress( int keyCode, KeyEvent event ) {
      if( keyCode == KeyEvent.KEYCODE_BACK ) {
    	    Intent intent = getIntent();
    	    overridePendingTransition(0, 0);
    	    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    	    finish();

    	    overridePendingTransition(0, 0);
    	    startActivity(intent);
    	  Toast.makeText(this, "LongPress", Toast.LENGTH_SHORT).show();
    	  
        return true;
      }
      return super.onKeyLongPress( keyCode, event );
    }
    public void onDestroy(){
       //k1.reenableKeyguard();

        super.onDestroy();
    }
}