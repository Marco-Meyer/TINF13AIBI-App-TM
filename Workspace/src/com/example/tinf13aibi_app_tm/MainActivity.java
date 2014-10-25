package com.example.tinf13aibi_app_tm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private PhotoList list;
	private LocationManager locManager;
	private LocationListener locListener;
	private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        this.list = new PhotoList(this, (ListView)findViewById(R.id.photo_list), getDirectories());
        list.loadOldPhotoData();
        list.onClick();
        currentLocation = null;
        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        setLocationListener();
    }
    
    @Override
	protected void onStart() {
		super.onStart();
		if (locationProviderExists()) {
			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locListener);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		locManager.removeUpdates(locListener);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.takePhoto) {
        	Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        	startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            switch (resultCode) {
            	case RESULT_OK:
            		Bitmap picture = (Bitmap) data.getExtras().get("data");
   		    	 
    		    	Date currentDate = new Date();
    		    	Time currentTime = new Time(currentDate.getTime()); 
    				String pictureId = new SimpleDateFormat("yyyyMMdd_HHmmss").format(currentDate);
    				
    		        Photo resultPhoto = new Photo(pictureId, currentDate, currentTime,  currentLocation);
    		        list.addPhoto(resultPhoto, picture);
    		        list.onClick();
    		        Toast.makeText(this, "Photo added.", Toast.LENGTH_SHORT).show();
    		        break;
            	case RESULT_CANCELED:
            		Toast.makeText(this, "Canceled image capture. No photo added.", Toast.LENGTH_SHORT).show();
            		break;
            	default:
            		Toast.makeText(this, "Error occured. No photo added.", Toast.LENGTH_LONG).show();
            }
        }
    }
    
    private boolean locationProviderExists() {
    	boolean result = false;
    	
    	if (locManager != null) {
    		if (locManager.getProvider(LocationManager.GPS_PROVIDER) != null) {
    			result = true;
    		}
    	}
    	
    	return result;
    }
    
    private void setLocationListener() {
    	locListener =  new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				currentLocation = location; 
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			@Override
			public void onProviderEnabled(String provider) {
			}

			@Override
			public void onProviderDisabled(String provider) {
			}
    		
    	};
    }
    
    private String[] getDirectories() {
    	String photoDir = getDir("Photos", Context.MODE_PRIVATE).getAbsolutePath();
        String xmlDir = getDir("XMLs", Context.MODE_PRIVATE).getAbsolutePath();
        String[] directories = {photoDir, xmlDir};
        return directories;
    }
}
    








