package com.example.tinf13aibi_app_tm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
	private static final String PICTURE_PREFIX = "Photo_";
	private String photoDir;
	private String xmlDir;
	private XStream xstream;
	
	public SaveManager() {
		xstream = new XStream();
		xstream.alias("Photo", Photo.class);
		setDirectories();
	}
	
	public void saveEntirePictureData(Photo photo) {
		savePictureMetaDataInXml(photo);
	}

	public List<Photo> loadEntirePictureData() {
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
	
	public Bitmap loadPictureWithId(Long pictureId) {
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
	
	public long getUniqueId() {
    	File xmlFolder = new File(xmlDir);
    	long result = 0;
    	if (xmlFolder.listFiles().length > 0) {
    		
    		long highestId = 0;
    		long currentFileId;
    		try {
	    		for (String fileName: xmlFolder.list()) {
	    			
	    			currentFileId = Long.parseLong(fileName.substring(PICTURE_PREFIX.length(), fileName.indexOf(".", PICTURE_PREFIX.length())));
	    			System.out.println(currentFileId);
	    			if (highestId < currentFileId) {
	    				highestId = currentFileId;
	    			}
	    		}
	    		result = highestId + 1;
    		} catch (NumberFormatException e) {
    			e.printStackTrace();
    		}
    	}
    	
    	return result;
    }
	
	public String getNewPictureFileName() {
		return getPictureFileName(getUniqueId());
	}
	
	public String getPictureFileName(long id) {
		return photoDir + File.separator + PICTURE_PREFIX + id + ".jpg";
	}
	
	public void deleteEntirePictureData(Photo photo){
		deletePictureInDir(photo);
		deletePictureMetaDataXml(photo);
	}
	
	public void deleteAllPictureData(){
		deleteAllPictureInDir();
		deleteAllPictureMetaDataXml();
	}
	
	private Photo loadPictureMetaDataFromXml(File file) {
		return (Photo) xstream.fromXML(file); 
	}
	
	private void savePictureMetaDataInXml(Photo photo) {
		 String xml = xstream.toXML(photo);
		 System.out.println(xml);
		 writeXmlToFile(photo.getId(), xml);
	}
	
	private void writeXmlToFile(long id, String xml) {
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
	
	private String getXmlFileName(long id) {
		return xmlDir + File.separator +  PICTURE_PREFIX + id + ".xml";
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
