package com.example.hbkjgoa.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.RYXZ_NBean;
import com.example.hbkjgoa.txl.ZCSL3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 项目联系Adapter
 *
 * @author George
 */

public class RYXZ_NAdapter2 extends BaseAdapter {
	private Context context; // 运行上下文
	private List<RYXZ_NBean> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器
	public List<Boolean> mChecked;
	public final class ListItemView { // 自定义控件集合
		public ImageView image;
		public TextView title;
		public TextView info;
		public TextView detail;
		public TextView ztItem;
		public RelativeLayout R1;
		public LinearLayout L1;
		public CheckBox c1;
	}

	public RYXZ_NAdapter2(Context context, List<RYXZ_NBean> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
		mChecked = new ArrayList<Boolean>();
		for(int i=0;i<this.listItems.size();i++){
			mChecked.add(false);
		}
	}
	@Override
	public int getCount() {
			//return listItems.get(0).getMCString().split(",").length;
			return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		
		return null;
	}
	public void setmes(List<RYXZ_NBean> listItems) {
		this.listItems = listItems;
		mChecked.clear();
		for(int i=0;i<this.listItems.size();i++){
			mChecked.add(false);
		}
	}
	@Override
	public long getItemId(int position) {
		
		return 0;
	}


	@RequiresApi(api = Build.VERSION_CODES.N)
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final int x=position;
		ListItemView listItemView = null;
		if (convertView == null) {

			listItemView = new ListItemView();
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.userlistitem, null);
			// 获取控件对象
			listItemView.image = (ImageView) convertView.findViewById(R.id.im1);
			listItemView.title = (TextView) convertView.findViewById(R.id.tv1);
			listItemView.R1= (RelativeLayout) convertView.findViewById(R.id.R1);
			listItemView.c1 = (CheckBox) convertView.findViewById(R.id.c1);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		
		
		listItemView.image.setBackgroundResource(R.mipmap.bm);

		// 设置控件集到convertView
		listItemView.c1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox)v;
				mChecked.set(x, cb.isChecked());
			}
		});

		listItemView.c1.setChecked(mChecked.get(position));








		if (listItems.get(position).getFenzu().equals("")){
			listItemView.R1.setVisibility(View.GONE);
		}else {
			listItemView.R1.setVisibility(View.VISIBLE);
		}



/*


		if (!listItems.get(position).getFenzu().equals("")){
			listItemView.title.setText(listItems.get(x).getFenzu());
		}else {
			listItemView.title.setText("");
		}


*/


/*
		List<RYXZ_NBean> list = new ArrayList<RYXZ_NBean>();
		RYXZ_NBean student = new RYXZ_NBean();
		student.setFenzu("lishiwei");
		list.add(student);
		Set<RYXZ_NBean> ts = new HashSet<RYXZ_NBean>();
		ts.addAll(list);
		for (RYXZ_NBean s : ts) {
			System.out.println("11111"+student.getFenzu());
		}*/






		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
						new RYXZ_NBean();
						RYXZ_NBean email = listItems.get(x);
						Intent intent = new Intent();
					    intent.setClass(context, ZCSL3.class);
					    Bundle localBundle = new Bundle();
					    localBundle.putSerializable("TXL_New", email);
					    intent.putExtras(localBundle);
					    context.startActivity(intent);
			}
		});
			
		return  convertView;

	}

	private static List<RYXZ_NBean> removeListDuplicateObject(List<RYXZ_NBean> list) {
		System.out.println(Arrays.toString(list.toArray()));
		Set<RYXZ_NBean> set = new HashSet<RYXZ_NBean>();
		set.addAll(list);
		System.out.println(Arrays.toString(set.toArray()));
		List<RYXZ_NBean> listnewList = new ArrayList<RYXZ_NBean>(set);
		return listnewList;
	}

}
