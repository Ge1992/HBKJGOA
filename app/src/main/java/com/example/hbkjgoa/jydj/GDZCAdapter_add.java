package com.example.hbkjgoa.jydj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.GDZC_Bean;

import java.util.List;


public class GDZCAdapter_add extends BaseAdapter {
	private Context context; // 运行上下文
	private List<GDZC_Bean> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器

	public final class ListItemView { // 自定义控件集合
		public TextView text01,text02, text03,text04,text05, text01_1,text06;
		public ImageView image;
	}

	public GDZCAdapter_add(Context context, List<GDZC_Bean> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
		Log.d("yin","listItems:"+listItems);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setmes(List<GDZC_Bean> listItems) {
		this.listItems = listItems;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final int w=position;
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			convertView = listContainer.inflate(R.layout.listitem, null);
			// 获取控件对象
			listItemView.text01 = (TextView) convertView.findViewById(R.id.text01);
			listItemView.text02 = (TextView) convertView.findViewById(R.id.text02);
			listItemView.text03 = (TextView) convertView.findViewById(R.id.text03);
			listItemView.text04 = (TextView) convertView.findViewById(R.id.text04);
			listItemView.text05 = (TextView) convertView.findViewById(R.id.text05);
			listItemView.text06 = (TextView) convertView.findViewById(R.id.text06);
			listItemView.text01_1 = (TextView) convertView.findViewById(R.id.text01_1);
			listItemView.image =  convertView.findViewById(R.id.im1);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}


		listItemView.text05.setVisibility(View.VISIBLE);
		 listItemView.image.setBackgroundResource(R.mipmap.gdzc);
		listItemView.text01.setText((Html.fromHtml("<font color='#000000'>"+listItems.get(position).getShiYongBuMen()+"  -  "+listItems.get(position).getSheBeiName()+"</font> ")));
		listItemView.text02.setText(Html.fromHtml("编码: <font color='#3592D1'>"+listItems.get(position).getYuanBianHao()+"</font>"+"       "+"人员: <font color='#3592D1'>"+listItems.get(position).getGuanLiUser()+"</font>"));
	    listItemView.text03.setText(Html.fromHtml("状态: <font color='#DD5838'>"+listItems.get(position).getZhuangTai()+"</font> "));
		listItemView.text06.setText(Html.fromHtml("购买日期: <font color='#3592D1'>"+listItems.get(position).getQiYongDate()+"</font> "));
		listItemView.text05.setText(Html.fromHtml("型号: <font color='#3592D1'>"+listItems.get(position).getXingHao()+"</font> "));
		convertView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {


				if (listItems.get(w).getZhuangTai().equals("")){
					new GDZC_Bean();
					GDZC_Bean localGZRZ = listItems.get(w);
					Intent intent = new Intent();
					intent.setClass(context, Add_SBJY.class);
					Bundle localBundle = new Bundle();
					localBundle.putSerializable("GDZC", localGZRZ);
					intent.putExtras(localBundle);
					context.startActivity(intent);

				}else {
					Toast.makeText(context, "当前设备不能借用",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		return  convertView;
	}

}
