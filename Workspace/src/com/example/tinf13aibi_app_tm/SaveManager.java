package com.example.tinf13aibi_app_tm;

import java.io.File;
import java.util.Date;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class SaveManager {
	//fixed location we want to save our xml-files / pictures. Might a bit too inflexible..
	private static final String directory = "";
	private XStream xstream; //xstream is a lib able to convert entire classes to xml and vice versa. Convenient, isn't it?
	
	public SaveManager() {
		xstream = new XStream(new StaxDriver());
		xstream.alias("Photo", Photo.class);
	}
	
	
	//public void savePictureInDir(long pictureId, Bitmap picture) {
		
	//}
	
	public void savePictureMetaDataInXml(Photo photo) {
		 String xml = xstream.toXML(photo);
		 System.out.println(xml);
		 writeXmlToFile(photo.getId(), xml);
	}
	
	public Photo loadPictureMetaDataInXml(long pictureId) {
		return (Photo) xstream.fromXML(getFileWithId(pictureId)); 
	}
	
	private void writeXmlToFile(long id, String xml) {
		//well... new File(StringToXml(xml)) maybe?..xD
	}
	
	private File getFileWithId(long pictureId) {
		// we have to search our dir and find the associated file here
		return new File("file");
	}
	
	public static void main(String[] args) {
		Photo testphoto = new Photo(10, "today", "now", "here");
		SaveManager sm = new SaveManager();
		sm.savePictureMetaDataInXml(testphoto);
	}
}
