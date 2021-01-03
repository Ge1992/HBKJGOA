package com.example.hbkjgoa.xmjd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.XMJDA_Fdapter;
import com.example.hbkjgoa.model.XMJD_Bean;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.ToastUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.xlistview.XListView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class xmjd_Fragment extends FragmentActivity implements AdapterView.OnItemClickListener, XListView.IXListViewListener {
    private RelativeLayout R1,R2 ,R3,R4,R5;
    private LoadingDialog dialog1;
    private String json,spname;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
    private List<XMJD_Bean.projProgress> list=new ArrayList<XMJD_Bean.projProgress>();
    private List<XMJD_Bean> list1=new ArrayList<XMJD_Bean>();
    private XListView mListView;
    private XMJD_Bean ap;
    private XMJDA_Fdapter adapter;
    TextView t1,t2,t3,t4,t5,t6,L1,L2,L3;
    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                dialog1.dismiss();
                setData(list);


            } else if (msg.what == 2) {
                list.clear();
                list = (List<XMJD_Bean.projProgress>) msg.obj;
                adapter.setmes((List<XMJD_Bean.projProgress>) msg.obj);
                mListView.stopRefresh();
                adapter.notifyDataSetChanged();
                mListView.setRefreshTime("更新于：" + dateFormat.format(new Date(System.currentTimeMillis())));
            } else if (msg.what == 3) {
                adapter.setmes(list);
                mListView.stopLoadMore();
                adapter.notifyDataSetChanged();
            }else if (msg.what==4){
                for(int i=0;i<list1.size();i++){
                    t1.setText(list1.get(i).getProjectName());
                    t2.setText(list1.get(i).getProjectSerils());
                    t3.setText(list1.get(i).getSuoShuKeHu());
                    t4.setText(list1.get(i).getXiangMuJinE());
                    t5.setText(list1.get(i).getXiangMuDaXiao());
                    t6.setText(list1.get(i).getBackInfo());
                }
            }

        }

    };


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
        Intent intent = this.getIntent();
        ap=(XMJD_Bean)intent.getSerializableExtra("XQ");
        mListView =findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
        mListView.setAdapter(adapter);
        mListView.setXListViewListener(this);
        mListView.setPullLoadEnable(false);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        mListView.setPullRefreshEnable(false);
        mListView.setOnItemClickListener(this);

        initView();
        getinfo(0,3);
        initStatusBar();
    }
    private void setData(List<XMJD_Bean.projProgress> listitem) {
        adapter = new XMJDA_Fdapter(xmjd_Fragment.this, listitem);
        mListView.setAdapter(adapter);
    }
    private  void  findview(){
        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);
        t4=findViewById(R.id.t4);
        t5=findViewById(R.id.t5);
        t6=findViewById(R.id.t6);

        L1=findViewById(R.id.text1);
        L2=findViewById(R.id.text2);
        L3=findViewById(R.id.text3);
    }

    private void initView() {
        View view = LayoutInflater.from(xmjd_Fragment.this).inflate(R.layout.info_xmjd, mListView, false);
        mListView.addHeaderView(view);
        //禁止头部出现分割线
        mListView.setHeaderDividersEnabled(true);
        getInfo2();
        // 初始化控件
        findview();
        setOnclick();
    }

    private void setOnclick() {
        L1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getinfo(0,3);
                list.clear();
                L1.setTextColor(Color.rgb(255, 0, 0));
                L2.setTextColor(Color.rgb(0, 0, 0));
                L3.setTextColor(Color.rgb(0, 0, 0));

            }
        });
        L2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getinfo(3,4);
                list.clear();
                L2.setTextColor(Color.rgb(255, 0, 0));
                L1.setTextColor(Color.rgb(0, 0, 0));
                L3.setTextColor(Color.rgb(0, 0, 0));

            }
        });
        L3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getinfo(4,5);
                list.clear();
                L3.setTextColor(Color.rgb(255, 0, 0));
                L1.setTextColor(Color.rgb(0, 0, 0));
                L2.setTextColor(Color.rgb(0, 0, 0));

            }
        });

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
            StatusBarUtils.setStatusBarColor(xmjd_Fragment.this, R.color.btground);
        }
    }


    //详情
    private void getInfo2() {
        boolean havenet = NetHelper.IsHaveInternet(xmjd_Fragment.this);
        if (havenet) {

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        json = WebServiceUtil.everycanforStr("projID", "userName", "", "pageNo",ap.getID(), spname, "", 1, "Project_Details");

                        if(json != null && !json.equals("0")) {
                            JSONObject jObj = new JSONObject(json);
                            JSONObject jObj1 = jObj.getJSONObject("project");
                            for (int i = 0; i < jObj1.length(); i++) {
                                XMJD_Bean ap = new XMJD_Bean();
                                ap.setID(jObj1.getString("ID"));
                                ap.setProjectName(jObj1.getString("ProjectName"));
                                ap.setProjectSerils(jObj1.getString("ProjectSerils"));
                                ap.setSuoShuKeHu(jObj1.getString("SuoShuKeHu"));
                                ap.setYuJiChengJiaoRiQi(jObj1.getString("YuJiChengJiaoRiQi"));
                                ap.setTiXingDate(jObj1.getString("TiXingDate"));
                                ap.setFuZeRen(jObj1.getString("FuZeRen"));
                                ap.setXiangMuJinE(jObj1.getString("XiangMuJinE"));

                                ap.setXiangMuYuSuan(jObj1.getString("XiangMuYuSuan"));
                                ap.setXiangMuDaXiao(jObj1.getString("XiangMuDaXiao"));
                                ap.setPeiHeRenList(jObj1.getString("PeiHeRenList"));
                                ap.setUserName(jObj1.getString("UserName"));
                                ap.setTimeStr(jObj1.getString("TimeStr"));
                                ap.setHeTongAndFuJian(jObj1.getString("HeTongAndFuJian"));
                                ap.setBackInfo(jObj1.getString("BackInfo"));


                                list1.add(ap);
                            }

                            Message msg = new Message();
                            msg.what = 4;
                            handler.sendMessage(msg);
                        }

                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                }

            }).start();
        } else {
            ToastUtils.showToast(xmjd_Fragment.this, "网络连接失败！");
        }
    }



    //列表
    private void getinfo(final int a,final int b){
        boolean havenet = NetHelper.IsHaveInternet(xmjd_Fragment.this);
        if (havenet) {
            LoadingDialog.Builder builder1=new LoadingDialog.Builder(xmjd_Fragment.this)
                    .setMessage("加载中...")
                    .setCancelable(false);
            dialog1=builder1.create();
            dialog1.show();
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        json = WebServiceUtil.everycanforStr("projID", "userName", "", "pageNo",ap.getID(), spname, "", 1, "Project_Details");

                        if(json != null && !json.equals("0")) {
                            JSONTokener jsonTokener=new JSONTokener(json);
                            JSONObject person= (JSONObject) jsonTokener.nextValue();
                            JSONArray  jsonObjs=person.getJSONArray("projProgress");
                            for(int i = a; i < b ; i++){
                                XMJD_Bean.projProgress ap = new XMJD_Bean.projProgress();
                                JSONObject jsonObj = (JSONObject)jsonObjs.opt(i);
                                ap.setTypeName(jsonObj.getString("TypeName"));
                                ap.setFileCount(jsonObj.getString("FileCount"));
                                ap.setTypeID(jsonObj.getString("TypeID"));
                                JSONArray nodArray = jsonObj.getJSONArray("FileList");
                                List<XMJD_Bean.FileList> nodeItems = new ArrayList<XMJD_Bean.FileList>();
                                for (int j = 0; j < nodArray.length(); j++) {
                                    JSONObject nodejsonObj = (JSONObject) nodArray.opt(j);
                                    XMJD_Bean.FileList wf = new XMJD_Bean.FileList();
                                    wf.setID(nodejsonObj.getString("ID"));
                                    wf.setProjectID(nodejsonObj.getString("ProjectID"));
                                    wf.setTypeID(nodejsonObj.getString("TypeID"));
                                    wf.setFilePath(nodejsonObj.getString("FilePath"));
                                    wf.setFileName(nodejsonObj.getString("FileName"));
                                    wf.setUserName(nodejsonObj.getString("UserName"));
                                    wf.setUploadTime(nodejsonObj.getString("UploadTime"));


                                    nodeItems.add(wf);

                                }
                                ap.setFileLists(nodeItems);
                                list.add(ap);
                            }


                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }

                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                }

            }).start();
        } else {
            ToastUtils.showToast(xmjd_Fragment.this, "网络连接失败！");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
    public void bt_back(View v){

        finish();

    }

    private void initFragment(int index) {
        // 由于是引用了V4包下的Fragment，所以这里的管理器要用getSupportFragmentManager获取
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 开启事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 隐藏所有Fragment
        hideFragment(transaction);
        switch (index) {
            case 0:
                if (fragment1 == null) {
                    fragment1 = new xmjd_Fragment1();
                    Bundle bundle = new Bundle();
                    bundle.putString("method", " ");
                    fragment1.setArguments(bundle);

                    transaction.add(R.id.ZTQK_content, fragment1);
                } else {
                    transaction.show(fragment1);
                }
                break;
            case 1:
                if (fragment2 == null) {
                    fragment2 = new xmjd_Fragment1();
                    Bundle bundle = new Bundle();
                    bundle.putString("method", " ");
                    fragment2.setArguments(bundle);

                    transaction.add(R.id.ZTQK_content, fragment2);
                } else {
                    transaction.show(fragment2);
                }

                break;
            case 2:
                if (fragment3 == null) {
                    fragment3 = new xmjd_Fragment1();
                    Bundle bundle = new Bundle();
                    bundle.putString("method", " ");
                    fragment3.setArguments(bundle);

                    transaction.add(R.id.ZTQK_content, fragment3);
                } else {
                    transaction.show(fragment3);
                }

                break;
            default:
                break;
        }
        // 提交事务
        transaction.commit();
    } // 隐藏Fragment

    private void hideFragment(FragmentTransaction transaction) {
        if (fragment1 != null) {
            transaction.hide(fragment1);
        }
        if (fragment2 != null) {
            transaction.hide(fragment2);
        }
        if (fragment3 != null) {
            transaction.hide(fragment3);
        }

    }
}
