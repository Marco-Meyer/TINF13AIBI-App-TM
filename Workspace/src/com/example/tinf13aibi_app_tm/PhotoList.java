package com.example.tinf13aibi_app_tm;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PhotoList {
	private ListView list;
	private ArrayAdapter<Photo> adapter;
	
	public PhotoList(Context context, ListView list) {
		this.list = list;
		this.adapter = new ArrayAdapter<Photo>(context, android.R.layout.simple_list_item_1);
		this.list.setAdapter(this.adapter);
	}
	
	public void addPhoto(Photo photo) {
		adapter.add(photo);
	}
	
	public void loadPhotos() {
		//has jet to be done.
	}
}
