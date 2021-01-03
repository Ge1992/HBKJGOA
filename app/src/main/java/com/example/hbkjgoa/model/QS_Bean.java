package com.example.hbkjgoa.model;
import java.io.Serializable;


public class QS_Bean implements Serializable {
       private String ID;
       private String userid;
       private String bm;
       private String truename;
       private String readtime;
       private String SFYD;

	public String getSFYD() {
		return SFYD;
	}

	public void setSFYD(String SFYD) {
		this.SFYD = SFYD;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getReadtime() {
		return readtime;
	}

	public void setReadtime(String readtime) {
		this.readtime = readtime;
	}
}
