package com.example.hbkjgoa.model;
import java.io.Serializable;
import java.util.List;


public class TXL implements Serializable {

	private String id;
	private String MC;
	private String count;
	private String EmailStr;

	private String UserName;
	private String TrueName;
	private String JiaTingDianHua;


	private int ID;
	private String FID;
	private String BGSHM;
	private String FL;
	private String ZFHS;
	private String SSBM;
	private String ZW;
	private String SJHM;


	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getFID() {
		return FID;
	}

	public void setFID(String FID) {
		this.FID = FID;
	}

	public String getBGSHM() {
		return BGSHM;
	}

	public void setBGSHM(String BGSHM) {
		this.BGSHM = BGSHM;
	}

	public String getFL() {
		return FL;
	}

	public void setFL(String FL) {
		this.FL = FL;
	}

	public String getZFHS() {
		return ZFHS;
	}

	public void setZFHS(String ZFHS) {
		this.ZFHS = ZFHS;
	}

	public String getSSBM() {
		return SSBM;
	}

	public void setSSBM(String SSBM) {
		this.SSBM = SSBM;
	}

	public String getZW() {
		return ZW;
	}

	public void setZW(String ZW) {
		this.ZW = ZW;
	}

	public String getSJHM() {
		return SJHM;
	}

	public void setSJHM(String SJHM) {
		this.SJHM = SJHM;
	}

	private List<TXL2> txl_list ;

	public List<TXL2> getTxl_list() {
		return txl_list;
	}

	public void setTxl_list(List<TXL2> txl_list) {
		this.txl_list = txl_list;
	}

	public String getEmailStr() {
		return EmailStr;
	}

	public void setEmailStr(String emailStr) {
		EmailStr = emailStr;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMC() {
		return MC;
	}
	public void setMC(String mC) {
		MC = mC;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getTrueName() {
		return TrueName;
	}
	public void setTrueName(String trueName) {
		TrueName = trueName;
	}
	public String getJiaTingDianHua() {
		return JiaTingDianHua;
	}
	public void setJiaTingDianHua(String jiaTingDianHua) {
		JiaTingDianHua = jiaTingDianHua;
	}









}
