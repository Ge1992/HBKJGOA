package com.example.hbkjgoa.model;

import java.io.Serializable;

/**
 * 项目联系model
 * @author George
 *
 */
public class Xmjl_sjxBean implements Serializable {
	private String idString;
	private String ShouUser;
	private String EmailTitle;
	private String TimeStr;
	private String EmailContent;
	private String FuJian;
	private String FromUser;
	private String ToUser;
	private String EmailState;
	private String ID;
	private String XMMC;
	private String XMID;
	
	
	
	
	
	
	public String getXMMC() {
		return XMMC;
	}
	public void setXMMC(String xMMC) {
		XMMC = xMMC;
	}
	public String getXMID() {
		return XMID;
	}
	public void setXMID(String xMID) {
		XMID = xMID;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getIdString() {
		return idString;
	}
	public void setIdString(String idString) {
		this.idString = idString;
	}
	public String getShouUser() {
		return ShouUser;
	}
	public void setShouUser(String shouUser) {
		ShouUser = shouUser;
	}
	public String getEmailTitle() {
		return EmailTitle;
	}
	public void setEmailTitle(String emailTitle) {
		EmailTitle = emailTitle;
	}
	public String getTimeStr() {
		return TimeStr;
	}
	public void setTimeStr(String timeStr) {
		TimeStr = timeStr;
	}
	public String getEmailContent() {
		return EmailContent;
	}
	public void setEmailContent(String emailContent) {
		EmailContent = emailContent;
	}
	public String getFuJian() {
		return FuJian;
	}
	public void setFuJian(String fuJian) {
		FuJian = fuJian;
	}
	public String getFromUser() {
		return FromUser;
	}
	public void setFromUser(String fromUser) {
		FromUser = fromUser;
	}
	public String getToUser() {
		return ToUser;
	}
	public void setToUser(String toUser) {
		ToUser = toUser;
	}
	public String getEmailState() {
		return EmailState;
	}
	public void setEmailState(String emailState) {
		EmailState = emailState;
	}
	
	
	
}