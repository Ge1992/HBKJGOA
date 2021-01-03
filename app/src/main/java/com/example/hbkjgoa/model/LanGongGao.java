package com.example.hbkjgoa.model;

import java.io.Serializable;
import java.util.List;

public class LanGongGao implements Serializable {
	private Integer ID;
	private String titleString;
	private String usernameString;
	private String userBumenString;
	private String noticeType;
	private String fujianString;
	private String contentString;
	private String typeString;
	private String timeString;
	private String ZT;
	private String SFYY;
	private List<QS_Bean> qd_list ;

	public List<QS_Bean> getQd_list() {
		return qd_list;
	}

	public void setQd_list(List<QS_Bean> qd_list) {
		this.qd_list = qd_list;
	}

	public String getSFYY() {
		return SFYY;
	}

	public void setSFYY(String SFYY) {
		this.SFYY = SFYY;
	}

	public String getZT() {
		return ZT;
	}

	public void setZT(String ZT) {
		this.ZT = ZT;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getTitleString() {
		return titleString;
	}

	public void setTitleString(String titleString) {
		this.titleString = titleString;
	}

	public String getUsernameString() {
		return usernameString;
	}

	public void setUsernameString(String usernameString) {
		this.usernameString = usernameString;
	}

	public String getUserBumenString() {
		return userBumenString;
	}

	public void setUserBumenString(String userBumenString) {
		this.userBumenString = userBumenString;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getFujianString() {
		return fujianString;
	}

	public void setFujianString(String fujianString) {
		this.fujianString = fujianString;
	}

	public String getContentString() {
		return contentString;
	}

	public void setContentString(String contentString) {
		this.contentString = contentString;
	}

	public String getTypeString() {
		return typeString;
	}

	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}
}
