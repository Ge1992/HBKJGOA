package com.example.hbkjgoa.model;

import java.io.Serializable;
import java.util.List;

public class KSSJ_LB_Beans implements Serializable {
	private String xh;
	private String tx;
	private String fz;
	private String sl;
	private String zfz;
	private List<KSSJ_LB_Bean> tilb;

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getXh() {
		return xh;
	}

	public void setTx(String tx) {
		this.tx = tx;
	}

	public String getTx() {
		return tx;
	}

	public void setFz(String fz) {
		this.fz = fz;
	}

	public String getFz() {
		return fz;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}

	public String getSl() {
		return sl;
	}

	public void setZfz(String zfz) {
		this.zfz = zfz;
	}

	public String getZfz() {
		return zfz;
	}

	public void setTilb(List<KSSJ_LB_Bean> tilb) {
		this.tilb = tilb;
	}

	public List<KSSJ_LB_Bean> getTilb() {
		return tilb;
	}

}
