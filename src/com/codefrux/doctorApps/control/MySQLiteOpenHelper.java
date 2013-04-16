package com.codefrux.doctorApps.control;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteOpenHelper extends SQLiteOpenHelper{
	 private static final String DATABASE_NAME = "database_doctor.db";
	  private static final int DATABASE_VERSION = 1;
	  
	  public static final String TABLE_DOCTOR="Doctor";
	  public static final String COLUMN_DOCTOR_ID="Id";
	  public static final String COLUMN_DOCTOR_NAME="Doctor_name";
	  public static final String COLUMN_DOCTOR_LOGIN_DATE="Login_date";
	  public static final String COLUMN_DOCTOR_LOGIN_TIME="Login_time";
	  public static final String COLUMN_DOCTOR_LOGOUT_DATE="Logout_date";
	  public static final String COLUMN_DOCTOR_LOGOUT_TIME="Logout_time";
	  public static final String COLUMN_DOCTOR_PASSWORD="PassWord";
	  
	  private static final String DATABASE_CREATE_DOCTOR="create table "+TABLE_DOCTOR+"("+COLUMN_DOCTOR_ID+" integer primary key,"+COLUMN_DOCTOR_NAME+" text not null,"+COLUMN_DOCTOR_LOGIN_DATE+" text not null,"+COLUMN_DOCTOR_LOGIN_TIME+" text not null,"+COLUMN_DOCTOR_LOGOUT_DATE+" text,"+COLUMN_DOCTOR_LOGOUT_TIME+" text,"+COLUMN_DOCTOR_PASSWORD+" text not null);";

	  public MySQLiteOpenHelper(Context context) {
			 super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE_DOCTOR);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(MySQLiteOpenHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "+ newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTOR);
	    onCreate(db);
		
	}
	

}
