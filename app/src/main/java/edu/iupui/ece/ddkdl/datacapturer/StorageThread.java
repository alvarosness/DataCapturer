package edu.iupui.ece.ddkdl.datacapturer;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
            // I made a change
            //
        }

    }

    public synchronized void store(){

    }

    public void setStoring(boolean value){
        storing = value;
    }

    public boolean isStoring(){
        return storing;
    }
}
