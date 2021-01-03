package com.example.hbkjgoa;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.hbkjgoa.KS.MainWeixin_KS;
import com.example.hbkjgoa.dbsx.WorkFlowActivity;
import com.example.hbkjgoa.jydj.GOGAO_JY;
import com.example.hbkjgoa.rcgl.New_LDActivity;
import com.example.hbkjgoa.rczyk.gdzc_list;
import com.example.hbkjgoa.rczyk.tysj_list;
import com.example.hbkjgoa.sjyw.Xxglactivity;
import com.example.hbkjgoa.sqsp.InfoWorkFlow_xj;
import com.example.hbkjgoa.tzgg.GOGAO_N;
import com.example.hbkjgoa.tzgg.gzjh_list;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.xmjd.xmjd_list;
import com.example.hbkjgoa.ybsx.WorkFlowDBActivity;
import com.example.hbkjgoa.zcfg.ZCFG_ONE;
import com.example.hbkjgoa.zxing.activity.CaptureActivity;
import com.pgyersdk.update.PgyUpdateManager;
import com.readystatesoftware.viewbadger.BadgeView;

import static android.support.constraint.Constraints.TAG;


/**
 * Created by dm on 18-12-28.
 * 第一个页面
 */
public class FirstFragment extends Fragment {
     LinearLayout L1,L2,L3,L4,L5,L5_1,L6,L7,L8,L9,L10,L11,L12,L13,L14,L15,L16,L5_2
             ,L2_5,L2_6,L2_7,L2_8,L5_3;
     RelativeLayout R1;
    boolean canbo=false;
    public static FirstFragment instance = null;
    private BadgeView badge, badge4, badge5;
     String json,json2,spname;

    private boolean mIsDestroyed = false;

/*    @Override
    public void onDestroy() {
        canbo=false;
        super.onDestroy();
    }

    @Override
    public void onPause() {
        canbo=false;
        super.onPause();
    }

    @Override
    public void onResume() {
        canbo=true;
        super.onResume();
    }*/
    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "Activity onPause");
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Activity onDestroy");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "Activity onResume");
    }


    private void doDestroy() {
        if (mIsDestroyed) {
            return;
        }

        mIsDestroyed = true;

    }
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           	if (msg.what == 7) {
                if (json2 != null && !json2.equals("") && json2.contains("|")) {
                    String[] a = json2.split("\\|");
                    badge = new BadgeView(getActivity(), L6);
                    if (a[0].equals("0")) {

                    } else {
                        badge.setText(a[0] + "");
                        setbadgeView(badge);
                    }

                    badge4 = new BadgeView(getActivity(), L1);
                    if (a[1].equals("0")) {

                    } else {
                        badge4.setText(a[1] + "");
                        setbadgeView(badge4);

                    }
                    badge5 = new BadgeView(getActivity(), L2_8);
                    if (a[3].equals("0")) {

                    } else {
                        badge5.setText(a[3] + "");
                        setbadgeView(badge5);

                    }
                }
            }


        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activityn_gzt, container, false);
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        instance = this;
        canbo=true;
        spname=getActivity().getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE+ Context.MODE_PRIVATE).getString("spname","");
        findview();
        checkUpdate();
        setInfo2();
        initStatusBar();
    }

    private void findview(){
        L1=getView().findViewById(R.id.L1);
        L2=getView().findViewById(R.id.L2);
        L3=getView().findViewById(R.id.L3);
        L4=getView().findViewById(R.id.L4);
        L5=getView().findViewById(R.id.L5);
        L5_1=getView().findViewById(R.id.L6);
        L6=getView().findViewById(R.id.L2_1);
        L7=getView().findViewById(R.id.L2_2);
        L9=getView().findViewById(R.id.L2_4);
        L8=getView().findViewById(R.id.L2_3);
        L10=getView().findViewById(R.id.L3_1);
        L11=getView().findViewById(R.id.L3_2);
        L12=getView(). findViewById(R.id.L4_1);
        L13=getView(). findViewById(R.id.L4_2);
        L14=getView(). findViewById(R.id.L4_3);
        L15=getView(). findViewById(R.id.L4_4);
        L16=getView(). findViewById(R.id.L4_5);
        L5_3=getView(). findViewById(R.id.L5_3);


        L5_2=getView(). findViewById(R.id.L5_2);


        L2_5=getView(). findViewById(R.id.L2_5);
        L2_6=getView(). findViewById(R.id.L2_6);
        L2_7=getView(). findViewById(R.id.L2_7);
        L2_8=getView(). findViewById(R.id.L2_8);



        L1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GOGAO_N.class);
                intent.putExtra("LX", "通知公告");
                startActivity(intent);

            }
        });

        L2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Xxglactivity.class);
                intent.putExtra("id", "0");
                intent.putExtra("LX", "审计要闻");
                startActivity(intent);
            }
        });

        L3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ZCFG_ONE.class);
                intent.putExtra("LX", "政策法规");
                startActivity(intent);

            }
        });

        L4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), New_LDActivity.class);
                startActivity(intent);

            }
        });

        L5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), gzjh_list.class);
                startActivity(intent);
            }
        });
        L5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), gzjh_list.class);
                startActivity(intent);
            }
        });
        L5_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), xmjd_list.class);
                startActivity(intent);
            }
        });

        L6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("sdlxLogin",  Context.MODE_PRIVATE + Context.MODE_PRIVATE).edit();
                editor.putString("nowtitle", "待办事项");
                editor.commit();
                Intent intent = new Intent();
                intent.setClass(getActivity(), WorkFlowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("method", "GetDept");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


        L7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("sdlxLogin",  Context.MODE_PRIVATE + Context.MODE_PRIVATE).edit();
                editor.putString("nowtitle", "已办事项");
                editor.commit();
                Intent intent = new Intent(getActivity(), WorkFlowDBActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID", 0);
                bundle.putString("show", "no");
                intent.putExtras(bundle);
                startActivity(intent);
          //      getActivity().finish();

            }
        });

        L8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent intent = new Intent();
                intent.setClass(getActivity(), InfoWorkFlow_xj.class);
                Bundle bundle = new Bundle();
                intent.putExtra("tile", "用印审批");
                bundle.putString("id","75");
                bundle.putString("wid","67");
                bundle.putString("next","yes");
                intent.putExtras(bundle);
                startActivity(intent);*/

            }
        });





        L9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断Android版本是否为6.0以上，如果是则进行权限允许
                if (Build.VERSION.SDK_INT >= 23){
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA);
                    if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},222);
                        return;
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("tile", "耗材领取");
                intent.setClass(getActivity(), CaptureActivity.class);
                startActivity(intent);
            }
        });

        //设备维修
