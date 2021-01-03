package com.example.hbkjgoa.model;

import java.io.Serializable;

public class SpinnerData2 implements Serializable {

	private String value = "";
	private String text = "";

	public SpinnerData2() {
		value = "";
		text = "";
	}

	public SpinnerData2(String _value, String _text) {
		value = _value;
		text = _text;
	}

	@Override
	public String toString() {
		return "SpinnerData2{" +
				"value='" + value + '\'' +
				", text='" + text + '\'' +
				'}';
	}

	public String getValue() {
		return value;
	}

	public String getText() {
		return text;
	}


}
