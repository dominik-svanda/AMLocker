/**
 * GestureRecorder.java
 *
 * Created: 18.08.2011
 *
 * Copyright (C) 2011 Robert Nesselrath
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.dfki.GestureFramework.recorder;

import java.util.ArrayList;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.svanda.amlocker.fragments.FragmentOne;
import com.svanda.amlocker.lockscreen.LockScreenAppActivity;


public class GestureRecorder implements SensorEventListener {
	
	public enum RecordMode {
		MOTION_DETECTION, PUSH_TO_GESTURE
	};

	final int MIN_GESTURE_SIZE = 3;
	float THRESHOLD = 4;
	SensorManager sensorManager;
	boolean isRecording;

	int stepsSinceNoMovement = 0;
	ArrayList<float[]> gestureValues;
	Context context;
	GestureRecorderListener listener;
	boolean isRunning;
	RecordMode recordMode = RecordMode.MOTION_DETECTION;

	public GestureRecorder(Context context) {
		this.context = context;
	}

	private float calcVectorNorm(float[] values) {
		float norm = (float) Math.sqrt(values[SensorManager.DATA_X] * values[SensorManager.DATA_X] + values[SensorManager.DATA_Y] * values[SensorManager.DATA_Y] + values[SensorManager.DATA_Z]
				* values[SensorManager.DATA_Z]) - 9.9f;
		return norm;
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	public RecordMode getRecordMode() {
		return recordMode;
	}

	public void setThreshold(float threshold) {
		THRESHOLD = threshold;
		System.err.println("New Threshold " + threshold);
	}

	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// do nothing
	}

	public void onPushToGesture(boolean pushed) {

		if (recordMode == RecordMode.PUSH_TO_GESTURE) {
			isRecording = pushed;
			if (isRecording) {
				gestureValues = new ArrayList<float[]>();
			} else {
				if (gestureValues.size() > MIN_GESTURE_SIZE) {
					listener.onGestureRecorded(gestureValues);
				}
				gestureValues = null;
			}
		}
	}
	/**
	 * Action when accelerometer sensor state changed
	 * track action when user starts create 3D gesture
	 */
	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {

		float[] value = { sensorEvent.values[SensorManager.DATA_X], sensorEvent.values[SensorManager.DATA_Y], sensorEvent.values[SensorManager.DATA_Z] };
		/*if(GestureTrainer.pressedTrainOK.isPressed()){
			GestureTrainer.mylayout.setBackgroundColor(0xFF0BB5FF);
		}else{
			GestureTrainer.mylayout.setBackgroundColor(0xFFFFFFFF);
		}*/
		switch (recordMode) {
		case MOTION_DETECTION:
			if (isRecording && (FragmentOne.gestureStartStop.isPressed() /*|| LockScreenAppActivity.UnlockGestureButton.isPressed()*/)) {
				gestureValues.add(value);
				if (calcVectorNorm(value) < THRESHOLD) {
					stepsSinceNoMovement++;
				} else {
					stepsSinceNoMovement = 0;
				}
			} else if (calcVectorNorm(value) >= THRESHOLD) {
				isRecording = true;
				stepsSinceNoMovement = 0;
				gestureValues = new ArrayList<float[]>();
				gestureValues.add(value);
			}
			if (((!FragmentOne.gestureStartStop.isPressed() /*|| !LockScreenAppActivity.UnlockGestureButton.isPressed()*/)) && stepsSinceNoMovement != 0) {

				//System.out.println("Length is: " + String.valueOf(gestureValues.size() - 10));
				if (gestureValues.size() - 10 > MIN_GESTURE_SIZE) {
					listener.onGestureRecorded(gestureValues.subList(0, gestureValues.size() - 10));
				}
				gestureValues = null;
				stepsSinceNoMovement = 0;
				isRecording = false;
			}
			break;
		case PUSH_TO_GESTURE:
			if(FragmentOne.gestureStartStop.isPressed() /*|| LockScreenAppActivity.UnlockGestureButton.isPressed()*/){
				if (isRecording) {
					gestureValues.add(value);
				}
			}else{
				if (gestureValues.size() - 10 > MIN_GESTURE_SIZE) {
					listener.onGestureRecorded(gestureValues.subList(0, gestureValues.size() - 10));
				}
				gestureValues = null;
				stepsSinceNoMovement = 0;
				isRecording = false;
			}
			
			
			break;
		}

	}

	public void registerListener(GestureRecorderListener listener) {
		this.listener = listener;
		start();
	}

	public void setRecordMode(RecordMode recordMode) {
		this.recordMode = recordMode;
	}

	public void start() {
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
		isRunning = true;
	}

	public void stop() {
		sensorManager.unregisterListener(this);
		isRunning = false;
	}

	public void unregisterListener(GestureRecorderListener listener) {
		this.listener = null;
		stop();
	}

	public void pause(boolean b) {
		if (b) {
			sensorManager.unregisterListener(this);
		} else {
			sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
		}
	}

}