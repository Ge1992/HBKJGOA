package com.example.hbkjgoa.model;
import java.io.Serializable;


@SuppressWarnings("serial")
public class NodeItem implements Serializable {
	private String Tiaojian;

	private String Spms;

	private String Psms;

	private String Mrspr;

	private String Texts;

	private String Values;

	public void setTiaojian(String Tiaojian){
	this.Tiaojian = Tiaojian;
	}
	public String getTiaojian(){
	return this.Tiaojian;
	}
	public void setSpms(String Spms){
	this.Spms = Spms;
	}
	public String getSpms(){
	return this.Spms;
	}
	public void setPsms(String Psms){
	this.Psms = Psms;
	}
	public String getPsms(){
	return this.Psms;
	}
	public void setMrspr(String Mrspr){
	this.Mrspr = Mrspr;
	}
	public String getMrspr(){
	return this.Mrspr;
	}
	public void setTexts(String Texts){
	this.Texts = Texts;
	}
	public String getTexts(){
	return this.Texts;
	}
	public void setValues(String Values){
	this.Values = Values;
	}
	public String getValues(){
	return this.Values;
	}
    
}
