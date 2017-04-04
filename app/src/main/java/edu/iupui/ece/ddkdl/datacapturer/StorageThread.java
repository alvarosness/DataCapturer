package edu.iupui.ece.ddkdl.datacapturer;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.currentTimeMillis;

/**
 * Created by Software Development on 4/3/2017.
 */

public class StorageThread implements Runnable {
    private boolean storing;
    private double[] buffer;
    private DatabaseOperations database;

    StorageThread(double[] list , Context context){
        storing = false;
        buffer = list;
        database = new DatabaseOperations(context);
    }

    @Override
    public void run() {
        while(isStoring()){
            //sleep for a couple of milliseconds
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //read store what's in the buffer in the database
            store();
        }

    }

    public synchronized void store(){
        long timetag = System.currentTimeMillis();
        database.InsertData(buffer[0], buffer[1], buffer[2], buffer[3], buffer[4], buffer[5], timetag+"");

    }

    public void setStoring(boolean value){
        storing = value;
    }

    public boolean isStoring(){
        return storing;
    }
}
