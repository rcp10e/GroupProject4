package com.example.grabmyspot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdb.android.InsertRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;

public class PostMessageActivity extends Activity {

	final String APP_KEY = "00#OoQ-1Ss-euagfD2021Y010Jum3WoBmmM-DOCLlK5HtbmWgGgCsW0O1CIA77Y509";
	String mGarageName;
	TextView mMessageIntro;
	RadioGroup mGroup1;
	RadioGroup mGroup2;
	Button mPostMessageButton;
	String mComment;
	RadioButton mEnter;
	RadioButton mExit;
	RadioButton mFloor1;
	RadioButton mFloor2;
	RadioButton mFloor3;
	RadioButton mFloor4;
	RadioButton mFloor5;
	RadioButton mFloor6;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.post_message);
	
	    mGarageName = getIntent().getStringExtra("garageName");
	    mMessageIntro = (TextView) findViewById(R.id.messageintro);
	    mGroup1= (RadioGroup) findViewById(R.id.radioGroup1);
	    mGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
	    mPostMessageButton = (Button) findViewById(R.id.postmessagebutton);
	    mMessageIntro.setText("Post a message for " + mGarageName);
	    mEnter = (RadioButton) findViewById(R.id.radioenter);
	    mExit = (RadioButton) findViewById(R.id.radioexit);
	    mFloor1 = (RadioButton) findViewById(R.id.floor1);
	    mFloor2 = (RadioButton) findViewById(R.id.floor2);
	    mFloor3 = (RadioButton) findViewById(R.id.floor3);
	    mFloor4 = (RadioButton) findViewById(R.id.floor4);
	    mFloor5 = (RadioButton) findViewById(R.id.floor5);
	    mFloor6 = (RadioButton) findViewById(R.id.floor6);
	    
	}
	
	
	public void postMessageHandler(View v){
		
		int floor = 0;
		
		
		//set the users floor level
		if(mFloor1.isChecked()){
			floor = 1;
		}else if(mFloor2.isChecked()){
			floor = 2;
		}else if(mFloor3.isChecked()){
			floor = 3;
		}else if(mFloor4.isChecked()){
			floor = 4;
		}else if(mFloor5.isChecked()){
			floor = 5;
		}else if(mFloor6.isChecked()){
			floor = 6;
		}
		
		if(mEnter.isChecked()){
			mComment = "Entering the " + floor + " floor of " + mGarageName;
		}else{
			mComment = "Exiting the " + floor + " floor of " + mGarageName;
		}
		
		//Get date to make timestamp
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
		String formattedDate = sdf.format(date);
		
		
		//insert user given data to backend
		InsertRowData insertRowData = new InsertRowData("comments");
		insertRowData.setValue("timestamp", formattedDate);
		insertRowData.setValue("comment", mComment);
		insertRowData.setValue("garagename", mGarageName);
		
		MobDB.getInstance().execute(APP_KEY, insertRowData, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	Toast.makeText(getApplicationContext(), "Message Posted to: " + mGarageName, Toast.LENGTH_LONG).show();
		    	finish();
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
		    	Toast.makeText(getApplicationContext(), "didnt insert data", Toast.LENGTH_LONG).show();
		    }
		});	
		
		
		
	}
	
	

}
