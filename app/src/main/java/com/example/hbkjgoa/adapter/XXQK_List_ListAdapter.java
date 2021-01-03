package com.example.hbkjgoa.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hbkjgoa.KS.XXQK_List2;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.XXQK_Bean;
import com.example.hbkjgoa.util.photo.ImagePagerActivity;

import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint({ "InflateParams", "HandlerLeak" })
public class XXQK_List_ListAdapter extends BaseAdapter {

	private LayoutInflater listContainer;
	private List<XXQK_Bean> listItems;
	private Context context;
	private String json,spname;

	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==1){
				if(json!=null&&!json.equals("")&&!json.equals("null")){




				}
			}
		}
	};

	public final class ListItemView {
		public TextView text01,text02,text03,text04,text04_1,text05,text06,text06_1;
		public ImageView Im1;
	}



	public XXQK_List_ListAdapter(Context context, List<XXQK_Bean> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
	}

	@Override
	public int getCount() {
		if(listItems!=null){
			return listItems.size();
		}else{
			return 0;
		}

	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	public void setmes(List<XXQK_Bean> listItems) {
		this.listItems = listItems;
	}

	@Override
	public long getItemId(int position) {
		return 1;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;
		final int w=position;
		if (convertView == null) {
			listItemView = new ListItemView();
			convertView = listContainer.inflate(R.layout.listitem_xxqk, null);
			listItemView.text01 = (TextView) convertView.findViewById(R.id.titleItem);

			listItemView.Im1 = (ImageView) convertView.findViewById(R.id.Im1);


			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		listItemView.text01.setText(listItems.get(position).getShiJuanTitle());



		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent=new Intent(context, XXQK_List2.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("YZM",listItems.get(position));
				intent.putExtras(bundle);
				context.startActivity(intent);

			}
		});
		
		return  convertView;
	}



	private void imageBrower(int position, String[] urls) {
		Intent intent = new Intent(context, ImagePagerActivity.class);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		context.startActivity(intent);
	}

}
