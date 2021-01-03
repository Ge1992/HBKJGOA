package com.example.hbkjgoa.news;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.TXL;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressLint({ "WorldWriteableFiles", "WorldReadableFiles", "HandlerLeak", "InflateParams" })
public class TXLActivity_n extends Activity{


	private String json,searchstr="",departmentname="";
	private NoteBookAdapter adapter;
	private TXL ap;
	private int pid=1041;
	private int step=1;
	private String pid3="1041";
	private boolean webbing=false;
	private List<TXL> list;
	private List<TXL> list2;
	private  String ubmid,role;
	private ListView tab2_list;
	private Button login_reback_btn,serch;
	private Boolean iswebbing=false;
	private ImageView imahe;

	private Handler handler =new Handler(){
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {

			if (msg.what==1) {
				adapter = new NoteBookAdapter(TXLActivity_n.this,(List<TXL>)msg.obj);
				tab2_list.setAdapter(adapter);
				webbing=false;
				list2=(List<TXL>)msg.obj;
			}else if (msg.what==2) {

				for (int i = 0; i < list.size(); i++) {
					list2.add(list.get(i));
				}
				adapter.sendlist(list2);
				adapter.notifyDataSetChanged();
				webbing=false;
			}else if (msg.what==3) {
				adapter.sendlist(list);
				adapter.notifyDataSetChanged();
				webbing=false;
				pid3=pid3+","+pid;
				step++;
				list2=list;
			}else if (msg.what==4) {
				if (adapter!=null) {
					adapter.sendlist(list);
					adapter.notifyDataSetChanged();
					webbing=false;
					String[] a=pid3.split(",");
					String nstr="";
					for(int i=0;i<a.length-1;i++){
						nstr=nstr+a[i]+",";
					}
					pid3=nstr.substring(0, nstr.length()-1);
					step--;
					list2=(List<TXL>)msg.obj;
				}
			}else if (msg.what==5) {
				adapter.sendlist(list);
				adapter.notifyDataSetChanged();
				webbing=false;
				if(step==1){
					pid3=pid3+","+pid;
					step++;
				}
				list2=list;
			}

		}

	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.txl2);

