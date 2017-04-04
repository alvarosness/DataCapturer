package edu.iupui.ece.ddkdl.datacapturer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by Veronica on 11/8/2016.
 */

public class DatabaseOperations extends SQLiteOpenHelper {
    public static final int DBversion = 1;
    public String Create_Query = "Create Table " + Database.Table.TABLE_NAME + "("
            + Database.Table.X_ACC + " TEXT," + Database.Table.Y_ACC + " TEXT," + Database.Table.Z_ACC + " TEXT," +
            Database.Table.X_ROT + " TEXT," + Database.Table.Y_ROT + " TEXT," + Database.Table.Z_ROT + " TEXT," + Database.Table.TIME + " TEXT" + ")";

    public DatabaseOperations(Context context) {
        super(context, Database.Table.DATABASE_NAME, null, DBversion);
       //Log.d("Database Operations", Create_Query + " ");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Create_Query);
        //Log.d("Database Operations", "Table Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public DatabaseOperations getThis(){
        return this;
    }

    public void InsertData (double xacc, double yacc, double zacc, double xrot, double yrot, double zrot, String t){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Database.Table.X_ACC, xacc);
        cv.put(Database.Table.Y_ACC, yacc);
        cv.put(Database.Table.Z_ACC, zacc);
        cv.put(Database.Table.X_ROT, xrot);
        cv.put(Database.Table.Y_ROT, yrot);
        cv.put(Database.Table.Z_ROT, zrot);
        cv.put(Database.Table.TIME, t);
        long k = sq.insert(Database.Table.TABLE_NAME,null,cv);
      // Log.d("Database Operations", "One row inserted");

    }

    public Cursor RetrieveData(){

        SQLiteDatabase sq = getReadableDatabase();
        String column[] = {Database.Table.X_ACC, Database.Table.Y_ACC, Database.Table.Z_ACC, Database.Table.X_ROT, Database.Table.Y_ROT, Database.Table.Z_ROT, Database.Table.TIME};
        Cursor cr = sq.query(Database.Table.TABLE_NAME, column, null, null, null, null ,null);

        return cr;
    }



}
