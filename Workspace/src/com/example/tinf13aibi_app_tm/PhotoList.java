package com.example.tinf13aibi_app_tm;

import java.util.List;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class PhotoList extends FragmentActivity {
	private ListView list;
	private ArrayAdapter<Photo> adapter;
	private SaveManager sm;
	
	public PhotoList(Context context, ListView list) {
		this.list = list;
		this.adapter = new ArrayAdapter<Photo>(context, android.R.layout.simple_list_item_1);
		this.list.setAdapter(this.adapter);
		sm = new SaveManager();
	}
	
	public void addPhoto(Photo photo) {
		adapter.add(photo);
		sm.saveEntirePictureData(photo);
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
		sm.deleteAllPictureData();
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
				view.getContext().startActivity(intent);
			}
	    });
	}
	
	public void setOnLongClickListener(){
		
		this.list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, final View view,
					int position, long id) {

					final Photo currentPhoto = adapter.getItem(position);
					AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

					builder.setTitle(R.string.deleteDialogTitle);
					builder.setMessage(R.string.deleteDialogForOne);
					builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				           
						public void onClick(DialogInterface dialog, int id) {
				               deletePhoto(currentPhoto);
				               Toast.makeText(view.getContext(),"Bild gelöscht.",Toast.LENGTH_SHORT).show();
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
		});
	}
}