		findView();
		//1是初始化，2是添加，3是清除刷新(list点击),4是清除刷新(返回)
		getInfo(1);
		setClick();
		initStatusBar();

	}

	private boolean back(){

		if (step==1) {

			finish();
			return false;
		}else {
			if(step==2){
				login_reback_btn.setVisibility(View.GONE);
				imahe.setVisibility(View.VISIBLE);
			}
			String[] a=pid3.split(",");
			pid=Integer.parseInt(a[step-2]);
			departmentname="";
			getInfo(4);
			return true;
		}


	}

	private void setClick() {

		serch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!iswebbing) {
					iswebbing=true;
					final EditText inputServer = new EditText(TXLActivity_n.this);

					new AlertDialog.Builder(TXLActivity_n.this).setTitle("请输入姓名或手机号码")//使用默认设备 浅色主题
							.setView(inputServer)
							.setNegativeButton("取消",new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									iswebbing=false;
								}
							}).setOnCancelListener(new OnCancelListener() {
						@Override
						public void onCancel(DialogInterface arg0) {
							iswebbing=false;
						}
					}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {


							searchstr=inputServer.getText().toString();
						//	login_reback_btn.setVisibility(View.VISIBLE);
							imahe.setVisibility(View.VISIBLE);
						//	getSearch();
							imahe.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									finish();
								}
							});
							getInfo(1);
							iswebbing=false;
							dialog.dismiss();
						}

					}).show();
				}

			}
		});

		imahe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				back();
			}
		});


		tab2_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
									long arg3) {
				if(webbing){
					return;
				}else{
					//
					if(list2.get(position).getFL().equals("R")){

						if(list2.get(position).getBGSHM().equals("")){
							Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+list2.get(position).getSJHM()));
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);

						}else{

						/*	Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+list2.get(position).getSJHM()));
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);*/

						/*	final String sjhm=list2.get(position).getSJHM();
							final String BGSHM=list2.get(position).getBGSHM();

							AlertDialog.Builder builder = new AlertDialog.Builder(TXLActivity.this);
				            builder.setItems(new String[]{"拨打电话"}, new DialogInterface.OnClickListener() {
				                 public void onClick(DialogInterface dialog, int which) {

				                	 if(which==0){
				                		 if(BGSHM.contains("/")){

				                			final String a[]=BGSHM.split("/");
				                			AlertDialog.Builder builder = new AlertDialog.Builder(TXLActivity.this);
				 				            builder.setItems(a, new DialogInterface.OnClickListener() {
				 				                 public void onClick(DialogInterface dialog, int which) {
			 				                		Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+a[which]));
			 										intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 										startActivity(intent);
				 				                 }
				 				             });
				 					         builder.create().show();

				                		 }else{

				                				 Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+sjhm));
												 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
												 startActivity(intent);

				                	 }
				                	 else{
				                		Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+sjhm));
										intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										startActivity(intent);
				                	 }
				                 }
				             });
					         builder.create().show();*/


							final String sjhm=list2.get(position).getSJHM();
							final String BGSHM=list2.get(position).getBGSHM();

							AlertDialog.Builder builder = new AlertDialog.Builder(TXLActivity_n.this);
							builder.setItems(new String[]{BGSHM,sjhm}, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {

									if(which==0){
										if(BGSHM.contains("/")){

											final String a[]=BGSHM.split("/");
											AlertDialog.Builder builder = new AlertDialog.Builder(TXLActivity_n.this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
											builder.setItems(a, new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog, int which) {
													Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+a[which]));
													intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
													startActivity(intent);
												}
											});
											builder.create().show();

										}else{
											Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+BGSHM));
											intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
											startActivity(intent);
										}

									}else{
										Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+sjhm));
										intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									startActivity(intent);
									}
								}
							});
							builder.create().show();
						}

					}else {
					//	login_reback_btn.setVisibility(View.VISIBLE);
						pid=(Integer)list2.get(position).getID();
						departmentname=list2.get(position).getMC();
						getInfo(3);
						imahe.setVisibility(View.VISIBLE);
					}

				}
			}
		});

		tab2_list.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		tab2_list.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
					// 当不滚动时
					case OnScrollListener.SCROLL_STATE_IDLE:
						// 判断滚动到底部
	                /*if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
	                	//if (!thread_open&&PageNo<totals) {
	                		//ispool=true;
	                	Log.d("yin","到底部的事件");
	                		if (!webbing) {
	                			pageNo++;
		                		getInfo(2);
							}


						//}
	                }else if (view.getFirstVisiblePosition()==0) {
	                	//if (!thread_open&&PageNo<totals) {
	                	Log.d("yin","到顶部的事件");
	                	if (!webbing) {
	                		pageNo=1;
	                		getInfo(3);
						}
						//}
					}*/

						break;
				}

			}
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
			}
		});


	}

	private void findView() {
		tab2_list=(ListView)findViewById(R.id.tab2_list);
		login_reback_btn=(Button)findViewById(R.id.login_reback_btn);
		imahe=(ImageView) findViewById(R.id.imahe);
		serch=(Button)findViewById(R.id.serch);
	}


	private void getInfo(final int a) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				webbing=true;
				json= WebServiceUtil.everycanforStr4("departmentname","search","","","","pid",
						departmentname,searchstr, "",0,0,pid,"GetTXLByLX");
				Log.d("yin","通讯录"+json);
				JSONArray jsonObjs;
				JSONObject jsonObj;
				if (json!=null) {
					JSONTokener jsonTokener=new JSONTokener(json);
					try {

						jsonObjs=(JSONArray) jsonTokener.nextValue();
						list = new ArrayList<TXL>();
						for(int i = 0; i < jsonObjs.length() ; i++)
						{
							TXL ap = new TXL();
							jsonObj = (JSONObject)jsonObjs.opt(i);

							ap.setID(Integer.parseInt(jsonObj.getString("ID")));

							if (jsonObj.getString("MC")!=null) {

								if(!jsonObj.getString("ZW").equals("")){
									//	ap.setMC(jsonObj.getString("MC")+"（"+jsonObj.getString("ZW")+"）");
									ap.setMC(jsonObj.getString("MC"));
								}else{
									ap.setMC(jsonObj.getString("MC"));
								}

							}else {
								ap.setMC("");
							}
							ap.setZFHS(jsonObj.getString("ZFHS"));
							ap.setSSBM(jsonObj.getString("SSBM"));
							ap.setSJHM(jsonObj.getString("SJHM"));
							ap.setBGSHM(jsonObj.getString("BGSHM"));
							ap.setFL(jsonObj.getString("FL"));
							ap.setFID(jsonObj.getString("FID"));
							ap.setZW(jsonObj.getString("ZW"));
							list.add(ap);


						}

						Message msg = new Message();
						msg.what = a;
						msg.obj=list;
						handler.sendMessage(msg);
					}catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}













	public class NoteBookAdapter extends BaseAdapter {

		private Context context;
		private LayoutInflater inflater;
		private List<TXL> list;
		@SuppressWarnings("unused")
		private boolean mBusy = false;

		public NoteBookAdapter(Activity activity,List<TXL> list) {
			this.context = activity;
			this.list = list;
			inflater = LayoutInflater.from(context);
		}

		public void sendlist(List<TXL> list){
			this.list =list;
		}

		@Override
		public int getCount() {
			if (list==null) {
				return 0;
			}else {
				return list.size();
			}
		}

		@Override
		public Object getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView =inflater.inflate(R.layout.txl_utemn, null);
				holder = new ViewHolder();

				holder.R1=(RelativeLayout)convertView.findViewById(R.id.R1);
				holder.R2=(RelativeLayout)convertView.findViewById(R.id.R2);

				holder.bm=(TextView)convertView.findViewById(R.id.bm);
				holder.mc=(TextView)convertView.findViewById(R.id.mc);
				holder.sjhm=(TextView)convertView.findViewById(R.id.sjhm);
				holder.zf=(TextView)convertView.findViewById(R.id.zf);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			if(list.get(position).getFL().equals("R")){
				holder.R1.setVisibility(View.GONE);
				holder.R2.setVisibility(View.VISIBLE);

				//	holder.mc.setText(list.get(position).getMC()+"      ("+list.get(position).getZW()+")");

				holder.mc.setText(list.get(position).getMC()+"    "+list.get(position).getFID());

				if(list.get(position).getFID().equals("0")){
					holder.mc.setText(list.get(position).getMC());
				}else {
					holder.mc.setText(list.get(position).getMC()+"    "+list.get(position).getFID());
				}



				holder.zf.setText(list.get(position).getSSBM());



				//	holder.zf.setText("("+list.get(position).getSSBM()+"  走访户数:"+list.get(position).getZFHS()+")");
				System.out.println("qwerr"+list.get(position).getSSBM());
				if(list.get(position).getBGSHM().equals("")){
					holder.sjhm.setText(list.get(position).getSJHM());
				}else{
					holder.sjhm.setText(list.get(position).getSJHM()+"\n"+list.get(position).getBGSHM());
				}


			}else {
				holder.R1.setVisibility(View.VISIBLE);
				holder.R2.setVisibility(View.GONE);
				holder.bm.setText(list.get(position).getMC());

			}

			return convertView;
		}
		class ViewHolder {
			public TextView bm;
			public TextView mc;
			public TextView sjhm;
			public TextView zf;
			public RelativeLayout R1;
			public RelativeLayout R2;
		}
	}





	private void initStatusBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			//    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
			StatusBarUtils.setStatusBarColor(TXLActivity_n.this, R.color.btground);
		}
	}





	public void bt_back(View v) {
		this.finish();
	}



}