/*        L2_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断Android版本是否为6.0以上，如果是则进行权限允许
                if (Build.VERSION.SDK_INT >= 23){
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA);
                    if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},222);
                        return;
                    }
                }

                Intent intent = new Intent();
                intent.putExtra("tile", "设备维修");
                intent.setClass(getActivity(), CaptureActivity.class);
                startActivity(intent);

            }
        });*/

        //办公用品领用
/*        L2_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", "78");
                bundle.putString("wid", "71");
                bundle.putString("next", "yes");
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.putExtra("tile", "购买申请");
                intent.setClass(getActivity(), InfoWorkFlow_xj.class);
                startActivity(intent);

            }
        });*/
        //电子数据申请
        L2_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", "85");
                bundle.putString("wid", "80");
                bundle.putString("next", "yes");
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.putExtra("tile", "数据申请");
                intent.setClass(getActivity(), InfoWorkFlow_xj.class);
                startActivity(intent);

            }
        });
        //档案查询
        L2_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Bundle bundle = new Bundle();
                bundle.putString("id", "84");
                bundle.putString("wid", "79");
                bundle.putString("next", "yes");
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.putExtra("tile", "借用登记");
                intent.setClass(getActivity(), InfoWorkFlow_xj.class);
                startActivity(intent);*/

                Intent intent = new Intent(getActivity(), GOGAO_JY.class);
                intent.putExtra("LX", "借用登记");
                startActivity(intent);

            }
        });



        L10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), gdzc_list.class);
                startActivity(intent);



            }
        });

        L11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        L12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), tysj_list.class);
                intent.putExtra("lx", "2");
                intent.putExtra("name", "特约审计员");
                startActivity(intent);

            }
        });
        L13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), tysj_list.class);
                intent.putExtra("lx", "1");
                intent.putExtra("name", "内审人才");
                startActivity(intent);

            }
        });
        L14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), tysj_list.class);
                intent.putExtra("lx", "3");
                intent.putExtra("name", "审计攻关团队");
                startActivity(intent);

            }
        });
        L15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), tysj_list.class);
                intent.putExtra("lx", "4");
                intent.putExtra("name", "审计师");
                startActivity(intent);

            }
        });
        L16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), tysj_list.class);
                intent.putExtra("lx", "5");
                intent.putExtra("name", "中级考试");
                startActivity(intent);

            }
        });

        L5_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), wjdc_list.class);
                startActivity(intent);

            }
        });
        L5_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainWeixin_KS.class);
                startActivity(intent);

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
          getActivity().  getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
            StatusBarUtils.setStatusBarColor(getActivity(), R.color.btground);
        }
    }
    // 角标样式
    private void setbadgeView(BadgeView badge) {
        badge.setTextSize(15);
        badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badge.setBadgeMargin(30, 10);
        badge.setBadgeBackgroundColor(Color.parseColor("#FF0000"));
        TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
        anim.setInterpolator(new BounceInterpolator());
        anim.setDuration(1000);
        badge.toggle(anim, null);
    }

    // 角标接口数据
    public void setInfo2() {
        new Thread() {
            @Override
            public void run() {
                json2 = WebServiceUtil.everycanforStr("username", "", "", "", spname, "", "", 0, "GetDBCount");
                Message message = new Message();
                message.what =7;
                handler.sendMessage(message);
            }
        }.start();
    }
    private void checkUpdate() {
        new PgyUpdateManager.Builder()
                .setForced(true)                //设置是否强制更新
                .setUserCanRetry(true)         //失败后是否提示重新下载
                .setDeleteHistroyApk(true)     // 检查更新前是否删除本地历史 Apk
                .register();

    }
}