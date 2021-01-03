package com.example.hbkjgoa.model;

import java.io.Serializable;
import java.util.List;

public class CJCX_Beans implements Serializable {
	private int ID;
    private String UserName;
    private String TrueName;
    private String TimeStr;
    private int ShiJuanID;
    private String ShiJuanName;
    private String zf;
    private String dndf;
    private String rgdf;
    private List<TxList> txList;
    public void setID(int ID) {
         this.ID = ID;
     }
     public int getID() {
         return ID;
     }

    public void setUserName(String UserName) {
         this.UserName = UserName;
     }
     public String getUserName() {
         return UserName;
     }

    public void setTrueName(String TrueName) {
         this.TrueName = TrueName;
     }
     public String getTrueName() {
         return TrueName;
     }

    public void setTimeStr(String TimeStr) {
         this.TimeStr = TimeStr;
     }
     public String getTimeStr() {
         return TimeStr;
     }

    public void setShiJuanID(int ShiJuanID) {
         this.ShiJuanID = ShiJuanID;
     }
     public int getShiJuanID() {
         return ShiJuanID;
     }

    public void setShiJuanName(String ShiJuanName) {
         this.ShiJuanName = ShiJuanName;
     }
     public String getShiJuanName() {
         return ShiJuanName;
     }

    public void setZf(String zf) {
         this.zf = zf;
     }
     public String getZf() {
         return zf;
     }

    public void setDndf(String dndf) {
         this.dndf = dndf;
     }
     public String getDndf() {
         return dndf;
     }

    public void setRgdf(String rgdf) {
         this.rgdf = rgdf;
     }
     public String getRgdf() {
         return rgdf;
     }

    public void setTxList(List<TxList> txList) {
         this.txList = txList;
     }
     public List<TxList> getTxList() {
         return txList;
     }



}
