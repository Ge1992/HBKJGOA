package com.example.hbkjgoa.model;

import java.io.Serializable;

public class WorkFlowItem implements Serializable {
private String valueString;
private String textString;
private String tijaoString;
private String psmsString;
private String spmsString;
private String mrsprString;
private String ShenPiUserListString;




public String getShenPiUserListString() {
	return ShenPiUserListString;
}
public void setShenPiUserListString(String shenPiUserListString) {
	ShenPiUserListString = shenPiUserListString;
}
private String JieDianNameString;

public String getJieDianNameString() {
	return JieDianNameString;
}
public void setJieDianNameString(String jieDianNameString) {
	JieDianNameString = jieDianNameString;
}
public String getValueString() {
	return valueString;
}
public void setValueString(String valueString) {
	this.valueString = valueString;
}
public String getTextString() {
	return textString;
}
public void setTextString(String textString) {
	this.textString = textString;
}
public String getTijaoString() {
	return tijaoString;
}
public void setTijaoString(String tijaoString) {
	this.tijaoString = tijaoString;
}
public String getPsmsString() {
	return psmsString;
}
public void setPsmsString(String psmsString) {
	this.psmsString = psmsString;
}
public String getSpmsString() {
	return spmsString;
}
public void setSpmsString(String spmsString) {
	this.spmsString = spmsString;
}
public String getMrsprString() {
	return mrsprString;
}
public void setMrsprString(String mrsprString) {
	this.mrsprString = mrsprString;
}
}
