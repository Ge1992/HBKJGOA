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
import com.example.hbkjgoa.model.DCWJ_Bean;
import com.example.hbkjgoa.model.SJ_Bean;
import com.example.hbkjgoa.tzgg.YWFB;

import java.util.List;


public class WJDCAdapter extends BaseAdapter {
	private Context context; // 运行上下文
	private List<DCWJ_Bean> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器

	public final class ListItemView { // 自定义控件集合
		public TextView text01,text02, text03,text04,text05,text06, text01_1;
		public ImageView image;
	}

	public WJDCAdapter(Context context, List<DCWJ_Bean> listItems) {
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

	public void setmes(List<DCWJ_Bean> listItems) {
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


		listItemView.image.setBackgroundResource(R.mipmap.wsdc);


		listItemView.text01.setText(Html.fromHtml("<font color='#000000'>"+listItems.get(position).getWenJuanName()+"</font> "));
		listItemView.text02.setText(Html.fromHtml("<font color='#3592D1'>"+listItems.get(position).getRemark()+"</font> "));
	    listItemView.text03.setText(Html.fromHtml("开始时间: <font color='#3592D1'>"+listItems.get(position).getStartDate().substring(0, listItems.get(position).getStartDate().length()-9)+"</font> "));
		listItemView.text06.setText(Html.fromHtml("结束时间: <font color='#3592D1'>"+listItems.get(position).getEndDate().substring(0, listItems.get(position).getStartDate().length()-9)+"</font> "));

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new SJ_Bean();
				DCWJ_Bean localGZRZ = listItems.get(w);
				Intent intent = new Intent();
				intent.setClass(context, YWFB.class);
				Bundle localBundle = new Bundle();
				localBundle.putSerializable("HD", localGZRZ);
				intent.putExtras(localBundle);
				context.startActivity(intent);

			}
		});
		return  convertView;
	}

}
