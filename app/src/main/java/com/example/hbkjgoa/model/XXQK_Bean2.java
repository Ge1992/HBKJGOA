package com.example.hbkjgoa.model;

import java.io.Serializable;

public class XXQK_Bean2 implements Serializable {
	private String UserName;
	private String Department;
	private String Score;


	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	public String getScore() {
		return Score;
	}

	public void setScore(String score) {
		Score = score;
	}
}