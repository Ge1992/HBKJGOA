package com.example.hbkjgoa.util;


public class FileManager {

	public static String getSaveFilePath() {
		if (CommonUtil.hasSDCard()) {
			return CommonUtil.getRootFilePath() + "com.geniuseoe2012/files/";
		} else {
			return CommonUtil.getRootFilePath() + "com.geniuseoe2012/files";
		}
	}
}
