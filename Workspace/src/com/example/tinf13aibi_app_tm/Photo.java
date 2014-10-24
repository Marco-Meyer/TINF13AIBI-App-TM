package com.example.tinf13aibi_app_tm;

import java.util.Date;

import android.text.format.Time;

public class Photo {
	/* 
	 * dummy implementation for this class to test xml-writing/reading of the savemanager.
	 * We should think about reasonable data types of "date" and "time"..
	*/
	private long id;
	private String date;
	private String time;
	private String location;
	
	public Photo() {
		this.id = 0;
		this.date = "";
		this.time = "";
		this.location = "";
	}
	
	public Photo(long id, String date, String time, String location) {
		this.id = id;
		this.date = date;
		this.time = time;
		this.location = location;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		
		return "id: " + id  +
			   " date: " + date;
	}
}
