package com.example.tinf13aibi_app_tm;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PhotoList {
	private ListView list;
	private ArrayAdapter<Photo> adapter;
	private SaveManager sm;
	
	public PhotoList(Context context, ListView list, String[] directories) {
		this.list = list;
		this.adapter = new ArrayAdapter<Photo>(context, android.R.layout.simple_list_item_1);
		this.list.setAdapter(this.adapter);
		sm = new SaveManager(directories);
	}
	
	public void addPhoto(Photo photo, Bitmap picture) {
		adapter.add(photo);
		sm.saveEntirePictureData(photo, picture);
	}
	
	public void loadOldPhotoData() {
		//has jet to be done.
		List<Photo> loadedPhotos = sm.loadEntirePictureData();
		adapter.addAll(loadedPhotos);
	}
	
	public void onClick()
	{
		
		this.list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent = new Intent(view.getContext(), ImageActivity.class);
				view.getContext().startActivity(intent);
//				Toast.makeText(view.getContext(),"You've got an event" + context,Toast.LENGTH_LONG).show();

				
			}
	    });
	}
	
}
