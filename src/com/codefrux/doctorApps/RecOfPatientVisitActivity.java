package com.codefrux.doctorApps;


/*
 * This Activity Class maintain the time taken and visiting the patient 
 * 
 */


import com.codefrux.doctorApps.control.ControlHeader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RecOfPatientVisitActivity extends Activity {
	private TextView tvDate,tvTime,tvDoctorNane,tvPatientName,tvTimeOfVisit;
	private EditText etConHours,etConMin;
	private Button bLogout,bBack,bNext;
	
	private String Date,Doctor_Id,Patient_Id,Patient_Name,Visiting_Time;
	private ControlHeader backgroundThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recofpatientvisit);
		
		Bundle extras = getIntent().getExtras();
	    if (extras == null) {
	      return;
	    }
	    Date=extras.getString("Date");
	    Doctor_Id=extras.getString("Doctor_Id");
        Patient_Id=extras.getString("Patient_Id");
	    Patient_Name=extras.getString("Patient_Name");
	    Visiting_Time=extras.getString("Visiting_Time");
	    
	  
		
		tvDate=(TextView)findViewById(R.id.textView1headerfooterDate);
		tvTime=(TextView)findViewById(R.id.textView2headerfooterTime);
		tvDoctorNane=(TextView)findViewById(R.id.textView3headerfooterDoctorName);
		tvPatientName=(TextView)findViewById(R.id.textView4RecOfPatPatientName);
		tvTimeOfVisit=(TextView)findViewById(R.id.textView5RecOfPatVisitingTime);
		etConHours=(EditText)findViewById(R.id.editText1ReOfPatHours);
		etConMin=(EditText)findViewById(R.id.editText2RecOfPatMin);
		bLogout=(Button)findViewById(R.id.button1headerfooterLogout);
		bBack=(Button)findViewById(R.id.button2headerfooterBack);
		bNext=(Button)findViewById(R.id.button3headerfooterNext);
		
	
		tvPatientName.setText(Patient_Name);
		tvTimeOfVisit.setText(Date+" "+Visiting_Time);
		
		
		bNext.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String hours,mins,hourMins;
				hours=etConHours.getText().toString();
				mins=etConMin.getText().toString();
				
				if(hours.equals("")||mins.equals(""))
					Toast.makeText(getApplicationContext(), "Please fill Hours and Mins", Toast.LENGTH_SHORT).show();
				else if(Integer.parseInt(mins)>60)
					etConMin.setError("Mins Should be Less than 60");
				    else
				    {
				    	hourMins= String.format("%02d", Integer.parseInt(hours))+":"+String.format("%02d",Integer.parseInt ( mins));//hours+":"+mins;
				    	startActivity(new Intent(RecOfPatientVisitActivity.this,ReportActivity.class).putExtra("Doctor_Id", Doctor_Id).putExtra("Patient_Id", Patient_Id).putExtra("Patient_Name", Patient_Name).putExtra("Time_Taken",hourMins));
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
	   	 backgroundThread.setValue(tvDate, tvTime, tvDoctorNane, bLogout, Doctor_Id);
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
