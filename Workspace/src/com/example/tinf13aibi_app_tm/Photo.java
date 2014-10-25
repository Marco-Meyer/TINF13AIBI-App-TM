package com.example.tinf13aibi_app_tm;

import java.sql.Time;
import java.util.Date;

import android.location.Location;

public class Photo {

	private String id;
	private Date date;
	private Time time;
	private double latitude;
	private double longitude;
	
	public Photo() {
		this.id = "";
		this.date = new Date();
		this.time = new Time(0);
		this.latitude = 0.0;
		this.longitude = 0.0;
	}
	
	public Photo(String id, Date date, Time time, Location location) {
		this.id = id;
		this.date = date;
		this.time = time;
		setLatitude(location);
		setLongitude(location);
	}

	public String getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public Time getTime() {
		return time;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
	
	private void setLatitude(Location location) {
		this.latitude = 0.0;
		if (location != null) {
			this.latitude = location.getLatitude();
		}
	}

	private void setLongitude(Location location) {
		this.longitude = 0.0;
		if (location != null) {
			this.longitude = location.getLongitude();
		}
	}

	@Override
	public String toString() {
		
		return "id: " + getId()  +
			   " date: " + getDate();
	}
}
