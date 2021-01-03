package com.example.hbkjgoa.rcgl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.GGListViewAdapter2;
import com.example.hbkjgoa.model.HD;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.xlistview.XListView;
import com.example.hbkjgoa.xlistview.XListViewFooter;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class New_LDActivity extends Activity implements XListView.IXListViewListener , AdapterView.OnItemClickListener {
    private MyMonthPager monthPager;
    private TextView textViewYearDisplay;
    private TextView textViewMonthDisplay;
    private TextView backToday;
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private boolean initiated = false;
    private CalendarDate currentDate;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private CustomDayView customDayView;
    private boolean isCurrentMonth = true;
    private boolean isNextYearCurrentMonth = false;
    private String json,json2,spname;
    private int pageNo=1;
    private List<HD> listItems = new ArrayList<HD>();
    public static New_LDActivity listact;
    private ListView listview;
    private GGListViewAdapter2 listViewAdapter;
    private String search="";
    private Button xj;
    private Dialog progressDialog;
    public static New_LDActivity listen;
    private XListView mListView;
    private XListViewFooter footer;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
    private LoadingDialog dialog1;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(android.os.Message msg) {

            if (msg.what == 1) {
                dialog1.dismiss();
                setData(listItems);

            } else if (msg.what == 2) {
                listItems.clear();
                listItems = (List<HD>) msg.obj;
                listViewAdapter.setmes((List<HD>) msg.obj);
                mListView.stopRefresh();
                listViewAdapter.notifyDataSetChanged();
                mListView.setRefreshTime("更新于：" + dateFormat.format(new Date(System.currentTimeMillis())));
            } else if (msg.what == 3) {
                listViewAdapter.setmes(listItems);
                listViewAdapter.notifyDataSetChanged();
                mListView.stopLoadMore();
            }else if (msg.what == 4) {
                if(json2.equals("1")){
                    Toast.makeText(New_LDActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    onRefresh();
                }
            }

        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ldhd_new);
        listact = this;
        monthPager = (MyMonthPager) this.findViewById(R.id.calendar_view);
        textViewYearDisplay = (TextView) findViewById(R.id.show_year_view);
        textViewMonthDisplay = (TextView) findViewById(R.id.show_month_view);
        backToday = ((TextView) this.findViewById(R.id.back_today_button));
        //刷新
        mListView = (XListView) findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
        mListView.setAdapter(listViewAdapter);
        mListView.setXListViewListener(this);
        mListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        mListView.setOnItemClickListener(this);

        mListView.setCacheColorHint(0x00000000);
        mListView.setDivider(getResources().getDrawable(R.color.divider_color));
        mListView.setDividerHeight(1);
        Date now = new Date();
        SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");
        search = d1.format(now);
        getInfo();
        //初始化默认
        currentDate = new CalendarDate();
        textViewYearDisplay.setText(currentDate.getYear() + "年");
        textViewMonthDisplay.setText(currentDate.getMonth() + "月");
        backToday.setBackgroundResource(0);//"字体今天"背景设置1
        monthPager.setScrollble(1, false);

        initCalendarView();
        initStatusBar();
        //回到今天
        backToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarDate today = new CalendarDate();
                calendarAdapter.notifyDataChanged(today);
                textViewYearDisplay.setText(today.getYear() + "年");
                textViewMonthDisplay.setText(today.getMonth() + "月");
                backToday.setBackgroundResource(0);//"字体今天"背景设置2
                monthPager.setScrollble(1, false);
            }
        });
        xj=(Button)findViewById(R.id.xj);
        xj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent();
                intent.setClass(New_LDActivity.this,LDHD_XJ.class);
           //     intent.putExtra("id", "0");
                startActivity(intent);
            }
        });
    }

    private void setData(List<HD> listitem) {
        listViewAdapter = new GGListViewAdapter2(this, listitem);
        mListView.setAdapter(listViewAdapter);
    }

    private void initCalendarView() {
        initListener();
        CustomDayView customDayView = new CustomDayView(this, R.layout.custom_day);
        calendarAdapter = new CalendarViewAdapter(
                this,
                onSelectDateListener,
                CalendarAttr.CalendarType.MONTH,
                CalendarAttr.WeekArrayType.Monday,
                customDayView);
     //   initMarkData();
        initMonthPager();
    }

    /**
     * 初始化monthPager，MonthPager继承自ViewPager
     *
     * @return void
     */
    private void initMonthPager() {
        monthPager.setAdapter(calendarAdapter);
        monthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        monthPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });

        monthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) instanceof com.ldf.calendar.view.Calendar) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    textViewYearDisplay.setText(date.getYear() + "年");
                    textViewMonthDisplay.setText(date.getMonth() + "月");

                    //本年本月
                    CalendarDate today = new CalendarDate();
                    if (date.getYear() == today.getYear() && date.getMonth() == today.getMonth()) {
                        monthPager.setScrollble(1, false);
                        Log.e("666", "=== 本月===");
                        isCurrentMonth = true;
                        isNextYearCurrentMonth = false;
                        //下年的本月
                    } else if (date.getYear() == today.getYear() + 1 && date.getMonth() == today.getMonth()) {
                        monthPager.setScrollble(-1, false);
                        Log.e("666", "=== 下年本月===");
                        isCurrentMonth = false;
                        isNextYearCurrentMonth = true;
                        //其他月
                    } else {
                        monthPager.setScrollble(0, true);
                        Log.e("666", "=== 其他月 ===");
                        isCurrentMonth = false;
                        isNextYearCurrentMonth = false;
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    //日期点击事件
    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {

                //"字体今天"背景设置3
                CalendarDate today = new CalendarDate();

                    backToday.setBackgroundResource(R.drawable.button_bg);

              //      Toast.makeText(getApplicationContext(), today.getYear()+"-"+date.getMonth()+"-"+date.getDay(),     Toast.LENGTH_SHORT).show();

                    search=today.getYear()+"-"+date.getMonth()+"-"+date.getDay();
                    onRefresh();


                currentDate = date;
                textViewYearDisplay.setText(date.getYear() + "年");
                textViewMonthDisplay.setText(date.getMonth() + "月");
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                Log.e("666", "=== offset ===" + offset);
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                if (!isCurrentMonth && !isNextYearCurrentMonth) {
                    monthPager.selectOtherMonth(offset);
             //       Toast.makeText(New_LDActivity.this, "哈哈，我是本月和下年本月之间的月份", Toast.LENGTH_SHORT).show();
                } else if (isCurrentMonth && offset == 1) {
                    monthPager.selectOtherMonth(1);
             //       Toast.makeText(New_LDActivity.this, "呵呵，我是本月", Toast.LENGTH_SHORT).show();
                } else if (isNextYearCurrentMonth && offset == (-1)) {
                    monthPager.selectOtherMonth(-1);
                //    Toast.makeText(New_LDActivity.this, "嘻嘻，我是下年本月", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    /**
     * 初始化标记数据，HashMap的形式，可自定义
     *
     * @return void
     */
    private void initMarkData() {
        HashMap<String, String> markData = new HashMap<>();
        markData.put("2019-1-1", "0");
        markData.put("2019-1-2", "0");
        markData.put("2019-1-3", "0");
        markData.put("2019-1-4", "0");
        calendarAdapter.setMarkData(markData);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !initiated) {
            CalendarDate today = new CalendarDate();
            calendarAdapter.notifyDataChanged(today);
            initiated = true;
        }
    }
    public void bt_back(View v){


        finish();
    }
    @SuppressLint("WorldWriteableFiles")
    private void getInfo(){
        boolean havenet = NetHelper.IsHaveInternet(New_LDActivity.this);
        if (havenet) {
            LoadingDialog.Builder builder1=new LoadingDialog.Builder(New_LDActivity.this)
                    .setMessage("加载中...")
                    .setCancelable(false);
            dialog1=builder1.create();
            dialog1.show();
            new Thread() {
                @Override
                public void run() {

                    spname = getSharedPreferences("sdlxLogin",
                            Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
                    /**
                     * 获取当前时间
                     */
                    SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
                    Date curDate    =   new Date(System.currentTimeMillis());//获取当前时间
                    String str    =    formatter.format(curDate);

                    json = WebServiceUtil.everycanforStr2("", "userid","stime", "", "", "pageNo",
                            "", spname , str, "", "",pageNo, "GetAnPaiList");
                    Log.d("领导数据", json);
                    if (json != null && !json.equals("0")) {
                        JSONArray jsonObjs;
                        JSONObject jsonObj;
                        JSONTokener jsonTokener = new JSONTokener(json);
                        try {

                            jsonObjs = (JSONArray) jsonTokener.nextValue();
                            for (int i = 0; i < jsonObjs.length(); i++) {
                                jsonObj = (JSONObject) jsonObjs.opt(i);
                                HD email = new HD();
                                if (jsonObj.getString("ID") != null) {
                                    email.setID(jsonObj.getString("ID"));
                                } else {
                                    email.setID("");
                                }
                                if (jsonObj.getString("UserName") != null) {
                                    email.setUserName(jsonObj.getString("UserName"));
                                } else {
                                    email.setUserName("");
                                }
                                if (jsonObj.getString("TitleStr") != null) {
                                    email.setTitleStr(jsonObj.getString("TitleStr"));
                                } else {
                                    email.setTitleStr("");
                                }
                                if (jsonObj.getString("ContentStr") != null) {
                                    email.setContentStr(jsonObj.getString("ContentStr"));
                                } else {
                                    email.setContentStr("");
                                }
                                if (jsonObj.getString("TimeStart") != null) {
                                    email.setTimeStart(jsonObj.getString("TimeStart"));
                                } else {
                                    email.setTimeStart("");
                                }
                                if (jsonObj.getString("TimeEnd") != null) {
                                    email.setTimeEnd(jsonObj.getString("TimeEnd"));
                                } else {
                                    email.setTimeEnd("");
                                }
                                if (jsonObj.getString("TimeTiXing") != null) {
                                    email.setTimeTiXing(jsonObj.getString("TimeTiXing"));
                                } else {
                                    email.setTimeTiXing("");
                                }
                                if (jsonObj.getString("TimeStr") != null) {
                                    email.setTimeStr(jsonObj.getString("TimeStr"));
                                } else {
                                    email.setTimeStr("");
                                }
                                if (jsonObj.getString("GongXiangWho") != null) {
                                    email.setGongXiangWho(jsonObj.getString("GongXiangWho"));
                                } else {
                                    email.setGongXiangWho("");
                                }
                                listItems.add(email);

                            }
                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                    }

                }
            }.start();

        } else {
            Toast.makeText(getApplicationContext(), "网络连接失败，请检查网络！", Toast.LENGTH_SHORT).show();

        }

    }
    @Override
    public void onRefresh() {
        pageNo=1;
        boolean havenet = NetHelper.IsHaveInternet(New_LDActivity.this);
        if (havenet) {
            new Thread() {
                @Override
                public void run() {
                    List<HD> list = new ArrayList<HD>();
                    spname = getSharedPreferences("sdlxLogin",
                            Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
                    json = WebServiceUtil.everycanforStr2("", "userid", "stime", "", "", "pageNo",
                            "", spname, search, "", "", pageNo, "GetAnPaiList");

                    Log.d("领导活动", json);
                    if (json != null && !json.equals("0")) {
                        JSONArray jsonObjs;
                        JSONObject jsonObj;
                        JSONTokener jsonTokener = new JSONTokener(json);
                        try {

                            jsonObjs = (JSONArray) jsonTokener.nextValue();
                            for (int i = 0; i < jsonObjs.length(); i++) {
                                jsonObj = (JSONObject) jsonObjs.opt(i);
                                HD email = new HD();
                                if (jsonObj.getString("ID") != null) {
                                    email.setID(jsonObj.getString("ID"));
                                } else {
                                    email.setID("");
                                }
                                if (jsonObj.getString("UserName") != null) {
                                    email.setUserName(jsonObj.getString("UserName"));
                                } else {
                                    email.setUserName("");
                                }
                                if (jsonObj.getString("TitleStr") != null) {
                                    email.setTitleStr(jsonObj.getString("TitleStr"));
                                } else {
                                    email.setTitleStr("");
                                }
                                if (jsonObj.getString("ContentStr") != null) {
                                    email.setContentStr(jsonObj.getString("ContentStr"));
                                } else {
                                    email.setContentStr("");
                                }
                                if (jsonObj.getString("TimeStart") != null) {
                                    email.setTimeStart(jsonObj.getString("TimeStart"));
                                } else {
                                    email.setTimeStart("");
                                }
                                if (jsonObj.getString("TimeEnd") != null) {
                                    email.setTimeEnd(jsonObj.getString("TimeEnd"));
                                } else {
                                    email.setTimeEnd("");
                                }
                                if (jsonObj.getString("TimeTiXing") != null) {
                                    email.setTimeTiXing(jsonObj.getString("TimeTiXing"));
                                } else {
                                    email.setTimeTiXing("");
                                }
                                if (jsonObj.getString("TimeStr") != null) {
                                    email.setTimeStr(jsonObj.getString("TimeStr"));
                                } else {
                                    email.setTimeStr("");
                                }
                                if (jsonObj.getString("GongXiangWho") != null) {
                                    email.setGongXiangWho(jsonObj.getString("GongXiangWho"));
                                } else {
                                    email.setGongXiangWho("");
                                }

                                list.add(email);

                            }
                            Message msg = new Message();
                            msg.what = 2;
                            msg.obj = list;
                            handler.sendMessage(msg);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                    }

                }
            }.start();
        }else {
            Toast.makeText(getApplicationContext(), "网络连接失败，请检查网络！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        boolean havenet = NetHelper.IsHaveInternet(New_LDActivity.this);
        if (havenet) {
            new Thread() {
                @Override
                public void run() {
                    spname = getSharedPreferences("sdlxLogin",
                            Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
                    json = WebServiceUtil.everycanforStr2("", "userid","stime", "", "", "pageNo",
                            "", spname , "2017-10-25", "", "",pageNo, "GetAnPaiList");

                    if (json != null && !json.equals("0")) {
                        JSONArray jsonObjs;
                        JSONObject jsonObj;
                        JSONTokener jsonTokener = new JSONTokener(json);
                        try {

                            jsonObjs = (JSONArray) jsonTokener.nextValue();
                            for (int i = 0; i < jsonObjs.length(); i++) {
                                jsonObj = (JSONObject) jsonObjs.opt(i);
                                HD email = new HD();
                                if (jsonObj.getString("ID") != null) {
                                    email.setID(jsonObj.getString("ID"));
                                } else {
                                    email.setID("");
                                }
                                if (jsonObj.getString("UserName") != null) {
                                    email.setUserName(jsonObj.getString("UserName"));
                                } else {
                                    email.setUserName("");
                                }
                                if (jsonObj.getString("TitleStr") != null) {
                                    email.setTitleStr(jsonObj.getString("TitleStr"));
                                } else {
                                    email.setTitleStr("");
                                }
                                if (jsonObj.getString("ContentStr") != null) {
                                    email.setContentStr(jsonObj.getString("ContentStr"));
                                } else {
                                    email.setContentStr("");
                                }
                                if (jsonObj.getString("TimeStart") != null) {
                                    email.setTimeStart(jsonObj.getString("TimeStart"));
                                } else {
                                    email.setTimeStart("");
                                }
                                if (jsonObj.getString("TimeEnd") != null) {
                                    email.setTimeEnd(jsonObj.getString("TimeEnd"));
                                } else {
                                    email.setTimeEnd("");
                                }
                                if (jsonObj.getString("TimeTiXing") != null) {
                                    email.setTimeTiXing(jsonObj.getString("TimeTiXing"));
                                } else {
                                    email.setTimeTiXing("");
                                }
                                if (jsonObj.getString("TimeStr") != null) {
                                    email.setTimeStr(jsonObj.getString("TimeStr"));
                                } else {
                                    email.setTimeStr("");
                                }
                                if (jsonObj.getString("GongXiangWho") != null) {
                                    email.setGongXiangWho(jsonObj.getString("GongXiangWho"));
                                } else {
                                    email.setGongXiangWho("");
                                }



                                listItems.add(email);

                            }
                            Message msg = new Message();
                            msg.what = 3;
                            handler.sendMessage(msg);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                    }

                }
            }.start();

        } else {
            new AlertDialog.Builder(New_LDActivity.this)
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    public void DeleteRC(final String id){
        new Thread(new Runnable() {

            @Override
            public void run() {

                json2=WebServiceUtil.everycanforStr2("id", "username", "", "", "", "", id, spname, "", "", "",0, "deleteRC");
             Log.d("删除测试",json2);
                Message msg = new Message();
                msg.what = 4;
                handler.sendMessage(msg);
            }
        }).start();

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
            StatusBarUtils.setStatusBarColor(New_LDActivity.this, R.color.btground);
        }
    }
}
