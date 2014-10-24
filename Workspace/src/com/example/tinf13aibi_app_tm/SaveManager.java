package com.example.tinf13aibi_app_tm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;

import com.thoughtworks.xstream.XStream;

public class SaveManager {
	//fixed location we want to save our xml-files / pictures. Might a bit too inflexible..
	private String directory = "";
	private XStream xstream; //xstream is a lib able to convert entire classes to xml and vice versa. Convenient, isn't it?
	
	public SaveManager(String directory) {
		this.directory = directory;
		xstream = new XStream();
		xstream.alias("Photo", Photo.class);
	}
	
	
	public void savePictureInDir(long pictureId, Bitmap picture) {
	try{
		
    	if(picture != null)
        {
			String fileName = "Photo_" + String.valueOf(pictureId) + ".jpg";
    	    
			File mediaFile = new File(directory,fileName);  
    	    
			FileOutputStream fOut = new FileOutputStream(mediaFile);
    	    picture.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
    	    fOut.flush();
    	    fOut.close(); 
    	}
	}
		catch(Exception e)
	    {
	    	
	    }
	}
	
	public void savePictureMetaDataInXml(Photo photo) {
		 String xml = xstream.toXML(photo);
		 System.out.println(xml);
		 writeXmlToFile(photo.getId(), xml);
	}
	
	public Photo loadPictureMetaDataFromXml(long pictureId) {
		return (Photo) xstream.fromXML(getFileWithId(pictureId)); 
	}
	
	private void writeXmlToFile(long id, String xml) {
		BufferedWriter writer = null;
		try{
		    BufferedReader reader = new BufferedReader(new StringReader("<?xml version=\"1.0\"?>\n" + xml));
		    writer = new BufferedWriter(new FileWriter(getFileName(id), true));
		
		    while ((xml = reader.readLine()) != null) {
		
		        writer.write(xml + System.getProperty("line.separator"));

        }
		} catch (Exception e) {
			System.err.println("Error in XML Write: " + e.getMessage());
		} finally{
			if(writer != null){
		        try{
		            writer.close();
		        }catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
		//well... new File(StringToXml(xml)) maybe?..xD
		/*FileOutputStream oStream = null;
		try{            
		    oStream = new FileOutputStream(getFileName(id));
		    oStream.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
		    byte[] bytes = xml.getBytes("UTF-8");
		    oStream.write(bytes);
		
		}catch (Exception e){
		    System.err.println("Error in XML Write: " + e.getMessage());
		}
		finally{
		    if(oStream != null){
		        try{
		            oStream.close();
		        }catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}*/
	}
	
	private File getFileWithId(long pictureId) {
		// we have to search our dir and find the associated file here
		return new File(getFileName(pictureId));
	}
	
	private String getFileName(long id) {
		return directory + "Photo_" + id + ".xml";
	}
	
	public static void main(String[] args) {
//		Photo testphoto = new Photo(10, "today", "now", "here");
//		SaveManager sm = new SaveManager();
//		/*sm.savePictureMetaDataInXml(testphoto);*/
//		Photo testphoto2 = sm.loadPictureMetaDataFromXml(10);
//		System.out.println(testphoto2.toString());
		
	}
}
