package com.example.grabmyspot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdb.android.GetRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;
import com.mobdb.android.UpdateRowData;

public class CheckInActivity extends Activity {
	
	Button mCheckIn;
	Button mCheckOut;
	Button mPostMessage;
	final String APP_KEY = "00#OoQ-1Ss-euagfD2021Y010Jum3WoBmmM-DOCLlK5HtbmWgGgCsW0O1CIA77Y509";
	String mGarageName;
	TextView mGarageInfo;
	int mCurrent;
	int mCapacity;
	String[] mComments;
	String[] mTimestamp;
	ListView mList;
	
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.checkin);
	    
	    mGarageName = getIntent().getStringExtra("garageName");
	    mCheckIn = (Button) findViewById(R.id.checkIn);
	    mCheckOut = (Button) findViewById(R.id.checkOut);
	    mPostMessage = (Button) findViewById(R.id.postmessagebutton);
	    mGarageInfo = (TextView) findViewById(R.id.garageInfo);
	    mList = (ListView) findViewById(R.id.listView1);
	   
	    
	    	    
	   //get initial data to populate page
	    GetRowData getRowData = new GetRowData("garage");
		getRowData.getField("current");
		getRowData.getField("capacity");
		getRowData.whereEqualsTo("name", mGarageName);
		
		MobDB.getInstance().execute(APP_KEY, getRowData, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	//Toast.makeText(getApplicationContext(), "got garage data", Toast.LENGTH_LONG).show();
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (for format example: refer JSON REST API)
		    	try{
		    		JSONObject response = new JSONObject(jsonStr);
		    		int status = response.getInt("status");
		    		if(status == 101){
		    			
		    			JSONArray array = response.getJSONArray("row");
		    			JSONObject object = array.getJSONObject(0);
		    			mCurrent = object.getInt("current");
		    			mCapacity = object.getInt("capacity");
		    			
		    			
		    			mGarageInfo.setText(mGarageName + "    " + mCurrent + "/" + mCapacity);
		    			
		    			
		    		}
		    		
		    		
		    	}catch(JSONException e){
		    		
		    	}
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    	Toast.makeText(getApplicationContext(), "didnt get garage data", Toast.LENGTH_LONG).show();
		    }
		});	
	    
	      
		//get initial info to populate page
		GetRowData getRowData2 = new GetRowData("comments");
	    getRowData2.getField("comment");
	    getRowData2.getField("timestamp");
	    getRowData2.whereEqualsTo("garagename", mGarageName);
	    
	    MobDB.getInstance().execute(APP_KEY, getRowData2, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	//Toast.makeText(getApplicationContext(), "got comments data", Toast.LENGTH_LONG).show();
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (for format example: refer JSON REST API)
		    	try{
		    		JSONObject response = new JSONObject(jsonStr);
		    		int status = response.getInt("status");
		    		if(status == 101){
		    			
		    			JSONArray array = response.getJSONArray("row");
		    			
		    			int length = array.length();
		    			    
		    			int x = length - 1;
		    			mComments = new String[5];
		    			mTimestamp = new String[5];
		    			for(int i = 0; i < 5; i++){
		    				
		    				if(array.getJSONObject(x - i) != null){
		    				mComments[i] = array.getJSONObject(x - i).getString("comment"); 
		    				mTimestamp[i] = array.getJSONObject(x - i).getString("timestamp");
		    				}
		        				  
		    			}
		    	
		    		}
		    		
		    		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	    			for (int i = 0; i < 5; i++) {
	    			    Map<String, String> datum = new HashMap<String, String>(2);
	    			    datum.put("comment", mComments[i]);
	    			    datum.put("timestamp", mTimestamp[i]);
	    			    data.add(datum);
	    			}
	    			
	    			SimpleAdapter adapter = new SimpleAdapter(CheckInActivity.this, data,
	    			                                          android.R.layout.simple_list_item_2,
	    			                                          new String[] {"comment", "timestamp"},
	    			                                          new int[] {android.R.id.text1,
	    				      								  android.R.id.text2});
	    				     								 
	    			
	    			mList.setAdapter(adapter);
		    		   
		    		
		    	}catch(JSONException e){
		    		   
		    	}
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    	Toast.makeText(getApplicationContext(), "didnt get comments data", Toast.LENGTH_LONG).show();
		    }
		});	
	
			    			
			    	
	 
	    
	    
	}
	
	
	//update the backend for check in
	public void checkInHandler(View v){
		
		mCurrent = mCurrent + 1;
		UpdateRowData updateRowData = new UpdateRowData("garage");
		updateRowData.setValue("current", mCurrent);
		updateRowData.whereEqualsTo("name", mGarageName);
		
		MobDB.getInstance().execute(APP_KEY, updateRowData, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	Toast.makeText(getApplicationContext(), "You have been checked into " + mGarageName, Toast.LENGTH_LONG).show();
		    	
		    	mGarageInfo.setText(mGarageName + "    " + mCurrent + "/" + mCapacity);
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (for format example: refer JSON REST API)
		    	
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    	Toast.makeText(getApplicationContext(), "not updated", Toast.LENGTH_LONG).show();
		    }
		});	
		
		
	}
	
	
	//update the backend info for checkout
	public void checkOutHandler(View v){
		mCurrent = mCurrent - 1;
		UpdateRowData updateRowData = new UpdateRowData("garage");
		updateRowData.setValue("current", mCurrent);
		updateRowData.whereEqualsTo("name", mGarageName);
		
		MobDB.getInstance().execute(APP_KEY, updateRowData, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	
		    	mGarageInfo.setText(mGarageName + "    " + mCurrent + "/" + mCapacity);
		    	Toast.makeText(getApplicationContext(), "You have been checked out of " + mGarageName, Toast.LENGTH_LONG).show();
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (for format example: refer JSON REST API)
		    	
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    	Toast.makeText(getApplicationContext(), "not updated", Toast.LENGTH_LONG).show();
		    }
		});	
		
		
	}
	
	//go to post messeage
	public void postMessageHandler(View v){
		
		Intent intent = new Intent(this, PostMessageActivity.class);
		intent.putExtra("garageName", mGarageName);
		startActivity(intent);
		
		
	}
	
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		finish();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		GetRowData getRowData2 = new GetRowData("comments");
	    getRowData2.getField("comment");
	    getRowData2.getField("timestamp");
	    getRowData2.whereEqualsTo("garagename", mGarageName);
	    
	    MobDB.getInstance().execute(APP_KEY, getRowData2, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	//Toast.makeText(getApplicationContext(), "got comments data", Toast.LENGTH_LONG).show();
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (for format example: refer JSON REST API)
		    	try{
		    		JSONObject response = new JSONObject(jsonStr);
		    		int status = response.getInt("status");
		    		if(status == 101){
		    			
		    			JSONArray array = response.getJSONArray("row");
		    			
		    			int length = array.length();
		    			    
		    			int x = length - 1;
		    			mComments = new String[5];
		    			mTimestamp = new String[5];
		    			for(int i = 0; i < 5; i++){
		    				
		    				if(array.getJSONObject(x - i) != null){
		    				mComments[i] = array.getJSONObject(x - i).getString("comment"); 
		    				mTimestamp[i] = array.getJSONObject(x - i).getString("timestamp");
		    				}
		        				  
		    			}
		    	
		    		}
		    		
		    		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	    			for (int i = 0; i < 5; i++) {
	    			    Map<String, String> datum = new HashMap<String, String>(2);
	    			    datum.put("comment", mComments[i]);
	    			    datum.put("timestamp", mTimestamp[i]);
	    			    data.add(datum);
	    			}
	    			
	    			SimpleAdapter adapter = new SimpleAdapter(CheckInActivity.this, data,
	    			                                          android.R.layout.simple_list_item_2,
	    			                                          new String[] {"comment", "timestamp"},
	    			                                          new int[] {android.R.id.text1,
	    				      								  android.R.id.text2});
	    				     								 
	    			
	    			mList.setAdapter(adapter);
		    		   
		    		
		    	}catch(JSONException e){
		    		   
		    	}
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    	Toast.makeText(getApplicationContext(), "didnt get comments data", Toast.LENGTH_LONG).show();
		    }
		});	
	
		
	}

}
