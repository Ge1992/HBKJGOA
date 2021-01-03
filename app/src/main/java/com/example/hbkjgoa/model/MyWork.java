package com.example.hbkjgoa.model;
import java.io.Serializable;
import java.util.List;


public class MyWork implements Serializable {
	private int ID;

	private String WorkName;

	private List<NodeItem> NodeItems ;

	private int FormID;

	private int WorkFlowID;

	private String UserName;

	private String TimeStr;

	private String FormContent;

	private String FuJianList;

	private String ShenPiYiJian;

	private int JieDianID;

	private String JieDianName;

	private String ShenPiUserList;

	private String OKUserList;

	private String StateNow;

	private String LateTime;

	private String WenHao;

	public void setID(int ID){
	this.ID = ID;
	}
	public int getID(){
	return this.ID;
	}
	public void setWorkName(String WorkName){
	this.WorkName = WorkName;
	}
	public String getWorkName(){
	return this.WorkName;
	}
	public void setNodeItem(List<NodeItem> NodeItem){
	this.NodeItems = NodeItem;
	}
	public List<NodeItem> getNodeItem(){
	return this.NodeItems;
	}
	public void setFormID(int FormID){
	this.FormID = FormID;
	}
	public int getFormID(){
	return this.FormID;
	}
	public void setWorkFlowID(int WorkFlowID){
	this.WorkFlowID = WorkFlowID;
	}
	public int getWorkFlowID(){
	return this.WorkFlowID;
	}
	public void setUserName(String UserName){
	this.UserName = UserName;
	}
	public String getUserName(){
	return this.UserName;
	}
	public void setTimeStr(String TimeStr){
	this.TimeStr = TimeStr;
	}
	public String getTimeStr(){
	return this.TimeStr;
	}
	public void setFormContent(String FormContent){
	this.FormContent = FormContent;
	}
	public String getFormContent(){
	return this.FormContent;
	}
	public void setFuJianList(String FuJianList){
	this.FuJianList = FuJianList;
	}
	public String getFuJianList(){
	return this.FuJianList;
	}
	public void setShenPiYiJian(String ShenPiYiJian){
	this.ShenPiYiJian = ShenPiYiJian;
	}
	public String getShenPiYiJian(){
	return this.ShenPiYiJian;
	}
	public void setJieDianID(int JieDianID){
	this.JieDianID = JieDianID;
	}
	public int getJieDianID(){
	return this.JieDianID;
	}
	public void setJieDianName(String JieDianName){
	this.JieDianName = JieDianName;
	}
	public String getJieDianName(){
	return this.JieDianName;
	}
	public void setShenPiUserList(String ShenPiUserList){
	this.ShenPiUserList = ShenPiUserList;
	}
	public String getShenPiUserList(){
	return this.ShenPiUserList;
	}
	public void setOKUserList(String OKUserList){
	this.OKUserList = OKUserList;
	}
	public String getOKUserList(){
	return this.OKUserList;
	}
	public void setStateNow(String StateNow){
	this.StateNow = StateNow;
	}
	public String getStateNow(){
	return this.StateNow;
	}
	public void setLateTime(String LateTime){
	this.LateTime = LateTime;
	}
	public String getLateTime(){
	return this.LateTime;
	}
	public void setWenHao(String WenHao){
	this.WenHao = WenHao;
	}
	public String getWenHao(){
	return this.WenHao;
	}
}
