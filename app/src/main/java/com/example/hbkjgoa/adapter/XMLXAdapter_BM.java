package com.example.hbkjgoa.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.TXL_New;
import com.example.hbkjgoa.ryxz.ZCSL_BM;

import java.util.List;


/**
 * 项目联系Adapter
 *
 * @author George
 */

public class XMLXAdapter_BM extends BaseAdapter {
	private Context context; // 运行上下文
	private List<TXL_New> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器
	public final class ListItemView { // 自定义控件集合
		public ImageView image;
		public TextView title;
		public TextView info;
		public TextView detail;
		public TextView ztItem;
		public RelativeLayout R1;
	}

	public XMLXAdapter_BM(Context context, List<TXL_New> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
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
	public void setmes(List<TXL_New> listItems) {
		this.listItems = listItems;
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
			convertView = listContainer.inflate(R.layout.item_xmlx, null);
			// 获取控件对象
			listItemView.image = (ImageView) convertView.findViewById(R.id.im1);
			listItemView.title = (TextView) convertView.findViewById(R.id.tv1);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		
		
		listItemView.image.setBackgroundResource(R.mipmap.bm);
	
	
		
//		String bm[]=listItems.get(0).getMCString().split(",");
		
//			listItemView.title.setText(bm[position]);
		listItemView.title.setText(listItems.get(position).getMC());
		
	//	listItemView.title.setText(listItems.get(0).getChildlist().get(0).getMCString());
		
/*		for (Childlist item :listItems.get(0).getChildlist()) {
			
			Toast.makeText(context,item.getMCString(),
				     Toast.LENGTH_SHORT).show();
			
			listItemView.title.setText(item.getMCString());
		}
		
			
*/
		
		
		
		
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
					new TXL_New();
						TXL_New email = listItems.get(x);
						Intent intent = new Intent();
					    intent.setClass(context, ZCSL_BM.class);
					    Bundle localBundle = new Bundle();
					    localBundle.putSerializable("TXL_New", email);
					    intent.putExtras(localBundle);
					    context.startActivity(intent);
			}
		});
			
		return  convertView;
	}

}
