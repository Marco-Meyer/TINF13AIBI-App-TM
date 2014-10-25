package com.example.tinf13aibi_app_tm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

import com.thoughtworks.xstream.XStream;

public class SaveManager {
	//fixed location we want to save our xml-files / pictures. Might a bit too inflexible..
	private String photoDir;
	private String xmlDir;
	private XStream xstream; //xstream is a lib able to convert entire classes to xml and vice versa. Convenient, isn't it?
	
	public SaveManager(String[] directories) {
		this.photoDir = directories[0];
		this.xmlDir = directories[1];
		xstream = new XStream();
		xstream.alias("Photo", Photo.class);
	}
	
	public void saveEntirePictureData(Photo photo, Bitmap picture) {
		savePictureInDir(photo.getId(), picture);
		savePictureMetaDataInXml(photo);
	}
	
	public List<Photo> loadEntirePictureData() {
		// what happens with broken xml-files? we may need a delete-routine here..somewhere.
		File directory = new File(xmlDir);
		File[] fileList = directory.listFiles();
		ArrayList<Photo> photoList = new ArrayList<Photo>();
		try {
			for (File file : fileList) {
				photoList.add(loadPictureMetaDataFromXml(file));
			}
		} catch (Exception e) {System.out.println("Excepion thrown during loading");}	
		
		return photoList;
	}
	
	private void savePictureInDir(String pictureId, Bitmap picture) {
	try{
		
    	if(picture != null)
        {
			String fileName = "Photo_" + pictureId + ".jpg";
    	    
			File mediaFile = new File(photoDir, fileName);  
    	    
			FileOutputStream fOut = new FileOutputStream(mediaFile);
    	    picture.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
    	    fOut.flush();
    	    fOut.close(); 
    	}
	}
		catch(Exception e)
	    {
			e.printStackTrace();
	    }
	}
	
	private  void savePictureMetaDataInXml(Photo photo) {
		 String xml = xstream.toXML(photo);
		 System.out.println(xml);
		 writeXmlToFile(photo.getId(), xml);
	}
	
	public Photo loadPictureMetaDataFromXml(File file) {
		return (Photo) xstream.fromXML(file); 
	}
	
	private void writeXmlToFile(String id, String xml) {
		BufferedWriter writer = null;
		try{
		    BufferedReader reader = new BufferedReader(new StringReader("<?xml version=\"1.0\"?>\n" + xml));
		    writer = new BufferedWriter(new FileWriter(getXmlFileName(id), true));
		
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
	}
	
	@Deprecated
	private File getFileWithId(String pictureId) {
		return new File(getXmlFileName(pictureId));
	}
	
	private String getXmlFileName(String id) {
		return xmlDir + File.separator +  "Photo_" + id + ".xml";
	}
	
	public static void main(String[] args) {
//		Photo testphoto = new Photo(10, "today", "now", "here");
//		SaveManager sm = new SaveManager();
//		/*sm.savePictureMetaDataInXml(testphoto);*/
//		Photo testphoto2 = sm.loadPictureMetaDataFromXml(10);
//		System.out.println(testphoto2.toString());
		
	}
}
