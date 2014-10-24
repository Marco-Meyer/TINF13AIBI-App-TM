package com.example.tinf13aibi_app_tm;

import java.sql.Time;
import java.util.Date;

import android.location.Location;

public class Photo {

	private String id;
	private Date date;
	private Time time;
	private Location location;
	
	public Photo() {
		this.id = "";
		this.date = new Date();
		this.time = new Time(0);
		this.location = new Location("");
	}
	
	public Photo(String id, Date date, Time time, Location location) {
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
