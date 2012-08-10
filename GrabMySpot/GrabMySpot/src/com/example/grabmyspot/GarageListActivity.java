package com.example.grabmyspot;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GarageListActivity extends ListActivity {
	
	String[] mGarageList;
	ArrayAdapter<String>adapter;
	ListView lv;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.garage_list);
	    
	    getListView().setCacheColorHint(Color.rgb(125, 9, 0));
	    getListView().setBackgroundColor(Color.rgb(125, 9, 0));
	    mGarageList = new String[6];
	    mGarageList[0] = "Woodward Ave";
	    mGarageList[1] = "Call St";
	    mGarageList[2] = "Copeland St";
	    mGarageList[3] = "Traditions Way";
	    mGarageList[4] = "Stadium Dr";
	    mGarageList[5] = "West Pensacola St";
	    
	    //populate list with garages
	    adapter = new ArrayAdapter<String>(GarageListActivity.this, android.R.layout.simple_list_item_1, mGarageList);
	    setListAdapter(adapter);
	    
	    
	    lv = getListView();
	    
	    //set click event handler fo item click
	    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(getApplicationContext(), CheckInActivity.class);
				String garageName = lv.getItemAtPosition(arg2).toString();
				intent.putExtra("garageName", garageName);
				startActivity(intent);
				
				
			}
	    	
	    	
		});
	    
	    
	 
	}

}
