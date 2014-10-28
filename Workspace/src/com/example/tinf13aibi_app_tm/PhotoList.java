package com.example.tinf13aibi_app_tm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

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
	
	public void deletePhoto(Photo photo){
		sm.deleteEntirePictureData(photo);
		adapter.remove(photo);
	}
	
	public void deleteAllPhotos(){
		adapter.clear();
		sm.deleteAllEntirePictureData();
	}
	public void setOnClickListener()
	{
		this.list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				Photo currentPhoto = adapter.getItem(position);
				Intent intent = new Intent(view.getContext(), ImageActivity.class);
				intent.putExtra("com.example.tinf13aibi_app_tm.photo" , currentPhoto);
				intent.putExtra("com.example.tinf13aibi_app_tm.picture", sm.loadPictureWithId(currentPhoto.getId()));
				view.getContext().startActivity(intent);
//				Toast.makeText(view.getContext(),"You've got an event" + context,Toast.LENGTH_LONG).show();	
			}
	    });
	}
	
	public void setOnLongClickListener(){
		
		this.list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
					Photo currentPhoto = adapter.getItem(position);
					deletePhoto(currentPhoto);
					Toast.makeText(view.getContext(),"Photo_" + currentPhoto.getId() + ".jpg deleted.",Toast.LENGTH_SHORT).show();
					return true;
			}
			
		});
	}
}
