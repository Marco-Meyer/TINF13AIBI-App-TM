package com.example.tinf13aibi_app_tm;

import java.util.Date;
import java.io.File;
import java.sql.Time;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
//import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private PhotoList photoList;
	private LocationManager locManager;
	private LocationListener locListener;
	private Location currentLocation;
	private Uri fileUri;
	private SaveManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.photoList = new PhotoList(this, (ListView)findViewById(R.id.photo_list));
        sm = new SaveManager();
        photoList.loadOldPhotoData();
        photoList.setOnClickListener();
        photoList.setOnLongClickListener();
        currentLocation = null;
        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        setLocationListener();
    }
    
    @Override
	protected void onStart() {
		super.onStart();
		if (locationProviderExists()) {
			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locListener);
		}else
		{
			locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locListener);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		locManager.removeUpdates(locListener);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.takePhoto) {
        	Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        	fileUri = getOutputMediaFileUri(); 
        	intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, fileUri);
        	startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            return true;
        }
        if (id == R.id.deletePhotos) {

			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setTitle(R.string.deleteDialogTitle);
			builder.setMessage(R.string.deleteDialogForAll);
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
		           
				public void onClick(DialogInterface dialog, int id) {
					photoList.deleteAllPhotos();
					Toast.makeText(MainActivity.this,"Alle Bilder gelöscht.",Toast.LENGTH_SHORT).show();
		        }
		    });
			builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
			               // User cancelled the dialog
			    }
			 });

			AlertDialog dialog = builder.create();
			dialog.show();
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
        	
            switch (resultCode) {
            	case RESULT_OK:

            		if(data==null){
//            		Bitmap picture = (Bitmap) data.getExtras().get("data");
   		    	 
    		    	Date currentDate = new Date();
    		    	Time currentTime = new Time(currentDate.getTime()); 
    		        
    				Photo resultPhoto = new Photo(sm.getUniqueId(), currentDate, currentTime,  currentLocation);
    		        photoList.addPhoto(resultPhoto);

    		        Toast.makeText(this, "Bild hinzugefügt.", Toast.LENGTH_SHORT).show();
    		        break;
    		        }
            	case RESULT_CANCELED:
            		Toast.makeText(this, "Bildaufnahme wurde abgebrochen.\nKein Bild hinzugefügt.", Toast.LENGTH_SHORT).show();
            		break;
            	default:
            		Toast.makeText(this, "Ein Fehler ist aufgetreten.\nKein Bild hinzugefügt.", Toast.LENGTH_LONG).show();
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
				Toast.makeText(MainActivity.this,"GPS-Koordinaten sind verfügbar.",Toast.LENGTH_SHORT).show();
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
    
    
  public Uri getOutputMediaFileUri(){
        return Uri.fromFile(getOutputMediaFile());
  }

  public File getOutputMediaFile(){
	  	return	new File(sm.getNewPictureFileName());
  }
    
    
    
    
}
    








