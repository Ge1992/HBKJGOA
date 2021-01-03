package com.example.hbkjgoa.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.dbsx.GOGAO_DB;
import com.example.hbkjgoa.model.NWorkToDo;

import java.util.List;


public class WFListViewAdapter_SBJL extends BaseAdapter {
	private Context context; // 运行上下文
	private List<NWorkToDo> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器
	private boolean[] hasChecked;

	public final class ListItemView { // 自定义控件集合
		public ImageView image;
		public TextView t1,t2,t3,t4,t5,t6;
	}

	public WFListViewAdapter_SBJL(Context context, List<NWorkToDo> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
	}

	public void setmes(List<NWorkToDo> listItems) {
		this.listItems = listItems;
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

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final int x=position;
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.list_item2_1, null);
			// 获取控件对象
			listItemView.image =  convertView.findViewById(R.id.im1);
			listItemView.t1 =  convertView.findViewById(R.id.text1);
			listItemView.t2 =  convertView.findViewById(R.id.text2);
			listItemView.t3 =  convertView.findViewById(R.id.text3);
			listItemView.t4 =  convertView.findViewById(R.id.text4);
			listItemView.t5 =  convertView.findViewById(R.id.text5);
			listItemView.t6 =  convertView.findViewById(R.id.text6);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		listItemView.image.setBackgroundResource(R.mipmap.stocks);
		listItemView.t1.setText(listItems.get(position).getWorkname());
		listItemView.t2.setText(Html.fromHtml("发起人: <font color='#3592D1'>"+listItems.get(position).getUsernameString()+"</font> "));
		listItemView.t3.setText(Html.fromHtml("待办人员: <font color='#3592D1'>"+listItems.get(position).getShenpiUserString()+"</font> "));
		listItemView.t4.setText(Html.fromHtml("发起时间: <font color='#3592D1'>"+listItems.get(position).getTimestr()+"</font> "));
		listItemView.t5.setText(Html.fromHtml("最新审批时间: <font color='#3592D1'>"+listItems.get(position).getTimestr()+"</font> "));
		listItemView.t6.setText(Html.fromHtml("当前状态: <font color='#3592D1'>"+listItems.get(position).getJiedianNameString()+"</font> "));




	convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NWorkToDo email = new NWorkToDo();
				email =  listItems.get(x);
				Intent intent = new Intent();
				intent.setClass(context, GOGAO_DB.class);
				intent.putExtra("bt", "已办事项");
				Bundle bundle = new Bundle();
				bundle.putSerializable("NWorkToDo", email);
				bundle.putString("show", "no");
				intent.putExtras(bundle);
				context.startActivity(intent);



			}
		});


		
		
		return  convertView;
	}

}
