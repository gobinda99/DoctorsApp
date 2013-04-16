package com.codefrux.doctorApps.control;

/*
 * This Class perform all the Operation of 
 * the SQLite database of LoginLogout Model 
 */


import com.codefrux.doctorApps.model.LoginLogoutModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class LoginLogoutDataSource {
	private SQLiteDatabase database;
	  private MySQLiteOpenHelper dbHelper;
	  
	  public LoginLogoutDataSource(Context context) {
		    dbHelper = new MySQLiteOpenHelper(context);
		  }
	 
	  public void open() throws SQLException {
		    database = dbHelper.getWritableDatabase();
		  }
	 
	  public void close() {
		    dbHelper.close();
		  }
	  private long insert(String Id,String Doctor_name,String Login_date,String Login_time,String PassWord){
		  ContentValues  values=new ContentValues();
		  values.put(MySQLiteOpenHelper.COLUMN_DOCTOR_ID,Id);
		  values.put(MySQLiteOpenHelper.COLUMN_DOCTOR_NAME, Doctor_name);
		  values.put(MySQLiteOpenHelper.COLUMN_DOCTOR_LOGIN_DATE,Login_date);
		  values.put(MySQLiteOpenHelper.COLUMN_DOCTOR_LOGIN_TIME, Login_time);
		  values.put(MySQLiteOpenHelper.COLUMN_DOCTOR_LOGOUT_DATE, "");
		  values.put(MySQLiteOpenHelper.COLUMN_DOCTOR_LOGOUT_TIME, "");
		  values.put(MySQLiteOpenHelper.COLUMN_DOCTOR_PASSWORD, PassWord);
		  
		  long insertId=database.insert(MySQLiteOpenHelper.TABLE_DOCTOR, null, values);
		  Log.d(insertId+"", Id);
		  	return insertId;
	  }
	  private int update(String Id,String Logout_date,String Logout_time){
		  ContentValues values=new ContentValues();
		  values.put(MySQLiteOpenHelper.COLUMN_DOCTOR_LOGOUT_DATE, Logout_date);
		  values.put(MySQLiteOpenHelper.COLUMN_DOCTOR_LOGOUT_TIME, Logout_time);
		  int updateId=database.update(MySQLiteOpenHelper.TABLE_DOCTOR, values,MySQLiteOpenHelper.COLUMN_DOCTOR_ID+" = ? ", new String[]{Id});
		  Log.d(updateId+"", Id);
		  	return updateId;
	  }
	  private int delete(String Id){
		  int deleteId=database.delete(MySQLiteOpenHelper.TABLE_DOCTOR, MySQLiteOpenHelper.COLUMN_DOCTOR_ID+" = ? ", new String[]{Id});
		  Log.d(deleteId+"", Id);
		  	return deleteId;
	  }
	  public LoginLogoutModel search(String Id){
		  LoginLogoutModel newLoginLogoutModel=null;
		  Cursor cursor = database.query(MySQLiteOpenHelper.TABLE_DOCTOR, new String[]{MySQLiteOpenHelper.COLUMN_DOCTOR_ID,MySQLiteOpenHelper.COLUMN_DOCTOR_NAME,MySQLiteOpenHelper.COLUMN_DOCTOR_LOGIN_DATE,MySQLiteOpenHelper.COLUMN_DOCTOR_LOGIN_TIME,MySQLiteOpenHelper.COLUMN_DOCTOR_LOGOUT_DATE,MySQLiteOpenHelper.COLUMN_DOCTOR_LOGOUT_TIME,MySQLiteOpenHelper.COLUMN_DOCTOR_PASSWORD},MySQLiteOpenHelper.COLUMN_DOCTOR_ID+" = ? ", new String[]{Id} , null, null, null);
			  Log.d("Cursor", cursor.getCount()+"");
		  if(cursor!=null && cursor.getCount()>0){
				   if(cursor.moveToFirst())
			          newLoginLogoutModel = cursorToLoginLogout(cursor);
			    cursor.close();
			   }
			    return newLoginLogoutModel;  
	  }
	  
	  private LoginLogoutModel cursorToLoginLogout(Cursor cursor){
		  LoginLogoutModel loginLogout=new LoginLogoutModel();
		  loginLogout.setId(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_DOCTOR_ID)));
		  loginLogout.setDoctor_name(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_DOCTOR_NAME)));
		  loginLogout.setLogin_date(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_DOCTOR_LOGIN_DATE)));
		  loginLogout.setLogin_time(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_DOCTOR_LOGIN_TIME)));
		  loginLogout.setLogout_date(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_DOCTOR_LOGOUT_DATE)));
		  loginLogout.setLogout_time(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_DOCTOR_LOGOUT_TIME)));
		  loginLogout.setPassWord(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_DOCTOR_PASSWORD)));
		  return loginLogout;
	  }
	  public long login(String Id,String Doctor_name,String Login_date,String Login_time,String PassWord){
		  delete(Id);
		  return insert(Id, Doctor_name, Login_date, Login_time,PassWord);
	  }
	  public LoginLogoutModel logout(String Id,String Logout_date,String Logout_time){
		  update(Id, Logout_date, Logout_time);
		  return search(Id);
	  }

}
