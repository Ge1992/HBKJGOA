package com.example.hbkjgoa.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hbkjgoa.Email.Info_YJXX2;
import com.example.hbkjgoa.Email.XMJL_Email;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.Xmjl_sjxBean;
import com.example.hbkjgoa.util.WebServiceUtil;

import java.util.List;


@SuppressLint("InflateParams")
public class XMJL_SJXAdapter3 extends BaseAdapter {
	private Context context; // 运行上下文
	private List<Xmjl_sjxBean> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器
	private String json,spname;
	public final class ListItemView { // 自定义控件集合
		public ImageView image;
		public TextView title;
		public TextView zt;
		public TextView sj;
		public TextView fsr;
		public TextView xmmc;
	}

	public XMJL_SJXAdapter3(Context context, List<Xmjl_sjxBean> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
	}

	@Override
	public int getCount() {
		if (listItems == null) {
			return 0;
		} else {
			return listItems.size();
		}
	}

	@Override
	public Object getItem(int position) {

		return null;
	}

	public void setmes(List<Xmjl_sjxBean> listItems) {
		this.listItems = listItems;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int x=position;
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.item_xmjl_sjx, null);
			// 获取控件对象
			listItemView.image = (ImageView) convertView.findViewById(R.id.imageItem);
			listItemView.title = (TextView) convertView.findViewById(R.id.title);
			listItemView.zt = (TextView) convertView.findViewById(R.id.zt);
			listItemView.sj = (TextView) convertView.findViewById(R.id.sj);
			listItemView.fsr = (TextView) convertView.findViewById(R.id.bkItem);
			listItemView.xmmc = (TextView) convertView.findViewById(R.id.xmmc);
			
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		
		if (listItems.get(position).getEmailState().equals("已读")) {
			listItemView.image.setBackgroundResource(R.mipmap.rmail);
		}else {
			listItemView.image.setBackgroundResource(R.mipmap.mail);
		}

		listItemView.title.setText(listItems.get(position).getEmailTitle());
		listItemView.zt.setText(listItems.get(position).getEmailState());
		listItemView.sj.setText(listItems.get(position).getTimeStr());
		listItemView.fsr.setText(listItems.get(position).getFromUser());
	/*	if (listItems.get(position).getXMMC().equals("")) {
			listItemView.xmmc.setVisibility(View.GONE);
		}else {
			listItemView.xmmc.setText(listItems.get(position).getXMMC());
		}*/
		if (listItems.get(position).getEmailState().equals("已读")) {
			listItemView.image.setBackgroundResource(R.mipmap.rmail);
		}else {
			listItemView.image.setBackgroundResource(R.mipmap.mail);
		}
		
		 spname =  context.getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE)	.getString("spname", "");
		 
				//长按删除
				convertView.setOnLongClickListener(new OnLongClickListener() {
					
					@Override
					public boolean onLongClick(View v) {
						 AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
					        builder.setTitle("提示");
					        builder.setMessage("是否确定删除这条邮件?");
					        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialog, int which) {
					               
					            }
					        });
					        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialog, int which) {
					            /*   if(XMJL_SJX.listact!=null){
					            	   XMJL_SJX.listact.DelEmail(listItems.get(x).getID());
					               }*/
					            	   if(XMJL_Email.listact!=null){
					            		   XMJL_Email.listact.DelEmail(listItems.get(x).getID());
						               }
					            }
					        });
					        AlertDialog dialog = builder.create();
					        dialog.show();
						return true;
					}
				});
		 
	
		convertView.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				Xmjl_sjxBean email = new Xmjl_sjxBean();
				email = listItems.get(x);
				Intent intent = new Intent();
				intent.setClass(context, Info_YJXX2.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("Xmjl_sjxBean", email);
				intent.putExtras(bundle);
				context.startActivity(intent);
				
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						
						json= WebServiceUtil.everycanforStr2("id", "uname", "", "", "", "", listItems.get(x).getID(), spname, "", "", "",0, "SetEmailZT");
					}
				}).start();

			}
		});
		
		
		return convertView;
	}

}
