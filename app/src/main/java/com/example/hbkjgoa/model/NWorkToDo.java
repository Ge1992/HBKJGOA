package com.example.hbkjgoa.model;

import java.io.Serializable;
import java.util.List;

public class NWorkToDo implements Serializable {
	private String BeiYong1;
	private Integer ID;
	private String workname;
	private Integer formid;
	private Integer flowid;
	private String usernameString;
	private String timestr;
	private String fromContentString;
	private String fujianString;
	private String shenpiyjString;
	private Integer jiedianid;
	private String jiedianNameString;
	private String shenpiUserString;
	private String okuserlist;
	private String stateNowString;
	private String latetimeString;
	private String wenhaoString;
	private String beiyong1;
	private String beiyong2;
	private String PSMS;
	private String CS;
	private String JieDianName;
	private String ShenPiUserList;
	
	private String WorkName;
	private String ShenPiUserListString;

	private String Values;
	private String Spms;
	private Integer Psms;
	private Integer Mrspr;
	private String Texts;

	//log

	public String    FId;
	public String    EditUser;
	public String    YNR;
	public String    NNR;
	public String    zd1;
	public String    zd2;
	public String    zd3;
	public String    edittime;




	public String getFId() {
		return FId;
	}

	public void setFId(String FId) {
		this.FId = FId;
	}

	public String getEditUser() {
		return EditUser;
	}

	public void setEditUser(String editUser) {
		EditUser = editUser;
	}

	public String getYNR() {
		return YNR;
	}

	public void setYNR(String YNR) {
		this.YNR = YNR;
	}

	public String getNNR() {
		return NNR;
	}

	public void setNNR(String NNR) {
		this.NNR = NNR;
	}

	public String getZd1() {
		return zd1;
	}

	public void setZd1(String zd1) {
		this.zd1 = zd1;
	}

	public String getZd2() {
		return zd2;
	}

	public void setZd2(String zd2) {
		this.zd2 = zd2;
	}

	public String getZd3() {
		return zd3;
	}

	public void setZd3(String zd3) {
		this.zd3 = zd3;
	}

	public String getEdittime() {
		return edittime;
	}

	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}

	public String getBeiYong1() {
		return BeiYong1;
	}

	public void setBeiYong1(String beiYong1) {
		BeiYong1 = beiYong1;
	}

	public String getValues() {
		return Values;
	}

	public void setValues(String values) {
		Values = values;
	}

	public String getSpms() {
		return Spms;
	}

	public void setSpms(String spms) {
		Spms = spms;
	}

	public Integer getPsms() {
		return Psms;
	}

	public void setPsms(Integer psms) {
		Psms = psms;
	}

	public Integer getMrspr() {
		return Mrspr;
	}

	public void setMrspr(Integer mrspr) {
		Mrspr = mrspr;
	}

	public String getTexts() {
		return Texts;
	}

	public void setTexts(String texts) {
		Texts = texts;
	}

	public String getShenPiUserListString() {
		return ShenPiUserListString;
	}
	public void setShenPiUserListString(String shenPiUserListString) {
		ShenPiUserListString = shenPiUserListString;
	}
	public String getWorkName() {
		return WorkName;
	}
	public void setWorkName(String workName) {
		WorkName = workName;
	}
	public String getPSMS() {
		return PSMS;
	}
	public void setPSMS(String pSMS) {
		PSMS = pSMS;
	}
	public String getCS() {
		return CS;
	}
	public void setCS(String cS) {
		CS = cS;
	}
	public String getShenPiUserList() {
		return ShenPiUserList;
	}
	public void setShenPiUserList(String shenPiUserList) {
		ShenPiUserList = shenPiUserList;
	}
	public String getJieDianName() {
		return JieDianName;
	}
	public void setJieDianName(String jieDianName) {
		JieDianName = jieDianName;
	}
	public void setFormid(Integer formid) {
		this.formid = formid;
	}
	public void setJiedianid(Integer jiedianid) {
		this.jiedianid = jiedianid;
	}
	public Integer getFlowid() {
		return flowid;
	}
	public void setFlowid(Integer flowid) {
		this.flowid = flowid;
	}
	
	
	public String getSSGS() {
		return SSGS;
	}
	public void setSSGS(String sSGS) {
		SSGS = sSGS;
	}
	private String SSGS;
	
	private List<WorkFlowItem> nodeList;
	public List<WorkFlowItem> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<WorkFlowItem> nodeList) {
		this.nodeList = nodeList;
	}

	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getWorkname() {
		return workname;
	}
	public void setWorkname(String workname) {
		this.workname = workname;
	}
	public int getFormid() {
		return formid;
	}
	public void setFormid(int formid) {
		this.formid = formid;
	}
	public String getUsernameString() {
		return usernameString;
	}
	public void setUsernameString(String usernameString) {
		this.usernameString = usernameString;
	}
	public String getTimestr() {
		return timestr;
	}
	public void setTimestr(String timestr) {
		this.timestr = timestr;
	}
	public String getFromContentString() {
		return fromContentString;
	}
	public void setFromContentString(String fromContentString) {
		this.fromContentString = fromContentString;
	}
	public String getFujianString() {
		return fujianString;
	}
	public void setFujianString(String fujianString) {
		this.fujianString = fujianString;
	}
	public String getShenpiyjString() {
		return shenpiyjString;
	}
	public void setShenpiyjString(String shenpiyjString) {
		this.shenpiyjString = shenpiyjString;
	}
	public int getJiedianid() {
		return jiedianid;
	}
	public void setJiedianid(int jiedianid) {
		this.jiedianid = jiedianid;
	}
	public String getJiedianNameString() {
		return jiedianNameString;
	}
	public void setJiedianNameString(String jiedianNameString) {
		this.jiedianNameString = jiedianNameString;
	}
	public String getShenpiUserString() {
		return shenpiUserString;
	}
	public void setShenpiUserString(String shenpiUserString) {
		this.shenpiUserString = shenpiUserString;
	}
	public String getOkuserlist() {
		return okuserlist;
	}
	public void setOkuserlist(String okuserlist) {
		this.okuserlist = okuserlist;
	}
	public String getStateNowString() {
		return stateNowString;
	}
	public void setStateNowString(String stateNowString) {
		this.stateNowString = stateNowString;
	}
	public String getLatetimeString() {
		return latetimeString;
	}
	public void setLatetimeString(String latetimeString) {
		this.latetimeString = latetimeString;
	}
	public String getWenhaoString() {
		return wenhaoString;
	}
	public void setWenhaoString(String wenhaoString) {
		this.wenhaoString = wenhaoString;
	}
	public String getBeiyong1() {
		return beiyong1;
	}
	public void setBeiyong1(String beiyong1) {
		this.beiyong1 = beiyong1;
	}
	public String getBeiyong2() {
		return beiyong2;
	}
	public void setBeiyong2(String beiyong2) {
		this.beiyong2 = beiyong2;
	}

}
