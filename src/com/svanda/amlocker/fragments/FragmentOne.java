package com.svanda.amlocker.fragments;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.dfki.GestureFramework.IGestureRecognitionListener;
import com.dfki.GestureFramework.IGestureRecognitionService;
import com.dfki.GestureFramework.classifier.Distribution;
import com.svanda.amlocker.R;
import com.svanda.amlocker.activities.MainActivity;

/**
 * Fragment for managing 3D gestures recorded from accelerometer
 * Provides training mode to create new gestures, delete gestures, test recorded gestures
 * and start gesture recognition service
 * @author Dominik Svanda
 *
 */
public class FragmentOne extends Fragment {

	IGestureRecognitionService recognitionService;
	String activeTrainingSet;
	public static boolean gesture_track = false; 
//	@InjectView(R.id.gesture_start_stop)
	public static Button gestureStartStop;
	@InjectView(R.id.gesture_train)
	Button gestureTrain;
	@InjectView(R.id.save_password)
	Button gestureDelete;
	@InjectView(R.id.gesture_around_start)
	RelativeLayout gestureAroundStart;
	@InjectView(R.id.gesture_dropdown)
	Spinner gestureDropdown;
	@InjectView(R.id.gesture_train_progress)
	ProgressBar gestureTrainProgress;
	@InjectView(R.id.gesture_tolerance)
	SeekBar gestureTolerance;
	
	private int trainProgress = 0;
	
	public boolean getTrackMode(){
		return gesture_track;
	}
	public void setTrackMode(boolean mode){
		gesture_track = mode;
	}
	
