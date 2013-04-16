package com.codefrux.doctorApps;

/*
 *This Activity Class is for Patient Information
 * It Display all the information the patient
 */
 
 



import com.codefrux.doctorApps.control.ControlHeader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PatientInformationActivity extends Activity {
	private TextView tvDate,tvTime,tvDoctorName,txPatientId,txPatientName,tvGender,txAdress,txPhoneNumber,txSymtoms;
	private Button bLogout,bBack,bNext;
	
	private String Date,Doctor_Id,Patient_Id,Patient_Name,Visiting_Time;
	private ControlHeader backgroundThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patientinformation);
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
		tvDoctorName=(TextView)findViewById(R.id.textView3headerfooterDoctorName);
		tvTime=(TextView)findViewById(R.id.textView2headerfooterTime);
		txPatientId=(TextView)findViewById(R.id.textView6PatientInfoPatientId);
		txPatientName=(TextView)findViewById(R.id.textView9PatientInfoPatientName);
		tvGender=(TextView)findViewById(R.id.textView11PatientInfoGender);
		txAdress=(TextView)findViewById(R.id.textView10PatientInfoAdress);
		txPhoneNumber=(TextView)findViewById(R.id.textView12PatientInfoPhoneNo);
		txSymtoms=(TextView)findViewById(R.id.textView13PatientInfoSymtoms);
		bLogout=(Button)findViewById(R.id.button1headerfooterLogout);
		bBack=(Button)findViewById(R.id.button2headerfooterBack);
		bNext=(Button)findViewById(R.id.button3headerfooterNext);
		

		
		
	
	
		txPatientId.setText(extras.getString("Patient_Id"));
		txPatientName.setText(extras.getString("Patient_Name"));
		tvGender.setText(extras.getString("Patient_Sex"));
		txAdress.setText(extras.getString("Patient_Address"));
		txPhoneNumber.setText(extras.getString("Patient_Mobile"));
		txSymtoms.setText(extras.getString("Overview"));
		
		bBack.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			onBackPressed();	
			}
		});
		
		bNext.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(PatientInformationActivity.this,RecOfPatientVisitActivity.class).putExtra("Date",Date ).putExtra("Doctor_Id", Doctor_Id).putExtra("Patient_Id", Patient_Id).putExtra("Patient_Name", Patient_Name).putExtra("Visiting_Time", Visiting_Time));
			}
		});
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		backgroundThread = new ControlHeader(this);
	   	 backgroundThread.setRunning(true);
	   	 backgroundThread.setValue(tvDate, tvTime, tvDoctorName, bLogout,Doctor_Id );
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
