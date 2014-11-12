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
	private String pictureDate;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_maps);
		
		getIntentData();
				 
		createMap();
		                   
		map.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPosition()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.google_maps, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		   switch (item.getItemId()) {

	        case R.id.satellite:
	            if (item.isChecked()){ 
	            	item.setChecked(false);	
	            }
	            else{ 
	            item.setChecked(true);
	            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	            }
	            return true;
	        case R.id.map:
	        	 if (item.isChecked()){ 
	        		 item.setChecked(false);		
		            }
		         else {
		        	 item.setChecked(true);
		        	 map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		         }
		         return true;
	        default:
	            return super.onOptionsItemSelected(item);
		   }
	}
	
	private void getIntentData() {
		this.longitude = (double)getIntent().getDoubleExtra("com.example.tinf13aibi_app_tm.photoLongitude", 0.0);
		this.latitude = (double)getIntent().getDoubleExtra("com.example.tinf13aibi_app_tm.photoLatitude", 0.0);
		this.pictureId = (String)getIntent().getStringExtra("com.example.tinf13aibi_app_tm.photoId");
		this.pictureDate = (String)getIntent().getStringExtra("com.example.tinf13aibi_app_tm.photoDate");
	}
	
	private void createMap() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		final SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
		map = mapFragment.getMap();
		map.setMyLocationEnabled(true);
		map.addMarker(new MarkerOptions()
         .position(new LatLng(latitude, longitude))
         .title("Photo_" + pictureId + ".jpg")
         .snippet(pictureDate));
		
	}
	
	private CameraPosition getCameraPosition() {
		CameraPosition cameraPosition = new CameraPosition.Builder()
	    .target(new LatLng(latitude, longitude))
	    .zoom(10)
	    .build();
		return cameraPosition;
	}
}
