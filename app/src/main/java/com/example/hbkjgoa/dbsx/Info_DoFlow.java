package com.example.hbkjgoa.dbsx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.NWorkToDo;
import com.example.hbkjgoa.model.SpinnerData;
import com.example.hbkjgoa.model.WorkFlowItem;
import com.example.hbkjgoa.ryxz.ryxz_Fragment;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressLint({ "HandlerLeak", "UseSparseArrays" })
public class Info_DoFlow extends Activity {

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

		}
		return super.onKeyDown(keyCode, event);
	}
private  String value,Texts;
	private int iZLID;
	private int ZL_ID;
	private String sGCMC;
	private String sZLZT;
	private int GCXX_ID;
	private int GCXX_FWQID;
	private String name;
	private String xm;
	private String lsdw;
	private String YH_LSDQ;
	private String jd_wd = "";
	private int KEY_pd;
	private int currentPosition;
	private String json;
	private String json2;
	private Button zlcl01_B3,zlcl01_B31,zlcl01_B311;
	private Button zlcl01_up;
	private EditText zlcl01_T31;
	private EditText zlcl01_T2;

	private WebView t3;
	private TextView spms;
	private TextView psms,Text1;
	private CheckBox tjxz;
	private Spinner lcxzSpinner;
	private ProgressDialog progressDialog;
	private Message message;
	private Bundle bundle;
	private List<Map<String, Object>> list;
	private List<WorkFlowItem> listitem;
	// private Map<Integer,Integer> MultiChoiceID2=new HashMap<Integer,
	// Integer>();
	private String nextuser = "";
	private String html;
	private String step;
	private List<String> listcc;
	private FileInputStream fis;
	private String sdcardDir = Environment.getExternalStorageDirectory()
			.toString();
	private String imagebuffer;
	private String sWPID;
	private String ip = "0";
	private String userID;
	private String sNrString;
	private LocationListener ll;
	private NWorkToDo nWorkToDo;
	private static final int WHAT_DID_LOAD_DATA = 0;
	private String spname;
	private String yj;
	private RelativeLayout R1,R2;
	private TextView zlcl01_T311,zlcl01_T3;
	private String spr;
	public static Info_DoFlow listact;
	String fomart[];
	private RadioGroup group; // 点选按钮组
	@Override
	protected void onDestroy() {
		if (ll != null) {

		}
		super.onDestroy();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTexts() {
		return Texts;
	}

	public void setTexts(String texts) {
		Texts = texts;
	}

	private Handler handler = new Handler() {
		@SuppressLint("NewApi")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);


			if (msg.what == 1) {
				progressDialog.dismiss();
				if (json == null) {

				} else if (json.equals("success")) {
					final String[] mItems = new String[list.size()];
					final String[] LXRHM = new String[list.size()];

					for (int i = 0; i < list.size(); ++i) {
						mItems[i] = (String) list.get(i).get("userID");
						LXRHM[i] = (String) list.get(i).get("userName");

					}
					AlertDialog.Builder builder = new AlertDialog.Builder(
							Info_DoFlow.this);
					builder.setTitle("请选择接收人");
					builder.setItems(LXRHM,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
                                                    int which) {
									zlcl01_T3.setText(LXRHM[which]);
									userID = mItems[which];
								}
							});
					builder.create().show();
				}

			} else if (msg.what == 2) {
				progressDialog.dismiss();
				if (json == null) {

				} else if (json.contains("ok")) {
					// 提交后更改待处理人
					Log.d("yin", "提交后待处理人：" + nextuser + "++++name:" + name
							+ "+++iZLID:" + iZLID);
					AlertDialog.Builder builder = new AlertDialog.Builder(
							Info_DoFlow.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
					builder.setMessage("提交成功！");
					builder.setTitle("注意");
					builder.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											if(InfoWorkFlow.mm!=null){
												InfoWorkFlow.mm.closehere();
											}
											if(WorkFlowActivity.mm!=null){
												WorkFlowActivity.mm.onRefresh();
											}

											 finish();

										}
									}).show();
				}
			}else if (msg.what == 3) {
				setData();
			}else if (msg.what == 4) {

				if(json==null||json.equals("0")){

					final String opinion = zlcl01_T2.getText().toString();

					if (zlcl01_T3.getVisibility() == View.GONE) {

						boolean havenet = NetHelper
								.IsHaveInternet(Info_DoFlow.this);
						if (havenet) {
							progressDialog = ProgressDialog.show(Info_DoFlow.this,
									"正在获取数据中", "请稍等...");
							new Thread() {
								@Override
								public void run()
								{
									String tiaoj="0";
							if(tjxz.isChecked())
							{

								tiaoj="1";
							}
									if(!((SpinnerData)lcxzSpinner.getSelectedItem()).getValue().equals(""))
									{
									json = WebServiceUtil.everycanforStr3("wid",
											"Content", "mrspr", "tiaojian",
											"PiShiStr", "NodeID", "uname", nWorkToDo.getID().toString(),
											nWorkToDo.getFromContentString(),zlcl01_T3.getText().toString() , tiaoj,zlcl01_T2.getText().toString(), Integer.parseInt(((SpinnerData)lcxzSpinner.getSelectedItem()).getValue()), ""+spname+"",
											"BanLi");
									}
									else
									{
										json = WebServiceUtil.everycanforStr3_1("wid",
												"Content", "mrspr", "tiaojian",
												"PiShiStr", "NodeID", "uname", nWorkToDo.getID().toString(),
												nWorkToDo.getFromContentString(),zlcl01_T3.getText().toString() , tiaoj,zlcl01_T2.getText().toString(),"", ""+spname+"",
												"BanLi");

									}



									message = new Message();
									message.what = 2;
									handler.sendMessage(message);
								}
							}.start();
						} else {
							new AlertDialog.Builder(Info_DoFlow.this)
									.setMessage("请检查网络连接设置！")
									.setTitle("无网络连接")
									.setPositiveButton("确定",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
												}
											}).show();
						}
					} else {
						nextuser = zlcl01_T3.getText().toString();
						if (nextuser.equals("") && !((SpinnerData)lcxzSpinner.getSelectedItem()).getValue().equals("")) {
							new AlertDialog.Builder(Info_DoFlow.this)
									.setMessage("未选择人员，请选择！")
									.setTitle("注意")
									.setPositiveButton("确定",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
												}
											}).show();
						} else {
							boolean havenet = NetHelper
									.IsHaveInternet(Info_DoFlow.this);
							if (havenet) {
								progressDialog = ProgressDialog.show(
										Info_DoFlow.this, "正在获取数据中", "请稍等...");
								new Thread() {
									@Override
									public void run() {
										String tiaoj="0";
										if(tjxz.isChecked())
										{

											tiaoj="1";
										}

										if(!((SpinnerData)lcxzSpinner.getSelectedItem()).getValue().equals(""))
										{
										json = WebServiceUtil.everycanforStr3("wid",
												"Content", "mrspr", "tiaojian",
												"PiShiStr", "NodeID", "uname", nWorkToDo.getID().toString(),
												nWorkToDo.getFromContentString(),zlcl01_T3.getText().toString() , tiaoj,zlcl01_T2.getText().toString(), Integer.parseInt(((SpinnerData)lcxzSpinner.getSelectedItem()).getValue()), ""+spname+"",
												"BanLi");
										}
										else
										{
											json = WebServiceUtil.everycanforStr3_1("wid",
													"Content", "mrspr", "tiaojian",
													"PiShiStr", "NodeID", "uname", nWorkToDo.getID().toString(),
													nWorkToDo.getFromContentString(),zlcl01_T3.getText().toString() , tiaoj,zlcl01_T2.getText().toString(),"", ""+spname+"",
													"BanLi");

										}

										message = new Message();
										message.what = 2;
										handler.sendMessage(message);
									}
								}.start();
							} else {
								new AlertDialog.Builder(Info_DoFlow.this)
										.setMessage("请检查网络连接设置！")
										.setTitle("无网络连接")
										.setPositiveButton(
												"确定",
												new DialogInterface.OnClickListener() {
													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {
													}
												}).show();
							}
						}
					}
				}else{
					boolean havenet = NetHelper
							.IsHaveInternet(Info_DoFlow.this);
					if (havenet) {
						progressDialog = ProgressDialog.show(Info_DoFlow.this,
								"正在获取数据中", "请稍等...");
						new Thread() {
							@Override
							public void run()
							{
								String tiaoj="0";
						if(tjxz.isChecked())
						{

							tiaoj="1";
						}
								if(!((SpinnerData)lcxzSpinner.getSelectedItem()).getValue().equals(""))
								{
								json = WebServiceUtil.everycanforStr3("wid",
										"Content", "mrspr", "tiaojian",
										"PiShiStr", "NodeID", "uname", nWorkToDo.getID().toString(),
										nWorkToDo.getFromContentString(),zlcl01_T3.getText().toString() , tiaoj,zlcl01_T2.getText().toString(),0, ""+spname+"",
										"BanLi");
								}
								else
								{
									json = WebServiceUtil.everycanforStr3_1("wid",
											"Content", "mrspr", "tiaojian",
											"PiShiStr", "NodeID", "uname", nWorkToDo.getID().toString(),
											nWorkToDo.getFromContentString(),zlcl01_T3.getText().toString() , tiaoj,zlcl01_T2.getText().toString(),"", ""+spname+"",
											"BanLi");

								}



								message = new Message();
								message.what = 2;
								handler.sendMessage(message);
							}
						}.start();
					} else {
						new AlertDialog.Builder(Info_DoFlow.this)
								.setMessage("请检查网络连接设置！")
								.setTitle("无网络连接")
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
											}
										}).show();
					}
				}
			}else if(msg.what==5){
				if(json!=null&&json.equals("1")){

					new AlertDialog.Builder(Info_DoFlow.this)
					.setMessage("流程已结束，是否确认提交！")
					.setNegativeButton("取消", null)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										DialogInterface dialog,
										int which) {

									boolean havenet = NetHelper.IsHaveInternet(Info_DoFlow.this);
									if (havenet) {
										progressDialog = ProgressDialog.show(Info_DoFlow.this,
												"正在获取数据中", "请稍等...");
										new Thread() {
											@Override
											public void run()
											{
												String tiaoj="0";
										if(tjxz.isChecked())
										{

											tiaoj="1";
										}
												if(!((SpinnerData)lcxzSpinner.getSelectedItem()).getValue().equals(""))
												{
												json = WebServiceUtil.everycanforStr3("wid",
														"Content", "mrspr", "tiaojian",
														"PiShiStr", "NodeID", "uname", nWorkToDo.getID().toString(),
														nWorkToDo.getFromContentString(),zlcl01_T3.getText().toString() , tiaoj,zlcl01_T2.getText().toString(),0, ""+spname+"",
														"BanLi");
												}
												else
												{
													json = WebServiceUtil.everycanforStr3_1("wid",
															"Content", "mrspr", "tiaojian",
															"PiShiStr", "NodeID", "uname", nWorkToDo.getID().toString(),
															nWorkToDo.getFromContentString(),zlcl01_T3.getText().toString() , tiaoj,zlcl01_T2.getText().toString(),"", ""+spname+"",
															"BanLi");

												}



												message = new Message();
												message.what = 2;
												handler.sendMessage(message);
											}
										}.start();
									} else {
										new AlertDialog.Builder(Info_DoFlow.this)
												.setMessage("请检查网络连接设置！")
												.setTitle("无网络连接")
												.setPositiveButton("确定",
														new DialogInterface.OnClickListener() {
															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {
															}
														}).show();
									}


								}
							}).show();



				}
			}



		}
	};

	private void setData() {
		List<SpinnerData> lst = new ArrayList<SpinnerData>();

		for (WorkFlowItem item : nWorkToDo.getNodeList()) {
			SpinnerData c = new SpinnerData(item.getValueString(),item.getTextString(),item.getMrsprString(),item.getSpmsString(),item.getPsmsString());
			lst.add(c);
			zlcl01_T3.setText(nWorkToDo.getBeiYong1());
		}
		ArrayAdapter<SpinnerData> Adapter = new ArrayAdapter<SpinnerData>(this,android.R.layout.simple_spinner_item, lst);
		Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		lcxzSpinner.setAdapter(Adapter);

		int i = 0;
		for(WorkFlowItem s:nWorkToDo.getNodeList()) {

			RadioButton radio = new RadioButton(this);
			radio.setText(s.getTextString());
			radio.setId(i++);
			radio.setTextSize(16);
			group.addView(radio);
			group.check(0);
			RadioButton rb = (RadioButton)Info_DoFlow.this.findViewById(group.getCheckedRadioButtonId());

			zlcl01_T311.setText(nWorkToDo.getNodeList().get(0).getMrsprString());
		}
		Info_DoFlow.listact.setValue(nWorkToDo.getNodeList().get(0).getValueString());
		Info_DoFlow.listact.setTexts(nWorkToDo.getNodeList().get(0).getTextString());
	//	Toast.makeText(Info_DoFlow.this, "选取的值："+value, Toast.LENGTH_LONG).show();
		if (Texts.contains("结束")) {

			zlcl01_T311.setText(" ");
		}

		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				RadioButton rb=(RadioButton)findViewById(checkedId);
				if(rb.isChecked()){
					for(WorkFlowItem s:nWorkToDo.getNodeList()) {
						if(rb.getText().equals(s.getTextString())){
							String	str=s.getTextString();
							Info_DoFlow.listact.setValue(s.getValueString());
							Info_DoFlow.listact.setTexts(s.getTextString());
				//			Toast.makeText(Info_DoFlow.this, "选取的值："+value, Toast.LENGTH_LONG).show();


						}
					}

				}
			}

		});

	//	RadioButton rb = (RadioButton)Info_DoFlow.this.findViewById(group.getCheckedRadioButtonId());
	//	Toast.makeText(Info_DoFlow.this, "选取的值："+rb.getText(), Toast.LENGTH_LONG).show();


	}








	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent paramIntent) {

		if ((requestCode == 1) && (resultCode == 101)) {
			String dclr = paramIntent.getStringExtra("dclr");
			zlcl01_T3.setText(dclr);
		}
		if ((requestCode == 1) && (resultCode == 102)) {
			String dclrl = paramIntent.getStringExtra("dclrl");
			zlcl01_T311.setText(dclrl);
		}

		super.onActivityResult(requestCode, resultCode, paramIntent);
	}

	public void setry(String name ){
		zlcl01_T311.setText(name);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.info_doflow);
		listact = this;

		spname=getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE+ Context.MODE_PRIVATE).getString("spname","");
		findView();
		Text1.setText("欢迎你，"+spname);
		getBundle();
		setData();
		setClick();
		initStatusBar();
		 yj = getIntent().getStringExtra("yj");
		new Thread() {
			@Override
			public void run()
			{

				json = WebServiceUtil.everycanforStr3("wid",
						"Content", "mrspr", "tiaojian",
						"PiShiStr", "NodeID", "uname", nWorkToDo.getID().toString(),
						nWorkToDo.getFromContentString(),zlcl01_T3.getText().toString() ,"0",zlcl01_T2.getText().toString(),0, ""+spname+"",
						"SFJS");
				Log.d("yin", "SFJS:"+json);
				message = new Message();
				message.what = 5;
				handler.sendMessage(message);
			}
		}.start();
	}
	private void setClick() {
		R1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			//	if (((SpinnerData)lcxzSpinner.getSelectedItem()).getText().contains("结束")) {
				if (Texts.contains("结束")||Texts.contains("发起人接收")) {

					Toast.makeText(getApplicationContext(), "该审批模式下不能选择人员！",
						     Toast.LENGTH_SHORT).show();
				}else {
					//选择所有人
					Intent intent = new Intent(Info_DoFlow.this, ryxz_Fragment.class);
					bundle=new Bundle();
					bundle.putSerializable("NWorkToDo", nWorkToDo);
					intent.putExtras(bundle);
					startActivityForResult(intent, 1);
				}
			}
		});


			/**
			 * 提交
			 */
			zlcl01_up.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (nWorkToDo.getNodeList().size()==0) {
						progressDialog = ProgressDialog.show(Info_DoFlow.this,"正在获取数据中", "请稍等...");
						new Thread() {
							@Override
							public void run()	{
								json = WebServiceUtil.everycanforStr_banli(
								"wid",
								"Content",
								"mrspr",
								"yijian",
								"NodeID",
								"NodeStr",
								"uname",
								"cs",
								"",
								nWorkToDo.getID().toString(),
										fomart[0],
								zlcl01_T311.getText().toString(),
								"",
								"0",
								"0",
								spname,
								zlcl01_T3.getText().toString(),
										fomart[1],
								"BanLi");
								message = new Message();
								message.what = 2;
								handler.sendMessage(message);
							}
						}.start();
					}else {

				if (((SpinnerData)lcxzSpinner.getSelectedItem()).getValue().equals("")) {
					progressDialog = ProgressDialog.show(Info_DoFlow.this,"正在获取数据中", "请稍等...");
					new Thread() {
						@Override
						public void run() {

							json = WebServiceUtil.everycanforStr2("wid", "Content", "mrspr", "yijian", "uname", "",
									nWorkToDo.getID().toString(), nWorkToDo.getFromContentString(), "", "", spname, 0,  "JS1");

							message = new Message();
							message.what = 2;
							handler.sendMessage(message);
						}
					}.start();
				}else {
					if (zlcl01_T311.getText().toString().equals("")&& ! Texts.contains("结束")) {
						Toast.makeText(Info_DoFlow.this,"请选择下一步审批人！", Toast.LENGTH_SHORT).show();
					}else {
					progressDialog = ProgressDialog.show(Info_DoFlow.this,"正在获取数据中", "请稍等...");
					new Thread() {
						@Override
						public void run()	{
							json = WebServiceUtil.everycanforStr_banli(
							"wid",
							"Content",
							"mrspr",
							"yijian",
							"NodeID",
							"NodeStr",
							"uname",
							"cs",
							"",
							nWorkToDo.getID().toString(),
							fomart[0],
							zlcl01_T311.getText().toString(),
							"",
						//	((SpinnerData)lcxzSpinner.getSelectedItem()).getValue(),
							value,
							nWorkToDo.getNodeList().get(0).getPsmsString(),
							spname,
							zlcl01_T3.getText().toString(),
							fomart[1],
							"BanLi");
							Log.d("提交", "1wid:  "+nWorkToDo.getID().toString()+"3mrspr:  "+zlcl01_T311.getText().toString()
							+"5NodeID:  "+((SpinnerData)lcxzSpinner.getSelectedItem()).getValue()+"6NodeStr:  "+nWorkToDo.getNodeList().get(0).getPsmsString()
							+"7uname:  "+spname+"8cs:  "+zlcl01_T3.getText().toString());
							message = new Message();
							message.what = 2;
							handler.sendMessage(message);
						}
					}.start();
					}
				}
				}

				}
			});

	}

	@SuppressWarnings("unused")
	private String Savejosn() {
		JSONArray jsonObjs;
		JSONObject jsonObj;
		JSONTokener jsonTokener = new JSONTokener(json);
		try {
			JSONObject person = (JSONObject) jsonTokener.nextValue();
			jsonObjs = person.getJSONArray("wpusers");
			if (jsonObjs.length() == 0) {
				return "haveno";
			}
			for (int i = 0; i < jsonObjs.length(); i++) {
				jsonObj = (JSONObject) jsonObjs.opt(i);
				String WPID = jsonObj.getString("WPID");
				JSONArray Users = jsonObj.getJSONArray("Users");
				if (Users.length() == 0) {
					return "haveno";
				}
				list = new ArrayList<Map<String, Object>>();
				for (int c = 0; c < Users.length(); c++) {
					JSONObject jsonObj2 = (JSONObject) Users.opt(c);

					String userID = jsonObj2.getString("userID");
					String userName = jsonObj2.getString("userName");
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("userID", userID);
					map.put("userName", userName);
					list.add(map);
				}

			}
			return "success";
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}



	@SuppressLint("WrongViewCast")
	private void findView() {
		zlcl01_B3 = (Button) findViewById(R.id.zlcl01_B3);
		zlcl01_T3 = (TextView) findViewById(R.id.zlcl01_T3);
		zlcl01_T311 = (TextView) findViewById(R.id.zlcl01_T311);
		zlcl01_up = (Button) findViewById(R.id.zlcl01_up);
		zlcl01_T2 = (EditText) findViewById(R.id.zlcl01_T2);
		lcxzSpinner = (Spinner) findViewById(R.id.S1);
		spms = (TextView) findViewById(R.id.t2);
		psms = (TextView) findViewById(R.id.t1);
		tjxz = (CheckBox) findViewById(R.id.c1);
		Text1=(TextView)findViewById(R.id.Text1);
		group = (RadioGroup) findViewById(R.id.radioGroup);
		t3=(WebView)findViewById(R.id.t3);

		zlcl01_T31 = (EditText) findViewById(R.id.zlcl01_T31);
		zlcl01_B31= (Button) findViewById(R.id.zlcl01_B31);
		zlcl01_B311= (Button) findViewById(R.id.zlcl01_B311);
		zlcl01_T31.setEnabled(false);
		zlcl01_T31.setFocusable(false);
		zlcl01_B31.setVisibility(View.GONE);
		R1=(RelativeLayout) findViewById(R.id.R1);
		R2=(RelativeLayout) findViewById(R.id.R2);

	}

	private void getBundle() {
		Intent intent = this.getIntent();
		Bundle bundle=this.getIntent().getExtras();
		nWorkToDo = (NWorkToDo) intent.getSerializableExtra("NWorkToDo");
		html = bundle.getString("KEY_NR");
		fomart=html.split("WoEaSy");


	}


	public void bt_back(View v){
		finish();
	}

	public void bt_back2(View v){

		if(WorkFlowActivity.mm!=null){
			WorkFlowActivity.mm.closehere();
		}
		if(InfoWorkFlow.mm!=null){
			InfoWorkFlow.mm.closehere();
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
			StatusBarUtils.setStatusBarColor(Info_DoFlow.this, R.color.btground);
		}
	}
}
