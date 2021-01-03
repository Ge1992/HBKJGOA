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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
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
import com.example.hbkjgoa.model.WorkFlowItem;
import com.example.hbkjgoa.rczyk.InfoWorkFlow_xq;
import com.example.hbkjgoa.ryxz.ryxz_Fragment;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 设备维修，申请耗材 新建功能
 */
@SuppressLint({"HandlerLeak", "UseSparseArrays", "WrongViewCast"})
public class Info_DoFlow_xj extends Activity {

private String valueid,textsid;
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
    private Button zlcl01_B3, zlcl01_B31;
    private Button zlcl01_up;
    private EditText zlcl01_T31;
    private EditText zlcl01_T2;
    private TextView spms, zlcl01_T3;
    private TextView psms, Text1;
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
    private static final int WHAT_DID_LOAD_DATA = 0;
    private String spname;
    private RelativeLayout R1, R2;
    private TextView zlcl01_T311;
    private String id = "", wid = "", value = "";
    String fomart[];
    private RadioGroup group; // 点选按钮组
    public static Info_DoFlow_xj listact;
    @Override
    protected void onDestroy() {
        if (ll != null) {

        }
        super.onDestroy();
    }

    public String getValueid() {
        return valueid;
    }

    public void setValueid(String valueid) {
        this.valueid = valueid;
    }

    public String getTextsid() {
        return textsid;
    }

