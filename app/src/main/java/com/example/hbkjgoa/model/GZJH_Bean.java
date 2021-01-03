package com.example.hbkjgoa.model;

import java.io.Serializable;

public class GZJH_Bean implements Serializable {
	public String    ID;
	public String    TitleStr;
	public String    ContentStr;
	public String    FuJianStr;
	public String    UserName;
	public String    CanLookUser;
	public String    TimeStr;

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getTitleStr() {
		return TitleStr;
	}

	public void setTitleStr(String titleStr) {
		TitleStr = titleStr;
	}

	public String getContentStr() {
		return ContentStr;
	}

	public void setContentStr(String contentStr) {
		ContentStr = contentStr;
	}

	public String getFuJianStr() {
		return FuJianStr;
	}

	public void setFuJianStr(String fuJianStr) {
		FuJianStr = fuJianStr;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getCanLookUser() {
		return CanLookUser;
	}

	public void setCanLookUser(String canLookUser) {
		CanLookUser = canLookUser;
	}

	public String getTimeStr() {
		return TimeStr;
	}

	public void setTimeStr(String timeStr) {
		TimeStr = timeStr;
	}
}