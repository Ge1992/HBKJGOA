package com.example.hbkjgoa.xmjd;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.XMJD_Bean;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.ToastUtils;
import com.example.hbkjgoa.util.WebServiceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class xmjd_Fragment1 extends Fragment {
    private RelativeLayout R1,R2 ,R3,R4,R5;
    private LoadingDialog dialog1;
    private String json,spname;
    private TextView t1_2,t2_2,t3_2,t4_2,t5_2;
    private List<XMJD_Bean.FileList> list=new ArrayList<XMJD_Bean.FileList>();
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                dialog1.dismiss();
            }

        }

    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.listitem_scj, container, false);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();// 从activity传过来的Bundle
        findview();
        getinfo();
    }

    private  void  findview(){
        R1=getView().findViewById(R.id.R1);
        R2=getView().findViewById(R.id.R2);
        R3=getView().findViewById(R.id.R3);
        R4=getView().findViewById(R.id.R4);
        R5=getView().findViewById(R.id.R5);


        t1_2=getView().findViewById(R.id.t1_2);
        t2_2=getView().findViewById(R.id.t2_2);
        t3_2=getView().findViewById(R.id.t3_2);
        t4_2=getView().findViewById(R.id.t4_2);
        t5_2=getView().findViewById(R.id.t5_2);


        R4.setVisibility(View.GONE);
        R5.setVisibility(View.GONE);
    }


    private void getinfo(){
        boolean havenet = NetHelper.IsHaveInternet(getActivity());
        if (havenet) {
            LoadingDialog.Builder builder1=new LoadingDialog.Builder(getActivity())
                    .setMessage("加载中...")
                    .setCancelable(false);
            dialog1=builder1.create();
            dialog1.show();
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        json = WebServiceUtil.everycanforStr("projID", "userName", "", "pageNo","5", spname, "", 1, "Project_Details");

                        if(json != null && !json.equals("0")) {
                            JSONObject jObj = new JSONObject(json);
                            JSONArray jArr = jObj.getJSONArray("projProgress");


                            for (int i = 0; i < jArr.length(); i++){
                                JSONObject  jsonObj = (JSONObject) jArr.opt(i);

                                XMJD_Bean ap = new XMJD_Bean();



                                JSONArray nodArray = jsonObj.getJSONArray("FileList");

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


                                    list.add(wf);

                                }

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
            ToastUtils.showToast(getActivity(), "网络连接失败！");
        }
    }
}
