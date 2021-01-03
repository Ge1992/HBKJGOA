package com.example.hbkjgoa.Email;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.WebServiceUtil;


/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class ThirdFragment extends Fragment {
    private RelativeLayout R1, R2, R3, R4, R5;
    private TextView t2;
    private String yjs,json,spname,Department;
    RelativeLayout tile1;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if(msg.what==1){
                if(json!=null&&!json.equals("")){
                    String[] a=json.split("\\|");
                   	t2.setText(a[2]);
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_xmjl, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spname = getActivity().getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
        Department = getActivity().getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("Department", "");
        FindView();
        SetClic();
        setInfo();
    }

    private void FindView() {
        R1 = (RelativeLayout) getView().findViewById(R.id.Rx1);
        R2 = (RelativeLayout) getView().findViewById(R.id.Rx2);
        R3 = (RelativeLayout) getView().findViewById(R.id.Rx3);
        R4 = (RelativeLayout) getView().findViewById(R.id.Rx4);
        R5 = (RelativeLayout) getView().findViewById(R.id.Rx5);

        t2=(TextView) getView().findViewById(R.id.t2);
        Intent intent = getActivity().getIntent();//获取传来的intent对象
        //	yjs = intent.getStringExtra("yjs");//获取键值对的键名
        //	t2.setText(yjs);
        tile1=(RelativeLayout)  getView().findViewById(R.id.title1);
        tile1.setVisibility(View.GONE);
    }

    private void SetClic() {
        //写邮件
        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddEmail2.class);
                intent.putExtra("name", "1");
                intent.putExtra("name1", "");
                intent.putExtra("name2","");
                startActivity(intent);
            }
        });
        //收件箱
        R2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), XMJL_SJX.class);
                //	intent.putExtra("name", "收件箱");
                startActivity(intent);
            }
        });
        //草稿箱
        R3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), XMJL_Email.class);
                intent.putExtra("name", "草稿箱");
                startActivity(intent);
            }
        });
        //已删除
        R4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), XMJL_Email.class);
                intent.putExtra("name", "已删除");
                startActivity(intent);
            }
        });
        //已发送
        R5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), XMJL_Email.class);
                intent.putExtra("name", "已发送");
                startActivity(intent);
            }
        });
    }



    public void setInfo() {
        boolean havenet = NetHelper.IsHaveInternet(getActivity());
        if (havenet) {
            new Thread() {
                @Override
                public void run() {
                    json= WebServiceUtil.everycanforStr("username","","","", spname,"", "", 0,"GetDBCount");
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
                }
            }.start();
        }else {
            Toast.makeText(getActivity().getApplicationContext(), "网络连接失败，请检查网络！", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
          /*  Intent intent = new Intent(getActivity(), MainWexin.class);
            startActivity(intent);*/
            getActivity().finish();

        }
        return super.getActivity().onKeyDown(keyCode, event);
    }
    public void bt_back(View v){
     /*   Intent intent = new Intent(getActivity(), MainWexin.class);
        startActivity(intent);*/
        getActivity().finish();
    }

}