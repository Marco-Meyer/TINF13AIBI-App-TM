package com.example.tinf13aibi_app_tm;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageActivity extends ActionBarActivity {
	private Photo photo;
	private ImageView imgView;
	private Bitmap currentPicture;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		this.photo = (Photo) getIntent().getSerializableExtra("com.example.tinf13aibi_app_tm.photo");
		Toast.makeText(this, "photo is " + photo.getId(), Toast.LENGTH_LONG).show();
		imgView = (ImageView) findViewById(R.id.imageView);
		currentPicture = (Bitmap) getIntent().getParcelableExtra("com.example.tinf13aibi_app_tm.picture");
		imgView.setImageBitmap(currentPicture);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.sendAsMail) {
			return true;
		}
		if (id == R.id.showOnMap) {
			Intent intent = new Intent(ImageActivity.this,GoogleMapsActivity.class);
			intent.putExtra("com.example.tinf13aibi_app_tm.photoLongitude",photo.getLongitude());
			intent.putExtra("com.example.tinf13aibi_app_tm.photoLatitude",photo.getLatitude());
			startActivity(intent);
			return true;
		}
		if (id == R.id.saveInLibrary) {
			MediaStore.Images.Media.insertImage(getContentResolver(), currentPicture, photo.getId() , "");
			Toast.makeText(this, "Bild in der Bildergalerie gespeichert", Toast.LENGTH_SHORT).show()	;
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
