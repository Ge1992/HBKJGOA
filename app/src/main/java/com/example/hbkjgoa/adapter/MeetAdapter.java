package com.example.hbkjgoa.adapter;

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

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.Meet_Bean;
import com.example.hbkjgoa.news.Meet_XQXX;
import com.example.hbkjgoa.rczyk.InfoRCXX2;

import java.util.List;


public class MeetAdapter extends BaseAdapter {
	private Context context; // 运行上下文
	private List<Meet_Bean> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器

	public final class ListItemView { // 自定义控件集合
		public TextView text01,text02, text03,text04,text05,text06, text01_1;
		public ImageView image;
	}

	public MeetAdapter(Context context, List<Meet_Bean> listItems) {
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

	public void setmes(List<Meet_Bean> listItems) {
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
			convertView = listContainer.inflate(R.layout.meet_item, null);
			// 获取控件对象
			listItemView.text01 = (TextView) convertView.findViewById(R.id.text01);
			listItemView.text02 = (TextView) convertView.findViewById(R.id.text02);
			listItemView.text03 = (TextView) convertView.findViewById(R.id.text03);
			listItemView.text04 = (TextView) convertView.findViewById(R.id.text04);

			listItemView.image =  convertView.findViewById(R.id.im1);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		listItemView.image.setBackgroundResource(R.mipmap.xmjd);
		listItemView.text01.setText(Html.fromHtml("<font color='#000000'>"+listItems.get(position).getMeetingTitle()+"</font> "));
		listItemView.text02.setText(Html.fromHtml("出席人员: <font color='#3592D1'>"+listItems.get(position).getChuXiRen()+"</font> "));
		listItemView.text03.setText(Html.fromHtml("开始时间: <font color='#3592D1'>"+listItems.get(position).getKaiShiTime()+"</font> "));
		listItemView.text04.setText(Html.fromHtml("结束时间: <font color='#3592D1'>"+listItems.get(position).getJieShuTime()+"</font> "));


		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Meet_Bean();
				Meet_Bean localGZRZ = listItems.get(w);
				Intent intent = new Intent();
				intent.setClass(context, Meet_XQXX.class);
				Bundle localBundle = new Bundle();
				localBundle.putSerializable("HD", localGZRZ);
				intent.putExtras(localBundle);
				context.startActivity(intent);

			}
		});
		return  convertView;
	}

}
