package com.example.hbkjgoa.model;

import java.io.Serializable;

public class NWork_XJ implements Serializable {
	private String Tiaojian;
	private String Spms;
	private String Psms;
	private String Mrspr;
	private String Texts;
	private String Values;

	public String getTiaojian() {
		return Tiaojian;
	}

	public void setTiaojian(String tiaojian) {
		Tiaojian = tiaojian;
	}

	public String getSpms() {
		return Spms;
	}

	public void setSpms(String spms) {
		Spms = spms;
	}

	public String getPsms() {
		return Psms;
	}

	public void setPsms(String psms) {
		Psms = psms;
	}

	public String getMrspr() {
		return Mrspr;
	}

	public void setMrspr(String mrspr) {
		Mrspr = mrspr;
	}

	public String getTexts() {
		return Texts;
	}

	public void setTexts(String texts) {
		Texts = texts;
	}

	public String getValues() {
		return Values;
	}

	public void setValues(String values) {
		Values = values;
	}
}
