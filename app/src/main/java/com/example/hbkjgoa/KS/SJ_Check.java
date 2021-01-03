package com.example.hbkjgoa.KS;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.KSSJ_LB_Bean;
import com.example.hbkjgoa.model.KSSJ_LB_Beans;
import com.example.hbkjgoa.model.SJ_LB_Bean;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;


import java.util.ArrayList;
import java.util.List;

@SuppressLint({ "SimpleDateFormat", "WorldReadableFiles",
		"WorldWriteableFiles", "HandlerLeak" })
public class SJ_Check extends Activity {

	private boolean isoncl=true;

	private LinearLayout check_1,check_2,check_3,check_4,check_5;
	private Button bt1,bt2,back;
	private ImageView login_check1,login_check2,login_check3,login_check4,login_check5;
	private TextView add,show,zong,login_checkt1,login_checkt2,login_checkt3,login_checkt4,login_checkt5,te2,main_title,te1,ts;
	private SJ_LB_Bean email;
	public static SJ_Check badBehavior_Add;
	private String json, title, json2 = "", ChoiceID = "", MultiChoiceID = "",
			json3 = "", userid, uxm,sfz, app_versionName, desc, messagedata = "",DWMC,SJHM,spname,TX,udw;
	private List<KSSJ_LB_Beans> listItems = new ArrayList<KSSJ_LB_Beans>();
	private ListView sd;
	private Double geoLat;
	private Double geoLng;
	private int tihao=0,nowtihao=1;
	private ProgressDialog progressDialog;
	// 声明AMapLocationClient类对象
	public AMapLocationClient mLocationClient = null;
	// 声明定位回调监听器
	public AMapLocationListener mLocationListener;
	public AMapLocationClientOption mLocationOption;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			new AlertDialog.Builder(SJ_Check.this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("退出将清空考试内容？")
					.setNegativeButton("取消",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {}})
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
							if(timer!=null){
								timer.cancel();
							}
						}
					}).show();

			return true;//return true;拦截事件传递,从而屏蔽back键。
		}
		if (KeyEvent.KEYCODE_HOME == keyCode) {

			Toast.makeText(SJ_Check.this, "当前正在考试！", Toast.LENGTH_SHORT).show();
			return true;//同理
		}
		return super.onKeyDown(keyCode, event);
	}

	private Handler handler = new Handler() {
		@SuppressLint("NewApi")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				if (json == null || json.equals("")) {

				} else {
					listItems = JSONObject.parseArray(json, KSSJ_LB_Beans.class);
					setInfo();
				}

			} else if (msg.what == 2) {
				if (json2 == null || json2.equals("")) {
					Toast.makeText(SJ_Check.this,"提交失败！", Toast.LENGTH_SHORT).show();
				} else{
					Toast.makeText(SJ_Check.this,"提交成功！", Toast.LENGTH_SHORT).show();
					finish();
				/*	if(SJ_List.listact!=null){
						SJ_List.listact.finish();
					}*/
					/*if(CJCX_List.listact!=null){
						CJCX_List.listact.Refresh();
					}*/

					if(timer!=null){
						timer.cancel();
					}
					if(MainWeixin_KS.instance!=null) {
						MainWeixin_KS.instance.finish();
						Intent intent = new Intent();
						intent.setClass(SJ_Check.this, MainWeixin_KS.class);
						startActivity(intent);
					}

				}


			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(0x80000000, 0x80000000);
		setContentView(R.layout.sj_check);
		badBehavior_Add = this;
		//创建广播
		InnerRecevier innerReceiver = new InnerRecevier();
		//动态注册广播
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		//启动广播
		registerReceiver(innerReceiver, intentFilter);

		findView();
		setClick();
		checkstate();
		initStatusBar();
	}

	private void setClick() {
		//上一題
		bt1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(nowtihao!=1){
					tihao=0;
					//L1.removeAllViews();
					nowtihao--;
					setInfo();
					ts.setText("");
				}

			}
		});

		//下一題
		bt2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(nowtihao<tihao){
					tihao=0;
					//L1.removeAllViews();
					nowtihao++;
					setInfo();
					ts.setText("");
				}

			}
		});

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				new AlertDialog.Builder(SJ_Check.this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("退出将清空考试内容？")
						.setNegativeButton("取消",new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {}})
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								finish();
							}
						}).show();
			}
		});

		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				String wz="";
				int sd=0;
				if(listItems!=null&&listItems.size()>0){
					for(int i=0;i<listItems.size();i++){
						List<KSSJ_LB_Bean> bb=listItems.get(i).getTilb();
						if(bb!=null&&bb.size()>0){
							for(int j=0;j<bb.size();j++){
								sd++;
								if(listItems.get(i).getTilb().get(j).getItemsStr()==null||listItems.get(i).getTilb().get(j).getItemsStr().equals("")){
									wz=wz+sd+",";
									messagedata=messagedata+"<item><TJID>"+listItems.get(i).getTilb().get(j).getID()+"</TJID><TJAnswerStr>"
											+toS(listItems.get(i).getTilb().get(j).getItemsStr())+"</TJAnswerStr></item>";
								}else{
									messagedata=messagedata+"<item><TJID>"+listItems.get(i).getTilb().get(j).getID()+"</TJID><TJAnswerStr>"
											+toS(listItems.get(i).getTilb().get(j).getItemsStr())+"</TJAnswerStr></item>";
								}
							}
						}
					}
					if(wz.length()>1){
						wz=wz.substring(0, wz.length()-1);
						wz="还有这些题目未完成:"+wz;
					}

					messagedata="<checkResult>"+messagedata+"</checkResult>";

					new AlertDialog.Builder(SJ_Check.this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("确定提交？").setMessage(wz)
							.setNegativeButton("取消",new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {}})
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {

									new Thread(new Runnable() {
										@Override
										public void run() {

											json2 = WebServiceUtil.TJShiJuan(email.getID(), email.getShiJuanTitle(),userid,messagedata, udw, SJHM, TX, spname);

											Message message = new Message();
											message.what = 2;
											handler.sendMessage(message);

										}
									}).start();


								}
							}).show();



				}




			}
		});
	}

	private String toS(String ch)
	{
		//定义一个List集合，用来存储录入的数据
		List list =new ArrayList();
		//遍历
		for (int i = 0; i <ch.length() ; i++)
		{
			//截取每一个字母
			String st = ch.substring(i,i+1);
			//将截取到的字符添加到list集合中
			list.add(st);
		}
		//对字母进行排序
		java.util.Collections.sort(list);
		if(ch.equals("")){
			return "";
		}else{

			String acc="";
			for (int i = 0; i <list.size() ; i++)
			{
				acc=acc+list.get(i);
			}

			return acc;
		}


	}

	protected void setInfo() {

		if(listItems!=null&&listItems.size()>0){
			for(int i=0;i<listItems.size();i++){
				KSSJ_LB_Beans aa=listItems.get(i);
				List<KSSJ_LB_Bean> bb=listItems.get(i).getTilb();
				if(bb!=null&&bb.size()>0){

					for(int j=0;j<bb.size();j++){
						tihao++;
						//当当前未按过最上面的题号时，显示第一题
						if(nowtihao==1){
							//第一个高亮显示
							if(i==0&&j==0){
								/*if(bb.get(j).getItemsStr()!=null&&!bb.get(j).getItemsStr().equals("")){
									addshow2("已");
								}else{
									addshow2(tihao+"");
								}*/
								showTM(i,j);

							}else{
								/*if(bb.get(j).getItemsStr()!=null&&!bb.get(j).getItemsStr().equals("")){
									addshow("已");
								}else{
									addshow(tihao+"");
								}*/

							}

							//按过最上面行题号之后
						}else{

							if(tihao==nowtihao){

								showTM(i,j);

								/*if(bb.get(j).getItemsStr()!=null&&!bb.get(j).getItemsStr().equals("")){
									addshow2("已");
								}else{
									addshow2(tihao+"");
								}*/
							}else{
								/*if(bb.get(j).getItemsStr()!=null&&!bb.get(j).getItemsStr().equals("")){
									addshow("已");
								}else{
									addshow(tihao+"");
								}*/
							}

						}

					}

				}
			}

			te2.setText(nowtihao+"/"+tihao);


		}

	}


	private void showTM(final int i1,final int j1) {

		show.setText(Html.fromHtml("<font color='#FF0000'>["+listItems.get(i1).getTilb().get(j1).getFenLeiStr()+"]</font>"+"   "+listItems.get(i1).getTilb().get(j1).getTitleStr()));


		//判断题
		if(listItems.get(i1).getTilb().get(j1)!=null&&listItems.get(i1).getTilb().get(j1).getFenLeiStr().equals("判断题")){
			if(listItems.get(i1).getTilb().get(j1).getItemsA()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsA().equals("")){
				login_checkt1.setText("A: "+listItems.get(i1).getTilb().get(j1).getItemsA());
				check_1.setVisibility(View.VISIBLE);
				ts.setText("");
				if(listItems.get(i1).getTilb().get(j1).getItemsStr()!=null&&listItems.get(i1).getTilb().get(j1).getItemsStr().contains("A")){
					login_check1.setImageResource(R.mipmap.checkbox_p);
				}else{
					login_check1.setImageResource(R.mipmap.checkbox_n);
				}
			}

			if(listItems.get(i1).getTilb().get(j1).getItemsB()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsB().equals("")){
				login_checkt2.setText("B: "+listItems.get(i1).getTilb().get(j1).getItemsB());
				check_2.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getItemsStr()!=null&&listItems.get(i1).getTilb().get(j1).getItemsStr().contains("B")){
					login_check2.setImageResource(R.mipmap.checkbox_p);
				}else{
					login_check2.setImageResource(R.mipmap.checkbox_n);
				}
			}

			check_3.setVisibility(View.GONE);
			check_4.setVisibility(View.GONE);
			check_5.setVisibility(View.GONE);

			check_1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					login_check1.setImageResource(R.mipmap.checkbox_p);
					login_check2.setImageResource(R.mipmap.checkbox_n);

					listItems.get(i1).getTilb().get(j1).setItemsStr("A");

					String sd=listItems.get(i1).getTilb().get(j1).getItemsStr();
					if(sd!=null&&sd.contains(listItems.get(i1).getTilb().get(j1).getAnswerStr())){

						ts.setText("");

					}else{
						ts.setText("正确答案： "+listItems.get(i1).getTilb().get(j1).getAnswerStr()+"\n"+"正确解析： "+listItems.get(i1).getTilb().get(j1).getItemsH());

					}




					//	ts.setText(Html.fromHtml("正确答案： <font color='#3592D1'>"+listItems.get(i1).getTilb().get(j1).getAnswerStr()+"</font> +"));
				//	"正确解析："+listItems.get(i1).getTilb().get(j1).getItemsH()
			//		ts.setText("正确答案： "+listItems.get(i1).getTilb().get(j1).getAnswerStr()+"\n"+"正确解析： "+listItems.get(i1).getTilb().get(j1).getItemsH());
				}
			});

			check_2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					login_check2.setImageResource(R.mipmap.checkbox_p);
					login_check1.setImageResource(R.mipmap.checkbox_n);
					listItems.get(i1).getTilb().get(j1).setItemsStr("B");

					String sd=listItems.get(i1).getTilb().get(j1).getItemsStr();
					if(sd!=null&&sd.contains(listItems.get(i1).getTilb().get(j1).getAnswerStr())){

						ts.setText("");

					}else{
						ts.setText("正确答案： "+listItems.get(i1).getTilb().get(j1).getAnswerStr()+"\n"+"正确解析： "+listItems.get(i1).getTilb().get(j1).getItemsH());

					}

				}
			});


			//单项选择题
		}else if(listItems.get(i1).getTilb().get(j1)!=null&&listItems.get(i1).getTilb().get(j1).getFenLeiStr().equals("单项选择题")){

			if(listItems.get(i1).getTilb().get(j1).getItemsA()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsA().equals("")){
				ts.setText("");
				login_checkt1.setText("A: "+listItems.get(i1).getTilb().get(j1).getItemsA());
				check_1.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getItemsStr()!=null&&listItems.get(i1).getTilb().get(j1).getItemsStr().contains("A")){
					login_check1.setImageResource(R.mipmap.checkbox_p);

				}else{
					login_check1.setImageResource(R.mipmap.checkbox_n);
				}

			}
			if(listItems.get(i1).getTilb().get(j1).getItemsB()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsB().equals("")){
				login_checkt2.setText("B: "+listItems.get(i1).getTilb().get(j1).getItemsB());
				check_2.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getItemsStr()!=null&&listItems.get(i1).getTilb().get(j1).getItemsStr().contains("B")){
					login_check2.setImageResource(R.mipmap.checkbox_p);

				}else{
					login_check2.setImageResource(R.mipmap.checkbox_n);
				}

			}
			if(listItems.get(i1).getTilb().get(j1).getItemsC()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsC().equals("")){
				login_checkt3.setText("C: "+listItems.get(i1).getTilb().get(j1).getItemsC());
				check_3.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getItemsStr()!=null&&listItems.get(i1).getTilb().get(j1).getItemsStr().contains("C")){
					login_check3.setImageResource(R.mipmap.checkbox_p);

				}else {
					login_check3.setImageResource(R.mipmap.checkbox_n);
				}
			}else{
				check_3.setVisibility(View.GONE);
			}
			if(listItems.get(i1).getTilb().get(j1).getItemsD()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsD().equals("")){
				login_checkt4.setText("D: "+listItems.get(i1).getTilb().get(j1).getItemsD());
				check_4.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getItemsStr()!=null&&listItems.get(i1).getTilb().get(j1).getItemsStr().contains("D")){
					login_check4.setImageResource(R.mipmap.checkbox_p);

				}else{
					login_check4.setImageResource(R.mipmap.checkbox_n);
				}

			}else{
				check_4.setVisibility(View.GONE);
			}
			if(listItems.get(i1).getTilb().get(j1).getItemsE()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsE().equals("")){
				login_checkt5.setText("E: "+listItems.get(i1).getTilb().get(j1).getItemsE());
				check_5.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getItemsStr()!=null&&listItems.get(i1).getTilb().get(j1).getItemsStr().contains("E")){
					login_check5.setImageResource(R.mipmap.checkbox_p);

				}else{
					login_check5.setImageResource(R.mipmap.checkbox_n);
				}
			}else{
				check_5.setVisibility(View.GONE);
			}

			check_1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					login_check1.setImageResource(R.mipmap.checkbox_p);
					login_check2.setImageResource(R.mipmap.checkbox_n);
					login_check3.setImageResource(R.mipmap.checkbox_n);
					login_check4.setImageResource(R.mipmap.checkbox_n);
					login_check5.setImageResource(R.mipmap.checkbox_n);
					listItems.get(i1).getTilb().get(j1).setItemsStr("A");
					String sd=listItems.get(i1).getTilb().get(j1).getItemsStr();
					if(sd!=null&&sd.contains(listItems.get(i1).getTilb().get(j1).getAnswerStr())){

						ts.setText("");

					}else{
						ts.setText("正确答案： "+listItems.get(i1).getTilb().get(j1).getAnswerStr()+"\n"+"正确解析： "+listItems.get(i1).getTilb().get(j1).getItemsH());

					}
				}
			});

			check_2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					login_check2.setImageResource(R.mipmap.checkbox_p);
					login_check1.setImageResource(R.mipmap.checkbox_n);
					login_check3.setImageResource(R.mipmap.checkbox_n);
					login_check4.setImageResource(R.mipmap.checkbox_n);
					login_check5.setImageResource(R.mipmap.checkbox_n);
					listItems.get(i1).getTilb().get(j1).setItemsStr("B");

					String sd=listItems.get(i1).getTilb().get(j1).getItemsStr();
					if(sd!=null&&sd.contains(listItems.get(i1).getTilb().get(j1).getAnswerStr())){

						ts.setText("");

					}else{
						ts.setText("正确答案： "+listItems.get(i1).getTilb().get(j1).getAnswerStr()+"\n"+"正确解析： "+listItems.get(i1).getTilb().get(j1).getItemsH());

					}
				}
			});

			check_3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					login_check3.setImageResource(R.mipmap.checkbox_p);
					login_check1.setImageResource(R.mipmap.checkbox_n);
					login_check2.setImageResource(R.mipmap.checkbox_n);
					login_check4.setImageResource(R.mipmap.checkbox_n);
					login_check5.setImageResource(R.mipmap.checkbox_n);
					listItems.get(i1).getTilb().get(j1).setItemsStr("C");
					String sd=listItems.get(i1).getTilb().get(j1).getItemsStr();
					if(sd!=null&&sd.contains(listItems.get(i1).getTilb().get(j1).getAnswerStr())){

						ts.setText("");

					}else{
						ts.setText("正确答案： "+listItems.get(i1).getTilb().get(j1).getAnswerStr()+"\n"+"正确解析： "+listItems.get(i1).getTilb().get(j1).getItemsH());

					}

				}
			});

			check_4.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					login_check4.setImageResource(R.mipmap.checkbox_p);
					login_check1.setImageResource(R.mipmap.checkbox_n);
					login_check3.setImageResource(R.mipmap.checkbox_n);
					login_check2.setImageResource(R.mipmap.checkbox_n);
					login_check5.setImageResource(R.mipmap.checkbox_n);
					listItems.get(i1).getTilb().get(j1).setItemsStr("D");
					String sd=listItems.get(i1).getTilb().get(j1).getItemsStr();
					if(sd!=null&&sd.contains(listItems.get(i1).getTilb().get(j1).getAnswerStr())){

						ts.setText("");

					}else{
						ts.setText("正确答案： "+listItems.get(i1).getTilb().get(j1).getAnswerStr()+"\n"+"正确解析： "+listItems.get(i1).getTilb().get(j1).getItemsH());

					}
				}
			});

			check_5.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					login_check5.setImageResource(R.mipmap.checkbox_p);
					login_check1.setImageResource(R.mipmap.checkbox_n);
					login_check3.setImageResource(R.mipmap.checkbox_n);
					login_check4.setImageResource(R.mipmap.checkbox_n);
					login_check2.setImageResource(R.mipmap.checkbox_n);
					listItems.get(i1).getTilb().get(j1).setItemsStr("E");
					String sd=listItems.get(i1).getTilb().get(j1).getItemsStr();
					if(sd!=null&&sd.contains(listItems.get(i1).getTilb().get(j1).getAnswerStr())){

						ts.setText("");

					}else{
						ts.setText("正确答案： "+listItems.get(i1).getTilb().get(j1).getAnswerStr()+"\n"+"正确解析： "+listItems.get(i1).getTilb().get(j1).getItemsH());

					}
				}
			});

			//多项选择题
		}else if(listItems.get(i1).getTilb().get(j1)!=null&&listItems.get(i1).getTilb().get(j1).getFenLeiStr().equals("多项选择题")){

			if(listItems.get(i1).getTilb().get(j1).getItemsA()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsA().equals("")){
				ts.setText("");
				login_checkt1.setText("A: "+listItems.get(i1).getTilb().get(j1).getItemsA());
				check_1.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getItemsStr()!=null&&listItems.get(i1).getTilb().get(j1).getItemsStr().contains("A")){
					login_check1.setImageResource(R.mipmap.checkbox_p);

				}else{
					login_check1.setImageResource(R.mipmap.checkbox_n);
				}

			}
			if(listItems.get(i1).getTilb().get(j1).getItemsB()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsB().equals("")){
				login_checkt2.setText("B: "+listItems.get(i1).getTilb().get(j1).getItemsB());
				check_2.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getItemsStr()!=null&&listItems.get(i1).getTilb().get(j1).getItemsStr().contains("B")){
					login_check2.setImageResource(R.mipmap.checkbox_p);

				}else{
					login_check2.setImageResource(R.mipmap.checkbox_n);
				}

			}
			if(listItems.get(i1).getTilb().get(j1).getItemsC()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsC().equals("")){
				login_checkt3.setText("C: "+listItems.get(i1).getTilb().get(j1).getItemsC());
				check_3.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getItemsStr()!=null&&listItems.get(i1).getTilb().get(j1).getItemsStr().contains("C")){
					login_check3.setImageResource(R.mipmap.checkbox_p);

				}else{
					login_check3.setImageResource(R.mipmap.checkbox_n);
				}

			}else{
				check_3.setVisibility(View.GONE);
			}
			if(listItems.get(i1).getTilb().get(j1).getItemsD()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsD().equals("")){
				login_checkt4.setText("D: "+listItems.get(i1).getTilb().get(j1).getItemsD());
				check_4.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getItemsStr()!=null&&listItems.get(i1).getTilb().get(j1).getItemsStr().contains("D")){
					login_check4.setImageResource(R.mipmap.checkbox_p);

				}else{
					login_check4.setImageResource(R.mipmap.checkbox_n);
					ts.setText("");
				}

			}else{
				check_4.setVisibility(View.GONE);
			}
			if(listItems.get(i1).getTilb().get(j1).getItemsE()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsE().equals("")){
				login_checkt5.setText("E: "+listItems.get(i1).getTilb().get(j1).getItemsE());
				check_5.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getItemsStr()!=null&&listItems.get(i1).getTilb().get(j1).getItemsStr().contains("E")){
					login_check5.setImageResource(R.mipmap.checkbox_p);

				}else{
					login_check5.setImageResource(R.mipmap.checkbox_n);
					ts.setText("");
				}

			}else{
				check_5.setVisibility(View.GONE);
			}

			check_1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					String sd=listItems.get(i1).getTilb().get(j1).getItemsStr();
					if(sd!=null&&sd.contains("A")){
						login_check1.setImageResource(R.mipmap.checkbox_n);
						sd=sd.replace("A","");
						listItems.get(i1).getTilb().get(j1).setItemsStr(sd);

					}else{
						login_check1.setImageResource(R.mipmap.checkbox_p);
						sd=sd.replace("A","");
						listItems.get(i1).getTilb().get(j1).setItemsStr(sd+"A");
					}


					if(sd!=null&&sd.contains(listItems.get(i1).getTilb().get(j1).getAnswerStr())){

						ts.setText("");

					}else{
						ts.setText("正确答案： "+listItems.get(i1).getTilb().get(j1).getAnswerStr()+"\n"+"正确解析： "+listItems.get(i1).getTilb().get(j1).getItemsH());

					}

				}
			});

			check_2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {

					String sd=listItems.get(i1).getTilb().get(j1).getItemsStr();
					if(sd!=null&&sd.contains("B")){
						login_check2.setImageResource(R.mipmap.checkbox_n);
						sd=sd.replace("B","");
						listItems.get(i1).getTilb().get(j1).setItemsStr(sd);
					}else{
						login_check2.setImageResource(R.mipmap.checkbox_p);
						sd=sd.replace("B","");
						listItems.get(i1).getTilb().get(j1).setItemsStr(sd+"B");
					}
					if(sd!=null&&sd.contains(listItems.get(i1).getTilb().get(j1).getAnswerStr())){

						ts.setText("");

					}else{
						ts.setText("正确答案： "+listItems.get(i1).getTilb().get(j1).getAnswerStr()+"\n"+"正确解析： "+listItems.get(i1).getTilb().get(j1).getItemsH());

					}
				}
			});

			check_3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					String sd=listItems.get(i1).getTilb().get(j1).getItemsStr();
					if(sd!=null&&sd.contains("C")){
						login_check3.setImageResource(R.mipmap.checkbox_n);
						sd=sd.replace("C","");
						listItems.get(i1).getTilb().get(j1).setItemsStr(sd);
					}else{
						login_check3.setImageResource(R.mipmap.checkbox_p);
						sd=sd.replace("C","");
						listItems.get(i1).getTilb().get(j1).setItemsStr(sd+"C");
					}
					if(sd!=null&&sd.contains(listItems.get(i1).getTilb().get(j1).getAnswerStr())){

						ts.setText("");

					}else{
						ts.setText("正确答案： "+listItems.get(i1).getTilb().get(j1).getAnswerStr()+"\n"+"正确解析： "+listItems.get(i1).getTilb().get(j1).getItemsH());

					}

				}
			});

			check_4.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					String sd=listItems.get(i1).getTilb().get(j1).getItemsStr();
					if(sd!=null&&sd.contains("D")){
						login_check4.setImageResource(R.mipmap.checkbox_n);
						sd=sd.replace("D","");
						listItems.get(i1).getTilb().get(j1).setItemsStr(sd);
					}else{
						login_check4.setImageResource(R.mipmap.checkbox_p);
						sd=sd.replace("D","");
						listItems.get(i1).getTilb().get(j1).setItemsStr(sd+"D");
					}
					if(sd!=null&&sd.contains(listItems.get(i1).getTilb().get(j1).getAnswerStr())){

						ts.setText("");

					}else{
						ts.setText("正确答案： "+listItems.get(i1).getTilb().get(j1).getAnswerStr()+"\n"+"正确解析： "+listItems.get(i1).getTilb().get(j1).getItemsH());

					}

				}
			});

			check_5.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					String sd=listItems.get(i1).getTilb().get(j1).getItemsStr();
					if(sd!=null&&sd.contains("E")){
						login_check5.setImageResource(R.mipmap.checkbox_n);
						sd=sd.replace("E","");
						listItems.get(i1).getTilb().get(j1).setItemsStr(sd);
					}else{
						login_check5.setImageResource(R.mipmap.checkbox_p);
						sd=sd.replace("E","");
						listItems.get(i1).getTilb().get(j1).setItemsStr(sd+"E");
					}
					if(sd!=null&&sd.contains(listItems.get(i1).getTilb().get(j1).getAnswerStr())){

						ts.setText("");

					}else{
						ts.setText("正确答案： "+listItems.get(i1).getTilb().get(j1).getAnswerStr()+"\n"+"正确解析： "+listItems.get(i1).getTilb().get(j1).getItemsH());

					}

				}
			});


		}


	}


	private void checkstate() {

		email=(SJ_LB_Bean)getIntent().getSerializableExtra("YZM");

		te1.setText(email.getShiJuanTitle()+"");

		timer = new CountDownTimer(email.getKaoShiXianShi() * 60 * 1000, 1000) {
			/**
			 * 固定间隔被调用,就是每隔countDownInterval会回调一次方法onTick
			 * @param millisUntilFinished
			 */
			@Override
			public void onTick(long millisUntilFinished) {
				zong.setText("剩余时间："+formatTime(millisUntilFinished));
			}

			/**
			 * 倒计时完成时被调用
			 */
			@Override
			public void onFinish() {


				Toast.makeText(SJ_Check.this,"考试结束，正在提交试卷！", Toast.LENGTH_SHORT).show();

				String wz="";
				int sd=0;
				if(listItems!=null&&listItems.size()>0){
					for(int i=0;i<listItems.size();i++){
						List<KSSJ_LB_Bean> bb=listItems.get(i).getTilb();
						if(bb!=null&&bb.size()>0){
							for(int j=0;j<bb.size();j++){
								sd++;
								if(listItems.get(i).getTilb().get(j).getItemsStr()==null||listItems.get(i).getTilb().get(j).getItemsStr().equals("")){
									wz=wz+sd+",";
								}else{
									messagedata=messagedata+"<item><TJID>"+listItems.get(i).getTilb().get(j).getID()+"</TJID><TJAnswerStr>"
											+toS(listItems.get(i).getTilb().get(j).getItemsStr())+"</TJAnswerStr></item>";
								}
							}
						}
					}
					if(wz.length()>1){
						wz=wz.substring(0, wz.length()-1);
						wz="还有这些题目未完成:"+wz;
					}

				}

				messagedata="<checkResult>"+messagedata+"</checkResult>";

				new Thread(new Runnable() {
					@Override
					public void run() {

						json2 = WebServiceUtil.TJShiJuan(email.getID(), email.getShiJuanTitle(),userid,messagedata, udw, SJHM, TX, sfz);

						Message message = new Message();
						message.what = 2;
						handler.sendMessage(message);

					}
				}).start();


			}
		};

		timer.start();

		new Thread(new Runnable() {
			@Override
			public void run() {

				json = WebServiceUtil.GetShiJuanTIKu(email.getID());

				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);

			}
		}).start();

	}

	/**
	 * 倒数计时器
	 */
	private CountDownTimer timer;

	public String formatTime(long millisecond) {
		int minute;//分钟
		int second;//秒数
		minute = (int) ((millisecond / 1000) / 60);
		second = (int) ((millisecond / 1000) % 60);
		if (minute < 10) {
			if (second < 10) {
				return "0" + minute + " 分" + "0" + second+" 秒";
			} else {
				return "0" + minute + " 分" + second+" 秒";
			}
		}else {
			if (second < 10) {
				return minute + " 分" + "0" + second+" 秒";
			} else {
				return minute + " 分" + second+" 秒";
			}
		}
	}





	private void findView() {

		ts= (TextView) findViewById(R.id.ts);


		back = (Button) findViewById(R.id.back);
		add = (TextView) findViewById(R.id.add);
		show= (TextView) findViewById(R.id.show);
		zong= (TextView) findViewById(R.id.zong);
		te2= (TextView) findViewById(R.id.te2);
		te1= (TextView) findViewById(R.id.te1);
		main_title= (TextView) findViewById(R.id.main_title);

		check_1 = (LinearLayout) findViewById(R.id.check_1);
		check_2 = (LinearLayout) findViewById(R.id.check_2);
		check_3 = (LinearLayout) findViewById(R.id.check_3);
		check_4 = (LinearLayout) findViewById(R.id.check_4);
		check_5 = (LinearLayout) findViewById(R.id.check_5);

		login_check1=(ImageView) findViewById(R.id.login_check1);
		login_check2=(ImageView) findViewById(R.id.login_check2);
		login_check3=(ImageView) findViewById(R.id.login_check3);
		login_check4=(ImageView) findViewById(R.id.login_check4);
		login_check5=(ImageView) findViewById(R.id.login_check5);

		login_checkt1= (TextView) findViewById(R.id.login_checkt1);
		login_checkt2= (TextView) findViewById(R.id.login_checkt2);
		login_checkt3= (TextView) findViewById(R.id.login_checkt3);
		login_checkt4= (TextView) findViewById(R.id.login_checkt4);
		login_checkt5= (TextView) findViewById(R.id.login_checkt5);

		bt1 = (Button) findViewById(R.id.bt1);
		bt2 = (Button) findViewById(R.id.bt2);

		userid = getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		uxm = getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("uxm", "");
		sfz = getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("sfz", "");
		DWMC = getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("DWMC", "");
		TX = getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("TX", "");
		SJHM = getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("SJHM", "");
		spname= getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		udw= getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("udw", "");
		SJHM= getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("udh", "");
		final ScrollView relative = (ScrollView) findViewById(R.id.LL1);
		relative.setLongClickable(true);
		relative.setOnTouchListener(new MyGestureListener(this));
	}

	public void back_bt(View view) {
		this.finish();
	}

	private class MyGestureListener extends GestureListener {
		public MyGestureListener(Context context) {
			super(context);
		}

		@Override
		public boolean left() {

			if(nowtihao<tihao){
				tihao=0;
				//L1.removeAllViews();
				nowtihao++;
				setInfo();
			}

			return super.left();
		}

		@Override
		public boolean right() {

			if(nowtihao!=1){
				tihao=0;
				//L1.removeAllViews();
				nowtihao--;
				setInfo();
			}

			return super.right();
		}
	}

	class InnerRecevier extends BroadcastReceiver {

		final String SYSTEM_DIALOG_REASON_KEY = "reason";

		final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

		final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
				String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
				if (reason != null) {
					if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
						Toast.makeText(SJ_Check.this, "退出考试页面，已为您清空考试成绩！", Toast.LENGTH_SHORT).show();
						finish();


					} else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
						Toast.makeText(SJ_Check.this, "退出考试页面，已为您清空考试成绩！", Toast.LENGTH_SHORT).show();
						finish();



					}
				}
			}
		}
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
			StatusBarUtils.setStatusBarColor(SJ_Check.this, R.color.btground);
		}
	}

}
