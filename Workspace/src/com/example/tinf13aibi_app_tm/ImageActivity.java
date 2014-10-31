package com.example.tinf13aibi_app_tm;

import java.io.File;

import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	private String photoDir;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

		this.photo = (Photo) getIntent().getSerializableExtra("com.example.tinf13aibi_app_tm.photo");
		
		imgView = (ImageView) findViewById(R.id.imageView);
		this.photoDir = getIntent().getStringExtra("com.example.tinf13aibi_app_tm.picture");
		//Toast.makeText(this, "Das Bild liegt unter " + photoDir, Toast.LENGTH_LONG).show();
		currentPicture = BitmapFactory.decodeFile(photoDir);
		//currentPicture = (Bitmap) getIntent().getParcelableExtra("com.example.tinf13aibi_app_tm.picture");
		imgView.setImageBitmap(currentPicture);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image, menu);
		 
		MenuItem menuItem = menu.findItem(R.id.action_share);
		shareActionProvider = new ShareActionProvider(getApplicationContext());
		//shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
	    //shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
	    //MenuItemCompat.setActionProvider(menuItem, shareActionProvider);
		menuItem.setActionProvider(shareActionProvider);
	    shareActionProvider.setShareIntent(getShareIntent());

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_share) {
			return true;
		}
		if (id == R.id.showOnMap) {
			
			if(photo.getLongitude() == 0.0 && photo.getLatitude() == 0.0)
			{
				Toast.makeText(ImageActivity.this,"No Location registered.",Toast.LENGTH_SHORT).show();
			}else
			{
				Intent intent = new Intent(ImageActivity.this,GoogleMapsActivity.class);
				intent.putExtra("com.example.tinf13aibi_app_tm.photoLongitude",photo.getLongitude());
				intent.putExtra("com.example.tinf13aibi_app_tm.photoLatitude",photo.getLatitude());
				intent.putExtra("com.example.tinf13aibi_app_tm.photoId" , photo.getId());
				startActivity(intent);
			}
			return true;
		}
		if (id == R.id.saveInLibrary) {
			MediaStore.Images.Media.insertImage(getContentResolver(), currentPicture, photo.getId() , "");
			Toast.makeText(this, "Picture saved to gallery", Toast.LENGTH_SHORT).show()	;
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private Intent getShareIntent() {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/jpeg");
		File file = new File(photoDir);
		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		return shareIntent;
	}
}
