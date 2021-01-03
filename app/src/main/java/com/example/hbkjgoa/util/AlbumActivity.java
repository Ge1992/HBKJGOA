/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.example.hbkjgoa.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.hbkjgoa.R;
import com.example.localphotodemo.bean.AlbumInfo;
import com.example.localphotodemo.bean.PhotoInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


/**
 * 调用相册界面
 * @author Alan
 * 2013-11-15
 */
public class AlbumActivity extends Activity implements LoaderCallbacks<Cursor> {
	
	private GridView gridView;              //控件
	private ArrayList<String> dataList = new ArrayList<String>();                 //�?????有数�?????
	private HashMap<String,ImageView> hashMap = new HashMap<String,ImageView>();  //图片键�??
	private ArrayList<String> selectedDataList = new ArrayList<String>();         //被�?�中的数据组
	private String cameraDir = "/DCIM/";   //指定文件路径
	private String cameraDir2 = "/";   //指定文件路径
	//private String cameraDir = "/";   //指定文件路径
	private ProgressBar progressBar;       //进度�?????
	private AlbumGridViewAdapter gridImageAdapter;  //适配�?????
	private LinearLayout selectedImageLayout;       //底部布局
	private Button okButton;
	private HorizontalScrollView scrollview;        //滚动组件
	private static final String[] STORE_IMAGES = {
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaStore.Images.Media.LATITUDE,
        MediaStore.Images.Media.LONGITUDE,
        MediaStore.Images.Media._ID
    };
	private ContentResolver cr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		selectedDataList = (ArrayList<String>)bundle.getSerializable("dataList");  //获取上个窗体转过来的数据�?????
		initStatusBar();
		init();  //实例化控�?????
		initListener();  //点击事件
		
	}

	/**
	 * 说明：
	 * 1. SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，但状态栏不会被隐藏覆盖。
	 * 2. SYSTEM_UI_FLAG_LIGHT_STATUS_BAR：设置状态栏图标为黑色或者白色
	 * 3. StatusBarUtil 工具类是修改状态栏的颜色为白色。
	 */
	private void initStatusBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
			//    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
			StatusBarUtils.setStatusBarColor(AlbumActivity.this, R.color.main_title_bg);
		}
	}

	private void init() {
		
		progressBar = (ProgressBar)findViewById(R.id.progressbar);
		progressBar.setVisibility(View.GONE);
		gridView = (GridView)findViewById(R.id.myGrid);
		gridImageAdapter = new AlbumGridViewAdapter(AlbumActivity.this, dataList,selectedDataList);
		gridView.setAdapter(gridImageAdapter);
		cr = AlbumActivity.this.getContentResolver(); 
		refreshData(); //执行异步后台操作
		selectedImageLayout = (LinearLayout)findViewById(R.id.selected_image_layout);
		okButton = (Button)findViewById(R.id.ok_button);
		scrollview = (HorizontalScrollView)findViewById(R.id.scrollview);
		
		initSelectImage();
		
	}

	private void initSelectImage() {
		if(selectedDataList==null)
			return;
		for(final String path:selectedDataList){
			ImageView imageView = (ImageView) LayoutInflater.from(AlbumActivity.this).inflate(R.layout.choose_imageview, selectedImageLayout,false);
			selectedImageLayout.addView(imageView);			
			hashMap.put(path, imageView);
			ImageManager2.from(AlbumActivity.this).displayImage(imageView, path,R.mipmap.camera_default,100,100);  //被�?�中的状�?????
			imageView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					removePath(path);
					gridImageAdapter.notifyDataSetChanged();
				}
			});
		}
		okButton.setText("完成("+selectedDataList.size()+"/6)");
	}

	private void initListener() {
		
		gridImageAdapter.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(final ToggleButton toggleButton, int position, final String path, boolean isChecked) {
				
				if(selectedDataList.size()>=6){
					toggleButton.setChecked(false);
					if(!removePath(path)){
						Toast.makeText(AlbumActivity.this, "只能选择6张图", Toast.LENGTH_LONG).show();
					}
					return;
				}
					
				if(isChecked){
					if(!hashMap.containsKey(path)){
						ImageView imageView = (ImageView) LayoutInflater.from(AlbumActivity.this).inflate(R.layout.choose_imageview, selectedImageLayout,false);
						selectedImageLayout.addView(imageView);
						imageView.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								
								int off = selectedImageLayout.getMeasuredWidth() - scrollview.getWidth();  
							    if (off > 0) {  
							    	  scrollview.smoothScrollTo(off, 0); 
							    } 
								
							}
						}, 100);
						
						hashMap.put(path, imageView);
						selectedDataList.add(path);
						ImageManager2.from(AlbumActivity.this).displayImage(imageView, path,R.mipmap.camera_default,100,100);
						imageView.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								toggleButton.setChecked(false);
								removePath(path);
								
							}
						});
						okButton.setText("完成("+selectedDataList.size()+"/6)");
					}
				}else{
					removePath(path);
				}
				
				
				
			}
		});
		
		okButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				// intent.putArrayListExtra("dataList", dataList);
				bundle.putStringArrayList("dataList",selectedDataList);
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
				
			}
		});
		
	}
	
	private boolean removePath(String path){
		if(hashMap.containsKey(path)){
			selectedImageLayout.removeView(hashMap.get(path));
			hashMap.remove(path);
			removeOneData(selectedDataList,path);
			okButton.setText("完成("+selectedDataList.size()+"/6)");
			return true;
		}else{
			return false;
		}
	}
	
	
	private void removeOneData(ArrayList<String> arrayList, String s){
		for(int i =0;i<arrayList.size();i++){
			if(arrayList.get(i).equals(s)){
				arrayList.remove(i);
				return;
			}
		}
	}
	
	/**
	 * 执行异步后台操作
	 */
    @SuppressLint("StaticFieldLeak")
	private void refreshData(){
    	
    	new AsyncTask<Void, Void, ArrayList<String>>(){
    		
    		@Override
    		protected void onPreExecute() {
    			progressBar.setVisibility(View.VISIBLE);
    			super.onPreExecute();
    		}

			@Override
			protected ArrayList<String> doInBackground(Void... params) {
				ArrayList<String> tmpList = new ArrayList<String>();
				
				/*ArrayList<String> listDirlocal =  listAlldir(new File(cameraDir));  //指定读取文件�?????
                ArrayList<String> listDiranjuke = new ArrayList<String>();
                listDiranjuke.addAll(listDirlocal);
                
                for (int i = 0; i < listDiranjuke.size(); i++){
                    listAllfile(new File( listDiranjuke.get(i) ),tmpList);
                }*/
                
                Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, "date_modified DESC");

    			String _path="_data";
    			String _album="bucket_display_name";

    			HashMap<String, AlbumInfo> myhash = new HashMap<String, AlbumInfo>();
    			AlbumInfo albumInfo = null;
    			PhotoInfo photoInfo = null;
    			if (cursor!=null&&cursor.moveToFirst())
    			{
    				do{  
    					int index = 0;
    					int _id = cursor.getInt(cursor.getColumnIndex("_id")); 
    					String path = cursor.getString(cursor.getColumnIndex(_path));
    					String album = cursor.getString(cursor.getColumnIndex(_album));
    					
    					tmpList.add(path);
    				}while (cursor.moveToNext());
    			}
                
				return tmpList;
				
			}
			
			protected void onPostExecute(ArrayList<String> tmpList) {
				
				if(AlbumActivity.this==null||AlbumActivity.this.isFinishing()){
					return;
				}
				progressBar.setVisibility(View.GONE);
				dataList.clear();
				dataList.addAll(tmpList);
				gridImageAdapter.notifyDataSetChanged();
				return;
				
			};
    		
    	}.execute();
    	
    }
    /**
     * 指定读取文件夹下的所有文件夹
     * @param nowDir
     * @return
     */
    private ArrayList<String> listAlldir(File nowDir){
        ArrayList<String> listDir = new ArrayList<String>();
        nowDir = new File(Environment.getExternalStorageDirectory() + nowDir.getPath());
        if(!nowDir.isDirectory()){  //判断是不是文件夹类型
            return listDir;
        }
                
        File[] files = nowDir.listFiles();

        for (int i = 0; i < files.length; i++){
            if(files[i].getName().substring(0,1).equals(".")){
               continue; 
            }
            File file = new File(files[i].getPath());
            if(file.isDirectory()){
                listDir.add(files[i].getPath());
            }
        }              
        return listDir;
    }
    /**
     * 获取全部的符合规定的文件格式的文件路�?????
     * @param baseFile 根文件夹
     * @param tmpList 保存文件
     * @return
     */
    private ArrayList<String> listAllfile(File baseFile, ArrayList<String> tmpList){
        if(baseFile != null && baseFile.exists()){
            File[] file = baseFile.listFiles();
            if(file==null){
            	return tmpList;
            }
            ArrayList<FileInfo> fileList = new ArrayList<FileInfo>();
            for(File f : file){
            	//图片类型
            	if (f.getPath().endsWith(".jpg") /*|| f.getPath().endsWith(".gif") */||f.getPath().endsWith(".png")|| f.getPath().endsWith(".jpeg") ||f.getPath().endsWith(".bmp")) {
            		//tmpList.add(f.getPath());
            		
            		//将需要的子文件信息存入到FileInfo里面
                	FileInfo fileInfo = new FileInfo();
                	fileInfo.path = f.getPath();
                	fileInfo.lastModified= f.lastModified(); 
                	fileList.add(fileInfo);
                	Collections.sort(fileList, new FileComparator());//通过重写Comparator的实现类FileComparator来实现按文件创建时间排序�?????
				}
            }
            for(int i=0;i<fileList.size();i++){
            	tmpList.add(fileList.get(i).path);
            }
        }         
        return tmpList;
    }
    
    class FileComparator implements Comparator<FileInfo> {
    	public int compare(FileInfo file1, FileInfo file2) {
    	if(file1.lastModified < file2.lastModified)
    	{
    	return 1;
    	}else
    	{
    	return -1;
    	}
    	}
    	}
    
    @Override
    public void onBackPressed() {
//    	finish();
    	super.onBackPressed();
    }
    
    @Override
    public void finish() {
    	super.finish();
//    	ImageManager2.from(AlbumActivity.this).recycle(dataList);
    }
    
    @Override
    protected void onDestroy() {
    	
    	super.onDestroy();
    }
    
    class FileInfo{
    	private String path;
    	private long lastModified;
    	
    }
    
    public void btn_back(View view){
    	setResult(0);
		finish();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		CursorLoader cursorLoader = new CursorLoader(
                this, 
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                STORE_IMAGES, 
                null, 
                null, 
                null);
        return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
 
}
