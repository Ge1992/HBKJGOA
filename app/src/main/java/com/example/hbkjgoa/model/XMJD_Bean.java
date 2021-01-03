package com.example.hbkjgoa.model;

import java.io.Serializable;
import java.util.List;

public class XMJD_Bean implements Serializable {
	public String ID;
	public String ProjectName;
	public String ProjectSerils;
	public String SuoShuKeHu;
	public String YuJiChengJiaoRiQi;
	public String TiXingDate;
	public String FuZeRen;
	public String XiangMuJinE;
	public String XiangMuYuSuan;
	public String XiangMuDaXiao;
	public String PeiHeRenList;
	public String UserName;
	public String TimeStr;
	public String HeTongAndFuJian;
	public String BackInfo;

	private List<projProgress> projList;


	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getProjectName() {
		return ProjectName;
	}

	public void setProjectName(String projectName) {
		ProjectName = projectName;
	}

	public String getProjectSerils() {
		return ProjectSerils;
	}

	public void setProjectSerils(String projectSerils) {
		ProjectSerils = projectSerils;
	}

	public String getSuoShuKeHu() {
		return SuoShuKeHu;
	}

	public void setSuoShuKeHu(String suoShuKeHu) {
		SuoShuKeHu = suoShuKeHu;
	}

	public String getYuJiChengJiaoRiQi() {
		return YuJiChengJiaoRiQi;
	}

	public void setYuJiChengJiaoRiQi(String yuJiChengJiaoRiQi) {
		YuJiChengJiaoRiQi = yuJiChengJiaoRiQi;
	}

	public String getTiXingDate() {
		return TiXingDate;
	}

	public void setTiXingDate(String tiXingDate) {
		TiXingDate = tiXingDate;
	}

	public String getFuZeRen() {
		return FuZeRen;
	}

	public void setFuZeRen(String fuZeRen) {
		FuZeRen = fuZeRen;
	}

	public String getXiangMuJinE() {
		return XiangMuJinE;
	}

	public void setXiangMuJinE(String xiangMuJinE) {
		XiangMuJinE = xiangMuJinE;
	}

	public String getXiangMuYuSuan() {
		return XiangMuYuSuan;
	}

	public void setXiangMuYuSuan(String xiangMuYuSuan) {
		XiangMuYuSuan = xiangMuYuSuan;
	}

	public String getXiangMuDaXiao() {
		return XiangMuDaXiao;
	}

	public void setXiangMuDaXiao(String xiangMuDaXiao) {
		XiangMuDaXiao = xiangMuDaXiao;
	}

	public String getPeiHeRenList() {
		return PeiHeRenList;
	}

	public void setPeiHeRenList(String peiHeRenList) {
		PeiHeRenList = peiHeRenList;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getTimeStr() {
		return TimeStr;
	}

	public void setTimeStr(String timeStr) {
		TimeStr = timeStr;
	}

	public String getHeTongAndFuJian() {
		return HeTongAndFuJian;
	}

	public void setHeTongAndFuJian(String heTongAndFuJian) {
		HeTongAndFuJian = heTongAndFuJian;
	}

	public String getBackInfo() {
		return BackInfo;
	}

	public void setBackInfo(String backInfo) {
		BackInfo = backInfo;
	}

	public List<projProgress> getProjList() {
		return projList;
	}

	public void setProjList(List<projProgress> projList) {
		this.projList = projList;
	}

	public static class projProgress implements Serializable {
		public String TypeID;
		public String TypeName;
		public String FileCount;
		private List<FileList> fileLists;


		public List<FileList> getFileLists() {
			return fileLists;
		}

		public void setFileLists(List<FileList> fileLists) {
			this.fileLists = fileLists;
		}

		public String getTypeID() {
			return TypeID;
		}

		public void setTypeID(String typeID) {
			TypeID = typeID;
		}

		public String getTypeName() {
			return TypeName;
		}

		public void setTypeName(String typeName) {
			TypeName = typeName;
		}

		public String getFileCount() {
			return FileCount;
		}

		public void setFileCount(String fileCount) {
			FileCount = fileCount;
		}


	}

	public static class FileList implements Serializable {
		public String ID;
		public String    ProjectID;
		public String    TypeID;
		public String    FilePath;
		public String    FileName;
		public String    UserName;
		public String    UploadTime;


		public String getID() {
			return ID;
		}

		public void setID(String ID) {
			this.ID = ID;
		}

		public String getProjectID() {
			return ProjectID;
		}

		public void setProjectID(String projectID) {
			ProjectID = projectID;
		}

		public String getTypeID() {
			return TypeID;
		}

		public void setTypeID(String typeID) {
			TypeID = typeID;
		}

		public String getFilePath() {
			return FilePath;
		}

		public void setFilePath(String filePath) {
			FilePath = filePath;
		}

		public String getFileName() {
			return FileName;
		}

		public void setFileName(String fileName) {
			FileName = fileName;
		}

		public String getUserName() {
			return UserName;
		}

		public void setUserName(String userName) {
			UserName = userName;
		}

		public String getUploadTime() {
			return UploadTime;
		}

		public void setUploadTime(String uploadTime) {
			UploadTime = uploadTime;
		}
	}
}