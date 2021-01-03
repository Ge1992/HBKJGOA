package com.example.hbkjgoa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.TXL_New2;

import java.util.ArrayList;
import java.util.List;


/**
 * 项目联系Adapter
 *
 * @author George
 */

public class XMLXAdapter3_RY extends BaseAdapter {
	private Context context; // 运行上下文
	private List<TXL_New2> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器
	public List<Boolean> mChecked;
	public final class ListItemView { // 自定义控件集合
		public ImageView image;
		public TextView title,title2;
		public TextView info;
		public TextView detail;
		public TextView ztItem;
		public RelativeLayout R1;
		public LinearLayout L1;
		public CheckBox c1;
	}

	public XMLXAdapter3_RY(Context context, List<TXL_New2> listItems) {
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
	public void setmes(List<TXL_New2> listItems) {
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
		
		
		listItemView.image.setBackgroundResource(R.mipmap.newtx);
		listItemView.c1.setVisibility(View.GONE);

		// 设置控件集到convertView
		listItemView.c1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox)v;
				mChecked.set(x, cb.isChecked());
			}
		});

			listItemView.c1.setChecked(mChecked.get(position));


			listItemView.title.setText(listItems.get(position).getUserName());

			
			

			
			
			
		return  convertView;
	}

}
