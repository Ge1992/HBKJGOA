package com.example.hbkjgoa.sqsp;

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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.Email.ChooseUserActivity2;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.NWorkToDo;
import com.example.hbkjgoa.model.SpinnerData2;
import com.example.hbkjgoa.model.WorkFlowItem;
import com.example.hbkjgoa.ryxz.ryxz_Fragment;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 废
 */

@SuppressLint({ "HandlerLeak", "UseSparseArrays", "WrongViewCast" })
public class Info_DoFlow2 extends Activity {

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
		}
		return super.onKeyDown(keyCode, event);
	}

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
	private Button zlcl01_B3,zlcl01_B31;
	private Button zlcl01_up;
	private EditText zlcl01_T31;
	private EditText zlcl01_T2;
	private TextView spms,zlcl01_T3;
	private TextView psms,Text1;
	private CheckBox tjxz;
	private Spinner lcxzSpinner;
	private ProgressDialog progressDialog;
	private Message message;
	private Bundle bundle;
	private List<Map<String, Object>> list;
	private List<WorkFlowItem> listitem;
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
	private RelativeLayout R1,R2;
	private TextView zlcl01_T311;
	private String id="",wid="";
	@Override
	protected void onDestroy() {
		if (ll != null) {

		}
		super.onDestroy();
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			progressDialog.dismiss();

			if (msg.what == 1) {
				if (json == null) {

				} else if (json.equals("success")) {
					final String[] mItems = new String[list.size()];
					final String[] LXRHM = new String[list.size()];

					for (int i = 0; i < list.size(); ++i) {
						mItems[i] = (String) list.get(i).get("userID");
						LXRHM[i] = (String) list.get(i).get("userName");

					}
					AlertDialog.Builder builder = new AlertDialog.Builder(
							Info_DoFlow2.this);
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

					Toast.makeText(getApplicationContext(), "提交成功",
							Toast.LENGTH_SHORT).show();
				if(InfoWorkFlow2.mm!=null){
					InfoWorkFlow2.mm.closehere();
				}
					finish();
			} else if (msg.what == 3) {

				setData();
			}
		}
	};

	private void setData() {
		List<SpinnerData2> lst = new ArrayList<SpinnerData2>();

		for (WorkFlowItem item : nWorkToDo.getNodeList()) {

			SpinnerData2 c = new SpinnerData2(item.getValueString(),item.getTextString());
			lst.add(c);
			if (item.getTijaoString() == "1") {
				tjxz.setChecked(true);
			}
			psms.setText("评审模式：" + nWorkToDo.getNodeList().get(0).getPsmsString());
			spms.setText("审批人选择模式：" + nWorkToDo.getNodeList().get(0).getSpmsString());
			zlcl01_T3.setText(nWorkToDo.getNodeList().get(0).getMrsprString());
		}

		ArrayAdapter<SpinnerData2> Adapter = new ArrayAdapter<SpinnerData2>(this,
				android.R.layout.simple_spinner_item, lst);
		Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		lcxzSpinner.setAdapter(Adapter);

		String Department=getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE+ Context.MODE_PRIVATE).getString("Department","");

		zlcl01_T31.setText(Department);

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.info_doflow);

		id=this.getIntent().getStringExtra("id");
		wid=this.getIntent().getStringExtra("wid");
		spname=getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE+ Context.MODE_PRIVATE).getString("spname","");
		findView();

		Text1.setText("欢迎你，"+spname);


		getBundle();
		setData();
		setClick();
		initStatusBar();
	}

	private void setClick() {

		R1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

                    //选择所有人
                    Intent intent = new Intent(Info_DoFlow2.this, ryxz_Fragment.class);
                    bundle=new Bundle();
                    bundle.putSerializable("NWorkToDo", nWorkToDo);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1);



			}
		});

		zlcl01_B3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(spms.getText().toString().contains("默认")||spms.getText().toString().contains("自动选择"))
					{

					new AlertDialog.Builder(Info_DoFlow2.this)
					.setMessage("该审批模式下不能选择人员！")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										DialogInterface dialog,
										int which) {
								}
							}).show();

					return;

					}

				boolean havenet = NetHelper.IsHaveInternet(Info_DoFlow2.this);
				if (havenet) {

					Intent intent = new Intent(Info_DoFlow2.this,ChooseUserActivity2.class);
					bundle=new Bundle();
					bundle.putSerializable("NWorkToDo", nWorkToDo);
					intent.putExtras(bundle);
					startActivityForResult(intent, 1);
					//startActivity(intent);

				}

				else {
					new AlertDialog.Builder(Info_DoFlow2.this)
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
		});

		zlcl01_up.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
					nextuser = zlcl01_T311.getText().toString();
					if (nextuser.equals("") ) {
						new AlertDialog.Builder(Info_DoFlow2.this)
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
						boolean havenet = NetHelper	.IsHaveInternet(Info_DoFlow2.this);
						if (havenet) {
							progressDialog = ProgressDialog.show(Info_DoFlow2.this, "提交中",
									"请稍等...");
							new Thread() {
								@Override
								public void run() {
									String tiaoj="0";

									json = WebServiceUtil.AddBanLi(
											Integer.parseInt(id),
											Integer.parseInt(wid),
											nWorkToDo.getFromContentString(),
											zlcl01_T311.getText().toString(),
											tiaoj,
											"",
											Integer.parseInt(((SpinnerData2)lcxzSpinner.getSelectedItem()).getValue()),
											""+spname+"","");

									message = new Message();
									message.what = 2;
									handler.sendMessage(message);
								}
							}.start();
						} else {
							new AlertDialog.Builder(Info_DoFlow2.this)
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

	private List<String> getSD() {
		List<String> it = new ArrayList<String>();
		File f = new File(sdcardDir + "/woyeapp/fileforhtml/" + iZLID + "/");
		if (!f.exists()) {
			// Log.d("yin","1无图片");
			return null;
		} else {
			File[] files = f.listFiles();
			if (files.length == 0) {
				// Log.d("yin","2无图片");
				return null;
			} else {
				for (int i = 0; i < files.length; i++) {
					// Log.d("yin","有图片");
					File file = files[i];
					if (getImageFile(file.getPath()))
						it.add(file.getPath());
				}
				return it;
			}
		}
	}

	@SuppressLint("DefaultLocale")
	private boolean getImageFile(String fName) {
		boolean re;
		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length())
				.toLowerCase();
		if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			re = true;
		} else {
			re = false;
		}
		return re;
	}

	private void findView() {
		zlcl01_B3 = (Button) findViewById(R.id.zlcl01_B3);
		zlcl01_T3 = (TextView) findViewById(R.id.zlcl01_T3);
		zlcl01_up = (Button) findViewById(R.id.zlcl01_up);
		zlcl01_T2 = (EditText) findViewById(R.id.zlcl01_T2);
		lcxzSpinner = (Spinner) findViewById(R.id.S1);
		spms = (TextView) findViewById(R.id.t2);
		psms = (TextView) findViewById(R.id.t1);
		tjxz = (CheckBox) findViewById(R.id.c1);
		Text1=(TextView)findViewById(R.id.Text1);


		zlcl01_T31 = (EditText) findViewById(R.id.zlcl01_T31);
		zlcl01_B31= (Button) findViewById(R.id.zlcl01_B31);
		zlcl01_T311 = (TextView) findViewById(R.id.zlcl01_T311);
		R1= (RelativeLayout) findViewById(R.id.R1);
		R2= (RelativeLayout) findViewById(R.id.R2);
		R2.setVisibility(View.GONE);
	}

	private void getBundle() {
		Intent intent = this.getIntent();
		Bundle bundle=this.getIntent().getExtras();
		nWorkToDo = (NWorkToDo) intent.getSerializableExtra("NWorkToDo");
		sNrString=bundle.getString("sNR");
		String name=nWorkToDo.getPSMS();
	}

	private void getListItems() {
		boolean havenet = NetHelper.IsHaveInternet(Info_DoFlow2.this);
		if (havenet) {
			progressDialog = ProgressDialog.show(Info_DoFlow2.this, "正在登录中",
					"请稍等...");
			new Thread() {
				@Override
				public void run() {

					json = WebServiceUtil.everycanforStr2("wid", "", "", "",
							"", "", "" + iZLID + "", "", "", "", "", 0,
							"GetNextNode");

					if (json != null && !json.equals("0")) {
						JSONArray jsonObjs;
						JSONObject jsonObj;
						JSONTokener jsonTokener = new JSONTokener(json);
						listitem = new ArrayList<WorkFlowItem>();
						try {

							jsonObjs = (JSONArray) jsonTokener.nextValue();
							for (int i = 0; i < jsonObjs.length(); i++) {
								jsonObj = (JSONObject) jsonObjs.opt(i);
								WorkFlowItem email = new WorkFlowItem();
								email.setValueString(jsonObj
										.getString("Values"));
								email.setTextString(jsonObj.getString("Texts"));
								email.setTijaoString(jsonObj
										.getString("Tiaojian"));
								email.setSpmsString(jsonObj.getString("Spms"));
								email.setPsmsString(jsonObj.getString("Psms"));
								email.setMrsprString(jsonObj.getString("Mrspr"));
								listitem.add(email);
							}
							message = new Message();
							message.what = 3;
							handler.sendMessage(message);
						} catch (Exception e) {
							// TODO: handle exception
						}

					}

				}
			}.start();

		} else {
			new AlertDialog.Builder(Info_DoFlow2.this)
					.setMessage("请检查网络连接设置！")
					.setTitle("无网络连接")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
                                                    int which) {
								}
							}).show();

		}
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
			StatusBarUtils.setStatusBarColor(Info_DoFlow2.this, R.color.btground);
		}
	}
}
