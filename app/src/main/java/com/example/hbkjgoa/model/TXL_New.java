package com.example.hbkjgoa.model;
import java.io.Serializable;
import java.util.List;


public class TXL_New implements Serializable {
	   private String id;
       private String MC;
       
       private String idString;
       private String MCString;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMC() {
		return MC;
	}
	public void setMC(String mC) {
		MC = mC;
	}
	
	private List<Childlist> childlist;

	public List<Childlist> getChildlist() {
		return childlist;
	}
	public void setChildlist(List<Childlist> childlist) {
		this.childlist = childlist;
	}
	public String getIdString() {
		return idString;
	}
	public void setIdString(String idString) {
		this.idString = idString;
	}
	public String getMCString() {
		return MCString;
	}
	public void setMCString(String mCString) {
		MCString = mCString;
	}
	
       
	
	
	
}
