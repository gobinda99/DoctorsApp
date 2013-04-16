package com.codefrux.doctorApps;

/**
 * This Activity is for displaying list of Patient 
 */


import java.util.ArrayList;
import org.json.JSONException;

import com.codefrux.doctorApps.control.ControlHeader;
import com.codefrux.doctorApps.control.CustomAdapter;
import com.codefrux.doctorApps.control.JSONcommon;
import com.codefrux.doctorApps.model.CustomObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class PatientListActivity extends Activity{
	private TextView tvDate,tvTime,tvDoctorName;
	private Button bLogout;
	private String JSON;
	private ArrayList<CustomObject> objects;
	
	
	private CustomObject obj;
	private ControlHeader backgroundThread;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patientlist);
        
        Bundle extras = getIntent().getExtras();
	    if (extras == null) {
	      return;
	    }
	    JSON=extras.getString("JSON");
        tvDate=(TextView)findViewById(R.id.textView1headerDate);
        tvTime=(TextView)findViewById(R.id.textView2headerTime);
        tvDoctorName=(TextView)findViewById(R.id.textView3headerDoctorName);
        bLogout=(Button)findViewById(R.id.button1headerLogout);
        final ListView lv = (ListView)findViewById( R.id.listView1 );  
        
		try {
			
			JSONcommon jsonCommon=new JSONcommon();
			objects = jsonCommon.parseResult(JSON);
			CustomAdapter customAdapter = new CustomAdapter(this, objects);
			 lv.setAdapter(customAdapter);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 obj=objects.get(0);
        }
    
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	backgroundThread = new ControlHeader(this);
	   	 backgroundThread.setRunning(true);
	   	 backgroundThread.setValue(tvDate, tvTime, tvDoctorName, bLogout,obj.getDoctor_Id() );
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
    
      
    	 @Override
    	public void onBackPressed() {
    		// TODO Auto-generated method stub
    		 
    	}    
    	       	     

}
