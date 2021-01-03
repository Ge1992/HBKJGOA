package com.example.hbkjgoa.model;

import java.io.Serializable;

public class SpinnerData implements Serializable {
	private String value = "";
	private String text = "";
	private String mrspr="";
	private String Spms="";
	private String Psms="";



	public SpinnerData(String _value, String _text, String _mrspr, String _Spms, String _Psms) {
		value = _value;
		text = _text;
		mrspr=_mrspr;
		Spms=_Spms;
		Psms=_Psms;
	}

	@Override
	public String toString() {

		return text;
	}

	public String getValue() {
		return value;
	}

	public String getText() {
		return text;
	}

	public String getMrspr() {
		return mrspr;
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

	public void setMrspr(String mrspr) {
		this.mrspr = mrspr;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setText(String text) {
		this.text = text;
	}

}
