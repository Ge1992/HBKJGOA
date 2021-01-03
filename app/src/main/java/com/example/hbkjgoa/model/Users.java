package com.example.hbkjgoa.model;

import java.io.Serializable;

public class Users implements Serializable {
private String id;
private String useridString;
private String usernameString;
private String truename;
private String department;
private String jiaose;
//
private String ID;
private String username;
private String jp;
private String qp;

	private boolean isChecked; //每条item的状态
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean checked) {
		isChecked = checked;
	}

public String getID() {
	return ID;
}
public void setID(String iD) {
	ID = iD;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getJp() {
	return jp;
}
public void setJp(String jp) {
	this.jp = jp;
}
public String getQp() {
	return qp;
}
public void setQp(String qp) {
	this.qp = qp;
}
//
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getUseridString() {
	return useridString;
}
public void setUseridString(String useridString) {
	this.useridString = useridString;
}
public String getUsernameString() {
	return usernameString;
}
public void setUsernameString(String usernameString) {
	this.usernameString = usernameString;
}
public String getTruename() {
	return truename;
}
public void setTruename(String truename) {
	this.truename = truename;
}
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}
public String getJiaose() {
	return jiaose;
}
public void setJiaose(String jiaose) {
	this.jiaose = jiaose;
}
}
