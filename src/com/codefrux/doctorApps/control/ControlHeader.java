package com.codefrux.doctorApps.control;

/*
 * This Class maintain common header to all the layout
 */


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.codefrux.doctorApps.MainActivity;
import com.codefrux.doctorApps.R;
import com.codefrux.doctorApps.model.LoginLogoutModel;
import com.codefrux.doctorApps.model.Parameter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.SQLException;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ControlHeader extends Thread {
	private Context context;
    private boolean running = false; 
    private TextView tvTime;
    private Button bLogout;
    private String Id;
    private LoginLogoutDataSource loginLogoutDataSource=null;
    private LoginLogoutModel loginLogoutModel;
   

   public ControlHeader(Context context){
   	this.context=context;
   	
   }
  public void setValue(TextView tvDate ,TextView tvTime,TextView tvDoctorName,Button bLogout,String Doctor_Id ){
   	tvDate.setText(DateFormat.getDateInstance(2).format(new Date()));
   	this.tvTime=tvTime;
   	this.bLogout=bLogout;
   	this.Id=Doctor_Id;
   	try{
   		loginLogoutDataSource=new LoginLogoutDataSource(context);
	    loginLogoutDataSource.open();
	    loginLogoutModel =new LoginLogoutModel();
	    loginLogoutModel=loginLogoutDataSource.search(Doctor_Id);
	    tvDoctorName.setText("Dr. "+loginLogoutModel.getDoctor_name());
   	}catch (SQLException e) {
		// TODO: handle exception
   		e.printStackTrace();
	}
   	finally{
   		if(loginLogoutDataSource!=null)
   			loginLogoutDataSource.close();
   	}
   	
   	logout();
   	
   }
   	
   	
    @Override
	 public void run() {
	  // TODO Auto-generated method stub
	  //super.run();
	  while(running){
	   try {
	    sleep(1000);
	   } catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	   }
	     handler.sendMessage(handler.obtainMessage());
	  }
	 }
    
    public  void setRunning(boolean b){
 	  running = b;
 	 }
   
    
    
    private	Handler handler = new Handler(){

   	 @Override
   	 public void handleMessage(Message msg) {
   	 
   		 Date d=new Date();
   		tvTime.setText(String.format("%02d",d.getHours())+":"+String.format("%02d",d.getMinutes())+":"+String.format("%02d",d.getSeconds()));
   		
   		  }	 
   	};
   	
   	
   	
   	private void logout(){
   		final AlertDialog.Builder ad=new AlertDialog.Builder(context);
		 ad.setTitle("Logout");
	        ad.setIcon(R.drawable.logout);
	        
	        
	          ad.setPositiveButton("Yes",new OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					try{

					
						loginLogoutDataSource=new LoginLogoutDataSource(context);
					    loginLogoutDataSource.open();
					    loginLogoutModel =new LoginLogoutModel();
					    Date d=new Date();
					    String logoutTime=String.format("%02d",d.getHours())+":"+String.format("%02d",d.getMinutes())+":"+String.format("%02d",d.getSeconds());
			            loginLogoutModel=loginLogoutDataSource.logout(Id,new SimpleDateFormat("yyyy-MM-dd").format(d) , logoutTime);
					    if(loginLogoutModel!=null)
					    {  String In_Time=loginLogoutModel.getLogin_date()+" "+loginLogoutModel.getLogin_time();
					       String Out_Time=loginLogoutModel.getLogout_date()+" "+loginLogoutModel.getLogout_time();
					       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					        sdf.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
					        Date loginDate,logoutDate;
					            loginDate=new Date();
					            loginDate=sdf.parse(In_Time);
					            logoutDate=new Date();
					            logoutDate=sdf.parse(Out_Time);
					            Long TotalTimeInMS=logoutDate.getTime()-loginDate.getTime();
					            sdf=new SimpleDateFormat("HH:mm:ss");
					            String Total_Time=sdf.format(new Date(TotalTimeInMS+66600000));				            

					        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					        postParameters.add(new BasicNameValuePair(Parameter.DOCTOR_ID,Id ));
					        postParameters.add(new BasicNameValuePair(Parameter.IN_TIME, In_Time));
					        postParameters.add(new BasicNameValuePair(Parameter.OUT_TIME, Out_Time));
					        postParameters.add(new BasicNameValuePair(Parameter.TOTAL_TIME, Total_Time));
					        URLConnection con=null;
		                    String stringBuffer=null;
		                    con=new URLConnection(context);
		                    stringBuffer=con.postURL(URLConnection.LOGOUT, postParameters);
		                    if(stringBuffer!=null)
		                    if(stringBuffer.equals("INSERTED\n") && !stringBuffer.equals("No Internet")){
		                    	Toast.makeText(context, "Successfully Logout\n Total Duration "+Total_Time, Toast.LENGTH_SHORT).show();
					    	   context.startActivity(new Intent(context,MainActivity.class));
					          }else
					        	  Toast.makeText(context, "Logout data Not Uploaded", Toast.LENGTH_SHORT).show();

		                    
						}
					   
					    
					}catch (SQLException e) {
						// TODO: handle exception
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finally{
						if(loginLogoutDataSource!=null)
							loginLogoutDataSource.close();	
					}
					
				}
	    	});
	        
	        ad.setNegativeButton("No",new OnClickListener() {

				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
	    	});
	        
	        ad.setNeutralButton("Cancel",new OnClickListener() {

				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
	    	});
		bLogout.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
					loginLogoutDataSource=new LoginLogoutDataSource(context);
				    loginLogoutDataSource.open();
				    loginLogoutModel =new LoginLogoutModel();
				    loginLogoutModel=loginLogoutDataSource.search(Id);
				    ad.setMessage(loginLogoutModel.getDoctor_name()+"\n"+"  Login Date "+loginLogoutModel.getLogin_date()+"\n\t\t    Time "+loginLogoutModel.getLogin_time());
				    
				}catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				finally{
					if(loginLogoutDataSource!=null)
						loginLogoutDataSource.close();	
				}
				ad.show();
			}
		});
		
   }
   	
   
   	
   
   	
   	
   	}
   	
   	

