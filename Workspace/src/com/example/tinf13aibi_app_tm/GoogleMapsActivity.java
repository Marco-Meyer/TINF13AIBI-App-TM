package com.example.tinf13aibi_app_tm;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.support.v7.app.ActionBarActivity;
//import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class GoogleMapsActivity extends ActionBarActivity {

	private GoogleMap map;
	private double longitude;
	private double latitude;
	private String pictureId;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_maps);
		
		this.longitude = (double)getIntent().getDoubleExtra("com.example.tinf13aibi_app_tm.photoLongitude", 0.0);
		this.latitude = (double)getIntent().getDoubleExtra("com.example.tinf13aibi_app_tm.photoLatitude", 0.0);
		this.pictureId = (String)getIntent().getStringExtra("com.example.tinf13aibi_app_tm.photoId");
				 
		FragmentManager fragmentManager = getSupportFragmentManager();
		final SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
		map = mapFragment.getMap();
		map.setMyLocationEnabled(true);
		map.addMarker(new MarkerOptions()
         .position(new LatLng(latitude, longitude))
         .title("Photo_"+ pictureId + ".jpg"));
		
		CameraPosition cameraPosition = new CameraPosition.Builder()
	    .target(new LatLng(latitude, longitude))
	    .zoom(10)
	    .build();                   
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


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

		return super.onOptionsItemSelected(item);
	}

}
