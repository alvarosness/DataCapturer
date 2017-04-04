package edu.iupui.ece.ddkdl.datacapturer;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;


public class MainActivity extends AppCompatActivity implements SensorEventListener{

    //Audio recognition variables
    private RecordingThread mRecordingThread;
    private static final int REQUEST_RECORD_AUDIO = 13;
    private final short THRESHOLD = 400;

    //Motion data variables
    private SensorManager mSensorManager;
    private Sensor accSensor;
    private Sensor rotSensor;
    private Data data;
    private DatabaseOperations database;
    private Context ctx = this;
    private boolean first = true;
    private long start = 0;
    private long end = 0;
    private int seconds = 30;
    private TextView status;
    private Button restart;
    public Bundle tempBundle;
    private static int SAMPLING_PERIOD = 10;
    long lastSaved = System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restart = (Button)findViewById(R.id.restartbutton);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                status.setText("Restarting");
                //init();
            }
        });
        init();
    }

    private void init(){

        data = new Data();
        database = new DatabaseOperations(ctx);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        rotSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        status = (TextView) findViewById(R.id.status_text);
        ctx.deleteDatabase("muri_research");


        mRecordingThread = new RecordingThread(new AudioDataReceivedListener() {
            @Override
            public void onAudioDataReceived(short[] data) {
                if(isClick(data)){
                    mRecordingThread.stopRecording();
                    Log.i("REC TAG", "Click or some high amplitude sound was heard.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            status.setText("Recording");
                        }
                    });
                    //status.setText("Recording");
                    turnOnSensors();
                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        startAudioRecordingSafe();

    }

    @Override
    protected void onStop() {
        super.onStop();

        mRecordingThread.stopRecording();
    }


    private void startAudioRecordingSafe() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            mRecordingThread.startRecording();
        } else {
            requestMicrophonePermission();
        }
    }

    private void requestMicrophonePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.RECORD_AUDIO)) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    android.Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    android.Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_RECORD_AUDIO && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mRecordingThread.stopRecording();
        }
    }

    private boolean isClick(short[] soundSamples){
        for(int i = 0; i < soundSamples.length; i++){
            if(soundSamples[i] > THRESHOLD)
                return true;
        }
        return false;
    }

    private void turnOnSensors(){
        mSensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, rotSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void turnOffSensors(){
        mSensorManager.unregisterListener(this, accSensor);
        mSensorManager.unregisterListener(this, rotSensor);
    }

    @Override
    public void onAccuracyChanged(Sensor s, int i) {
        //Nothing for now
        //I just made a change
    }

    //
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (first) {
            start = System.currentTimeMillis();
            end = start + seconds * 1000;
            first = false;
        }

        if ((System.currentTimeMillis() - lastSaved) >= SAMPLING_PERIOD) {
            lastSaved = System.currentTimeMillis();

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                data.setxAcc(event.values[0]);
                data.setyAcc(event.values[1]);
                data.setzAcc(event.values[2]);
            }
            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                data.setxRot(event.values[0]);
                data.setyRot(event.values[1]);
                data.setzRot(event.values[2]);
            }

            long current_t = System.currentTimeMillis();
            double time_elapsed = (current_t - start) / 1000.0;

            database.InsertData(data.getxAcc(), data.getyAcc(), data.getzAcc(),
                    data.getxRot(), data.getyRot(), data.getzRot(), time_elapsed + " ");

            if (System.currentTimeMillis() >= end) {
                turnOffSensors();
                status.setText("Finished Recording");
                exportDB();

            }
        }

    }

    private void exportDB() {
        File myFile = new File(ctx.getExternalFilesDir(null), "motion_data.csv");

        Cursor cr = database.RetrieveData();
        cr.moveToFirst();

        try {
            myFile.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(myFile));
            SQLiteDatabase db = database.getThis().getWritableDatabase();

            Cursor curCSV = db.rawQuery("SELECT * FROM data_capture", null);
            csvWrite.writeNext(curCSV.getColumnNames());
            Log.i("Column Names", cr.getColumnNames().toString());
            while (curCSV.moveToNext()) {
                //Which column you want to exprort
                String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2),
                        curCSV.getString(3), curCSV.getString(4), curCSV.getString(5), curCSV.getString(6)};
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
        } catch (Exception sqlEx) {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }
    }
}
