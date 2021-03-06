package com.example.tinf13aibi_app_tm;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import android.location.Location;

public class Photo implements Serializable{

	private static final long serialVersionUID = -7561336969818481142L;
	private long id;
	private Date date;
	private Time time;
	private double latitude;
	private double longitude;
	
	public Photo() {
		this.id = 0;
		this.date = new Date();
		this.time = new Time(0);
		this.latitude = 0.0;
		this.longitude = 0.0;
	}
	
	public Photo(long id, Date date, Time time, Location location) {
		this.id = id;
		this.date = date;
		this.time = time;
		setLatitude(location);
		setLongitude(location);
	}

	public long getId() {
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
		return "Photo_" + id  + ".jpg \n" +
			   "Datum: " + getDate();
	}
}
