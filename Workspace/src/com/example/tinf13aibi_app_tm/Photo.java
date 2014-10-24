package com.example.tinf13aibi_app_tm;

import java.sql.Time;
import java.util.Date;

import android.graphics.Bitmap;
import android.location.Location;

public class Photo {

	private String id;
	private Date date;
	private Time time;
	private String location;
	
	public Photo() {
		this.id = "";
		this.date = new Date();
		this.time = new Time(0);
		this.location = "";
	}
	
	public Photo(String id, Date date, Time time, String location) {
		this.id = id;
		this.date = date;
		this.time = time;
		this.location = location;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		
		return "id: " + id  +
			   " date: " + date;
	}
}