	/**
	 * Service connection for Gesture Recognition Service
	 */
	private final ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			recognitionService = IGestureRecognitionService.Stub.asInterface(service);
			try {
				recognitionService.startClassificationMode(activeTrainingSet);
				recognitionService.registerListener(IGestureRecognitionListener.Stub.asInterface(gestureListenerStub));
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName className) {
			recognitionService = null;
		}
	};

	IBinder gestureListenerStub = new IGestureRecognitionListener.Stub() {

		/**
		 * Make an action when new gesture is learned
		 * increment trainProgress which fill progress bar which indicate number of training  
		 * @param gestureName Name of gesture
		 */
		@Override
		public void onGestureLearned(String gestureName) throws RemoteException {
			Toast.makeText(getActivity(), String.format("Gesto %s natrenovane", gestureName), Toast.LENGTH_SHORT).show();

			trainProgress++;
			if (trainProgress < 3)
				gestureTrainProgress.getProgressDrawable().setColorFilter(0xFFFF3232, Mode.SRC_IN);
			if (trainProgress == 3 || trainProgress == 4)
				gestureTrainProgress.getProgressDrawable().setColorFilter(0xFFFF9900, Mode.SRC_IN);			
			if(trainProgress == 5){
				gestureTrainProgress.getProgressDrawable().setColorFilter(0xFF00E500, Mode.SRC_IN);
			}
			gestureTrainProgress.setProgress(trainProgress);
		}
		
		/**
		 * Make an action when gesture set is deleted
		 * @param trainingSet Name of gesture set
		 */
		@Override
		public void onTrainingSetDeleted(String trainingSet) throws RemoteException {
			Toast.makeText(getActivity(), String.format("Trenovacia mnozina %s vymazana", trainingSet), Toast.LENGTH_SHORT).show();
			System.err.println(String.format("Training set %s deleted", trainingSet));
			gestureTrainProgress.setProgress(0);
		}
		/**
		 * Make an action when gesture is recognized
		 * If gesture is recognized and distance from trained gesture is under tolerance threshold
		 * set background of spinner to green else to red color
		 * 
		 */
		@Override
		public void onGestureRecognized(final Distribution distribution) throws RemoteException {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (distribution.getBestDistance() < gestureTolerance.getProgress() &&  distribution.getBestMatch().equals( gestureDropdown.getSelectedItem().toString() ) ){	
						gestureDropdown.setBackgroundColor(0xFF00E500);
						//gestureAroundStart.setBackgroundColor(0xFF00FF00);
						//Toast.makeText(getActivity(), String.format("%s: %f", distribution.getBestMatch(), distribution.getBestDistance()), Toast.LENGTH_LONG).show();
						Toast.makeText(getActivity(), String.format("%s", distribution.getBestMatch()), Toast.LENGTH_LONG).show();
					}else{
						gestureDropdown.setBackgroundColor(0xFFFF3232);
						//gestureAroundStart.setBackgroundColor(0xFFFF0000);
						//Toast.makeText(getActivity(), String.format("%s: %f", distribution.getBestMatch(), distribution.getBestDistance()), Toast.LENGTH_LONG).show();
						Toast.makeText(getActivity(), String.format("%s", distribution.getBestMatch()), Toast.LENGTH_LONG).show();

					}
				}
			});
		}
	};
	
	public static FragmentOne newInstance() {
		FragmentOne fragment = new FragmentOne();
		return fragment;
	}
	
	/**
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activeTrainingSet = getActivity().getResources().getString(R.string.gesture_train_set);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_one, container, false);
		gestureStartStop = (Button)view.findViewById(R.id.gesture_start_stop);
		ButterKnife.inject(this, view);
		//final SeekBar tolerance=(SeekBar) view.findViewById(R.id.gesture_tolerance);     
		gestureTolerance.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			/**
			 * Make an activity when progress on seek bar was changed
			 * Save value tolerance, which presents tolerance threshold for gestures
			 */
			@Override

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				SharedPreferences settings = getActivity().getSharedPreferences("Gestures", 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putInt("Tolerance",progress);
				editor.commit();
				gestureStartStop.setText(String.valueOf(progress));
				
			}
		});
	    gestureStartStop.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                	//Toast.makeText(getActivity(), String.format("Akcia DOLE"), Toast.LENGTH_SHORT).show();
                	gesture_track = true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                	//Toast.makeText(getActivity(), String.format("Akcia HORE"), Toast.LENGTH_SHORT).show();
                	gesture_track = false;
                }
                return true;
            }
    });
		return view;
	}
	

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * Action when gesture train button was clicked
	 * Disable other buttons and settings on screen and start train mode for gestures
	 * @param v View of this activity fragment
	 */
	@OnClick(R.id.gesture_train)
	void gestureTrainClicked(View v){
		activeTrainingSet = getActivity().getResources().getString(R.string.gesture_train_set);
		if (recognitionService == null)
			Toast.makeText(getActivity(), String.format("recognitionService je NULL"), Toast.LENGTH_LONG).show();
		if (recognitionService != null) {
			
			try {
				if (!recognitionService.isLearning()) {
					gestureTrain.setText(getActivity().getResources().getString(R.string.gesture_train_stop));
					gestureDelete.setEnabled(false);
					gestureDropdown.setEnabled(false);
					//Zaciatok trenovania
					recognitionService.startLearnMode(activeTrainingSet, gestureDropdown.getSelectedItem().toString());
				} else {
					gestureTrain.setText(getActivity().getResources().getString(R.string.gesture_train));
					gestureDropdown.setEnabled(true);
					gestureDelete.setEnabled(true);
					recognitionService.stopLearnMode();
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * Action when gesture delete button is clicked
	 * Delete current gesture which is chosen in spinner
	 * @param v View of this activity window
	 */
	@OnClick(R.id.save_password)
	void gestureDeleteClicked(View v){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(getActivity().getResources().getString(R.string.msg_delete_gesture)+" "+gestureDropdown.getSelectedItem().toString()+"?").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				if (recognitionService != null) {
					try {
						recognitionService.deleteGesture(activeTrainingSet, gestureDropdown.getSelectedItem().toString());
						trainProgress = 0;
						gestureTrainProgress.getProgressDrawable().clearColorFilter();
						gestureTrainProgress.setProgress(trainProgress);
						//recognitionService.deleteTrainingSet(activeTrainingSet);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		builder.create().show();
	}
	/**
	 * Bind gesture recognition service when application resume from background
	 */
	@Override
	public void onResume() {
		activeTrainingSet = getActivity().getResources().getString(R.string.gesture_train_set);
		Intent bindIntent = new Intent("com.dfki.GestureFramework.GESTURE_RECOGNIZER");
		getActivity().bindService(bindIntent, serviceConnection, getActivity().BIND_AUTO_CREATE);
		trainProgress = 0;
		gestureTrainProgress.getProgressDrawable().clearColorFilter();
		gestureTrainProgress.setProgress(trainProgress);
		super.onResume();
	}
	/**
	 * Unregister gesture recognition service when application goes to background
	 */
	@Override
	public void onPause() {
		try {
			recognitionService.unregisterListener(IGestureRecognitionListener.Stub.asInterface(gestureListenerStub));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		recognitionService = null;
		getActivity().unbindService(serviceConnection);
		super.onPause();
	}
	
}
