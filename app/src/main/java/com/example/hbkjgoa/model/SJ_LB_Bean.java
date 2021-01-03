package com.example.hbkjgoa.model;

import java.io.Serializable;

public class SJ_LB_Bean implements Serializable {
	private int ID;

	private String ShiJuanTitle;

	private String IFSuiJiChuTi;

	private String FenLeiShunXu;

	private int KaoShiXianShi;

	private double PanDuanFenShu;

	private double DanXuanFenShu;

	private double DuoXuanFenShu;

	private double TianKongFenShu;

	private double JianDaFenShu;

	public void setID(int ID){
	this.ID = ID;
	}
	public int getID(){
	return this.ID;
	}
	public void setShiJuanTitle(String ShiJuanTitle){
	this.ShiJuanTitle = ShiJuanTitle;
	}
	public String getShiJuanTitle(){
	return this.ShiJuanTitle;
	}
	public void setIFSuiJiChuTi(String IFSuiJiChuTi){
	this.IFSuiJiChuTi = IFSuiJiChuTi;
	}
	public String getIFSuiJiChuTi(){
	return this.IFSuiJiChuTi;
	}
	public void setFenLeiShunXu(String FenLeiShunXu){
	this.FenLeiShunXu = FenLeiShunXu;
	}
	public String getFenLeiShunXu(){
	return this.FenLeiShunXu;
	}
	public void setKaoShiXianShi(int KaoShiXianShi){
	this.KaoShiXianShi = KaoShiXianShi;
	}
	public int getKaoShiXianShi(){
	return this.KaoShiXianShi;
	}
	public void setPanDuanFenShu(double PanDuanFenShu){
	this.PanDuanFenShu = PanDuanFenShu;
	}
	public double getPanDuanFenShu(){
	return this.PanDuanFenShu;
	}
	public void setDanXuanFenShu(double DanXuanFenShu){
	this.DanXuanFenShu = DanXuanFenShu;
	}
	public double getDanXuanFenShu(){
	return this.DanXuanFenShu;
	}
	public void setDuoXuanFenShu(double DuoXuanFenShu){
	this.DuoXuanFenShu = DuoXuanFenShu;
	}
	public double getDuoXuanFenShu(){
	return this.DuoXuanFenShu;
	}
	public void setTianKongFenShu(double TianKongFenShu){
	this.TianKongFenShu = TianKongFenShu;
	}
	public double getTianKongFenShu(){
	return this.TianKongFenShu;
	}
	public void setJianDaFenShu(double JianDaFenShu){
	this.JianDaFenShu = JianDaFenShu;
	}
	public double getJianDaFenShu(){
	return this.JianDaFenShu;
	}

}
