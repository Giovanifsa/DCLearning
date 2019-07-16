package ecommerce.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class BinaryData {
	@Id
	private int id;
	
	@Lob
	private byte[] fileData;
	private String fileNameExt;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte[] getFileData() {
		return fileData;
	}
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	public String getFileNameExt() {
		return fileNameExt;
	}
	public void setFileNameExt(String fileNameExt) {
		this.fileNameExt = fileNameExt;
	}
}
