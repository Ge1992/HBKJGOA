package com.example.hbkjgoa.model;

import java.io.Serializable;

public class KSSJ_LB_Bean implements Serializable{

	private int txxh;
    private int ID;
    private String TitleStr;
    private String ItemsA;
    private String ItemsB;
    private String ItemsC;
    private String ItemsD;
    private String ItemsE;

    private String ItemsStr="";


    public String getItemsStr() {
		return ItemsStr;
	}
	public void setItemsStr(String itemsStr) {
		ItemsStr = itemsStr;
	}
	private String ItemsF;
    private String ItemsG;
    private String ItemsH;
    private int TiKuID;
    private String AnswerStr;
    private String FenLeiStr;
    public void setTxxh(int txxh) {
         this.txxh = txxh;
     }
     public int getTxxh() {
         return txxh;
     }

    public void setID(int ID) {
         this.ID = ID;
     }
     public int getID() {
         return ID;
     }

    public void setTitleStr(String TitleStr) {
         this.TitleStr = TitleStr;
     }
     public String getTitleStr() {
         return TitleStr;
     }

    public void setItemsA(String ItemsA) {
         this.ItemsA = ItemsA;
     }
     public String getItemsA() {
         return ItemsA;
     }

    public void setItemsB(String ItemsB) {
         this.ItemsB = ItemsB;
     }
     public String getItemsB() {
         return ItemsB;
     }

    public void setItemsC(String ItemsC) {
         this.ItemsC = ItemsC;
     }
     public String getItemsC() {
         return ItemsC;
     }

    public void setItemsD(String ItemsD) {
         this.ItemsD = ItemsD;
     }
     public String getItemsD() {
         return ItemsD;
     }

    public void setItemsE(String ItemsE) {
         this.ItemsE = ItemsE;
     }
     public String getItemsE() {
         return ItemsE;
     }

    public void setItemsF(String ItemsF) {
         this.ItemsF = ItemsF;
     }
     public String getItemsF() {
         return ItemsF;
     }

    public void setItemsG(String ItemsG) {
         this.ItemsG = ItemsG;
     }
     public String getItemsG() {
         return ItemsG;
     }

    public void setItemsH(String ItemsH) {
         this.ItemsH = ItemsH;
     }
     public String getItemsH() {
         return ItemsH;
     }

    public void setTiKuID(int TiKuID) {
         this.TiKuID = TiKuID;
     }
     public int getTiKuID() {
         return TiKuID;
     }

    public void setAnswerStr(String AnswerStr) {
         this.AnswerStr = AnswerStr;
     }
     public String getAnswerStr() {
         return AnswerStr;
     }

    public void setFenLeiStr(String FenLeiStr) {
         this.FenLeiStr = FenLeiStr;
     }
     public String getFenLeiStr() {
         return FenLeiStr;
     }


}