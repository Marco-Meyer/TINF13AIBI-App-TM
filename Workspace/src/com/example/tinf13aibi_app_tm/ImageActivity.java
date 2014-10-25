package com.example.tinf13aibi_app_tm;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageActivity extends ActionBarActivity {
	private Photo photo;
	private ImageView imgView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		this.photo = (Photo) getIntent().getSerializableExtra("com.example.tinf13aibi_app_tm.photo");
		Toast.makeText(this, "photo is " + photo.getId(), Toast.LENGTH_LONG).show();
		imgView = (ImageView) findViewById(R.id.imageView);
		imgView.setImageBitmap((Bitmap) getIntent().getParcelableExtra("com.example.tinf13aibi_app_tm.picture"));
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
			return true;
		}
		if (id == R.id.saveInLibrary) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
