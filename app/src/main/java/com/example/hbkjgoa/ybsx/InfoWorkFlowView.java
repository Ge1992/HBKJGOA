package com.example.hbkjgoa.ybsx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.MyApplication;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.dbsx.Info_DoFlow;
import com.example.hbkjgoa.dbsx.WorkFlowActivity;
import com.example.hbkjgoa.dbsx.ZlglInfo;
import com.example.hbkjgoa.model.NWorkToDo;
import com.example.hbkjgoa.util.FileUtils;
import com.example.hbkjgoa.util.PreferenceUtils;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.UtilStr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




public class InfoWorkFlowView extends Activity {
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KEY_pd==1) {

			
		}else if (KEY_pd==2) {

		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	private int GCXX_ID;
	private int GCXX_FWQID,GCXX_WID;
	private String name;
	private String xm;
	private String lsdw,spname;
	private String YH_LSDQ;
	private Gallery ziliao02_gallery1;
	private ImageView i;
	private WebView ziliaoWebView01;
	private TextView ziliao02_sZLZT;
	private Button ziliao02_up;
	private Button ziliao02_down;
	private Button ziliao_upPic;
	private int iZLID;
	private int ZL_ID;
	private String sNR;
	private String sGCMC;
	private String sZLZT;
	private String json;
	private String json2;
	private ProgressDialog progressDialog;
	private Message message;
	private TextView Text1;
	private String sdcardDir= Environment.getExternalStorageDirectory().toString();
	private int KEY_pd;
	private int currentPosition;
	private int currentPosition2;
	private Bundle bundle;
	private boolean xianshi=false;
	private List<String> listcc;
	private String phototime=null;
	private String json3;
	private String step="";
	private String sWPID;
	private int picdel=1;
	private NWorkToDo email;
	private LocationListener ll;
	private LocationManager locationManager;
	 final Handler myHandler = new Handler();
	private String jwd;
	private String a;
	private String b,next;
	public static InfoWorkFlowView mm;
	private String aaa;
	private Button ziliao02_zj,ziliao02_js;
	private TextView zlcl01_T311,zlcl01_T3;
	private String nr;
	private RelativeLayout bt,ziliao01R;
	public String getNr() {
		return nr;
	}

	public void setNr(String nr) {
		this.nr = nr;
	}

	public void closehere(){
		finish();
	}
	
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){
		@SuppressLint("MissingPermission")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			if(msg.what==1) {
				progressDialog.dismiss();
				if (json!=null&&!json.equals("0")) {
//					json=json.replaceAll("src=\"/Content/","src=\""+WebServiceUtil.getURL()+"Content/");
//					FileUtils.write2SDFromInput2("woyeapp/fileforhtml/",iZLID+".htm",json);
					
					
				}else {
					new AlertDialog.Builder(InfoWorkFlowView.this).setMessage("获取数据失败！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {progressDialog.dismiss();}}).show();
					
				}
			}else if(msg.what==2){
				if(json2==null){
					
				}else if(json2.equals("success")){
					ziliao02_sZLZT.setText("资料状态："+sZLZT);
					if (xianshi) {
						ziliao02_down.setVisibility(View.VISIBLE);
						ziliao02_up.setVisibility(View.VISIBLE);
						picdel=2;
					}
				}
			}else if(msg.what==3){
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,10,ll);
			}	
		}
	};
	
	@Override
	protected void onResume() {
		getpic();
		super.onResume();
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
		        getpic();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	@SuppressLint("NewApi")
	private void toshowhtml() {
		
		ziliaoWebView01.getSettings().setDefaultTextEncodingName("utf-8");		
		ziliaoWebView01.getSettings().setBuiltInZoomControls(true);
		//下面一行在低版本的SDK上不支持
		//ziliaoWebView01.getSettings().setDisplayZoomControls(false);		
		ziliaoWebView01.getSettings().setUseWideViewPort(true);
		ziliaoWebView01.getSettings().setLoadWithOverviewMode(true);
		ziliaoWebView01.getSettings().setJavaScriptEnabled(true);
		
		
		//ziliaoWebView01.loadUrl("file:///"+sdcardDir+"/woyeapp/fileforhtml/"+iZLID+".htm");
		//String date="loadData方法加载本地图片<img src ='file:///"+sdcardDir+"/woyeapp/show/"+"1.jpg' />";
		//date="loadData方法加载本地图片<img src ='288.jpg' />";
		//date="<img  src='http://img.divcss5.com/image-css/adimg/px.png' />";
		Log.d("yin","内容："+sNR);
		//String date1=date.replaceAll("src='/Content/signs","src='Content/signs");
		//ziliaoWebView01.loadDataWithBaseURL("file:///"+sdcardDir+"/woyeapp/fileforhtml/Content/signs/",date,"text/html","utf-8",null);
		//ziliaoWebView01.loadData(sNR, "text/html","utf-8");

		ziliaoWebView01.setWebViewClient(new MyWebViewClient());
		//ziliaoWebView01.loadDataWithBaseURL("",sNR,"text/html","utf-8",null);
		ziliaoWebView01.loadUrl("file:///android_asset/ZLXX.htm");
		ziliaoWebView01.setInitialScale(45);
		//ziliaoWebView01.getSettings().setSupportZoom(true);
		ziliaoWebView01.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		ziliaoWebView01.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		ziliaoWebView01.setWebChromeClient(new WebChromeClient());
		//不显示webview缩放按钮
		ziliaoWebView01.getSettings().setDisplayZoomControls(false);
		//ziliaoWebView01.loadDataWithBaseURL(null,"file:///"+sdcardDir+"/DCIM/Camera/"+"1.htm", "text/html","utf-8", null);		
	}

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.info_workflow);
		mm=this;
		findView();
		getBundle();
		
		
		spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		Text1=(TextView)findViewById(R.id.Text1);
		Text1.setText("欢迎你，"+spname);
		bt=findViewById(R.id.bt);
		ziliao01R=findViewById(R.id.ziliao01R);
		bt.setVisibility(View.GONE);
		ziliao01R.setVisibility(View.GONE);
		toshowhtml();
		getpic();
		setClick();
		initStatusBar();
		String show=getIntent().getStringExtra("show");
	/*	if(show!=null&&show.equals("no")){
			//ziliao02_up.setVisibility(View.GONE);
			
			ziliao02_up.setText("接收记录");
			
			ziliao02_up.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent intent=new Intent(InfoWorkFlowView.this, SPJL_Info.class);
					bundle=new Bundle();
					bundle.putSerializable("NWorkToDo", email);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
			
			ziliao02_sZLZT.setText(sZLZT+"-"+email.getShenpiUserString());
			
		}*/
		ziliao02_up.setVisibility(View.GONE);
		//ziliao02_down.setVisibility(View.GONE);
		ziliao02_down.setText("打印");
		
		
    }
	
	//webview 截屏保存图片
	public void saveImage(View view) {
        Picture picture = ziliaoWebView01.capturePicture();
        Bitmap b = Bitmap.createBitmap(  picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        picture.draw(c);
    	FileUtils.creatSDDir("/DCIM/Camera/资料/");
    //    File file = new File("/资料/" + "page.jpg");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
       File file = new File(Environment.getExternalStorageDirectory(),"/DCIM/Camera/资料/"+"IMG_"+date+".jpg");
       //系统图库刷新
       Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
       Uri uri = Uri.fromFile(file);
       intent.setData(uri);
       sendBroadcast(intent);
        if(file.exists()){
            file.delete();
        }
        
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file.getAbsoluteFile());
            if (fos != null) {
                b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

	
	
	
	
	final class MyWebViewClient extends WebViewClient {

        @Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);   

            return true;   

        }  

        @Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {

            Log.d("WebView","onPageStarted");

            super.onPageStarted(view, url, favicon);

        }    

        @Override
		public void onPageFinished(WebView view, String url) {

            Log.d("WebView","onPageFinished ");

            view.loadUrl("javascript:window.local_obj.showSource();");

            super.onPageFinished(view, url);

        }

    }

     

    final class InJavaScriptLocalObj {
    	@JavascriptInterface
        public void showSource() {
        	  
        	 myHandler.post(new Runnable() {
                 @Override
                 public void run() {
                     // This gets executed on the UI thread so it can safely modify Views
                	 String a= UtilStr.url;
                	 String b=UtilStr.url;
                
                		 ziliaoWebView01.loadUrl("javascript:myFunction3("+GCXX_FWQID+",'"+b+"')");
                		 Log.d("yin","ID:"+GCXX_FWQID);
                	
                 }
             });

        }
    	@JavascriptInterface
        public void showNR(String html) {
     	    Log.d("HTML", html);
     	   PreferenceUtils.putString(MyApplication.context, "html", html);
     	   
     	    email.setFromContentString(html);
	     	sNR=html;
	     	
	     	Intent intent=new Intent(InfoWorkFlowView.this, Info_DoFlow.class);
			putbundles();
			intent.putExtras(bundle);
			startActivity(intent);
     }
    		//打印功能
       	@JavascriptInterface
        public void showNR3(String html) {
       		try {
    			
    			Time t=new Time();
    	        t.setToNow();
 /*   	        int year=t.year;
    	        int month=t.month;
    	        int day=t.monthDay;
    	        int hour=t.hour;
    	        int minute=t.minute;
    	        int second=t.second;
    	        Log.i("mytag", "time==="+ year + month + day+hour+minute+second);
    	        String filename=""+year+month+day+hour+minute+second;
 */		
    			//判断
    			//String path = "/storage/emulated/0/DCIM/mdm.jpg"; //获取到的图片完整路径（例子）
    			String path1 = Environment.getExternalStorageDirectory()+"/"+GCXX_FWQID+".html"; //获取到的图片完整路径（例子）
    			if (!TextUtils.isEmpty(path1)) {
    				File file = new File(path1);
    				if(file.exists()){
    					file.delete();
    					
    					String path = "/sdcard/"+GCXX_FWQID+".html";
    	    			FileOutputStream fos = new FileOutputStream(path, true);
    					OutputStreamWriter osw = new OutputStreamWriter(fos,"gbk");
    	    			StringBuffer htmlString = new StringBuffer();
    	    			String s="<html><head><meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\"/></head><body>";
    	    			s=s+html;
    	    			s=s+"</body></html>";
    	    			htmlString.append(s);
    	    			osw.write(htmlString.toString());
    	    			osw.close();
    	    			
    				}else {
    					String path = "/sdcard/"+GCXX_FWQID+".html";
    	    			FileOutputStream fos = new FileOutputStream(path, true);
    					OutputStreamWriter osw = new OutputStreamWriter(fos,"gbk");
    	    			StringBuffer htmlString = new StringBuffer();
    	    			String s="<html><head><meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\"/></head><body>";
    	    			s=s+html;
    	    			s=s+"</body></html>";
    	    			htmlString.append(s);
    	    			osw.write(htmlString.toString());
    	    			osw.close();
    	    			
					}
    				
    				}
    			
    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (UnsupportedEncodingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
       	 	printer();
     }
      
       	
       	
    	//打印
    	//打印检查记录表
    	/**
    	 * 为了保证模板的可用，最好在现有的模板上复制后修改
    	 */
    	private void printer(){
    		 //现场检查记录
    			final String bbfileur3= Environment.getExternalStorageDirectory()+"/"+GCXX_FWQID+".html";
    			//调用printershare软件来打印该文件
    			File picture = new File(bbfileur3);
    				Uri data_uri= Uri.fromFile(picture);
    		/*	data_type - Mime type. MIME类型如下:
    				"application/pdf"
    				"text/html"
    				"text/plain"
    				"image/png"
    				"image/jpeg"
    				"application/msword" - .doc
    				"application/vnd.openxmlformats-officedocument.wordprocessingml.document" - .docx
    				"application/vnd.ms-excel" - .xls
    				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" - .xlsx
    				"application/vnd.ms-powerpoint" - .ppt
    				"application/vnd.openxmlformats-officedocument.presentationml.presentation" - .pptx
    			*/
    			
    			try {

    			String data_type="text/html";
    			Intent i = new Intent(Intent.ACTION_VIEW);
    			i.setPackage("com.dynamixsoftware.printershare");//未注册之前com.dynamixsoftware.printershare，注册后加上amazon
    			i.setDataAndType(data_uri, data_type);
    			startActivity(i);
    			} catch (Exception e) {
    				//没有找到printershare
    				Toast toast = Toast.makeText(getApplicationContext(),
						     "请先安装PrintShare打印工具！", Toast.LENGTH_LONG);
						   toast.setGravity(Gravity.CENTER, 0, 0);
						   toast.show();
    			}
    	}
          
    }
    
    protected void putbundles() {
		bundle=new Bundle();
		bundle.putSerializable("NWorkToDo", email);
		//bundle.putString("SNR",sNR);
		
	}
	
	private void setClick() {
		ziliao02_up.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ziliaoWebView01.loadUrl("javascript:window.local_obj.showNR('<head>'+" +
		                    "document.getElementById('ZLNR').innerHTML+'</head>');");
				
			}
		});
		
		ziliao02_down.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ziliaoWebView01.loadUrl("javascript:window.local_obj.showNR3('<head>'+" +
						"document.getElementById('ZLNR').innerHTML+'</head>');");
			
			}
		});
		
		
		
		ziliao_upPic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
					String aaString="";
					/**
					 * 
					for(int i=0;i<email.urls.length;i++){
						aaString=aaString+email.urls[i]+",";
					}
					 */
					
					final String fileurl2=aaString;
					
					Intent intent = new Intent();
					intent.setClass(InfoWorkFlowView.this, ZlglInfo.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("NWorkToDo", email);
					intent.putExtras(bundle);
					startActivity(intent);
				
			}
		});
	}
	
	private void getBundle() {
		Intent intent = this.getIntent();
		email=(NWorkToDo)intent.getSerializableExtra("NWorkToDo");
		zlcl01_T3.setText("流程："+email.getBeiyong1());
		next=intent.getStringExtra("next");
		if(next==null||next.equals("")){
			name=email.getUsernameString();
			xm=email.getWorkname();
			lsdw=email.getJiedianNameString();
			currentPosition=email.getJiedianid();
			currentPosition2=email.getFormid();
			KEY_pd=email.getFormid();
			sNR=email.getFromContentString();
			GCXX_ID=email.getFormid();
			sGCMC=email.getWorkname();
			iZLID=email.getID();
			ZL_ID=email.getID();
			sZLZT=email.getJiedianNameString();
		}else{
			sZLZT="";
		}
		GCXX_FWQID=email.getID();
		GCXX_WID=email.getFlowid();
		aaa=email.getFujianString();
		if (email.getFujianString().equals("")) {
			ziliao_upPic.setVisibility(View.GONE);
		}else {
			aaa=email.getFujianString()+"|";
			Log.d("附件", aaa);
			int x=0;  
	        //遍历数组的每个元素    
	        for(int i=0;i<=aaa.length()-1;i++) {  
	            String getstr=aaa.substring(i,i+1);
	       //     Toast.makeText(InfoWorkFlow.this, getstr, 90000).show();
	            if(getstr.equals("|")){  
	                x++;  
	            }  
	        }  
			
		
			ziliao_upPic.setText(Html.fromHtml("附件(<Span style='color:red;' >"+x+"</Span>)"));
		
		
			
	     	if(x!=0){
	     		ziliao_upPic.setVisibility(View.VISIBLE);
	     	}else{
	     		ziliao_upPic.setVisibility(View.GONE);
	     	}
		}
		
	
	
	}
	
	private void findView() {
		ziliaoWebView01=(WebView)findViewById(R.id.ziliaowebView01);
		ziliao02_sZLZT=(TextView)findViewById(R.id.ziliao02_sZLZT);
		ziliao02_sZLZT.setText("资料状态："+sZLZT);
		ziliao02_up=(Button)findViewById(R.id.ziliao02_up);
		ziliao02_down=(Button)findViewById(R.id.ziliao02_down);
		ziliao_upPic=(Button)findViewById(R.id.ziliao_upPic);
		ziliao02_gallery1=(Gallery)findViewById(R.id.ziliao02_gallery1);
		ziliao02_zj = (Button) findViewById(R.id.ziliao02_zj);
		ziliao02_js = (Button) findViewById(R.id.ziliao02_js);
		zlcl01_T3=(TextView)findViewById(R.id.zlcl01_T3);
		ziliao02_down.setVisibility(View.GONE );
		ziliao02_zj.setVisibility(View.GONE );
		ziliao02_js.setVisibility(View.GONE );
	}

   public void btn_back(View v) {     //标题栏 返回按钮
      	this.finish();
      } 
   public void btn_back_send(View v) {     //标题栏 返回按钮
     	this.finish();
     } 
   private void getpic() {
		listcc=getSD();
		if (listcc==null) {
			ziliao02_gallery1.setVisibility(View.GONE);
		}else {
			ziliao02_gallery1.setVisibility(View.VISIBLE);
			ziliao02_gallery1.setAdapter(new ImageAdapter(this,listcc));
			ziliao02_gallery1.setPadding(-230,0,0,0);
			ziliao02_gallery1.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
					final int args=arg2;
					if (picdel==2) {
						new AlertDialog.Builder(InfoWorkFlowView.this).setTitle("请选择")
						.setPositiveButton("查看", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
//								Intent intent = new Intent(InfoWorkFlow.this,MultiTouch.class);
//								intent.putExtra("id",listcc.get(args).toString());
//								startActivity(intent);
							}})
						.setNegativeButton("删除", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								File file01=new File(listcc.get(args).toString());
								if (file01.exists()) {
									file01.delete();
								}
								getpic();
							}})	
						.show();
					}else {
						new AlertDialog.Builder(InfoWorkFlowView.this).setTitle("请选择")
						.setPositiveButton("查看", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
//								Intent intent = new Intent(InfoWorkFlow.this,MultiTouch.class);
//								intent.putExtra("id",listcc.get(args).toString());
//								startActivity(intent);
							}})
						.show();
					}
				}
			});
		}
	}
	
	public class ImageAdapter extends BaseAdapter {
		
		int mGalleryItemBackground;
		private Context mContext;
		private List<String> lis;
		
		public ImageAdapter(Context c, List<String> li){
			mContext=c;
			lis=li;
			//Log.d("yin","看一下设置图片传来的list"+lis.toString());
		}

		@Override
		public int getCount() {
			return lis.size();
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View converView, ViewGroup parent) {
			i=new ImageView(mContext);
			if (lis==null) {
			
			}else {
				Bitmap bitmap = BitmapFactory.decodeFile(lis.get(position).toString());
				//得到缩略图        
				bitmap = ThumbnailUtils.extractThumbnail(bitmap,100,120);
				i.setImageBitmap(bitmap);
				i.setAdjustViewBounds(true);
				i.setLayoutParams(new Gallery.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
						android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
				
			}
			return i;
		}
	}
	
	private List<String> getSD(){
		List<String> it=new ArrayList<String>();
		File f=new File(sdcardDir+"/woyeapp/fileforhtml/"+iZLID+"/");
		if (!f.exists()) {
			//Log.d("yin","1无图片");
			return null;
		}else {
			File[] files=f.listFiles();
			if (files.length==0) {
				//Log.d("yin","2无图片");
				return null;
			}else {
				for(int i=0;i<files.length;i++){
					//Log.d("yin","有图片");
					File file=files[i];
					if(getImageFile(file.getPath()))
						it.add(file.getPath());
				}
				return it;
			}
		}
	}
	@SuppressLint("DefaultLocale")
	private boolean getImageFile(String fName){
		boolean re;
		String end=fName.substring(fName.lastIndexOf(".")+1,fName.length()).toLowerCase();
		if(end.equals("jpg")|| end.equals("gif")|| end.equals("png")|| end.equals("jpeg")|| end.equals("bmp"))
		{
			re=true;
		}else{
			re=false;
		}
		return re;
	}
    
	public void bt_back(View v){
		finish();
	}
	
	public void bt_back2(View v){

		if(WorkFlowActivity.mm!=null){
			WorkFlowActivity.mm.closehere();
		}

		finish();
		
	}

	/**
	 * 说明：
	 * 1. SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，但状态栏不会被隐藏覆盖。
	 * 2. SYSTEM_UI_FLAG_LIGHT_STATUS_BAR：设置状态栏图标为黑色或者白色
	 * 3. StatusBarUtil 工具类是修改状态栏的颜色为白色。
	 */

	private void initStatusBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			//    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
			StatusBarUtils.setStatusBarColor(InfoWorkFlowView.this, R.color.btground);
		}
	}
}



