package edu.iupui.ece.ddkdl.datacapturer;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by MURI on 11/29/2016.
 */

class SensorReader implements Runnable, SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor accSensor;
    private Sensor rotSensor;

    public SensorReader(Activity activity){
        mSensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        accSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        rotSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    private void turnOnSensors(){
        mSensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, rotSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void turnOffSensors(){
        mSensorManager.unregisterListener(this, accSensor);
        mSensorManager.unregisterListener(this, rotSensor);
    }

    @Override
    public void run(){

    }

    @Override
    public void onAccuracyChanged(Sensor s, int i){

    }


    @Override
    public void onSensorChanged(SensorEvent event){

    }
}
