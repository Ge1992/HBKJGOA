package com.example.hbkjgoa.xmjd;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.XMJDA_Fdapter;
import com.example.hbkjgoa.model.XMJD_Bean;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.NetHelper;
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

public class xmjd_Fragment_n extends Fragment implements AdapterView.OnItemClickListener, XListView.IXListViewListener {
    private RelativeLayout R1,R2 ,R3,R4,R5;
    private LoadingDialog dialog1;
    private String json,spname;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
    private List<XMJD_Bean.projProgress> list=new ArrayList<XMJD_Bean.projProgress>();
    private XListView mListView;
    private XMJDA_Fdapter adapter;
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
            }

        }

    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_list, container, false);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();// 从activity传过来的Bundle
        mListView = getView().findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
        mListView.setAdapter(adapter);
        mListView.setXListViewListener(this);
        mListView.setPullLoadEnable(false);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        mListView.setPullRefreshEnable(false);
        mListView.setOnItemClickListener(this);
        findview();
        getinfo();
    }

    private  void  findview(){

    }
    private void setData(List<XMJD_Bean.projProgress> listitem) {
        adapter = new XMJDA_Fdapter(getActivity(), listitem);
        mListView.setAdapter(adapter);
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



                            JSONTokener jsonTokener=new JSONTokener(json);

                            JSONObject person= (JSONObject) jsonTokener.nextValue();
                            JSONArray  jsonObjs=person.getJSONArray("projProgress");
                            for(int i = 0; i < jsonObjs.length() ; i++){
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
            ToastUtils.showToast(getActivity(), "网络连接失败！");
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
}
