package com.example.tinf13aibi_app_tm;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class GoogleMapsActivity extends ActionBarActivity {

	private GoogleMap map;
	private double longitude;
	private double latitude;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_maps);
		
		this.longitude = (double)getIntent().getDoubleExtra("com.example.tinf13aibi_app_tm.photoLongitude", 0.0);
		System.out.println(longitude);
		this.latitude = (double)getIntent().getDoubleExtra("com.example.tinf13aibi_app_tm.photoLatitude", 0.0);
		 
		FragmentManager fragmentManager = getSupportFragmentManager();
		final SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
		map = mapFragment.getMap();
		map.addMarker(new MarkerOptions()
         .position(new LatLng(latitude, longitude)));

	}

	 @Override
	 protected void onResume() {
		 super.onResume();
		 //setUpMapIfNeeded();
	 }
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.google_maps, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