    public void setTextsid(String textsid) {
        this.textsid = textsid;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

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
                            Info_DoFlow_xj.this);
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
                if (InfoWorkFlow2.mm != null) {
                    InfoWorkFlow2.mm.closehere();
                }
                if (InfoWorkFlow_xj.mm != null) {
                    InfoWorkFlow_xj.mm.finish();
                }
                if (InfoWorkFlow_xq.mm != null) {
                    InfoWorkFlow_xq.mm.finish();
                }
                finish();
            } else if (msg.what == 3) {
                setData();
            }
        }
    };

    private void setData() {


/*
        List<SpinnerData> lst = new ArrayList<SpinnerData>();


        for (WorkFlowItem item : listitem) {

            SpinnerData c = new SpinnerData(item.getValueString(),item.getTextString(),item.getMrsprString(),item.getSpmsString(),item.getPsmsString());
            lst.add(c);
            if(listitem.get(0).getTijaoString()=="1")
            {
                tjxz.setChecked(true);
            }
            psms.setText("评审模式："+listitem.get(0).getPsmsString());
            spms.setText("审批人选择模式："+listitem.get(0).getSpmsString());
            zlcl01_T3.setText(listitem.get(0).getMrsprString());
        }
        ArrayAdapter<SpinnerData> Adapter = new ArrayAdapter<SpinnerData>(this,
                android.R.layout.simple_spinner_item, lst);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lcxzSpinner.setAdapter(Adapter);
*/

        int i = 0;
        for (WorkFlowItem s : listitem) {

            RadioButton radio = new RadioButton(this);
            radio.setText(s.getTextString());
            radio.setId(i++);
            radio.setTextSize(16);
            group.addView(radio);
            group.check(0);

        }
        Info_DoFlow_xj.listact.setValueid(listitem.get(0).getValueString());
        Info_DoFlow_xj.listact.setTextsid(listitem.get(0).getTextString());

   //     Toast.makeText(Info_DoFlow_xj.this, "选取的值："+valueid, Toast.LENGTH_LONG).show();
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                RadioButton rb=(RadioButton)findViewById(checkedId);
                if(rb.isChecked()){
                    for (WorkFlowItem s : listitem) {
                        if(rb.getText().equals(s.getTextString())){
                            String	str=s.getTextString();
                            Info_DoFlow_xj.listact.setValueid(s.getValueString());
                            Info_DoFlow_xj.listact.setTextsid(s.getTextString());

                      //      Toast.makeText(Info_DoFlow_xj.this, "选取的值："+valueid, Toast.LENGTH_LONG).show();

                        }
                    }

                }
            }

        });




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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.info_doflow);
        listact = this;
        id = this.getIntent().getStringExtra("id");
        value = this.getIntent().getStringExtra("value");
        wid = this.getIntent().getStringExtra("wid");
        spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
        findView();
        getBundle();
        getListItems();
        setClick();
        initStatusBar();
    }

    private void setClick() {
        R1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean havenet = NetHelper.IsHaveInternet(Info_DoFlow_xj.this);
                if (havenet) {
                    //选择所有人
                    Intent intent = new Intent(Info_DoFlow_xj.this, ryxz_Fragment.class);
                    bundle = new Bundle();
//                    bundle.putSerializable("NWorkToDo", ap);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1);

                } else {
                    new AlertDialog.Builder(Info_DoFlow_xj.this)
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

        zlcl01_B3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (spms.getText().toString().contains("默认") || spms.getText().toString().contains("自动选择")) {
                    new AlertDialog.Builder(Info_DoFlow_xj.this)
                            .setMessage("该审批模式下不能选择人员！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                    return;
                }

                boolean havenet = NetHelper.IsHaveInternet(Info_DoFlow_xj.this);
                if (havenet) {
                    Intent intent = new Intent(Info_DoFlow_xj.this, ryxz_Fragment.class);
                    bundle = new Bundle();
//                    bundle.putSerializable("NWorkToDo", ap);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1);
                    //startActivity(intent);
                } else {
                    new AlertDialog.Builder(Info_DoFlow_xj.this)
                            .setMessage("请检查网络连接设置！")
                            .setTitle("无网络连接")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                }
            }
        });

        zlcl01_up.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                nextuser = zlcl01_T311.getText().toString();
                if (nextuser.equals("")) {
                    new AlertDialog.Builder(Info_DoFlow_xj.this)
                            .setMessage("未选择人员，请选择！")
                            .setTitle("注意")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                } else {
                    boolean havenet = NetHelper.IsHaveInternet(Info_DoFlow_xj.this);
                    if (havenet) {
                        progressDialog = ProgressDialog.show(Info_DoFlow_xj.this, "提交中", "请稍等...");
                        new Thread() {
                            @Override
                            public void run() {
                                String tiaoj = "0";
                                json = WebServiceUtil.AddBanLi(
                                        Integer.parseInt(id),
                                        Integer.parseInt(wid),
                                        fomart[0],
                                        zlcl01_T311.getText().toString(),
                                        tiaoj,
                                        "",
                                        Integer.parseInt(valueid),
                                        spname,
                                        fomart[1]);



                                message = new Message();
                                message.what = 2;
                                handler.sendMessage(message);
                            }
                        }.start();
                    } else {
                        Toast.makeText(getApplicationContext(), "请检查网络连接设置！",
                                Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }


    private void findView() {
        Intent intent = this.getIntent();
 //       ap = (NWork_XJ) intent.getSerializableExtra("NWork_XJ");
        zlcl01_B3 = (Button) findViewById(R.id.zlcl01_B3);
        zlcl01_T3 = (TextView) findViewById(R.id.zlcl01_T3);
        zlcl01_up = (Button) findViewById(R.id.zlcl01_up);
        zlcl01_T2 = (EditText) findViewById(R.id.zlcl01_T2);
        lcxzSpinner = (Spinner) findViewById(R.id.S1);
        spms = (TextView) findViewById(R.id.t2);
        psms = (TextView) findViewById(R.id.t1);
        tjxz = (CheckBox) findViewById(R.id.c1);
        Text1 = (TextView) findViewById(R.id.Text1);
        group = (RadioGroup) findViewById(R.id.radioGroup);

        zlcl01_T31 = (EditText) findViewById(R.id.zlcl01_T31);
        zlcl01_B31 = (Button) findViewById(R.id.zlcl01_B31);
        zlcl01_T311 = (TextView) findViewById(R.id.zlcl01_T311);
        R1 = (RelativeLayout) findViewById(R.id.R1);
        R2 = (RelativeLayout) findViewById(R.id.R2);
        R2.setVisibility(View.GONE);
    }

    private void getBundle() {
        Intent intent = this.getIntent();
        Bundle bundle = this.getIntent().getExtras();
 //       ap = (NWork_XJ) intent.getSerializableExtra("NWorkToDo");
        sNrString = bundle.getString("sNR");
        html = bundle.getString("KEY_NR");
        fomart=html.split("WoEaSy");
     //   Toast.makeText(Info_DoFlow_xj.this, fomart[1], Toast.LENGTH_SHORT).show();


    }


    public void bt_back(View v) {
        finish();
    }

    public void bt_back2(View v) {

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
            StatusBarUtils.setStatusBarColor(Info_DoFlow_xj.this, R.color.btground);
        }
    }

    private void getListItems() {
        boolean havenet = NetHelper.IsHaveInternet(Info_DoFlow_xj.this);
        if (havenet) {
            new Thread() {
                @Override
                public void run() {

                    json = WebServiceUtil.everycanforStr2("wid", "username",
                            "", "", "", "", wid, spname, "",
                            "", "", 0, "NGetNode");

                    if (json != null && !json.equals("0")) {
                        JSONArray jsonObjs;
                        JSONObject jsonObj;
                        JSONTokener jsonTokener = new JSONTokener(json);
                        listitem=new ArrayList<WorkFlowItem>();

                        try {
                            jsonObjs = (JSONArray) jsonTokener.nextValue();
                            for (int i = 0; i < jsonObjs.length(); i++) {
                                jsonObj = (JSONObject) jsonObjs.opt(i);
                                WorkFlowItem email = new WorkFlowItem();
                                email.setValueString(jsonObj .getString("Values"));
                                email.setTextString(jsonObj .getString("Texts"));
                                email.setTijaoString(jsonObj.getString("Tiaojian"));
                                email.setSpmsString(jsonObj.getString("Spms"));
                                email.setPsmsString(jsonObj.getString("Psms"));
                                email.setMrsprString(jsonObj.getString("Mrspr"));
                                listitem.add(email);
                            }
                            message=new Message();
                            message.what=3;
                            handler.sendMessage(message);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                    }

                }
            }.start();

        } else {
            new AlertDialog.Builder(Info_DoFlow_xj.this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
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
}
