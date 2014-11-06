package com.example.tinf13aibi_app_tm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.thoughtworks.xstream.XStream;

public class SaveManager {
	//fixed location we want to save our xml-files / pictures. Might a bit too inflexible..
	private String photoDir;
	private String xmlDir;
	private XStream xstream; //xstream is a lib able to convert entire classes to xml and vice versa. Convenient, isn't it?
	
	public SaveManager() {
	//	this.photoDir = directories[0];
	//	this.xmlDir = directories[1];
		xstream = new XStream();
		xstream.alias("Photo", Photo.class);
		setDirectories();
	}
	
	public void saveEntirePictureData(Photo photo) {
//		savePictureInDir(photo.getId(), picture);
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
		} catch (Exception e) {System.out.println("Während dem Laden ist ein Fehler aufgetreten");}	
		
		return photoList;
	}
	
	private void savePictureInDir(String pictureId, Bitmap picture) {
		try{
		
	    	if(picture != null)
	        {
				String fileName = getPictureFileName(pictureId);
	    	    
				File mediaFile = new File(fileName);

				FileOutputStream fOut = new FileOutputStream(mediaFile);
	    	    picture.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
	    	    fOut.flush();
	    	    fOut.close(); 
	    	}
		}
		catch(Exception e) {
			e.printStackTrace();
	    }
	}
	
	public Bitmap loadPictureWithId(String pictureId) {
		Bitmap result = null;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			result = BitmapFactory.decodeFile(getPictureFileName(pictureId), options);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
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
	
	public String getPictureFileName(String id) {
		return photoDir + File.separator + "Photo_" + id + ".jpg";
	}
	
	private String getXmlFileName(String id) {
		return xmlDir + File.separator +  "Photo_" + id + ".xml";
	}
	
	public String getPhotoDir() {
		return photoDir;
	}
	
	public void deleteEntirePictureData(Photo photo){
		deletePictureInDir(photo);
		deletePictureMetaDataXml(photo);
	}
	
	private void deletePictureInDir(Photo photo){
		File file = new File(photoDir + File.separator + "Photo_" + photo.getId() + ".jpg");
		System.out.println(getPictureFileName(photo.getId()) + " was deleted.");
		file.delete();	
	}
	
	private void deletePictureMetaDataXml(Photo photo){
		File file = new File(xmlDir + File.separator + "Photo_" + photo.getId() + ".xml");
		System.out.println(getXmlFileName(photo.getId()) + " was deleted.");
		file.delete();
	}
	
	public void deleteAllEntirePictureData(){
		deleteAllPictureInDir();
		deleteAllPictureMetaDataXml();
	}
	
	private void deleteAllPictureInDir() {
			File directory = new File(photoDir);
			System.out.println(directory);
			File[] fileList = directory.listFiles();
			for (File file : fileList) {
				file.delete();
			}
	}
	
	private  void deleteAllPictureMetaDataXml() {
		File directory = new File(xmlDir);
		System.out.println(directory);
		File[] fileList = directory.listFiles();
		for (File file : fileList) {
			file.delete();
		}
	}
	
    private void setDirectories() {
    	File photoFolder = new File(Environment.getExternalStorageDirectory() + File.separator + "Photos");
    	File xmlFolder = new File(Environment.getExternalStorageDirectory() + File.separator + "XMLs");
    	createDirIfNotExist(photoFolder);
    	createDirIfNotExist(xmlFolder);
    	photoDir = photoFolder.getAbsolutePath();
    	xmlDir = xmlFolder.getAbsolutePath();
    }
    
    private void createDirIfNotExist(File path) {
    	if (!path.exists()) {
    		path.mkdir();
    	}
    }
}
