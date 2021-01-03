package com.example.hbkjgoa.model;

import java.io.Serializable;

public class CJCX implements Serializable {
	private int ID;

	private String UserName;

	private String TrueName;

	private String TimeStr;

	private int ShiJuanID;

	private String ShiJuanName;

	private String zf;

	private String dndf;

	private String rgdf;
	
	private String DWMC;
	private String SJHM;
	private String TX;
	private String SFZ;

	public String getDWMC() {
		return DWMC;
	}
	public void setDWMC(String dWMC) {
		DWMC = dWMC;
	}
	public String getSJHM() {
		return SJHM;
	}
	public void setSJHM(String sJHM) {
		SJHM = sJHM;
	}
	public String getTX() {
		return TX;
	}
	public void setTX(String tX) {
		TX = tX;
	}
	public String getSFZ() {
		return SFZ;
	}
	public void setSFZ(String sFZ) {
		SFZ = sFZ;
	}
	public void setID(int ID){
	this.ID = ID;
	}
	public int getID(){
	return this.ID;
	}
	public void setUserName(String UserName){
	this.UserName = UserName;
	}
	public String getUserName(){
	return this.UserName;
	}
	public void setTrueName(String TrueName){
	this.TrueName = TrueName;
	}
	public String getTrueName(){
	return this.TrueName;
	}
	public void setTimeStr(String TimeStr){
	this.TimeStr = TimeStr;
	}
	public String getTimeStr(){
	return this.TimeStr;
	}
	public void setShiJuanID(int ShiJuanID){
	this.ShiJuanID = ShiJuanID;
	}
	public int getShiJuanID(){
	return this.ShiJuanID;
	}
	public void setShiJuanName(String ShiJuanName){
	this.ShiJuanName = ShiJuanName;
	}
	public String getShiJuanName(){
	return this.ShiJuanName;
	}
	public void setZf(String zf){
	this.zf = zf;
	}
	public String getZf(){
	return this.zf;
	}
	public void setDndf(String dndf){
	this.dndf = dndf;
	}
	public String getDndf(){
	return this.dndf;
	}
	public void setRgdf(String rgdf){
	this.rgdf = rgdf;
	}
	public String getRgdf(){
	return this.rgdf;
	}


}