package edu.iupui.ece.ddkdl.datacapturer;

/**
 * Created by alvaro on 2/13/17.
 */

public interface AudioDataReceivedListener {
    void onAudioDataReceived(short[] data);
}
