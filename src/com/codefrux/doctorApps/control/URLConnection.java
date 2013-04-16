package com.codefrux.doctorApps.control;

/*
 * This Class performs Connection to The server 
 * And Getting the Response From the Server
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;


public class URLConnection extends AsyncTask<Void, Void, String> {
	
	public static String LOGIN="http://192.168.2.12/DoctorFieldApp/login.php";
	public static String FORGOT_PASSWORD="http://192.168.2.12/DoctorFieldApp/ForgotPassword.php";
	public static String INSERT_DETAILS="http://192.168.2.12/DoctorFieldApp/insertDetails.php";
	public static String LOGOUT="http://192.168.2.12/DoctorFieldApp/logout.php";
	
	private Context context;
	private ProgressDialog pDialog=null;
	private String URL=null; 
	private List<NameValuePair> postParameters;
	
	
	public URLConnection(Context context){
		this.context=context;
	}
	public String postURL(String URL,List<NameValuePair> postParameters){
		this.URL=URL;
		this.postParameters=postParameters;
	    execute();
	    try {
	    	if(get().equals("No Internet"))
			  Toast.makeText(context,"No Internet" , Toast.LENGTH_SHORT).show();
			return get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 return null;
		
	}
	
	
 	private void showProDialog(){
   		pDialog =new ProgressDialog(context);
   		pDialog.setMessage("Downloading....");
   		pDialog.setIndeterminate(true);
   		pDialog.show();
   	}
   	
   	private void dismissProDialog(){
   		if(pDialog!=null)
   			if(pDialog.isShowing())
   				pDialog.dismiss();
   	}
   	private  boolean isOnline() {
   	    ConnectivityManager cm =
   	         (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
   	     NetworkInfo netInfo = cm.getActiveNetworkInfo();
   	     if (netInfo != null && netInfo.isConnectedOrConnecting()) {
   	         return true;
   	     }else
   	     return false;
   	     
   	 }
	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
	if(isOnline()){
			BufferedReader bufferedReader = null;
			StringBuffer stringBuffer=null;
	        HttpClient httpClient = new DefaultHttpClient();
	        HttpPost request = new HttpPost(URL);
	        try{
		    	UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParameters);
			       request.setEntity(entity);
			       HttpResponse response= httpClient.execute(request);
			       bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			        stringBuffer = new StringBuffer("");
			       String line = "";
			       String LineSeparator = System.getProperty("line.separator");
			       while ((line = bufferedReader.readLine()) != null) {
			           stringBuffer.append(line + LineSeparator); 
			        }
			       bufferedReader.close();
	     	       return stringBuffer.toString();
			      
			       }catch (ClientProtocolException e) {
					// TODO: handle exception
			    	   e.printStackTrace();
				}
			       catch(IOException e){
			    	   e.printStackTrace();
			       }
			       finally{
			    	   if (bufferedReader != null){
			    		    try {
			    		     bufferedReader.close();
			    		    } catch (IOException e) {
			    		     // TODO Auto-generated catch block
			    		     e.printStackTrace();
			    		    }
			    		   }
			    	   if(stringBuffer==null)
			    	   { 
			    		   return null;
			    	   }
			       }
	        return null;
			}
			
			return "No Internet";
			
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		showProDialog();
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dismissProDialog();
	}
}
