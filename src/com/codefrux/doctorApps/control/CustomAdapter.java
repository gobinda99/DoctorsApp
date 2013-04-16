package com.codefrux.doctorApps.control;

import java.util.ArrayList;

import com.codefrux.doctorApps.PatientInformationActivity;
import com.codefrux.doctorApps.R;

import com.codefrux.doctorApps.model.CustomObject;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;




public class CustomAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private Context context;
	
	  private ArrayList<CustomObject> objects;
	  static class ViewHolder {
	      TextView tvPatientName;
	      TextView tvVisitingTimr;
	      TextView tvStatus;
	      Button bGo;
	   }
	  public CustomAdapter(Context context, ArrayList<CustomObject> objects) {
	      inflater = LayoutInflater.from(context);
	      this.objects = objects;
	      this.context = context;
	   }

	public int getCount() {
		// TODO Auto-generated method stub
		return objects.size();
	}

	public CustomObject getItem(int position) {
		// TODO Auto-generated method stub
		return objects.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		 return position;
		}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
	      if(convertView == null) {
	    	 
	         holder = new ViewHolder();
             convertView = inflater.inflate(R.layout.inflate_xml, null);
	         holder.tvPatientName = (TextView) convertView.findViewById(R.id.textView1inflatePatientName);
	        holder.tvVisitingTimr = (TextView) convertView.findViewById(R.id.textView2inflateVisitingTime);
	        holder.tvStatus = (TextView) convertView.findViewById(R.id.textView3inflateStatus);
	        holder.bGo = (Button) convertView.findViewById(R.id.button1InflateGo);
	         convertView.setTag(holder);
	      } else {
	         holder = (ViewHolder) convertView.getTag();
	      }
	      holder.tvPatientName.setText(objects.get(position).getPatient_Name());
	      holder.tvVisitingTimr.setText(objects.get(position).getDate()+" "+objects.get(position).getVisiting_Time());
	    //  Toast.makeText(context, objects.get(position).getStatus(), 2000).show();
	      String status=objects.get(position).getStatus();
	      if(status.equals("0"))
	      { holder.tvStatus.setText("INCOMPLETE");
	         holder.tvStatus.setTextColor(Color.RED);
	      }
	      else
	      { 
	    	  holder.tvStatus.setText("COMPLETE");
	    	  holder.tvStatus.setTextColor(Color.GREEN);
	    	//  holder.bGo.setVisibility(View.INVISIBLE);
	      }
	      holder.bGo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			      CustomObject obj=(CustomObject)getItem(position);
			      context.startActivity(new Intent(context,PatientInformationActivity.class)
			      .putExtra("Doctor_Id", obj.getDoctor_Id())
			      .putExtra("Visiting_Time", obj.getVisiting_Time())
			      .putExtra("Date", obj.getDate())
			      .putExtra("Patient_Id", obj.getPatient_Id())
			      .putExtra("Patient_Name", obj.getPatient_Name())
			      .putExtra("Patient_Age", obj.getPatient_Age())
			      .putExtra("Patient_Sex", obj.getPatient_Sex())
			      .putExtra("Patient_Address", obj.getPatient_Address())
			      .putExtra("Patient_Mobile", obj.getPatient_Mobile())
			      .putExtra("Overview", obj.getOverview()));
			}
		});
	     
	      
	      return convertView;
		
	}
}
