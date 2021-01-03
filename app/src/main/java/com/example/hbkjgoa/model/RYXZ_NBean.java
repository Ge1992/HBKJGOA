package com.example.hbkjgoa.model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class RYXZ_NBean implements Serializable {
	public String    ID;
	public String    username;
	public String    bumen;
	public String    jiaose;
	public String    gangwei;
	public String    fenzu;
	public String    jp;
	public String    qp;


	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RYXZ_NBean)) return false;
		RYXZ_NBean that = (RYXZ_NBean) o;
		return Objects.equals(getID(), that.getID()) &&
				Objects.equals(getUsername(), that.getUsername()) &&
				Objects.equals(getBumen(), that.getBumen()) &&
				Objects.equals(getJiaose(), that.getJiaose()) &&
				Objects.equals(getGangwei(), that.getGangwei()) &&
				Objects.equals(getFenzu(), that.getFenzu()) &&
				Objects.equals(getJp(), that.getJp()) &&
				Objects.equals(getQp(), that.getQp());
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public int hashCode() {
		return Objects.hash(getID(), getUsername(), getBumen(), getJiaose(), getGangwei(), getFenzu(), getJp(), getQp());
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBumen() {
		return bumen;
	}

	public void setBumen(String bumen) {
		this.bumen = bumen;
	}

	public String getJiaose() {
		return jiaose;
	}

	public void setJiaose(String jiaose) {
		this.jiaose = jiaose;
	}

	public String getGangwei() {
		return gangwei;
	}

	public void setGangwei(String gangwei) {
		this.gangwei = gangwei;
	}

	public String getFenzu() {
		return fenzu;
	}

	public void setFenzu(String fenzu) {
		this.fenzu = fenzu;
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
}
