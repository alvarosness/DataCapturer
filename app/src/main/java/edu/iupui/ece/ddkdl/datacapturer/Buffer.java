package edu.iupui.ece.ddkdl.datacapturer;

/**
 * Created by alvaro on 2/15/17.
 */

public interface Buffer {
    void set(short[] buffer) throws InterruptedException;

    short[] get() throws InterruptedException;
}
