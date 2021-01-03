package com.example.hbkjgoa.sqsp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.NWorkToDo;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.UtilStr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class InfoWorkFlow2 extends Activity {

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
	private TextView Text1;
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
	public static InfoWorkFlow2 mm;
	private String id="",wid="";
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

				}else {
					new AlertDialog.Builder(InfoWorkFlow2.this).setMessage("获取数据失败！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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


	private void toshowhtml() {

		ziliaoWebView01.getSettings().setDefaultTextEncodingName("UTF-8");
		ziliaoWebView01.getSettings().setBuiltInZoomControls(true);
		//下面一行在低版本的SDK上不支持
		ziliaoWebView01.getSettings().setUseWideViewPort(true);
		ziliaoWebView01.getSettings().setLoadWithOverviewMode(true);
		ziliaoWebView01.getSettings().setJavaScriptEnabled(true);

		ziliaoWebView01.setWebViewClient(new MyWebViewClient());
		ziliaoWebView01.loadUrl("file:///android_asset/ZLXX.htm");
		ziliaoWebView01.setInitialScale(45);
		ziliaoWebView01.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		ziliaoWebView01.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		ziliaoWebView01.setWebChromeClient(new WebChromeClient());

	}


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.info_workflow);
		mm=this;
		id=this.getIntent().getStringExtra("id");
		wid=this.getIntent().getStringExtra("wid");
		getBundle();

		findView();
		spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		Text1=(TextView)findViewById(R.id.Text1);
		Text1.setText("欢迎你，"+spname);
		toshowhtml();
		getpic();
		setClick();
		initStatusBar();
		String show=getIntent().getStringExtra("show");
		if(show!=null&&show.equals("no")){
			ziliao02_up.setVisibility(View.GONE);
			ziliao02_sZLZT.setText(sZLZT+"-"+email.getShenPiUserList());
		}
		ziliao02_down.setVisibility(View.GONE);
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
					String a= UtilStr.url;
					String b=UtilStr.url;
					if(next==null||next.equals("")){
						ziliaoWebView01.loadUrl("javascript:myFunction("+id+",'"+spname+"','"+b+"')");
						Log.d("yin","ID:"+GCXX_FWQID);
					}else{
						ziliaoWebView01.loadUrl("javascript:myFunction2("+id+",'"+spname+"','"+a+"',"+wid+")");
						Log.d("yin","2ID"+GCXX_FWQID);
					}

				}
			});

        }
    	@JavascriptInterface


			public void showNR(String html) {
				Log.d("HTML", html);
				if(html.equals("1")){

				}else{
					email.setFromContentString(html);
					sNR=html;
					Intent intent=new Intent(InfoWorkFlow2.this,Info_DoFlow2.class);

					putbundles();
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}


    	@JavascriptInterface
        public void CheckBiTians(String html) {
    		if(!html.equals("false")){
				InfoWorkFlow2.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						ziliaoWebView01.loadUrl("javascript:window.local_obj.showNR(''+" +"getZLNR()+'');");
					}
				});
    		}





     }

    }

    protected void putbundles() {
		bundle=new Bundle();
		bundle.putSerializable("NWorkToDo", email);
        bundle.putString("id","75");
        bundle.putString("wid","67");
	}

	private void setClick() {
		ziliao02_up.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ziliaoWebView01.loadUrl("javascript:removefocus();");
				ziliaoWebView01.loadUrl("javascript:window.local_obj.showNR(''+" +"getZLNR()+'');");


			}
		});

		ziliao02_down.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});

	}

	private void getBundle() {
		Intent intent = this.getIntent();
		email=(NWorkToDo)intent.getSerializableExtra("NWorkToDo");
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
			sZLZT=email.getJieDianName();
		}else{
			sZLZT="";
		}
	//	GCXX_FWQID=email.getID();
	//	GCXX_WID=email.getFlowid();

	}

	private void findView() {
		ziliaoWebView01=(WebView)findViewById(R.id.ziliaowebView01);
		ziliao02_sZLZT=(TextView)findViewById(R.id.ziliao02_sZLZT);
		ziliao02_sZLZT.setText("资料状态："+sZLZT);
		ziliao02_up=(Button)findViewById(R.id.ziliao02_up);
		ziliao02_down=(Button)findViewById(R.id.ziliao02_down);
		ziliao02_gallery1=(Gallery)findViewById(R.id.ziliao02_gallery1);
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
						new AlertDialog.Builder(InfoWorkFlow2.this).setTitle("请选择")
						.setPositiveButton("查看", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
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
						new AlertDialog.Builder(InfoWorkFlow2.this).setTitle("请选择")
						.setPositiveButton("查看", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
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
			StatusBarUtils.setStatusBarColor(InfoWorkFlow2.this, R.color.btground);
		}
	}
}



