package com.example.tinf13aibi_app_tm;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
		List<Photo> loadedPhotos = sm.loadEntirePictureData();
		adapter.addAll(loadedPhotos);
	}
}
