package com.codefrux.doctorApps.control;

/*
 * This Class parse Json Class to an Array List of Object here CustomObject
 */

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codefrux.doctorApps.model.CustomObject;
import com.codefrux.doctorApps.model.Parameter;

public class JSONcommon {
	
	public ArrayList<CustomObject> parseResult(String json) throws JSONException{
    	ArrayList<CustomObject> results = new ArrayList<CustomObject>();
    	results.clear();
    	CustomObject obj;
    	JSONArray jsonArray_results = new JSONArray(json);
    	for(int i = 0; i < jsonArray_results.length(); i++){
  	      JSONObject jsonObject_i = jsonArray_results.getJSONObject(i);
  	      obj=new CustomObject();
  	      obj.setDoctor_Id(jsonObject_i.getString(Parameter.DOCTOR_ID));
  	      obj.setVisiting_Time(jsonObject_i.getString(Parameter.VISITING_TIME));
  	      obj.setDate(jsonObject_i.getString(Parameter.DATE));
  	      obj.setStatus(jsonObject_i.getString(Parameter.STATUS));
  	      obj.setPatient_Id(jsonObject_i.getString(Parameter.PATIENT_ID));
  	      obj.setPatient_Name(jsonObject_i.getString(Parameter.PATIENT_NAME));
  	      obj.setPatient_Age(jsonObject_i.getString(Parameter.PATIENT_AGE));
  	      obj.setPatient_Sex(jsonObject_i.getString(Parameter.PATIENT_SEX));
  	      obj.setPatient_Address(jsonObject_i.getString(Parameter.PATIENT_ADDRESS));
  	      obj.setPatient_Mobile(jsonObject_i.getString(Parameter.PATIENT_MOBILE));
  	      obj.setOverview(jsonObject_i.getString(Parameter.OVERVIEW));
  	      results.add(obj);
  	      }    
    	  return results;   
    }

}
