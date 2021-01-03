package com.example.hbkjgoa.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.HD;
import com.example.hbkjgoa.rcgl.InfoLDHD;
import com.example.hbkjgoa.rcgl.New_LDActivity;

import java.util.List;


public class GGListViewAdapter2 extends BaseAdapter {
	private Context context; // 运行上下文
	private List<HD> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器

	public final class ListItemView { // 自定义控件集合
		public ImageView image;
		public TextView title;
		public TextView info;
		public TextView detail;
		public TextView ztItem;
	}

	public GGListViewAdapter2(Context context, List<HD> listItems) {
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
	
	public void setmes(List<HD> listItems) {
		this.listItems = listItems;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int w=position;
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.listitem_ldhd, null);
			// 获取控件对象
			listItemView.image = (ImageView) convertView
					.findViewById(R.id.imageItem);
			listItemView.title = (TextView) convertView
					.findViewById(R.id.titleItem);
			listItemView.info = (TextView) convertView
					.findViewById(R.id.infoItem);
			listItemView.detail = (TextView) convertView
					.findViewById(R.id.bkItem);
			listItemView.ztItem = (TextView) convertView
					.findViewById(R.id.ztItem);
			
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		listItemView.image.setBackgroundResource(R.mipmap.rc);
		/*listItemView.title.setText(listItems.get(position).getTitleString());
	//	  listItemView.info.setText(listItems.get(position).getTimeString());
		  listItemView.detail.setText(listItems.get(position).getTimeString());*/
		
		listItemView.title.setText(listItems.get(position).getTitleStr());
		listItemView.detail.setText(listItems.get(position).getTimeStart()+" --- "+listItems.get(position).getTimeEnd());
		listItemView.ztItem.setText(listItems.get(position).getUserName());
		listItemView.info.setText(listItems.get(position).getGongXiangWho());


		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new HD();
				HD localGZRZ = listItems.get(w);
				Intent intent = new Intent();
				intent.setClass(context, InfoLDHD.class);
				Bundle localBundle = new Bundle();
				localBundle.putSerializable("HD", localGZRZ);
				intent.putExtras(localBundle);
				context.startActivity(intent);

			}
		});

		//长按删除
		convertView.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
				builder.setTitle("提示");
				builder.setMessage("是否确定删除这条日程记录?");
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(New_LDActivity.listact!=null)
							New_LDActivity.listact.DeleteRC(listItems.get(w).getID());
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
				return true;
			}
		});
		return  convertView;
	}

}
