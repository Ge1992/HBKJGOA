package com.example.hbkjgoa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.NWorkToDo;

import java.util.List;


public class WFListViewAdapter_db extends BaseAdapter {
	private Context context; // 运行上下文
	private List<NWorkToDo> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器
	private boolean[] hasChecked;

	public final class ListItemView { // 自定义控件集合
		public ImageView image;
		public TextView title;
		public TextView info;
		public TextView detail;
		public TextView zt;
	}

	public WFListViewAdapter_db(Context context, List<NWorkToDo> listItems) {
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
			convertView = listContainer.inflate(R.layout.listitem, null);
			// 获取控件对象
			listItemView.image = (ImageView) convertView.findViewById(R.id.imageItem);
			listItemView.title = (TextView) convertView.findViewById(R.id.titleItem);
			listItemView.info = (TextView) convertView.findViewById(R.id.infoItem);
			listItemView.detail = (TextView) convertView.findViewById(R.id.bkItem);
			listItemView.zt = (TextView) convertView.findViewById(R.id.ztItem);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

	listItemView.image.setBackgroundResource(R.mipmap.stocks);

		listItemView.title.setText(listItems.get(position).getWorkname());
		listItemView.info.setText(listItems.get(position).getJiedianNameString());
		listItemView.detail.setText(listItems.get(position).getStateNowString());
		listItemView.zt.setText(listItems.get(position).getTimestr());
		// 注册按钮点击时间爱你

/*	convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NWorkToDo email = new NWorkToDo();
				email = listItems.get(x);
				Intent intent = new Intent();
					intent.setClass(context,InfoWorkFlowView.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("NWorkToDo", email);
				bundle.putString("show","no");
				intent.putExtras(bundle);
				context.startActivity(intent);
				
			}
		});*/
		
		
		return  convertView;
	}

}
