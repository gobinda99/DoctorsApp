package com.codefrux.doctorApps;

/**
  Activity for gorget password
 **/




import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codefrux.doctorApps.control.JSONcommon;
import com.codefrux.doctorApps.control.LoginLogoutDataSource;
import com.codefrux.doctorApps.control.URLConnection;
import com.codefrux.doctorApps.model.CustomObject;
import com.codefrux.doctorApps.model.Parameter;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class ForgetPasswordActivity extends Activity {
	private EditText etMobileNo;
	private TextView tvUserName,tvPassword;
	private Button bSummit,bClickToLogin;
	
	private URLConnection con=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgotpwd);
		
		etMobileNo=(EditText)findViewById(R.id.editText1ForgetPwdPhoneNo);
		tvUserName=(TextView)findViewById(R.id.TextView5ForgetPwdUsername);
		tvPassword=(TextView)findViewById(R.id.TextView4ForgetPwdPassword);
		bSummit=(Button)findViewById(R.id.button1ForgetPwdSumit);
		bClickToLogin=(Button)findViewById(R.id.button2ForgetPwdLogin);
		bClickToLogin.setVisibility(View.INVISIBLE);
		
		bSummit.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String MobileNo=etMobileNo.getText().toString();
				if(MobileNo.equals(""))
					etMobileNo.setError("Mobile No is Blank");
				else{
					con=new URLConnection(ForgetPasswordActivity.this);
					 List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			        postParameters.add(new BasicNameValuePair(Parameter.MOBILE_NUMBER, MobileNo));
			        String Json=con.postURL(URLConnection.FORGOT_PASSWORD, postParameters);
			        

			        if(Json!=null)
			        if(!Json.equals("[]"+System.getProperty("line.separator")) && !Json.equals("No Internet"))
			        {
			        	try {
							JSONArray jsonArray_results = new JSONArray(Json);
							JSONObject jsonObject_i = jsonArray_results.getJSONObject(0);
							tvUserName.setText(jsonObject_i.getString(Parameter.DOCTOR_NAME));
							tvPassword.setText(jsonObject_i.getString(Parameter.PASSWORD));
							if(tvUserName.getText()!=null)
								bClickToLogin.setVisibility(View.VISIBLE);
							      
								
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }else if(Json.equals("[]"+System.getProperty("line.separator")))
			        	Toast.makeText(getApplicationContext(), "Mobile Number is Unknown ", Toast.LENGTH_SHORT).show();
			        
			        	
				}
					
			}
		});
		
		bClickToLogin.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String UserName=tvUserName.getText().toString();
				String Password=tvPassword.getText().toString();
				if(UserName.equals("")||Password.equals(""))
					etMobileNo.setError("Please fill correct No.");
				else{
                    con=new URLConnection(ForgetPasswordActivity.this);
					List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
     		        postParameters.add(new BasicNameValuePair(Parameter.DOCTOR_NAME, UserName));
			        postParameters.add(new BasicNameValuePair(Parameter.PASSWORD, Password));
			        String JSON=con.postURL(URLConnection.LOGIN, postParameters);
			        if(JSON!=null)
			        if(!JSON.equals("[]"+System.getProperty("line.separator")) && !JSON.equals("No Internet"))
			        {
			        	ArrayList<CustomObject> objects;
			        	LoginLogoutDataSource loginLogoutDataSource=null;
			        	CustomObject obj=new CustomObject();
			        	JSONcommon jsonCom;
			        	 try{
	                   	    	jsonCom  =new JSONcommon(); 
	                   	    	objects = jsonCom.parseResult(JSON);
	                    	obj=objects.get(0);
	                   	    }catch (JSONException e) {
								// TODO: handle exception
	                   	    	e.printStackTrace();
							}
	             		try{
	             			loginLogoutDataSource=new LoginLogoutDataSource(ForgetPasswordActivity.this);
	             			loginLogoutDataSource.open(); 
	             			Date d=new Date();
	             			String loginTime=String.format("%02d",d.getHours())+":"+String.format("%02d",d.getMinutes())+":"+String.format("%02d",d.getSeconds());
	             			loginLogoutDataSource.login(obj.getDoctor_Id(),UserName ,new SimpleDateFormat("yyyy-MM-dd").format(d) ,loginTime,Password);
	             			
	             		}catch (SQLException e) {
	             			// TODO: handle exception
	             			e.printStackTrace();
	             		}
	             		finally{
	             			if(loginLogoutDataSource!=null)
	             				loginLogoutDataSource.close();
	             		}
			        	startActivity(new Intent(ForgetPasswordActivity.this,PatientListActivity.class).putExtra("JSON",JSON ));
			        }
				}
			}
		});
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		startActivity(new Intent(ForgetPasswordActivity.this,MainActivity.class));
		super.onBackPressed();
	}

}
