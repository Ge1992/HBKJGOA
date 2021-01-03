package com.example.hbkjgoa.adapter;

import android.content.Context;
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
import com.example.hbkjgoa.model.XMJD_Bean;
import com.example.hbkjgoa.xmjd.ZQYZT_NEW;

import java.util.List;


public class XMJDA_Fdapter extends BaseAdapter {
	private Context context; // 运行上下文
	private List<XMJD_Bean.projProgress> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器

	public final class ListItemView { // 自定义控件集合
		public TextView text01,text02;
		public ImageView image;
	}

	public XMJDA_Fdapter(Context context, List<XMJD_Bean.projProgress> listItems) {
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

	public void setmes(List<XMJD_Bean.projProgress> listItems) {
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
			convertView = listContainer.inflate(R.layout.listitem_fre, null);
			// 获取控件对象
			listItemView.text01 = (TextView) convertView.findViewById(R.id.t1);
			listItemView.text02 = (TextView) convertView.findViewById(R.id.t1_2);

			listItemView.image =  convertView.findViewById(R.id.im1);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		listItemView.text01.setText(listItems.get(position).getTypeName());
		if (listItems.get(position).getFileCount().equals("0")){
			listItemView.text02.setText("未完成");
		}else {
			listItemView.text02.setText("文件数："+listItems.get(position).getFileCount());
		}


				convertView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						new XMJD_Bean.FileList();
						XMJD_Bean.projProgress localGZRZ = listItems.get(w);
						Intent intent = new Intent();
						intent.setClass(context, ZQYZT_NEW.class);
						Bundle localBundle = new Bundle();
						localBundle.putSerializable("fj", localGZRZ);

						intent.putExtras(localBundle);
						context.startActivity(intent);



			}
		});
		return  convertView;
	}

}
