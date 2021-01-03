package com.example.hbkjgoa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.Users;

import java.util.ArrayList;
import java.util.List;


public class UsersListViewAdapter_dx extends BaseAdapter {
	private Context context; // 运行上下文
	private List<Users> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器
	private boolean[] hasChecked;
	 public List<Boolean> mChecked;

	public final class ListItemView { // 自定义控件集合
		public ImageView image;
		public TextView title;
		public TextView info;
		public TextView detail;
		public CheckBox c1;
	}

	public UsersListViewAdapter_dx(Context context, List<Users> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
		mChecked = new ArrayList<Boolean>();
		for(int i=0;i<this.listItems.size();i++){
			mChecked.add(false);
		}

	}

	public void setmes(List<Users> listItems) {
		this.listItems = listItems;
		mChecked.clear();
		for(int i=0;i<this.listItems.size();i++){
			mChecked.add(false);
		}
		
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
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.userlistitem2, null);
			// 获取控件对象
			listItemView.image = (ImageView) convertView.findViewById(R.id.imageItem);
			listItemView.title = (TextView) convertView.findViewById(R.id.titleItem);
			listItemView.info = (TextView) convertView.findViewById(R.id.infoItem);
			listItemView.detail = (TextView) convertView.findViewById(R.id.bkItem);
			listItemView.c1 = (CheckBox) convertView.findViewById(R.id.c1);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		
		final int p = position;
		// 设置控件集到convertView
		listItemView.c1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				mChecked.set(p, cb.isChecked());
			}
		});






		listItemView.image.setBackgroundResource(R.mipmap.mini_avatar_shadow);
		listItemView.title.setText(listItems.get(position).getUsername());
		listItemView.c1.setChecked(mChecked.get(position));

		return  convertView;
	}

}
