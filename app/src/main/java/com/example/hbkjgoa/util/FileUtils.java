package com.example.hbkjgoa.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Base64;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@SuppressLint("NewApi")
public class FileUtils {
	private static String SDPATH=Environment.getExternalStorageDirectory()+ "/";
	public String getSDPATH() {
		return SDPATH; }

	/** * 在SD卡上创建文件 *  * @throws IOException */
	public static File creatSDFile(String fileName) throws IOException {
		File file = new File(SDPATH+fileName);
		file.createNewFile();
		return file; }
	/** * 在SD卡上创建目录 *  * @param dirName */
	public static File creatSDDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir; }
	/** * 判断SD卡上的文件是否存在 */
	public static boolean isFileExist(String fileName){
		File file = new File(SDPATH + fileName);
		return file.exists(); }
	/** * 将一个InputStream里面的数据写入到SD卡中 （这个方法还是有小小的问题的，写HTML时会多写一行数据上去所以我又谢了下面那个方法）*/
	public File write2SDFromInput(String path,String fileName,InputStream input){
		File file = null;
		OutputStream output = null;
		try{
			creatSDDir(path);
			file = creatSDFile(path+fileName);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];

			while((input.read(buffer))!=-1){
				output.write(buffer);
			}
			output.flush();

		}catch(Exception e){ e.printStackTrace(); }
		finally{
			try{
				output.close();
			}catch(Exception e){ e.printStackTrace(); } }
		return file;
	}

	public static void deleteFolderFile(String filePath, boolean deleteThisPath)
			throws IOException {
		//if (!TextUtils.isEmpty(SDPATH + filePath)) {
		File file = new File(filePath);
		if (file.isDirectory()) {
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFolderFile(files[i].getAbsolutePath(), true);
			}
		}
		if (deleteThisPath) {
			if (!file.isDirectory()) {
				file.delete();
			} else {
				if (file.listFiles().length == 0) {
					file.delete();
				}
			}
		}
	}

	public static File write2SDFromInput2(String path,String fileName,String input){
		File file = null;
		OutputStream output = null;
		try{
			creatSDDir(path);
			file = creatSDFile(path+fileName);
			output = new FileOutputStream(file);
			output.write(input.getBytes());
			output.flush();

		}catch(Exception e){ e.printStackTrace(); }
		finally{
			try{
				output.close();
			}catch(Exception e){ e.printStackTrace(); } }
		return file;
	}

	//下载图片时需要解码(2013.5.25)
	public static File write2SDFromInput3(String path,String fileName,String input){
		File file = null;
		OutputStream output = null;
		try{
			creatSDDir(path);
			file=creatSDFile(path+"/"+fileName);
			output = new FileOutputStream(file);

			output.write(Base64.decode(input,1));
			output.flush();

		}catch(Exception e){ e.printStackTrace(); }
		finally{
			try{
				output.close();
			}catch(Exception e){ e.printStackTrace(); } }
		return file;
	}

	public static String saveBitmap(Bitmap bm, String picName) {
		try {
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			File f = new File(SDPATH, picName);
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SDPATH+picName;
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			//System.out.println("createSDDir:" + dir.getAbsolutePath());
			//System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static Bitmap getAtTimeBmp(String paramString)
	{
		MediaMetadataRetriever localMediaMetadataRetriever = new MediaMetadataRetriever();
		File localFile = new File(paramString);
		if (localFile.exists())
		{
			localMediaMetadataRetriever.setDataSource(localFile.getAbsolutePath());
			Bitmap localBitmap = localMediaMetadataRetriever.getFrameAtTime();
			if (localBitmap != null)
				return localBitmap;
			return null;
		}
		return null;
	}

	public static Bitmap combineBitmap(Bitmap paramBitmap1, Bitmap paramBitmap2)
	{
		if (paramBitmap1 == null)
			return null;
		int i = paramBitmap1.getWidth();
		int j = paramBitmap1.getHeight();
		int k = paramBitmap2.getWidth();
		int m = paramBitmap2.getHeight();
		Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
		Canvas localCanvas = new Canvas(localBitmap);
		localCanvas.drawBitmap(paramBitmap1, 0.0F, 0.0F, null);
		localCanvas.drawBitmap(paramBitmap2, (i - k) / 2, (j - m) / 2, null);
		localCanvas.save();
		localCanvas.restore();
		return localBitmap;
	}






}
		
		
