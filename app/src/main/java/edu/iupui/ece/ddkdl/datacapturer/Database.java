package edu.iupui.ece.ddkdl.datacapturer;

import android.provider.BaseColumns;

/**
 * Created by Veronica on 11/8/2016.
 */

public class Database {

    public Database(){

    }


    public static abstract class Table implements BaseColumns {

        public static final String DATABASE_NAME = "muri_research";
        public static final String TABLE_NAME = "data_capture";
        public static final String X_ACC = "x_acc_col";
        public static final String Y_ACC = "y_acc_col";
        public static final String Z_ACC = "Z_acc_col";
        public static final String X_ROT = "x_rot_col";
        public static final String Y_ROT = "y_rot_col";
        public static final String Z_ROT = "Z_rot_col";
        public static final String TIME = "Time_col";
    }
}
