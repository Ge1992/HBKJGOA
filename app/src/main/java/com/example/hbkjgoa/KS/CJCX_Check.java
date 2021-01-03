package com.example.hbkjgoa.KS;

import com.alibaba.fastjson.JSONObject;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.CJCX;
import com.example.hbkjgoa.model.CJCX_Beans;
import com.example.hbkjgoa.model.Tilb;
import com.example.hbkjgoa.model.TxList;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

@SuppressLint({ "SimpleDateFormat", "WorldReadableFiles",
		"WorldWriteableFiles", "HandlerLeak" })
public class CJCX_Check extends Activity {

	private LinearLayout check_1,check_2,check_3,check_4,check_5,check_10;
	private Button bt1,bt2,back;
	private ImageView login_check1,login_check2,login_check3,login_check4,login_check5,login_check10;
	private TextView add,show,login_checkt1,login_checkt2,login_checkt3,login_checkt4,login_checkt5,login_checkt10,te2,main_title,zong;
	private CJCX email;
	public static CJCX_Check badBehavior_Add;
	private String json, json2 = "",cuotistr, userid;
	private List<TxList> listItems = new ArrayList<TxList>();
	private int tihao=0,nowtihao=1;
	private boolean iscuoti=false;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			finish();

			return true;//return true;拦截事件传递,从而屏蔽back键。
		}
		if (KeyEvent.KEYCODE_HOME == keyCode) {

			Toast.makeText(CJCX_Check.this, "当前正在考试！", Toast.LENGTH_SHORT).show();
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
					CJCX_Beans jj =JSONObject.parseObject(json, CJCX_Beans.class);
					listItems = jj.getTxList();
					setInfo();
				}

			} else if (msg.what == 2) {
				if (json2 == null || json2.equals("")) {
					Toast.makeText(CJCX_Check.this,"提交失败！", Toast.LENGTH_LONG).show();
				} else{
					Toast.makeText(CJCX_Check.this,"提交成功！", Toast.LENGTH_LONG).show();
					finish();
					if(SJ_List.listact!=null){
						SJ_List.listact.finish();
					}
					if(CJCX_List.listact!=null){
						CJCX_List.listact.onRefresh();
					}


				}


			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(0x80000000, 0x80000000);
		setContentView(R.layout.cjcx_check);
		badBehavior_Add = this;
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

				if(iscuoti){

					String[] aa=cuotistr.split(",");
					int nowp=0;
					int nowp2=0;
					for(int j=0;j<aa.length;j++){
						if(Integer.parseInt(aa[j])==(nowtihao)){
							if(j==0){
								nowp2=1;
							}
							nowp=j-1;
						}
					}
					tihao=0;
					if(nowp2==0){
						nowtihao=Integer.parseInt(aa[nowp]);
					}
					setInfo();


				}else{
					if(nowtihao!=1){
						tihao=0;
						nowtihao--;
						setInfo();
					}
				}

			}
		});

		//下一題
		bt2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(iscuoti){

					String[] aa=cuotistr.split(",");
					int nowp=0;
					int nowp2=0;
					for(int j=0;j<aa.length;j++){
						if(Integer.parseInt(aa[j])==(nowtihao)){
							if(j==aa.length-1){
								nowp2=1;
							}
							nowp=j+1;
						}
					}
					tihao=0;
					if(nowp2==0){
						nowtihao=Integer.parseInt(aa[nowp]);
					}
					setInfo();

				}else{
					if(nowtihao<tihao){
						tihao=0;
						nowtihao++;
						setInfo();
					}
				}

			}
		});

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		check_10.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				if(iscuoti){
					login_check10.setImageResource(R.mipmap.checkbox_n);
					iscuoti=false;
					tihao=0;
					nowtihao=1;
					setInfo();
				}else{

					if(cuotistr.equals("")){
						Toast.makeText(CJCX_Check.this,"您答题全对，不需要看错题！",Toast.LENGTH_LONG).show();
						return;
					}

					login_check10.setImageResource(R.mipmap.checkbox_p);
					iscuoti=true;

					String[] aa=cuotistr.split(",");
					tihao=0;
					nowtihao=Integer.parseInt(aa[0]);
					setInfo();
				}

			}
		});

	}

	protected void setInfo() {

		cuotistr="";

		if(listItems!=null&&listItems.size()>0){
			for(int i=0;i<listItems.size();i++){
				TxList aa=listItems.get(i);
				List<Tilb> bb=listItems.get(i).getTilb();
				if(bb!=null&&bb.size()>0){

					for(int j=0;j<bb.size();j++){
						tihao++;
						//当当前未按过最上面的题号时，显示第一题
						if(nowtihao==1){
							//第一个高亮显示
							if(i==0&&j==0){
								showTM(i,j);
							}

							//按过最上面行题号之后
						}else{
							if(tihao==nowtihao){
								showTM(i,j);
							}

						}

						//判断错题显示
						if(listItems.get(i).getTilb().get(j).getDeFen().equals("0.0")){
							cuotistr=cuotistr+tihao+",";
						}


					}

				}
			}

			if(cuotistr.length()>1){
				cuotistr=cuotistr.substring(0, cuotistr.length()-1);
			}


			if(!cuotistr.equals("")){
				String[] aa=cuotistr.split(",");
				login_checkt10.setText("只看错题（"+aa.length+"）");
			}

			te2.setText(nowtihao+"/"+tihao);
		}

	}


	private void showTM(final int i1,final int j1) {

		show.setText(Html.fromHtml("<font color='#FF0000'>["+listItems.get(i1).getTilb().get(j1).getFenLeiStr()+"]</font>"+listItems.get(i1).getTilb().get(j1).getTitleStr()));
		zong.setText(listItems.get(i1).getTilb().get(j1).getJieGuoStr());

	
		//判断题
		if(listItems.get(i1).getTilb().get(j1)!=null&&listItems.get(i1).getTilb().get(j1).getFenLeiStr().equals("判断题")){
			if(listItems.get(i1).getTilb().get(j1).getItemsA()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsA().equals("")){
				login_checkt1.setText("A: "+listItems.get(i1).getTilb().get(j1).getItemsA());
				check_1.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getDaAn().contains("A")){
					login_check1.setImageResource(R.mipmap.checkbox_p);
				}else if(listItems.get(i1).getTilb().get(j1).getUserDaAn()!=null&&listItems.get(i1).getTilb().get(j1).getUserDaAn().equals(listItems.get(i1).getTilb().get(j1).getDaAn())){
					login_check1.setImageResource(R.mipmap.checkbox_n);
				}else{
					login_check1.setImageResource(R.mipmap.checkbox_e);
				}
			}
			if(listItems.get(i1).getTilb().get(j1).getItemsB()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsB().equals("")){
				login_checkt2.setText("B: "+listItems.get(i1).getTilb().get(j1).getItemsB());
				check_2.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getDaAn().contains("B")){
					login_check2.setImageResource(R.mipmap.checkbox_p);
				}else if(listItems.get(i1).getTilb().get(j1).getUserDaAn()!=null&&listItems.get(i1).getTilb().get(j1).getUserDaAn().equals(listItems.get(i1).getTilb().get(j1).getDaAn())){
					login_check2.setImageResource(R.mipmap.checkbox_n);
				}else{
					login_check2.setImageResource(R.mipmap.checkbox_e);
				}
			}

			check_3.setVisibility(View.GONE);
			check_4.setVisibility(View.GONE);
			check_5.setVisibility(View.GONE);


			//单项选择题
		}else if(listItems.get(i1).getTilb().get(j1)!=null&&listItems.get(i1).getTilb().get(j1).getFenLeiStr().equals("单项选择题")){

			if(listItems.get(i1).getTilb().get(j1).getItemsA()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsA().equals("")){
				login_checkt1.setText("A: "+listItems.get(i1).getTilb().get(j1).getItemsA());
				check_1.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getDaAn().contains("A")){
					login_check1.setImageResource(R.mipmap.checkbox_p);
				}else if(listItems.get(i1).getTilb().get(j1).getUserDaAn()==null||(!listItems.get(i1).getTilb().get(j1).getUserDaAn().equals("A")&&!listItems.get(i1).getTilb().get(j1).getDaAn().equals("A"))){
					login_check1.setImageResource(R.mipmap.checkbox_n);
				}else{
					login_check1.setImageResource(R.mipmap.checkbox_e);
				}

			}
			if(listItems.get(i1).getTilb().get(j1).getItemsB()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsB().equals("")){
				login_checkt2.setText("B: "+listItems.get(i1).getTilb().get(j1).getItemsB());
				check_2.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getDaAn().contains("B")){
					login_check2.setImageResource(R.mipmap.checkbox_p);
				}else if(listItems.get(i1).getTilb().get(j1).getUserDaAn()==null||(!listItems.get(i1).getTilb().get(j1).getUserDaAn().equals("B")&&!listItems.get(i1).getTilb().get(j1).getDaAn().equals("B"))){
					login_check2.setImageResource(R.mipmap.checkbox_n);
				}else{
					login_check2.setImageResource(R.mipmap.checkbox_e);
				}

			}
			if(listItems.get(i1).getTilb().get(j1).getItemsC()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsC().equals("")){
				login_checkt3.setText("C: "+listItems.get(i1).getTilb().get(j1).getItemsC());
				check_3.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getDaAn().contains("C")){
					login_check3.setImageResource(R.mipmap.checkbox_p);
				}else if(listItems.get(i1).getTilb().get(j1).getUserDaAn()==null||(!listItems.get(i1).getTilb().get(j1).getUserDaAn().equals("C")&&!listItems.get(i1).getTilb().get(j1).getDaAn().equals("C"))){
					login_check3.setImageResource(R.mipmap.checkbox_n);
				}else{
					login_check3.setImageResource(R.mipmap.checkbox_e);
				}

			}else{
				check_3.setVisibility(View.GONE);
			}
			if(listItems.get(i1).getTilb().get(j1).getItemsD()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsD().equals("")){
				login_checkt4.setText("D: "+listItems.get(i1).getTilb().get(j1).getItemsD());
				check_4.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getDaAn().contains("D")){
					login_check4.setImageResource(R.mipmap.checkbox_p);
				}else if(listItems.get(i1).getTilb().get(j1).getUserDaAn()==null||(!listItems.get(i1).getTilb().get(j1).getUserDaAn().equals("D")&&!listItems.get(i1).getTilb().get(j1).getDaAn().equals("D"))){
					login_check4.setImageResource(R.mipmap.checkbox_n);
				}else{
					login_check4.setImageResource(R.mipmap.checkbox_e);
				}

			}else{
				check_4.setVisibility(View.GONE);
			}
			if(listItems.get(i1).getTilb().get(j1).getItemsE()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsE().equals("")){
				login_checkt5.setText("E: "+listItems.get(i1).getTilb().get(j1).getItemsE());
				check_5.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getDaAn().contains("E")){
					login_check5.setImageResource(R.mipmap.checkbox_p);
				}else if(listItems.get(i1).getTilb().get(j1).getUserDaAn()==null||(!listItems.get(i1).getTilb().get(j1).getUserDaAn().equals("E")&&!listItems.get(i1).getTilb().get(j1).getDaAn().equals("E"))){
					login_check5.setImageResource(R.mipmap.checkbox_n);
				}else{
					login_check5.setImageResource(R.mipmap.checkbox_e);
				}
			}else{
				check_5.setVisibility(View.GONE);
			}


			//多项选择题
		}else if(listItems.get(i1).getTilb().get(j1)!=null&&listItems.get(i1).getTilb().get(j1).getFenLeiStr().equals("多项选择题")){

			if(listItems.get(i1).getTilb().get(j1).getItemsA()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsA().equals("")){
				login_checkt1.setText("A: "+listItems.get(i1).getTilb().get(j1).getItemsA());
				check_1.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getDaAn().contains("A")){
					login_check1.setImageResource(R.mipmap.checkbox_p);
				}else if(listItems.get(i1).getTilb().get(j1).getUserDaAn()==null||!listItems.get(i1).getTilb().get(j1).getUserDaAn().contains("A")){
					login_check1.setImageResource(R.mipmap.checkbox_n);
				}else{
					login_check1.setImageResource(R.mipmap.checkbox_e);
				}

			}
			if(listItems.get(i1).getTilb().get(j1).getItemsB()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsB().equals("")){
				login_checkt2.setText("B: "+listItems.get(i1).getTilb().get(j1).getItemsB());
				check_2.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getDaAn().contains("B")){
					login_check2.setImageResource(R.mipmap.checkbox_p);
				}else if(listItems.get(i1).getTilb().get(j1).getUserDaAn()==null||!listItems.get(i1).getTilb().get(j1).getUserDaAn().contains("B")){
					login_check2.setImageResource(R.mipmap.checkbox_n);
				}else{
					login_check2.setImageResource(R.mipmap.checkbox_e);
				}

			}
			if(listItems.get(i1).getTilb().get(j1).getItemsC()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsC().equals("")){
				login_checkt3.setText("C: "+listItems.get(i1).getTilb().get(j1).getItemsC());
				check_3.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getDaAn().contains("C")){
					login_check3.setImageResource(R.mipmap.checkbox_p);
				}else if(listItems.get(i1).getTilb().get(j1).getUserDaAn()==null||!listItems.get(i1).getTilb().get(j1).getUserDaAn().contains("C")){
					login_check3.setImageResource(R.mipmap.checkbox_n);
				}else{
					login_check3.setImageResource(R.mipmap.checkbox_e);
				}

			}else{
				check_3.setVisibility(View.GONE);
			}
			if(listItems.get(i1).getTilb().get(j1).getItemsD()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsD().equals("")){
				login_checkt4.setText("D: "+listItems.get(i1).getTilb().get(j1).getItemsD());
				check_4.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getDaAn().contains("D")){
					login_check4.setImageResource(R.mipmap.checkbox_p);
				}else if(listItems.get(i1).getTilb().get(j1).getUserDaAn()==null||!listItems.get(i1).getTilb().get(j1).getUserDaAn().contains("D")){
					login_check4.setImageResource(R.mipmap.checkbox_n);
				}else{
					login_check4.setImageResource(R.mipmap.checkbox_e);
				}

			}else{
				check_4.setVisibility(View.GONE);
			}
			if(listItems.get(i1).getTilb().get(j1).getItemsE()!=null&&!listItems.get(i1).getTilb().get(j1).getItemsE().equals("")){
				login_checkt5.setText("E: "+listItems.get(i1).getTilb().get(j1).getItemsE());
				check_5.setVisibility(View.VISIBLE);
				if(listItems.get(i1).getTilb().get(j1).getDaAn().contains("E")){
					login_check5.setImageResource(R.mipmap.checkbox_p);
				}else if(listItems.get(i1).getTilb().get(j1).getUserDaAn()==null||!listItems.get(i1).getTilb().get(j1).getUserDaAn().contains("E")){
					login_check5.setImageResource(R.mipmap.checkbox_n);
				}else{
					login_check5.setImageResource(R.mipmap.checkbox_e);
				}

			}else{
				check_5.setVisibility(View.GONE);
			}



		}


	}


	private void checkstate() {

		email=(CJCX)getIntent().getSerializableExtra("YZM");
		main_title.setText(email.getShiJuanName()+"");

		new Thread(new Runnable() {
			@Override
			public void run() {

				json = WebServiceUtil.GetKSJieGuoDetail(email.getID(), userid);

				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);

			}
		}).start();

	}

	private void findView() {

		back = (Button) findViewById(R.id.back);
		add = (TextView) findViewById(R.id.add);
		show= (TextView) findViewById(R.id.show);
		te2= (TextView) findViewById(R.id.te2);
		main_title= (TextView) findViewById(R.id.te1);
		zong= (TextView) findViewById(R.id.zong);

		check_1 = (LinearLayout) findViewById(R.id.check_1);
		check_2 = (LinearLayout) findViewById(R.id.check_2);
		check_3 = (LinearLayout) findViewById(R.id.check_3);
		check_4 = (LinearLayout) findViewById(R.id.check_4);
		check_5 = (LinearLayout) findViewById(R.id.check_5);
		check_10 = (LinearLayout) findViewById(R.id.check_10);

		login_check1=(ImageView) findViewById(R.id.login_check1);
		login_check2=(ImageView) findViewById(R.id.login_check2);
		login_check3=(ImageView) findViewById(R.id.login_check3);
		login_check4=(ImageView) findViewById(R.id.login_check4);
		login_check5=(ImageView) findViewById(R.id.login_check5);
		login_check10=(ImageView) findViewById(R.id.login_check10);

		login_checkt1= (TextView) findViewById(R.id.login_checkt1);
		login_checkt2= (TextView) findViewById(R.id.login_checkt2);
		login_checkt3= (TextView) findViewById(R.id.login_checkt3);
		login_checkt4= (TextView) findViewById(R.id.login_checkt4);
		login_checkt5= (TextView) findViewById(R.id.login_checkt5);
		login_checkt10= (TextView) findViewById(R.id.login_checkt10);

		bt1 = (Button) findViewById(R.id.bt1);
		bt2 = (Button) findViewById(R.id.bt2);

		userid = getSharedPreferences("sdlxLogin",
				Context.MODE_PRIVATE + Context.MODE_PRIVATE)
				.getString("spname", "");

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

			if(iscuoti){

				String[] aa=cuotistr.split(",");
				int nowp=0;
				int nowp2=0;
				for(int j=0;j<aa.length;j++){
					if(Integer.parseInt(aa[j])==(nowtihao)){
						if(j==aa.length-1){
							nowp2=1;
						}
						nowp=j+1;
					}
				}
				tihao=0;
				if(nowp2==0){
					nowtihao=Integer.parseInt(aa[nowp]);
				}
				setInfo();

			}else{
				if(nowtihao<tihao){
					tihao=0;
					nowtihao++;
					setInfo();
				}
			}

			return super.left();
		}

		@Override
		public boolean right() {

			if(iscuoti){

				String[] aa=cuotistr.split(",");
				int nowp=0;
				int nowp2=0;
				for(int j=0;j<aa.length;j++){
					if(Integer.parseInt(aa[j])==(nowtihao)){
						if(j==0){
							nowp2=1;
						}
						nowp=j-1;
					}
				}
				tihao=0;
				if(nowp2==0){
					nowtihao=Integer.parseInt(aa[nowp]);
				}
				setInfo();


			}else{
				if(nowtihao!=1){
					tihao=0;
					nowtihao--;
					setInfo();
				}
			}

			return super.right();
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
			StatusBarUtils.setStatusBarColor(CJCX_Check.this, R.color.btground);
		}
	}

	public void bt_back(View v) {
		finish();
	}
}
