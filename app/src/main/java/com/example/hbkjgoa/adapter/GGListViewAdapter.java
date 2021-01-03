package com.example.hbkjgoa.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.LanGongGao;
import com.example.hbkjgoa.tzgg.DZGGInfo3_1;

import java.util.List;


public class GGListViewAdapter extends BaseAdapter {
	private Context context; // 运行上下文
	private List<LanGongGao> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器

	public final class ListItemView { // 自定义控件集合
		public TextView text01,text02, text03,text04,text05,text06 ,text01_1;
		public ImageView image;
	}

	public GGListViewAdapter(Context context, List<LanGongGao> listItems) {
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

	public void setmes(List<LanGongGao> listItems) {
		this.listItems = listItems;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ListItemView listItemView = null;
		if (convertView == null) {



			listItemView = new ListItemView();
			convertView = listContainer.inflate(R.layout.listitem, null);
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

		if(listItems.get(position).getSFYY().equals("未签收")){
			//	listItemView.text01_1.setVisibility(View.VISIBLE);
			listItemView.image.setBackgroundResource(R.mipmap.mail);
			listItemView.text01.setText(Html.fromHtml("[未签收] <font color='#000000'>"+listItems.get(position).getTitleString()+"</font> "));
		}else{
			//	listItemView.text01_1.setVisibility(View.GONE);
			listItemView.image.setBackgroundResource(R.mipmap.rmail);
			listItemView.text01.setText(Html.fromHtml(" <font color='#000000'>"+listItems.get(position).getTitleString()+"</font> "));
		}



//		listItemView.text04.setText(listItems.get(position).getSj().replace(" ","\n"));
//		listItemView.text01.setText(listItems.get(position).getTitleString());
//		listItemView.text02.setText(Html.fromHtml("发布单位: <font color='#3592D1'>"+listItems.get(position).getNoticeType()+"</font> "));
		listItemView.text02.setVisibility(View.GONE);
		listItemView.text03.setText(Html.fromHtml("发布人员: <font color='#3592D1'>"+listItems.get(position).getUsernameString()+"</font> "));
		listItemView.text06.setText(Html.fromHtml("发布时间: <font color='#3592D1'>"+listItems.get(position).getTimeString()+"</font> "));

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				LanGongGao email = new LanGongGao();
				email=listItems.get(position);
				Intent intent = new Intent();
				intent.setClass(context, DZGGInfo3_1.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("info", email);
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
		});
		return  convertView;
	}

}
