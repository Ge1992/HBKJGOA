package com.example.hbkjgoa.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.NWorkToDo;

import java.util.List;


public class LogAdapter extends BaseAdapter {
	private Context context; // 运行上下文
	private List<NWorkToDo> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器

	public final class ListItemView { // 自定义控件集合
		public TextView text01,text02, text03,text04,text05, text01_1,text06;
		public ImageView image;
	}

	public LogAdapter(Context context, List<NWorkToDo> listItems) {
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

	public void setmes(List<NWorkToDo> listItems) {
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
			convertView = listContainer.inflate(R.layout.listitem_log, null);
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


		listItemView.text06.setVisibility(View.VISIBLE);
		listItemView.image.setBackgroundResource(R.mipmap.stocks);
		listItemView.text01.setText(Html.fromHtml("修改前: <font color='#3592D1'>"+listItems.get(position).getYNR()+"</font> "));
		listItemView.text02.setText(Html.fromHtml("修改后: <font color='#3592D1'>"+listItems.get(position).getNNR()+"</font> "));
	    listItemView.text03.setText(Html.fromHtml("操作: <font color='#3592D1'>"+listItems.get(position).getZd1()+"</font> "));
		listItemView.text06.setText(Html.fromHtml("修改人: <font color='#3592D1'>"+listItems.get(position).getEditUser()+"</font> "));
		listItemView.text05.setText(Html.fromHtml("修改时间: <font color='#3592D1'>"+listItems.get(position).getEdittime()+"</font> "));

		return  convertView;
	}

}
