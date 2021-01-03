package com.example.hbkjgoa.model;

import java.io.Serializable;

public class DCWJ_Bean implements Serializable {
	public String    id;
	public String    WenJuanName;
	public String    Remark;
	public String    StartDate;
	public String    EndDate;
	public String    AddTime;
	public String    TjUserId;
	public String    IsOver;
	public String    IsParticipate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWenJuanName() {
		return WenJuanName;
	}

	public void setWenJuanName(String wenJuanName) {
		WenJuanName = wenJuanName;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(String startDate) {
		StartDate = startDate;
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		EndDate = endDate;
	}

	public String getAddTime() {
		return AddTime;
	}

	public void setAddTime(String addTime) {
		AddTime = addTime;
	}

	public String getTjUserId() {
		return TjUserId;
	}

	public void setTjUserId(String tjUserId) {
		TjUserId = tjUserId;
	}

	public String getIsOver() {
		return IsOver;
	}

	public void setIsOver(String isOver) {
		IsOver = isOver;
	}

	public String getIsParticipate() {
		return IsParticipate;
	}

	public void setIsParticipate(String isParticipate) {
		IsParticipate = isParticipate;
	}
}
