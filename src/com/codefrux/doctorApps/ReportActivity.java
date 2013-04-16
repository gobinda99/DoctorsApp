package com.codefrux.doctorApps;


import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.codefrux.doctorApps.control.ControlHeader;
import com.codefrux.doctorApps.control.LoginLogoutDataSource;
import com.codefrux.doctorApps.control.URLConnection;
import com.codefrux.doctorApps.model.LoginLogoutModel;
import com.codefrux.doctorApps.model.Parameter;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReportActivity  extends Activity {
	private TextView tvDate,tvTime,tvDoctorName,tvPatientName;
	private EditText etBPMax,etBPMin,etPulse,etTemperature,etBloodGroup,etRemark;
	private Button bLogout,bBack,bSummit;
	
	private String Doctor_Id,Patient_Id,Patient_Name,Time_Taken;
	private ControlHeader backgroundThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report);
		
		Bundle extras = getIntent().getExtras();
	    if (extras == null) {
	      return;
	    }
	    
	    Doctor_Id=extras.getString("Doctor_Id");
	    
	    Patient_Id=extras.getString("Patient_Id");
	    Patient_Name=extras.getString("Patient_Name");
	    Time_Taken=extras.getString("Time_Taken");
	    
	    tvDate=(TextView)findViewById(R.id.textView1headerfooter1Date);
	    tvTime=(TextView)findViewById(R.id.textView2headerfooter1Time);
	    tvDoctorName=(TextView)findViewById(R.id.textView3headerfooter1DoctorName);
	    tvPatientName=(TextView)findViewById(R.id.textView6ReportPatName);
	    etBPMax=(EditText)findViewById(R.id.editText1ReportBp);
	    etBPMin=(EditText)findViewById(R.id.editText1ReportBpMin);
	    etPulse=(EditText)findViewById(R.id.editText2ReportPulse);
	    etTemperature=(EditText)findViewById(R.id.editText3ReportTemperature);
	    etBloodGroup=(EditText)findViewById(R.id.editText6ReportBloodGroup);
	    etRemark=(EditText)findViewById(R.id.editText5ReportRemark);
	    bLogout=(Button)findViewById(R.id.button1headerfooter1Logout);
	    bBack=(Button)findViewById(R.id.button2headerfooter1Back);
	    bSummit=(Button)findViewById(R.id.button3headerfooter1Summit);
	    
	   
	    
	    tvPatientName.setText(Patient_Name);
	    
	    bSummit.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String Blood_PressureMax=etBPMax.getText().toString();
				String Blood_PressureMin=etBPMin.getText().toString();
				String Pulse_Rate=etPulse.getText().toString();
				String Temperature=etTemperature.getText().toString();
				String Blood_Group=etBloodGroup.getText().toString();
				String Remark=etRemark.getText().toString();
				if(Blood_PressureMax.equals("")||Blood_PressureMin.equals("")||Pulse_Rate.equals("")||Temperature.equals("")||Blood_Group.equals("")||Remark.equals(""))
					Toast.makeText(getApplicationContext(), "Please Fill all the Field", Toast.LENGTH_SHORT).show();
				else
				{
			        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			        postParameters.add(new BasicNameValuePair(Parameter.PATIENT_ID, Patient_Id));
			        postParameters.add(new BasicNameValuePair(Parameter.BLOOD_PRESSURE, Blood_PressureMax+"/"+Blood_PressureMin));
			        postParameters.add(new BasicNameValuePair(Parameter.PULSE_RATE, Pulse_Rate));
			        postParameters.add(new BasicNameValuePair(Parameter.TEMPRATURE, Temperature));
			        postParameters.add(new BasicNameValuePair(Parameter.BLOOD_GROUP, Blood_Group));
			        postParameters.add(new BasicNameValuePair(Parameter.REMARK, Remark));
			        postParameters.add(new BasicNameValuePair(Parameter.TIME_TAKEN, Time_Taken));
			        URLConnection con=null;
                    String stringBuffer=null;
                    con=new URLConnection(ReportActivity.this);
                    stringBuffer=con.postURL(URLConnection.INSERT_DETAILS, postParameters);
                    Toast.makeText(getApplicationContext(), stringBuffer,4000).show();
                    if(stringBuffer!=null)
                   if(stringBuffer.toString().equals("UPDATED\n"))
                   { 
                	   
                 	   LoginLogoutDataSource loginLogoutDataSource=null;
            	       LoginLogoutModel loginLogoutModel=null;
            	       
            	       
            	       try{
                			loginLogoutDataSource=new LoginLogoutDataSource(ReportActivity.this);
                			loginLogoutDataSource.open(); 
                			loginLogoutModel =new LoginLogoutModel();
                		loginLogoutModel=loginLogoutDataSource.search(Doctor_Id);
                			
                		}catch (SQLException e) {
                			// TODO: handle exception
                			e.printStackTrace();
                		}
                		finally{
                			if(loginLogoutDataSource!=null)
                				loginLogoutDataSource.close();
                		}
            	        postParameters = new ArrayList<NameValuePair>();
   			            postParameters.add(new BasicNameValuePair(Parameter.DOCTOR_NAME, loginLogoutModel.getDoctor_name()));
                       postParameters.add(new BasicNameValuePair(Parameter.PASSWORD, loginLogoutModel.getPassWord()));
                       
                        con=null;
                        stringBuffer="";
                       con=new URLConnection(ReportActivity.this);
                       stringBuffer=con.postURL(URLConnection.LOGIN, postParameters);
                       Log.d(stringBuffer, "");
                       if(stringBuffer!=null)
                       if(!stringBuffer.toString().equals("[]"+System.getProperty("LineSeparator")) && !stringBuffer.equals("No Internet"))
 			    	   startActivity(new Intent(ReportActivity.this,PatientListActivity.class).putExtra("JSON",stringBuffer));
                   }
				}	
			}
		});
	    
	    
	    bBack.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				onBackPressed();
			}
		});
	    
	    
	    
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		backgroundThread = new ControlHeader(this);
	   	 backgroundThread.setRunning(true);
	   	 backgroundThread.setValue(tvDate, tvTime, tvDoctorName, bLogout, Doctor_Id);
	   	       backgroundThread.start();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		 boolean retry = true;
    	 backgroundThread.setRunning(false);
          while(retry){
    	  try {
    	   backgroundThread.join();
    	   retry = false;
    	  } catch (InterruptedException e) {
    	   // TODO Auto-generated catch block
    	   e.printStackTrace();
    	  }
    	 }
	}
	


}
