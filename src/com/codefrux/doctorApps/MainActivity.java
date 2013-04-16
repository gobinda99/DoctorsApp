package com.codefrux.doctorApps;


/**
  This Class Takes Doctor Name and Password 
  and pass to the server and gets response as JSON Object
 **/
 

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import com.codefrux.doctorApps.control.JSONcommon;
import com.codefrux.doctorApps.control.LoginLogoutDataSource;
import com.codefrux.doctorApps.control.URLConnection;
import com.codefrux.doctorApps.model.CustomObject;
import com.codefrux.doctorApps.model.Parameter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;



import android.util.Log;
import android.view.View;

import android.widget.Button;

import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TextView tvForgetPassword;
	private EditText etUsername,etPassword;
	private Button bLogin;
	
	private ArrayList<CustomObject> objects;
	private LoginLogoutDataSource loginLogoutDataSource=null;
	private CustomObject obj;
	private JSONcommon jsonCom;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		tvForgetPassword=(TextView)findViewById(R.id.textView3LoginForgetPassword);
		etUsername=(EditText)findViewById(R.id.editText1LoginUsername);
		etPassword=(EditText)findViewById(R.id.editText2Loginpass);
		bLogin=(Button)findViewById(R.id.button1Login);
		
		bLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				 //TODO Auto-generated method stub
				String UserName=etUsername.getText().toString();
				String pass  = etPassword.getText().toString();
				
				if(UserName.equals("")||pass.equals(""))
					Toast.makeText(getApplicationContext(), "Please Fill all the Field",Toast.LENGTH_SHORT).show();
				else
				{  

			        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			        postParameters.add(new BasicNameValuePair(Parameter.DOCTOR_NAME, UserName));
                    postParameters.add(new BasicNameValuePair(Parameter.PASSWORD, pass));
                    
                    URLConnection con=null;
                    String stringBuffer="";
                    con=new URLConnection(MainActivity.this);
                    stringBuffer=con.postURL(URLConnection.LOGIN, postParameters);
                    Log.d(stringBuffer, "");
                    if(stringBuffer!=null)
                    if(!stringBuffer.equals("[]\n") && !stringBuffer.equals("No Internet")){
                   	    try{
                   	    	jsonCom  =new JSONcommon(); 
                   	    	objects = jsonCom.parseResult(stringBuffer);
                    	obj=objects.get(0);
                   	    }catch (JSONException e) {
							// TODO: handle exception
                   	    	e.printStackTrace();
						}catch(IndexOutOfBoundsException e){
							e.printStackTrace();
						}
             		try{
             			loginLogoutDataSource=new LoginLogoutDataSource(MainActivity.this);
             			loginLogoutDataSource.open(); 
             			Date d=new Date();
             			String loginTime=String.format("%02d",d.getHours())+":"+String.format("%02d",d.getMinutes())+":"+String.format("%02d",d.getSeconds());
             			loginLogoutDataSource.login(obj.getDoctor_Id(),UserName ,new SimpleDateFormat("yyyy-MM-dd").format(d) ,loginTime,pass);
             			
             		}catch (SQLException e) {
             			// TODO: handle exception
             			e.printStackTrace();
             		}catch(NullPointerException e){
             			e.printStackTrace();
             		}
             		finally{
             			if(loginLogoutDataSource!=null)
             				loginLogoutDataSource.close();
             		}
				    	   
		                startActivity(new Intent(MainActivity.this,PatientListActivity.class).putExtra("JSON",stringBuffer.toString()));
				          }else if(stringBuffer.equals("[]\n"))
				        	  Toast.makeText(getApplicationContext(), "UserName & Password Incorrect", Toast.LENGTH_SHORT).show();
                       
				}
			}
		});
		
		tvForgetPassword.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,ForgetPasswordActivity.class));
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
	
		super.onBackPressed();
	}
	

	

	

	
}

	

