package com.example.hbkjgoa.model;

import java.io.Serializable;

public class XXQK_Bean implements Serializable {

	private String ShiJuanTitle;

	private String ShiJuanID;

	private String CKRS;


	public String getShiJuanTitle() {
		return ShiJuanTitle;
	}

	public void setShiJuanTitle(String shiJuanTitle) {
		ShiJuanTitle = shiJuanTitle;
	}

	public String getShiJuanID() {
		return ShiJuanID;
	}

	public void setShiJuanID(String shiJuanID) {
		ShiJuanID = shiJuanID;
	}

	public String getCKRS() {
		return CKRS;
	}

	public void setCKRS(String CKRS) {
		this.CKRS = CKRS;
	}
}