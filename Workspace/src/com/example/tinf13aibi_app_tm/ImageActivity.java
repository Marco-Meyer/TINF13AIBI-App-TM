package com.example.tinf13aibi_app_tm;

import java.io.File;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class ImageActivity extends ActionBarActivity {
	private Photo photo;
	private ImageView imgView;
	private Bitmap currentPicture;
	private ShareActionProvider shareActionProvider;
	private SaveManager sm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		
		sm = new SaveManager();
		this.photo = (Photo) getIntent().getSerializableExtra("com.example.tinf13aibi_app_tm.photo");
		
		imgView = (ImageView) findViewById(R.id.imageView);
		currentPicture = sm.loadPictureWithId(photo.getId());
		imgView.setImageBitmap(currentPicture);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.image, menu);
		 
		MenuItem menuItem = menu.findItem(R.id.action_share);
		shareActionProvider = new ShareActionProvider(getApplicationContext());
		menuItem.setActionProvider(shareActionProvider);
	    shareActionProvider.setShareIntent(getShareIntent());

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_share) {
			return true;
		}
		if (id == R.id.showOnMap) {
			showMap();
			return true;
		}
		if (id == R.id.saveInLibrary) {
			savePictureInLibrary();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private Intent getShareIntent() {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/jpeg");
		File file = new File(sm.getPictureFileName(photo.getId()));
		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		return shareIntent;
	}

	private void showMap() {
		if(photo.getLongitude() == 0.0 && photo.getLatitude() == 0.0) {
			Toast.makeText(ImageActivity.this,"Kein Standort verfügbar", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(ImageActivity.this,"Karte wird geladen", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(ImageActivity.this,GoogleMapsActivity.class);
			intent.putExtra("com.example.tinf13aibi_app_tm.photoLongitude",photo.getLongitude());
			intent.putExtra("com.example.tinf13aibi_app_tm.photoLatitude",photo.getLatitude());
			intent.putExtra("com.example.tinf13aibi_app_tm.photoId" , String.valueOf(photo.getId()));
			intent.putExtra("com.example.tinf13aibi_app_tm.photoDate" , String.valueOf(photo.getDate()));
			startActivity(intent);
		}
	}
	
	private void savePictureInLibrary() {
		MediaStore.Images.Media.insertImage(getContentResolver(), currentPicture, String.valueOf(photo.getId()) , "");
		Toast.makeText(this, "In Bildergalerie gespeichert.", Toast.LENGTH_SHORT).show();
	}
}
